package com.danil.labtemp.model;

public record User(
        String username,
        String password,
        String role,
        boolean requirePasswordChange
) {
}
