package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public class FireBullet extends Bullet {


    public FireBullet(float px, float py, GameWorld gw) {
        super(px, py, gw);
        setBullet(getGameWorld().assetManager.manager.get(GameAssetManager.bulletTexture));
        setDamage(Bullets.FIRE_B.damage);
        setVelocity(Bullets.FIRE_B.velocity);
    }

     public FireBullet() {

    }

    @Override
    public void transition(Batch batch, float ellapsedTime) {

    }

}
