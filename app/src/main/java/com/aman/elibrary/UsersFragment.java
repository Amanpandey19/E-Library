package com.aman.elibrary;

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


public class UsersFragment extends Fragment implements AddUserDialogFragment.AddUserDialogListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private Library library;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        library = new Library(requireContext());
        library.loadUsers(requireContext());

        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        updateUserList();

        FloatingActionButton fabAddUser = view.findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(view1 -> showAddUserDialog());
        return view;
    }

    public void updateUserList() {
        library.loadUsers(getContext());
        List<User> users = library.getUsers();
        userAdapter = new UserAdapter(users);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }

    private void showAddUserDialog() {
        AddUserDialogFragment dialogFragment = new AddUserDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "AddUserDialog");
    }

    @Override
    public void onUserAdded(User newUser) {
        // Handle the new user added from the dialog
        library.addUser(newUser);
        library.saveUsers(requireContext());
        updateUserList();
        Snackbar.make(getView(), "User added successfully", Snackbar.LENGTH_SHORT).show();
    }
}