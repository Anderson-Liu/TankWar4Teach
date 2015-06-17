package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {
    private static final int WIDTH = 600;                       // 大小
    private static final int HEIGTHT = 400;
    private static final int x = 400;                           // 位置
    private static final int y = 300;

    Tank t = new Tank(200, 200);




    public static void main(String args[]) {
        new TankClient().launchFrame();
    }

    public void launchFrame() {                                 // 初始化界面
        this.setSize(WIDTH, HEIGTHT);
        this.setLocation(x, y);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }


    @Override
    public void paint(Graphics g) {                             // 画法
        super.paint(g);
        t.draw(g);
    }
}
