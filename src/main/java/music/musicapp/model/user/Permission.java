package music.musicapp.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    USER_PREMIUM_READ("user_premium:read"),
    USER_PREMIUM_UPDATE("user_premium:update"),
    USER_PREMIUM_CREATE("user_premium:create"),
    USER_PREMIUM_DELETE("user_premium:delete")
    ;

    @Getter
    private final String permission;
}