package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;

public interface BulletBehaviour {


    /**
     * draw bullet effect before collision
     * @param batch batch to use
     * @param elapsedTime starts when bullets are out
     */
    void transEffect(Batch batch , float elapsedTime);

    /**
     * define the collision behaviour
     * @param collided collision boolean
     */
    void shatter(boolean collided);

}
