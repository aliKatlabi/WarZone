package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;

public interface ArmyUnitTool {

     static final float  SHOOTING_RANGE     = 80f;
     static final float  DESTRUCTION_TIME   = 0.7f;
     static final float  DEVIATION          = 15f;

     Body creatBody(BodyDef.BodyType type );
     Bullet loadBullet(float x, float y);
     void installCircularBody(Body body ,int density,float radius,boolean multiBody);
     boolean hit( Vector3 mousePos);

}
