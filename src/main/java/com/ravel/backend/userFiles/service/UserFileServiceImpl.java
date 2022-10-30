package com.ravel.backend.userFiles.service;

import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.exception.FileNotUploadedException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.userFiles.dto.UserFilesGetList;
import com.ravel.backend.userFiles.model.UserFiles;
import com.ravel.backend.userFiles.model.UserFilesData;
import com.ravel.backend.userFiles.repository.UserFileRepository;
import com.ravel.backend.userFiles.repository.UserFilesDataRepository;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Transactional
@Service
@AllArgsConstructor
public class UserFileServiceImpl implements UserFileService {

	private final UserFileRepository userFileRepository;
	private final UserFilesDataRepository userFilesDataRepository;
	private IAuthenticationFacade authenticationFacade;

	@Override
	public UserFiles storeFiles(MultipartFile newUserFile) throws IOException {
		try {
			Authentication authentication = authenticationFacade.getAuthentication();
			String fileName = validateFileName(newUserFile.getOriginalFilename());
			UUID uuid = UUID.randomUUID();
			UserFiles userFile = new UserFiles();
			userFile.setId(uuid);
			userFile.setFileName(fileName);
			userFile.setUserUuid(
				UUID.fromString(authentication.getPrincipal().toString())
			);
			userFile.setCreatedAt(OffsetDateTime.now());
			userFile.setPrivate(false);
			//			userFile.setData(newUserFile.getBytes());
			userFile.setFileSize(newUserFile.getSize());
			userFile.setFileType(newUserFile.getContentType());
			String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("api/v1/userfiles/download/")
				.path(String.valueOf(uuid))
				.toUriString();
			userFile.setFileUrl(fileDownloadUri);
			userFileRepository.save(userFile);
			UserFilesData userFilesData = new UserFilesData();
			userFilesData.setId(uuid);
			userFilesData.setData(newUserFile.getBytes());
			userFilesData.setFileType(userFile.getFileType());
			userFilesData.setFileType(userFile.getFileType());
			userFilesData.setFileName(userFile.getFileName());
			userFilesData.setFileSize(userFile.getFileSize());

			userFilesDataRepository.save(userFilesData);
			return null;
		} catch (Exception e) {
			throw new FileNotUploadedException("File has not been stored");
		}
	}

	@Override
	public List<UserFilesGetList> getAllUserFiles() {
		Authentication authentication = authenticationFacade.getAuthentication();
		List<UserFilesGetList> files = userFileRepository
			.findByUserUuid(UUID.fromString(authentication.getPrincipal().toString()))
			.map(
				file -> {
					//					String fileDownloadUri = ServletUriComponentsBuilder
					//						.fromCurrentContextPath()
					//						.path("api/v1/userfiles/download/")
					//						.path(String.valueOf(file.getId()))
					//						.toUriString();
					return new UserFilesGetList(
						file.getId(),
						file.getFileName(),
						file.getFileType(),
						file.isPrivate(),
						file.getFileUrl(),
						file.getFileSize()
					);
				}
			)
			.collect(Collectors.toList());
		return files;
	}

	@Override
	public UserFiles setFileActiveOrPrivate(UUID id, Boolean bool) {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());
		UserFiles personalFile = userFileRepository
			.findByIdAndUserUuid(id, userUuid)
			.orElseThrow(() -> new NotFoundException("File not found"));
		personalFile.setPrivate(bool);
		userFileRepository.save(personalFile);
		return null;
	}

	@Override
	public void deleteUserFile(UUID id) {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());
		if (!userFileRepository.existsByUserUuidAndId(userUuid, id)) {
			throw new NotFoundException("File not found");
		}
		userFileRepository.deleteByUserUuidAndId(userUuid, id);
		userFilesDataRepository.deleteById(id);
	}

	@Override
	public UserFilesData getFile(UUID id) {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());
		if (userFileRepository.existsByIdAndIsPrivate(userUuid, true)) {
			UserFilesData personalFile = userFilesDataRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("File not found"));
			return personalFile;
		} else {
			UserFilesData publicFile = userFilesDataRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("File not found"));
			return publicFile;
		}
	}

	@Override
	public UserFilesData getFile2(UUID id) {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());

		//		if (userFileRepository.existsByIdAndIsPrivate(userUuid, true)) {
		//			UserFiles personalFile = userFileRepository
		//					.findByIdAndUserUuid(id, userUuid)
		//					.orElseThrow(() -> new NotFoundException("File not found"));
		//			return personalFile;
		//		} else {
		UserFilesData publicFile = userFilesDataRepository
			.findById(id)
			.orElseThrow(() -> new NotFoundException("File not found"));
		return publicFile;
		//		}
	}

	private String validateFileName(String filename) {
		String normalizedFilename = StringUtils.cleanPath(filename);
		if (normalizedFilename.contains("< > ? ! @ # $ % ^ * : ;")) {
			throw new FileNotUploadedException("Filename contains invalid characters");
		}
		return normalizedFilename;
	}
}
