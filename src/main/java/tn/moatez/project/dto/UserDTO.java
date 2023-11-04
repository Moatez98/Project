package tn.moatez.project.dto;



import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import tn.moatez.project.model.User;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Date birthday;
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
