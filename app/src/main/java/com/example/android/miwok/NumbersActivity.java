package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    /** Declaration of the global instance variable MediaPlayer */
    private MediaPlayer m_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordslist);

        //  Create a list of words based on Custom Class Word.java
        final ArrayList<Word> words = new ArrayList<Word>();

        //  Instantiate and adding default and miwok words
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        /**
         *  Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
            adapter knows how to create list items for each item in the list.
         */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        /**
         Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         There should be a {@link ListView} with the view ID called list, which is declared in the
         activity_wordslist.xmlml layout file.
        */
        ListView listView = (ListView) findViewById(R.id.list);

        /**
         *  Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
         *  {@link ListView} will display list items for each {@link Word} in the list.
         */
        listView.setAdapter(adapter);

        /**
         *  An Item Click Listener that makes {@link ListView} interactive with sound. On clicking
         *  the audio file is played for the word clicked. An audio resource Id is called that
         *  links the activity with the audio file.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //  Get the {@link Word} object at the given position clicked by the user
                Word word = words.get(position);

                Log.v("NumbersActivity", "Current word: " + word);

                //  Create an instance of the media player
                m_player = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());

                //  Start the audio
                m_player.start();
            }
        });

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
