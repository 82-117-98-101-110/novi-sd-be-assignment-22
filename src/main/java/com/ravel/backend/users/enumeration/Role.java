package com.ravel.backend.users.enumeration;

import static com.ravel.backend.users.constant.AuthorityConstant.*;

public enum Role {
	ROLE_USER(USER_AUTHORITIES),
	ROLE_USER_ADMIN(USER_ADMIN_AUTHORITIES),
	ROLE_DEV(DEV_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES),
	ROLE_ADMIN_USER(SYSTEM_USER_AUTHORITIES);

	private String[] authorities;

	Role(String... authorities) {
		this.authorities = authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}
}
