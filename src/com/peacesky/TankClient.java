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

    List<Missile> msList = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    Image offScreenImage = null;
    Tank myTank = new Tank(200, 200, true, this);
    Tank tank = new Tank(300, 300, false, this);




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

        new Thread(new PaintThread()).start();
        new Thread(new KeyThread(this)).start();
    }


    @Override
    public void paint(Graphics g) {                                         // 画法
        g.drawString("子弹数量：" + msList.size(), 20, 50);
        myTank.draw(g);
        tank.draw(g);
        for (int i=0; i < msList.size(); i++) {
            Missile m = msList.get(i);
            m.draw(g);
            m.hitTank(tank);
            m.hitTank(myTank);
        }

        for (int i=0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }
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
                    Thread.sleep(40);
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
