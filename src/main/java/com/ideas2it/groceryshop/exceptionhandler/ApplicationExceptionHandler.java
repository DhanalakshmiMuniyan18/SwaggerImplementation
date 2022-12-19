/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ideas2it.groceryshop.dto.ErrorResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

import javax.validation.UnexpectedTypeException;

/**
 * <p>
 *     It handles exception occurs in this application.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since  05-11-22
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * <p>
     *     Implemented to handle an exception when the user is not
     *     getting the desired output.
     * </p>
     *
     * @param notFoundException - Contains message and status code
     * @return                  - ErrorDto
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponseDto handleNotFoundException(NotFoundException notFoundException) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setErrorMessage(notFoundException.getMessage());
        error.setStatusCode(404);
        return error;
    }

    /**
     * <p>
     *     Implemented to handle an exception when the user request
     *     has conflict.
     * </p>
     * @param exception - contains message
     * @return          - ErrorDto
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ExistedException.class,
                       ParseException.class,
                       SQLIntegrityConstraintViolationException.class})
    public ErrorResponseDto handleAlReadyExistsException(Exception exception) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setErrorMessage(exception.getMessage());
        error.setStatusCode(409);
        return error;
    }

    /**
     * <p>
     *     It is implemented to handle BadCredentialsException or
     *     UsernameNotFoundException or HttpClientErrorException when thrown.
     * </p>
     *
     * @param exception - Contains error message.
     * @return errorDto - Contains error message and error status code.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class,
            UsernameNotFoundException.class,
            HttpClientErrorException.Unauthorized.class})
    public ErrorResponseDto handlerBadCredentials(Exception exception) {
        ErrorResponseDto errorDto = new ErrorResponseDto();
        errorDto.setErrorMessage(exception.getMessage());
        errorDto.setStatusCode(401);
        return errorDto;
    }

    /**
     * <p>
     *     It is implemented to handle an MethodArgumentNotValidException thrown
     *     when given payload is not valid
     * </p>
     * @param exception MethodArgumentNotValidException exception with Message
     * @return errors with field name and error message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            String fieldName =   fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

    /**
     * <p>
     *     It is implemented to handle IllegalArgumentException when thrown.
     * </p>
     *
     * @param httpMessageNotReadableException - Contains error message.
     * @return                                - Contains error message and error status code.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDto handlerMessageNotReadableException
                           (HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorResponseDto errorDto = new ErrorResponseDto();
        errorDto.setErrorMessage(httpMessageNotReadableException.getMessage());
        errorDto.setStatusCode(400);
        return errorDto;
    }

    /**
     * <p>
     *     It is implemented to handle HttpClientErrorException when thrown.
     * </p>
     *
     * @param httpClientErrorException - Contains error message.
     * @return                         - Contains error message and error code.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ErrorResponseDto handlerForbiddenException
                                         (HttpClientErrorException httpClientErrorException) {
        ErrorResponseDto errorDto = new ErrorResponseDto();
        errorDto.setErrorMessage(httpClientErrorException.getMessage());
        errorDto.setStatusCode(403);
        return errorDto;
    }

    /**
     * <p>
     *     It is implemented to handle IllegalArgumentException when thrown.
     * </p>
     *
     * @param illegalArgumentException - Contains error message.
     * @return errorDto                - Contains error message and error code.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto handleIllegalArgumentException
                                      (IllegalArgumentException illegalArgumentException) {
        ErrorResponseDto errorDto = new ErrorResponseDto();
        errorDto.setErrorMessage(illegalArgumentException.getMessage());
        errorDto.setStatusCode(401);
        return errorDto;
    }

    /**
     * <p>
     *     It is implemented to handle UnexpectedTypeException when thrown.
     * </p>
     *
     * @param unexpectedTypeException - Contains error message.
     * @return errorDto               - Contains error message and error code.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnexpectedTypeException.class)
    public ErrorResponseDto handleUnexpectedTypeException
                                         (UnexpectedTypeException unexpectedTypeException) {
        ErrorResponseDto errorDto = new ErrorResponseDto();
        errorDto.setErrorMessage(unexpectedTypeException.getMessage());
        errorDto.setStatusCode(400);
        return errorDto;
    }
}