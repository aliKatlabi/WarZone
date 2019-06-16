package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class CMTextButton extends TextButton {

    private Label lable;

    public CMTextButton(String text, Skin skin) {
        super(text, skin);
        lable = new Label(text,skin);
    }

    public CMTextButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        lable = new Label(text,skin);
    }

    public CMTextButton(String text, TextButtonStyle style) {
        super(text, style);

    }

    void setConfig1(float fontScale){

        lable.setColor(0.8f,0.2f,0.2f,1);
        lable.setFontScale(fontScale);
        lable.setAlignment(Align.center);
        setLabel(lable);
        setColor(Color.BLACK);
    }
}
