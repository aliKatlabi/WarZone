package com.mygdx.rtsgame.elemnts.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public class NeutronBullet extends Bullet{

    private Texture effect;

    public NeutronBullet(float px, float py, GameWorld gw) {
        super(px, py, gw);
        setBullet(getGameWorld().assetManager.manager.get(GameAssetManager.NeutronBulletTexture));
        setDamage(Bullets.NEUTRON_B.damage);
        setVelocity(Bullets.NEUTRON_B.velocity);
        setExistanceTime(Bullets.NEUTRON_B.existenceTime);
        setTransitionTime(Bullets.NEUTRON_B.transitionTime);

        setWidth(7f);
        setHeight(7f);

        effect = getGameWorld().assetManager.manager.get(GameAssetManager.bulletTexture);

    }

    @Override
    public void transition(Batch batch, float ellapsedTime) {

        batch.end();
        batch.begin();

        batch.draw(effect, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation()*3, 1, 1,
                effect.getWidth()/2, effect.getHeight()/2, false, false);

        setBounds(this.getX(), this.getY(),effect.getWidth()*ellapsedTime/15f, effect.getHeight()*ellapsedTime/13f);
    }
}
