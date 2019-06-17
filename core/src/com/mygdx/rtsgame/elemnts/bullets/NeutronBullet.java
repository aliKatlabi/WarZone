package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public final class NeutronBullet extends Bullet{

    private Texture effect;
    private float e =0f;

    public NeutronBullet(float px, float py) {
        super(px, py);
        setBullet(GameAssetManager.getInstance().manager.get(GameAssetManager.NeutronBulletTexture));
        setDamage(Bullets.NEUTRON_B.damage);
        setVelocity(Bullets.NEUTRON_B.velocity);
        setExistenceTime(Bullets.NEUTRON_B.existenceTime);
        setTransitionTime(Bullets.NEUTRON_B.transitionTime);

        setWidth(7f);
        setHeight(7f);

        effect = GameAssetManager.getInstance().manager.get(GameAssetManager.bulletTexture);

    }

    @Override
    public void transEffect(Batch batch, float ellapsedTime) {


    }
}
