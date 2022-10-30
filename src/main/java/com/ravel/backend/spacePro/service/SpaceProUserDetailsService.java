package com.ravel.backend.spacePro.service;

import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.spacePro.model.SpaceProUserDetails;
import com.ravel.backend.spacePro.model.SpaceRole;
import com.ravel.backend.spacePro.repository.SpaceProUserDetailsRepository;
import java.time.OffsetDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service
public class SpaceProUserDetailsService {

	private final SpaceProUserDetailsRepository spaceProUserDetailsRepository;
	private SpaceRoleService spaceRoleService;

	public SpaceProUserDetails createNewSpaceUserDetails() {
		SpaceProUserDetails newSpaceProUserDetails = new SpaceProUserDetails();
		newSpaceProUserDetails.setCreated_at(OffsetDateTime.now());
		return spaceProUserDetailsRepository.save(newSpaceProUserDetails);
	}

	public SpaceProUserDetails addRoleToSpaceProUserDetails(Long id, String appRoleName) {
		SpaceRole existingSpaceRole = spaceRoleService.findSpaceRole(appRoleName);
		SpaceProUserDetails existingSpaceProUserDetails = spaceProUserDetailsRepository
			.findById(id)
			.orElseThrow(
				() -> new NotFoundException("Error adding role to User for Space")
			);
		existingSpaceProUserDetails.setUpdatedAt(OffsetDateTime.now());
		existingSpaceProUserDetails.getSpaceRoles().add(existingSpaceRole);
		spaceProUserDetailsRepository.save(existingSpaceProUserDetails);
		return existingSpaceProUserDetails;
	}

	public void deleteDetailsForDeletingSpacePro(List<Long> spaceProId) {
		spaceProUserDetailsRepository.deleteBySpaceProUserDetailsId(spaceProId);
	}
}
