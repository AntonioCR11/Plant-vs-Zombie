package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Peashooter extends Plant implements Dealer {
    private final int attSpeed,dmg;

    public Peashooter(int x, int y) {
        super("Pea Shooter", 40, 5, 80, 80, x, y);
        price = 100;
        attSpeed = 2;
        dmg=5;
    }
    public Peashooter(int x, int y, String gambarGif) {
        super("Pea Shooter", 60, 5, x, y, 80, 80, gambarGif);
        price = 100;
        attSpeed = 2;
        dmg=5;
    }
    public Peashooter(int x, int y, ArrayList<File> gambarFrame) {
        super("Pea Shooter", 40, 5, x, y, 80, 80, gambarFrame);
        price = 100;
        attSpeed = 2;
        dmg=5;
    }

    @Override
    public int giftDamage() { return dmg; }
}
