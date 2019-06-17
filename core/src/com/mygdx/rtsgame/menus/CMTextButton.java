package com.mygdx.rtsgame.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class CMTextButton extends TextButton {

    private Label label;

    CMTextButton(String text, Skin skin) {
        super(text, skin);
        label = new Label(text,skin);
    }

    public CMTextButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        label = new Label(text,skin);
    }

    public CMTextButton(String text, TextButtonStyle style) {
        super(text, style);

    }

    void setConfig1(float fontScale){

        label.setColor(0.8f,0.2f,0.2f,1);
        label.setFontScale(fontScale);
        label.setAlignment(Align.center);
        setLabel(label);
        setColor(Color.BLACK);
    }
}
