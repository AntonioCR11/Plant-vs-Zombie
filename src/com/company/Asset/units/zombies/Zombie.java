package com.company.Asset.units.zombies;

import com.company.Asset.units.Entity;

import java.io.File;
import java.util.ArrayList;

public class Zombie extends Entity implements Cloneable {
    protected boolean isEating;
    protected int movementSpeed;
    protected int damage;
    protected String eatGif;

    // Constructor Tanpa gambar
    public Zombie(String name, int hp, int x, int y, int movementSpeed) {
        super(name, hp, x, y);
        isEating = false;
        this.movementSpeed = movementSpeed;
        damage = 15;
    }
    // Constructor dengan GIF
    public Zombie(String name, int hp, int x, int y, String gif,String eatGif, int movementSpeed) {
        super(name, hp, x, y, gif);
        this.eatGif = eatGif;
        isEating = false;
        this.movementSpeed = movementSpeed;
        damage = 15;
    }
    // Constructor dengan Frame
    public Zombie(String name, int hp, int x, int y, ArrayList<File> frame, int movementSpeed) {
        super(name, hp, x, y, frame);
        isEating = false;
        this.movementSpeed = movementSpeed;
        damage = 15;
    }

    //Accessor dan Mutator Properti
    //Nama
    @Override
    public String getName() { return name; }
    @Override
    public void setName(String name) { this.name = name; }

    // Health Point
    @Override
    public int getHp() { return hp; }
    @Override
    public void setHp(int hp) { this.hp = hp; }
    @Override
    public void mutateHp(int hp) { this.hp += hp; }

    // Koordinat
    @Override
    public int getX() { return x; }
    @Override
    public void setX(int x) { this.x = x; }
    @Override
    public void mutateX(int x) { this.x += x; }

    @Override
    public int getY() { return y; }
    @Override
    public void setY(int y) { this.y = y; }
    @Override
    public void mutateY(int y) { this.y += y; }

    // === Cloneable ===
    @Override
    protected Zombie clone() throws CloneNotSupportedException {
        Zombie deepCopy = new Zombie(this.name, this.hp, this.x, this.y, this.gambarFrame, this.movementSpeed);
        return deepCopy;
    }

    public boolean isEating() {
        return isEating;
    }

    public void setEating(boolean eating) {
        isEating = eating;
    }

    public String getEatGif(){return eatGif;}

    public int getDamage() {
        return damage;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }
}
