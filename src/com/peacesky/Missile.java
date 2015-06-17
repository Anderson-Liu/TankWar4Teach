package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;
import java.util.*;
import java.util.List;

public class Missile {
    int x, y;
    Direction dir;
    boolean live = true;
    private boolean good;
    TankClient tankClient;
    Tank t;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final int XSPEED = 14;
    private static final int YSPEED = 14;

    public Missile (Tank tank) {
        this.t = tank;
        this.x = t.x;
        this.y = t.y;
        this.dir = t.ptDir;
        this.good = t.isGood();
        this.tankClient = t.tc;
    }

    // 指定方向的子弹，用于大招
    public Missile(Tank tank, Direction dirs) {
        this.t = tank;
        this.x = t.x;
        this.y = t.y;
        dir = dirs;
        this.good = t.isGood();
        this.tankClient = t.tc;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        if (good) {
            g.setColor(Color.CYAN);
        }
        else {
            g.setColor(Color.LIGHT_GRAY);
        }
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

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void hitTank(Tank tank) {
        if(this.live && this.getRect().intersects(tank.getRect()) && tank.isLive() && this.good != tank.isGood()) {
            if (tank.isGood()) {
                this.live = false;
                tankClient.msList.remove(this);
                tank.life = tank.life - 20;
                if (tank.life <= 0) {
                    this.live = false;
                    tankClient.msList.remove(this);
                    tank.setLive(false);
                    Explode e = new Explode(x, y, tankClient);
                    tankClient.explodes.add(e);
                }
            } else {
                this.live = false;
                tankClient.msList.remove(this);
                tank.setLive(false);
                tankClient.tanks.remove(tank);
                Explode e = new Explode(x, y, tankClient);
                tankClient.explodes.add(e);
            }
        }
    }

    public void hitTanks(List<Tank> tanks) {
        for (int i=0; i<tanks.size(); i++ ) {
            Tank tank = tanks.get(i);
            hitTank(tank);
        }
    }

    public void hitWall(Wall wall) {
        if (wall.getRect().intersects(this.getRect())) {
            this.live = false;
            tankClient.msList.remove(this);
        }
    }
}