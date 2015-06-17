package com.peacesky;
/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankClient extends Frame {
    public static final int WIDTH = 600;                                   // 大小
    public static final int HEIGHT = 400;
    private static final int x = 400;                                       // 位置
    private static final int y = 300;
    private static int tank_Gap = 50;

    List<Missile> msList = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();

    int initTankCount;
    Image offScreenImage = null;
    Tank myTank = new Tank(200, 200, true, this);
    Wall wall_1 = new Wall(100, 150, 15, 200, this);
    Wall wall_2 = new Wall(200, 130, 200, 15, this);




    public static void main(String args[]) {
        new TankClient().launchFrame();
    }

    public void launchFrame() {                                             // 初始化界面
        this.setSize(WIDTH, HEIGHT);                                        // 大小
        this.setLocation(x, y);                                             // 位置
        this.setBackground(Color.WHITE);                                    // 背景
        this.setVisible(true);                                              // 是否可见
        this.setResizable(true);                                            // 能否改变窗口大小
        this.addWindowListener(new WindowAdapter() {                        // 窗口事件监听
            @Override
            public void windowClosing(WindowEvent e) {                      // 匿名类
                super.windowClosing(e);
                System.exit(0);
            }
        });

        initTankCount = Integer.parseInt(PropertyMngr.getProperty("initCount"));
        for (int i=0; i < initTankCount; i++) {
            tanks.add(new Tank(300 + tank_Gap* (i + 1), 90, false, this));
        }
        new Thread(new PaintThread()).start();
        new Thread(new KeyThread(this)).start();
    }


    @Override
    public void paint(Graphics g) {                                         // 画法
        g.drawString("子弹数量：" + msList.size(), 20, 50);
        g.drawString("爆炸数量：" + explodes.size(), 20, 70);
        g.drawString("敌方坦克数量：" + tanks.size(), 20, 90);
        g.drawString("主战坦克生命值" + myTank.life, 20, 110);

        myTank.draw(g);
        myTank.hitWall(wall_1);
        myTank.hitWall(wall_2);
        myTank.hitEach(tanks);

        for (int i=0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.draw(g);
            tank.hitWall(wall_1);
            tank.hitWall(wall_2);
            tank.hitEach(tanks);
        }

        for (int i=0; i < msList.size(); i++) {
            Missile m = msList.get(i);
            m.draw(g);
            m.hitTanks(tanks);
            m.hitTank(myTank);
            m.hitWall(wall_1);
            m.hitWall(wall_2);
        }

        for (int i=0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

        wall_1.draw(g);
        wall_2.draw(g);
    }


    @Override
    public void update(Graphics g) {                                        // 重写update实现双缓冲降低闪烁效果
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);               // 创建一张白纸
        }
        Graphics gOffScreen = offScreenImage.getGraphics();                 // 获取白纸的画笔gOffScreen
        Color c = gOffScreen.getColor();                                    // 保存画笔原来的颜色
        gOffScreen.setColor(Color.WHITE);                                   // 设置画笔的颜色
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);                           // 画一个和主界面一样大的背景
        gOffScreen.setColor(c);                                             // 画画结束，把画笔颜色还原
        paint(gOffScreen);                                                  // 将背景画到白纸上
        g.drawImage(offScreenImage, 0, 0, null);                            // 将这张带有了背景的图片画到前台去
    }

    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(50);
                    repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {                           // 内部类

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    private class KeyThread implements Runnable {

        TankClient tc;

        public KeyThread (TankClient tc) {
            this.tc = tc;
        }

        @Override
        public void run() {
            tc.addKeyListener(new KeyMonitor());
        }
    }
}
