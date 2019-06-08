package com.mygdx.rtsgame;

import com.badlogic.gdx.Game;
import com.mygdx.rtsgame.menus.Maps;
import com.mygdx.rtsgame.screens.GameScreen;

public class RTSGame extends Game {
    @Override
    public void create() {

        //this.setScreen(new StartScreen(this));
        this.setScreen(new GameScreen(this,Maps.MAP3));

    }
}
