package org.example.shedu.security;

public class SecurityConstants {
    public static final String[] WHITE_LIST = {
            "/auth/**",
            "file/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/swagger-resources/**",
            "/webjars/**",
            "/ws/**",
    };
}
