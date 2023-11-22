package com.aman.elibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class BooksFragment extends Fragment implements AddBookDialogFragment.AddBookDialogListener{

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Library library;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        library = new Library(requireContext());
        library.loadBooks(requireContext());

        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        updateBookList();

        FloatingActionButton fabAddBook = view.findViewById(R.id.fabAddBook);
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBookDialog();
            }
        });

        return view;
    }

    private void showAddBookDialog() {
        AddBookDialogFragment dialogFragment = new AddBookDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "AddBookDialog");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBookAdded(Book newBook) {
        // Handle the new book added from the dialog
        library.addBook(newBook);
        library.saveBooks(requireContext());
        updateBookList();
        Snackbar.make(getView(), "Book added successfully", Snackbar.LENGTH_SHORT).show();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateBookList() {
        library.loadBooks(getContext());
        List<Book> books = library.getBooks();
        bookAdapter = new BookAdapter(books );
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        bookAdapter.notifyItemInserted(books.size() - 1);
    }
}