package com.apress.dayle.exception;

import com.apress.dayle.dto.UsersDTO;

public class CustomErrorType extends UsersDTO {
    private String errorMessage;
    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
