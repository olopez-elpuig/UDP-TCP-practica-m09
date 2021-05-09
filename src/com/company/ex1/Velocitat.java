package com.company.ex1;

public class Velocitat {

    int vel,max;
    public Velocitat(int max) {
        this.max = max;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public int agafaVelocitat() {
        setVel((int)(Math.random()*max)+1);
        return getVel();
    }

}
