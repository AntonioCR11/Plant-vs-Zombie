package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class ConeheadZombie extends Zombie{
    // Constructor Tanpa gambar
    public ConeheadZombie(int x, int y) {
        super("Conehead Zombie", 125, x, y, 5);
    }
    // Constructor dengan GIF
    public ConeheadZombie(int x, int y, String gif,String eatGif) {
        super("Conehead Zombie", 125, x, y, gif,eatGif, 5);
    }
    // Constructor dengan Frame
    public ConeheadZombie(int x, int y, ArrayList<File> frame) {
        super("Conehead Zombie", 125, x, y, frame, 5);
    }

}
