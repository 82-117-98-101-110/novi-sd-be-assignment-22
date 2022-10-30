package com.ravel.backend.users.constant;

public class AuthorityConstant {

	public static final String[] USER_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
	};
	public static final String[] USER_ADMIN_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
		"user_admin:access",
	};
	public static final String[] DEV_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
		"user_admin:access",
		"dev:access",
	};
	public static final String[] ADMIN_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
		"user_admin:access",
		"dev:access",
		"admin:access",
	};
	public static final String[] SUPER_ADMIN_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
		"user_admin:access",
		"dev:access",
		"admin:access",
		"super_admin:access",
	};
	public static final String[] SYSTEM_USER_AUTHORITIES = {
		"user:read",
		"user:create",
		"user:update",
		"user:delete",
		"user_admin:access",
		"dev:access",
		"admin:access",
		"super_admin:access",
		"system_user:access",
	};
}
