package org.example.exercicio1.resources;

public interface Validator<T> {
    boolean validate(T value);
}
