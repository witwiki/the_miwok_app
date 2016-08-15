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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {

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

    @Override
    public void onStop() {
        super.onStop();
        //  Releasing media player resources immediately when activity is stopped
        releaseMediaPlayer();
    }

    public PhrasesFragment() {
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
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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

                Log.v("PhrasesActivity", "Current word: " + word);

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

}
