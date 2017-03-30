package com.aarondevelops.endangeredeightball;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MessageHelper
{
    private static final String MESSAGE_HELPER_TAG = "MessageHelper";

    private static TextToSpeech speaker = null;
    private static boolean speakerInitialized = false;

    /***
     * Creates a toast with the given message and displays it in the context
     * @param context - The context to display the message
     * @param message - The message to toast.
     */

    public static void makeToast(Context context, String message)
    {
        Toast errorToast = Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT);

        errorToast.show();
    }

    /***
     * Creates a snackbar with the given message and displays it in the view
     * @param view - The view to display the message
     * @param message - The message to toast.
     */
    public static void makeSnackbar(View view, String message)
    {
        Snackbar displayMessage = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        displayMessage.show();
    }

    /***
     * Initializes the speaker.
     * @param context - The context this method was called from.
     */
    public static void initializeSpeaker(Context context)
    {
        speaker = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {
                if(status == TextToSpeech.ERROR)
                {
                    return;
                }

                speakerInitialized = true;
                setSpeakerVoiceAndSpeed("en-gb-x-fis#male_1-local");
            }
        });

    }

    /***
     * Initializes the speaker with an optional argument to pass a message along.
     * @param context - The context this method was called from.
     * @param message - The message to read once the speaker is successfully initialized.
     */
    private static void initializeSpeaker(final Context context, final String message)
    {
        speaker = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {
                if(status == TextToSpeech.ERROR)
                {
                    return;
                }

                speakerInitialized = true;
                setSpeakerVoiceAndSpeed("en-gb-x-fis#male_1-local");
                makeSpeech(context, message);
            }
        });
    }

    /***
     * Reads the given message through the phone speaker
     * @param context - The context this method was called from.
     * @param message - The message to read aloud.
     */
    public static void makeSpeech(Context context, String message)
    {
        if( ! speakerInitialized)
        {
            makeToast(context, "Speaker not ready, information will be spoken shortly...");
            initializeSpeaker(context, message);
            return;
        }

        speaker.speak(message, TextToSpeech.QUEUE_FLUSH, null, MESSAGE_HELPER_TAG);
    }

    /***
     * Sets the speaker voice to specified name. If name cannot be found,
     * the default voice will be used. This method then sets the speech rate
     * of the voice to slow down the default speed.
     */
    private static void setSpeakerVoiceAndSpeed(String voiceName)
    {
        try
        {
            // find the male Great Britain voice
            for(Voice voice : speaker.getVoices())
            {
                if(voice.getName().equals(voiceName))
                {
                    speaker.setVoice(voice);
                }
            }
        }
        catch(NullPointerException npe)
        {
            Log.e("RuntimeError", "No voices found.");
        }
        finally
        {
            // set speech rate a tad slower
            speaker.setSpeechRate(.95f);
        }

    }

    public static void shutdownSpeaker()
    {
        if(speaker == null)
        {
            return;
        }

        speaker.stop();
        speaker.shutdown();
        speakerInitialized = false;
    }
}
