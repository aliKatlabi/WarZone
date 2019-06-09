package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar {

    private ShapeRenderer shapeRenderer;
    private float width;
    HealthBar(ShapeRenderer shapeRenderer){

        this.shapeRenderer=shapeRenderer;
        width = 32f;
    }

    void draw(float x,float y){

        shapeRenderer.rect(x,y,width,1);
    }
    void update(float damage , float hp){

        width =  width - damage*(width/hp);

    }
    public void setWidth(float width) {

        this.width = width;
    }

}
