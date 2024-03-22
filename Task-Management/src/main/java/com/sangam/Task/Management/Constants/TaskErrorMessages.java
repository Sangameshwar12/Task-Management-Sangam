package com.sangam.Task.Management.Constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TaskErrorMessages {
    SUCCESS(HttpStatus.OK, "Operation completed successfully"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred"),
    DUPLICATE_REQUEST(HttpStatus.CONFLICT, "Duplicate request"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task not found with ID: ");

    private final HttpStatus status;
    private final String message;

    TaskErrorMessages(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
