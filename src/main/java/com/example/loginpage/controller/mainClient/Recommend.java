package com.example.loginpage.controller.mainClient;

import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * A class responsible for creating book recommendations for users based on their favourite genres.
 */
public class Recommend {
    /**
     * Data access object for user-related operations
     */
    private UserDAO userDAO;
    /**
     * Data access object for book-related operations
     */
    private BookDAO bookDAO;
    /**
     * Default constructor initializing UserDAO and BookDAO objects.
     */
    public Recommend() {
        this.userDAO = new UserDAO();
        this.bookDAO = new BookDAO();
    }

    /**
     * Generates book recommendations for a user based on the genres of their favorite books.
     * @param user The user for whom recommendations are to be generated.
     * @return A list of recommended books.
     */
    public List<Book> recommendBooks(User user) {
        Map<String, Integer> genreFrequency = new HashMap<>();
        List<Book> allBooks = bookDAO.index();
        List<Book> recommendedBooks = new ArrayList<>();

        for (Book book : userDAO.getFavoriteBooks(user.getId())) {
            String genre = book.getGenre();
            genreFrequency.put(genre, genreFrequency.getOrDefault(genre, 0) + 1);
        }

        //Identify top genres
        List<String> topGenres = genreFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        // Filter books that are not already favorites or recently interacted
        List<Book> potentialBooks = allBooks.stream()
                .filter(book -> topGenres.contains(book.getGenre()))
                .filter(book -> !user.getFavouriteBooks().contains(book) && !user.getLatestBook().contains(book))
                .toList();

        int maxRecommendations = 10;
        for (Book book : potentialBooks) {
            if (recommendedBooks.size() < maxRecommendations) {
                recommendedBooks.add(book);
            } else {
                break;
            }
        }

        return recommendedBooks;
    }
}
