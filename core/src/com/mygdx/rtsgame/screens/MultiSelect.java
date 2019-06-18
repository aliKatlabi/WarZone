package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

/**
 * a collection that allow to handle touch/mouse positions
 * to be able to draw a visual rectangle.
 * <p>this rectangle can contain Elements if they fell inside the rectangle,
 * also can exclude Elements that are not inside it.</p>
 * <p>this can make easy to manipulate Element selection where it is needed.</p>
 * <p>only Element that are in the game world and has a position can benefit of this component.</p>
 *
 * @param <E> Element
 *
 * @author ali Katlabi
 */
public interface MultiSelect<E> {

    ShapeRenderer rectangle = new ShapeRenderer();
    float BE = 37f;

    /**
     * draw the rectangle of selection component
     * @param touchMoveX mouse/touch movement x
     * @param touchMoveY mouse/touch movement y
     */
    void render(float touchMoveX , float touchMoveY);

    /**
     * reset the resizing identifiers
     */
    void reset();

    /**
     * change the resizing identifiers
     * @param touchMoveX mouse/touch  movement x
     * @param touchMoveY mouse/touch  movement y
     * @param dragX mouse/touch  drag x
     * @param dragY mouse/touch drag y
     */
    void resize(float touchMoveX, float touchMoveY,float dragX,float dragY);

    /**
     * check if an Element is contained inside the rectangle boundaries
     * <p> works on Elements contain a getX getY methods
     * make sure to add getX getY methods </p>
     * @param unit element
     * @return true if the E is inside the selection rectangle
     */
    boolean contain(E unit);


    /**
     * clears the selected Elements list
     * @return true when done
     */
    boolean clearIn();

    /**
     * clears the excluded (not selected) Elements list
     */
    void clearOut();

    /**
     * add the unit to the selected Elements list
     * @param unit unit
     */
    void pick(E unit);

    /**
     * add the Element to the excluded (not selected) Elements list
     * @param unit unit
     */
    void exclude(E unit);

    /**
     * dispose and clear
     */
    void dispose();

    /**
     * set the touch up vector
     * @param screenX touch up x
     * @param screenY touch up y
     */
    void setTouchUp(float screenX , float screenY);

    /**
     * set the touch down vector
     * @param screenX touch down x
     * @param screenY touch down y
     */
    void setTouchDown(float screenX , float screenY);

    /**
     * all elements that has been picked
     * @return selected Elements
     */
    ArrayList<E> getInSelect();

    /**
     * all elements that has been excluded
     * @return excluded Elements
     */
    ArrayList<E> getOutSelect();

    /**
     * check if the rectangle is big enough to contain Elements
     * @return true if rectangle is wide enough
     */
    boolean isWideEnough();

    void setColor(Color color);
}
