package com.example.android.miwok;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    /** Declaration of the global instance variable MediaPlayer */
    private MediaPlayer m_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordslist);

        //  Create a list of words based on Custom Class Word.java
        final ArrayList<Word> words = new ArrayList<Word>();

        //  Instantiate and adding default and miwok words
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("grey", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        /**
         *  Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
         adapter knows how to create list items for each item in the list.
         */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

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

                Log.v("ColoursActivity", "Current word: " + word);

                //  Create an instance of the media player
                m_player = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());

                //  Start the audio
                m_player.start();
            }
        });
    }
}
