package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
    int x, y;                                                           // 位置
    int step = 0;
    TankClient tc;
    Direction dir = Direction.STOP;                                     // 坦克方向
    Direction ptDir = Direction.D;                                      // 炮筒方向
    private boolean good;
    Random r = new Random();


    private boolean live = true;
    private static int WIDTH = 30;                                      // 大小
    private static int HEIGHT = 30;
    private static int XSPEED = 10;                                     // 速度
    private static int YSPEED = 10;

    private boolean bL = false, bU = false, bR = false, bD = false;     // 方向

    public Tank (int x, int y, boolean good, TankClient tc) {                                        // 构造方法
        this.x = x;
        this.y = y;
        this.tc = tc;
        this.good = good;
    }

    public void draw(Graphics g) {                                      // 画法
        if (!live) {
            return;
        }
        Color c =g.getColor();
        if (good) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        switch(ptDir) {
            case L:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
                break;
            case LU:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
                break;
            case U:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
                break;
            case RU:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
                break;
            case RD:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
                break;
        }
        move();
    }

    protected void move() {
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
        if (dir != Direction.STOP) {
            ptDir = dir;
        }
        if(x < 0) x = 0;
        if(y < 30) y = 30;
        if(x + Tank.WIDTH > TankClient.WIDTH) x = TankClient.WIDTH - Tank.WIDTH;
        if(y + Tank.HEIGHT > TankClient.HEIGHT) y = TankClient.HEIGHT - Tank.HEIGHT;

        if(!good) {
            Direction[] dirs = Direction.values();
            if(step == 0) {
                step = r.nextInt(15) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }
            step --;

            if(r.nextInt(40) > 38) this.fire();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:                                              // 上下左右键
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDir();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
                fire();
                return;
            case KeyEvent.VK_LEFT:                                              // 少写break的话容易造成穿透;
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locateDir();

    }

    public void locateDir() {
        if (bL && !bU && !bR && !bD) dir = Direction.L;
        else if (bL && bU && !bR && !bD) dir = Direction.LU;
        else if (!bL && bU && !bR && !bD) dir = Direction.U;
        else if (!bL && bU && bR && !bD) dir = Direction.RU;
        else if (!bL && !bU && bR && !bD) dir = Direction.R;
        else if (!bL && !bU && bR && bD) dir = Direction.RD;
        else if (!bL && !bU && !bR && bD) dir = Direction.D;
        else if (bL && !bU && !bR && bD) dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
    }

    public void fire() {
        Missile m = new Missile(this);
        tc.msList.add(m);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean isLive() {
        return live;
    }

    public boolean isGood() {
        return good;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
