package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Lilypad extends Plant{
    public Lilypad(int x, int y) {
        super("Lilypad", 40, 10, x, y, 80, 80);
        price = 25;
    }

    public Lilypad(int x, int y, String gambarGif) {
        super("Lilypad", 40, 10, x, y, 80, 80, gambarGif);
        price = 25;
    }

    public Lilypad(int x, int y, ArrayList<File> gambarFrame) {
        super("Lilypad", 40, 10, x, y, 80, 80, gambarFrame);
        price = 25;
    }
}
