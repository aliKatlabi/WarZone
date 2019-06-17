package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.rtsgame.screens.GameScreen;
import com.mygdx.rtsgame.screens.StartScreen;


public class EscapeMenu extends Table {

    private GameScreen gameScreen;
    private Skin skin = new Skin(Gdx.files.internal("skins/skin-composer/skin/skin-composer-ui.json"));
    private static final float BUTTON_HEIGHT=20f;
    private static final float BUTTON_WIDTH=100f;

    public EscapeMenu(GameScreen gs){
        gameScreen = gs;
        this.setFillParent(true);
        this.left().top();
        this.add(mainMenu()).padTop(20).padLeft(10).width(BUTTON_WIDTH-20).height(BUTTON_HEIGHT);
        row();
    }

    private TextButton mainMenu(){


        TextButton b = new TextButton("Menu", skin);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.getMultiSelect().clearIn();
                gameScreen.dispose();
                gameScreen.getGame().setScreen(new StartScreen(gameScreen.getGame()));

            }
        });

        return b;
    }

}
