package investment.portfolio.investment.portfolio.model.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public enum Role {


    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.MANAGER_READ,
                    Permission.MANAGER_UPDATE,
                    Permission.MANAGER_DELETE,
                    Permission.MANAGER_CREATE,
                    Permission.USER_READ,
                    Permission.USER_DELETE,
                    Permission.USER_UPDATE,
                    Permission.USER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    Permission.MANAGER_READ,
                    Permission.MANAGER_UPDATE,
                    Permission.MANAGER_DELETE,
                    Permission.MANAGER_CREATE,
                    Permission.USER_READ,
                    Permission.USER_DELETE,
                    Permission.USER_UPDATE,
                    Permission.USER_CREATE
            )
    ),
    USER_PREMIUM(Set.of(
                    Permission.USER_PREMIUM_READ,
                    Permission.USER_PREMIUM_CREATE,
                    Permission.USER_PREMIUM_DELETE,
                    Permission.USER_PREMIUM_UPDATE
    )
    ),
    USER(
            Set.of(
                    Permission.USER_READ,
                    Permission.USER_DELETE,
                    Permission.USER_UPDATE,
                    Permission.USER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}