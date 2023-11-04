package tn.moatez.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import tn.moatez.project.dto.UserDTO;

import java.io.Serial;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractBaseEntity implements Cloneable{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String firstname;
    @NotNull
    @Column(unique = true)
    private String lastname;
    @NotNull
    @Column(unique = true)
    private String username;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true )
    private String email;
    @NotNull
    @Size(min = 6, max = 100)
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String phoneNumber;
    private String genre;
    private Boolean verified;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    private Address address;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    public static User mapToEntity(UserDTO dto){
        if(dto!=null) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(dto, User.class);
        }
        return null;
    }
    public User clone()throws CloneNotSupportedException{
        return (User) super.clone();
    }
}
