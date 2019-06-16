package com.mygdx.rtsgame;

import com.badlogic.gdx.Game;
import com.mygdx.rtsgame.menus.Maps;
import com.mygdx.rtsgame.screens.LoadingScreen;

public class RTSGame extends Game {
    @Override
    public void create() {

        //this.setScreen(new StartScreen(this));
        this.setScreen(new LoadingScreen(this,Maps.MAP3));

    }

}
