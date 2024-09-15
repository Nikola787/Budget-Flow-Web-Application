package com.nikola787.budget_flow_tracker.exception;

public class ModelInvalidException extends RuntimeException {
    public ModelInvalidException() {super("Model is invalid.");}

    public <T> ModelInvalidException(Class<T> modelClass, String message) {
        super(String.format("Model type of %s is invalid. %s", modelClass, message));
    }
}
