package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.RTSGame;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.menus.Maps;


public class LoadingScreen  extends Stage implements Screen {
    
    private final RTSGame game;
    //private OrthographicCamera camera;
    private ShapeRenderer progressBar;
    private GameAssetManager gameAssetManager = GameAssetManager.getInstance();


    public LoadingScreen(final RTSGame game, Maps map){
        this.game=game;
        progressBar = new ShapeRenderer();
        progressBar.setAutoShapeType(true);
        GameWorld gameWorld = GameWorld.getInstance();

        gameWorld.loadMap(map);

        gameAssetManager.load();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        progressBar.begin(ShapeRenderer.ShapeType.Filled);

        float progress;

        if(!gameAssetManager.manager.update()){

            progress=gameAssetManager.manager.getProgress();
            progressBar.rect(10f,Gdx.graphics.getHeight()/2f,(progress*(Gdx.graphics.getWidth()-10f)),2f);

        }else{

            progressBar.dispose();
            GameScreen gs = new GameScreen(game);
            gs.setVolume(0.2f);
            game.setScreen(gs);
        }

        progressBar.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        progressBar.dispose();
    }
}
