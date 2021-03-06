package com.example.android.miwok;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass for ColorsActivity.
 */
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_wordslist, container, false);

        //  Setup and create an {@link AudioManager} instance to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        /**
         Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         There should be a {@link ListView} with the view ID called list, which is declared in the
         activity_wordslist.xmlml layout file.
         */
        ListView listView = (ListView) rootView.findViewById(R.id.list);

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

                Log.v("ColoursActivity", "Current word: " + word);

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
                m_player = MediaPlayer.create(getActivity(), word.getmAudioResourceId());

                //  Start the audio
                m_player.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                m_player.setOnCompletionListener(mCompletionListener);
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //  Releasing media player resources immediately when activity is stopped
        releaseMediaPlayer();
    }

}
