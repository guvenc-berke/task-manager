package com.appcent.taskmanager.dto.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value ="Jwt response DTO", description ="Response DTO containing JWT token for auth")
public class JwtResponse {

    @ApiModelProperty(value = "jwt token")
    private String token;

    @ApiModelProperty(value = "user id")
    private Long id;

    @ApiModelProperty(value = "user email")
    private String email;

    @ApiModelProperty(value = "encrypted user password")
    private String password;

    public JwtResponse(String token, Long id, String email, String password) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
