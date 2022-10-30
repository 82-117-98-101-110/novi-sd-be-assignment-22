package com.ravel.backend.userFiles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserFilesGetList")
public class UserFilesGetList {

	private UUID id;
	private String fileName;
	private String fileType;
	private boolean isPrivate;
	private String fileUrl;
	private long fileSize;

	public UserFilesGetList(
		UUID id,
		String fileName,
		String fileType,
		boolean isPrivate,
		String fileUrl,
		long fileSize
	) {
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
		this.isPrivate = isPrivate;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
	}

	public UserFilesGetList() {}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean aPrivate) {
		isPrivate = aPrivate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
