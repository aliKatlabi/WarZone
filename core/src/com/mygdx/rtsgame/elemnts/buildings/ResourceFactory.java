package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.Player;

public final  class ResourceFactory extends Building {

    public final static int price = Buildings.RESOURSEFACTORY.price;
    private float moneyTime=0;
    private float moneyDelevered=5f;
    private final int profits=140;
    private final CharSequence str = ""+profits+" $";

    private BitmapFont font = new BitmapFont();

    public ResourceFactory(float px, float py, GameWorld gw, Player pid) {
        super(px, py, gw, pid);
        setTexture(new Texture(Gdx.files.internal("buildings/orange extraction rig02.png")));

        setConstructionTime(Buildings.RESOURSEFACTORY.constructionTime);
        setHp(Buildings.RESOURSEFACTORY.HP);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        // batch.setProjectionMatrix(gameWorld.getCamera().combined);
        super.draw(batch,parentAlpha);

        if(isConstructed() && getPlayerId() == gameWorld.currentPlayer)
            font.draw(batch,str,getX()+getWidth()/2,getY()+getHeight()+4*moneyTime);

    }
    @Override
    public void act(float delta) {
        super.act(delta);

        moneyTime += delta;
        if (getPlayerId() == gameWorld.currentPlayer) {
            if (moneyTime > moneyDelevered && isConstructed()) {
                gameWorld.setResorces(gameWorld.getResorces() + profits);
                moneyTime = 0;
            }

        }
    }
}
