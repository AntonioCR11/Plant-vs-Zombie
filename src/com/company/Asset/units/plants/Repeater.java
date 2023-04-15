package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Repeater extends Plant implements Dealer{
    private final int attSpeed,dmg;

    public Repeater(int x, int y) {
        super("Pea Shooter", 40, 5, x, y, 100, 100);
        price = 200;
        attSpeed = 1;
        dmg=5;
    }
    public Repeater(int x, int y, String gambarGif) {
        super("Pea Shooter", 40, 5, x, y, 100, 100, gambarGif);
        price = 200;
        attSpeed = 1;
        dmg=5;
    }
    public Repeater(int x, int y, ArrayList<File> gambarFrame) {
        super("Repeater", 40, 5, x, y, 100, 100, gambarFrame);
        price = 200;
        attSpeed = 1;
        dmg=5;
    }

    @Override
    public int giftDamage() { return dmg; }
}
