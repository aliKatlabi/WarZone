package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;
import com.mygdx.rtsgame.elemnts.units.HealthBar;

import static java.lang.Math.abs;

public abstract class Building extends ArmyUnit {

    private Texture     inConstructTexture = new Texture(Gdx.files.internal("buildings/orange defense base.png"));
    private Texture     texture=new Texture(Gdx.files.internal("buildings/manufactory02.png"));
    private Sound       destroyedSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/unitExpolded.mp3"));

    private float hp ;
    private Player playerId;
    private float elapsedTime=0;
    private float constructionTime;
    private boolean destroyed = false;
    private boolean constructed=false;
    private static final float DEVIATION = 50f;

    private GameWorld       gameWorld=GameWorld.getInstance();
    private Body            BuildingBody;
    private ShapeRenderer   shapeRenderer;
    private HealthBar       healthBar;

    Building(float px, float py, Player pid){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        playerId =pid;
        scale = 0.28f;
        setHeight(getHeight()*scale);
        setWidth(getWidth()*scale);
        setBounds(px,py,texture.getWidth()*scale,texture.getHeight()*scale);
        setBodyRadius(50f);
        setBuildingBody(creatBody(BodyDef.BodyType.StaticBody));
        installCircularBody(getBuildingBody(),1000,this.getWidth() /4 ,true);

        healthBar =new HealthBar(shapeRenderer,96f);
    }


    @Override
    public void destroy(){
        gameWorld.world.destroyBody(BuildingBody);
        this.addAction(Actions.removeActor());
    }

    @Override
    public  void getDamage(float damage){

       // healthBarWidth=healthBarWidth - d*(healthBarWidth/hp);
        healthBar.update(damage,hp);
        hp-=damage;

        if(hp<=0) {
            setDestroyed(true);
            setSpawned(false);
            destroyedSound.play(0.45f);
        }
    }
    @Override
    public boolean hit(Vector3 clickPos){

        return  Math.hypot(abs(clickPos.x  - (3*DEVIATION + getX() - getWidth()/2))
                ,abs(clickPos.y - (DEVIATION/3 + getY()+getHeight()/2)))<getBodyRadius();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

            shapeRenderer.setProjectionMatrix(gameWorld.getCamera().combined);
            batch.setProjectionMatrix(gameWorld.getCamera().combined);

            shapeRenderer.end();
            shapeRenderer.begin();

            healthBar.draw(getX() + getWidth()/3, getY() + getHeight());

            batch.end();
            batch.begin();

            if (elapsedTime < constructionTime) {
                batch.draw(getInConstructTexture(), this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                        this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                        getInConstructTexture().getWidth(), getInConstructTexture().getHeight(), false, false);
            }
            if (elapsedTime >= constructionTime) {
                constructed = true;
                batch.draw(getTexture(), this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                        this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                        getTexture().getWidth(), getTexture().getHeight(), false, false);
            }

    }

    @Override
    public void act(float delta) {

        elapsedTime+=delta;

        for (Action a:getActions()) {
            a.act(delta);
        }
    }
/*
    @Override
    public Actor hit(float x, float y, boolean touchable){

        if (touchable && getTouchable() != Touchable.enabled) return null;

        return (x >= 0 && x < texture.getWidth() && y >= 0 && y < texture.getHeight() ? this : null);
    }
*/
   /////////////////////

    public void dispose(){
        destroyedSound.dispose();
    }
   //////////////setters & getters


    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Player getPlayerId() {
        return playerId;
    }

    private Texture getInConstructTexture() {
        return inConstructTexture;
    }

    void setInConstructTexture(Texture inConstructTexture) {
        this.inConstructTexture = inConstructTexture;
    }


    void setConstructionTime(float constructionTime) {
        this.constructionTime = constructionTime;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }


    private Body getBuildingBody() {
        return BuildingBody;
    }

    private void setBuildingBody(Body buildingBody) {
        BuildingBody = buildingBody;
    }

    public boolean isConstructed() {
        return constructed;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    /*
    public Sound getDestroyedSound() {
        return destroyedSound;
    }

    public void setDestroyedSound(Sound destroyedSound) {
        this.destroyedSound = destroyedSound;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    public int getPrice() {
        return price;
    }

    public float getEllapsedTime() {
        return ellapsedTime;
    }


    public void setEllapsedTime(float ellapsedTime) {
        this.ellapsedTime = ellapsedTime;
    }

    public float getConstructionTime() {
        return constructionTime;
    }

    public void setConstructed(boolean constructed) {
        this.constructed = constructed;
    }
*/
}
