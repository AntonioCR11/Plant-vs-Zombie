package com.company;

import com.company.Asset.units.plants.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PlantPlaceholder extends JLabel implements Runnable{
    private int hp;
    private int gridX;
    private int gridY;

    private int width, height;
    private int offsetX, offsetY;

    private boolean water;
    private Plant thisPlant;
    private LilypadPlaceholder lilypad;
    private int counter;
    private boolean isEating = false;
    private volatile boolean runThread = true;

    MapProperty mapProperty;

    // ==== Constructor ====

    @Override
    public void run() {

    }

    public PlantPlaceholder(MapProperty mapProperty) {
        this.hp = 0;
        this.gridX = 0;
        this.gridY = 0;
        thisPlant = null;
        water = false;
        this.mapProperty = mapProperty;

        resetSize();

        // ==== Lilypad ====
        lilypad = new LilypadPlaceholder(mapProperty);
        lilypad.setBounds(mapProperty.getCoorX()[this.getGridX()], mapProperty.getCoorX()[this.getGridY()]+15, mapProperty.getGridSize(), mapProperty.getGridSize());
        setBounds(getX()-offsetX, getY()-offsetY+5, width, height);
//        frameCount = 0;
    }

    // Display plant image
    public void displayPlant(MapProperty mapProperty) {
        resetSize();
        File f = new File(thisPlant.getGif());
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        //You have to convert it to URL because ImageIO just ruins the animation

//        int width = mapProperty.getGridSize();
        width = thisPlant.getWidth();
        height = thisPlant.getHeight();

        offsetX = width / 4;
        offsetY = height / 4;

        setBounds(getX()-offsetX, getY()-offsetY, width, height);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setIcon(icon);
    }

    public void displayEat(MapProperty mapProperty){
        File f = new File("src/com/company/Asset/animation/plants/Chomper/ChomperEating/ChomperEating.gif");
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);

        width = thisPlant.getWidth();
        height = thisPlant.getHeight();
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setIcon(icon);
    }
    public void displayAtt(MapProperty mapProperty){
        File f = new File("src/com/company/Asset/animation/plants/Chomper/ChomperAttack/ChomperAttack.gif");
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);

        width = thisPlant.getWidth();
        height = thisPlant.getHeight();
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setIcon(icon);
    }

    // Reset PlantPlaceholder Size
    public void resetSize() {
        this.width = mapProperty.getGridSize();
        this.height = mapProperty.getGridSize();

        setBounds(getX()+offsetX, getY()+offsetY, width, height);
        offsetX = 0;
        offsetY = 0;
    }

    // ==== Accessor dan Mutator
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int x) {
        this.gridX = x;
        lilypad.setBounds(mapProperty.getCoorX()[this.getGridX()], mapProperty.getCoorY()[this.getGridY()]+20, mapProperty.getGridSize(), mapProperty.getGridSize());
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int y) {
        this.gridY = y;
        lilypad.setBounds(mapProperty.getCoorX()[this.getGridX()], mapProperty.getCoorY()[this.getGridY()]+20, mapProperty.getGridSize(), mapProperty.getGridSize());
    }

    public Plant getThisPlant() {
        return thisPlant;
    }

    public boolean removePlant() {
        boolean success = false;
        if (isTherePlant()) {
            thisPlant = null;
            setIcon(null);
            resetSize();
            success = true;
            runThread = false;
        }
        else if (this.lilypad.removeLily()) {
            success = true;
        }
        return success;
    }

    public boolean isTherePlant() {
        if (thisPlant == null) return false;
        else return true;
    }

    public void setThisPlant(Plant thisPlant) {
        this.thisPlant = thisPlant;
    }

    public boolean getWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isThereLilypad() {
        return lilypad.isThereLilypad();
    }
    public Lilypad getLilypad() {
        return this.lilypad.getLilypad();
    }

    public LilypadPlaceholder getLilypadPlaceholder() {
        return this.lilypad;
    }
    public void setLilypad(Lilypad lilypad) {
        this.lilypad.setLilypad(lilypad);
    }
    public void removeLilypad() {
        this.getLilypadPlaceholder().removeLily();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isRunThread() {
        return runThread;
    }

    public void setRunThread(boolean runThread) {
        this.runThread = runThread;
    }

    public boolean isEating() {
        return isEating;
    }

    public void setEating(boolean eating) {
        isEating = eating;
    }
}
