package com.ravel.backend.users.service;

import com.ravel.backend.users.dtos.*;
import com.ravel.backend.users.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	List<UserDetailsGetDto> findUsersDetailsForOrg(List<UUID> userUuid);

	UserDetailsGetDto signupUser(SignupPostDto signupPostDto);

	UserDetailsGetDto addUser(CreateUserPostDto addUserDto);

	String acceptNewUserInviteOrg(String token, SignupPostDto signupPostDto);

	UserDetailsGetDto updateAvatar(String userEmail, AvatarPostDto avatarPostDto);

	UserDetailsGetDto updateAvatarWithUuid(UUID userUuid, AvatarPostDto avatarPostDto);

    UserDetailsGetDto updateAvatarFullBodyWithUuid(
            UUID userUuid,
            AvatarFullBodyPostDto avatarFullBodyPostDto
    );

    User deactivateUser(String email, Boolean bool);

	User deactivateUserWithUuid(UUID userUuid, Boolean bool);

	User lockUser(String email, Boolean bool);

	User lockUserWithUuid(UUID userUuid, Boolean bool);

	User resetPasswordWithToken(String token, ResetPasswordPost resetPasswordPost);

	GetSelfDto getSelf();

	User getUserByEmailAndIsActive(String email);

	User getUserByUUIDAndIsActive(UUID userUuid);

	boolean existsByEmailAndIsActive(String email);

	UserDetailsGetDto getUserDetailsDtoByEmailAndIsActive(String email);

	UserDetailsGetDto getUserByUuid(UUID userUuid);

	UUID getUserUuidFromAnyExistingUser(String email);

	UUID getUserUuidFromActiveUser(String email);

	void updateUserSystemRole(String role, String userEmail);

	void updateUserSystemRoleWithUuid(String role, UUID userUuid);

	UUID inviteNewUserForOrg(String email, UUID organizationId, String organizationName);

	List<UserDetailsGetDto> findOnlyActiveUsersByEmails(List<String> emails);

	List<UserDetailsGetDto> findOnlyActiveUsersByUuids(List<UUID> uuids);

	List<User> findUsersForOrg(List<UUID> userUuid);

	UUID validateUserForPasswordLess(UUID userUuid);

	List<UserDetailsGetAdminDto> getAllUsersAdmin();

	User sendResetPassword(String userEmail);

	void uploadUserProfileImage(UUID userUUID, MultipartFile userProfileImage);
}
