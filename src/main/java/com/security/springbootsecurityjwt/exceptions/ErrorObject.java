package com.security.springbootsecurityjwt.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;

}
