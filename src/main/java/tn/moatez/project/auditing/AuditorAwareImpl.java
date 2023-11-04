package tn.moatez.project.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String NOMEN_NESCIO = "N.N.";



    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        if(!Objects.equals(name, "anonymousUser")){
            return Optional.of("u.getEmail()");
        }else {
            return Optional.of(NOMEN_NESCIO);
        }


    }

}
