package com.mygdx.rtsgame.elemnts.units;

public enum ArmyUnits {
    NOA(0,0,0,0,0,0),
    TANK(1320,460,400000f,1.1f,11f,6f),
    SOLDIER(560,460,20000f,0.4f,7f,4f),
    SUPER_SOLDIER(4560,4000,26000f,0.2f,7f,4f) ;

    public int price;
    public float HP;
    public float range ;
    public float fireRate;
    public float moveSpeed;
    public float spawnTime ;


    ArmyUnits(int price ,float HP, float range ,float fireRate,float moveSpeed, float spawnTime ) {
        this.price = price;
        this.HP = HP;
        this.range=range;
        this.fireRate=fireRate;
        this.moveSpeed=moveSpeed;
        this.spawnTime=spawnTime;
    }

}
