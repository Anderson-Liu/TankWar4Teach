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
    private static final int WIDTH = 600;                       // ��С
    private static final int HEIGTHT = 400;
    private static final int x = 400;                           // λ��
    private static final int y = 300;

    Image offScreenImage = null;
    Tank myTank = new Tank(200, 200);




    public static void main(String args[]) {
        new TankClient().launchFrame();
    }

    public void launchFrame() {                                 // ��ʼ������
        this.setSize(WIDTH, HEIGTHT);                           // ��С
        this.setLocation(x, y);                                 // λ��
        this.setBackground(Color.WHITE);                        // ����
        this.setVisible(true);                                  // �Ƿ�ɼ�
        this.setResizable(true);                                // �ܷ�ı䴰�ڴ�С
        this.addWindowListener(new WindowAdapter() {            // �����¼�����
            @Override
            public void windowClosing(WindowEvent e) {          // ������
                super.windowClosing(e);
                System.exit(0);
            }
        });

        new Thread(new PaintThread()).start();
        new Thread(new KeyThread(this)).start();
    }


    @Override
    public void paint(Graphics g) {                             // ����
//        super.paint(g);
        myTank.draw(g);
    }


    @Override
    public void update(Graphics g) {                                        // ��дupdateʵ��˫���彵����˸Ч��
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);               // ����һ�Ű�ֽ
        }
        Graphics gOffScreen = offScreenImage.getGraphics();                 // ��ȡ��ֽ�Ļ���gOffScreen
        Color c = gOffScreen.getColor();                                    // ���滭��ԭ������ɫ
        gOffScreen.setColor(Color.DARK_GRAY);                               // ���û��ʵ���ɫ
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);                           // ��һ����������һ����ı���
        gOffScreen.setColor(c);                                             // �����������ѻ�����ɫ��ԭ
        paint(gOffScreen);                                                  // ������������ֽ��
        g.drawImage(offScreenImage, 0, 0, null);                            // �����Ŵ����˱�����ͼƬ����ǰ̨ȥ
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

    private class KeyMonitor extends KeyAdapter {                   // �ڲ���

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
