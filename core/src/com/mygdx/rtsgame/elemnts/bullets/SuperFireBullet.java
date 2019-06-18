package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public final class SuperFireBullet extends Bullet {

    public SuperFireBullet(float px, float py) {
        super(px, py);

        setBullet(GameAssetManager.getInstance().manager.get(GameAssetManager.bulletTexture));
        setDamage(Bullets.SUPER_FIRE_B.damage);
        setVelocity(Bullets.SUPER_FIRE_B.velocity);
    }

    public SuperFireBullet() {

    }

    @Override
    public void transEffect(Batch batch, float elapsedTime) {

    }
}
