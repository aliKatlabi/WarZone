package com.mygdx.rtsgame.assets;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.org.apache.xpath.internal.operations.Variable;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameAssetManager {



    public AssetManager manager = new AssetManager();

    private static ArrayList<AssetDescriptor> ADList = new ArrayList<AssetDescriptor>();


    private static GameAssetManager SINGLE_INS = null;
    ////texture

    public static final AssetDescriptor<Texture> armyUnitTexture =
            new AssetDescriptor<Texture>("data/sprites/tank/eyelander.png", Texture.class);


    public static final AssetDescriptor<Texture> soldierTexture =
            new AssetDescriptor<Texture>("data/sprites/soldier/eyelander1.png", Texture.class);

    //bullets
    public static final AssetDescriptor<Texture> bulletTexture =
            new AssetDescriptor<Texture>("data/sprites/bullets/Fireball-PNG-HD.png", Texture.class);

    public static final AssetDescriptor<Texture> NeutronBulletTexture =
            new AssetDescriptor<Texture>("data/sprites/bullets/blue-fire-15.png", Texture.class);

    //sound
    public static final AssetDescriptor<Sound> gameSound =
            new AssetDescriptor<Sound>("data/sounds/backSong.mp3", Sound.class);

    public static final AssetDescriptor<Sound> shootingSound =
            new AssetDescriptor<Sound>("data/sounds/9_mm_gunshot-mike-koenig-123.mp3", Sound.class);
    public static final AssetDescriptor<Sound> movingSound =
            new AssetDescriptor<Sound>("data/sounds/soldierRunning.mp3", Sound.class);
    public static final AssetDescriptor<Sound> destroyedSound =
            new AssetDescriptor<Sound>("data/sounds/soldierDying.mp3", Sound.class);
    public static final AssetDescriptor<Sound> tankShootingSound =
            new AssetDescriptor<Sound>("data/sounds/Tankshooting.mp3", Sound.class);
    public static final AssetDescriptor<Sound> tankMovingSound =
            new AssetDescriptor<Sound>("data/sounds/TankMoving.mp3", Sound.class);
    public static final AssetDescriptor<Sound> tankDestroyedSound =
            new AssetDescriptor<Sound>("data/sounds/destroyedSound.mp3", Sound.class);


    //skin
    public static final AssetDescriptor<Skin> composerSkin =
            new AssetDescriptor<Skin>("skins/skin-composer/skin/skin-composer-ui.json", Skin.class);
    public static final AssetDescriptor<Skin> tracerSkin =
            new AssetDescriptor<Skin>("skins/tracer/skin/tracer-ui.json", Skin.class);

    private GameAssetManager() throws IllegalAccessException{

        for(Field f : this.getClass().getFields()){

            if((f.get(this)) instanceof AssetDescriptor){

                ADList.add(((AssetDescriptor)(f.get(this))));
            }

        }
    }

    public static GameAssetManager getInstance()
    {

        if(SINGLE_INS == null){
            try {
                SINGLE_INS = new GameAssetManager();
            }catch (IllegalAccessException iae ){
                iae.printStackTrace();
            }
        }
        return SINGLE_INS;
    }

    public void load()
    {
        for(AssetDescriptor ad : ADList){
            manager.load(ad);
        }

    }


    public void dispose()
    {
        manager.dispose();
    }

}
