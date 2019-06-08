package com.mygdx.rtsgame.assets;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {


    public AssetManager manager = new AssetManager();

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


    public void load()
    {
        //texture
        manager.load(armyUnitTexture);
        manager.load(soldierTexture);

        //bulltes
        manager.load(bulletTexture);
        manager.load(NeutronBulletTexture);

        //sound
        manager.load(shootingSound);
        manager.load(movingSound);
        manager.load(destroyedSound);

        manager.load(tankShootingSound);
        manager.load(tankMovingSound);
        manager.load(tankDestroyedSound);
        manager.load(shootingSound);


        //skin

        manager.load(tracerSkin);
        manager.load(composerSkin);

    }

    public void dispose()
    {
        manager.dispose();
    }

}
