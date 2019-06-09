package com.mygdx.rtsgame.elemnts.bullets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;

import java.util.Iterator;


public abstract class Bullet extends ArmyUnit implements BulletBehaviour {

    private Texture bullet ;
    private boolean collided=false;
    public float damage;
    private float velocity;
    private float ellapsedTime = 0;
    private float existanceTime=2f;
    private float activeTime=0.1f;
    private float transitionTime;

    Bullet(float px, float py, GameWorld gw) {
        super(px,py,gw,Player.NOP);
        setX(px);
        setY(py);
        this.setGameWorld(gw);
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
    public void moveTo(float mouseX, float mouseY,boolean sound){

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

            if(ellapsedTime<transitionTime)
                transEffect(batch,ellapsedTime);


    }

    @Override
    public void act(float delta) {

        ellapsedTime += delta;

        if(ellapsedTime > existanceTime){
            destroy();
        }
        if(ellapsedTime > activeTime && !collided){
            getUnitBody().setActive(true);
        }

        collide(collided);

        for (Iterator<Action> iter = this.getActions().iterator(); iter.hasNext(); ) {
            iter.next().act(delta);
        }

    }


    @Override
    public void fired() {

    }

    @Override
    public void transEffect(Batch batch , float ellapsedTime) {


    }

    @Override
    public void collide(boolean collided) {

        if(collided)
            getUnitBody().setActive(false);

    }

    @Override
    public void shatter(boolean condition) {

    }

    @Override
    public void destroy() {

        this.setVisible(false);
        this.setDestroyed(true);
    }


//////////////////////////////////////
    private boolean isCollided() {
        return collided;
    }

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

    protected float getExistanceTime() {
        return existanceTime;
    }

    void setExistanceTime(float existanceTime) {
        this.existanceTime = existanceTime;
    }

    protected float getTransitionTime() {
        return transitionTime;
    }

    void setTransitionTime(float transitionTime) {
        this.transitionTime = transitionTime;
    }
}
