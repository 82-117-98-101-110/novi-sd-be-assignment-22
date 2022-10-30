package com.ravel.backend.userFiles.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_files")
public class UserFiles {

	@Id
	private UUID id;

	private String fileName;

	@Column(length = 100)
	private String fileType;

	private long fileSize;
	private String fileUrl;
	private UUID userUuid;

	@Column(
		name = "created_at",
		columnDefinition = "TIMESTAMP WITH TIME ZONE",
		nullable = false
	)
	private OffsetDateTime createdAt;

	@Column(name = "is_private")
	private boolean isPrivate;
}
