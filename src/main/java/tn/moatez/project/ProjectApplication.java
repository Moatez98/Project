package tn.moatez.project;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.moatez.project.enums.ERole;
import tn.moatez.project.model.Role;
import tn.moatez.project.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjectApplication {

    final RoleRepository roleRepository;

    public ProjectApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
    @PostConstruct
    public void enregistrer() {
        ERole[] role={ERole.ROLE_USER, ERole.ROLE_ADMIN, ERole.ROLE_MODERATOR};
        List<Role> roleSet = roleRepository.findAll();
        if(roleSet.isEmpty()){
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
    }

}
