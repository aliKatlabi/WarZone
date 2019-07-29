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
import com.badlogic.gdx.utils.SerializationException;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.buildings.Building;
import com.mygdx.rtsgame.elemnts.buildings.Buildings;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;
import com.mygdx.rtsgame.elemnts.units.Directions;
import com.mygdx.rtsgame.menus.Maps;
import static java.lang.StrictMath.abs;


public class GameWorld extends Stage implements InputProcessor {


    public Maps currentMap;

    public Buildings buildingInConstruction;
    public  World world;
    private TiledMap gameMap;
    private GameMapRenderer mapRenderer;
    private OrthographicCamera camera ;
    private int resources=1000000;
    public boolean hasUnits = false;

    public         Player     currentPlayer = Player.PLAYER1;
    public   static      int        currentPlayersUnitsCount=0;
    public   static      int        enemyUnitsCount=0;
    private  static      float      RANDOM_POS =5.5f;
    private  static      GameWorld  SINGLE_INS = null;

    private GameWorld (){

    }


    public static GameWorld getInstance()
    {

        if(SINGLE_INS == null)
        {
            SINGLE_INS=new GameWorld();
        }

        return SINGLE_INS;
    }

    public void loadMap(Maps map) throws SerializationException
    {

        if(SINGLE_INS != null)
        {
            world = new World(new Vector2(0,0),false);
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            currentMap = map;
            gameMap = new TmxMapLoader().load(map.path());
            //gameMap = GameWorld.assetManager.manager.get(GameAssetManager.maps);

            camera.update();
            world.setContactListener(SINGLE_INS.myContactListener());


            mapRenderer = new GameMapRenderer(SINGLE_INS.gameMap, SINGLE_INS.world);


        }
    }

    public void worldRender() {

        for (ArmyUnit u : getArmyUnits()) {

            //////////de-spawn all units that are set destroyed
            if (!u.isSpawned()&&u.isDestroyed())
                despawn(u);

            ////////OUTSIDE / INSIDE CAMERA RENDERER ///////////////


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


    public void build(Building building)
    {
        this.addActor(building);
        hasUnits=true;
        mapRenderer.addUnitToMapRenderer(building);

        building.getPlayerId().plus();
        /*
        if (building.getPlayerId() == currentPlayer) {
            currentPlayersUnitsCount++;
            currentPlayer.plus();
        } else {
            enemyUnitsCount++;
        }
        */

    }

    public void spawn(ArmyUnit p){


        this.addActor(p);
        mapRenderer.addUnitToMapRenderer(p);
        hasUnits = true;

        p.getPlayerId().plus();
        /*
        if (p.getPlayerId() == Player.PLAYER1) {
            if(currentPlayersUnitsCount++>0)
                hasUnits=true;

        }else{
            if(enemyUnitsCount++>0)
                hasUnits=true;
        }
        */
    }

    private void despawn(ArmyUnit p){
        p.destroy();
        mapRenderer.removeUnitFromMapRenderer(p);

        p.getPlayerId().minus();
        /*
        if (p.getPlayerId() == Player.PLAYER1) {
            if(currentPlayersUnitsCount--==0)
                hasUnits=false;
        } else {
            if(enemyUnitsCount--==0)
                hasUnits=false;
        }*/
    }

    public void dispose(){

        if(SINGLE_INS!=null)
        {
            for (ArmyUnit u : getArmyUnits())
                u.dispose();

            getArmyUnits().clear();
            GameAssetManager.getInstance().manager.clear();
            mapRenderer.dispose();
            gameMap.dispose();
        }

    }

    private float getRandom(float random){
        return random*(float)Math.random();
    }

    private ContactListener myContactListener() {

        return new ContactListener() {
            @Override
            public void beginContact (Contact contact){
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because

                /////////////////  TWO UNITS COLLIDING
                if (contact.getFixtureA().getDensity() == ArmyUnit.Density.armyU.density && contact.getFixtureB().getDensity() == ArmyUnit.Density.armyU.density) {
                    //////////////////////////
                    ArmyUnit p1 = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    ArmyUnit p2 = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());

                    Vector2 p1v = new Vector2(p1.getX(),p1.getY());
                    Vector2 p2v = new Vector2(p2.getX(),p2.getY());

                    p1.pump(p2);
                    p2.pump(p1);

                    Directions d;

                    if(p1.prevMove!=null&&p1.isMoving() ){


                        Vector2 pm = new Vector2(p1.prevMove.getX(),p1.prevMove.getY());
                        d = Directions.translate(p1v,pm);

                        p1.moveTo(p1.prevMove.getX() + (getRandom(RANDOM_POS))*d.X,
                                  p1.prevMove.getY() + (getRandom(RANDOM_POS))*d.Y,
                                false);
                    }
                    if(p2.prevMove!=null&& p2.isMoving()){

                        Vector2 p2m = new Vector2(p2.prevMove.getX(),p2.prevMove.getY());
                        d = Directions.translate(p2v,p2m);

                        p2.moveTo(p2.prevMove.getX() + (getRandom(RANDOM_POS))*d.X,
                                  p2.prevMove.getY() + (getRandom(RANDOM_POS))*d.Y,
                                false);
                    }


                }
                ///////////////////////COLLIDING WITH MAP OBJECT
                if (contact.getFixtureA().getDensity() == 20000f  && contact.getFixtureB().getDensity() == ArmyUnit.Density.armyU.density) {

                    ArmyUnit p = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());

                    Vector2 pv = new Vector2(p.getX()+p.getHeight()/2,p.getY()+p.getHeight()/2);
                    Vector2 pm = new Vector2();
                    if(p.prevMove!=null)
                        pm.set(p.prevMove.getX(),p.prevMove.getY());
                    Directions d = Directions.translate(pv,pm);
                    p.moveTo(p.getX() + d.X*10, p.getY()+ d.Y*10, false);

                    p.moveTo(p.getX() - 1, p.getY() - 1, false);
                }
                if (contact.getFixtureB().getDensity() == 20000f  && contact.getFixtureA().getDensity() == ArmyUnit.Density.armyU.density) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());

                    Vector2 pv = new Vector2(p.getX()+p.getHeight()/2,p.getY()+p.getHeight()/2);
                    Vector2 pm = new Vector2();
                    if(p.prevMove!=null)
                        pm.set(p.prevMove.getX(),p.prevMove.getY());
                    Directions d = Directions.translate(pv,pm);
                    p.moveTo(p.getX() + d.X*20, p.getY()+ d.Y*20, false);

                    p.moveTo(p.getX() - 1, p.getY() - 1, false);
                }
                ///////////////////COLLIDING WITH BUILDING
                if (contact.getFixtureA().getDensity() == 1000f  && contact.getFixtureB().getDensity() == ArmyUnit.Density.armyU.density) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureB().getBody().getUserData());

