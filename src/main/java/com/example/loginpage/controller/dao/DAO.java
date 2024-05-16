package com.example.loginpage.controller.dao;

import java.util.List;

/**
 * An interface that represents a data access object (DAO) for generic types. The DAO pattern is crucial for separating
 * Data access logic from business logic, improving the maintainability, reusability, and scalability of the application.
 * @param <T> The type of entity with which the DAO works.
 */
public interface DAO<T> {
    /**
     * Returns a list of entities.
     * @return A list of entities.
     */
    List<T> index();

    /**
     * Saves an entity.
     * @param entity The entity to be saved.
     * @return true if the entity is successfully saved, false otherwise.
     */
    boolean save(T entity);
    /**
     * Deletes an entity by its ID.
     *
     * @param id   The ID of the entity to be deleted.
     * @param need An additional parameter indicating whether the deletion needs confirmation.
     * @return true if the entity is successfully deleted, false otherwise.
     */
    boolean delete(int id,int need);
}
