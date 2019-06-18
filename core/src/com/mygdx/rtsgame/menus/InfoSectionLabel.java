package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class InfoSectionLabel extends Label {


    private ShapeRenderer shapeRenderer=new ShapeRenderer();
    //private float height;
    //private float width;

     InfoSectionLabel(CharSequence text, Skin skin) {
        super(text, skin);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.BLACK);

    }

    public void render() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect( getX(),getY(),
                            getOriginX(),
                            getOriginY(),
                getWidth()+5f, getHeight()+3f,
                            getScaleX(),
                            getScaleY(), 0);
        shapeRenderer.end();

    }

    void set(float width,float height){

         setWidth(width);
         setHeight(height);
    }
    public void dispose(){
        shapeRenderer.dispose();
    }
}
