package com.ravel.backend.spacePro.service;

import com.ravel.backend.spacePro.dto.EnvironmentProGetDto;
import com.ravel.backend.spacePro.dto.EnvironmentProPostDto;
import com.ravel.backend.spacePro.model.EnvironmentPro;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface EnvironmentProService {
	EnvironmentProGetDto createNewProEnvironment(
		EnvironmentProPostDto environmentProPostDto
	);

	EnvironmentProGetDto getActiveEnvironmentByName(String environmentProName);

	EnvironmentPro getEnvironmentByName(String environmentProName);

	EnvironmentProGetDto getActiveEnvironmentByUuid(UUID environmentUuid);

	List<EnvironmentProGetDto> getAllActiveEnvironments();

    List<EnvironmentProGetDto> getActivePublicEnvironments();

    EnvironmentPro uploadEnvironmentImage(
		String environmentName,
		MultipartFile multipartFile
	);
}
