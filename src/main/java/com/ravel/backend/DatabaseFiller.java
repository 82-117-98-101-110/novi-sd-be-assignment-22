package com.ravel.backend;

import com.ravel.backend.appAuth.dto.PermissionPostDto;
import com.ravel.backend.appAuth.dto.RolePostDto;
import com.ravel.backend.appAuth.service.AppPermissionService;
import com.ravel.backend.appAuth.service.AppRoleService;
import com.ravel.backend.organization.dtos.OrganizationPostDto;
import com.ravel.backend.organization.service.OrganizationService;
import com.ravel.backend.organization.service.OrganizationUserRoleServiceImpl;
import com.ravel.backend.spacePro.dto.SpaceProPostDto;
import com.ravel.backend.spacePro.dto.SpaceProRolePostDto;
import com.ravel.backend.spacePro.enumeration.SpaceType;
import com.ravel.backend.spacePro.model.EnvironmentPro;
import com.ravel.backend.spacePro.model.SpacePro;
import com.ravel.backend.spacePro.repository.EnvironmentProRepository;
import com.ravel.backend.spacePro.repository.SpaceRoleRepository;
import com.ravel.backend.spacePro.service.EnvironmentProServiceImpl;
import com.ravel.backend.spacePro.service.SpaceProServiceImpl;
import com.ravel.backend.spacePro.service.SpaceRoleService;
import com.ravel.backend.users.dtos.CreateUserPostDto;
import com.ravel.backend.users.service.ConnectionService;
import com.ravel.backend.users.service.UserService;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("h2")
public class DatabaseFiller implements CommandLineRunner {

	private final AppRoleService appRoleService;
	private final AppPermissionService appPermissionService;
	private final OrganizationService organizationService;
	private final UserService userService;
	private final OrganizationUserRoleServiceImpl organizationUserRoleService;
	private final ConnectionService connectionService;
	private final EnvironmentProServiceImpl environmentProService;
	private final EnvironmentProRepository environmentProRepository;
	private final SpaceRoleRepository spaceRoleRepository;
	private final SpaceRoleService spaceRoleService;
	private final SpaceProServiceImpl spaceProService;

	@Autowired
	public DatabaseFiller(
			AppRoleService appRoleService,
			AppPermissionService appPermissionService,
			OrganizationService organizationService,
			UserService userService,
			OrganizationUserRoleServiceImpl organizationUserRoleService,
			ConnectionService connectionService,
			EnvironmentProServiceImpl environmentProService, EnvironmentProRepository environmentProRepository, SpaceRoleRepository spaceRoleRepository, SpaceRoleService spaceRoleService, SpaceProServiceImpl spaceProService) {
		this.appRoleService = appRoleService;
		this.appPermissionService = appPermissionService;
		this.organizationService = organizationService;
		this.userService = userService;
		this.organizationUserRoleService = organizationUserRoleService;
		this.connectionService = connectionService;
		this.environmentProService = environmentProService;
		this.environmentProRepository = environmentProRepository;
		this.spaceRoleRepository = spaceRoleRepository;
		this.spaceRoleService = spaceRoleService;
		this.spaceProService = spaceProService;
	}

