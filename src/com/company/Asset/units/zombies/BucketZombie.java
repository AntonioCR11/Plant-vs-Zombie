package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class BucketZombie extends Zombie {
    // Constructor Tanpa gambar
    public BucketZombie(int x, int y) {
        super("Bucket Head Zombie", 200, x, y, 5);
    }
    // Constructor dengan GIF
    public BucketZombie(int x, int y, String gif,String eatGif) {
        super("Bucket Head Zombie", 200, x, y, gif, eatGif, 5);
    }
    // Constructor dengan Frame
    public BucketZombie(int x, int y, ArrayList<File> frame, int movementSpeed) {
        super("Bucket Head Zombie", 200, x, y, frame, 5);
    }
}
