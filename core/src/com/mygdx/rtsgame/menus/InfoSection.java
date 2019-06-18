package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.rtsgame.GameWorld;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InfoSection extends Table {

    private InfoSectionLabel resources;
    private InfoSectionLabel bodyCount;
    private float fontScale ;
    private Skin skin ;
    private float maxWidth;
    //private float height;
    //private float width;

    public InfoSection(float fontScale,Skin skin,Color color){

        this.fontScale = fontScale;
        this.skin = skin;
        this.setFillParent(true);

        resources  = setUp(color);
        bodyCount  = setUp(color);


        maxWidth = getMaxWidth(resources.getWidth(),bodyCount.getWidth());

        resources.set(maxWidth,getHeight());
        bodyCount.set(maxWidth,getHeight());
        resources.setAlignment(Align.center);
        bodyCount.setAlignment(Align.center);

        right().bottom();
        add(resources).padBottom(3f).width(150f).padRight(10f);
        row();
        add(bodyCount).padBottom(3f).width(150f).padRight(10f);
    }

    private float getMaxWidth(float... width){
        float max=0;

            for(float v:width)
                max = Math.max(max , v);

        return max;
    }
    private InfoSectionLabel setUp(Color color){

        InfoSectionLabel label = new InfoSectionLabel("",skin);
        label.setFontScale(fontScale);
        label.setColor(color);
        return label;
    }
    public void updateState() {

        maxWidth = getMaxWidth(resources.getWidth(),bodyCount.getWidth());

        resources.set(maxWidth,resources.getHeight());
        resources.render();
        resources.setText("Resources" + GameWorld.getInstance().getResources());

        bodyCount.set(maxWidth,bodyCount.getHeight());
        bodyCount.render();
        if(GameWorld.getInstance().currentPlayer!=null)
            bodyCount.setText("Units in field : " + GameWorld.getInstance().currentPlayer.bodyCount);
    }

    public void dispose(){


        for(Field f : getClass ().getFields()){
            if(f.getType().getName().equals("InfoSectionLabel")) {
                Object o = new Object();
                try {
                    f.get(o).getClass().getMethod("dispose").invoke(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e){
                    e.printStackTrace();
                } catch (InvocationTargetException e){
                    e.printStackTrace();
                }
            }
        }

        //or
        //resources.dispose();
        //bodyCount.dispose();

    }
}
