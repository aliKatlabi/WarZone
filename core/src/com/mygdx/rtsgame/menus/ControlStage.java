package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.screens.GameScreen;

public class ControlStage extends Stage {

    GameWorld gameWorld;

    public ControlStage(GameWorld gameWorld){

        this.gameWorld=gameWorld;
    }

    @Override
    public boolean keyUp(int keycode) {


        if(keycode == Input.Keys.TAB ){

            GameScreen.TAB = !GameScreen.TAB;

        }


        if(keycode == Input.Keys.ESCAPE ){

            GameScreen.ESC = !GameScreen.ESC;

        }



        if(keycode == Input.Keys.NUM_1 ){
            gameWorld.setCurrentPlayer(Player.PLAYER1);
        }
        if(keycode == Input.Keys.NUM_2 ){
            gameWorld.setCurrentPlayer(Player.PLAYER2);
        }

        return true;
    }

}
