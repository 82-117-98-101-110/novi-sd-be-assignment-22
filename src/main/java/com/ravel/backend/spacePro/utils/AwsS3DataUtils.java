package com.ravel.backend.spacePro.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public class AwsS3DataUtils {

	public static File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
		fileOutputStream.write(file.getBytes());
		fileOutputStream.close();
		return convertedFile;
	}

	public static String generateFileName(MultipartFile multipartFile) {
		return Objects
			.requireNonNull(multipartFile.getOriginalFilename())
			.replace(" ", "_");
	}
}
