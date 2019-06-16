package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.buildings.*;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;
import com.mygdx.rtsgame.elemnts.units.Tank;
import com.mygdx.rtsgame.menus.*;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;
import com.mygdx.rtsgame.RTSGame;
import java.util.ArrayList;
import static java.lang.Math.abs;


public class GameScreen extends Stage implements Screen , InputProcessor {

    private final RTSGame game;

    private ControlMenu controlMenu;
    private EscapeMenu escMenu;
    private ShapeRenderer shr;
    private int mouseMoveX,mouseMoveY;
    private float resizew=0,resizeh=0;
    private final float SELECTION_MARGIN=4;
    private final float MARGIN = 50f;
    private Vector3  upPositionV , downPositionV , mouseMouvePositionV ,dragPositionV;
    private ArrayList<ArmyUnit> selectedUnits;
    private Box2DDebugRenderer debugRenderer;
    private float volume = 0.2f;
    public static boolean TAB=false;
    public static boolean ESC=false;
    private ControlStage controlStage;
    Rectangle rect;
    ///for testing
    private  float resizewV=0,resizehV=0;
    private EndGameDialog endGameDialog;

    private MultiSelect<ArmyUnit> multiSelect;

    private GameWorld gameWorld       = GameWorld.getInstance();
    private Sound     gameSong        = GameAssetManager.getInstance().manager.get(GameAssetManager.gameSound);
    private Skin      skin            = GameAssetManager.getInstance().manager.get(GameAssetManager.composerSkin);

