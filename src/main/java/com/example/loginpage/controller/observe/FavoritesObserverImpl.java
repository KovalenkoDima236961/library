package com.example.loginpage.controller.observe;

import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.module.books.Book;

/**
 * Implementation of the {@link FavouritesObserver} interface that interacts with a data access object (DAO).
 * This class provides a specific reaction to adding or removing books from the favourites list
 */
public class FavoritesObserverImpl implements FavouritesObserver {

    /**
     * The data access object (DAO) for books, used to interact with book data storage and manage favorite books.
     */
    private BookDAO bookDAO;

    /**
     * Constructs a new FavoritesObserverImpl and registers itself as an observer to the BookDAO.
     * This ensures that this observer is notified of any changes to the favorite books list.
     */
    public FavoritesObserverImpl() {
        this.bookDAO = BookDAO.getInstance();
        this.bookDAO.addObserver(this);
    }

    /**
     * Handles the event of adding a book to the favourites list.
     * This method logs the addition and can be extended to include additional logic, such as sending notifications
     *
     * @param book The book that has been added to the favorites list. The book is not {@code null}.
     */
    @Override
    public void onFavoriteBookAdded(Book book) {
        System.out.println("Book added to favorites: " + book.getTitle());
    }

    /**
     * Handles the event of removing a book from the favourites list.
     *
     * @param book The book that has been removed from the favorites list. The book is not {@code null}.
     */
    @Override
    public void onFavoriteBookRemoved(Book book) {
        System.out.println("Book removed from favorites: " + book.getTitle());
    }
}
