package com.mygdx.rtsgame;

public enum Player {
    NOP(0) ,PLAYER1(0) , PLAYER2(0);

    public int bodyCount;
    public int resources;

    Player(int bodyCount){
        this.bodyCount=bodyCount;
    }
    void plus(){
        bodyCount++;
    }
    void minus(){
        bodyCount--;
    }


}

