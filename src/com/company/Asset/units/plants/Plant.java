package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;
import com.company.Asset.units.Entity;

public class Plant extends Entity implements Cloneable {
    protected int cooldown;
    protected int price;

    protected int width, height;

    public Plant(String name, int hp, int cooldown, int x, int y, int width, int height) {
        super(name, hp, x, y);
        this.cooldown = cooldown;
        this.width = width;
        this.height = height;
    }

    public Plant(String name, int hp, int cooldown, int x, int y, int width, int height, String gambarGif) {
        super(name, hp, x, y, gambarGif);
        this.cooldown = cooldown;
        this.width = width;
        this.height = height;
    }

    public Plant(String name, int hp, int cooldown, int x, int y, int width, int height, ArrayList<File> gambarFrame) {
        super(name, hp, x, y, new ArrayList<File>(gambarFrame));
        this.cooldown = cooldown;
        this.width = width;
        this.height = height;
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

    public int getPrice() {
        return price;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    // === Cloneable ===

    @Override
    protected Plant clone() throws CloneNotSupportedException {
        Plant deepCopy = new Plant(this.name, this.hp, this.cooldown, this.x, this.y, this.width, this.height, this.gambarGif);
        return deepCopy;
    }
}
