package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    /** Declaration of the global instance variable MediaPlayer */
    private MediaPlayer m_player;

    /** Audio focus during audio file operations */
    private AudioManager audioManager;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This listener is activated whenever the audio focus state is changed.
     * For example, having a gain or loss of audio focus due to other running apps
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                /**
                 *  Here, the "LOSS_TRANSIENT" scenario means we've lost sound for a short
                 *  time period. Whilst, "LOSS_TRANSIENT_CAN_DUCK" scenario means can run
                 *  but on lower output sounds.
                 */
                m_player.pause();
                m_player.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //  Audio focus has been gained/regained and we start the audio file from the beginning
                m_player.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //  Audio focus has been lost so we stop playback and release/clean up resources
                releaseMediaPlayer();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordslist);

        //  Setup and create an {@link AudioManager} instance to request audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //  Create a list of words based on Custom Class Word.java
        final ArrayList<Word> words = new ArrayList<Word>();

        //  Instantiate and adding default and miwok words
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        /**
         *  Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
         adapter knows how to create list items for each item in the list.
         */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

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

                //  Release MediaPlayer resources before the MediaPlayer is initialized
                releaseMediaPlayer();

                //  Get the {@link Word} object at the given position clicked by the user
                Word word = words.get(position);

                Log.v("FamilyActivity", "Current word: " + word);

                /** Requests audio focus to play the audio file. Playing an audio would
                 * require a short amount of time so, we called AUDIOFOCUS_GAIN_TRANSIENT
                 * with the request for audio focus.
                 * */
                int requestAF = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (requestAF == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //  Audio focus now exists and is granted
                }

                //  Create an instance of the media player
                m_player = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());

                //  Start the audio
                m_player.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                m_player.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //  Releasing media player resources immediately when activity is stopped
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (m_player != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            m_player.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            m_player = null;

            /**  Whether or not audio focus is granted, we unregister the
             *  AudioFocusChangeListener to avoid any callbacks
             */
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
