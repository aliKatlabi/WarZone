package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class SelectionComponent<E> implements MultiSelect<E> {

    //private ShapeRenderer rectangle;

    /**
     * Elements in screen coordinates
     * if E has position it should be projected onto the screen
     */

    private Vector2 touchDown,touchUp= new Vector2();
    private Vector3 pos = new Vector3();
    private Color color;
    private float resizeW,resizeH;
    private ArrayList<E> inSelect  = new ArrayList<E>();
    private ArrayList<E> outSelect = new ArrayList<E>();


    SelectionComponent(){

        color = Color.RED;
        rectangle.setAutoShapeType(true);
        rectangle.setColor(color);
    }


    @Override
    public void render(float touchMoveX , float touchMoveY) {

        rectangle.begin(ShapeRenderer.ShapeType.Line);
        rectangle.rect(touchMoveX, Gdx.graphics.getHeight() - touchMoveY, resizeW, resizeH);
        rectangle.end();

    }

    @Override
    public boolean contain(E unit) {

        try {
            pos = pos(unit);

        } catch (IllegalAccessException e) {

            e.printStackTrace();

        }

        boolean left     = touchDown.x > touchUp.x;
        boolean right    = touchDown.x < touchUp.x;
        boolean down     = touchDown.y > touchUp.y;
        boolean up       = touchDown.y < touchUp.y;


        boolean a = left&&(pos.x <touchDown.x && pos.x>touchUp.x);
        boolean c = right&&(pos.x >touchDown.x && pos.x<touchUp.x);

        boolean b = up&&(pos.y>touchDown.y && pos.y<touchUp.y);
        boolean d = down &&(pos.y<touchDown.y && pos.y>touchUp.y);

        return b&&a||c&&a||a&&d||c&&d;



    }

    @Override
    public void reset() {

        resizeH=0;
        resizeW=0;
    }

    @Override
    public void resize(float touchMoveX, float touchMoveY,float dragX,float dragY){

        resizeW = dragX - touchMoveX;
        resizeH = touchMoveY - dragY;
    }
    @Override
    public boolean clearIn()
    {
        if(!inSelect.isEmpty()) {

            for (E e : inSelect) {
                if (e instanceof ArmyUnit)
                    ((ArmyUnit) e).setSelected(false);
            }

            inSelect.clear();
        }

        return true;
    }

    @Override
    public void clearOut()
    {
        outSelect.clear();
    }

    @Override
    public void pick(E unit)
    {
        inSelect.add(unit);
    }

    @Override
    public void exclude(E unit)
    {
        outSelect.add(unit);
    }

    private Vector3 pos(E unit) throws IllegalAccessException{

        for(Field f :unit.getClass().getFields()){

            if(f.getName().contains("x")&&f.isAccessible()){

                pos.x = f.getFloat(unit);
            }else{
                pos.x=0;
            }
            if(f.getName().contains("y")&&f.isAccessible()){
                pos.y = f.getFloat(unit);
            }else{
                pos.y=0;
            }
            if(f.getName().contains("z")&&f.isAccessible()){
                pos.z = f.getFloat(unit);
            }else{

                pos.z=0;
            }
        }

        return  GameWorld.getInstance().getCamera().project(pos);
    }

    public void setTouchUp(Vector2 touchUp){
        this.touchUp =touchUp;
    }

    public void setTouchDown(Vector2 touchDown) {
        this.touchDown = touchDown;
    }
    public void setTouchUp(float screenX , float screenY){
        touchUp.set(screenX,screenY);
    }
    private void setTouchDown(float screenX , float screenY){
        touchDown.set(screenX,screenY);
    }

    public ArrayList<E> getInSelect() {
        return inSelect;
    }

    public ArrayList<E> getOutSelect() {
        return outSelect;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