	@Override
	public void run(String... args) throws Exception {
		RolePostDto ORGANIZATION_ADMIN = new RolePostDto();
		ORGANIZATION_ADMIN.setAppRoleName("ORGANIZATION_ADMIN");
		ORGANIZATION_ADMIN.setDescription(
			"Normal SystemUser that is the admin of a organization"
		);
		ORGANIZATION_ADMIN.setPurpose("ORGANIZATION");
		appRoleService.createNewAppRole(ORGANIZATION_ADMIN);

		RolePostDto ORGANIZATION_OWNER = new RolePostDto();
		ORGANIZATION_OWNER.setAppRoleName("ORGANIZATION_OWNER");
		ORGANIZATION_OWNER.setDescription(
			"Normal SystemUser that is the owner of a organization"
		);
		ORGANIZATION_OWNER.setPurpose("ORGANIZATION");
		appRoleService.createNewAppRole(ORGANIZATION_OWNER);

		RolePostDto ORGANIZATION_USER = new RolePostDto();
		ORGANIZATION_USER.setAppRoleName("ORGANIZATION_USER");
		ORGANIZATION_USER.setDescription(
			"Normal SystemUser that is a user within a organization"
		);
		ORGANIZATION_USER.setPurpose("ORGANIZATION");
		appRoleService.createNewAppRole(ORGANIZATION_USER);

		RolePostDto ORGANIZATION_GUEST = new RolePostDto();
		ORGANIZATION_GUEST.setAppRoleName("ORGANIZATION_GUEST");
		ORGANIZATION_GUEST.setDescription(
			"Normal SystemUser that is a guest within a organization"
		);
		ORGANIZATION_GUEST.setPurpose("ORGANIZATION");
		appRoleService.createNewAppRole(ORGANIZATION_GUEST);

		RolePostDto ORGANIZATION_DEV = new RolePostDto();
		ORGANIZATION_DEV.setAppRoleName("ORGANIZATION_DEV");
		ORGANIZATION_DEV.setDescription(
			"Normal SystemUser that is a DEV within a organization"
		);
		ORGANIZATION_DEV.setPurpose("ORGANIZATION");
		appRoleService.createNewAppRole(ORGANIZATION_DEV);

		RolePostDto DEVELOPER = new RolePostDto();
		DEVELOPER.setAppRoleName("DEVELOPER");
		DEVELOPER.setDescription("Normal SystemUser that is a DEVELOPER");
		DEVELOPER.setPurpose("SPACE");
		appRoleService.createNewAppRole(DEVELOPER);

		RolePostDto USER = new RolePostDto();
		USER.setAppRoleName("USER");
		USER.setDescription("Normal SystemUser that is a USER");
		USER.setPurpose("SPACE");
		appRoleService.createNewAppRole(USER);

		RolePostDto OWNER = new RolePostDto();
		OWNER.setAppRoleName("SPACE_OWNER");
		OWNER.setDescription("Normal SystemUser that is a OWNER");
		OWNER.setPurpose("SPACE");
		appRoleService.createNewAppRole(OWNER);

		RolePostDto PRESENTER = new RolePostDto();
		PRESENTER.setAppRoleName("PRESENTER");
		PRESENTER.setDescription("Normal SystemUser that is a Presentor");
		PRESENTER.setPurpose("SPACE");
		appRoleService.createNewAppRole(PRESENTER);

		RolePostDto MODERATOR = new RolePostDto();
		MODERATOR.setAppRoleName("MODERATOR");
		MODERATOR.setDescription("Normal SystemUser that is a Moderator");
		MODERATOR.setPurpose("SPACE");
		appRoleService.createNewAppRole(MODERATOR);

		RolePostDto GUEST = new RolePostDto();
		GUEST.setAppRoleName("GUEST");
		GUEST.setDescription("Normal SystemUser that is a GUEST");
		GUEST.setPurpose("SPACE");
		appRoleService.createNewAppRole(GUEST);

		RolePostDto VISITOR = new RolePostDto();
		VISITOR.setAppRoleName("VISITOR");
		VISITOR.setDescription("Normal SystemUser that is a VISITOR");
		VISITOR.setPurpose("SPACE");
		appRoleService.createNewAppRole(VISITOR);

		PermissionPostDto present = new PermissionPostDto();
		present.setAppPermissionName("Present");
		present.setDescription("Enables the user to present in a room");
		appPermissionService.createNewAppPermission(present);
		appPermissionService.addRoleToPermission(
			present.getAppPermissionName(),
			PRESENTER.getAppRoleName()
		);
		appPermissionService.addRoleToPermission(
			present.getAppPermissionName(),
			PRESENTER.getAppRoleName()
		);

		PermissionPostDto assignUsertoOrganization = new PermissionPostDto();
		assignUsertoOrganization.setAppPermissionName("assignUsertoOrganization");
		assignUsertoOrganization.setDescription(
			"Enables the user assignUsertoOrganization"
		);
		appPermissionService.createNewAppPermission(assignUsertoOrganization);
		appPermissionService.addRoleToPermission(
			assignUsertoOrganization.getAppPermissionName(),
			ORGANIZATION_OWNER.getAppRoleName()
		);
		appPermissionService.addRoleToPermission(
			assignUsertoOrganization.getAppPermissionName(),
			ORGANIZATION_ADMIN.getAppRoleName()
		);

		PermissionPostDto removeUsersfromOrganization = new PermissionPostDto();
		removeUsersfromOrganization.setAppPermissionName("removeUsersfromOrganization");
		removeUsersfromOrganization.setDescription(
			"Enables the user removeUsersfromOrganization"
		);
		appPermissionService.createNewAppPermission(removeUsersfromOrganization);
		appPermissionService.addRoleToPermission(
			removeUsersfromOrganization.getAppPermissionName(),
			ORGANIZATION_OWNER.getAppRoleName()
		);
		appPermissionService.addRoleToPermission(
			removeUsersfromOrganization.getAppPermissionName(),
			ORGANIZATION_ADMIN.getAppRoleName()
		);

		PermissionPostDto assignRoletoUserOrganization = new PermissionPostDto();
		assignRoletoUserOrganization.setAppPermissionName("assignRoletoUserOrganization");
		assignRoletoUserOrganization.setDescription(
			"Enables the user assignRoletoUserOrganization"
		);
		appPermissionService.createNewAppPermission(assignRoletoUserOrganization);
		appPermissionService.addRoleToPermission(
			assignRoletoUserOrganization.getAppPermissionName(),
			ORGANIZATION_OWNER.getAppRoleName()
		);
		appPermissionService.addRoleToPermission(
			assignRoletoUserOrganization.getAppPermissionName(),
			ORGANIZATION_ADMIN.getAppRoleName()
		);

		PermissionPostDto muteAll = new PermissionPostDto();
		muteAll.setAppPermissionName("muteAll");
		muteAll.setDescription("Enables the user mute all in a room");
		appPermissionService.createNewAppPermission(muteAll);
		appPermissionService.addRoleToPermission(
			muteAll.getAppPermissionName(),
			PRESENTER.getAppRoleName()
		);

		PermissionPostDto unMuteAll = new PermissionPostDto();
		unMuteAll.setAppPermissionName("unMuteAll");
		unMuteAll.setDescription("Enables the user unMuteAll all in a room");
		appPermissionService.createNewAppPermission(unMuteAll);
		appPermissionService.addRoleToPermission(
			unMuteAll.getAppPermissionName(),
			PRESENTER.getAppRoleName()
		);

		PermissionPostDto devPermission = new PermissionPostDto();
		devPermission.setAppPermissionName("Dev Permission");
		devPermission.setDescription("Enables the user devPermission");
		appPermissionService.createNewAppPermission(devPermission);
		appPermissionService.addRoleToPermission(
			devPermission.getAppPermissionName(),
			DEVELOPER.getAppRoleName()
		);

		PermissionPostDto spawnTestPlayers = new PermissionPostDto();
		spawnTestPlayers.setAppPermissionName("SpawnTestPlayers");
		spawnTestPlayers.setDescription("Enables the user SpawnTestPlayers");
		appPermissionService.createNewAppPermission(spawnTestPlayers);
		appPermissionService.addRoleToPermission(
			spawnTestPlayers.getAppPermissionName(),
			DEVELOPER.getAppRoleName()
		);

		appPermissionService.addRoleToPermission("Present", "ORGANIZATION_ADMIN");
		appPermissionService.addRoleToPermission(
			"SpawnTestPlayers",
			"ORGANIZATION_ADMIN"
		);
		appPermissionService.addRoleToPermission("Dev Permission", "ORGANIZATION_ADMIN");

		appPermissionService.addRoleToPermission("Present", "ORGANIZATION_DEV");
		appPermissionService.addRoleToPermission("SpawnTestPlayers", "ORGANIZATION_DEV");
		appPermissionService.addRoleToPermission("Dev Permission", "ORGANIZATION_DEV");

		OrganizationPostDto testOrganization = new OrganizationPostDto();
		testOrganization.setOrganizationName("testOrganization");
		organizationService.createOrganization(testOrganization);

		OrganizationPostDto demo = new OrganizationPostDto();
		demo.setOrganizationName("Demo");
		organizationService.createOrganization(demo);

		OrganizationPostDto ahk = new OrganizationPostDto();
		ahk.setOrganizationName("AHK");
		organizationService.createOrganization(ahk);

		OrganizationPostDto wateschap = new OrganizationPostDto();
		wateschap.setOrganizationName("Waterschappen");
		organizationService.createOrganization(wateschap);

		OrganizationPostDto marineterrein = new OrganizationPostDto();
		marineterrein.setOrganizationName("Marineterrein");
		organizationService.createOrganization(marineterrein);

		OrganizationPostDto ravel = new OrganizationPostDto();
		ravel.setOrganizationName("Ravel");
		organizationService.createOrganization(ravel);

		CreateUserPostDto newUser = new CreateUserPostDto();
		newUser.setEmail("developer@ravel.world");
		newUser.setFirstName("Developer");
		newUser.setLastName("Ravel");
		newUser.setPassword("secret");
		newUser.setRole("ROLE_ADMIN");
		userService.addUser(newUser);

		CreateUserPostDto newUser1 = new CreateUserPostDto();
		newUser1.setEmail("FirstName001@ravel.world");
		newUser1.setFirstName("FirstName001");
		newUser1.setLastName("ROLE_ADMIN");
		newUser1.setPassword("secret");
		newUser1.setRole("ROLE_USER");
		userService.addUser(newUser1);

		CreateUserPostDto newUser2 = new CreateUserPostDto();
		newUser2.setEmail("FirstName002@ravel.world");
		newUser2.setFirstName("FirstName002");
		newUser2.setLastName("ROLE_DEV");
		newUser2.setPassword("secret");
		newUser2.setRole("ROLE_DEV");
		userService.addUser(newUser2);

		CreateUserPostDto newUser3 = new CreateUserPostDto();
		newUser3.setEmail("ROLE_SUPER_ADMIN@email.world");
		newUser3.setFirstName("SUPER_ADMIN");
		newUser3.setLastName("admin");
		newUser3.setPassword("secret");
		newUser3.setRole("ROLE_SUPER_ADMIN");
		userService.addUser(newUser3);

		CreateUserPostDto newUser4 = new CreateUserPostDto();
		newUser4.setEmail("ROLE_USER@email.world");
		newUser4.setFirstName("ROLE_USER");
		newUser4.setLastName("user");
		newUser4.setPassword("secret");
		newUser4.setRole("ROLE_USER");
		userService.addUser(newUser4);

		CreateUserPostDto newUser5 = new CreateUserPostDto();
		newUser5.setEmail("rubenlangeweg@gmail.com");
		newUser5.setFirstName("Ruben");
		newUser5.setLastName("Langeweg");
		newUser5.setPassword("Langeweg");
		newUser5.setRole("ROLE_SUPER_ADMIN");
		userService.addUser(newUser5);

		CreateUserPostDto newUser50 = new CreateUserPostDto();
		newUser50.setEmail("ruben@thenewbase.co");
		newUser50.setFirstName("Ruben");
		newUser50.setLastName("Langeweg");
		newUser50.setPassword("Langeweg");
		newUser50.setRole("ROLE_SUPER_ADMIN");
		userService.addUser(newUser50);

		String organizationName0 = "testOrganization";
		String userEmail0 = "developer@ravel.world";
		String organizationRole0 = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName0,
			userEmail0,
			organizationRole0
		);

