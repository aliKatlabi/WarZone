package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static java.lang.StrictMath.abs;


public class SelectionComponent<E> implements MultiSelect<E> {

    //Elements in screen coordinates
    //if E has position it should be projected onto the screen

    private Vector3 touchDown =new Vector3();
    private Vector3  touchUp = new Vector3();
    private Vector3 pos = new Vector3();
    private Color color;
    private float resizeW,resizeH;
    private ArrayList<E> inSelect  = new ArrayList<E>();
    private ArrayList<E> outSelect = new ArrayList<E>();


    SelectionComponent(){

        color = Color.RED;
        rectangle.setAutoShapeType(true);
        setColor(color);
    }


    @Override
    public void render(float touchMoveX , float touchMoveY) {
        rectangle.end();
        rectangle.begin(ShapeRenderer.ShapeType.Line);

        rectangle.rect(touchMoveX, Gdx.graphics.getHeight() - touchMoveY, resizeW, resizeH);



    }

    @Override
    public boolean contain(E unit) {

        try {
             updatePos(unit);

        } catch (NoSuchMethodException e) {

            e.printStackTrace();
        }catch (InvocationTargetException e) {

            e.printStackTrace();

        }catch (IllegalAccessException e) {

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

    private void updatePos(E unit) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        pos.x = (Float) unit.getClass().getMethod("getX").invoke(unit);
        pos.y = (Float) unit.getClass().getMethod("getY").invoke(unit);
        //pos.z = (Float) unit.getClass().getMethod("getZ").invoke(unit);

    }

    @Override
    public void dispose() {
        clearIn();
        clearOut();
        rectangle.dispose();
    }

    @Override
    public void setTouchUp(float screenX , float screenY){
        touchUp.set(screenX,screenY,0);
        GameWorld.getInstance().getCamera().unproject(touchUp);
    }
    @Override
    public void setTouchDown(float screenX , float screenY){
        touchDown.set(screenX,screenY,0);
        GameWorld.getInstance().getCamera().unproject(touchDown);
    }
    @Override
    public ArrayList<E> getInSelect() {
        return inSelect;
    }
    @Override
    public ArrayList<E> getOutSelect() {
        return outSelect;
    }

    @Override
    public boolean isWideEnough() {
        return abs(resizeW) > BE && abs(resizeH) > BE;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
