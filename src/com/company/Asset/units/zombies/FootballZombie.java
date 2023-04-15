package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class FootballZombie extends Zombie {
    // Constructor Tanpa gambar
    public FootballZombie(int x, int y) {
        super("Football Zombie", 225, x, y, 12);
    }
    // Constructor dengan GIF
    public FootballZombie(int x, int y, String gif, String eatGif) {
        super("Football Zombie", 225, x, y, gif, eatGif, 12);
    }
    // Constructor dengan Frame
    public FootballZombie(int x, int y, ArrayList<File> frame) {
        super("Football Zombie", 225, x, y, frame, 12);
    }
}
