package tn.moatez.project.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import tn.moatez.project.enums.ERole;
import tn.moatez.project.model.Role;

@Data
public class RoleDTO {
    private Long id;
    private ERole name;
    public static RoleDTO mapToDto(Role role){
        if(role!=null){
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(role, RoleDTO.class);
        }
        return null;
    }
}
