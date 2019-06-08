package com.mygdx.rtsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.buildings.Building;
import com.mygdx.rtsgame.elemnts.buildings.Buildings;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;
import com.mygdx.rtsgame.menus.Maps;

import static java.lang.StrictMath.abs;


public class GameWorld extends Stage implements InputProcessor {


    public Maps currentMap;
    public Player currentPlayer;
    public Buildings buildingInConstruction;
    public  World world;
    private TiledMap gameMap;
    private GameMapRenderer mapRenderer;
    private OrthographicCamera camera ;
    private float HIT_AMPLIFICATION=50;
    private int resorces=1000000;
    public boolean hasUnits = false;
    public static int currentPlayersUnitsCount=0;
    public static int enemyUnitsCount=0;
    private float LAUNCH_POINT = 10f;
    public static GameAssetManager assetManager;
    public GameWorld (Maps map ){

        assetManager = new GameAssetManager();

        currentPlayer = Player.PLAYER1;
        currentMap = map;
        buildingInConstruction = Buildings.NOB;
        world = new World(new Vector2(0,0),false);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameMap = new TmxMapLoader().load(map.path());
        mapRenderer = new GameMapRenderer(gameMap,world);
        camera.update();
        //////////////////////
        world.setContactListener(myContactLisinter());
        ////////debug

        assetManager.load();
        assetManager.manager.finishLoading();

    }


