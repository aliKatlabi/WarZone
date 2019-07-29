package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.rtsgame.RTSGame;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.screens.EndScreen;
import com.mygdx.rtsgame.screens.LoadingScreen;

public class MainMenu extends Table {

    private RTSGame game;
    private Maps currentMap = Maps.NOM;
    private final Skin skin;
    private static final float BUTTON_HEIGHT=40f;
    private static final float BUTTON_WIDTH=200f;

    public MainMenu(final RTSGame game){
        this.game = game;
        skin = new Skin( Gdx.files.internal("skins/tracer/skin/tracer-ui.json"));
        //setDebug(true);
        Label head = new Label("WarZone",skin,"default");
        head.setFontScale(2.9f);

        this.setFillParent(true);
        this.center();
        this.add(head);
        this.row();

        this.add(chooseMap1()).padTop(40f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        this.row();
        this.add(chooseMap2()).padTop(10f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        this.row();
        this.add(chooseMap3()).padTop(10f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        this.row();
        this.add(startButton()).padTop(100f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        this.row();
        this.add(exitButton()).padTop(10f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);


    }


    private TextButton startButton(){

        TextButton b1;
        b1 = new TextButton("Scrimmage",skin);

        b1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(!(currentMap == Maps.NOM)){
                    game.setScreen(new LoadingScreen(game,currentMap));
                }
            }
        });
        return b1;
    }
    private TextButton chooseMap1(){
        TextButton mapb1;
        mapb1 = new TextButton("Borders",skin );

        mapb1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP1;
            }
        });
        return mapb1;
    }
    private TextButton chooseMap2(){
        TextButton mapb2;
        mapb2 = new TextButton("Behind enemies line",skin );
        mapb2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP3;
            }
        });
        return mapb2;
    }

    private TextButton chooseMap3(){
        TextButton mapb3;
        mapb3 = new TextButton("Far Land",skin );
        mapb3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP2;
            }
        });
        return mapb3;
    }


    private TextButton exitButton(){


        TextButton b = new TextButton("Exit",skin);
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                GameAssetManager.getInstance().dispose();
                game.setScreen(new EndScreen(game));
            }
        });
        return b;
    }


}
