package com.mygdx.rtsgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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



public class GameScreen extends Stage implements Screen , InputProcessor {

    private final RTSGame game;
    private ControlMenu controlMenu;
    private EscapeMenu escMenu;
    private InfoSection infoSection;
    private static final float MARGIN = 50f;
    private float volume=0.2f;
    public static boolean TAB=false;
    public static boolean ESC=false;
    private ControlStage controlStage;

    private Box2DDebugRenderer debugRenderer;
    ///for testing
    //private EndGameDialog endGameDialog;

    static MultiSelect<ArmyUnit> multiSelect;

    private GameWorld     gameWorld         = GameWorld.getInstance();
    private Sound         gameSong          = GameAssetManager.getInstance().manager.get(GameAssetManager.gameSound);
    private final Skin    skin              = GameAssetManager.getInstance().manager.get(GameAssetManager.composerSkin);

    private static final int styleColor = 0X468c3f;

    //
    private Vector3 move = new Vector3();
    private Vector3 drag  = new Vector3();

    GameScreen(final RTSGame game) {


        this.game = game;

        controlStage    =   new ControlStage();
        controlMenu     =   new ControlMenu(this);
        escMenu         =   new EscapeMenu(this);
        infoSection     =   new InfoSection(0.9f,skin, Color.BLACK,new Color(styleColor));

        //endGameDialog = new EndGameDialog("war zone",skin,this);

        controlStage.addActor(controlMenu);
        controlStage.addActor(escMenu);
        controlStage.addActor(infoSection);
        //controlStage.addActor(endGameDialog);

        gameSong.loop(volume);
        ////////////////
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(controlStage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);

        multiSelect = new SelectionComponent<ArmyUnit>();
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
        // endGameDialog.updateState();
        infoSection.updateState();
        multiSelect.render(move.x,move.y);

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


        //debugRenderer.render(gameWorld.world, gameWorld.getCamera().combined);
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

       // multiSelect.dispose();
        gameWorld.dispose();
        gameSong.dispose();
        skin.dispose();
        getMultiSelect().clearIn();
        infoSection.dispose();
        TAB =false;
        ESC =false;
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

        if(button == Input.Buttons.RIGHT){ multiSelect.clearIn(); }

        ///////////////////////............ fitting touchDown coordinates to camera coordinates
        Vector3 touchDown;
        touchDown = gameWorld.getCamera().unproject(new Vector3(screenX,screenY,0));
        multiSelect.setTouchDown(screenX,screenY);

        if(!multiSelect.getInSelect().isEmpty()) {
            ////moving and attacking

            //touchDown click if no number of all armyUnits equal the size of the army units array in the gameWorld
            //if not selected add it to not selected
            //count the number outside the touchdown
            //if the count == the number of al units Means there are no units clicked

            ///THIS MEANS the touchDown is meant to be for moving selected units

            int count = 0;

            for (ArmyUnit nu : gameWorld.getArmyUnits()){
                if (!nu.isSelected()) {
                    multiSelect.exclude(nu);
                    //notSelectedUnits.add(nu);
                }
                if (!nu.hit(touchDown)){
                    count++;
                }
                if( count == gameWorld.getArmyUnits().size) {
                    for (ArmyUnit su : multiSelect.getInSelect()){
                        su.moveTo(touchDown.x, touchDown.y,true);
                    }
                }
            }
            ///if the touchDown hits a not selected (enemy) unit
            //if it hits a not selected own unit it will select it and remove the selection from others
            ///the selected unit OR units should attack it
            for(ArmyUnit him :multiSelect.getOutSelect()){
                if (him.hit(touchDown) && him.playerId != gameWorld.currentPlayer) {
                    for (ArmyUnit me : multiSelect.getInSelect()) {
                        me.attack(him);
                    }
                    break;
                }
            }
            multiSelect.clearOut();
      
        }else{
            /////////////////Building

            if(gameWorld.buildingInConstruction== Buildings.WARFACTORY){
                if(gameWorld.getResources()> Buildings.WARFACTORY.price()) {
                    gameWorld.setResources(gameWorld.getResources() - Buildings.WARFACTORY.price());
                    Building newWarFactory = new WarFactory(touchDown.x - MARGIN, touchDown.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(newWarFactory);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }
            }
            if(gameWorld.buildingInConstruction== Buildings.BARRAKS){
                if(gameWorld.getResources()> Buildings.BARRAKS.price()) {
                    gameWorld.setResources(gameWorld.getResources() - Buildings.BARRAKS.price());
                    Building barrck = new Barrack(touchDown.x - MARGIN, touchDown.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(barrck);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }

            }
            if(gameWorld.buildingInConstruction== Buildings.RESOURSEFACTORY){
                if(gameWorld.getResources()> Buildings.RESOURSEFACTORY.price()) {
                    gameWorld.setResources(gameWorld.getResources() - Buildings.RESOURSEFACTORY.price());
                    Building newResourceFactory = new ResourceFactory(touchDown.x - MARGIN,
                            touchDown.y - MARGIN, gameWorld.currentPlayer);
                    gameWorld.build(newResourceFactory);
                    gameWorld.setBuildingInConstruction(Buildings.NOB);
                }
            }
            if(gameWorld.buildingInConstruction== Buildings.WALL){
                if(gameWorld.getResources()> Buildings.WALL.price()) {
                    gameWorld.setResources(gameWorld.getResources() - Buildings.WALL.price());
                    Building newWall = new Wall(touchDown.x - MARGIN, touchDown.y - MARGIN,gameWorld.currentPlayer);
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
        Vector3 touchUp;
        touchUp = gameWorld.getCamera().unproject(new Vector3(screenX, screenY, 0));

        multiSelect.setTouchUp(screenX,screenY);

        if(button == Input.Buttons.RIGHT){
            //clearSelection();
            multiSelect.clearIn();
            }

        ////////////////////////// SINGLE SELECT /////////////////////////////////

        for (ArmyUnit u : gameWorld.getArmyUnits()) {
            if (!(u instanceof Building)) {

                if (u.getPlayerId()== gameWorld.currentPlayer ) { //select units with the player id of this game world
                    if (u.hit(touchUp)) {
                        if(u.playerId == gameWorld.currentPlayer) {
                            if(multiSelect.clearIn()) {
                                multiSelect.pick(u);
                                u.setSelected(true);
                            }
                        }
                    }
                    ////////////////////////..... MULTI SELECT .... selection rectangle /////////////////////////

                    if (multiSelect.isWideEnough()){ ////if the selection rectangle is big enough

                        if (multiSelect.contain(u)){///////check if there are units inside the rect
                            u.setSelected(true);
                            multiSelect.pick(u);
                        }
                    }
                }
            }
            else {
                System.out.println("select building");
                //for selecting buildings
            }
        }

        multiSelect.reset();
        return true;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        drag.set(screenX,screenY,0);
        drag.set(getCamera().unproject(drag));
        multiSelect.resize(move.x,move.y,drag.x,drag.y);

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY){


        move.set(screenX,screenY,0);
        move.set(getCamera().unproject(move));

            if (screenX > Gdx.graphics.getWidth() - 30) {//to move right
                gameWorld.getCamera().translate(10, 0);
            }
            if (screenX < 30) {//to move left
                gameWorld.getCamera().translate(-10, 0);
            }

            if (screenY > Gdx.graphics.getHeight() - 30) {//to move down
                gameWorld.getCamera().translate(0, -10);
            }
            if (screenY < 30) {//to move up
                gameWorld.getCamera().translate(0, 10);
            }

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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

     void setVolume(float volume) { this.volume = volume; }

    public MultiSelect<ArmyUnit> getMultiSelect() {
        return multiSelect;
    }
}
