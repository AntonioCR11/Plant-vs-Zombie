package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Cherrybomb extends Plant implements Dealer {
    private final int dmg;
    public Cherrybomb(int x, int y) {
        super("Cherry Bomb", 300, 30, x, y, 250, 250);
        dmg=300;
        price = 150;
    }
    public Cherrybomb(int x, int y, String gambarGif) {
        super("Cherry Bomb", 300, 30, x, y, 250, 250, gambarGif);
        dmg=300;
        price = 150;
    }
    public Cherrybomb(int x, int y, ArrayList<File> gambarFrame) {
        super("Cherry Bomb", 300, 30, x, y, 250, 250, gambarFrame);
        dmg=300;
        price = 150;
    }

    @Override
    public int giftDamage() {
        return dmg;
    }
}
