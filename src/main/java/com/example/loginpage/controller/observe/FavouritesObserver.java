package com.example.loginpage.controller.observe;

import com.example.loginpage.module.books.Book;

/**
 * Interface for monitoring changes in the list of favourite books.
 * Users of this interface can receive notifications when books are added to or removed from the favourites list.
 */
public interface FavouritesObserver {

    /**
     * Called when a book is added to the favourites list.
     *
     * @param book The book that has been added to the favorites list.
     */
    void onFavoriteBookAdded(Book book);

    /**
     * Called when a book is removed from the favorites list.
     *
     * @param book The book that has been removed from the favorites list.
     */
    void onFavoriteBookRemoved(Book book);
}
