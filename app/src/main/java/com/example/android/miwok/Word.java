package com.example.android.miwok;

/**
 * Created by witwiki on 7/25/2016.
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    // Default translation for the word
    private String modDefaultTranslation;

    /** Miwok translation for the word */
    private String modMiwokTranslation;

    /**
    * Create a new Word object.
    *
    * @param defaultTranslation is the word in a language that the user is already familiar with
    *                           (such as English)
    * @param miwokTranslation is the word in the Miwok language
    */
    public Word(String defaultTranslation, String miwokTranslation) {
        modDefaultTranslation = defaultTranslation;
        modMiwokTranslation = miwokTranslation;
    }

    /**
    * Get the default translation of the word.
    */
    public String getDefaultTranslation() {
        return modDefaultTranslation;
    }

    /**
    * Get the Miwok translation of the word.
    */
    public String getMiwokTranslation() {
        return modMiwokTranslation;
    }
}
