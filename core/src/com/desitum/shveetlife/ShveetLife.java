package com.desitum.shveetlife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.screens.SplashScreen;

public class ShveetLife extends Game {
	
	@Override
	public void create () {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DataManager.sendData();
                    DataManager.receiveData();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Screen splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);
	}

	@Override
	public void render () {
        super.render();
	}

    public void exitScreen(){
        Screen splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);
    }
    //TODO Zack and Kody Checklist
    //Game Items
    //Game Objects (Buildings)
    //Inventory (Whats left)
    //Life
    //Gold Currency
    //Gold Shop Thingy
    //Save data/settings to Accounts
    //Add Savegame and LoadGame

    //OPTIONAL:
    //Fix Exitgame() command
    //Add More Particles (Fireworks?)
    //In Game Chat
    //In Game Compass/Mini Map

}
