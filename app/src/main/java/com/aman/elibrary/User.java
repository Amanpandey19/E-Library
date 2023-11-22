package com.aman.elibrary;

// User.java
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// User.java
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private int borrowedBooks;

    public User(String name, int borrowedBooks) {
        this.name = name;
        this.borrowedBooks = borrowedBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    // Getters and setters

    public void borrowBook() {
        borrowedBooks++;
    }

    public void returnBook() {
        borrowedBooks--;
    }

    public int getNumberOfBooksBorrowed() {
        return borrowedBooks;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("borrowedBooks", borrowedBooks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static User fromJson(JSONObject json) {
        try {
            String name = json.getString("name");
            int borrowedBooks = json.getInt("borrowedBooks");

            User user = new User(name, borrowedBooks);
            user.setBorrowedBooks(borrowedBooks);
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return name; // Or any other information you want to display in the spinner
    }
}
