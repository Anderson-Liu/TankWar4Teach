package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */


import java.awt.*;

public class Blood {
    int x, y;                                   // 位置
    int step = 0;
    int time = 0;
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private boolean live = true;
    private int[][] pos = {
            {500, 300},
            {360, 300},
            {500, 275},
            {400, 400},
            {360, 270},
            {365, 290},
            {340, 280}
    };


    public Blood() {
        x = pos[0][0];
        y = pos[0][1];
    }

    public void draw(Graphics g) {
        if (!live) return;
        Color c = g.getColor();
        g.setColor(Color.CYAN);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    private void move() {
        step++;
        if (step == pos.length) {
            step = 0;
            time++;
            if (time == 1000) {
                this.live = false;
                return;
            }
        }
        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
