package com.plaza.usuarios.infrastructure.mapper;

import com.plaza.usuarios.domain.model.User;
import com.plaza.usuarios.infrastructure.dto.UserDto;

public class UserMapper {

        public static UserDto toDto(User user) {
            if (user == null) {
                return null;
            }
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setPassword(user.getPassword());
            userDto.setDocumentNumber(user.getDocumentNumber());
            userDto.setBirthDate(user.getBirthDate());
            userDto.setCellPhone(user.getCellPhone());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole().getName());
            return userDto;
        }
}
