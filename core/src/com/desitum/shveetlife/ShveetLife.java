package com.desitum.shveetlife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.screens.SplashScreen;

import javax.swing.JOptionPane;

public class ShveetLife extends Game {
	
	@Override
	public void create () {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataManager.sendData();
                DataManager.receiveData();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }
        }).start();

        Screen splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);
	}

	@Override
	public void render () {
        super.render();
	}


    //TODO Zack and Kody Checklist
    //Pause Menu
    //Game Items
    //Game Objects (Buildings)
    //Inventory
    //Life
    //Gold Shop Thingy
    //Save data/settings to Accounts
    //Add Savegame and LoadGame

    //OPTIONAL:
    //Fix Exitgame() command
    //Add More Particles (Fireworks?)
    //In Game Chat
    //In Game Compass/Mini Map

}
