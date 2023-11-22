package com.aman.elibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookDialogFragment extends DialogFragment {

    private EditText etBookName;
    private EditText etBookQuantity;
    private Button btnAddBook;
    private AddBookDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_book_dialog, null);

        etBookName = view.findViewById(R.id.etBookName);
        etBookQuantity = view.findViewById(R.id.etBookQuantity);
        btnAddBook = view.findViewById(R.id.btnAddBook);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = etBookName.getText().toString().trim();
                String quantityStr = etBookQuantity.getText().toString().trim();

                if (!bookName.isEmpty() && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    Book newBook = new Book(bookName, quantity);
                    listener.onBookAdded(newBook);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Please Enter book name and quantity both", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddBookDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddBookDialogListener");
        }
    }

    public interface AddBookDialogListener {
        void onBookAdded(Book newBook);
    }
}