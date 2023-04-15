package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class DuckyZombie extends Zombie {
    private String byurGif;
    // Constructor Tanpa gambar
    public DuckyZombie(int x, int y) {
        super("Ducky Tube Zombie", 50, x, y, 5);
    }
    // Constructor dengan GIF
    public DuckyZombie(int x, int y, String gif, String byurGif, String eatGif) {
        super("Ducky Tube Zombie", 50, x, y, gif, eatGif, 5);
        this.byurGif = byurGif;
    }
    // Constructor dengan Frame
    public DuckyZombie(int x, int y, ArrayList<File> frame) {
        super("Ducky Tube Zombie", 50, x, y, frame, 5);
    }

    public String getByurGif() {
        return byurGif;
    }
}