    public void worldRender() {

        for (ArmyUnit u : getArmyUnits()) {

            //////////de-spawn all units that are set destroyed
            if (u.isDestroyed())
                despawn(u);

            ////////OUTSIDE / INSIDE CAMERA RENDERER //////////////////
            if(!(abs(u.getX()-camera.position.x)<  100  +Gdx.graphics.getWidth()/2 &&
                    abs(u.getY()-camera.position.y)<100 + Gdx.graphics.getHeight()/2)){
                ///outside the screen

                u.setVisible(true);   /////dont DRAW when units are outside the screen to save CPU power
                u.setVolume(0.05f);////lower units volume
            }else{
                ///inside the screen

                u.setVisible(false);
                u.setVolume(0.4f);
            }
        }
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public TiledMap getGameMap() {
        return gameMap;
    }
    public void setGameMap(TiledMap gameMap) {
        this.gameMap = gameMap;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Array<ArmyUnit> getArmyUnits() {
        Array<ArmyUnit> tmp = new Array<ArmyUnit>();
        for (Actor a : this.getActors()){
            if(a instanceof ArmyUnit)
                tmp.add((ArmyUnit)a);
        }
        return tmp;
    }
    public Array<Building> getBuildings() {
        Array<Building> tmp = new Array<Building>();

        for (Actor a : this.getActors()){
            if(a instanceof Building)
                tmp.add((Building) a);
        }
        return tmp;
    }


    public void build(Building building){

        this.addActor(building);
        hasUnits=true;
        mapRenderer.addUnitToMapRenderer(building);

        if (building.getPlayerId() == Player.PLAYER1) {
            currentPlayersUnitsCount++;
        } else {
            enemyUnitsCount++;
        }

    }

    public  void spawn(ArmyUnit p){
        this.addActor(p);
        mapRenderer.addUnitToMapRenderer( p);
        hasUnits=true;

        if ( p.getPlayerId() == Player.PLAYER1) {
            currentPlayersUnitsCount++;
        } else {
            enemyUnitsCount++;
        }

    }
    public void despawn(ArmyUnit p){
        p.destroy();
        mapRenderer.removeUnitFromMapRenderer(p);

        if (p.getPlayerId() == Player.PLAYER1) {
            currentPlayersUnitsCount--;
        } else {
            enemyUnitsCount--;
        }
    }

    public void disposeAll(){
        this.dispose();
        assetManager.dispose();
        mapRenderer.dispose();
        gameMap.dispose();

    }
    private ContactListener myContactLisinter() {

        return new ContactListener() {
            @Override
            public void beginContact (Contact contact){
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because

                /////////////////  TWO UNITS COLLIDING
                if (contact.getFixtureA().getDensity() == 10f && contact.getFixtureB().getDensity() == 10f) {
                    //////////////////////////
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    ArmyUnit p1 = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());

                    p.pump(p1);
                    p1.pump(p);


                    if(p.prevMove!=null&&p.isMoving())
                        p.moveTo(p.prevMove.getX(),p.prevMove.getY(),false);
                    if(p1.prevMove!=null&& p1.isMoving())
                        p1.moveTo(p1.prevMove.getX(),p1.prevMove.getY(),false);


                }
                ///////////////////////COLLIDING WITH MAP OBJECT
                if (contact.getFixtureA().getDensity() == 20000f) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());
                    p.moveTo(p.getX() - 10, p.getY() - 10, true);
                    p.moveTo(p.getX() - 1, p.getY() - 1, true);
                }
                if (contact.getFixtureB().getDensity() == 20000f) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    p.moveTo(p.getX() - 10, p.getY() - 10, true);
                    p.moveTo(p.getX() - 1, p.getY() - 1, true);
                }
                ///////////////////COLLIDING WITH BUILDING
                if (contact.getFixtureA().getDensity() == 1000f) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());
                    p.moveTo(p.getX() - 10, p.getY() - 10, true);
                    p.moveTo(p.getX() - 1, p.getY() - 1, true);
                }
                if (contact.getFixtureB().getDensity() == 1000f) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    p.moveTo(p.getX() - 10, p.getY() - 10, true);
                    p.moveTo(p.getX() - 1, p.getY() - 1, true);
                }
            }
            @Override
            public void endContact (Contact contact){
                //System.out.println("contact " );
            }
            @Override
            public void preSolve (Contact contact, Manifold oldManifold){
                /////////////////////////////// BULLET vs ARMY_UNITS /////////////////////

                ///contact between bullet and a armyUnit (10) or building (1000)
                if (contact.getFixtureA().getDensity() == 1000f && contact.getFixtureB().getDensity() == 999f ||
                        contact.getFixtureA().getDensity() == 10f && contact.getFixtureB().getDensity() == 999f) {//contact.setFriction(222f);

                    ArmyUnit enemy = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    Bullet b = (Bullet) (contact.getFixtureB().getBody().getUserData());
                    //b.setVisible(false);
                    //b.setDestroyed(true);
                    b.setCollided(true);
                    enemy.getDamage(b.damage);

                }

                ////////////////// MAP OBJECT vs BULLET   OR  between  BULLET vs BULLET

                if (contact.getFixtureA().getDensity() == 999f && contact.getFixtureB().getDensity() == 20000f){
                    Bullet b = (Bullet) (contact.getFixtureA().getBody().getUserData());
                    ///b1.setVisible(false);
                    //b.setDestroyed(true);
                    b.setCollided(true);

                }
                if (contact.getFixtureA().getDensity() == 20000f && contact.getFixtureB().getDensity() == 999f){
                    Bullet b = (Bullet) (contact.getFixtureB().getBody().getUserData());
                    //b1.setVisible(false);
                     //b.setDestroyed(true);
                     b.setCollided(true);
                }

                if( contact.getFixtureA().getDensity() == 999f && contact.getFixtureB().getDensity() == 999f) {//contact.setFriction(222f);
                    Bullet b1 = (Bullet) (contact.getFixtureA().getBody().getUserData());
                    Bullet b2 = (Bullet) (contact.getFixtureB().getBody().getUserData());
                    b1.setVisible(false);
                    b1.setDestroyed(true);
                    b2.setVisible(false);
                    b2.setDestroyed(true);
                    }

                }



            @Override
            public void postSolve (Contact contact, ContactImpulse impulse){
                //System.out.println("contact " );
                ///////Bullet collision handler


            }
        };
    }
















    ////////////////////


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Buildings getBuildingInConstruction() {
        return buildingInConstruction;
    }

    public void setBuildingInConstruction(Buildings buildingInConstruction) {
        this.buildingInConstruction = buildingInConstruction;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getResorces() {
        return resorces;
    }

    public void setResorces(int resorces) {
        this.resorces = resorces;
    }

    public  int getCurrentPlayersUnitsCount() {
        return currentPlayersUnitsCount;
    }

    public  void setCurrentPlayersUnitsCount(int currentPlayersUnitsCount) {
        GameWorld.currentPlayersUnitsCount = currentPlayersUnitsCount;
    }

    public  int getEnemyUnitsCount() {
        return enemyUnitsCount;
    }

    public  void setEnemyUnitsCount(int enemyUnitsCount) {
        GameWorld.enemyUnitsCount = enemyUnitsCount;
    }

    public GameMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(GameMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }


}
