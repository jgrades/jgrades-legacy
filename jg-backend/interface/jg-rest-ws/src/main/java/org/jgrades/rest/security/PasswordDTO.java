package org.jgrades.rest.security;

import lombok.Data;

@Data
public class PasswordDTO {
    private String password;
    private Long userId;
}
