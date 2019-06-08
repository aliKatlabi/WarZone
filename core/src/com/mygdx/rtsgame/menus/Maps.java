package com.mygdx.rtsgame.menus;

public enum Maps {

    NOM(""),MAP1("maps/Borders/firstmap.tmx"),MAP2("maps/Moon/moon.tmx"),MAP3("maps/Moon/moon.tmx");

    private String path;

    public String path()
    {
        return this.path; }

    Maps(String path ) {
        this.path = path;
    }

}
