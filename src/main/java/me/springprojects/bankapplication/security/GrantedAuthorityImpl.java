package me.springprojects.bankapplication.security;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.Authority;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
