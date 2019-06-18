package com.mygdx.rtsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.rtsgame.menus.Maps;
import com.mygdx.rtsgame.screens.LoadingScreen;
import com.mygdx.rtsgame.screens.StartScreen;

import java.util.ArrayList;

public class RTSGame extends Game {


    @Override
    public void create() {

        this.setScreen(new StartScreen(this));
        //this.setScreen(new LoadingScreen(this,Maps.MAP3))

    }



}
