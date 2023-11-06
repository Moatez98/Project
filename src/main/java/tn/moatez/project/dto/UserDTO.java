package tn.moatez.project.dto;



import jakarta.validation.constraints.*;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.format.annotation.DateTimeFormat;
import tn.moatez.project.model.User;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "can not be null")
    private String firstname;
    @NotBlank(message = "can not be null")
    private String lastname;
    private String username;
    @Email(message = "Email is not valid", regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Size(min = 6,
            message = "Password Must Be more than 6 word")
    // @Min(value = 6,message = "Password Must Be more than 6 word")
    @NotEmpty
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Min(value = 6,message = "Phone Number Must Be more than 6 number")
    @NotEmpty(message = "Phone cannot be empty")
    private String phoneNumber;
    private String genre;
    private Boolean verified;
    private AddressDTO address;

    private Set<RoleDTO> roles;

    public static UserDTO mapToDTO(User entity){
        if(entity!=null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
                @Override
                protected void configure() {
                    skip(destination.getPassword());
                }
            });
            return mapAllObjectsToDTO(entity, modelMapper.map(entity, UserDTO.class));
        }
        return null;
    }
    private static UserDTO mapAllObjectsToDTO(User entity, UserDTO dto) {
        if(entity.getRoles()!=null){
            dto.setAddress(AddressDTO.mapToDTO(entity.getAddress()));
            dto.setRoles(entity.getRoles().stream().map(RoleDTO::mapToDto).collect(Collectors.toSet()));
        }
        return dto;
    }
    @Override
    public UserDTO clone() throws CloneNotSupportedException {
        return (UserDTO) super.clone();
    }
}
