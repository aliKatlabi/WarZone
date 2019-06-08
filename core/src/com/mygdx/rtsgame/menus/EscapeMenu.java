package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.screens.GameScreen;
import com.mygdx.rtsgame.screens.StartScreen;


public class EscapeMenu extends Table {
    private GameWorld gameWorld;
    private GameScreen gameScreen;
    private Skin skin = new Skin(Gdx.files.internal("skins/skin-composer/skin/skin-composer-ui.json"));
    private float BUTTONHIEGHT=20f;
    private float BUTTONWIDTH=100f;

    public EscapeMenu(final GameWorld gw , GameScreen gs){
        gameScreen = gs;
        gameWorld = gw ;

        this.setFillParent(true);
        this.left().top();
        this.add(mainMenu()).padTop(20).padLeft(10).width(BUTTONWIDTH-20).height(BUTTONHIEGHT);
        row();
        this.add(exitButton()).padTop(20).padLeft(10).width(BUTTONWIDTH-20).height(BUTTONHIEGHT);



    }

    TextButton mainMenu(){
        gameScreen.clearSelection();
        TextButton b = new TextButton("Menu", skin);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.dispose();
                gameScreen.getGame().setScreen(new StartScreen(gameScreen.getGame()));

            }
        });

        return b;
    }
    public TextButton exitButton(){

        TextButton b1 = new TextButton("Exit",skin);
        b1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                Gdx.app.exit();


            }
        });
        return b1;
    }
}
