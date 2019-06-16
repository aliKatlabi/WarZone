package com.mygdx.rtsgame.screens;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface MultiSelect<E> {

    ShapeRenderer rectangle = new ShapeRenderer();


    void render(float mouseMoveX , float mouseMoveY);
    boolean contain(E unit);
    void reset();
    void resize(float touchMoveX, float touchMoveY,float dragX,float dragY);
    boolean clearIn();
    void clearOut();
    void pick(E unit);
    void exclude(E unit);

}
