package com.company.Asset.units.zombies;

import java.io.File;
import java.util.ArrayList;

public final class NewspaperZombie extends Zombie implements Special {
    String angryGif;
    boolean specialUsed;
    // Constructor Tanpa gambar
    public NewspaperZombie(int x, int y) {
        super("Newspaper Zombie", 65, x, y, 5);
        specialUsed = false;
    }
    // Constructor dengan GIF
    public NewspaperZombie(int x, int y, String gif, String angryGif, String eatGif) {
        super("Newspaper Zombie", 65, x, y, gif, eatGif, 5);
        specialUsed = false;
        this.angryGif = angryGif;
    }
    // Constructor dengan Frame
    public NewspaperZombie(int x, int y, ArrayList<File> frame) {
        super("Newspaper Zombie", 65, x, y, frame, 5);
        specialUsed = false;
    }

    /**
     *  Spesial dari zombie ini: Saat HP berkurang mencapai sebuah threshold, yaitu 65,
     *  maka zombie akan menjadi cepat dan damage bertambah.
     */
    @Override
    public void checkThreshold() {
        if (this.hp < 50 && !specialUsed) {
            doSpecial();
            specialUsed = true;
        }
    }
    @Override
    public void doSpecial() {
        this.movementSpeed = 12;
        this.damage = 25;
    }

}
