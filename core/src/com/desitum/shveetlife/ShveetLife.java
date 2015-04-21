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
                JOptionPane.showMessageDialog(null, "POOP. Hahahaha Sucker");
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
}
