package com.mygdx.rtsgame.elemnts.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.rtsgame.Player;

public final  class ResourceFactory extends Building {

    //public final static int price = Buildings.RESOURCE_FACTORY.price;
    private float moneyTime=0;
    private static final float moneyDelivered=5f;
    private final int profits=140;
    private final CharSequence str =""+profits+" $";

    private BitmapFont font = new BitmapFont();

    public ResourceFactory(float px, float py, Player pid) {
        super(px, py ,pid);
        setTexture(new Texture(Gdx.files.internal("buildings/orange extraction rig02.png")));

        setConstructionTime(Buildings.RESOURSEFACTORY.constructionTime);
        setHp(Buildings.RESOURSEFACTORY.HP);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        // batch.setProjectionMatrix(gameWorld.getCamera().combined);
        super.draw(batch,parentAlpha);

        if(isConstructed() && getPlayerId() == getGameWorld().currentPlayer)
            font.draw(batch,str,getX()+getWidth()/2,getY()+getHeight()+4*moneyTime);

    }
    @Override
    public void act(float delta) {
        super.act(delta);

        moneyTime += delta;
        if (getPlayerId() == getGameWorld().currentPlayer) {
            if (moneyTime > moneyDelivered && isConstructed()) {
                getGameWorld().setResources(getGameWorld().getResources() + profits);
                moneyTime = 0;
            }

        }
    }
}
