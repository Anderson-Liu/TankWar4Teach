package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.awt.*;

public class TankClient extends Frame {
    private static final int WIDTH = 600;
    private static final int HEIGTH = 400;



    public static void main(String args[]) {
        new TankClient().launchFrame();
    }

    public void launchFrame() {
        this.setSize(WIDTH, HEIGTH);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setResizable(false);
    }
}
