package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {
    private static final int WIDTH = 600;                       // 大小
    private static final int HEIGTHT = 400;
    private static final int x = 400;                           // 位置
    private static final int y = 300;

    Tank myTank = new Tank(200, 200);




    public static void main(String args[]) {
        new TankClient().launchFrame();
    }

    public void launchFrame() {                                 // 初始化界面
        this.setSize(WIDTH, HEIGTHT);                           // 大小
        this.setLocation(x, y);                                 // 位置
        this.setBackground(Color.WHITE);                        // 背景
        this.setVisible(true);                                  // 是否可见
        this.setResizable(true);                                // 能否改变窗口大小
        this.addWindowListener(new WindowAdapter() {            // 窗口事件监听
            @Override
            public void windowClosing(WindowEvent e) {          // 匿名类
                super.windowClosing(e);
                System.exit(0);
            }
        });

        new Thread(new PaintThread()).start();
        new Thread(new KeyThread(this)).start();
    }


    @Override
    public void paint(Graphics g) {                             // 画法
        super.paint(g);
        myTank.draw(g);
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

    private class KeyMonitor extends KeyAdapter {

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
