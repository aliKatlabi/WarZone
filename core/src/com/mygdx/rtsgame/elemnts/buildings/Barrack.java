package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;

public  final class Barrack extends Building {

    public Barrack(float px, float py, GameWorld gw, Player pid) {
        super(px, py, gw, pid);
        setTexture( new Texture(Gdx.files.internal("buildings/orangelab02.png")));
        setConstructionTime(Buildings.BARRAKS.constructionTime);
        setHp(Buildings.BARRAKS.HP);


    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        // batch.setProjectionMatrix(gameWorld.getCamera().combined);
        super.draw(batch,parentAlpha);

    }
}
