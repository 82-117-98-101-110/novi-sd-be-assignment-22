package com.ravel.backend.spacePro.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.dto.EnvironmentProGetDto;
import com.ravel.backend.spacePro.dto.EnvironmentProPostDto;
import com.ravel.backend.spacePro.mapper.SpaceProMapper;
import com.ravel.backend.spacePro.model.EnvironmentPro;
import com.ravel.backend.spacePro.repository.EnvironmentProRepository;
import com.ravel.backend.spacePro.utils.EnvironmentAwsS3Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class EnvironmentProServiceImpl implements EnvironmentProService {

	private final EnvironmentProRepository environmentProRepository;

	private SpaceProMapper spaceProMapper;

	private EnvironmentAwsS3Service environmentAwsS3Service;

	@Override
	public EnvironmentProGetDto createNewProEnvironment(
		EnvironmentProPostDto environmentProPostDto
	) {
		validationOfName(EMPTY, environmentProPostDto.getName());
		if (
			environmentProRepository.existsByUnitySceneNameIgnoreCase(
				environmentProPostDto.getUnitySceneName()
			)
		) {
			throw new AlreadyExistException("Unity SceneName already taken");
		}
		EnvironmentPro newEnvironmentPro = EnvironmentPro
			.builder()
			.created_at(OffsetDateTime.now())
			.isActive(true)
			.isPublicEnvironment(environmentProPostDto.isPublicEnvironment())
			.name(environmentProPostDto.getName())
			.description(environmentProPostDto.getDescription())
			.isActive(true)
			.environmentUuid(UUID.randomUUID())
			.unitySceneName(environmentProPostDto.getUnitySceneName())
			.build();
		environmentProRepository.save(newEnvironmentPro);
		EnvironmentProGetDto environmentProGetDto = spaceProMapper.environmentProToEnvironmentProGetDto(
			newEnvironmentPro
		);

		return environmentProGetDto;
	}

	@Override
	public EnvironmentProGetDto getActiveEnvironmentByName(String environmentProName) {
		EnvironmentPro environmentPro = environmentProRepository
			.findByNameIgnoreCase(environmentProName, true)
			.orElseThrow(() -> new NotFoundException("Environment not found"));
		return spaceProMapper.environmentProToEnvironmentProGetDto(environmentPro);
	}

	@Override
	public EnvironmentPro getEnvironmentByName(String environmentProName) {
		EnvironmentPro environmentPro = environmentProRepository
			.findByNameIgnoreCase(environmentProName, true)
			.orElseThrow(() -> new NotFoundException("Environment not found"));
		return environmentPro;
	}

	@Override
	public EnvironmentProGetDto getActiveEnvironmentByUuid(UUID environmentUuid) {
		EnvironmentPro environmentPro = environmentProRepository
			.findByEnvironmentUuidAndIsActive(environmentUuid, true)
			.orElseThrow(() -> new NotFoundException("Environment not found"));
		return spaceProMapper.environmentProToEnvironmentProGetDto(environmentPro);
	}

	@Override
	public List<EnvironmentProGetDto> getAllActiveEnvironments() {
		List<EnvironmentPro> environmentProList = environmentProRepository.findByIsActive(
			true
		);
		return spaceProMapper.environmentProListToEnvironmentProGetDtoList(
			environmentProList
		);
	}

	@Override
	public List<EnvironmentProGetDto> getActivePublicEnvironments() {
		List<EnvironmentPro> environmentProList = environmentProRepository.findByIsPublicEnvironmentAndIsActive(
				true, true
		);
		return spaceProMapper.environmentProListToEnvironmentProGetDtoList(
				environmentProList
		);
	}

	@Override
	public EnvironmentPro uploadEnvironmentImage(
		String environmentName,
		MultipartFile multipartFile
	) {
		EnvironmentPro environmentPro = environmentProRepository
			.findByNameIgnoreCase(environmentName, true)
			.orElseThrow(() -> new NotFoundException("Environment not found"));
		String url = environmentAwsS3Service.imageUploadEnvironments(
			environmentPro.getName(),
			environmentPro.getImageUrl(),
			multipartFile
		);
		environmentPro.setImageUrl(url);
		environmentPro.setUpdatedAt(OffsetDateTime.now());
		return environmentProRepository.save(environmentPro);
	}

	private EnvironmentPro findByName(String environmentName) {
		return environmentProRepository.findByName(environmentName);
	}

	private EnvironmentPro validationOfName(String currentValue, String newValue) {
		EnvironmentPro environmentByNewName = findByName(newValue);
		if (StringUtils.isNotBlank(currentValue)) {
			EnvironmentPro current = findByName(currentValue);
			if (current == null) {
				throw new NotFoundException(
					"Environment" + currentValue + "doest not exist"
				);
			}
			if (
				environmentByNewName != null &&
				!current
					.getEnvironmentUuid()
					.equals(environmentByNewName.getEnvironmentUuid())
			) {
				throw new AlreadyExistException(
					"Name with" + newValue + " is already taken"
				);
			}
			return current;
		} else {
			if (environmentByNewName != null) {
				throw new AlreadyExistException(
					"New name with " + newValue + " is already taken"
				);
			}
			return null;
		}
	}
}
