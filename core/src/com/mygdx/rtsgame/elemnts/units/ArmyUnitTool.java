package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;

public interface ArmyUnitTool {

    public Body creatBody(BodyDef.BodyType type );
    public Bullet loadBullet(float x, float y, GameWorld gw);
    public void installCircularBody(Body body ,int density,float radius,boolean multiBody);
    public boolean hit( Vector3 mousePos);

}
