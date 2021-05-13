package com.appcent.taskmanager.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value ="Authorization request DTO", description ="Authentication object for register and login requests")
public class AuthorizationRequestDto {
    @ApiModelProperty(value="User email", required = true)
    private String email;

    @ApiModelProperty(value="User password", required = true)
    private String password;
}
