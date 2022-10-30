package com.ravel.backend.security.service;

import com.ravel.backend.security.domain.AuthCode;
import com.ravel.backend.security.payload.VerifyCode;
import com.ravel.backend.security.payload.request.AuthenticationRequest;
import com.ravel.backend.security.payload.request.AuthenticationRequestVR;
import com.ravel.backend.security.payload.request.RequestCodeRequest;
import com.ravel.backend.security.payload.response.AuthenticationResponse;
import com.ravel.backend.security.payload.response.RequestCodeResponse;
import com.ravel.backend.security.repository.AuthCodeRepository;
import com.ravel.backend.shared.exception.VrClientAuthException;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.service.UserService;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthorizationService {

	private AuthenticationManager authenticationManager;
	private UserService userService;
	private JwtUtil jwtUtil;
	private AuthCodeRepository authCodeRepository;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private HttpServletRequest request;

	@Value("${security.constant.jwt.audience}")
	private String audience;

	@Value("${security.constant.jwt.clientId}")
	private String clientId;

	@Autowired
	public AuthorizationService(
		UserService userService,
		AuthCodeRepository authCodeRepository
	) {
		this.userService = userService;
		this.authCodeRepository = authCodeRepository;
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	public void setJwtUtils(JwtUtil jwtUtils) {
		this.jwtUtil = jwtUtils;
	}

	public AuthenticationResponse authenticateUser(
		AuthenticationRequest authenticationRequest
	) {
		String username = authenticationRequest.getEmail();
		String password = authenticationRequest.getPassword();

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return createJWT(authenticationRequest.getEmail());
	}

	public RequestCodeResponse requestCodeVrClient(
		RequestCodeRequest requestCodeRequest
	) {
		requestCodeRequest.getClientId(); //TODO VR requestCode: implement validation of getClientId in request Token
		String code = RandomStringUtils.randomAlphanumeric(5, 5).toUpperCase();
		final String ip = getClientIP();
		AuthCode authCode = new AuthCode(
			code,
			OffsetDateTime.now(),
			OffsetDateTime.now().plusMinutes(3)
		);
		authCode.setVerified(false);
		authCode.setActive(true);
		authCode.setIpRequest(ip);
		authCodeRepository.save(authCode);
		return new RequestCodeResponse(
			authCode.getCode(),
			authCode.getCreatedAt(),
			authCode.getExpiresAt()
		);
	}

	public void verifyCodeVrClient(VerifyCode verifyCode) {
		Authentication authentication = authenticationFacade.getAuthentication();
		UUID userUuid = UUID.fromString(authentication.getPrincipal().toString());
		UUID uuid = userService.validateUserForPasswordLess(userUuid);
		AuthCode currentAuthCode = authCodeRepository
			.findByCodeIgnoreCaseAndIsActive(verifyCode.getCode(), true)
			.orElseThrow(() -> new VrClientAuthException("Cannot verify code"));
		if (currentAuthCode.getVerifiedAt() != null) {
			throw new VrClientAuthException("Invite already confirmed");
		}
		OffsetDateTime expiredAt = currentAuthCode.getExpiresAt();
		if (expiredAt.isBefore(OffsetDateTime.now())) {
			throw new VrClientAuthException("Code expired");
		}
		currentAuthCode.setUserUUID(uuid);
		currentAuthCode.setVerifiedAt(OffsetDateTime.now());
		currentAuthCode.setVerified(true);
		authCodeRepository.save(currentAuthCode);
	}

	public AuthenticationResponse authenticateVrClient(
		AuthenticationRequestVR authenticationRequestVR
	) {
		authenticationRequestVR.getClientId(); //TODO VR requestCode: implement validation of getClientId in request Token
		AuthCode currentAuthCode = authCodeRepository
			.findByCodeAndIsActiveAndIsVerified(
				authenticationRequestVR.getCode(),
				true,
				true
			)
			.orElseThrow(() -> new VrClientAuthException("Cannot verify code"));
		currentAuthCode.getUserUUID();
		UserDetailsGetDto user = userService.getUserByUuid(currentAuthCode.getUserUUID()); //TODO replace UserDetailsGetDto to String userEmail.
		OffsetDateTime expiredAt = currentAuthCode.getExpiresAt();
		if (expiredAt.isBefore(OffsetDateTime.now())) {
			throw new VrClientAuthException("Code expired");
		}
		final String ip = getClientIP();
		currentAuthCode.setActive(false);
		currentAuthCode.setIpAuthenticate(ip);
		authCodeRepository.save(currentAuthCode);
		return createJWT(user.getEmail());
	}

	private String getClientIP() {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader != null) {
			return xfHeader.split(",")[0];
		}
		return request.getRemoteAddr();
	}

	private AuthenticationResponse createJWT(String email) {
		User loginUser = userService.getUserByEmailAndIsActive(email);
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(loginUser);
		String jwt = jwtUtil.generateJwtToken(userDetailsImpl);
		return new AuthenticationResponse(
			loginUser.getUserUUID(),
			loginUser.getFirstName(),
			loginUser.getLastName(),
			loginUser.getEmail(),
			loginUser.getAvatarUrl(),
				loginUser.getAvatarUrlFullBody(),
			loginUser.getProfileImageUrl(),
			loginUser.getRole(),
			loginUser.getAuthorities(),
			jwt
		);
	}
}
