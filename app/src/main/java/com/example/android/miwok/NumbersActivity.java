package com.example.android.miwok;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new NumbersFragment())
                .commit();

        /*
        // We define and find the root View so child views can be added to it
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        // Setup counter for the while loop to go through the ArrayList
        int count = 0;
            while (count < words.size()){
            // Create a new TextView
            TextView wordView = new TextView(this);
            // Set the text to be the word at the current count
            wordView.setText(words.get(count));
            //  Add this view as a child to the root view of this layout
            rootView.addView(wordView);
            //  Logs the word being added to logcat
            Log.v("NumbersActivity", "Word at index " + words.indexOf(words.get(count)) + ": " + words.get(count));
            // Update counter
            count++;
        }
        */
        /*
        //  Setup and execute for loop to the ArrayList
        for (int count = 0; count < words.size(); count++){
            // Create a new TextView
            TextView wordView = new TextView(this);
            // Set the text to be the word at the current count
            wordView.setText(words.get(count));
            //  Add this view as a child to the root view of this layout
            rootView.addView(wordView);
            //  Logs the word being added to logcat
            Log.v("NumbersActivity", "Word at index " + words.indexOf(words.get(count)) + ": " + words.get(count));
        }
        */
    }
}
