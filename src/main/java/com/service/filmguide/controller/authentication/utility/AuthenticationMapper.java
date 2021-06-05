package com.service.filmguide.controller.authentication.utility;

import com.service.filmguide.controller.authentication.request.RegisterDAO;
import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthenticationMapper {
    
    @InheritInverseConfiguration
    public UserProfileDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    public User registerDAOToUser(RegisterDAO registerDAO);

}