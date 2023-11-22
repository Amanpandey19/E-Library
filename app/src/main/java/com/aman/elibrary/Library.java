package com.aman.elibrary;

// Library.java
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<User> users;
    private Context context;

    // Constructors, getters, setters

    public Library(Context context) {
        // Initialize the list
        books = new ArrayList<>();
        users = new ArrayList<>();
        this.context = context;
        loadBooks(context);
        loadUsers(context);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addBook(Book book) {
        if(books == null)
        {
            books = new ArrayList<>();
        }
        books.add(book);
        saveBooks(context);
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        saveUsers(context);
    }

    public void borrowBook(User user, Book book) {
        user.borrowBook();
        book.setQuantity(book.getQuantity() - 1);
        saveUsers(context);
    }

    public void returnBook(User user, Book book) {
        user.returnBook();
        book.setQuantity(book.getQuantity() + 1);
        saveUsers(context);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void saveBooks(Context context) {
        try {
            File file = new File(context.getFilesDir(), "books.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            for (Book book : books) {
                String line = book.getName() + "," + book.getQuantity();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save users to a file
    // Save users to a file
    public void saveUsers(Context context) {
        try {
            File file = new File(context.getFilesDir(), "users.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            for (User user : users) {
                String line = user.getName() + "," + user.getNumberOfBooksBorrowed();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load users from a file
    public void loadUsers(Context context) {
        try {
            users = new ArrayList<>();
            File file = new File(context.getFilesDir(), "users.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String userName = parts[0].trim();
                int numberOfBooksBorrowed = Integer.parseInt(parts[1].trim());
                User user = new User(userName, numberOfBooksBorrowed);
                user.setBorrowedBooks(numberOfBooksBorrowed);
                users.add(user);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// ...

    private Book findBookByName(String bookName) {
        for (Book book : books) {
            if (book.getName().equals(bookName)) {
                return book;
            }
        }
        return null;
    }



    // Load books from a file
    public void loadBooks(Context context) {
        try {
            books = new ArrayList<>();
            File file = new File(context.getFilesDir(), "books.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    books.add(new Book(name, quantity));
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

