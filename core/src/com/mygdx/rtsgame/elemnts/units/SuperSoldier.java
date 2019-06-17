package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.bullets.SuperFireBullet;


public final class SuperSoldier extends ArmyUnit {

    public SuperSoldier(float px, float py,  Player playrid) {
        super(px, py, playrid);

        setHp(ArmyUnits.SUPER_SOLDIER.HP);
        setRange(ArmyUnits.SUPER_SOLDIER.range);
        setShootingSpeed(ArmyUnits.SUPER_SOLDIER.fireRate);
        setMoveSpeed(ArmyUnits.SUPER_SOLDIER.moveSpeed);
        setSpawnTime(ArmyUnits.SUPER_SOLDIER.spawnTime);
        scale = 0.8f;
        unitTexture     = GameAssetManager.getInstance().manager.get(GameAssetManager.soldierTexture);
        setBounds(px, py, unitTexture.getWidth()-5, unitTexture.getHeight()-5);
        shootingSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/9_mm_gunshot-mike-koenig-123.mp3"));

        movingSound     = GameAssetManager.getInstance().manager.get(GameAssetManager.movingSound);
        destroyedSound  = GameAssetManager.getInstance().manager.get(GameAssetManager.destroyedSound);

        setUnitBody( creatBody(BodyDef.BodyType.DynamicBody));
        this.installCircularBody(getUnitBody(),10,getWidth()/2,true);

    }

    @Override
    public Bullet loadBullet(float x, float y) {

        return new SuperFireBullet(x,y);
    }
}
