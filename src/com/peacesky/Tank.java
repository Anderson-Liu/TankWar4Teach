package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    int x, y;                                                           // 位置
    TankClient tc;
    Direction dir = Direction.STOP;                                     // 坦克方向
    Direction ptDir = Direction.D;                                      // 炮筒方向
    private static int WIDTH = 30;                                      // 大小
    private static int HEIGHT = 30;
    private static int XSPEED = 10;                                     // 速度
    private static int YSPEED = 10;

    private boolean bL = false, bU = false, bR = false, bD = false;     // 方向

    public Tank (int x, int y, TankClient tc) {                                        // 构造方法
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g) {                                      // 画法
        Color c =g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    private void move() {
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
        tcDir();
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
        tcDir();

    }

    public void tcDir() {
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
        Missile m = new Missile(x + WIDTH/3, y + HEIGHT/3, ptDir);
        tc.msList.add(m);
    }
}
