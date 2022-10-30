package com.ravel.backend.organization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.appAuth.mappers.AppAuthMapperImpl;
import com.ravel.backend.appAuth.model.AppRole;
import com.ravel.backend.appAuth.repository.AppRoleRepository;
import com.ravel.backend.appAuth.service.AppRoleService;
import com.ravel.backend.appAuth.service.AppRoleServiceImpl;
import com.ravel.backend.email.EmailService;
import com.ravel.backend.email.EmailServiceImpl;
import com.ravel.backend.organization.dtos.DetailedUsersOrganization;
import com.ravel.backend.organization.dtos.GetOrganizationsForUserDto;
import com.ravel.backend.organization.dtos.OrganizationUserRoleOnlyGetDto;
import com.ravel.backend.organization.dtos.OrganizationUsersRoleGetDto;
import com.ravel.backend.organization.mappers.OrganizationMapper;
import com.ravel.backend.organization.mappers.OrganizationMapperImpl;
import com.ravel.backend.organization.model.Organization;
import com.ravel.backend.organization.model.OrganizationInvite;
import com.ravel.backend.organization.model.OrganizationUserRole;
import com.ravel.backend.organization.model.OrganizationUserRoleId;
import com.ravel.backend.organization.repository.OrganizationInviteRepository;
import com.ravel.backend.organization.repository.OrganizationRepository;
import com.ravel.backend.organization.repository.OrganizationUserRoleRepository;
import com.ravel.backend.security.service.IAuthenticationFacade;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.AlreadyExistException;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.mapper.UserMapperImpl;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.ResetPasswordRepository;
import com.ravel.backend.users.repository.UserInviteRepository;
import com.ravel.backend.users.repository.UserRepository;
import com.ravel.backend.users.service.ResetPasswordService;
import com.ravel.backend.users.service.UserAwsS3Service;
import com.ravel.backend.users.service.UserInviteService;
import com.ravel.backend.users.service.UserService;
import com.ravel.backend.users.service.UserServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.spring5.SpringTemplateEngine;

@ContextConfiguration(classes = {OrganizationUserRoleServiceImpl.class, OrganizationInviteService.class,
        EnvironmentProperties.class})
@ActiveProfiles({"h2"})
@ExtendWith(SpringExtension.class)
class OrganizationUserRoleServiceImplTest {
    @MockBean
    private AppRoleService appRoleService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private IAuthenticationFacade iAuthenticationFacade;

    @MockBean
    private OrganizationInviteRepository organizationInviteRepository;

    @MockBean
    private OrganizationMapper organizationMapper;

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private OrganizationService organizationService;

    @MockBean
    private OrganizationUserRoleRepository organizationUserRoleRepository;

