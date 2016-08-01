package com.example.android.miwok;

/**
 * Created by witwiki on 7/25/2016.
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    //  Variables Declarations
    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;

    /** Image Resource ID for the word */
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    /** A constant value that represents that no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;            //  "-1" is used as it is out of scope of all resource IDs

    //  Constructor(s) Declarations
    /**
    * Create a new Word object. (1st Constructor)
    *
    * @param defaultTranslation is the word in a language that the user is already familiar with
    *                           (such as English)
    * @param miwokTranslation is the word in the Miwok language
    */
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    /**
     * Create a new Word object. (2nd Constructor)
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     * @param imageResourceID is the resource ID for an image in the Miwok word
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = imageResourceID;
    }

    //  Getters Declarations
    /**
    * Get the default translation of the word.
    */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
    * Get the Miwok translation of the word.
    */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Get the Image Resource ID for the word.
     */
    public int getImageResourceID() {
        return mImageResourceID;
    }

    /**
     * This method returns a boolean on whether or not an image exists
     */
    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

}
