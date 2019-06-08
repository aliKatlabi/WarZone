package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.rtsgame.screens.GameScreen;
import com.mygdx.rtsgame.screens.StartScreen;

public class EndGameDialog extends Dialog {

    GameScreen gameScreen;

    public EndGameDialog(String title, Skin skin, GameScreen G) {
        super(title, skin);
        gameScreen=G;
        this.button("ok",G);
        this.setBounds(gameScreen.getWidth()/2,gameScreen.getHeight()/2,200,100);

    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if(object instanceof GameScreen){
            ((GameScreen)object).dispose();
            ((GameScreen)object).getGame().setScreen(new StartScreen(((GameScreen)object).getGame()));
        }
    }


    public void updateState(){

        if(gameScreen.getGameWorld().hasUnits) {

            if (gameScreen.getGameWorld().enemyUnitsCount <= 0) {
                System.out.println("won" + gameScreen.getGameWorld().enemyUnitsCount);
                this.text("Congratulations! You Won");
                this.show(gameScreen.getGameWorld());
            }
            if (gameScreen.getGameWorld().currentPlayersUnitsCount <= 0) {
                //System.out.println("lost" + GameWorld.enemyUnitsCount);
                this.text("War is tragedy !");
                this.show(gameScreen.getGameWorld());
            }
        }

    }
}
