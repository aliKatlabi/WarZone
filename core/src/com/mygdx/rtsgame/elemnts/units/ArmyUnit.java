package com.mygdx.rtsgame.elemnts.units;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import static com.mygdx.rtsgame.elemnts.units.Directions.translate;
import static java.lang.Math.*;



public abstract class ArmyUnit extends Actor implements ArmyUnitTool {


    Texture unitTexture;
    Sound shootingSound;
    Sound destroyedSound;
    Sound movingSound;

    public Player playerId ;
    private Body unitBody;
    private GameWorld gameWorld = GameWorld.getInstance();
    private Bullet bullet ;
    private ShapeRenderer shapeRenderer;

    private boolean spawned      = false;
    private boolean destroyed    = false;
    private boolean selected     = false;
    private boolean moving       = false;
    private boolean shoot        = false;

    private float elapsedTime=0;
    private float spawnTime ;
    private float destructionPhase=0f;

    private     float    range ;
    private     float    hp;
    private     float    fireRate=0.5f;
    private     float    moveSpeed;
    private     float    volume=0.4f;
    protected   float    scale=1;
    private     float    mouseX , mouseY;
    private     float    bodyRadius ;

    private     ArmyUnit         enemy;
    public      MoveToAction     prevMove;
    private     HealthBar        healthBar;
    private     VisualShape      visual;


    private static final float  SHOOTING_RANGE     = 80f;
    private static final float  DESTRUCTION_TIME   = 0.7f;
    private static final float  DEVIATION          = 15f;


    public ArmyUnit(float px, float py, Player playerId) {
        //ArmyUnit to Unit or ArmyUnit
        setX(px);
        setY(py);
        this.playerId  = playerId;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        setDebug(false);
        setBodyRadius(25f);
        setVisible(true);

        healthBar = new HealthBar(shapeRenderer,32f);
        visual = new VisualShape(shapeRenderer);

    }

    protected ArmyUnit() {  }

