package com.peacesky;/*
 * Copyright (c) 2015. Peacesky.com Anderson_Liu
 */

import java.io.IOException;
import java.util.Properties;

public class PropertyMngr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMngr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ��չ��췽�� ʹ���޷���new ֻ�ܱ�ֱ�ӵ��þ�̬����
    private PropertyMngr() {
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
