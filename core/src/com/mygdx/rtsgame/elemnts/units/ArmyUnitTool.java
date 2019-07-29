package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;

public interface ArmyUnitTool {

     Body creatBody(BodyDef.BodyType type );
     Bullet loadBullet(float x, float y);
     void installCircularBody(Body body ,float density,float radius,boolean multiBody);
     boolean hit( Vector3 mousePos);

}
