package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public class SuperFireBullet extends Bullet {

    public SuperFireBullet(float px, float py, GameWorld gw) {
        super(px, py, gw);

        setBullet(getGameWorld().assetManager.manager.get(GameAssetManager.bulletTexture));
        setDamage(Bullets.SUPERFIRE_B.damage);
        setVelocity(Bullets.SUPERFIRE_B.velocity);
    }

    public SuperFireBullet() {

    }

    @Override
    public void transition(Batch batch, float ellapsedTime) {

    }
}
