package com.ravel.backend.spacePro.service;

import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.model.SpacePro;
import com.ravel.backend.spacePro.model.SpaceProUser;
import com.ravel.backend.spacePro.model.SpaceProUserDetails;
import com.ravel.backend.spacePro.model.SpaceProUserId;
import com.ravel.backend.spacePro.repository.SpaceProUserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class SpaceProUserService {

	private final SpaceProUserRepository spaceProUserRepository;
	private SpaceProUserDetailsService spaceProUserDetailsService;

	public SpaceProUser getSpaceProUserIfnNotExistCreateNew(
		SpacePro spacePro,
		UUID userUuid
	) {
		validateUserUuid(userUuid);
		SpaceProUserId id = new SpaceProUserId(spacePro.getId(), userUuid);

		if (!spaceProUserRepository.existsBySpaceProUserId(id)) {
			SpaceProUserDetails spaceProUserDetails = spaceProUserDetailsService.createNewSpaceUserDetails();
			SpaceProUser newSpaceProUser = createNewSpaceProUser(
				spacePro,
				id,
				spaceProUserDetails
			);
			return newSpaceProUser;
		} else {
			SpaceProUser spaceProUser = getSpaceProUser(id);
			return spaceProUser;
		}
	}

	public SpaceProUser getSpaceProUserDetails(SpacePro spacePro, UUID userUuid) {
		SpaceProUserId id = new SpaceProUserId(spacePro.getId(), userUuid);
		SpaceProUser spaceProUser = spaceProUserRepository
			.findById(id)
			.orElseThrow(
				() -> new NotFoundException("UserDetails for Space Pro not found")
			);
		return spaceProUser;
	}

	public boolean doesUserHasDetailsForSpacePro(SpacePro spacePro, UUID userUuid) {
		SpaceProUserId id = new SpaceProUserId(spacePro.getId(), userUuid);
		return spaceProUserRepository.existsBySpaceProUserId(id);
	}

	public void deleteSpaceProUserWhereSpaceUuid(Long spaceProId) {
		spaceProUserRepository.deleteBySpaceProUserId_SpaceProId(spaceProId);
	}

	public List<SpaceProUser> getSpaceProUserList(Long spaceProId) {
		return spaceProUserRepository.findBySpaceProUserId_SpaceProId(spaceProId);
	}

	private SpaceProUser getSpaceProUser(SpaceProUserId id) {
		SpaceProUser spaceProUser = spaceProUserRepository
			.findBySpaceProUserId(id)
			.orElseThrow(() -> new NotFoundException("SpaceProUser not found"));
		return spaceProUser;
	}

	private SpaceProUser createNewSpaceProUser(
		SpacePro spacePro,
		SpaceProUserId id,
		SpaceProUserDetails spaceProUserDetails
	) {
		SpaceProUser newSpaceProUser = new SpaceProUser();
		newSpaceProUser.setSpaceProUserId(id);
		newSpaceProUser.setCreated_at(OffsetDateTime.now());
		//		newSpaceProUser.setSpacePro(spacePro);
		newSpaceProUser.setSpaceProUserDetails(spaceProUserDetails);
		return spaceProUserRepository.save(newSpaceProUser);
	}

	private UUID validateUserUuid(UUID userUuid) {
		//TODO implement userUuid check with userService?
		return userUuid;
	}
}
