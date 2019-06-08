package com.mygdx.rtsgame.elemnts.buildings;

public enum Buildings {

    NOB(0,0,0),
    WARFACTORY(2000,42000f,10f),
    BARRAKS(1150,39999f,7f),
    RESOURSEFACTORY(3000,59999f,14f),
    WALL(300,11000,2f);

    protected int price;
    protected float HP;
    protected float constructionTime;

    public int price()
    {
        return this.price; }


    Buildings(int price,float HP , float constructionTime) {
        this.price = price;
        this.HP = HP;
        this.constructionTime=constructionTime;
    }

}