package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Chomper extends Plant implements Dealer{
    boolean att;
    private final int eatDuration,dmg;
    public Chomper(int x, int y) {
        super("Chomper", 40, 15, 80, 80, x, y);
        price = 150;
        att = true;eatDuration=30;dmg=300;
    }

    public Chomper(int x, int y, String gambarGif) {
        super("Chomper", 40, 15, x, y, 100, 100, gambarGif);
        price = 150;
        att = true;eatDuration=30;dmg=300;
    }

    public Chomper(int x, int y, ArrayList<File> gambarFrame) {
        super("Chomper", 40, 15, x, y, 100, 100, gambarFrame);
        price = 150;
        att = true;eatDuration=30;dmg=300;
    }

    @Override
    public int giftDamage() {
        return dmg;
    }

    public int getEatDuration() {
        return eatDuration;
    }
}
