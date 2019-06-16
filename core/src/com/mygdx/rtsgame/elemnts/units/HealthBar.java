package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar {

    private ShapeRenderer shapeRenderer;
    private float width;

    public HealthBar(ShapeRenderer shapeRenderer,float width){

        this.shapeRenderer=shapeRenderer;
        this.width = width;
    }

    public void draw(float x,float y){

        shapeRenderer.rect(x,y,width,1);
    }
    public void update(float damage , float hp){

        if( width - damage*(width/hp) >0)
            width =  width - damage*(width/hp);
        else
            width=0;

    }
    public void setWidth(float width) {

        this.width = width;
    }

    public void dispose(){
        shapeRenderer.dispose();
    }

}
