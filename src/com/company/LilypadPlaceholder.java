package com.company;

import com.company.Asset.units.plants.Lilypad;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class LilypadPlaceholder extends JLabel {
    private int hp;
    private Lilypad lilypad;
    private MapProperty mapProperty;

    private int width;
    private int height;

    private int offsetX;
    private int offsetY;

    // ==== Constructor ====
    public LilypadPlaceholder(MapProperty mapProperty) {
        lilypad = null;
        this.mapProperty = mapProperty;
        setVisible(true);
//        setBackground(new Color(255, 0, 0));
        setOpaque(false);

        resetSize();
    }

    // Display plant image
    public void displayLily() {
        File f = new File(lilypad.getGif());
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        //You have to convert it to URL because ImageIO just ruins the animation

//        int width = mapProperty.getGridSize();
        width = lilypad.getWidth();
        height = lilypad.getHeight();

        offsetX = width/4;
        offsetY = height/4;

        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setBounds(getX()-offsetX, getY()-offsetY+10, width, height);
        setIcon(icon);
    }

    // Reset PlantPlaceholder Size
    public void resetSize() {
        this.width = mapProperty.getGridSize();
        this.height = mapProperty.getGridSize();

        setBounds(getX()+offsetX, getY()+offsetY-10, width, height);
        offsetX = 0;
        offsetY = 0;
    }


    // ==== Accessor dan Mutator
    public boolean isThereLilypad() {
        if (lilypad == null) return false;
        else return true;
    }

    public boolean removeLily() {
        boolean success = false;
        if (isThereLilypad()) {
            lilypad = null;
            setIcon(null);
            resetSize();
            success = true;
        }
        return success;
    }

    public Lilypad getLilypad() {
        return lilypad;
    }

    public void setLilypad(Lilypad lilypad) {
        this.lilypad = lilypad;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
