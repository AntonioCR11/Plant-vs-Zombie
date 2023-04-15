package com.company.Asset.units;

import java.io.File;
import java.util.ArrayList;

public abstract class Entity {
    protected String name;
    protected int hp;
    protected int x, y;
    protected ArrayList<File> gambarFrame = new ArrayList<File>();
    protected String gambarGif;

    // Constructor Tanpa gambar
    public Entity(String name, int hp, int x, int y) {
        this.name = name;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.gambarGif = "";
    }
    // Constructor dengan GIF
    public Entity(String name, int hp, int x, int y, String gambarGif) {
        this.name = name;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.gambarGif = gambarGif;
    }
    // Constructor dengan Frame
    public Entity(String name, int hp, int x, int y, ArrayList<File> gambarFrame) {
        this.name = name;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.gambarFrame = gambarFrame;
        this.gambarGif = "";
    }

    // Accessor dan Mutator
    //Nama
    public abstract String getName();
    public abstract void setName(String name);

    // Health Point
    public abstract int getHp();
    public abstract void setHp(int hp);
    public abstract void mutateHp(int hp);

    // Koordinat
    public abstract int getX();
    public abstract void setX(int x);
    public abstract void mutateX(int x);

    public abstract int getY();
    public abstract void setY(int y);
    public abstract void mutateY(int y);

    public int[] getCoor() {
        int[] coordinates = {this.x, this.y};
        return coordinates;
    }
    public void setCoor(int[] coor) {
        this.x = coor[0];
        this.y = coor[1];
    }

    // Gambar Entity
    public String getGif() {
        return this.gambarGif;
    }
    public void setGif(String newGif) {
        this.gambarGif = newGif;
    }

    public ArrayList<File> getFrame() {
        return this.gambarFrame;
    }
    public void setGif(ArrayList<File> newFrame) {
        this.gambarFrame = newFrame;
    }
}
