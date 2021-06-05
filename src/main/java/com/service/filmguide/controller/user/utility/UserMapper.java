package com.service.filmguide.controller.user.utility;

import com.service.filmguide.controller.user.response.UserProfileDTO;
import com.service.filmguide.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    /*@InheritInverseConfiguration
    public User mapUserAndPersonalInformationDAO(@MappingTarget User user, PersonalInformationDAO personalInformation);*/

    @InheritInverseConfiguration
    public UserProfileDTO userToUserProfileDTO(User user);

   /* @Mappings(
        {
            @Mapping(source = "address.country", target = "contact.address.country"),
            @Mapping(source = "address.city", target = "contact.address.city"),
            @Mapping(source = "address.district", target = "contact.address.district"),
            @Mapping(source = "address.address", target = "contact.address.address"),
            @Mapping(source = "address.postCode", target = "contact.address.postCode"),
            @Mapping(source = "fax", target = "contact.fax"),
            @Mapping(source = "phone", target = "contact.phone"), 
            @Mapping(source = "website", target = "contact.website")
        }
    )
    public User mapUserAndContactInformationDAO(@MappingTarget User user, ContactInformationDAO contactInformation);*/
}