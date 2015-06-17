package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;

public class Wall {
    int x, y, width, height;
    TankClient tc;

    public Wall(int x, int y, int width, int height, TankClient tc) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