                    Vector2 pv = new Vector2(p.getX()+p.getHeight()/2,p.getY()+p.getHeight()/2);
                    Vector2 pm = new Vector2();
                    if(p.prevMove!=null)
                       pm.set(p.prevMove.getX(),p.prevMove.getY());

                    Directions d = Directions.translate(pv,pm);
                    p.moveTo(p.getX() + d.X*20, p.getY()+ d.Y*20, false);
                }
                if (contact.getFixtureB().getDensity() == 1000f  && contact.getFixtureA().getDensity() == ArmyUnit.Density.armyU.density) {
                    ArmyUnit p = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());

                    Vector2 pv = new Vector2(p.getX()+p.getHeight()/2,p.getY()+p.getHeight()/2);
                    Vector2 pm = new Vector2();
                    if(p.prevMove!=null)
                        pm.set(p.prevMove.getX(),p.prevMove.getY());
                    Directions d = Directions.translate(pv,pm);
                    p.moveTo(p.getX() + d.X*20, p.getY()+ d.Y*20, false);
                    p.moveTo(p.getX() - 1, p.getY() - 1, false);
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
                        contact.getFixtureA().getDensity() == ArmyUnit.Density.armyU.density && contact.getFixtureB().getDensity() == 999f) {//contact.setFriction(222f);

                    ArmyUnit enemy = (ArmyUnit) (contact.getFixtureA().getBody().getUserData());
                    Bullet b = (Bullet) (contact.getFixtureB().getBody().getUserData());
                    b.setVisible(false);
                    //b.setDestroyed(true);
                    b.setCollided(true);
                    enemy.getDamage(b.damage);

                }

                ////////////////// MAP OBJECT vs BULLET   OR  between  BULLET vs BULLET

                if (contact.getFixtureA().getDensity() == 999f && contact.getFixtureB().getDensity() == 20000f){
                    Bullet b = (Bullet) (contact.getFixtureA().getBody().getUserData());
                    b.setVisible(false);
                    //b.setDestroyed(true);
                    b.setCollided(true);

                }
                if (contact.getFixtureA().getDensity() == 20000f && contact.getFixtureB().getDensity() == 999f){
                    Bullet b = (Bullet) (contact.getFixtureB().getBody().getUserData());
                     b.setVisible(false);
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


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setBuildingInConstruction(Buildings buildingInConstruction) { this.buildingInConstruction = buildingInConstruction; }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    /*
    public  int getCurrentPlayersUnitsCount() {
        return currentPlayersUnitsCount;
    }

    public  void setCurrentPlayersUnitsCount(int currentPlayersUnitsCount) { GameWorld.currentPlayersUnitsCount = currentPlayersUnitsCount; }

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public World getWorld() { return world; }

    public Buildings getBuildingInConstruction() {
        return buildingInConstruction;
    }

    public TiledMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(TiledMap gameMap) {
        this.gameMap = gameMap;
    }

*/


}
