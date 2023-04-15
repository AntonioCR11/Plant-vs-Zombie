package com.company;

import com.company.Asset.units.zombies.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZombiePlaceholder extends JLabel implements Runnable{
    private volatile boolean runThread;
    private int hp;
    private int gridX;
    private int gridY;
    private int width, height, speed;

    // ==== Zombie Protection Label ====
    private JLabel protection;
    private int protectionWidth, protectionHeight;

    Zombie thisZombie;
    PlantPlaceholder tempPlant;
    boolean eat = false;

    Clip onEvent; // LOCAL VARIABLE FOR ON-EVENT SOUND

    // ==== Constructor ====
    public ZombiePlaceholder(int x, int y) {
        this();
        this.gridX = x;
        this.gridY = y;
    }

    public ZombiePlaceholder() {
        runThread = true;
        this.hp = 0;
        this.gridX = 0;
        this.gridY = 0;
        thisZombie = null;

        // ==== Protection Initialization ====
        protection = new JLabel();
        protection.setLocation(this.getX(), this.getY());
        protection.setEnabled(false);
        protectionWidth = 0;
        protectionHeight = 0;
    }

    public void onEventSound(String soundName) { // SOUND PLAYER
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            onEvent = AudioSystem.getClip( );
            onEvent.open(audioInputStream);
            onEvent.start( );
        } catch(Exception ex) { ex.printStackTrace( ); }
    }

    public void checkZombieType(){
        if (thisZombie instanceof FootballZombie){
            if (eat) { height = 155; width = 125; speed = 35; }
            else { height = 170; width = 145; speed = 35; }
        }
        else if(thisZombie instanceof NewspaperZombie){
            if (eat) { height = 150; width = 120; speed = 80; }
            else { height = 165; width = 135; speed = 80; }
        }
        else if(thisZombie instanceof DuckyZombie){
            height = 145; width = 105;speed = 65;
        }
        else{
            height = 145; width = 105;speed = 65;

            // Cek apakah zombie memiliki proteksi
            if (thisZombie instanceof ConeheadZombie) {
                protectionWidth = 60;
                protectionHeight = 65;
                protection.setLocation(getX(), getY());
            }
            else if (thisZombie instanceof BucketZombie) {
                protectionWidth = 65;
                protectionHeight = 65;
            }
            protection.setSize(protectionWidth, protectionHeight);
            displayProtection();
        }
        setSize(width,height);
    }

    public void displayZombo(MapProperty mapProperty) {
        checkZombieType(); // Tentukan ukuran zombie

        // ==== Tampilkan icon zombie
        File f = new File(thisZombie.getGif());
        URL img = null;
        try {
            img = f.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        setIcon(icon);
    }

    public void displayProtection() {
        String path = "";
        if (thisZombie != null) {
            // Dapatkan icon proteksi zombie
            if (thisZombie instanceof ConeheadZombie) {
                path = "src/com/company/Asset/animation/Cone.png";
            }
            else if (thisZombie instanceof BucketZombie) {
                path = "src/com/company/Asset/animation/Bucket.png";
            }

            // ==== Tampilkan icon proteksi zombie ====
            if (!path.equals("")) {
                File f = new File(path);
                URL img = null;
                try {
                    img = f.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(img);
                icon.setImage(icon.getImage().getScaledInstance(protectionWidth, protectionHeight, Image.SCALE_DEFAULT));
                protection.setEnabled(true); // Memberitahu bahwa zombie memiliki proteksi
                protection.setIcon(icon);
            }
        }
    }

    @Override
    public void run() {
        int delay;
        AtomicBoolean byur = new AtomicBoolean(false);
        boolean helmetless = false;     // Flag untuk animasi proteksi jatuh

        while(!Thread.interrupted() && runThread){
            if (eat) { delay=1200; }
            else delay = speed;
            duckyZombie(byur);

            try{ Thread.sleep(delay); }
            catch (InterruptedException e){ System.out.println(e.getMessage()); }

            if (!eat) { setLocation(getX()-1, getY()); }
            else if(tempPlant!=null){ EatingPlant(tempPlant); }

            if (hp > 50) {
                // Menggerakkan proteksi saat nyawa zombie lebih banyak dari normal
                if (thisZombie instanceof ConeheadZombie) {
                    protection.setLocation(getX(), getY()-protectionHeight/2);
                } else if (thisZombie instanceof BucketZombie) {
                    protection.setLocation(getX(), getY()-protectionHeight/3);
                }
            }
            else if (!helmetless && protection.isEnabled()) {
                // ==== Animasi Proteksi jatuh ====
                helmetless = true;
                Thread protectionDestroyed = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int duration = 0;
                        while(!Thread.interrupted() && duration < 70) {
                            // Delay untuk animasi jatuh
                            if (!Thread.interrupted()) {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            protection.setLocation(protection.getX(), protection.getY()+height/70); // Dibagi 70 karena durasi 0.7 detik
                            duration++;
                        }

                        // Delay 1 detik
                        if (!Thread.interrupted()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        protection.setEnabled(false);
                        protection.setVisible(false);
                    }
                });
                protectionDestroyed.start();
            }
        }
    }

    private void duckyZombie(AtomicBoolean byur) {
        if(thisZombie instanceof DuckyZombie) { // KALAU MISAL DUCKY ZOMBIE KASI ANIMASI NYEMPLUNG
            if(this.getX() <= 850 && this.getX() > 200){
                if(!byur.get()){
                    byur.set(true);
                    // ==== Tampilkan icon zombie
                    File f = new File(((DuckyZombie) thisZombie).getByurGif());
                    URL img = null;
                    try {
                        img = f.toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(img);
                    icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                    setIcon(icon);
                }
            }
            else if(byur.get()) {
                byur.set(false);

                // ==== Tampilkan icon zombie ====
                File f = new File(thisZombie.getGif());
                URL img = null;
                try {
                    img = f.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(img);
                icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                setIcon(icon);
            }
        }
    }

    public void EatingPlant(PlantPlaceholder plant) {
        if(plant.isTherePlant()){
            if(plant.getHp()>plant.getThisPlant().getHp() || plant.getHp()<=0){
                plant.setHp(plant.getThisPlant().getHp());
            }
        }
        else if(plant.isThereLilypad() && plant.getWater()){
            if(plant.getHp()>plant.getLilypad().getHp() || plant.getHp()<=0){
                plant.setHp(plant.getLilypad().getHp());
            }
        }
        onEventSound("src/com/company/SoundEffect/eatingZombie.wav");
        int dmg = plant.getHp() - thisZombie.getDamage();
        plant.setHp(dmg);
        System.out.print(plant.getHp() + " ");
        if(plant.getHp() <= 0){
            onEvent.stop();
            if (plant.isTherePlant()) plant.removePlant();
            else if (plant.isThereLilypad()) plant.removeLilypad();
            onEventSound("src/com/company/SoundEffect/Swallow.wav");
            if (!plant.isThereLilypad() && !plant.isTherePlant()) {
                setEat(false, tempPlant);
            }
        }
    }

    // ==== Accessor dan Mutator ====
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int x) {
        this.gridX = x;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int y) {
        this.gridY = y;
    }

    public Zombie getThisZombie() {
        return thisZombie;
    }

    public void setThisZombie(Zombie thisZombie) {
        this.thisZombie = thisZombie;
        this.hp = thisZombie.getHp();
    }

    public JLabel getProtection() {
        return protection;
    }

    public boolean isEat() {
        return eat;
    }

    public boolean isRunThread() {
        return runThread;
    }

    public void setRunThread(boolean runThread) {
        this.runThread = runThread;
    }

    public void setEat(boolean running, PlantPlaceholder plant) {
        eat = running;
        if(!eat){
            if (thisZombie instanceof DuckyZombie) {
                duckyZombie(new AtomicBoolean(false));
            }
            else {
                File f = new File(thisZombie.getGif());
                URL img = null;
                try {
                    img = f.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(img);
                checkZombieType();
                icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
                setIcon(icon);
            }
        }
        else{
            tempPlant = plant;
            File f = new File(thisZombie.getEatGif());
            URL img = null;
            try {
                img = f.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ImageIcon icon = new ImageIcon(img);
            checkZombieType();
            icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            setIcon(icon);
        }
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void mutateHp(int hp) {
        this.hp += hp;
    }

    public int getHp() {
        return hp;
    }
}
