package com.ravel.backend.spacePro.utils;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ravel.backend.shared.exception.BadRequestException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EnvironmentAwsS3Service extends EnvironmentsS3Config {

	@Value("${application.directories.aws.s3}")
	private String environmentDirectoryS3;

	public String imageUploadEnvironments(
		String environmentName,
		String currentUrl,
		MultipartFile userProfileImageUrl
	) {
		String directory = environmentDirectoryS3 + "/" + environmentName + "/";
		String currentImageUrl = currentUrl;
		if (currentImageUrl == null) {
			String imageUrl = uploadImageToAmazon(
				userProfileImageUrl,
				directory,
				environmentName
			);
			return imageUrl;
		} else {
			String fileName =
				directory +
				currentImageUrl.substring(currentImageUrl.lastIndexOf("/") + 1);
			removeModuleImageFromAmazon(fileName);
			String imageUrl = uploadImageToAmazon(
				userProfileImageUrl,
				directory,
				environmentName
			);
			return imageUrl;
		}
	}

	private String uploadImageToAmazon(
		MultipartFile multipartFile,
		String directory,
		String uniqueVar
	) {
		// Valid extensions array, like jpeg/jpg and png.
		List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");

		// Get extension of MultipartFile
		String extension = FilenameUtils.getExtension(
			multipartFile.getOriginalFilename()
		);
		if (!validExtensions.contains(extension)) {
			// If file have a invalid extension, call an Exception.
			//            log.warn(MessageUtil.renderMessage("invalid.image.extesion"));
			throw new BadRequestException("File has invalid image extension");
		} else {
			// Upload file to Amazon.
			String url = uploadMultipartFile(multipartFile, directory, uniqueVar);
			return url;
		}
	}

	private void removeModuleImageFromAmazon(String fileName) {
		getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
	}

	private String uploadMultipartFile(
		MultipartFile multipartFile,
		String directory,
		String uniqueVar
	) {
		String fileUrl;

		try {
			// Get the file from MultipartFile.
			File file = AwsS3DataUtils.convertMultipartToFile(multipartFile);

			// Extract the file name.
			String fileName = directory + AwsS3DataUtils.generateFileName(multipartFile);

			// Upload file.
			uploadPublicFile(fileName, file);

			// Delete the file and get the File Url.
			file.delete();
			fileUrl = getUrl().concat(fileName);
		} catch (IOException e) {
			throw new BadRequestException("Exception on conversion of file");
		}

		return fileUrl;
	}

	private void uploadPublicFile(String fileName, File file) {
		getClient()
			.putObject(
				new PutObjectRequest(getBucketName(), fileName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
	}
}
