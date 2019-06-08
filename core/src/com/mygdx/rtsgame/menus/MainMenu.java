package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.rtsgame.RTSGame;
import com.mygdx.rtsgame.screens.GameScreen;

public class MainMenu extends Table {

    RTSGame game;
    Maps currentMap = Maps.NOM;
    private Skin skin = new Skin(Gdx.files.internal("skins/tracer/skin/tracer-ui.json"));
    private float BUTTONHIEGHT=40f;
    private float BUTTONWIDTH=200f;
    private TextButton b1;
    private TextButton b2;
    private TextButton mapb1;
    private TextButton mapb2;
    private TextButton mapb3;
    ProgressBar progressBar;

    public MainMenu(final RTSGame game ){
        this.game = game;
        //setDebug(true);
        Label head = new Label("WarZone",skin,"default");
        head.setFontScale(2.9f);


        progressBar = new ProgressBar(0,100,30,false,skin);


        this.setFillParent(true);

        this.center();
        this.add(head);
        this.row();

        this.add(chooseMap1()).padTop(40f).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        this.row();
        this.add(chooseMap2()).padTop(10f).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        this.row();
        this.add(chooseMap3()).padTop(10f).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        this.row();
        this.add(startButton()).padTop(100f).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        this.row();
        this.add(exitButton()).padTop(10f).width(BUTTONWIDTH).height(BUTTONHIEGHT);


    }


    public TextButton startButton(){
        b1 = new TextButton("Scrimmage",skin);

        b1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(!(currentMap == Maps.NOM)){
                    row();
                    add(progressBar).padTop(10f).width(BUTTONWIDTH).height(10f);
                    progressBar.act(Gdx.graphics.getDeltaTime());
                    game.setScreen(new GameScreen(game,currentMap));
                }
            }
        });
        return b1;
    }
    public TextButton chooseMap1(){

        mapb1 = new TextButton("Borders",skin );

        mapb1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP1;
            }
        });
        return mapb1;
    }
    public TextButton chooseMap2(){

        mapb2 = new TextButton("Behind enemies line",skin );
        mapb2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP3;
            }
        });
        return mapb2;
    }

    public TextButton chooseMap3(){

        mapb3 = new TextButton("Far Land",skin );
        mapb3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                currentMap = Maps.MAP2;
            }
        });
        return mapb3;
    }


    public TextButton exitButton(){

        TextButton b = new TextButton("Exit",skin);
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();

            }
        });
        return b;
    }


}