    GameScreen(final RTSGame game) {


        multiSelect = new SelectionComponent<ArmyUnit>();

        selectedUnits = new ArrayList<ArmyUnit>();
        this.game = game;

        //gameWorld.loadMap(map);

        controlStage = new ControlStage(gameWorld);
        controlMenu = new ControlMenu(gameWorld,this);
        escMenu = new EscapeMenu(gameWorld,this);
        endGameDialog = new EndGameDialog("war zone",skin,this);

        controlStage.addActor(controlMenu);
        controlStage.addActor(escMenu);
        //controlStage.addActor(endGameDialog);

        shr = new ShapeRenderer();
        shr.setColor(Color.GREEN);
        gameSong.loop(volume);
        ////////////////
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(controlStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        ///
        rect = new Rectangle();

        ////////////////physics--Box2d----

        debugRenderer = new Box2DDebugRenderer();


        ///// simulation

        simulateEnemy();

     }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        gameWorld.world.step(1/60f, 6, 2);
        gameWorld.act(delta);
        gameWorld.draw();
        gameWorld.worldRender(); // gameWorld.worldRender() show go after gameWorld.draw()
        controlStage.act(delta);
        controlStage.draw();    //controlStage.draw() GO after all draw methods
        controlMenu.updateState();
       // endGameDialog.updateState();

         /////rendering selection rectangle
        shr.begin(ShapeRenderer.ShapeType.Line);
        shr.rect(mouseMoveX, Gdx.graphics.getHeight() - mouseMoveY, resizew, resizeh);
//        shr.rect(mouseMouvePositionV.x,Gdx.graphics.getHeight()-mouseMouvePositionV.y,resizewV,resizehV);
        shr.end();


        rect.set(mouseMoveX, Gdx.graphics.getHeight() - mouseMoveY, resizew, resizeh);


            ///////////////

            ////////////////////////TO SEE COLLISION EDGES

        //debugRenderer.render(gameWorld.world, gameWorld.getCamera().combined);

        if(!TAB) {
            //Gdx.input.setInputProcessor(this);
            controlMenu.setVisible(false);

        }else{
            // Gdx.input.setInputProcessor(controlStage);
            controlMenu.setVisible(true);

        }
        if(!ESC) {
            //Gdx.input.setInputProcessor(this);
            escMenu.setVisible(false);
        }else{
            // Gdx.input.setInputProcessor(controlStage);
            escMenu.setVisible(true);

        }

    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
	public void dispose () {

        System.out.println("dis1");

        selectedUnits.clear();
        gameWorld.dispose();
        shr.dispose();
        gameSong.dispose();
        skin.dispose();


    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(button == Input.Buttons.RIGHT){
            clearSelection();
        }
        ///////////////////////............ fitting touchDown coordinates to camera coordinates
        //Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        downPositionV = gameWorld.getCamera().unproject(new Vector3(screenX,screenY,0));
        /////////////debug/////////////////\\\\\\\\\\\\\\\\\\\\\\\
        //System.out.println("screen x:"+screenX+"y:"+screenY);

        /////////////
        ArrayList<ArmyUnit> notSelectedUnits = new ArrayList<ArmyUnit>();
        int count=0;
        if(!selectedUnits.isEmpty()) {
            ////moving and attacking

            System.out.println(gameWorld.getArmyUnits().size);

            //////////////touchDown click if no number of all armyUnits equal the size of the army units array in the gameWorld
            ////if not selected add it to not selected
            ////count the number outside the touchdown
            ////if the count == the number of al units Means there are no units clicked

            ///THIS MEANS the touchDown is meant to be for moving selected units

            for (ArmyUnit nu : gameWorld.getArmyUnits()){
                if (!nu.isSelected()) {
                    notSelectedUnits.add(nu);
                }
                if (!nu.hit(downPositionV)){
                    count++;
                }
                if( count == gameWorld.getArmyUnits().size) {
                    for (ArmyUnit su : selectedUnits){
                        (su).moveTo(downPositionV.x, downPositionV.y,true);
                    }
                }
            }
            ///if the touchDown hits a not selected (enemy) unit
            ///the selected unit OR units should attack it

            for(ArmyUnit u:notSelectedUnits){
                //insideUnitBoundries(u,downPositionV)
                if (u.hit(downPositionV) && u.playerId != gameWorld.currentPlayer) {
                    System.out.println("inside");
                    for (ArmyUnit su : selectedUnits) {
                        su.attack(u);
                        System.out.println("attack");
                    }
                    break;
                }
            }
            notSelectedUnits.clear();
            //clearSelection();
        }else{
            /////////////////Building

            if(gameWorld.buildingInConstruction== Buildings.WARFACTORY){
                if(gameWorld.getResorces()> Buildings.WARFACTORY.price()) {
                    gameWorld.setResorces(gameWorld.getResorces() - Buildings.WARFACTORY.price());
                    Building newWarFactory = new WarFactory(downPositionV.x - MARGIN, downPositionV.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(newWarFactory);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }
            }
            if(gameWorld.buildingInConstruction== Buildings.BARRAKS){
                if(gameWorld.getResorces()> Buildings.BARRAKS.price()) {
                    gameWorld.setResorces(gameWorld.getResorces() - Buildings.BARRAKS.price());
                    Building barrck = new Barrack(downPositionV.x - MARGIN, downPositionV.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(barrck);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }

            }
            if(gameWorld.buildingInConstruction== Buildings.RESOURSEFACTORY){
                if(gameWorld.getResorces()> Buildings.RESOURSEFACTORY.price()) {
                    gameWorld.setResorces(gameWorld.getResorces() - Buildings.RESOURSEFACTORY.price());
                    Building newResourceFactory = new ResourceFactory(downPositionV.x - MARGIN, downPositionV.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(newResourceFactory);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }
            }
            if(gameWorld.buildingInConstruction== Buildings.WALL){
                if(gameWorld.getResorces()> Buildings.WALL.price()) {
                    gameWorld.setResorces(gameWorld.getResorces() - Buildings.WALL.price());
                    Building newWall = new Wall(downPositionV.x - MARGIN, downPositionV.y - MARGIN,gameWorld.currentPlayer);
                    gameWorld.build(newWall);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        /////////////////////////////.......fitting touchDown coordinates to camera coordinates
        //Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        upPositionV = gameWorld.getCamera().unproject(new Vector3(screenX, screenY, 0));


        if(button == Input.Buttons.RIGHT){
            clearSelection(); }

        ////////////////////////// SINGLE SELECT /////////////////////////////////

        for (ArmyUnit u : gameWorld.getArmyUnits()) {
            if (!(u instanceof Building)) {

                //..

                //..
                if (u.getPlayerId()== gameWorld.currentPlayer ) { //select units with the player id of this game world
                    //insideUnitBoundries(u, upPositionV , u.scale )
                    if (u.hit(upPositionV)) {
                        if(u.playerId == gameWorld.currentPlayer) {
                            if(clearSelection()) {
                                selectedUnits.add(u);
                                u.setSelected(true);
                            }
                        }
                    }
                    ////////////////////////..... MULTI SELECT .... selection rectangle /////////////////////////
                    if (abs(resizew) > 37f && abs(resizeh) > 37f) { ////if the selection rectangle is big enough
                        if (insideSelectionRectangle(u)) {///////check if there are units inside the rect
                            u.setSelected(true);
                            selectedUnits.add(u);
                        }
                    }
                }
            }else{
                //for selecting buildings
            }
        }

        resizew=0;resizeh=0;//resetting the rectangle

        multiSelect.reset();
        return true;

    }




    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

	    /////////rectangle parameters
        resizew = screenX    - mouseMoveX;
        resizeh = mouseMoveY    - screenY;
        ///////////////////////////////////
        dragPositionV = gameWorld.getCamera().unproject(new Vector3(screenX,screenY,0));

        resizewV= dragPositionV.x - mouseMouvePositionV.x;
        resizehV=mouseMouvePositionV.y - dragPositionV.y;



        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY){

        mouseMoveX = screenX;
        mouseMoveY = screenY;

       // Vector3 vec = new Vector3(mouseMoveX,mouseMoveY,0);
        mouseMouvePositionV = gameWorld.getCamera().unproject(new Vector3(screenX,screenY,0));

        //float camPosx = gameWorld.getCamera().position.x;
        //float camPosy = gameWorld.getCamera().position.y;
        //float w = getWidth();
        //float h = getHeight();
        ////dealing with bounds

        //boolean bound = camPosx  < (5*w/4) - 100 && camPosx > w/2 && camPosy > h/2 && camPosy < (3*h/2) + 40 ;

            if (mouseMoveX > Gdx.graphics.getWidth() - 30) {//to move right
                gameWorld.getCamera().translate(10, 0);
            }
            if (mouseMoveX < 30) {//to move left
                gameWorld.getCamera().translate(-10, 0);
            }

            if (mouseMoveY > Gdx.graphics.getHeight() - 30) {//to move down
                gameWorld.getCamera().translate(0, -10);
            }
            if (mouseMoveY < 30) {//to move up
                gameWorld.getCamera().translate(0, 10);
            }

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private boolean insideSelectionRectangle(Actor u){

        float x = u.getX();
        float y = u.getY();

        getCamera().project(new Vector3(x,y,0));

        if(rect.contains(x,y)){
            System.out.println("inside new triangle");
        }


        ///upPosition.y downPosition.y
        boolean left     = downPositionV.x > upPositionV.x;
        boolean right    = downPositionV.x < upPositionV.x;
        boolean down     = downPositionV.y > upPositionV.y;
        boolean up       = downPositionV.y < upPositionV.y;

        boolean insideUpLeft    = up&&left     &&(x <downPositionV.x && x>upPositionV.x)
                && (y>downPositionV.y && y<upPositionV.y);
        boolean insideUpRight   = up&&right    &&(x >downPositionV.x && x<upPositionV.x)
                && (y>downPositionV.y && y<upPositionV.y);
        boolean insideDownLeft  = down&&left   &&(x <downPositionV.x && x>upPositionV.x)
                && (y<downPositionV.y && y>upPositionV.y);
        boolean insideDownRight = down&&right  &&(x >downPositionV.x && x<upPositionV.x)
                && (y<downPositionV.y && y>upPositionV.y);


        return  insideUpLeft || insideUpRight || insideDownLeft || insideDownRight;
    }


    public boolean clearSelection(){

        if(!selectedUnits.isEmpty()) {

            for (Actor u : selectedUnits) {
                if (u instanceof ArmyUnit)
                    ((ArmyUnit)u).setSelected(false);
            }

            selectedUnits.clear();
        }

        return true;
	}

	private void simulateEnemy(){

        if(gameWorld.currentMap ==Maps.MAP1) {

            gameWorld.spawn(new Tank(500, 500,  Player.PLAYER1));
            gameWorld.spawn(new Tank(400, 500,  Player.PLAYER1));
            gameWorld.spawn(new Tank(500, 200,  Player.PLAYER2));

            gameWorld.spawn(new Tank(400, 300, Player.PLAYER2));
            gameWorld.build(new WarFactory(700, 700, Player.PLAYER2));
        }
        if(gameWorld.currentMap ==Maps.MAP2 || gameWorld.currentMap ==Maps.MAP3){

            gameWorld.spawn(new Tank(955 , 1320, Player.PLAYER2));

            gameWorld.spawn(new Tank(1068, 1184, Player.PLAYER2));
            gameWorld.spawn(new Tank(1290, 1106, Player.PLAYER2));

            gameWorld.build(new WarFactory(1402,1205,Player.PLAYER2));
            gameWorld.build(new Barrack(897,1422,Player.PLAYER2));
            gameWorld.build(new ResourceFactory(1206,1299,Player.PLAYER2));


        }

    }

    public RTSGame getGame() {
        return game;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }
}