    @Override
    public void draw(Batch batch, float alpha) {

        if (spawned) {

            batch.setProjectionMatrix(gameWorld.getCamera().combined);
            shapeRenderer.setProjectionMatrix(gameWorld.getCamera().combined);

            shapeRenderer.end();
            shapeRenderer.begin();

            batch.end();
            batch.begin();


            //visual.visual5(getX()-5,getY()-5,getWidth()+10,0, VisualShape.VisualType.PENTAGON);
            if (selected) {

                visual.draw(this.getX() + unitTexture.getWidth() / 2f, this.getY() + unitTexture.getHeight() / 2f,25);
            }

            batch.draw(unitTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                    this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                    unitTexture.getWidth(), unitTexture.getHeight(), false, false);

            unitBody.setTransform(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, 0);


            healthBar.draw(getX() -3f, getY() + getHeight() + 10f);

            if(isDestroyed())
            {
                visual.explosion(getX()+getWidth()/2,getY()+getHeight()/2,getX(),getY());
                setShoot(false);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        elapsedTime += delta;

        if(elapsedTime > spawnTime) {///if the army unit exist

            setSpawned(true);//set that it is spawned

            /////////handle moving and in position vertical movement
            if (getX() == mouseX && getY() == mouseY)
            {
                moving = false;
            }

            if (!moving) {
                if (elapsedTime % 3 < 1)
                    staticMove(getX(), getY() + 10);
                if (elapsedTime % 3 > 1)
                    staticMove(getX(), getY() - 10);
            }
        }
        //////enemy state

        if (enemy != null) {
            if (!enemy.isDestroyed())
                shoot();
            else {
                setShoot(false);
                enemy = null;
            }
        }
        ////////////this state

        if (this.isDestroyed()) {
            destructionPhase += delta;
        }


        if(destructionPhase>DESTRUCTION_TIME) {
            setSpawned(false);

            if (destroyedSound != null)
                destroyedSound.play(volume);
        }

    }



    public void moveTo(float mouseX, float mouseY, boolean sound) {

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        moving = true;

        //if (moving){

        setBounds(this.getX(), this.getY(),
                 unitTexture.getWidth() * scale,
                unitTexture.getHeight() * scale);

        MoveToAction move = new MoveToAction();
        move.setPosition(mouseX, mouseY);
        move.setDuration(moveSpeed);
        this.addAction(move);
        prevMove = move;

        if (sound && movingSound != null)
            movingSound.play(volume);

        //}
    }

    public void pump(ArmyUnit u ){

        Vector2 v1 = new Vector2(this.getX(),this.getY());
        Vector2 v2 = new Vector2(u.getX(),u.getY());

        Vector2 path = v2.sub(v1);
        path.nor();

        if(prevMove!=null) {
            prevMove.setTime(0f);
            prevMove.setStartPosition(getX(),getY());
        }

        //prevMove.setDuration(prevMove.getTime());
        setBounds(this.getX(), this.getY(), unitTexture.getWidth() * scale, unitTexture.getHeight() * scale);
        MoveToAction move = new MoveToAction();
        move.setPosition(getX() - 1000*translate(this, u).X, getY() - 1000*translate(this, u).Y);
        //move.setPosition(10000*path.x, 10000*path.y);
        move.setDuration(3f);
        this.addAction(move);

    }

    private void staticMove(float X, float Y) {

        MoveToAction staticMove = new MoveToAction();
        staticMove.setPosition(X, Y);
        staticMove.setDuration(moveSpeed);
        this.addAction(staticMove);

    }

    public void attack(ArmyUnit e) {

        enemy = e;

        moveTo(e.getX() - SHOOTING_RANGE *translate(this,enemy).X ,
                e.getY()- SHOOTING_RANGE *translate(this,enemy).Y , true);

        setShoot(true);
    }

    private void shoot() {

        if (shoot&&inRange()) {

            if(elapsedTime>fireRate){


                bullet = loadBullet( getX()+getWidth()/2, getY()+getHeight()/2);

                gameWorld.spawn(bullet);

                bullet.moveTo(enemy.getX() + enemy.getWidth() / 2, enemy.getY() + enemy.getHeight() / 2, false);

                elapsedTime = 0;

                if (shootingSound != null)
                    shootingSound.play(volume);
            }
        }
    }

    private boolean inRange() {

        if(enemy!=null){
            Vector2 me   = new Vector2(this.getX(),this.getY());
            Vector2 him  = new Vector2(enemy.getX(),enemy.getY());

            if(translate(this,enemy)== Directions.UR||translate(this,enemy)==Directions.UL)
                return  me.dst2(him)< range;
            else
                return me.dst2(him)< 3*range;
        }else{
            return false;
        }
    }

    public void getDamage(float damage) {
        healthBar.update(damage,hp);
        hp-=damage;
        if(hp<=0)
            setDestroyed(true);//remember there is a loop that removes every destroyed item

    }

    public void destroy() {
        gameWorld.world.destroyBody(unitBody);
        this.addAction(Actions.removeActor());

    }

    @Override
    public Bullet loadBullet(float x, float y) {
        return null;
    }

    @Override
    public  boolean hit(Vector3 clickPos){

        return  Math.hypot(abs(clickPos.x  - (DEVIATION + getX() - getWidth()/2))
                ,abs(clickPos.y - (DEVIATION + getY()+getHeight()/2)))<getBodyRadius();

    }


    @Override
    public Body creatBody(BodyDef.BodyType type ){

        Body body ;
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = type;
        bodyDef.position.set(this.getX(), this.getY());
        body = getGameWorld().world.createBody(bodyDef);
        return body;
    }
    @Override
    public void installCircularBody(Body body,int density,float radius,boolean multiBody){
        int num;
        if(multiBody)
            num=5;
        else
            num=4;

        for(int i=4;i<=num;i++) {

            FixtureDef fixtureDef = new FixtureDef();
            CircleShape css = new CircleShape();
            body.setUserData(this);
            Vector2 vp = new Vector2(body.getLocalVector(new Vector2()));
            css.setPosition(vp);
            fixtureDef.density = density; //10
            css.setRadius(4*radius/i); //this.getWidth() /2 - 3
            fixtureDef.shape = css;
            body.createFixture(fixtureDef);
            float centerX = this.getX() + this.getWidth() / 2;
            float centerY = this.getY() + this.getHeight() / 2;
            body.setTransform(centerX, centerY, 0);
            css.dispose();
        }

    }

    public void dispose(){

       shapeRenderer.dispose();


    }

    /*.........SETTERS / GETTERS.........*/


    protected Body getUnitBody() {
        return unitBody;
    }

    protected void setUnitBody(Body unitBody) {
        this.unitBody = unitBody;
    }

    void setRange(float range) {
        this.range = range;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    float getHp() {
        return hp;
    }

    protected void setHp(float hp) {
        this.hp = hp;
    }

    void setShootingSpeed(float shootingSpeed) {
        this.fireRate = shootingSpeed;
    }

    public Player getPlayerId() {
        return playerId;
    }

    protected void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    public boolean isSpawned() {
        return spawned;
    }

    protected void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    protected float getBodyRadius() {
        return bodyRadius;
    }

    protected void setBodyRadius(float bodyRadius) {
        this.bodyRadius = bodyRadius;
    }

    public boolean isMoving() {
        return moving;
    }


    /*
    private Sound getShootingSound() {
        return shootingSound;
    }

    private void setShootingSound(Sound shootingSound) { this.shootingSound = shootingSound; }

    protected ArmyUnit getEnemy() {
        return enemy;
    }

    void setEnemy(ArmyUnit enemy) {
        this.enemy = enemy;
    }

    protected void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    protected float getSpawnTime() {
        return spawnTime; }

    protected float getEllapsedTime() {
        return elapsedTime;
    }

    protected void setEllapsedTime(float ellapsedTime) {
        this.elapsedTime = ellapsedTime;
    }

    private float getShootingSpeed() {
        return fireRate;
    }

    public boolean isMovement() {
        return moving;
    }

    void setMovement(boolean movement) {
        this.moving = movement;
    }

    boolean isShoot() {
        return shoot;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public void setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    public float getRange() {
        return range;
    }
*/

}