    @Autowired
    private OrganizationUserRoleServiceImpl organizationUserRoleServiceImpl;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#addUserEmailToOrgWithoutInviteEmail(String, String, String)}
     */
    @Test
    void testAddUserEmailToOrgWithoutInviteEmail() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any())).thenReturn(true);
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole);

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization1);

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .addUserEmailToOrgWithoutInviteEmail("Organization Name", "jane.doe@example.org", "Organization Role"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#addUserEmailToOrgWithoutInviteEmail(String, String, String)}
     */
    @Test
    void testAddUserEmailToOrgWithoutInviteEmail2() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any()))
                .thenThrow(new NotFoundException("An error occurred"));
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any()))
                .thenThrow(new NotFoundException("An error occurred"));

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.organizationUserRoleServiceImpl
                .addUserEmailToOrgWithoutInviteEmail("Organization Name", "jane.doe@example.org", "Organization Role"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#addUserEmailToOrgWithoutInviteEmail(String, String, String)}
     */
    @Test
    void testAddUserEmailToOrgWithoutInviteEmail3() {
        // Arrange
        UUID randomUUIDResult = UUID.randomUUID();
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(randomUUIDResult);

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any())).thenReturn(false);
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole);

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        UUID randomUUIDResult1 = UUID.randomUUID();
        organization1.setOrganizationId(randomUUIDResult1);
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization1);

        AppRole appRole = new AppRole();
        appRole.setAppPermissions(new HashSet<>());
        appRole.setAppRoleId(123L);
        appRole.setAppRoleName("App Role Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appRole.setDescription("The characteristics of someone or something");
        appRole.setPurpose("Purpose");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.appRoleService.validatePurposeOrganization((String) any())).thenReturn(appRole);

        // Act
        OrganizationUserRoleId actualAddUserEmailToOrgWithoutInviteEmailResult = this.organizationUserRoleServiceImpl
                .addUserEmailToOrgWithoutInviteEmail("Organization Name", "jane.doe@example.org", "Organization Role");

        // Assert
        assertSame(randomUUIDResult1, actualAddUserEmailToOrgWithoutInviteEmailResult.getOrganizationId());
        assertSame(randomUUIDResult, actualAddUserEmailToOrgWithoutInviteEmailResult.getUserUuid());
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.appRoleService).validatePurposeOrganization((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#addUserEmailToOrganizationWithInviteEmail(String, String, String)}
     */
    @Test
    void testAddUserEmailToOrganizationWithInviteEmail() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: Failed to load ApplicationContext
        //   java.lang.IllegalStateException: Could not evaluate condition on org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration due to javax/cache/Caching not found. Make sure your own configuration does not rely on that class. This can also happen if you are @ComponentScanning a springframework package (e.g. if you put a @ComponentScan in the default package by mistake)
        //   java.lang.NoClassDefFoundError: javax/cache/Caching
        //       at java.util.Map.forEach(Map.java:713)
        //       at java.util.Collections$UnmodifiableMap.forEach(Collections.java:1553)
        //   java.lang.ClassNotFoundException: javax.cache.Caching
        //       at java.util.Map.forEach(Map.java:713)
        //       at java.util.Collections$UnmodifiableMap.forEach(Collections.java:1553)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        OrganizationRepository organizationRepository = mock(OrganizationRepository.class);
        when(organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean()))
                .thenReturn(Optional.of(organization));
        when(organizationRepository.existsByOrganizationNameIgnoreCase((String) any())).thenReturn(true);
        OrganizationServiceImpl organizationService = new OrganizationServiceImpl(organizationRepository,
                new OrganizationMapperImpl());

        AppRoleRepository appRoleRepository = mock(AppRoleRepository.class);
        when(appRoleRepository.existsByAppRoleNameAndPurpose((String) any(), (String) any())).thenReturn(true);
        AppRoleServiceImpl appRoleService = new AppRoleServiceImpl(appRoleRepository, new AppAuthMapperImpl());

        IAuthenticationFacade iAuthenticationFacade = mock(IAuthenticationFacade.class);
        when(iAuthenticationFacade.getAuthentication()).thenThrow(new AlreadyExistException("An error occurred"));
        OrganizationRepository organizationRepository1 = mock(OrganizationRepository.class);
        OrganizationUserRoleRepository organizationUserRoleRepository = mock(OrganizationUserRoleRepository.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserRepository userRepository = mock(UserRepository.class);
        UserMapperImpl userMapper = new UserMapperImpl();
        UserInviteRepository userInviteRepository = mock(UserInviteRepository.class);
        OrganizationRepository organizationRepository2 = mock(OrganizationRepository.class);
        OrganizationUserRoleRepository organizationUserRoleRepository1 = mock(OrganizationUserRoleRepository.class);
        OrganizationUserRoleServiceImpl organizationUserRoleService = new OrganizationUserRoleServiceImpl(null, null,
                organizationRepository2, organizationUserRoleRepository1, null, new OrganizationMapperImpl(), null,
                mock(IAuthenticationFacade.class));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        EmailServiceImpl emailService = new EmailServiceImpl(mailSender, new SpringTemplateEngine());

        UserInviteService userInviteService = new UserInviteService(userInviteRepository, organizationUserRoleService,
                emailService, new EnvironmentProperties());

        ResetPasswordRepository resetPasswordRepository = mock(ResetPasswordRepository.class);
        JavaMailSenderImpl mailSender1 = new JavaMailSenderImpl();
        EmailServiceImpl emailService1 = new EmailServiceImpl(mailSender1, new SpringTemplateEngine());

        ResetPasswordService resetPasswordService = new ResetPasswordService(resetPasswordRepository, emailService1,
                new EnvironmentProperties());

        UserAwsS3Service userAwsS3Service = new UserAwsS3Service();
        IAuthenticationFacade authenticationFacade = mock(IAuthenticationFacade.class);
        UserServiceImpl userService = new UserServiceImpl(bCryptPasswordEncoder, userRepository, userMapper,
                userInviteService, resetPasswordService, userAwsS3Service, authenticationFacade, new EnvironmentProperties());

        OrganizationMapperImpl organizationMapper = new OrganizationMapperImpl();
        OrganizationInviteRepository organizationInviteRepository = mock(OrganizationInviteRepository.class);
        JavaMailSenderImpl mailSender2 = new JavaMailSenderImpl();
        EmailServiceImpl emailService2 = new EmailServiceImpl(mailSender2, new SpringTemplateEngine());

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> (new OrganizationUserRoleServiceImpl(organizationService, appRoleService, organizationRepository1,
                        organizationUserRoleRepository, userService, organizationMapper,
                        new OrganizationInviteService(organizationInviteRepository, emailService2, new EnvironmentProperties()),
                        iAuthenticationFacade)).addUserEmailToOrganizationWithInviteEmail("foo", "foo", "foo"));
        verify(organizationRepository).existsByOrganizationNameIgnoreCase((String) any());
        verify(organizationRepository).findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
        verify(appRoleRepository).existsByAppRoleNameAndPurpose((String) any(), (String) any());
        verify(iAuthenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#resentOrganizationInvite(String, String)}
     */
    @Test
    void testResentOrganizationInvite() {
        // Arrange
        UUID randomUUIDResult = UUID.randomUUID();
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(randomUUIDResult);

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        UUID randomUUIDResult1 = UUID.randomUUID();
        organization.setOrganizationId(randomUUIDResult1);
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);

        OrganizationInvite organizationInvite = new OrganizationInvite();
        organizationInvite.setConfirmedAt(null);
        organizationInvite.setCreatedAt(null);
        organizationInvite.setExpiresAt(null);
        organizationInvite.setOrganizationUUID(UUID.randomUUID());
        organizationInvite.setToken("ABC123");
        organizationInvite.setUserUUID(UUID.randomUUID());
        Optional<OrganizationInvite> ofResult = Optional.of(organizationInvite);

        OrganizationInvite organizationInvite1 = new OrganizationInvite();
        organizationInvite1.setConfirmedAt(null);
        organizationInvite1.setCreatedAt(null);
        organizationInvite1.setExpiresAt(null);
        organizationInvite1.setOrganizationUUID(UUID.randomUUID());
        organizationInvite1.setToken("ABC123");
        organizationInvite1.setUserUUID(UUID.randomUUID());
        when(this.organizationInviteRepository.save((OrganizationInvite) any())).thenReturn(organizationInvite1);
        when(this.organizationInviteRepository.findByUserUUIDAndOrganizationUUID((UUID) any(), (UUID) any()))
                .thenReturn(ofResult);
        doNothing().when(this.emailService).inviteUserToOrganizationEmail((String) any(), (String) any(), (String) any());

        // Act
        OrganizationUserRoleId actualResentOrganizationInviteResult = this.organizationUserRoleServiceImpl
                .resentOrganizationInvite("Organization Name", "jane.doe@example.org");

        // Assert
        assertSame(randomUUIDResult1, actualResentOrganizationInviteResult.getOrganizationId());
        assertSame(randomUUIDResult, actualResentOrganizationInviteResult.getUserUuid());
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.organizationInviteRepository).save((OrganizationInvite) any());
        verify(this.organizationInviteRepository).findByUserUUIDAndOrganizationUUID((UUID) any(), (UUID) any());
        verify(this.emailService).inviteUserToOrganizationEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#acceptOrganizationInvite(String)}
     */
    @Test
    void testAcceptOrganizationInvite() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any())).thenReturn(ofResult);

        OrganizationInvite organizationInvite = new OrganizationInvite();
        organizationInvite.setConfirmedAt(null);
        organizationInvite.setCreatedAt(null);
        organizationInvite.setExpiresAt(null);
        organizationInvite.setOrganizationUUID(UUID.randomUUID());
        organizationInvite.setToken("ABC123");
        organizationInvite.setUserUUID(UUID.randomUUID());
        Optional<OrganizationInvite> ofResult1 = Optional.of(organizationInvite);
        when(this.organizationInviteRepository.updateConfirmedAt((String) any(), (OffsetDateTime) any())).thenReturn(1);
        when(this.organizationInviteRepository.findByToken((String) any())).thenReturn(ofResult1);

        // Act
        this.organizationUserRoleServiceImpl.acceptOrganizationInvite("ABC123");

        // Assert
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
        verify(this.organizationInviteRepository).updateConfirmedAt((String) any(), (OffsetDateTime) any());
        verify(this.organizationInviteRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#acceptOrganizationInvite(String)}
     */
    @Test
    void testAcceptOrganizationInvite2() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any()))
                .thenReturn(Optional.empty());

        OrganizationInvite organizationInvite = new OrganizationInvite();
        organizationInvite.setConfirmedAt(null);
        organizationInvite.setCreatedAt(null);
        organizationInvite.setExpiresAt(null);
        organizationInvite.setOrganizationUUID(UUID.randomUUID());
        organizationInvite.setToken("ABC123");
        organizationInvite.setUserUUID(UUID.randomUUID());
        Optional<OrganizationInvite> ofResult = Optional.of(organizationInvite);
        when(this.organizationInviteRepository.updateConfirmedAt((String) any(), (OffsetDateTime) any())).thenReturn(1);
        when(this.organizationInviteRepository.findByToken((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> this.organizationUserRoleServiceImpl.acceptOrganizationInvite("ABC123"));
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
        verify(this.organizationInviteRepository).updateConfirmedAt((String) any(), (OffsetDateTime) any());
        verify(this.organizationInviteRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#updateRole(String, String, String)}
     */
    @Test
    void testUpdateRole() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any())).thenReturn(true);

        Organization organization2 = new Organization();
        organization2.setActive(true);
        organization2.setCreatedAt(null);
        organization2.setOrganizationId(UUID.randomUUID());
        organization2.setOrganizationName("Organization Name");
        organization2.setOrganizationUserRoles(new HashSet<>());
        organization2.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization2);

        AppRole appRole = new AppRole();
        appRole.setAppPermissions(new HashSet<>());
        appRole.setAppRoleId(123L);
        appRole.setAppRoleName("App Role Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appRole.setDescription("The characteristics of someone or something");
        appRole.setPurpose("Purpose");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.appRoleService.validatePurposeOrganization((String) any())).thenReturn(appRole);

        // Act
        this.organizationUserRoleServiceImpl.updateRole("Organization Name", "jane.doe@example.org", "Organization Role");

        // Assert
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findById((OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.appRoleService).validatePurposeOrganization((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#updateRole(String, String, String)}
     */
    @Test
    void testUpdateRole2() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any())).thenReturn(true);

        Organization organization2 = new Organization();
        organization2.setActive(true);
        organization2.setCreatedAt(null);
        organization2.setOrganizationId(UUID.randomUUID());
        organization2.setOrganizationName("Organization Name");
        organization2.setOrganizationUserRoles(new HashSet<>());
        organization2.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization2);
        when(this.appRoleService.validatePurposeOrganization((String) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl.updateRole("Organization Name",
                "jane.doe@example.org", "Organization Role"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationUserRoleRepository).findById((OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.appRoleService).validatePurposeOrganization((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#updateRole(String, String, String)}
     */
    @Test
    void testUpdateRole3() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);
        when(this.organizationUserRoleRepository.existsById((OrganizationUserRoleId) any())).thenReturn(false);

        Organization organization2 = new Organization();
        organization2.setActive(true);
        organization2.setCreatedAt(null);
        organization2.setOrganizationId(UUID.randomUUID());
        organization2.setOrganizationName("Organization Name");
        organization2.setOrganizationUserRoles(new HashSet<>());
        organization2.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization2);

        AppRole appRole = new AppRole();
        appRole.setAppPermissions(new HashSet<>());
        appRole.setAppRoleId(123L);
        appRole.setAppRoleName("App Role Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appRole.setDescription("The characteristics of someone or something");
        appRole.setPurpose("Purpose");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.appRoleService.validatePurposeOrganization((String) any())).thenReturn(appRole);

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl.updateRole("Organization Name",
                "jane.doe@example.org", "Organization Role"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository).existsById((OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#updateRoleWithUuid(String, UUID, String)}
     */
    @Test
    void testUpdateRoleWithUuid() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization1);
        when(this.iAuthenticationFacade.getAuthentication()).thenReturn(new BearerTokenAuthenticationToken("ABC123"));
        when(this.appRoleService.validatePurposeOrganization((String) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .updateRoleWithUuid("Organization Name", UUID.randomUUID(), "Organization Role"));
        verify(this.appRoleService).validatePurposeOrganization((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#updateRoleWithUuid(String, UUID, String)}
     */
    @Test
    void testUpdateRoleWithUuid2() {
        // Arrange
        when(this.organizationUserRoleRepository
                .findById((com.ravel.backend.organization.model.OrganizationUserRoleId) any())).thenReturn(Optional.empty());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);
        when(this.iAuthenticationFacade.getAuthentication()).thenReturn(new BearerTokenAuthenticationToken("ABC123"));

        AppRole appRole = new AppRole();
        appRole.setAppPermissions(new HashSet<>());
        appRole.setAppRoleId(123L);
        appRole.setAppRoleName("App Role Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appRole.setDescription("The characteristics of someone or something");
        appRole.setPurpose("Purpose");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appRole.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.appRoleService.validatePurposeOrganization((String) any())).thenReturn(appRole);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.organizationUserRoleServiceImpl
                .updateRoleWithUuid("Organization Name", UUID.randomUUID(), "Organization Role"));
        verify(this.organizationUserRoleRepository)
                .findById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.appRoleService).validatePurposeOrganization((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#removeUserFromOrganization(String, String)}
     */
    @Test
    void testRemoveUserFromOrganization() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.organizationUserRoleRepository)
                .deleteById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        when(this.organizationUserRoleRepository
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any())).thenReturn(true);

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);

        // Act
        this.organizationUserRoleServiceImpl.removeUserFromOrganization("Organization Name", "jane.doe@example.org");

        // Assert
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository)
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationUserRoleRepository)
                .deleteById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#removeUserFromOrganization(String, String)}
     */
    @Test
    void testRemoveUserFromOrganization2() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());
        doThrow(new AlreadyExistException("An error occurred")).when(this.organizationUserRoleRepository)
                .deleteById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        when(this.organizationUserRoleRepository
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any())).thenReturn(true);

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .removeUserFromOrganization("Organization Name", "jane.doe@example.org"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository)
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationUserRoleRepository)
                .deleteById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#removeUserFromOrganization(String, String)}
     */
    @Test
    void testRemoveUserFromOrganization3() {
        // Arrange
        when(this.userService.getUserUuidFromAnyExistingUser((String) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.organizationUserRoleRepository)
                .deleteById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        when(this.organizationUserRoleRepository
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any())).thenReturn(false);

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .removeUserFromOrganization("Organization Name", "jane.doe@example.org"));
        verify(this.userService).getUserUuidFromAnyExistingUser((String) any());
        verify(this.organizationUserRoleRepository)
                .existsById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationService).getOrganizationByName2((String) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#removeUserFromOrganizationWithUuid(String, UUID)}
     */
    @Test
    void testRemoveUserFromOrganizationWithUuid() {
        // Arrange
        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        when(this.organizationService.getOrganizationByName2((String) any())).thenReturn(organization);
        when(this.iAuthenticationFacade.getAuthentication()).thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .removeUserFromOrganizationWithUuid("Organization Name", UUID.randomUUID()));
        verify(this.organizationService).getOrganizationByName2((String) any());
        verify(this.iAuthenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setNewInvitedUserActive(UUID, UUID)}
     */
    @Test
    void testSetNewInvitedUserActive() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any())).thenReturn(ofResult);
        UUID organizationId = UUID.randomUUID();

        // Act
        this.organizationUserRoleServiceImpl.setNewInvitedUserActive(organizationId, UUID.randomUUID());

        // Assert
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setNewInvitedUserActive(UUID, UUID)}
     */
    @Test
    void testSetNewInvitedUserActive2() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any())).thenReturn(ofResult);
        UUID organizationId = UUID.randomUUID();

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.setNewInvitedUserActive(organizationId, UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setNewInvitedUserActive(UUID, UUID)}
     */
    @Test
    void testSetNewInvitedUserActive3() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any()))
                .thenReturn(Optional.empty());
        UUID organizationId = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> this.organizationUserRoleServiceImpl.setNewInvitedUserActive(organizationId, UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getOrganizationsForUserUuid(UUID)}
     */
    @Test
    void testGetOrganizationsForUserUuid() {
        // Arrange
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());
        ArrayList<GetOrganizationsForUserDto> getOrganizationsForUserDtoList = new ArrayList<>();
        when(this.organizationMapper.organizationToUserOrganizationsGetDtoList((List<Organization>) any()))
                .thenReturn(getOrganizationsForUserDtoList);

        // Act
        List<GetOrganizationsForUserDto> actualOrganizationsForUserUuid = this.organizationUserRoleServiceImpl
                .getOrganizationsForUserUuid(UUID.randomUUID());

        // Assert
        assertSame(getOrganizationsForUserDtoList, actualOrganizationsForUserUuid);
        assertTrue(actualOrganizationsForUserUuid.isEmpty());
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
        verify(this.organizationMapper).organizationToUserOrganizationsGetDtoList((List<Organization>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getOrganizationsForUserUuid(UUID)}
     */
    @Test
    void testGetOrganizationsForUserUuid2() {
        // Arrange
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());
        when(this.organizationMapper.organizationToUserOrganizationsGetDtoList((List<Organization>) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getOrganizationsForUserUuid(UUID.randomUUID()));
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
        verify(this.organizationMapper).organizationToUserOrganizationsGetDtoList((List<Organization>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getOrganizationIdsForUser(UUID)}
     */
    @Test
    void testGetOrganizationIdsForUser() {
        // Arrange
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertTrue(this.organizationUserRoleServiceImpl.getOrganizationIdsForUser(UUID.randomUUID()).isEmpty());
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getOrganizationIdsForUser(UUID)}
     */
    @Test
    void testGetOrganizationIdsForUser2() {
        // Arrange
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getOrganizationIdsForUser(UUID.randomUUID()));
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getDetailedOrganizationsForUserWithUuid(UUID)}
     */
    @Test
    void testGetDetailedOrganizationsForUserWithUuid() {
        // Arrange
        when(this.organizationUserRoleRepository.findByUserUuidAndIsActive((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());
        ArrayList<OrganizationUsersRoleGetDto> organizationUsersRoleGetDtoList = new ArrayList<>();
        when(this.organizationMapper.organizationListToOrganizationUsersRoleGetDtoList((List<OrganizationUserRole>) any()))
                .thenReturn(organizationUsersRoleGetDtoList);

        // Act
        List<OrganizationUsersRoleGetDto> actualDetailedOrganizationsForUserWithUuid = this.organizationUserRoleServiceImpl
                .getDetailedOrganizationsForUserWithUuid(UUID.randomUUID());

        // Assert
        assertSame(organizationUsersRoleGetDtoList, actualDetailedOrganizationsForUserWithUuid);
        assertTrue(actualDetailedOrganizationsForUserWithUuid.isEmpty());
        verify(this.organizationUserRoleRepository).findByUserUuidAndIsActive((UUID) any(), anyBoolean());
        verify(this.organizationMapper)
                .organizationListToOrganizationUsersRoleGetDtoList((List<OrganizationUserRole>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getDetailedOrganizationsForUserWithUuid(UUID)}
     */
    @Test
    void testGetDetailedOrganizationsForUserWithUuid2() {
        // Arrange
        when(this.organizationUserRoleRepository.findByUserUuidAndIsActive((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());
        when(this.organizationMapper.organizationListToOrganizationUsersRoleGetDtoList((List<OrganizationUserRole>) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getDetailedOrganizationsForUserWithUuid(UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).findByUserUuidAndIsActive((UUID) any(), anyBoolean());
        verify(this.organizationMapper)
                .organizationListToOrganizationUsersRoleGetDtoList((List<OrganizationUserRole>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getRoleForUserForOrganization(String, UUID)}
     */
    @Test
    void testGetRoleForUserForOrganization() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository
                .findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser((String) any(), (UUID) any(), anyBoolean()))
                .thenReturn(organizationUserRole);

        OrganizationUserRoleOnlyGetDto organizationUserRoleOnlyGetDto = new OrganizationUserRoleOnlyGetDto();
        organizationUserRoleOnlyGetDto.setOrganizationRole("Organization Role");
        when(this.organizationMapper.entityToOrganizationUserRoleOnlyGetDto((OrganizationUserRole) any()))
                .thenReturn(organizationUserRoleOnlyGetDto);

        // Act and Assert
        assertSame(organizationUserRoleOnlyGetDto,
                this.organizationUserRoleServiceImpl.getRoleForUserForOrganization("Organization Name", UUID.randomUUID()));
        verify(this.organizationUserRoleRepository)
                .findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser((String) any(), (UUID) any(), anyBoolean());
        verify(this.organizationMapper).entityToOrganizationUserRoleOnlyGetDto((OrganizationUserRole) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getRoleForUserForOrganization(String, UUID)}
     */
    @Test
    void testGetRoleForUserForOrganization2() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository
                .findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser((String) any(), (UUID) any(), anyBoolean()))
                .thenReturn(organizationUserRole);
        when(this.organizationMapper.entityToOrganizationUserRoleOnlyGetDto((OrganizationUserRole) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .getRoleForUserForOrganization("Organization Name", UUID.randomUUID()));
        verify(this.organizationUserRoleRepository)
                .findByOrganization_OrganizationNameAndId_UserUuidAndIsActiveUser((String) any(), (UUID) any(), anyBoolean());
        verify(this.organizationMapper).entityToOrganizationUserRoleOnlyGetDto((OrganizationUserRole) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersFromOrganizationsForUser(UUID)}
     */
    @Test
    void testGetUsersFromOrganizationsForUser() {
        // Arrange
        ArrayList<UserDetailsGetDto> userDetailsGetDtoList = new ArrayList<>();
        when(this.userService.findUsersDetailsForOrg((List<UUID>) any())).thenReturn(userDetailsGetDtoList);
        when(this.organizationUserRoleRepository.findById_OrganizationIdAndIsActiveUser((List<UUID>) any()))
                .thenReturn(new ArrayList<>());
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());

        // Act
        List<UserDetailsGetDto> actualUsersFromOrganizationsForUser = this.organizationUserRoleServiceImpl
                .getUsersFromOrganizationsForUser(UUID.randomUUID());

        // Assert
        assertSame(userDetailsGetDtoList, actualUsersFromOrganizationsForUser);
        assertTrue(actualUsersFromOrganizationsForUser.isEmpty());
        verify(this.userService).findUsersDetailsForOrg((List<UUID>) any());
        verify(this.organizationUserRoleRepository).findById_OrganizationIdAndIsActiveUser((List<UUID>) any());
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersFromOrganizationsForUser(UUID)}
     */
    @Test
    void testGetUsersFromOrganizationsForUser2() {
        // Arrange
        when(this.userService.findUsersDetailsForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findById_OrganizationIdAndIsActiveUser((List<UUID>) any()))
                .thenReturn(new ArrayList<>());
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getUsersFromOrganizationsForUser(UUID.randomUUID()));
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersFromOrganizationsForUser(UUID)}
     */
    @Test
    void testGetUsersFromOrganizationsForUser3() {
        // Arrange
        when(this.userService.findUsersDetailsForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findById_OrganizationIdAndIsActiveUser((List<UUID>) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));
        when(this.organizationRepository
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getUsersFromOrganizationsForUser(UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).findById_OrganizationIdAndIsActiveUser((List<UUID>) any());
        verify(this.organizationRepository)
                .findByOrganizationUserRoles_Id_UserUuidAndOrganizationUserRoles_IsActiveUser((UUID) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization() {
        // Arrange
        when(this.userService.findUsersForOrg((List<java.util.UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(new ArrayList<>());
        ArrayList<DetailedUsersOrganization> detailedUsersOrganizationList = new ArrayList<>();
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenReturn(detailedUsersOrganizationList);

        // Act
        List<DetailedUsersOrganization> actualUsersForOrganization = this.organizationUserRoleServiceImpl
                .getUsersForOrganization("Organization Name");

        // Assert
        assertSame(detailedUsersOrganizationList, actualUsersForOrganization);
        assertTrue(actualUsersForOrganization.isEmpty());
        verify(this.userService).findUsersForOrg((List<java.util.UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization2() {
        // Arrange
        when(this.userService.findUsersForOrg((List<java.util.UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(new ArrayList<>());
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.getUsersForOrganization("Organization Name"));
        verify(this.userService).findUsersForOrg((List<java.util.UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization3() {
        // Arrange
        when(this.userService.findUsersForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<DetailedUsersOrganization> detailedUsersOrganizationList = new ArrayList<>();
        detailedUsersOrganizationList.add(new DetailedUsersOrganization(UUID.randomUUID(), "Jane", "Doe",
                "jane.doe@example.org", "https://example.org/example", null, "Organization Role", true));
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenReturn(detailedUsersOrganizationList);

        // Act
        List<DetailedUsersOrganization> actualUsersForOrganization = this.organizationUserRoleServiceImpl
                .getUsersForOrganization("Organization Name");

        // Assert
        assertSame(detailedUsersOrganizationList, actualUsersForOrganization);
        assertEquals(1, actualUsersForOrganization.size());
        verify(this.userService).findUsersForOrg((List<UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization4() {
        // Arrange
        when(this.userService.findUsersForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<DetailedUsersOrganization> detailedUsersOrganizationList = new ArrayList<>();
        detailedUsersOrganizationList.add(new DetailedUsersOrganization(UUID.randomUUID(), "Jane", "Doe",
                "jane.doe@example.org", "https://example.org/example", null, "Organization Role", true));
        detailedUsersOrganizationList.add(new DetailedUsersOrganization(UUID.randomUUID(), "Jane", "Doe",
                "jane.doe@example.org", "https://example.org/example", null, "Organization Role", true));
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenReturn(detailedUsersOrganizationList);

        // Act
        List<DetailedUsersOrganization> actualUsersForOrganization = this.organizationUserRoleServiceImpl
                .getUsersForOrganization("Organization Name");

        // Assert
        assertSame(detailedUsersOrganizationList, actualUsersForOrganization);
        assertEquals(2, actualUsersForOrganization.size());
        verify(this.userService).findUsersForOrg((List<UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization5() {
        // Arrange
        when(this.userService.findUsersForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());

        ArrayList<OrganizationUserRole> organizationUserRoleList = new ArrayList<>();
        organizationUserRoleList.add(organizationUserRole);
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(organizationUserRoleList);

        ArrayList<DetailedUsersOrganization> detailedUsersOrganizationList = new ArrayList<>();
        detailedUsersOrganizationList.add(new DetailedUsersOrganization(UUID.randomUUID(), "Jane", "Doe",
                "jane.doe@example.org", "https://example.org/example", null, "Organization Role", true));
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenReturn(detailedUsersOrganizationList);

        // Act
        List<DetailedUsersOrganization> actualUsersForOrganization = this.organizationUserRoleServiceImpl
                .getUsersForOrganization("Organization Name");

        // Assert
        assertSame(detailedUsersOrganizationList, actualUsersForOrganization);
        assertEquals(1, actualUsersForOrganization.size());
        verify(this.userService).findUsersForOrg((List<UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#getUsersForOrganization(String)}
     */
    @Test
    void testGetUsersForOrganization6() {
        // Arrange
        when(this.userService.findUsersForOrg((List<UUID>) any())).thenReturn(new ArrayList<>());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());

        ArrayList<OrganizationUserRole> organizationUserRoleList = new ArrayList<>();
        organizationUserRoleList.add(organizationUserRole1);
        organizationUserRoleList.add(organizationUserRole);
        when(this.organizationUserRoleRepository.findByOrganization_OrganizationNameIgnoreCase((String) any()))
                .thenReturn(organizationUserRoleList);

        ArrayList<DetailedUsersOrganization> detailedUsersOrganizationList = new ArrayList<>();
        detailedUsersOrganizationList.add(new DetailedUsersOrganization(UUID.randomUUID(), "Jane", "Doe",
                "jane.doe@example.org", "https://example.org/example", null, "Organization Role", true));
        when(this.organizationMapper.entityToDetailedUsersOrganizationList((List<User>) any()))
                .thenReturn(detailedUsersOrganizationList);

        // Act
        List<DetailedUsersOrganization> actualUsersForOrganization = this.organizationUserRoleServiceImpl
                .getUsersForOrganization("Organization Name");

        // Assert
        assertSame(detailedUsersOrganizationList, actualUsersForOrganization);
        assertEquals(1, actualUsersForOrganization.size());
        verify(this.userService).findUsersForOrg((List<UUID>) any());
        verify(this.organizationUserRoleRepository).findByOrganization_OrganizationNameIgnoreCase((String) any());
        verify(this.organizationMapper).entityToDetailedUsersOrganizationList((List<User>) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#isUserOrganizationOwnerOrAdmin(String, UUID)}
     */
    @Test
    void testIsUserOrganizationOwnerOrAdmin() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);
        Optional<Organization> ofResult1 = Optional.of(organization1);
        when(this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean()))
                .thenReturn(ofResult1);

        // Act and Assert
        assertFalse(
                this.organizationUserRoleServiceImpl.isUserOrganizationOwnerOrAdmin("Organization Name", UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).findById((OrganizationUserRoleId) any());
        verify(this.organizationRepository).findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#isUserOrganizationOwnerOrAdmin(String, UUID)}
     */
    @Test
    void testIsUserOrganizationOwnerOrAdmin2() {
        // Arrange
        when(this.organizationUserRoleRepository
                .findById((com.ravel.backend.organization.model.OrganizationUserRoleId) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        Optional<Organization> ofResult = Optional.of(organization);
        when(this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean()))
                .thenReturn(ofResult);

        // Act and Assert
        assertThrows(AlreadyExistException.class, () -> this.organizationUserRoleServiceImpl
                .isUserOrganizationOwnerOrAdmin("Organization Name", UUID.randomUUID()));
        verify(this.organizationUserRoleRepository)
                .findById((com.ravel.backend.organization.model.OrganizationUserRoleId) any());
        verify(this.organizationRepository).findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#isUserOrganizationOwnerOrAdmin(String, UUID)}
     */
    @Test
    void testIsUserOrganizationOwnerOrAdmin3() {
        // Arrange
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(Optional.empty());

        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        OrganizationUserRole organizationUserRole = mock(OrganizationUserRole.class);
        when(organizationUserRole.getOrganizationRole()).thenReturn("Organization Role");
        doNothing().when(organizationUserRole).setActiveUser(anyBoolean());
        doNothing().when(organizationUserRole).setEmail((String) any());
        doNothing().when(organizationUserRole).setId((OrganizationUserRoleId) any());
        doNothing().when(organizationUserRole).setJoinedAt((OffsetDateTime) any());
        doNothing().when(organizationUserRole).setOrganization((Organization) any());
        doNothing().when(organizationUserRole).setOrganizationRole((String) any());
        doNothing().when(organizationUserRole).setUpdatedAt((OffsetDateTime) any());
        doNothing().when(organizationUserRole).setUserUUID((UUID) any());
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);
        Optional<Organization> ofResult = Optional.of(organization1);
        when(this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean()))
                .thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.organizationUserRoleServiceImpl
                .isUserOrganizationOwnerOrAdmin("Organization Name", UUID.randomUUID()));
        verify(this.organizationUserRoleRepository).findById((OrganizationUserRoleId) any());
        verify(organizationUserRole).setActiveUser(anyBoolean());
        verify(organizationUserRole).setEmail((String) any());
        verify(organizationUserRole).setId((OrganizationUserRoleId) any());
        verify(organizationUserRole).setJoinedAt((OffsetDateTime) any());
        verify(organizationUserRole).setOrganization((Organization) any());
        verify(organizationUserRole).setOrganizationRole((String) any());
        verify(organizationUserRole).setUpdatedAt((OffsetDateTime) any());
        verify(organizationUserRole).setUserUUID((UUID) any());
        verify(this.organizationRepository).findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#isUserOrganizationOwnerOrAdmin(String, UUID)}
     */
    @Test
    void testIsUserOrganizationOwnerOrAdmin4() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);
        OrganizationUserRole organizationUserRole = mock(OrganizationUserRole.class);
        when(organizationUserRole.getOrganizationRole()).thenReturn("Organization Role");
        doNothing().when(organizationUserRole).setActiveUser(anyBoolean());
        doNothing().when(organizationUserRole).setEmail((String) any());
        doNothing().when(organizationUserRole).setId((OrganizationUserRoleId) any());
        doNothing().when(organizationUserRole).setJoinedAt((OffsetDateTime) any());
        doNothing().when(organizationUserRole).setOrganization((Organization) any());
        doNothing().when(organizationUserRole).setOrganizationRole((String) any());
        doNothing().when(organizationUserRole).setUpdatedAt((OffsetDateTime) any());
        doNothing().when(organizationUserRole).setUserUUID((UUID) any());
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);
        when(this.organizationUserRoleRepository.findById((OrganizationUserRoleId) any())).thenReturn(ofResult);
        when(this.organizationRepository.findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean()))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.organizationUserRoleServiceImpl
                .isUserOrganizationOwnerOrAdmin("Organization Name", UUID.randomUUID()));
        verify(organizationUserRole).setActiveUser(anyBoolean());
        verify(organizationUserRole).setEmail((String) any());
        verify(organizationUserRole).setId((OrganizationUserRoleId) any());
        verify(organizationUserRole).setJoinedAt((OffsetDateTime) any());
        verify(organizationUserRole).setOrganization((Organization) any());
        verify(organizationUserRole).setOrganizationRole((String) any());
        verify(organizationUserRole).setUpdatedAt((OffsetDateTime) any());
        verify(organizationUserRole).setUserUUID((UUID) any());
        verify(this.organizationRepository).findByOrganizationNameIgnoreCaseAndIsActive((String) any(), anyBoolean());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setUserActive(OrganizationUserRoleId)}
     */
    @Test
    void testSetUserActive() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        Organization organization1 = new Organization();
        organization1.setActive(true);
        organization1.setCreatedAt(null);
        organization1.setOrganizationId(UUID.randomUUID());
        organization1.setOrganizationName("Organization Name");
        organization1.setOrganizationUserRoles(new HashSet<>());
        organization1.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole1 = new OrganizationUserRole();
        organizationUserRole1.setActiveUser(true);
        organizationUserRole1.setEmail("jane.doe@example.org");
        organizationUserRole1.setId(organizationUserRoleId1);
        organizationUserRole1.setJoinedAt(null);
        organizationUserRole1.setOrganization(organization1);
        organizationUserRole1.setOrganizationRole("Organization Role");
        organizationUserRole1.setUpdatedAt(null);
        organizationUserRole1.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole1);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any())).thenReturn(ofResult);

        OrganizationUserRoleId organizationUserRoleId2 = new OrganizationUserRoleId();
        organizationUserRoleId2.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId2.setUserUuid(UUID.randomUUID());

        // Act
        this.organizationUserRoleServiceImpl.setUserActive(organizationUserRoleId2);

        // Assert
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setUserActive(OrganizationUserRoleId)}
     */
    @Test
    void testSetUserActive2() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        Optional<OrganizationUserRole> ofResult = Optional.of(organizationUserRole);
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any()))
                .thenThrow(new AlreadyExistException("An error occurred"));
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any())).thenReturn(ofResult);

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        // Act and Assert
        assertThrows(AlreadyExistException.class,
                () -> this.organizationUserRoleServiceImpl.setUserActive(organizationUserRoleId1));
        verify(this.organizationUserRoleRepository).save((OrganizationUserRole) any());
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }

    /**
     * Method under test: {@link OrganizationUserRoleServiceImpl#setUserActive(OrganizationUserRoleId)}
     */
    @Test
    void testSetUserActive3() {
        // Arrange
        OrganizationUserRoleId organizationUserRoleId = new OrganizationUserRoleId();
        organizationUserRoleId.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId.setUserUuid(UUID.randomUUID());

        Organization organization = new Organization();
        organization.setActive(true);
        organization.setCreatedAt(null);
        organization.setOrganizationId(UUID.randomUUID());
        organization.setOrganizationName("Organization Name");
        organization.setOrganizationUserRoles(new HashSet<>());
        organization.setUpdatedAt(null);

        OrganizationUserRole organizationUserRole = new OrganizationUserRole();
        organizationUserRole.setActiveUser(true);
        organizationUserRole.setEmail("jane.doe@example.org");
        organizationUserRole.setId(organizationUserRoleId);
        organizationUserRole.setJoinedAt(null);
        organizationUserRole.setOrganization(organization);
        organizationUserRole.setOrganizationRole("Organization Role");
        organizationUserRole.setUpdatedAt(null);
        organizationUserRole.setUserUUID(UUID.randomUUID());
        when(this.organizationUserRoleRepository.save((OrganizationUserRole) any())).thenReturn(organizationUserRole);
        when(this.organizationUserRoleRepository.findByIdOptional((OrganizationUserRoleId) any()))
                .thenReturn(Optional.empty());

        OrganizationUserRoleId organizationUserRoleId1 = new OrganizationUserRoleId();
        organizationUserRoleId1.setOrganizationId(UUID.randomUUID());
        organizationUserRoleId1.setUserUuid(UUID.randomUUID());

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> this.organizationUserRoleServiceImpl.setUserActive(organizationUserRoleId1));
        verify(this.organizationUserRoleRepository).findByIdOptional((OrganizationUserRoleId) any());
    }
}

