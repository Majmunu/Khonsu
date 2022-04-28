package com.halo.khonsu.controller.dto;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String username;
    private String password;
    private String newPassword;
}
