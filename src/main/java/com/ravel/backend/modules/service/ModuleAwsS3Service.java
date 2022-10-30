package com.ravel.backend.modules.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ravel.backend.modules.Utils.ModuleAwsS3Config;
import com.ravel.backend.modules.Utils.ModuleAwsS3FileUtils;
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
public class ModuleAwsS3Service extends ModuleAwsS3Config {

	@Value("${application.directories.aws.s3}")
	private String environmentDirectoryS3;

	public String createModuleImageUpload(
		String moduleName,
		String moduleImageUrl,
		MultipartFile moduleImage
	) {
		String directory = environmentDirectoryS3 + "/app/" + moduleName;
		String currentImageUrl = moduleImageUrl;
		if (currentImageUrl == null) {
			String imageUrl = uploadImageToAmazon(moduleImage, directory);
			return imageUrl;
		} else {
			String fileName =
				directory +
				currentImageUrl.substring(currentImageUrl.lastIndexOf("/") + 1);
			removeModuleImageFromAmazon(fileName);
			String imageUrl = uploadImageToAmazon(moduleImage, directory);
			return imageUrl;
		}
	}

	private String uploadImageToAmazon(MultipartFile multipartFile, String directory) {
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
			String url = uploadMultipartFile(multipartFile, directory);
			return url;
		}
	}

	private void removeModuleImageFromAmazon(String fileName) {
		getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
	}

	private String uploadMultipartFile(MultipartFile multipartFile, String directory) {
		String fileUrl;

		try {
			// Get the file from MultipartFile.
			File file = ModuleAwsS3FileUtils.convertMultipartToFile(multipartFile);

			// Extract the file name.
			String fileName =
				directory + ModuleAwsS3FileUtils.generateFileName(multipartFile);

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
