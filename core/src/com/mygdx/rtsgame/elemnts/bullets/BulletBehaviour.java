package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface BulletBehaviour {


    void transEffect(Batch batch , float elapsedTime);
    void shatter(boolean collided);

}
