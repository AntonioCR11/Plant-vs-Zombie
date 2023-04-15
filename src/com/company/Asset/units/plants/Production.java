package com.company.Asset.units.plants;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Production extends JLabel implements Runnable{
    private int x,y,yDestination,lifeSpan = 5;
    private int value,timer,tipe;
    private boolean sunAlive = true;
    Random rand = new Random();
    private int[] YDes = {200,300,400};

    public Production(int x, int y, int value,int tipe) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.tipe=tipe;
        setSize(75,75);
        yDestination = YDes[rand.nextInt(3)];
    }

    public void displaySun(){
        File f = new File("src/com/company/Asset/animation/sun.png");
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        setIcon(icon);
    }

    @Override
    public void run() {
        if(tipe == 0){
            while(y<yDestination){
                try{
                    Thread.sleep(15);
                }catch (InterruptedException e){
                    System.out.println(e.getMessage());
                }
                setLocation(getX(),getY()+1);
                y++;
            }
        }
        while(lifeSpan>0){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            lifeSpan--;
        }
        sunAlive = false;
    }


    // GETTER SETTER
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getValue() {
        return value;
    }
    public int getTimer() { return timer; }
    public void setTimer(int timer) { this.timer = timer; }
    public boolean isSunAlive() { return sunAlive; }
    public void setSunAlive(boolean sunAlive) { this.sunAlive = sunAlive; }
}
