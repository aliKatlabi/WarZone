package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;

public class WarFactory extends Building {

    public WarFactory(float px, float py, GameWorld gw, Player pid) {
        super(px, py, gw, pid);
        setTexture( new Texture(Gdx.files.internal("buildings/manufactory02.png")));
        setConstructionTime(Buildings.WARFACTORY.constructionTime);
        setHp(Buildings.WARFACTORY.HP);

    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        // batch.setProjectionMatrix(gameWorld.getCamera().combined);
        super.draw(batch,parentAlpha);

    }

}
