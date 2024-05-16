package com.example.loginpage.controller.observe;

import com.example.loginpage.module.books.Book;

/**
 * Interface for objects that can observe changes in the list of favourite books.
 * This interface defines methods for adding and removing observers, and for notifying them when books are added or removed.
 */
public interface FavouritesObservable {

    /**
     * Adds an observer to the list of observers. This observer will be notified of any changes to the favourites list,
     * such as adding or removing books.
     *
     * @param observer The {@link FavouritesObserver} to be added to the list. Must not be {@code null}.
     */
    void addObserver(FavouritesObserver observer);

    /**
     * Removes an observer from the list of observers. After deletion, the observer will no longer receive notifications
     * About changes in the list of favourites.
     *
     * @param observer The {@link FavouritesObserver} to be removed from the list. Must not be {@code null}.
     */
    void removeObserver(FavouritesObserver observer);

    /**
     * Notifies all registered observers that a book has been added to the favorites list. Each observer's
     * {@code onFavoriteBookAdded} method will be called.
     *
     * @param book The book that has been added to the favorites list. This book is not {@code null}.
     */
    void notifyObserversBookAdded(Book book);

    /**
     * Notifies all registered observers that a book has been removed from the favorites list. Each observer's
     * {@code onFavoriteBookRemoved} method will be called.
     *
     * @param book The book that has been removed from the favorites list. This book is not {@code null}.
     */
    void notifyObserversBookRemoved(Book book);
}
