package com.mygdx.rtsgame.elemnts.bullets;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;

public abstract class Bullet extends ArmyUnit implements BulletBehaviour {

    private Texture bullet ;
    private boolean collided=false;
    public float damage;
    private float velocity;
    private float elapsedTime = 0;
    private float existenceTime=2f;
    private static final float activeTime=0.03f;
    private float transitionTime;

    Bullet(float px, float py) {
        super(px,py,Player.NOP);
        setX(px);
        setY(py);
        setHp(0);
        setSpawnTime(0);
        setWidth(5f);
        setHeight(5f);

        setUnitBody( creatBody(BodyDef.BodyType.DynamicBody));
        getUnitBody().setActive(false);
        installCircularBody(getUnitBody(),999,this.getWidth() /5 - 3,false);

    }

    public Bullet() {

    }

    @Override
    public void moveTo(float mouseX , float mouseY, boolean sound){

            MoveToAction move = new MoveToAction();
            move.setPosition(mouseX, mouseY);
            move.setDuration(velocity);
            this.addAction(move);

    }
    @Override
    public void draw(Batch batch, float alpha) {

            batch.end();
            batch.begin();

            batch.draw(bullet, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                    this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 1, 1,
                    bullet.getWidth(), bullet.getHeight(), false, false);

            getUnitBody().setTransform(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, 0);

            if(elapsedTime<transitionTime)
                transEffect(batch,elapsedTime);


    }

    @Override
    public void act(float delta) {

        elapsedTime += delta;

        if(elapsedTime > existenceTime){
            destroy();
        }
        if(elapsedTime > activeTime && !collided){
            getUnitBody().setActive(true);
        }

        shatter(collided);

        for (Action a:getActions()) {
            a.act(delta);

        }
    }


    @Override
    public abstract void transEffect(Batch batch , float elapsedTime);

    @Override
    public void shatter(boolean collided) {

        if(collided)
            getUnitBody().setActive(false);

    }


    @Override
    public void destroy() {

        getUnitBody().setActive(false);
        this.setVisible(false);
        this.setDestroyed(true);
        this.setSpawned(false);
    }


//////////////////////////////////////
    /*
    public boolean isCollided() {
        return collided;
    }
*/
    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public void setBullet(Texture bullet) {
        this.bullet = bullet;
    }

    void setDamage(float damage) {
        this.damage = damage;
    }

    void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    void setExistenceTime(float existenceTime) {
        this.existenceTime = existenceTime;
    }

    void setTransitionTime(float transitionTime) {
        this.transitionTime = transitionTime;
    }
}
