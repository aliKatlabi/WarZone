package com.mygdx.rtsgame.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private float BUTTONHIEGHT=25f;
    private float BUTTONWIDTH=85f;
    private long SEED = 100l;
    private float fontScale = 0.7f;
    private Label resources;
    private ShapeRenderer shr = new ShapeRenderer();

    public ControlMenu(final GameWorld gw , GameScreen gs){
        gameScreen = gs;
        gameWorld = gw ;
        skin = gameWorld.assetManager.manager.get(GameAssetManager.tracerSkin);
        resources = new Label("|Resources|",skin);
        resources.setFontScale(fontScale);

        resources.setColor(Color.BLACK);
        shr.setAutoShapeType(true);
        this.setFillParent(true);
        this.top().right();
        this.add(resources).padBottom(10).padLeft(10).padRight(15);
        row();
        this.add(superSoldierSpawn()).padBottom(10).padLeft(10).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        row();
        this.add(tankSpawn()).padBottom(10).padLeft(10).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        row();
        this.add(warFctory()).padBottom(10).padLeft(10).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        row();
        this.add(barrack()).padBottom(10).padLeft(10).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        row();
        this.add(resourceFactory()).padBottom(10).padLeft(10).width(BUTTONWIDTH).height(BUTTONHIEGHT);
        row();
        this.add(sol()).padBottom(10).padLeft(10).width(2*BUTTONWIDTH).height(3*BUTTONHIEGHT);


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        shr.end();
        shr.begin();
        shr.rect(getX(),getY(),getWidth(),getHeight(),Color.BLACK,Color.BLUE,Color.BLACK,Color.BLUE);
    }

    ImageButton sol(){

        Texture sol = GameWorld.assetManager.manager.get(GameAssetManager.soldierTexture);

        TextureRegion disRegion = new TextureRegion(sol);
        TextureRegionDrawable disImgRg = new TextureRegionDrawable(disRegion);

        ImageButton gi = new ImageButton(disImgRg);
        return gi;
    }


    TextButton soldierSpawn(){

        TextButton b = new TextButton("soldier", skin);
        final Label lb = new Label("Soldier",skin);
        lb.setColor(0.2f,0.2f,0.5f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);


        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {
                    if(b.isConstructed()) {

                        if ((b instanceof Barrack) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.SOLDIER.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.SOLDIER.price);
                                Soldier newSoldier = new Soldier(b.getX() + b.getWidth(), b.getY() + b.getHeight(), gameWorld, gameWorld.currentPlayer);
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

    TextButton superSoldierSpawn(){

        TextButton b = new TextButton("SuperSoldier", skin);
        final Label lb = new Label("Omega",skin);
        lb.setColor(0.2f,0.2f,0.5f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);


        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {
                    if(b.isConstructed()) {

                        if ((b instanceof Barrack) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.SUPER_SOLDIER.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.SUPER_SOLDIER.price);
                                SuperSoldier newSoldier = new SuperSoldier(b.getX() + b.getWidth(), b.getY() + b.getHeight(), gameWorld, gameWorld.currentPlayer);
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

    TextButton tankSpawn(){

        TextButton b = new TextButton("tank", skin);
        Label lb = new Label("Tank",skin);
        lb.setColor(0.2f,0.2f,0.5f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);


        b.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {

                gameScreen.clearSelection();
                for (Building b : gameWorld.getBuildings()) {

                    if (b.isConstructed()) {
                        if ((b instanceof WarFactory) && b.getPlayerId() == gameWorld.currentPlayer) {
                            if (gameWorld.getResorces() > ArmyUnits.TANK.price) {
                                gameWorld.setResorces(gameWorld.getResorces() - ArmyUnits.TANK.price);
                                Tank newTank = new Tank(b.getX() + b.getWidth(), b.getY() + b.getHeight(), gameWorld, gameWorld.currentPlayer);
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
    TextButton warFctory(){

        TextButton b = new TextButton("war factory", skin);
        Label lb = new Label("War factory",skin);
        lb.setColor(0.8f,0.2f,0.2f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.WARFACTORY);
            }
        });

        return b;
    }
    TextButton resourceFactory(){

        TextButton b = new TextButton("Gold plant", skin);
        Label lb = new Label("Gold plant",skin);
        lb.setColor(0.8f,0.2f,0.2f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.RESOURSEFACTORY);
            }
        });

        return b;
    }
    TextButton barrack(){

        TextButton b = new TextButton("Barrack", skin);
        Label lb = new Label("Barrack",skin);
        lb.setColor(0.8f,0.2f,0.2f,1);
        lb.setFontScale(fontScale);
        lb.setAlignment(Align.center);
        b.setLabel(lb);
        b.setColor(Color.BLACK);

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.BARRAKS);

            }
        });

        return b;
    }
    TextButton wall(){

        TextButton b = new TextButton("wall", skin);
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.clearSelection();
                gameWorld.setBuildingInConstruction(Buildings.WALL);
            }
        });

        return b;
    }

    public void updateState() {

        resources.setText("| Resources | " + gameWorld.getResorces());
        /*


        if(GameWorld.enemyUnitsCount <=0){
            System.out.println("won" + GameWorld.currentPlayersUnitsCount);

            gameFinisheDialoge.text("Congratulations! You Won");
            if(Gdx.graphics.getDeltaTime()<5f)
                gameFinisheDialoge.show(gameScreen);

        }
        if(gameWorld.hasUnits && GameWorld.currentPlayersUnitsCount <=0){
            System.out.println("lost" + GameWorld.enemyUnitsCount);
            gameFinisheDialoge.text("War is tragedy !" );
            if(Gdx.graphics.getDeltaTime()<5f)
                gameFinisheDialoge.show(gameScreen);
        }

    }

         */
    }


}
