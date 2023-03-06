package me.springprojects.bankapplication.auditing;

import me.springprojects.bankapplication.security.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String NO_AUTH_CREATOR = "No Auth Creator";

    @Override
    public Optional<String> getCurrentAuditor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            return Optional.of(userDetails.getUser().getId());
        }
        return Optional.of(NO_AUTH_CREATOR);
    }
}
