package com.aman.elibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class Book {
    private String name;
    private int quantity;

    public Book(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Constructors, getters, setters

    // Method to convert Book object to a JSON representation
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Method to create a Book object from a JSON representation
    public static Book fromJson(JSONObject json) {
        try {
            String name = json.getString("name");
            int quantity = json.getInt("quantity");
            return new Book(name, quantity);
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

