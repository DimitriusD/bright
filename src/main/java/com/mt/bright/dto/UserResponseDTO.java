package com.mt.bright.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UserResponseDTO {

    private long id;

    private String username;

    private String surname;

    private Integer age;

    private String phone;

    private String password;

}
