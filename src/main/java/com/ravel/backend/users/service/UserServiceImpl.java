package com.ravel.backend.users.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.BadRequestException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.dtos.*;
import com.ravel.backend.users.enumeration.Role;
import com.ravel.backend.users.mapper.UserMapper;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private UserMapper userMapper;
	private UserInviteService userInviteService;
	private ResetPasswordService resetPasswordService;
	private UserAwsS3Service userAwsS3Service;
	private IAuthenticationFacade authenticationFacade;
	private EnvironmentProperties environmentProperties;

	@Override
	public GetSelfDto getSelf() {
		Authentication authentication = authenticationFacade.getAuthentication();
		User user = userRepository.findByUserUUID(
			UUID.fromString(authentication.getPrincipal().toString())
		);
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		if (user.getActive() == false) {
			throw new DisabledException("This account is not active");
		}
		if (user.getNotLocked() == false) {
			throw new LockedException("this account is locked");
		}
		GetSelfDto getSelfDeto2 = new GetSelfDto();
		getSelfDeto2.setUserUUID(user.getUserUUID());
		getSelfDeto2.setEmail(user.getEmail());
		getSelfDeto2.setAvatarUrl(user.getAvatarUrl());
		getSelfDeto2.setFirstName(user.getFirstName());
		getSelfDeto2.setSystemAuthorities(user.getAuthorities());
		getSelfDeto2.setLastName(user.getLastName());
		getSelfDeto2.setSystemRole(user.getRole());
		getSelfDeto2.setProfileImageUrl(user.getProfileImageUrl());
		getSelfDeto2.setAvatarUrlFullBody(user.getAvatarUrlFullBody());
		return getSelfDeto2;
	}

	@Override
	public UUID validateUserForPasswordLess(UUID userUuid) {
		User user = userRepository.findByUserUUID(userUuid);
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		if (user.getActive() == false) {
			throw new DisabledException("This account is not active");
		}
		if (user.getNotLocked() == false) {
			throw new LockedException("this account is locked");
		}
		return user.getUserUUID();
	}

	@Override
	public List<UserDetailsGetAdminDto> getAllUsersAdmin() {
		List<User> userList = userRepository.findAll();
		List<UserDetailsGetAdminDto> userDetailsGetDtoList = userMapper.userToUserUserDetailsGetAdminDtoList(
			userList
		);
		return userDetailsGetDtoList;
	}

	@Override
	public User getUserByEmailAndIsActive(String email) {
		if (
			!userRepository.existsByEmailIgnoreCaseAndIsActive(email, true)
		) throw new NotFoundException("User with email " + email + " does not exists");
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User getUserByUUIDAndIsActive(UUID userUuid) {
		User user = userRepository
			.findByUserUUIDOptional(userUuid)
			.orElseThrow(
				() -> {
					NotFoundException notFoundException = new NotFoundException(
						"No user found"
					);
					return notFoundException;
				}
			);

		return user;
	}

	@Override
	public UserDetailsGetDto getUserDetailsDtoByEmailAndIsActive(String email) {
		if (
			!userRepository.existsByEmailIgnoreCaseAndIsActive(email, true)
		) throw new NotFoundException("User with email " + email + " not found");
		User user = userRepository.findUserByEmail(email);
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(user);
		return userDetailsGetDto;
	}

	@Override
	public UUID getUserUuidFromAnyExistingUser(String email) {
		User user = userRepository
			.findOptionalUserByEmail(email)
			.orElseThrow(() -> new NotFoundException("User not found"));
		return user.getUserUUID();
	}

	@Override
	public boolean existsByEmailAndIsActive(String email) {
		boolean user = userRepository.existsByEmailAndIsActive(email, true);
		return user;
	}

	@Override
	public List<UserDetailsGetDto> findOnlyActiveUsersByEmails(List<String> emails) {
		List<User> userList = userRepository.findByEmails(emails);
		List<UserDetailsGetDto> userDetailsGetDtoList = userMapper.userToUserDetailsGetDtoList(
			userList
		);
		return userDetailsGetDtoList;
	}

	@Override
	public UUID getUserUuidFromActiveUser(String email) {
		User user = findUserByEmailAndIsActive(email);
		return user.getUserUUID();
	}

	@Override
	public UserDetailsGetDto getUserByUuid(UUID userUuid) {
		if (
			!userRepository.existsByUserUUIDAndIsActive(userUuid, true)
		) throw new NotFoundException("User with UUID " + userUuid + " does not exists"); //TODO replace with optional repository
		User user = userRepository.findByUserUUID(userUuid); //TODO replace with only active users?
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(user);
		return userDetailsGetDto;
	}

	//TODO only active users?
	@Override
	public List<UserDetailsGetDto> findOnlyActiveUsersByUuids(List<UUID> uuids) {
		List<User> userList = userRepository.findByUuids(uuids);
		List<UserDetailsGetDto> userDetailsGetDtoList = userMapper.userToUserDetailsGetDtoList(
			userList
		);
		return userDetailsGetDtoList;
	}

	@Override
	public List<User> findUsersForOrg(List<UUID> userUuid) {
		List<User> userList2 = userRepository.findByUuids(userUuid);
		return userList2;
	}

	@Override
	public List<UserDetailsGetDto> findUsersDetailsForOrg(List<UUID> userUuid) {
		List<User> userList = userRepository.findByUuids(userUuid);
		List<UserDetailsGetDto> userDetailsGetDtoList = userMapper.userToUserDetailsGetDtoList(
			userList
		);
		return userDetailsGetDtoList;
	}

	//TODO signUpUser, thange into sign-up user with email and reset password invite.
	@Override
	public UserDetailsGetDto signupUser(SignupPostDto signupPostDto) {
		validateNewUserEmail(EMPTY, signupPostDto.getEmail());
		String encodedPassword = bCryptPasswordEncoder.encode(
			signupPostDto.getPassword()
		);
		User user = new User();
		user.setProfileImageUrl(
			tempProfileImage(signupPostDto.getFirstName(), signupPostDto.getLastName())
		);
		user.setFirstName(signupPostDto.getFirstName());
		user.setLastName(signupPostDto.getLastName());
		user.setEmail(signupPostDto.getEmail());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		user.setCreated_at(OffsetDateTime.now());

		userRepository.save(user);
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(user);
		return userDetailsGetDto;
	}

	@Override
	public UserDetailsGetDto addUser(CreateUserPostDto addUserDto) {
		validateNewUserEmail(EMPTY, addUserDto.getEmail());
		String encodedPassword = bCryptPasswordEncoder.encode(addUserDto.getPassword());
		User user = new User();
		String imageUrl = tempProfileImage(
			addUserDto.getFirstName(),
			addUserDto.getLastName()
		);
		user.setProfileImageUrl(imageUrl);
		user.setFirstName(addUserDto.getFirstName());
		user.setLastName(addUserDto.getLastName());
		user.setEmail(addUserDto.getEmail());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(getRoleEnumName(addUserDto.getRole()).name());
		user.setAuthorities(getRoleEnumName(addUserDto.getRole()).getAuthorities());
		user.setCreated_at(OffsetDateTime.now());

		userRepository.save(user);
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(user);
		return userDetailsGetDto;
	}

	@Override
	public void updateUserSystemRole(String role, String userEmail) {
		User currentUser = findUserByEmailAndIsActive(userEmail);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
		currentUser.setUpdatedAt(OffsetDateTime.now());

		userRepository.save(currentUser);
	}

	@Override
	public void updateUserSystemRoleWithUuid(String role, UUID userUuid) {
		User currentUser = findUserByUuidAndIsActive(userUuid);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
		currentUser.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(currentUser);
	}

	@Override
	public UUID inviteNewUserForOrg(
		String email,
		UUID organizationId,
		String organizationName
	) {
		if (userRepository.existsByEmailIgnoreCase(email)) throw new NotFoundException(
			"User already exists"
		);
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setActive(false);
		newUser.setNotLocked(false);
		newUser.setRole(Role.ROLE_USER.name());
		newUser.setAuthorities(Role.ROLE_USER.getAuthorities());
		newUser.setCreated_at(OffsetDateTime.now());
		userRepository.save(newUser);
		User newCreatedUser = userRepository.findByEmailIgnoreCase(email);
		userInviteService.newUserInviteForOrganization(
			newCreatedUser,
			organizationId,
			organizationName
		);
		return newCreatedUser.getUserUUID();
	}

	@Override
	public String acceptNewUserInviteOrg(String token, SignupPostDto signupPostDto) {
		UUID userUUID = userInviteService.confirmToken(token);
		validatePasswords(
			signupPostDto.getPassword(),
			signupPostDto.getPasswordValidate()
		);
		String encodedPassword = bCryptPasswordEncoder.encode(
			signupPostDto.getPassword()
		);
		User user = userRepository
			.findByUserUUIDOptional(userUUID)
			.orElseThrow(
				() -> {
					NotFoundException notFoundException = new NotFoundException(
						"No user found for invite token"
					);
					return notFoundException;
				}
			);
		user.setFirstName(signupPostDto.getFirstName());
		user.setLastName(signupPostDto.getLastName());
		user.setProfileImageUrl(
			tempProfileImage(signupPostDto.getFirstName(), signupPostDto.getLastName())
		);
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(user);
		String redirect = environmentProperties.getFrontendWebURL() + "/login";
		return redirect;
	}

	@Override
	public UserDetailsGetDto updateAvatar(String userEmail, AvatarPostDto avatarPostDto) {
		User currentUser = userRepository
			.findByEmailIgnoreCaseAndIsActive(userEmail, true)
			.orElseThrow(() -> new NotFoundException("User not found"));
		currentUser.setAvatarUrl(avatarPostDto.getAvatarUrl());
		currentUser.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(currentUser);
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(
			currentUser
		);
		return userDetailsGetDto;
	}

	@Override
	public UserDetailsGetDto updateAvatarWithUuid(
		UUID userUuid,
		AvatarPostDto avatarPostDto
	) {
		User currentUser = userRepository
			.findByUserUUIDAndIsActive(userUuid, true)
			.orElseThrow(() -> new NotFoundException("User not found"));
		currentUser.setAvatarUrl(avatarPostDto.getAvatarUrl());
		currentUser.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(currentUser);
		UserDetailsGetDto userDetailsGetDto = userMapper.userToUserDetailsGetDto(
			currentUser
		);
		return userDetailsGetDto;
	}

	@Override
	public UserDetailsGetDto updateAvatarFullBodyWithUuid(
			UUID userUuid,
			AvatarFullBodyPostDto avatarFullBodyPostDto
	) {
		User currentUser = userRepository
				.findByUserUUIDAndIsActive(userUuid, true)
				.orElseThrow(() -> new NotFoundException("User not found"));
		currentUser.setAvatarUrlFullBody(avatarFullBodyPostDto.getAvatarUrl());
		currentUser.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(currentUser);
		return null;
	}



	@Override
	public User deactivateUser(String email, Boolean bool) {
		if (!userRepository.existsByEmailIgnoreCase(email)) throw new NotFoundException(
			"User with email " + email + " does not exists"
		);
		User user = userRepository.findUserByEmail(email);
		user.setUpdatedAt(OffsetDateTime.now());
		user.setActive(bool);
		userRepository.save(user);
		return null;
	}

	@Override
	public User deactivateUserWithUuid(UUID userUuid, Boolean bool) {
		if (!userRepository.existsByUserUUID(userUuid)) throw new NotFoundException(
			"User does not exists"
		);
		User user = userRepository.findByUserUUID(userUuid);
		user.setActive(bool);
		userRepository.save(user);
		return null;
	}

	@Override
	public User lockUser(String email, Boolean bool) {
		if (!userRepository.existsByEmailIgnoreCase(email)) throw new NotFoundException(
			"User with email " + email + " does not exists"
		);
		User user = userRepository.findUserByEmail(email);
		user.setNotLocked(bool);
		user.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(user);
		return null;
	}

	@Override
	public User lockUserWithUuid(UUID userUuid, Boolean bool) {
		if (!userRepository.existsByUserUUID(userUuid)) throw new NotFoundException(
			"User does not exists"
		);
		User user = userRepository.findByUserUUID(userUuid);
		user.setNotLocked(bool);
		user.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(user);
		return null;
	}

	@Override
	public User resetPasswordWithToken(
		String token,
		ResetPasswordPost resetPasswordPost
	) {
		UUID userUuid = resetPasswordService.confirmToken(token);
		validatePasswords(
			resetPasswordPost.getPassword(),
			resetPasswordPost.getPasswordValidate()
		);
		String encodedPassword = bCryptPasswordEncoder.encode(
			resetPasswordPost.getPassword()
		);
		User user = userRepository.findByUserUUID(userUuid);
		user.setNotLocked(true);
		user.setPassword(encodedPassword);
		user.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(user);
		return null;
	}

	@Override
	public User sendResetPassword(String userEmail) {
		//step 1. validate email if it exists, if it does not eixist we trhow no exception so users cannot try to find out which emails are existing
		User user = privateFindUserByEmail(userEmail);
		if (user == null) {
			throw new NotFoundException(
				"User with email " + user.getEmail() + " does not exist"
			);
		}
		//step 2. send resetpasswordemail
		resetPasswordService.newResetPasswordEmail(user);
		return null;
	}

	@Override
	public void uploadUserProfileImage(UUID userUUID, MultipartFile userProfileImage) {
		User existingUser = findUserByUuidAndIsActive(userUUID);
		String userProfileImageUrl = userAwsS3Service.createModuleImageUpload(
			existingUser.getUserUUID(),
			existingUser.getProfileImageUrl(),
			userProfileImage
		);
		existingUser.setProfileImageUrl(userProfileImageUrl);
		existingUser.setUpdatedAt(OffsetDateTime.now());
		userRepository.save(existingUser);
	}

	private User findUserByUuidAndIsActive(UUID userUUID) {
		User existingUser = userRepository
			.findByUserUUIDAndIsActive(userUUID, true)
			.orElseThrow(() -> new NotFoundException("User not found"));
		return existingUser;
	}

	private User findUserByEmailAndIsActive(String userEmail) {
		User user = userRepository
			.findByEmailIgnoreCaseAndIsActive(userEmail, true)
			.orElseThrow(() -> new NotFoundException("User not found"));
		return user;
	}

	private void validatePasswords(String password, String validatePassword) {
		if ((validatePassword == password)) {
			throw new BadRequestException("Passwords don't match");
		}
		//TODO validate password: implement a validate password on special chars
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	private User privateFindUserByEmail(String userEmail) {
		return userRepository.findUserByEmail(userEmail);
	}

	private User validateNewUserEmail(String currentUserEmail, String newUserEmail) {
		User userByNewEmail = privateFindUserByEmail(newUserEmail);
		if (StringUtils.isNotBlank(currentUserEmail)) {
			User currentUser = privateFindUserByEmail(currentUserEmail);
			if (currentUser == null) {
				throw new NotFoundException(
					"User with email " + currentUserEmail + " does not exist"
				);
			}
			if (
				userByNewEmail != null &&
				!currentUser.getUserUUID().equals(userByNewEmail.getUserUUID())
			) {
				throw new AlreadyExistException(
					"New Email with email " + newUserEmail + " is already taken"
				);
			}
			return currentUser;
		} else {
			if (userByNewEmail != null) {
				throw new AlreadyExistException(
					"New Email with " + newUserEmail + " is already taken"
				);
			}
			return null;
		}
	}

	private String tempProfileImage(String firstname, String lastName) {
		String first = String.valueOf(firstname.charAt(0));
		String second = String.valueOf(lastName.charAt(0));
		String initials = first + second;

		String url =
			(
				"https://eu.ui-avatars.com/api/?name=" +
				initials +
				"&color=000000&size=256&bold=true&background=ffffff"
			);
		return url;
	}
}
