package com.ravel.backend.users.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ravel.backend.modules.Utils.ModuleAwsS3FileUtils;
import com.ravel.backend.shared.exception.BadRequestException;
import com.ravel.backend.users.utils.UserAwsS3Config;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserAwsS3Service extends UserAwsS3Config {

	@Value("${application.directories.aws.s3}")
	private String environmentDirectoryS3;

	public String createModuleImageUpload(
		UUID userUUID,
		String currentUserProfileImageUrl,
		MultipartFile userProfileImageUrl
	) {
		String directory = environmentDirectoryS3 + userUUID.toString();
		String currentImageUrl = currentUserProfileImageUrl;
		if (currentImageUrl == null) {
			String imageUrl = uploadImageToAmazon(
				userProfileImageUrl,
				directory,
				userUUID.toString()
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
				userUUID.toString()
			);
			return imageUrl;
		}
	}

	private String uploadImageToAmazon(
		MultipartFile multipartFile,
		String directory,
		String userUuid
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
			String url = uploadMultipartFile(multipartFile, directory, userUuid);
			return url;
		}
	}

	private void removeModuleImageFromAmazon(String fileName) {
		getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
	}

	private String uploadMultipartFile(
		MultipartFile multipartFile,
		String directory,
		String userUuid
	) {
		String fileUrl;

		try {
			// Get the file from MultipartFile.
			File file = ModuleAwsS3FileUtils.convertMultipartToFile(multipartFile);

			// Extract the file name.
			String fileName = userUuid;

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
