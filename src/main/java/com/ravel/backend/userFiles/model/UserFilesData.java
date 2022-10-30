package com.ravel.backend.userFiles.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_files_data")
public class UserFilesData {

	@Id
	@Column(nullable = false)
	private UUID id;

	@Lob
	private byte[] data;

	private UUID owner;
	private String fileName;
	private long fileSize;

	@Column(length = 100)
	private String fileType;

	@Column(
		name = "created_at",
		columnDefinition = "TIMESTAMP WITH TIME ZONE",
		nullable = true
	)
	private OffsetDateTime createdAt;
}
