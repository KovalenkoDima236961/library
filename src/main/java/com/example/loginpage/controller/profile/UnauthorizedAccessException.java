package com.example.loginpage.controller.profile;
/**
 * A user exception used to indicate that a user has attempted to perform an operation without the required permissions.
 * Typically, this exception is thrown in response to unauthorised attempts to access a specific function or data.
 */
public class UnauthorizedAccessException extends Exception{
    /**
     * Throws a new UnauthorisedAccessException with a detailed message.
     * The message explains the cause of the error, which helps to understand what operation was attempted
     * without proper permission.
     *
     * @param message The detailed message that explains the reason for the unauthorized access attempt.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
