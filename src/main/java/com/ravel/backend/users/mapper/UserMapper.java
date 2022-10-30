package com.ravel.backend.users.mapper;

import com.ravel.backend.users.dtos.GetSelfDto;
import com.ravel.backend.users.dtos.UserDetailsGetAdminDto;
import com.ravel.backend.users.dtos.UserDetailsGetDto;
import com.ravel.backend.users.model.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	List<UserDetailsGetDto> userToUserDetailsGetDtoList(List<User> userList);
	UserDetailsGetDto userToUserDetailsGetDto(User user);
	GetSelfDto userToGetSelfDto(User user);
	List<UserDetailsGetAdminDto> userToUserUserDetailsGetAdminDtoList(
		List<User> userList
	);
}
