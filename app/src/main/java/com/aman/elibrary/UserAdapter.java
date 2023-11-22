package com.aman.elibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// UserAdapter.java
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewBorrowedBooks.setText("Books borrowed : " +String.valueOf(user.getBorrowedBooks()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public void setUsers(List<User> users)
    {
        this.users = users;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewBorrowedBooks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewUserName);
            textViewBorrowedBooks = itemView.findViewById(R.id.textViewBorrowedBooks);
        }
    }
}

