package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.bullets.NeutronBullet;

public final class Tank extends ArmyUnit{

    public Tank(float px, float py, Player playrid){
        super(px,py,playrid);

        setHp(ArmyUnits.TANK.HP);
        setRange(ArmyUnits.TANK.range);
        setShootingSpeed(ArmyUnits.TANK.fireRate);
        setMoveSpeed(ArmyUnits.TANK.moveSpeed);
        setSpawnTime(ArmyUnits.TANK.spawnTime);
        scale =1.1f;

        unitTexture = GameAssetManager.getInstance().manager.get(GameAssetManager.armyUnitTexture);
        setBounds(px,py,unitTexture.getWidth(),unitTexture.getHeight());
        //setLaunchPoint( max(4*unitTexture.getWidth()/5,4*unitTexture.getHeight()/5));

        shootingSound   = GameAssetManager.getInstance().manager.get(GameAssetManager.tankShootingSound);
        movingSound     = GameAssetManager.getInstance().manager.get(GameAssetManager.tankMovingSound);
        destroyedSound  = GameAssetManager.getInstance().manager.get(GameAssetManager.tankDestroyedSound);

        setUnitBody(creatBody(BodyDef.BodyType.DynamicBody));
        installCircularBody(getUnitBody(),ArmyUnit.Density.armyU.density,this.getWidth() /2 - 3,true);
       // installCircularBody(getUnitBody(),10,getWidth()/3);

    }
    @Override
    public Bullet loadBullet(float x, float y) {

        return new NeutronBullet(x,y);
    }

}
