package com.mygdx.rtsgame.elemnts.bullets;

public enum Bullets {

    FIRE_B(130f,0.3f,2f,0.2f),
    SUPERFIRE_B(200f,0.3f,2f,0.2f),
    NEUTRON_B(230f,0.3f,2f,0.2f),
    VOID_B(340f,0.5f,3f,2f),
    NUCLEAR_B(450f,1f,5f,4f);

    public float damage;
    public float velocity;
    public float existenceTime;
    public float transitionTime;

    Bullets(float damage,float velocity,float existenceTime , float transitionTime){
        this.damage=damage;
        this.velocity=velocity;
        this.existenceTime=existenceTime;
        this.transitionTime=transitionTime;

    }


}
