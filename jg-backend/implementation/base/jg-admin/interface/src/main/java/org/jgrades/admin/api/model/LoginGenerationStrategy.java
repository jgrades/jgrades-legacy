package org.jgrades.admin.api.model;

public interface LoginGenerationStrategy<T> {
    String getLogin(T data);
}
