package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.bullets.FireBullet;

public final class Soldier extends ArmyUnit {
    public Soldier(float px, float py,  Player playrid) {
        super(px, py,  playrid);

        setHp(ArmyUnits.SOLDIER.HP);
        setRange(ArmyUnits.SOLDIER.range);
        setShootingSpeed(ArmyUnits.SOLDIER.fireRate);
        setMoveSpeed(ArmyUnits.SOLDIER.moveSpeed);
        setSpawnTime(ArmyUnits.SOLDIER.spawnTime);

        scale = 0.8f;

        unitTexture     = GameAssetManager.getInstance().manager.get(GameAssetManager.soldierTexture);
        setBounds(px, py, unitTexture.getWidth()-5, unitTexture.getHeight()-5);
        //setLaunchPoint( max(2*unitTexture.getWidth()/3,2*unitTexture.getHeight()/3));

        shootingSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/9_mm_gunshot-mike-koenig-123.mp3"));

        movingSound     = GameAssetManager.getInstance().manager.get(GameAssetManager.movingSound);
        destroyedSound  = GameAssetManager.getInstance().manager.get(GameAssetManager.destroyedSound);

        setUnitBody( creatBody(BodyDef.BodyType.DynamicBody));
        this.installCircularBody(getUnitBody(),10,getWidth()/2,true);
        //this.installCircularBody(getUnitBody(),10,getWidth()/3);
    }

    @Override
    public Bullet loadBullet(float x, float y)
    {
        return new FireBullet(x,y);
    }

}