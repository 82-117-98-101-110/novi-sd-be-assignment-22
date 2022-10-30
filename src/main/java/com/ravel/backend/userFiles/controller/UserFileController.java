package com.ravel.backend.userFiles.controller;

import com.ravel.backend.shared.CachingService;
import com.ravel.backend.userFiles.dto.UserFilesGetList;
import com.ravel.backend.userFiles.model.UserFilesData;
import com.ravel.backend.userFiles.service.UserFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User Files", description = "Uploading and Downloading Files")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(path = "api/v1/userfiles")
public class UserFileController {

	private UserFileService userFileService;

	@Autowired
    CachingService cachingService;

	@GetMapping("clearAllCaches")
	public void clearAllCaches() {
		cachingService.evictAllCaches();
	}

	@Autowired
	public UserFileController(UserFileService userFileService) {
		this.userFileService = userFileService;
	}

	@Operation(
		summary = "Upload a file for user",
		description = "With this endpoint a user can upload a file \n The maximum filesize is 20MB with a minimum filesize threshold of 2KB"
	)
	@PostMapping(name = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			userFileService.storeFiles(file);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@Operation(
		summary = "List the files for user",
		description = "Will return a list of file details for user"
	)
	@GetMapping("")
	public ResponseEntity<List<UserFilesGetList>> getListFiles2() {
		return ResponseEntity.ok().body(userFileService.getAllUserFiles());
	}

	@Operation(
		summary = "Download files",
		description = "With this endpoint a user can download a public or private file.\n Service will first check if file exists and is private, if file is private only the owner of file kan download the file with a access token. If file is not private, all users can download the file with a access token  \n \nThe maximum file request size is 20MB with a minimum filesize threshold of 2KB "
	)
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
		UserFilesData userFilesDB = userFileService.getFile(id);

		return ResponseEntity
			.ok()
			.header(
				HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + userFilesDB.getFileName() + "\""
			)
			.contentType(MediaType.valueOf(userFilesDB.getFileType()))
			.contentLength(userFilesDB.getData().length)
			.body(userFilesDB.getData());
	}

	@Operation(
		summary = "Download files",
		description = "With this endpoint a user can download a public or private file.\n Service will first check if file exists and is private, if file is private only the owner of file kan download the file with a access token. If file is not private, all users can download the file with a access token  \n \nThe maximum file request size is 20MB with a minimum filesize threshold of 2KB "
	)
	@GetMapping(value = "/download/2/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ByteArrayResource getFile2(@PathVariable UUID id) {
		UserFilesData userFilesDB = userFileService.getFile2(id);
		byte[] userFile = userFilesDB.getData();

		return new ByteArrayResource(userFile);
	}

	@Operation(
		summary = "Download files",
		description = "With this endpoint a user can download a public or private file.\n Service will first check if file exists and is private, if file is private only the owner of file kan download the file with a access token. If file is not private, all users can download the file with a access token  \n \nThe maximum file request size is 20MB with a minimum filesize threshold of 2KB "
	)
	@GetMapping(value = "/download/3/{id}")
	public ByteArrayResource getFile3(@PathVariable UUID id) {
		UserFilesData userFilesDB = userFileService.getFile2(id);
		byte[] userFile = userFilesDB.getData();

		return new ByteArrayResource(userFile);
	}

	@Operation(
		summary = "Download files",
		description = "With this endpoint a user can download a public or private file.\n Service will first check if file exists and is private, if file is private only the owner of file kan download the file with a access token. If file is not private, all users can download the file with a access token  \n \nThe maximum file request size is 20MB with a minimum filesize threshold of 2KB "
	)
	@GetMapping(value = "/download/4/{id}")
	public ResponseEntity<byte[]> getFile4(@PathVariable UUID id) {
		UserFilesData userFilesDB = userFileService.getFile2(id);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("File-Name", userFilesDB.getFileName());
		httpHeaders.add(
			HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + userFilesDB.getFileName() + "\""
		);
		return ResponseEntity
			.ok()
			.contentType(MediaType.parseMediaType(userFilesDB.getFileType()))
			.headers(httpHeaders)
			.body(userFilesDB.getData());
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> setFilePrivateOrPublic(
		@Parameter(
			description = "File id",
			example = "e5293532-6ccd-4de1-bb1e-f8cbeecab839"
		) @PathVariable("id") UUID id,
		Boolean bool
	) {
		userFileService.setFileActiveOrPrivate(id, bool);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteUserFile(
		@Parameter(
			description = "File id",
			example = "e5293532-6ccd-4de1-bb1e-f8cbeecab839"
		) @PathVariable("id") UUID id
	) {
		userFileService.deleteUserFile(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
