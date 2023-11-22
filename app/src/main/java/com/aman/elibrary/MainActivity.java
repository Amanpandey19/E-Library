package com.aman.elibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        AddBookDialogFragment.AddBookDialogListener,
        AddUserDialogFragment.AddUserDialogListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private BottomNavigationView bottomNavigationView;
    private Library library;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        library = new Library(this);


        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if(null != getSupportActionBar()) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the NavigationView for side navigation
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Handle side navigation item clicks here
            switch (menuItem.getItemId()) {
                case R.id.nav_share:
                    shareApp();
                    break;
                case R.id.nav_rate_us:
                    rateApp();
                    break;
                // Handle other menu items as needed
            }
            drawerLayout.closeDrawers();
            return true;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            // Handle bottom navigation item clicks here
            switch (menuItem.getItemId()) {
                case R.id.books:
                    loadFragment(new BooksFragment(), "BooksFragment");
                    break;
                case R.id.users:
                    loadFragment(new UsersFragment(), "UsersFragment");
                    break;
                case R.id.library:
                    loadFragment(new LibraryFragment(), "LibraryFragment");
                    break;
            }
            return true;
        });

        // Set the default fragment when the app starts
        loadFragment(new BooksFragment(), "BooksFragment");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void shareApp() {
        Toast.makeText(this, "Sharing App", Toast.LENGTH_SHORT).show();
    }

    private void rateApp() {
        Toast.makeText(this, "App is not on play store right now", Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment, tag)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBookAdded(Book newBook) {
        library.loadBooks(getApplicationContext());
        // Add the new book to the library
        library.addBook(newBook);

        // Save the updated data
        library.saveBooks(getApplicationContext());

        // Optionally, you can update the UI in the BooksFragment
        BooksFragment booksFragment = (BooksFragment) getSupportFragmentManager().findFragmentByTag("BooksFragment");
        if (booksFragment != null) {
            booksFragment.updateBookList();
        }

        // You can also display a message to indicate that the book has been added
        Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserAdded(User newUser) {
        library.addUser(newUser);

        // Save the updated data
        library.saveUsers(getApplicationContext());

        // Optionally, you can update the UI in the UsersFragment
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");
        if (usersFragment != null) {
            usersFragment.updateUserList();
        }

        // You can also display a message to indicate that the user has been added
        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();

    }
}