package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    /** Declaration of the global instance variable MediaPlayer */
    private MediaPlayer m_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordslist);

        //  Create a list of words based on Custom Class Word.java
        final ArrayList<Word> words = new ArrayList<Word>();

        //  Instantiate and adding default and miwok words
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        /**
         *  Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
         adapter knows how to create list items for each item in the list.
         */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

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

                //  Create an instance of the media player
                m_player = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourceId());

                //  Start the audio
                m_player.start();
            }
        });
    }
}
