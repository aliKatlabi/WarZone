package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.bullets.FireBullet;
import com.mygdx.rtsgame.elemnts.bullets.NeutronBullet;
import com.mygdx.rtsgame.elemnts.bullets.SuperFireBullet;

import static java.lang.Math.max;

public class SuperSoldier extends ArmyUnit {

    public SuperSoldier(float px, float py, GameWorld gw, Player playrid) {
        super(px, py, gw, playrid);

        setHp(ArmyUnits.SUPER_SOLDIER.HP);
        setRange(ArmyUnits.SUPER_SOLDIER.range);
        setShootingSpeed(ArmyUnits.SUPER_SOLDIER.fireRate);
        setMoveSpeed(ArmyUnits.SUPER_SOLDIER.moveSpeed);
        setSpawnTime(ArmyUnits.SUPER_SOLDIER.spawnTime);
        scale = 0.8f;

        unitTexture     = getGameWorld().assetManager.manager.get(GameAssetManager.soldierTexture);
        setBounds(px, py, unitTexture.getWidth()-5, unitTexture.getHeight()-5);
        //setLaunchPoint( max(2*unitTexture.getWidth()/3,2*unitTexture.getHeight()/3));

        shootingSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/9_mm_gunshot-mike-koenig-123.mp3"));

        movingSound     = getGameWorld().assetManager.manager.get(GameAssetManager.movingSound);
        destroyedSound  = getGameWorld().assetManager.manager.get(GameAssetManager.destroyedSound);

        setUnitBody( creatBody(BodyDef.BodyType.DynamicBody));
        this.installCircularBody(getUnitBody(),10,getWidth()/2,true);

    }

    @Override
    public Bullet loadBullet(float x, float y, GameWorld gw) {
        return new SuperFireBullet(x,y,gw);
    }
}
