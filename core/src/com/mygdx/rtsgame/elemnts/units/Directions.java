package com.mygdx.rtsgame.elemnts.units;

public enum Directions {

    NOD(0,0),UR(1,1),UL(-1,1),DR(1,-1),DL(-1,-1)
    ,U(0,1),D(0,-1),L(-1,0),R(1,0);

    protected float X;
    protected float Y;

    public static Directions translate(ArmyUnit u , ArmyUnit e)
    {
        //e.getX()+e.getWidth()/2 & e.getY()+ e.getHeight()/2
        //to make the zero of the unit in its  center

        if((e.getX()+e.getWidth()/2)<u.getX() && (e.getY()+ e.getHeight()/2)<u.getY())
            return Directions.DL;
        if((e.getX()+e.getWidth()/2)<u.getX() && (e.getY()+ e.getHeight()/2)>u.getY())
            return Directions.UL;
        if((e.getX()+e.getWidth()/2)>u.getX() && (e.getY()+ e.getHeight()/2)>u.getY())
            return Directions.UR;
        if((e.getX()+e.getWidth()/2)>u.getX() && (e.getY()+ e.getHeight()/2)<u.getY())
            return Directions.DR;
        /*
        if((e.getX()+e.getWidth()/2)>u.getX() && (e.getY()+ e.getHeight()/2) == u.getY())
            return Directions.R;
        if((e.getX()+e.getWidth()/2)<u.getX() && (e.getY()+ e.getHeight()/2) == u.getY())
            return Directions.L;
        if((e.getX()+e.getWidth()/2) == u.getX() && (e.getY()+ e.getHeight()/2)<u.getY())
            return Directions.U;
        if((e.getX()+e.getWidth()/2) == u.getX() && (e.getY()+ e.getHeight()/2)>u.getY())
            return Directions.D;

*/

        return Directions.NOD; }



    Directions(float x , float y) {

        this.X=x;
        this.Y=y;
    }

}
