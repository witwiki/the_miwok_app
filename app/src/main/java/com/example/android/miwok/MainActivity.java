package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Force set title in the action bar
        //setTitle("Miwok");

        // Find the View that shows the numbers, colors, family and phrases categories
        TextView numbers = (TextView) findViewById(R.id.numbers);
        TextView colors = (TextView) findViewById(R.id.colors);
        TextView famMembers = (TextView) findViewById(R.id.family);
        TextView phrases = (TextView) findViewById(R.id.phrases);

        //Set click listeners on the corresponding Views
        //  Numbers
        numbers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create a new Intent to open the {@link NumbersActivity}
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                // Start the new activity
                startActivity(numbersIntent);
            }
        });
        //  Colors
        colors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create a new Intent to open the {@link ColorsActivity}
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                // Start the new activity
                startActivity(colorsIntent);
            }
        });
        //  Family
        famMembers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create a new Intent to open the {@link FamilyActivity}
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                // Start the new activity
                startActivity(familyIntent);
            }
        });
        //  Phrases
        phrases.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create a new Intent to open the {@link PhrasesActivity}
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                // Start the new activity
                startActivity(phrasesIntent);
            }
        });
    }
}
