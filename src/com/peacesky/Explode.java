package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;

public class Explode {
    int x, y;
    boolean live;
    TankClient tankClient;

    int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
    int step = 0;

    public Explode(int x, int y, TankClient tankClient) {
        this.x = x;
        this.y = y;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g) {
        if(step == diameter.length) {
            live = false;
            tankClient.explodes.remove(this);
            step = 0;
            return;
        }

        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);

        step ++;
    }
}
