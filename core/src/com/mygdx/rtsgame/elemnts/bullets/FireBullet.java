package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public final class FireBullet extends Bullet {


    public FireBullet(float px, float py, GameWorld gw) {
        super(px, py, gw);
        setBullet(GameWorld.assetManager.manager.get(GameAssetManager.bulletTexture));
        setDamage(Bullets.FIRE_B.damage);
        setVelocity(Bullets.FIRE_B.velocity);
    }

     public FireBullet() {

    }

    @Override
    public void transEffect(Batch batch, float ellapsedTime) {

    }

}
