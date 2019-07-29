package com.mygdx.rtsgame;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.mygdx.rtsgame.elemnts.bullets.Bullet;
import com.mygdx.rtsgame.elemnts.units.ArmyUnit;

import java.util.ArrayList;

public class GameMapRenderer extends OrthogonalTiledMapRenderer {

    private World world;
    private ArrayList<ArmyUnit> warElements;

    private static  int mapObjectsCount = 0;

    GameMapRenderer(TiledMap map , World w  ) {
        super(map);
        world = w;
        warElements = new ArrayList<ArmyUnit>();
        installStaticBodies();
    }
    void addUnitToMapRenderer(ArmyUnit p){

        warElements.add(p);
    }
    void removeUnitFromMapRenderer(ArmyUnit p){
        warElements.remove(p); }

    @Override
    public void render() {

        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            ///////////////////////draw units behind some objects depend on the layer order
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                    currentLayer++;
                    final int drawSpritesAfterLayer=1;
                    if (currentLayer == drawSpritesAfterLayer) {
                        for (ArmyUnit p : warElements) {
                                p.draw(this.getBatch(), 0);
                        }


                    }

                    else {
                        for (MapObject object : layer.getObjects()) {
                            renderObject(object);

                        }
                        for (ArmyUnit p : warElements) {
                            if (p instanceof Bullet && currentLayer==2)
                                p.draw(this.getBatch(), 0);
                        }
                    }
                }
            }
        }



        endRender();
    }

    private  void installStaticBodies(){

        if(mapObjectsCount== 0) {
            for (MapObject mo : map.getLayers().get("Object Layer").getObjects()) {
                if(mo instanceof PolygonMapObject) {

                    PolygonShape shape = new PolygonShape();
                    Polygon poly = ((PolygonMapObject) mo).getPolygon();
                    float[] vertices = ((PolygonMapObject) mo).getPolygon().getVertices();
                    shape.set(vertices);
                    /////////////
                    Body body;
                    BodyDef bodydef = new BodyDef();
                    bodydef.type = BodyDef.BodyType.StaticBody;
                    bodydef.position.set(poly.getX(), poly.getY());
                    body = world.createBody(bodydef);

                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 20000f;
                    body.createFixture(fixtureDef);
                    renderObject(mo);
                    shape.dispose();
                }

                mapObjectsCount++;
            }

        }
    }

    public Rectangle getViewBounds(){

        return this.viewBounds;
    }


}
