package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class InfoSectionLabel extends Label {


    private ShapeRenderer frame =new ShapeRenderer();
    //private float height;
    //private float width;

     InfoSectionLabel(CharSequence text, Skin skin) {
        super(text, skin);
         frame.setAutoShapeType(true);

    }

    public void render() {

        frame.begin(ShapeRenderer.ShapeType.Line);
        frame.rect( getX(),getY(),
                            getOriginX(),
                            getOriginY(),
                getWidth()+5f, getHeight()+3f,
                            getScaleX(),
                            getScaleY(), 0);
        frame.end();

    }

    void set(float width,float height){

         setWidth(width);
         setHeight(height);
    }
    public void dispose(){
        frame.dispose();
    }

    void setFrameColor(Color color){ frame.setColor(color);}
}
