package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;

public class Missile {
    int x, y;
    Direction dir;
    boolean live = true;
    TankClient tankClient;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int XSPEED = 14;
    private static final int YSPEED = 14;

    public Missile (int x, int y, Direction dir, TankClient tankClient) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankClient = tankClient;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
       // g.drawOval(x, y, WIDTH, HEIGHT);
       g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    public void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
        if(x < 0 || y < 0 || x > TankClient.WIDTH || y > TankClient.HEIGHT) {
            live = false;
            tankClient.msList.remove(this);
        }
    }
}
