package br.com.bitzen.desafio.domain.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestError {

    private int httpStatus;
    private String httpValue;
    private String exception;
    private String message;
    private String urlPath;
    private Date timestamp;

}
