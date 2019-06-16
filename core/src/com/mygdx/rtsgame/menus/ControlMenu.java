package com.mygdx.rtsgame.menus;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;
import com.mygdx.rtsgame.elemnts.buildings.Barrack;
import com.mygdx.rtsgame.elemnts.buildings.Building;
import com.mygdx.rtsgame.elemnts.buildings.Buildings;
import com.mygdx.rtsgame.elemnts.buildings.WarFactory;
import com.mygdx.rtsgame.elemnts.units.ArmyUnits;
import com.mygdx.rtsgame.elemnts.units.Soldier;
import com.mygdx.rtsgame.elemnts.units.SuperSoldier;
import com.mygdx.rtsgame.elemnts.units.Tank;
import com.mygdx.rtsgame.screens.GameScreen;


public class ControlMenu extends Table   {

    private GameWorld gameWorld;
    private GameScreen gameScreen;
    private Skin skin ;

    private float fontScale = 0.7f;
    private Label resources;

    public ControlMenu(final GameWorld gw , GameScreen gs){
        gameScreen = gs;
        gameWorld = gw ;

        final float BUTTON_HEIGHT=65f;
        final float BUTTON_WIDTH=75f;

        skin = GameAssetManager.getInstance().manager.get(GameAssetManager.tracerSkin);
        resources = new Label("|Resources|",skin);
        resources.setFontScale(fontScale);
        resources.setColor(Color.BLACK);
        this.setFillParent(true);

        center();
        this.add(superSoldierSpawn()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);
        this.add(tankSpawn()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);
        this.add(soldierSpawn()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);
        row();
        this.add(warFactory()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);
        this.add(barrack()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);
        this.add(resourceFactory()).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5).padLeft(5);


    }



    private TextButton soldierSpawn(){

        CMTextButton b = new CMTextButton("soldier", skin);
        b.setConfig1(fontScale);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {
                    if(b.isConstructed()) {

                        if ((b instanceof Barrack) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.SOLDIER.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.SOLDIER.price);
                                Soldier newSoldier = new Soldier(b.getX() + b.getWidth(), b.getY() + b.getHeight(),gameWorld.currentPlayer);
                                gameWorld.spawn(newSoldier);
                                break;
                            }
                        }
                    }
                }
            }
        });

        return b;
    }

    private TextButton superSoldierSpawn(){

        CMTextButton b = new CMTextButton("SuperSoldier", skin);
        b.setConfig1(fontScale);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {
                    if(b.isConstructed()) {

                        if ((b instanceof Barrack) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.SUPER_SOLDIER.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.SUPER_SOLDIER.price);
                                SuperSoldier newSoldier = new SuperSoldier(b.getX() + b.getWidth(), b.getY() + b.getHeight(), gameWorld.currentPlayer);
                                gameWorld.spawn(newSoldier);
                                break;
                            }
                        }
                    }
                }
            }
        });

        return b;
    }

    private TextButton tankSpawn(){

        CMTextButton b = new CMTextButton("tank", skin);
        b.setConfig1(fontScale);


        b.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {

                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {

                    if (b.isConstructed()) {
                        if ((b instanceof WarFactory) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.TANK.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.TANK.price);
                                Tank newTank = new Tank(b.getX() + b.getWidth(), b.getY() + b.getHeight(), gameWorld.currentPlayer);
                                gameWorld.spawn(newTank);
                                break;
                            }
                        }
                    }
                }
            }
        });

        return b;
    }
    private TextButton warFactory(){

        CMTextButton b = new CMTextButton("war factory", skin);
        b.setConfig1(fontScale);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.WARFACTORY);
            }
        });

        return b;
    }
    private TextButton resourceFactory(){

        CMTextButton b = new CMTextButton("Gold plant", skin);
        b.setConfig1(fontScale);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.RESOURSEFACTORY);
            }
        });

        return b;
    }
    private TextButton barrack(){

        CMTextButton b = new CMTextButton("Barrack", skin);


        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.BARRAKS);

            }
        });

        return b;
    }
    /*
    TextButton wall(){

        CMTextButton b = new CMTextButton("wall", skin);
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.WALL);
            }
        });

        return b;
    }
*/
    public void updateState() {

        resources.setText("| Resources | " + gameWorld.getResorces());

    }


}