		String organizationName = "Ravel";
		String userEmail = "developer@ravel.world";
		String organizationRole = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName,
			userEmail,
			organizationRole
		);

		String userEmailRuben = "rubenlangeweg@gmail.com";
		String organizationRoleRuben = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName,
			userEmailRuben,
			organizationRoleRuben
		);

		String organizationName1 = "Demo";
		String userEmail1 = "developer@ravel.world";
		String organizationRole1 = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName1,
			userEmail1,
			organizationRole1
		);

		String organizationName2 = "AHK";
		String userEmail2 = "developer@ravel.world";
		String organizationRole2 = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName2,
			userEmail2,
			organizationRole2
		);

		String organizationName3 = "Waterschappen";
		String userEmail3 = "developer@ravel.world";
		String organizationRole3 = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName3,
			userEmail3,
			organizationRole3
		);

		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName3,
			userEmailRuben,
			organizationRole3
		);

		String organizationName4 = "Marineterrein";
		String userEmail4 = "developer@ravel.world";
		String organizationRole4 = ORGANIZATION_ADMIN.getAppRoleName();
		organizationUserRoleService.addUserEmailToOrgWithoutInviteEmail(
			organizationName4,
			userEmail4,
			organizationRole4
		);

		SpaceProRolePostDto guestSpace = new SpaceProRolePostDto();
		guestSpace.setAppRoleName("GUEST");
		spaceRoleService.addAppRoleToSpaceRoles(guestSpace);

		SpaceProRolePostDto userSpace = new SpaceProRolePostDto();
		userSpace.setAppRoleName("USER");
		spaceRoleService.addAppRoleToSpaceRoles(userSpace);

		SpaceProRolePostDto presenterSpace = new SpaceProRolePostDto();
		presenterSpace.setAppRoleName("PRESENTER");
		spaceRoleService.addAppRoleToSpaceRoles(presenterSpace);


		SpaceProRolePostDto moderatorSpace = new SpaceProRolePostDto();
		moderatorSpace.setAppRoleName("MODERATOR");
		spaceRoleService.addAppRoleToSpaceRoles(moderatorSpace);

		SpaceProRolePostDto ownerSpace = new SpaceProRolePostDto();
		ownerSpace.setAppRoleName("SPACE_OWNER");
		spaceRoleService.addAppRoleToSpaceRoles(ownerSpace);

		EnvironmentPro newEnvironmentPro = EnvironmentPro
				.builder()
				.created_at(OffsetDateTime.now())
				.isActive(true)
				.isPublicEnvironment(true)
				.name("Felix Meritis")
				.imageUrl("https://ravel-environments.s3.eu-central-1.amazonaws.com/live/Felix Meritis/FelixMeritis_Preview.png")
				.description("1")
				.unitySceneName("FelixMeritis")
				.isActive(true)
				.environmentUuid(UUID.randomUUID())
				.build();
		environmentProRepository.save(newEnvironmentPro);

		EnvironmentPro newEnvironmentPro2 = EnvironmentPro
				.builder()
				.created_at(OffsetDateTime.now())
				.isActive(true)
				.isPublicEnvironment(true)
				.name("Scheepvaart")
				.imageUrl("https://cdn.ravel.world/environments/live/vgbpjP/GPZwWB/image/1920/scheepvaart.png")
				.description("2")
				.unitySceneName("Scheepvaartmuseum")
				.isActive(true)
				.environmentUuid(UUID.randomUUID())
				.build();
		environmentProRepository.save(newEnvironmentPro2);

	}
}
