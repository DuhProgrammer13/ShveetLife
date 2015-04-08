package com.desitum.shveetlife.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Settings {
    public static boolean volumeOn = true;
    public static float volume = 1;

    public static void getSound() {
        Preferences prefs = Gdx.app.getPreferences("settings");
        prefs.putBoolean("soundOn", volumeOn);
        prefs.flush();
        if(Settings.volumeOn == true)
        {
            //Assets.menuMusic.setVolume(1);
            volume = 1;
        }
        else
        {
            //Assets.menuMusic.setVolume(0);
            volume = 0;
        }
    }

    public static void load(){
        Preferences prefs = Gdx.app.getPreferences("settings");
        volumeOn = prefs.getBoolean("soundOn", true);
        getSound();
    }


}
