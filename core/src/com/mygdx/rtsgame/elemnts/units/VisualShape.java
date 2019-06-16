package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

final class VisualShape {

    private ShapeRenderer shapeRenderer;
    private Vector2 base1;
    private Vector2 a1;
    private Vector2 a2;
    private Vector2 base2;
    private Vector2 a3;

    enum VisualType
    {
        STAR,PENTAGON
    }

    VisualShape(ShapeRenderer shapeRenderer){

        this.shapeRenderer=shapeRenderer;
        this.shapeRenderer.setColor(Color.GREEN);

    }

    void draw(float x, float y,float r){
         shapeRenderer.circle(x,y,r);

    }

    void drawRect(float x,float y , float w, float h){
        shapeRenderer.rect(x,y,w,h);

    }
    private void poly5(float x,float y, float width,float offset){

        float drift=width/5;
        base1 = new Vector2(x+drift,y);
        a1 = new Vector2(x+width+offset,y+width/2);
        a2 = new Vector2(x+offset,y+width/2);
        base2 = new Vector2(x+width-drift,y);
        a3 = new Vector2(x+width/2+offset,y+width);
    }

    void visual5(float x,float y, float width,float offset,VisualType type){

        poly5(x,y,width,offset);

        if(type == VisualType.STAR)
        {
            shapeRenderer.line(base1.x, base1.y, a1.x, a1.y);
            shapeRenderer.line(a1.x, a1.y, a2.x, a2.y);
            shapeRenderer.line(a2.x, a2.y, base2.x, base2.y);
            shapeRenderer.line(base2.x, base2.y, a3.x, a3.y);
            shapeRenderer.line(a3.x, a3.y, base1.x, base1.y);
        }
        if(type == VisualType.PENTAGON)
        {
            shapeRenderer.line(base1.x, base1.y, a2.x, a2.y);
            shapeRenderer.line(a2.x, a2.y, a3.x, a3.y);
            shapeRenderer.line(a3.x, a3.y, a1.x, a1.y);
            shapeRenderer.line(a1.x, a1.y, base2.x, base2.y);
            shapeRenderer.line(base2.x, base2.y, base1.x, base1.y);
        }
    }

    void pentagon(float x,float y, float width,float offset){



    }

    void explosion(float cx , float cy,float x,float y){


        shapeRenderer.line(cx,cy,x+(float)(50*Math.random()-50*Math.random()),
                y+(float)(100*Math.random()-100*Math.random()),Color.RED,Color.WHITE);


        shapeRenderer.line(cx,cy,x+(float)(70*Math.random()-70*Math.random()),
                y+(float)(100*Math.random()-100*Math.random()),Color.ORANGE,Color.RED);

    }
    public void dispose(){
        shapeRenderer.dispose();
    }
}
