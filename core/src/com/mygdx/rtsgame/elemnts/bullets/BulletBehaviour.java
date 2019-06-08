package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface BulletBehaviour {

    void fired();
    void transition(Batch batch , float ellapsedTime);
    void collide();
    void destroy();
}
