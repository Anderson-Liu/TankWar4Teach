package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;

public class Tank {
    int x, y;
    private static int WIDTH = 30;
    private static int HEIGHT = 30;

    public Tank (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        Color c =g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
    }

}
