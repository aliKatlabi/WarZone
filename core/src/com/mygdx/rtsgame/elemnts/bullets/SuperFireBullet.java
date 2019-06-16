package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public final class SuperFireBullet extends Bullet {

    public SuperFireBullet(float px, float py) {
        super(px, py);

        setBullet(GameWorld.assetManager.manager.get(GameAssetManager.bulletTexture));
        setDamage(Bullets.SUPERFIRE_B.damage);
        setVelocity(Bullets.SUPERFIRE_B.velocity);
    }

    public SuperFireBullet() {

    }

    @Override
    public void transEffect(Batch batch, float ellapsedTime) {

    }
}
