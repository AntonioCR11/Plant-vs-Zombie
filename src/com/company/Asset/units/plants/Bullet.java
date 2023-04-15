package com.company.Asset.units.plants;

import com.company.MapProperty;
import com.company.ZombiePlaceholder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Bullet extends JLabel implements Runnable {
    private int gridY;
    private int damage;
    private int speed;
    ArrayList<ZombiePlaceholder> zombieList;
    private volatile boolean runThread;
    Clip onClick;

    public void setPropertySize(Bullet prop, String filename,int width,int height) {
        // SETTING PROPERTY IMAGE,(JLABEL,etc) AND SIZE
        File f = new File(filename);
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        prop.setIcon(icon);
    }
    public void onClickSound(String soundName) { // MUSIC PLAYER
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            onClick = AudioSystem.getClip();
            onClick.open(audioInputStream);
            onClick.start();
        } catch(Exception ex) { ex.printStackTrace( ); }
    }
    public Bullet(int x, int y, int damage, ArrayList<ZombiePlaceholder> zombieList, MapProperty mapProperty) {
        this.damage = damage;
        this.speed = 15;
        runThread = true;
        this.zombieList = zombieList;
        gridY = -1;

        setVisible(true);

        // ==== Debugging Purposes ====
        //setBackground(new Color(50, 255, 50));
        //setOpaque(true);

        setLocation(x, y);
        setSize(25,25);
        setPropertySize(this,"src/com/company/Asset/animation/PeaBullet.png",25,25);

        // ==== Menentukan posisi bullet dalam baris yang mana ====
        int yCtr = 0;
        while(yCtr < mapProperty.getMapRow()-1 && gridY == -1) {
            if (mapProperty.getCoorY()[yCtr] <= this.getY() && mapProperty.getCoorY()[yCtr+1] > this.getY()) gridY = yCtr;
            yCtr++;
        }
        if (mapProperty.getCoorY()[mapProperty.getMapRow()-1] <= this.getY() && mapProperty.getCoorY()[mapProperty.getMapRow()-1]+mapProperty.getGridSize() > this.getY()) {
            gridY = mapProperty.getMapRow()-1;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public boolean isRunThread() { return runThread; }
    public void setRunThread(boolean runThread) { this.runThread = runThread; }

    // ==== Runnable Method ====
    @Override
    public void run() {
        while(runThread) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
            setLocation(this.getX()+speed, this.getY());

            boolean isHit = false;
            int zCtr = 0;
            while(zCtr < zombieList.size() && !isHit) {
                ZombiePlaceholder zombieHolder = zombieList.get(zCtr);

                if (this.getX() >= zombieHolder.getX() && this.getX() <= zombieHolder.getX()+zombieHolder.getWidth()/2 &&
                    this.gridY == zombieHolder.getGridY() && zombieHolder.getHp() > 0) {

                    // Men-damage zombie dan cek apakah sudah mati
                    zombieHolder.mutateHp(-(damage));
                    isHit = true;
                    int idx = new Random().nextInt(2)+1;
                    onClickSound("src/com/company/SoundEffect/bulletHit"+idx+".wav");

                    if (zombieHolder.getHp() <= 0) {
                        zombieHolder.setRunThread(false);
                        zombieHolder.setVisible(false);
                        zombieHolder.setEnabled(false);
                    }

                    runThread = false;
                    setVisible(false);
                    setEnabled(false);
                }

                zCtr++;
            }
//            for (ZombiePlaceholder zombieHolder : zombieList) {
//                if (this.getX() >= zombieHolder.getX() && this.getX() <= zombieHolder.getX()+zombieHolder.getWidth()/2 &&
//                    this.gridY == zombieHolder.getGridY() && zombieHolder.getHp() > 0) {
//                    zombieHolder.mutateHp(-(damage));
//                    runThread = false;
//                    setVisible(false);
//                    setEnabled(false);
//                }
//                if (zombieHolder.getHp() <= 0) {
//
//                }
//            }
            if (getX() > 1000) runThread = false;
        }
    }
}
