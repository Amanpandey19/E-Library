package com.aman.elibrary;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class LibraryFragment extends Fragment {


    private Library library;
    private AppCompatSpinner spinnerUsers, spinnerBooks;
    private Button btnBorrow;
    private Button btnReturn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        library = new Library(requireContext());
        library.loadUsers(requireContext());
        library.loadBooks(requireContext());

        spinnerUsers = view.findViewById(R.id.spinnerUsers);
        spinnerBooks = view.findViewById(R.id.spinnerBooks);
        btnBorrow = view.findViewById(R.id.btnBorrow);

        // Populate spinners with users and books data
        List<User> users = library.getUsers();
        List<Book> books = library.getBooks();

        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, users);
        ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, books);

        spinnerUsers.setAdapter(userAdapter);
        spinnerBooks.setAdapter(bookAdapter);

        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle borrow button click
                User selectedUser = (User) spinnerUsers.getSelectedItem();
                Book selectedBook = (Book) spinnerBooks.getSelectedItem();

                if (selectedUser != null && selectedBook != null) {
                    if (selectedBook.getQuantity() > 0) {
                        library.borrowBook(selectedUser, selectedBook);
                        library.saveBooks(requireContext());
                        // updateSpinners();
                        Snackbar.make(view, "Book borrowed successfully", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Selected book is out of stock", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Please select a user and a book", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReturn = view.findViewById(R.id.btnReturn);  // Initialize btnReturn

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle return button click
                User selectedUser = (User) spinnerUsers.getSelectedItem();
                Book selectedBook = (Book) spinnerBooks.getSelectedItem();

                if (selectedUser != null && selectedBook != null) {
                    if (selectedUser.getBorrowedBooks() > 0) {
                        library.returnBook(selectedUser, selectedBook);
                        library.saveBooks(requireContext());
                        // updateSpinners();
                        Snackbar.make(view, "Book returned successfully", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Selected user has no borrowed books", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Please select a user and a book", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void updateSpinners() {
        // Update spinners after borrowing a book
        List<User> users = library.getUsers();
        List<Book> books = library.getBooks();

        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, users);
        ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, books);

        spinnerUsers.setAdapter(userAdapter);
        spinnerBooks.setAdapter(bookAdapter);
    }
}