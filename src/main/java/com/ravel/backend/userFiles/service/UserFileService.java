package com.ravel.backend.userFiles.service;

import com.ravel.backend.userFiles.dto.UserFilesGetList;
import com.ravel.backend.userFiles.model.UserFiles;
import com.ravel.backend.userFiles.model.UserFilesData;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface UserFileService {
	UserFiles storeFiles(MultipartFile file) throws IOException;

	List<UserFilesGetList> getAllUserFiles();

	UserFiles setFileActiveOrPrivate(UUID id, Boolean bool);

	void deleteUserFile(UUID id);

	UserFilesData getFile(UUID id);

	UserFilesData getFile2(UUID id);
}
