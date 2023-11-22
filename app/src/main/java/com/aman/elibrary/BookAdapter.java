package com.aman.elibrary;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (books != null && position < books.size()) {
            Book currentBook = books.get(position);
            holder.tvBookName.setText(currentBook.getName());
            holder.tvBookQuantity.setText("Available : "+String.valueOf(currentBook.getQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        return books != null ? books.size() : 0;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBookName;
        private TextView tvBookQuantity;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.textViewName);
            tvBookQuantity = itemView.findViewById(R.id.textViewQuantity);
        }
    }
}
