package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class BasicZombie extends Zombie implements Cloneable {
    // Constructor Tanpa gambar
    public BasicZombie(int x, int y) {
        super("Basic Zombie", 50, x, y, 5);
    }
    // Constructor dengan GIF
    public BasicZombie(int x, int y, String gif,String eatGif) {
        super("Basic Zombie", 50, x, y, gif,eatGif, 5);
    }
    // Constructor dengan Frame
    public BasicZombie(int x, int y, ArrayList<File> frame) {
        super("Basic Zombie", 50, x, y, frame, 5);
    }

    @Override
    protected Zombie clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
