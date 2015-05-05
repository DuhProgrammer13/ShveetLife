package com.desitum.shveetlife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.screens.MenuScreen;
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
        Screen menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
    }
    //TODO Zack and Kody Checklist
    //Game Items
    //Game Objects (Buildings)
    //Life
    //Gold Currency
    //Gold Shop Thingy
    //Save data/settings to Accounts


    //OPTIONAL:
    //Add More NPC Commands/Actions
    //Add More Particles (Fireworks?)
    //In Game Chat
    //In Game Compass/Mini Map
    //RedStone Like Stuff
    //Add Savegame and LoadGame

    //POSSIBLE BUILDINGS
    //Castle
    //Bank
    //Village
    //Shop
    //Food Place
    //NPC Spawner Building Thing
    //Slave Shop (NPC's that follow you? :D)
    //Weapon/Tool Shop?
    //House
    //Fountain
    //Lamp
    //Bush/Hedge
    //Firework Shop
    //Firework Spawner

}
