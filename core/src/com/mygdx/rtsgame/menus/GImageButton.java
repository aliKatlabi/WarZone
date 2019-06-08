package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.rtsgame.GameWorld;
import com.mygdx.rtsgame.assets.GameAssetManager;

public class GImageButton extends ImageButton   {


    private Skin skin = GameWorld.assetManager.manager.get(GameAssetManager.tracerSkin);

    public GImageButton(Skin skin, Texture upImage, Texture downImage, Texture disableImage) {
        super(skin);

        TextureRegion upRegion = new TextureRegion(upImage);
        TextureRegionDrawable upImgRg = new TextureRegionDrawable(upRegion);

        TextureRegion downRegion = new TextureRegion(downImage);
        TextureRegionDrawable downImgRg = new TextureRegionDrawable(downRegion);

        TextureRegion disRegion = new TextureRegion(disableImage);
        TextureRegionDrawable disImgRg = new TextureRegionDrawable(disRegion);

        getStyle().imageUp = upImgRg;
        getStyle().imageDown = downImgRg;
        getStyle().imageDisabled = disImgRg;

    }
}
