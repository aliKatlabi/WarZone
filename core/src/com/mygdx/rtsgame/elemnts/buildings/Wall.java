package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;

public final class Wall  extends Building {

    public Wall(float px, float py, GameWorld gw, Player pid) {
        super(px, py, gw, pid);
        scale =1f;
        setInConstructTexture(new Texture(Gdx.files.internal("buildings/inConstruct.png")));
        setTexture( new Texture(Gdx.files.internal("buildings/wall.png")));
        setConstructionTime(2f);
        setHp(1000);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        // batch.setProjectionMatrix(gameWorld.getCamera().combined);
        super.draw(batch,parentAlpha);

    }
}
