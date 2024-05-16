package com.example.loginpage.controller.login;
/**
 * Exception class for handling validation errors.
 * This class extends {@link Exception} and is designed to provide detailed information about errors when validation fails while processing user input.
 */
public class ValidationException extends Exception{
    /**
     * Throws a new ValidationException with a detailed message.
     * The message helps to provide more information about the error that occurred during validation.
     *
     * @param message The detailed message that explains the reason for the validation error.
     */
    public ValidationException(String message) {
        super(message);
    }
}
