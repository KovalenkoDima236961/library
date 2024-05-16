package com.example.loginpage.controller.serialization;

import java.io.*;
/**
 * Provides utilities for serialising and deserialising a static people count
 * to and from a data file. This class is used to save counts between sessions
 */
public class SerializationForCountOfPeople {

    private static final String COUNT_FILE = "count_of_people.dat";
    /**
     * Saves the current count of people to a specified file.
     * This method writes the count to a file using {@link DataOutputStream}
     * to ensure it is stored persistently and can be retrieved in subsequent sessions.
     *
     * @param count the current count of people to be saved.
     * @throws IOException if an error occurs during the write operation.
     */
    public static void saveCount(int count) throws IOException {
        System.out.println(count);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(COUNT_FILE))) {
            dos.writeInt(count);
        } catch (IOException e) {
            System.err.println("Could not save count: " + e.getMessage());
        }
    }
    /**
     * Loads the count of people from a specified file.
     * This method reads the count from a file using {@link DataInputStream}.
     *
     * @return the loaded count of people, or 0 if the file does not exist or an error occurs.
     * @throws IOException if an error occurs during the read operation.
     */
    public static int loadCount() throws IOException {
        File file = new File(COUNT_FILE);
        if (!file.exists()) {
            return 0;
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int count = dis.readInt();
            System.out.println(count);
            return count;
        } catch (IOException e) {
            System.err.println("Could not load count: " + e.getMessage());
            return 0;
        }
    }
}
