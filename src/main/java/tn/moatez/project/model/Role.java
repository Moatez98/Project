package tn.moatez.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import tn.moatez.project.dto.RoleDTO;
import tn.moatez.project.enums.ERole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
    }
    public static Role mapToEntity(RoleDTO dto){
        if (dto != null) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(dto, Role.class);
        }
        return null;
    }
}
