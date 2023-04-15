package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Wallnutt extends Plant{
    public Wallnutt(int x, int y) {
        super("Walnutt", 600, 25, x, y, 80, 80);
        price = 50;
    }

    public Wallnutt(int x, int y, String gambarGif) {
        super("Walnutt", 600, 25, x, y, 80, 80, gambarGif);
        price = 50;
    }

    public Wallnutt(int x, int y, ArrayList<File> gambarFrame) {
        super("Walnutt", 600, 25, x, y, 80, 80, gambarFrame);
        price = 50;
    }
}
