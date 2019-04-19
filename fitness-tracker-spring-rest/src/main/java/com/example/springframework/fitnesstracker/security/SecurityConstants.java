package com.example.springframework.fitnesstracker.security;

public class SecurityConstants {

    static final String SIGN_UP_URL = "/api/users/register";
    static final String LOGIN_URL = "/api/users/login";
    static final String H2_URL = "/h2-console/**/**";
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 3000_000; // Few minutes
}
