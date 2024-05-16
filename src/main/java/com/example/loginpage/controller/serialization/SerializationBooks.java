package com.example.loginpage.controller.serialization;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;

import java.io.*;
import java.util.List;
/**
 * Provides utility methods for serializing and deserializing book lists to and from persistent storage.
 * This class is typically used to manage the persistence of user-specific data, such as recent interactions with books.
 */
public class SerializationBooks {
    /**
     * Flag for serialization
     */
    private static boolean flag;
    /**
     * Checks if the serialization flag is set.
     * This flag is typically used to indicate whether the books have recently been serialised, ensuring data consistency or preventing redundant transactions.
     * @return true if the flag is set (indicating recent serialization), false otherwise.
     */
    public static boolean isFlag() {
        return flag;
    }
    /**
     * Default constructor for the SerializationBooks class.
     */
    public SerializationBooks(){}

    /**
     * Sets the serialization flag.
     * This method is typically called after serialization operations to set a flag indicating the current state of the data.
     * @param flag the new state of the flag.
     */
    public static void setFlag(boolean flag) {
        SerializationBooks.flag = flag;
    }

    /**
     * Serializes a list of books to the specified file.
     * This method writes the book list to the specified file using object serialization,
     * which can then be used for permanent storage or later retrieval.
     * @param latestBooks the list of books to serialize.
     * @param filename the name of the file to which the books are to be serialized.
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    public static void serializeLatestBooks(List<Book> latestBooks, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            flag = true;
            oos.writeObject(latestBooks);
        }
    }
    /**
     * Deserializes the book list from the specified file.
     * This method reads the book list from the specified file, reconstructing it from the contents of the file.
     * @param filename the name of the file from which the books are to be deserialized.
     * @return a list of books as deserialized from the file.
     * @throws IOException if an I/O error occurs during reading from the file.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public static List<Book> deserializeLatestBooks(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Book>) ois.readObject();
        }
    }
}
