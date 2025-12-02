package com.kiwadev.mocktest.exception;

import com.kiwadev.mocktest.helper.ResponseHandler;
import io.jsonwebtoken.ClaimJwtException;
import org.hibernate.exception.ConstraintViolationException;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnprocessEntityException.class)
    public ResponseEntity<Object> handleUnprocessEntity(final UnprocessEntityException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(
                ex.getErrorCode(),
                "Data Not Available",
                ex.getMessage(),
                HttpStatus.UNPROCESSABLE_CONTENT
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(final ConflictException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(
                ex.getErrorCode(),
                "Data Not Available",
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());
        return ResponseHandler.generateExceptionResponse(ErrorCode.INVALID_REQUEST, "Invalid parameter type", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Endpoint not found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<Object> handleSqlException(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(ErrorCode.DB_ERROR, "Database Error", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(ErrorCode.INVALID_REQUEST, "Validation Error", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<Object> handleCostumeAuthException(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateResponse(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthException(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateResponse(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler({ClaimJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateResponse(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND
        );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseHandler.generateExceptionResponse(
                ErrorCode.UNSUPPORTED_MEDIA_TYPE,
                "Content-Type is not supported",
                ex.getMessage(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAll(final RuntimeException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        logger.info("request: " + request.getContextPath());

        return ResponseHandler.generateExceptionResponse(
                ErrorCode.INVALID_REQUEST,
                "Invalid Request",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseHandler.generateExceptionResponse(ErrorCode.INVALID_REQUEST, "Invalid parameter", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseHandler.generateExceptionResponse(ErrorCode.INVALID_REQUEST, "Invalid path", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseHandler.generateExceptionResponse(ErrorCode.INVALID_REQUEST, "Invalid Parameter", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
