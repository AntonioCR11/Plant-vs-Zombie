package com.company;

import com.company.Asset.units.plants.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class GameMap extends JFrame implements WindowListener{
    Random rand = new Random();

    // === THIS ===
    private MapProperty thisMapProperty;
    private String mode;

    /**
     * GUI Components
     */
    private String map;
    private JLayeredPane rootPanel; // Panel utama untuk menampilkan GUI
    private JPanel plantsPanel; // Panel untuk memilih tanaman dan jumlah matahari
    JLabel mapBackground = new JLabel(); // Label Background

    // ==== Placeholders ====
    private ArrayList<PlantPlaceholder> plantGrid = new ArrayList<>(); // ArrayList apakah posisi memiliki plant
    private ArrayList<ZombiePlaceholder> zombiePlace = new ArrayList<>(); // ArrayList Zombie
    private ArrayList<Bullet> bulletPlace = new ArrayList<>(); // ArrayList Bullet yang ditembakan

    private ArrayList<Thread> bulletThreadList = new ArrayList<>();
    private ArrayList<Thread> threadSList;

    // ==== SUN THREADLIST ====
    private ArrayList<Production> sunDrop = new ArrayList<>();
    private ArrayList<Thread> sunThreadList = new ArrayList<>();
    private ArrayList<Thread> sunProductingThread = new ArrayList<>();

    // ==== PLANT CARDS ====
    private JLabel Sunflower;
    private JLabel Peashooter;
    private JLabel Cherrybomb;
    private JLabel Wallnut;
    private JLabel Sunshroom;
    private JLabel Reapeter;
    private JLabel Chomper;
    private JLabel Lilypad;
    private JLabel SunHolder;
    private JLabel Shovel;
    private JLabel dragContainer;

    // ==== EXIT PANEL ====
    private JPanel exitPanel;
    private JLabel menuButton; // UNTUK TRIGGER EXIT PANEL
    private JLabel quitButton;
    private JLabel cancelButton;
    private JLabel exitMenu;

    // ==== GAMEOVER PANEL ====
    boolean isLose = false;
    private JPanel gameOverPanel;
    private JLabel theZombieAteYourBrain;
    private JLabel restartButton; // UNTUK TRIGGER VOID RESTART
    private JLabel backToMainMenuButton; // UNTUK TRIGGER VOID BACK TO MAIN MENU

    // ==== SUN PROPERTY ====
    public Timer RSPtimer;
    public Timer sunTimer;
    private int ctr=0, totalSun=50;
    private JLabel SunCtr; // LABEL UNTUK DISPLAY TOTAL SUN

    // ==== Zombie ====
    public boolean isSpawning = true;
    private int zCtr =0,counter,ctrZombie=0;
    private Timer timerGlobal;

    private String musicName;
    Clip clip; // LOCAL VARIABLE CLIP FOR BGM
    Clip onClick; // LOCAL VARIABLE FOR ON-CLICK EVENT SOUND

    //======== INDEX TANAMAN YANG DIPILIH =========
    private int idxPlant = -1;

    // ==== OTHER GUI COMPONENT ====
    private JLabel progressBar;
    private JLabel progressBarColor;

    private JLabel timeHolder;
    private int minute=0,second=0;
    private JLabel minuteValue; // UNTUK PENANDA MENIT SURVIVAL
    private JLabel secondValue; // UNTUK PENANDA DETIK SURVIVAL
    private JLabel RSP; // UNTUK ANIMASI READY SET PLANT!

    // Initialization of the GUI
    public GameMap(String map, String mode) {
        this.map = map;
        this.mode = mode;
        threadSList = new ArrayList<>();
        counter=0;
        setTitle("Plants vs Zombies");
        setSize(1000, 635);
        thisMapProperty = new MapProperty(map,this); // === Mempersiapkan properti dari map untuk game ===
        initComponent(map, thisMapProperty); // === Inisiasi semua komponen untuk UI ===
        sunGenrateInterval(); // ==== THREAD MATAHARI ====
        RSPanimation(map); // ==== Thread Zombie ====
        add(rootPanel);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(this);
    }

    // ======= SOUND EFFECT & BGM ========
    public void playSound(String soundName,int type) { // MUSIC PLAYER
        musicName = soundName;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
            if(type == 1) { clip.loop(Clip.LOOP_CONTINUOUSLY); }
        } catch(Exception ex) { ex.printStackTrace( ); }
    }
    public void onCLickSound(String soundName) { // MUSIC PLAYER
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            onClick = AudioSystem.getClip();
            onClick.open(audioInputStream);
            onClick.start();
        } catch(Exception ex) { ex.printStackTrace( ); }
    }

    // ======= PLANT CARD CLICKED ========
    public void CardMouseDragged(MouseEvent e,int idxPlant){
        dragContainer.setIcon(new ImageIcon(getClass().getResource("/com/company/DragDropImage/"+idxPlant+".png")));
        dragContainer.setLocation(e.getX()-20,e.getY()+(60*idxPlant));
    }
    public void CardMouseReleased(MouseEvent e,int  idxPlant, MapProperty mapProperty){
        dragContainer.setIcon(null);

        if (e.getX() >= mapProperty.getCoorX()[0] && e.getX() <= mapProperty.getCoorX()[8] + mapProperty.getGridSize() &&
                (e.getY()+50+(60*idxPlant)) >= mapProperty.getCoorY()[0] && (e.getY()+50+(60*idxPlant)) <= mapProperty.getCoorY()[mapProperty.getMapRow()-1] + mapProperty.getGridSize()) {
            // Dapatkan posisi Cursor dalam Grid
            int xPos = -1, yPos = -1;

            int ctr = 0;
            while(xPos == -1 && ctr < 9) {
                if (e.getX() >= mapProperty.getCoorX()[ctr] && e.getX() <= mapProperty.getCoorX()[ctr] + mapProperty.getGridSize()) xPos = ctr;
                ctr++;
            }
            ctr = 0;
            while(yPos == -1 && ctr < mapProperty.getMapRow()) {
                if (e.getY()+50+(60*idxPlant) >= mapProperty.getCoorY()[ctr] && e.getY()+50+(60*idxPlant) <= mapProperty.getCoorY()[ctr] + mapProperty.getGridSize()) yPos = ctr;
                ctr++;
            }

            if(idxPlant!=0){
                if(idxPlant>-1){ // KALAU -1 BRATI NGGA NGEDRAG APA"
                    // Jika ctrx dan ctry dibawah sembilan berarti berada di dalam sistem grid
                    if (xPos >= 0 && yPos >= 0) {
                        int plantPos = 9*yPos + xPos;

                        // TANAM DISINI...
                        if (!plantGrid.get(plantPos).isTherePlant()) {
                            if (plantGrid.get(plantPos).getWater()) {
                                // Kalau di air dan yang ditanam Lilypad
                                if (idxPlant == 8) {
                                    if (!plantGrid.get(plantPos).isThereLilypad()) {
                                        // Kalau matahari cukup, tanam tanaman
                                        if (totalSun-mapProperty.getPlantList().get(idxPlant-1).getPrice()>=0) {
                                            totalSun-=mapProperty.getPlantList().get(idxPlant-1).getPrice();
                                            SunCtr.setText(totalSun+"");

                                            onCLickSound("src/com/company/SoundEffect/plantPlantedWater.wav");
                                            plantGrid.get(plantPos).setLilypad((Lilypad) mapProperty.getPlantList().get(7));
                                            plantGrid.get(plantPos).getLilypadPlaceholder().displayLily();
                                        }
                                    }
                                }
                                // Kalau di air dan yang ditanam bukan Lilypad
                                else {
                                    if (plantGrid.get(plantPos).isThereLilypad()) {
                                        // Kalau matahari cukup, tanam tanaman
                                        if (totalSun-mapProperty.getPlantList().get(idxPlant-1).getPrice()>=0) {
                                            totalSun-=mapProperty.getPlantList().get(idxPlant-1).getPrice();
                                            SunCtr.setText(totalSun+"");

                                            // Masukan class tanaman ke Plantplaceholder dan tampilkan
                                            onCLickSound("src/com/company/SoundEffect/plantPlantedLand.wav");
                                            plantGrid.get(plantPos).setThisPlant(mapProperty.getPlantList().get(idxPlant-1));
                                            plantGrid.get(plantPos).displayPlant(mapProperty);

                                            // Kalau yang ditanam adalah sunflower
                                            if(idxPlant == 1) sunflower(plantGrid.get(plantPos));
                                            // Kalau yang ditanam adalah Chomper
                                            else if(idxPlant == 7) chomper(plantGrid.get(plantPos));
                                            // Kalau yang ditanam adalah cherrybomb
                                            else if (idxPlant == 3) cherryBomb(plantGrid.get(plantPos));
                                        }
                                    }
                                }
                            }
                            //Kalau bukan di air
                            else {
                                if (idxPlant < 8) {
                                    if (!plantGrid.get(plantPos).isTherePlant()) {
                                        // Kalau matahari cukup, tanam tanaman
                                        if (totalSun-mapProperty.getPlantList().get(idxPlant-1).getPrice()>=0) {
                                            totalSun -= mapProperty.getPlantList().get(idxPlant - 1).getPrice();
                                            SunCtr.setText(totalSun + "");

                                            // Masukan class tanaman ke Plantplaceholder dan tampilkan
                                            plantGrid.get(plantPos).setThisPlant(mapProperty.getPlantList().get(idxPlant-1));
                                            plantGrid.get(plantPos).displayPlant(mapProperty);

                                            // Kalau yang ditanam adalah sunflower
                                            if(idxPlant == 1) sunflower(plantGrid.get(plantPos));
                                                // Kalau yang ditanam adalah sunshroom
                                            else if(idxPlant == 5) sunflower(plantGrid.get(plantPos));
                                                // Kalau yang ditanam adalah Chomper
                                            else if(idxPlant == 7) chomper(plantGrid.get(plantPos));
                                                // Kalau yang ditanam adalah cherrybomb
                                            else if (idxPlant == 3) cherryBomb(plantGrid.get(plantPos));
                                            onCLickSound("src/com/company/SoundEffect/plantPlantedLand.wav");
                                        }
                                    }
                                }
                            }
                            // Akhir dari apakah di air atau tidak
                        }
                    }
                }
            }
            else if (idxPlant==0){
                // CHECKING APAKAH ADA TANAMAN PADA POSISI INI...
                if (xPos >= 0 && yPos >= 0) {
                    int plantPos = 9*yPos + xPos;

                    // Delete tanaman kalau ada...
                    if (plantGrid.get(plantPos).removePlant()) {
                        onCLickSound("src/com/company/SoundEffect/shovelPickedUp.wav");
                        for (ZombiePlaceholder zombiePlaceholder : zombiePlace) {
                            int min = plantGrid.get(plantPos).getX() - mapProperty.getGridSize()/2;
                            int max = plantGrid.get(plantPos).getX() + mapProperty.getGridSize()/2;
                            if (zombiePlaceholder.getX() > min && zombiePlaceholder.getX() < max && zombiePlaceholder.getY()-15 == plantGrid.get(plantPos).getY() - mapProperty.getGridMarginY()) {
                                zombiePlaceholder.setEat(false,plantGrid.get(plantPos));
                            }
                        }
                    }
                }
            } else if(totalSun-mapProperty.getPlantList().get(idxPlant-1).getPrice() < 0 ){ // MATAHARI TIDAK CUKUP
                onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");
            }
        }
    }

    // ======= READY SET PLANT ANIMATION =======
    public void RSPanimation(String map){
        plantsPanel.setVisible(false);
        RSPtimer = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ctr<3){
                    ctr++;onCLickSound("src/com/company/Asset/Warning/"+ctr+".wav");
                    RSP.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/Warning/"+(ctr)+".png")));
                } else{
                    plantsPanel.setVisible(true);
                    menuButton.setVisible(true);
                    RSP.setVisible(false);
                    RSPtimer.stop();
                    if(map.equals("Day")){ playSound("src/com/company/Soundtrack/DayStageNormal.wav",1); }
                    else if(map.equals("Night")){ playSound("src/com/company/Soundtrack/NightStageNormal.wav",1); }
                    else if(map.equals("Pool")){ playSound("src/com/company/Soundtrack/PoolStageNormal.wav",0); }
                    ctr=0; firstZombieSpawnTimer();
                }
            }
        });
        RSPtimer.start();
    }
    public void WaveAnimation(String filename){
        clip.stop();
        playSound("src/com/company/Soundtrack/"+map+"StageFinal.wav",0); // UNTUK GANTI LAGU NYA JADI LAGUWAVE
        onCLickSound("src/com/company/SoundEffect/"+filename+".wav");
        RSP.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/Warning/"+filename+".png")));
        RSP.setVisible(true);
        RSPtimer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RSP.setVisible(false);
                RSPtimer.stop();
            }
        });
        RSPtimer.start();
    }
    public void firstZombieSpawnTimer(){
        counter=0;
        RSPtimer = new Timer(1000, new ActionListener() { // DIKASI SELANG WAKTU 5 DETIK UNTUK ZOMBIE PERTAMA MUNCUL
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ctr<10){ System.out.print(ctr + " ");ctr++;}
                else{
                    RSPtimer.stop();
                    onCLickSound("src/com/company/SoundEffect/startWave.wav");
                    if(mode.equals("Adventure")){
                        progressBar.setVisible(true);
                        progressBarColor.setVisible(true);
                    }
                    else{ timeHolder.setVisible(true); }
                    startTimerGlobal();
                }
            }
        });
        RSPtimer.start();
    }
    public void startTimerGlobal(){
        timerGlobal = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CHECKING LOOP
                zombieCek(thisMapProperty); // Cek zombie makan tanaman
                deleteZombie();
                isSunAlive(); // CEK APAKAH LIFE SPAN MATAHARI MASIH ADA
                checkLane(); // Cek tanaman menyerang zombie

                if(!clip.isActive() && !musicName.equals("src/com/company/Soundtrack/"+map+"StageNormal.wav")){
                    playSound("src/com/company/Soundtrack/"+map+"StageNormal.wav",1);
                } // UNTUK KEMBALIKAN LAGU NYA JADI NORMAL
                int delay; // DELAY SPAWN ANTAR ZOMBIE
                if(counter<60) delay=12;
                else if(counter<150) delay=10;
                else if(counter<240) delay=8;
                else delay=5;

                if(mode.equals("Adventure")){
                    if( isSpawning ){ // APAKAH SUDAH SAMPAI FINAL WAVE ( KALO IYA JANGAN SPAWN LAGI )
                        progressBarColor.setLocation(progressBarColor.getX()-(progressBar.getWidth()-10)/210, progressBarColor.getY());
                        progressBarColor.setSize(progressBarColor.getWidth()+(progressBar.getWidth()-10)/210, 20);
                        isNowWave(thisMapProperty);
                        if( counter % delay == 0 ) randomZombie(thisMapProperty);
                    }
                    else if(!isSpawning && zombiePlace.size() == 0 ){
                        Thread winThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RSPtimer.stop();
                                timerGlobal.stop();
                                sunTimer.stop();
                                clip.stop();
                                playSound("src/com/company/Soundtrack/WinJingle.wav", 0);
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }
                                forceClose();//Mematikan Thread yang masih berjalan dengan paksa
                                Main.startMain();
                            }
                        });
                        winThread.start();
                    }
                } else { // KALO SURVIVAL SPAWN TERUS
                    updateSurvivalTimer(thisMapProperty);
                    if( counter % delay == 0 ) randomZombie(thisMapProperty);
                }
                counter++;
            }
        });
        timerGlobal.start();
    }
    public void updateSurvivalTimer(MapProperty mapProperty){
        second++;
        if( second == 60 ){
            minute++;
            minuteValue.setText(minute+" : ");
            second = 0;
            secondValue.setText(second+"");
        }else{ secondValue.setText(second+""); }
        if(second == 59 && (minute+1)%2==0 && minute>0){ // JIKA KELIPATAN 2 MENIT SPAWN WAVE
            WaveAnimation("partWave");
            for (int j = 0; j < 10; j++) {
                spawnZombie(mapProperty);
            }
        }
        rootPanel.repaint();
        rootPanel.revalidate();
    }

    // ===== Periksa Lajur untuk mencari Zombie =====
    private void checkLane() {
        /**
         *  Untuk setiap plantPlaceholder yang ada di grid,
         *  periksa apakah ada zombie yang ada di lajur yang sama.
         *  Untuk peashooter dan Repeater, periksa lane sejauh mungkin didepannya.
         *  Untuk Chomper, periksa satu grid didepannya.
         */
        for (PlantPlaceholder plantHolder : plantGrid) {
            // Periska apakah ada tanaman
            if (plantHolder.getThisPlant() != null) {
                // Jika Peashooter check semua zombie yang satu lajur dengannya.
                if (plantHolder.getThisPlant() instanceof com.company.Asset.units.plants.Peashooter) {
                    int zCheckCtr = 0;
                    while (zCheckCtr < zombiePlace.size()) {
                        if (zombiePlace.get(zCheckCtr).getHp() > 0 && zombiePlace.get(zCheckCtr).getGridY() == plantHolder.getGridY()) {
                            // Jika satu lajur dan hidup, periksa apakah ada didepannya.
                            if (zombiePlace.get(zCheckCtr).getX() > plantHolder.getX() + thisMapProperty.getGridSize()/2) {
                                // Jika ada di depannya, buat bullet untuk menyerang!
                                createBullet(plantHolder);
                            }
                        }
                        zCheckCtr++;
                    }
                }
                // Jika Repeater check semua zombie dalam 1 lajur yang sama dengannya
                else if (plantHolder.getThisPlant() instanceof Repeater) {
                    int zCheckCtr = 0;
                    while (zCheckCtr < zombiePlace.size()) {
                        if (zombiePlace.get(zCheckCtr).getGridY() == plantHolder.getGridY()) {
                            // Jika satu lajur, periksa apakah ada didepannya.
                            if (zombiePlace.get(zCheckCtr).getX() > plantHolder.getX() + thisMapProperty.getGridSize()/2) {
                                // Jika ada di depannya, buat 2 bullet untuk menyerang!
                                Thread doubleShoot = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        createBullet(plantHolder);

                                        try {
                                            if (!Thread.interrupted()) Thread.sleep(250);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        createBullet(plantHolder);
                                    }
                                });

                                doubleShoot.start();
                            }
                        }
                        zCheckCtr++;
                    }
                }
            }
        }

        // ==== Hapus semua Bullet yang ada dimap yang tidak aktif ====
        int bulletCtr = 0;
        while(bulletCtr < bulletPlace.size()) {
            if (!bulletPlace.get(bulletCtr).isRunThread()) {
                rootPanel.remove(bulletPlace.get(bulletCtr));
                rootPanel.repaint();
                bulletPlace.remove(bulletCtr);
                bulletCtr--;
            }
            bulletCtr++;
        }
//        System.out.println("Jumlah bullet: " + bulletPlace.size());
    }

    // Buat Bullet untuk ditembakkan Peashooter Family
    private void createBullet(PlantPlaceholder plantHolder) {
        Bullet newBullet = null;

        if (plantHolder.getThisPlant() instanceof Peashooter) {
            newBullet = new Bullet(plantHolder.getX()+thisMapProperty.getGridSize()/2, plantHolder.getY(), ((com.company.Asset.units.plants.Peashooter)plantHolder.getThisPlant()).giftDamage(), zombiePlace, thisMapProperty);
        }
        else if (plantHolder.getThisPlant() instanceof Repeater) {
            newBullet = new Bullet(plantHolder.getX()+thisMapProperty.getGridSize()/2, plantHolder.getY(), ((Repeater)plantHolder.getThisPlant()).giftDamage(), zombiePlace, thisMapProperty);
        }

        if (newBullet != null) {
            newBullet.setGridY(plantHolder.getGridY());

            bulletPlace.add(newBullet);
            rootPanel.add(newBullet, JLayeredPane.DRAG_LAYER, -1);

            // ==== Threading Bullet ====
            Thread bulletThread = new Thread(newBullet);
            bulletThread.start();
            bulletThreadList.add(bulletThread);
        }
    }

    // RANDOM PENEMPATAN + JENIS ZOMBIE
    public void randomZombie(MapProperty mapProperty) {
        deleteZombie();
        spawnZombie(mapProperty);
    }
    public void isNowWave(MapProperty mapProperty) {
        if( counter == 112 ){ // PART WAVE
            progressBarColor.setLocation(progressBarColor.getX()-(progressBar.getWidth()-112)/210, progressBarColor.getY());
            progressBarColor.setSize(progressBarColor.getWidth()+(progressBar.getWidth()-112)/210, 20);
            WaveAnimation("partWave");
            for (int j = 0; j < 8; j++) {
                spawnZombie(mapProperty);
            }
        }else if( counter == 225 ){ // LAST WAVE
            WaveAnimation("finalWave");
            for (int j = 0; j < 14; j++) {
                spawnZombie(mapProperty);
            }
            isSpawning = false;
        }
    }
    public void spawnZombie(MapProperty mapProperty) {
        int[] x = mapProperty.getCoorY();
        int temp = rand.nextInt(x.length);
        int y = x[temp] - mapProperty.getGridMarginY();
        ZombiePlaceholder newPlace = new ZombiePlaceholder();
        newPlace.setGridY(temp);
        newPlace.setGridX(9);
        int zomb;
        if(map.equals("Day")){
            if(ctrZombie<5)zomb=0;
            else if(ctrZombie<7) zomb = rand.nextInt(2);
            else zomb = rand.nextInt(3);
            newPlace.setThisZombie(mapProperty.getZombieList().get(zomb));
            newPlace.displayZombo(mapProperty);
        }
        else if(map.equals("Night")){
            if(ctrZombie<3)zomb=0;
            else if(ctrZombie<6) zomb = rand.nextInt(3);
            else zomb = rand.nextInt(5);
            newPlace.setThisZombie(mapProperty.getZombieList().get(zomb));
            newPlace.displayZombo(mapProperty);
        }
        else if(map.equals("Pool")){
            if(ctrZombie<3) zomb=0;
            else if(ctrZombie<6) {
                zomb = rand.nextInt(4);
                if(zomb==3){ zomb=5; }
            }else zomb = rand.nextInt(6);

            if(zomb==5){ // JIKA DUCKY MAKA SPAWN DI DAERAH POOL
                int tempY = rand.nextInt(2);
                if(tempY == 0 ){ y = x[2] - mapProperty.getGridMarginY(); }
                else{ y = x[3] - mapProperty.getGridMarginY(); }
                // ==== Pasang posisi zombie dalam grid ====
                newPlace.setGridY(tempY+2);
            }else{ // JIKA BUKA MAKA SPAWN DI DAERAH TANAH
                int tempY = rand.nextInt(4);
                if(tempY < 2 ){
                    // ==== Pasang posisi zombie grid ====
                    newPlace.setGridY(tempY);
                    y = x[tempY] - mapProperty.getGridMarginY();
                }
                else{
                    // ==== Pasang posisi zombie grid ====
                    newPlace.setGridY(tempY+2);
                    y = x[tempY+2] - mapProperty.getGridMarginY();
                }
            }
            newPlace.setThisZombie(mapProperty.getZombieList().get(zomb));
            newPlace.displayZombo(mapProperty);
        }

        newPlace.setLocation(1000,y+15);

//        System.out.println("Zombie spawned at row: " + newPlace.getGridY());
//        System.out.print("ZOMBIE-Y : "+y+" "+newPlace.getThisZombie().getClass().getSimpleName()+"\n");

        zombiePlace.add(newPlace);
        zombiePlace.get(zombiePlace.size()-1).displayZombo(mapProperty);
        threadSList.add(new Thread(newPlace));
        threadSList.get(threadSList.size()-1).start();
        ctrZombie++;
        rootPanel.add(newPlace,newPlace.getGridY()*2+2,2); // Masukan zombie ke dalam Frame
        rootPanel.add(newPlace.getProtection(), newPlace.getGridY()*2+3, 1); // Masukan proteksi zombie ke dalam Frame
        rootPanel.repaint();
        rootPanel.revalidate();
    }
    public void zombieCek(MapProperty mapProperty) {
        int idx=0;
        while(idx<zombiePlace.size() && !isLose){
            // ==== Kalau Zombie melewati batas taman, Game over =====
            if ( zombiePlace.get(idx).getX() < 125 ) {
                isLose = true;
                zombiePlace.get(idx).setEat(true,null);
                rootPanel.invalidate();
                rootPanel.repaint();

                // ==== DISPLAY GAME OVER PANEL ====
                quitButtonMouseClicked(-1);
            }
            else {
                // Menentukan posisi zombie dalam grid
                boolean isInGrid = false;
                for (int i = 0; i < mapProperty.getCoorX().length; i++) {
                    // ==== Menentukan posisi zombie dalam Grid ====
                    if (i < mapProperty.getCoorX().length-1) {
                        if (zombiePlace.get(idx).getX() + zombiePlace.get(idx).getWidth()/2 >= mapProperty.getCoorX()[i] &&
                            zombiePlace.get(idx).getX() + zombiePlace.get(idx).getWidth()/2 < mapProperty.getCoorX()[i+1]) {
                            zombiePlace.get(idx).setGridX(i);
                            isInGrid = true;
                        }
                    }
                    else {
                        if (zombiePlace.get(idx).getX() + zombiePlace.get(idx).getWidth()/2 >= mapProperty.getCoorX()[i] &&
                            zombiePlace.get(idx).getX() + zombiePlace.get(idx).getWidth()/2 < mapProperty.getCoorX()[i] + mapProperty.getGridSize()) {
                            zombiePlace.get(idx).setGridX(i);
                            isInGrid = true;
                        }
                    }
                }
                // Cek apakah diluar grid
                if (!isInGrid) {
                    if (zombiePlace.get(idx).getX() < mapProperty.getCoorX()[0]) zombiePlace.get(idx).setGridX(-1);
                    else if (zombiePlace.get(idx).getX() > mapProperty.getCoorX()[mapProperty.getCoorX().length-1]) zombiePlace.get(idx).setGridX(mapProperty.getCoorX().length);
                }

                // Mengecek apakah ada tanaman yang bisa dimakan
                for (PlantPlaceholder plantPlaceholder : plantGrid) {
                    int min = plantPlaceholder.getX() - mapProperty.getGridSize()/2;
                    int max = plantPlaceholder.getX() + mapProperty.getGridSize()/2;
                    if (zombiePlace.get(idx).getX() > min && zombiePlace.get(idx).getX() < max && plantPlaceholder.getGridY() == zombiePlace.get(idx).getGridY()) {
                        if(plantPlaceholder.isTherePlant() || plantPlaceholder.isThereLilypad()) {
                            if (!zombiePlace.get(idx).isEat()) {
                                zombiePlace.get(idx).setEat(true, plantPlaceholder);
                            }
                            else if(plantPlaceholder.getHp()<=0){
                                zombiePlace.get(idx).setEat(false, null);
                            }
                        }
                    }
                }
                idx++;
            }
        }
    }

    // ====== Delete Zombie =====
    private void deleteZombie() {
        // Kalau ada zombie yang sudah mati, maka delete dari GUI
        int zCtr = 0;
        while(zCtr < zombiePlace.size()) {
            if (!zombiePlace.get(zCtr).isRunThread()) {
                rootPanel.remove(zombiePlace.get(zCtr).getProtection());
                rootPanel.remove(zombiePlace.get(zCtr));
                rootPanel.repaint();
                zombiePlace.remove(zCtr);
                zCtr--;
            }
            zCtr++;
        }
    }

    // EXIT PANEL BUTTONS
    private void menuButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        plantsPanel.setVisible(false);
        exitPanel.setVisible(true);
        exitPanel.setEnabled(true);
    }
    private void quitButtonMouseClicked(int tipe) {
        clip.stop();
        RSPtimer.stop();
        sunTimer.stop();
        ActionListener stopThreads = e1 -> {
//            forceClose();
            if(timerGlobal!=null) timerGlobal.stop();
            System.out.println("\u001b[31mClosing!\u001b[0m");
            for (ZombiePlaceholder zombiePlaceholder : zombiePlace) {
                if(zombiePlaceholder.isRunThread())
                    zombiePlaceholder.setRunThread(false);
            }
            for (PlantPlaceholder plantPlaceHolder : plantGrid) {
                if(plantPlaceHolder.isRunThread()){
                    plantPlaceHolder.setRunThread(false);
                }
            }

            // ==== Memberhentikan Thread Bullets ====
            for (Bullet bullet : bulletPlace) {
                if(bullet.isRunThread()){
                    bullet.setRunThread(false);
                }
            }
        };
        Timer timer = new Timer(5, stopThreads);
        timer.setRepeats(false);
        timer.setInitialDelay(10);
        timer.start();
        if( tipe == 0 ){ backToMenu(); }
        else if( tipe == 1 ){ restart(); }
        else if( tipe == -1 ) {
            menuButton.setVisible(false);
            plantsPanel.setVisible(false);
            if(mode.equals("Adventure")){ gameOverPanel.setVisible(true); progressBar.setVisible(false); }
            else{
                gameOverPanel.setVisible(true);
                timeHolder.setText("You Survived "+minute+" minute and "+second+" second!");
                timeHolder.setForeground(new Color(255,200,0));
                timeHolder.setFont(new Font("Hobo Std", Font.PLAIN, 20));
                timeHolder.setBounds(new Rectangle(new Point(330, 550),timeHolder.getPreferredSize()));
                timeHolder.setIcon(null);
                timeHolder.remove(minuteValue);
                timeHolder.remove(secondValue);
                gameOverPanel.add(timeHolder);
            }
            onCLickSound("src/com/company/SoundEffect/GameOver.wav");
        }
    }
    private void quitButtonMouseEntered(MouseEvent e) { quitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelExitButtonHover.png"))); }
    private void quitButtonMouseExited(MouseEvent e) { quitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelExitButton.png"))); }
    private void cancelButtonMouseClicked(MouseEvent e) { onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");exitPanel.setVisible(false);plantsPanel.setVisible(true); }
    private void cancelButtonMouseEntered(MouseEvent e) { cancelButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelCancelButtonHover.png"))); }
    private void cancelButtonMouseExited(MouseEvent e) { cancelButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelCancelButton.png"))); }

    // ======== SUN DROP GENERATOR & SUN ON CLICK ========
    public void sunGenrateInterval(){ // INTERVAL UNTUK T
        sunTimer = new Timer(13000, new ActionListener() { // SETIAP 12 DETIK AKAN MEMANGGIL SUN GENERATOR
            @Override
            public void actionPerformed(ActionEvent e) {
                sunRandomGenerator();
            }
        });
        sunTimer.start();
    }
    public void sunRandomGenerator(){
        // AKAN DIPANGGIL SETIAP BERAPA DETIK
        //System.out.println("o> MATAHARI JOS!");
        Random rand = new Random();
        int x = rand .nextInt(400)+300;
        Production newSun = new Production(x,0,25,0);
        newSun.setLocation(x,0);
        newSun.displaySun();

        sunDrop.add(newSun);
        sunThreadList.add(new Thread(newSun));
        sunThreadList.get(sunThreadList.size()-1).start();
        newSun.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newSun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(!sunDrop.isEmpty()){
                    onCLickSound("src/com/company/SoundEffect/sunCollected.wav");
                    JLabel temp = (JLabel) e.getSource();
                    int xPos=temp.getX(),yPos=temp.getY(),idxSun;
                    //System.out.print("CLICKED SUN: " +xPos + " - " + yPos + " | ");
                    idxSun = searchSunIdx(xPos,yPos);
                    if(idxSun!=-1){
                        sunDrop.get(idxSun).setIcon(null);
                        totalSun+=25;
                        SunCtr.setText(totalSun+"");
                        //System.out.println("IDXSUN: " + idxSun);
                        rootPanel.remove(sunDrop.get(idxSun));
                        sunDrop.remove(idxSun);
                        rootPanel.revalidate();
                        rootPanel.repaint();
                    }
                }
            }
        });
        rootPanel.add(newSun,JLayeredPane.DRAG_LAYER,0);
        rootPanel.revalidate();
        rootPanel.repaint();
    }
    public void isSunAlive(){ // SUN LIFE SPAN
        int idx=0;
        while(idx<sunDrop.size()){
            if (!sunDrop.get(idx).isSunAlive()) {
                rootPanel.remove(sunDrop.get(idx));
                sunDrop.remove(idx);
                rootPanel.revalidate();
                rootPanel.repaint();
            }
            else idx++;
        }
    }
    public int searchSunIdx(int xPos,int yPos){ // UNTUK MENCARI IDX SUN MANA YANG DI KLIK
        for (int i = 0; i < sunDrop.size(); i++) {
            if( xPos == sunDrop.get(i).getX() && yPos == sunDrop.get(i).getY() ){
                //System.out.print(sunDrop.get(i).getX()+"/"+sunDrop.get(i).getY() + ", ");
                return i;
            }
        }
        return -1;
    }

    // ====== SUNFLOWER ======
    public void sunflower(PlantPlaceholder plantPlace){
        Thread productingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.interrupted() && plantPlace.isRunThread() && plantPlace.isTherePlant()){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                    int temp = plantPlace.getCounter()+1;
                    plantPlace.setCounter(temp);
                    if(plantPlace.getCounter()==12){
                        plantPlace.setCounter(0);

                        //System.out.println("o> SUNFLOWER JOS!");
                        int x = plantPlace.getX(), y = plantPlace.getY();
                        Production newSun = new Production(x+15,y,25,1);
                        newSun.setLocation(plantPlace.getX()+15,plantPlace.getY());
                        newSun.displaySun();

                        sunDrop.add(newSun);
                        sunThreadList.add(new Thread(newSun));
                        sunThreadList.get(sunThreadList.size()-1).start();
                        newSun.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        newSun.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e){
                                if(!sunDrop.isEmpty()){
                                    JLabel temp = (JLabel) e.getSource();
                                    int xPos=temp.getX(),yPos=temp.getY(),idxSun;
//                                    System.out.print("CLIKCED SUN: " +xPos + " - " + yPos + " | ");
                                    idxSun = searchSunIdx(xPos,yPos);
                                    if(idxSun!=-1){
                                        onCLickSound("src/com/company/SoundEffect/sunCollected.wav");
                                        sunDrop.get(idxSun).setIcon(null);
                                        totalSun+=25;
                                        SunCtr.setText(totalSun+"");
//                                        System.out.println("IDXSUN: " + idxSun);
                                        rootPanel.remove(sunDrop.get(idxSun));
                                        sunDrop.remove(idxSun);
                                        rootPanel.revalidate();
                                        rootPanel.repaint();
                                    }
                                }
                            }
                        });
                        rootPanel.add(newSun,JLayeredPane.DRAG_LAYER,0);
                        rootPanel.revalidate();
                        rootPanel.repaint();
                    }
                }
            }
        });
        sunProductingThread.add(productingThread);
        sunProductingThread.get(sunProductingThread.size()-1).start();
    }

    // ====== Cherry Bomb =======
    private void cherryBomb(PlantPlaceholder plantPlace) {
        Thread cherryExplode = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Explosion thread started!");
                Timer explosion = new Timer(0, e -> {
                    if (plantPlace.isTherePlant()) {
                        if (plantPlace.getThisPlant() instanceof Cherrybomb) {
                            System.out.println("Cherry bomb detected!");
                            // Cek setiap zombie apakah ada yang didekatnya cherry bomb
                            int zCtr = 0;
                            while (zCtr < zombiePlace.size()) {
                                // Kalau ada yang didekatnya, maka damage zombie itu
                                if (zombiePlace.get(zCtr).getGridY() >= plantPlace.getGridY()-1 && zombiePlace.get(zCtr).getGridY() <= plantPlace.getGridY()+1 &&
                                    zombiePlace.get(zCtr).getGridX() >= plantPlace.getGridX()-1 && zombiePlace.get(zCtr).getGridX() <= plantPlace.getGridX()+1) {

                                    zombiePlace.get(zCtr).mutateHp(-(((Cherrybomb) plantPlace.getThisPlant()).giftDamage()));
                                    System.out.println("Zombie "+ zCtr +" damaged!");

                                    if (zombiePlace.get(zCtr).getHp() <= 0) {
                                        zombiePlace.get(zCtr).setRunThread(false);
                                        zombiePlace.get(zCtr).setVisible(false);
                                        zombiePlace.get(zCtr).setEnabled(false);
                                    }
                                }
                                zCtr++;
                            }
                        }
                    }
                });
                explosion.setRepeats(false);
                explosion.setInitialDelay(1000);
                explosion.start();

                // Tunggu Timer selesai eksekusi
                while (explosion.isRunning());
                onCLickSound("src/com/company/SoundEffect/cherryBomb.wav");

                // Setelah meledak, biarkan animasi tetap berjalan lalu hapus tanaman
                if (!explosion.isRunning()) {
                    explosion = new Timer(4, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (plantPlace.isTherePlant()) {
                                if (plantPlace.getThisPlant() instanceof Cherrybomb) {
                                    plantPlace.removePlant();
//                                    System.out.println("Cherry Bomb removed!");
                                }
                            }
                        }
                    });
                    explosion.setInitialDelay(500);
                    explosion.setRepeats(false);
                    explosion.start();
                }
            }
        });
        cherryExplode.start();
    }

    // ====== CHOMPER ======
    public void chomper(PlantPlaceholder plantGrid){
        Thread chomperThread = new Thread(() -> {
            while(!Thread.interrupted() && plantGrid.isRunThread()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                // Bila Chomper tidak sedang makan.
                if (!plantGrid.isEating()) {
                    boolean isFound = false;
                    int tempSizeZombie = zombiePlace.size();
                    int ctrIDX = 0;
                    while(ctrIDX < zombiePlace.size() && !isFound && tempSizeZombie==zombiePlace.size()) {
                        // Apakah Zombie satu lajur dan didepan Chomper?
                        if (zombiePlace.get(ctrIDX).getGridY() == plantGrid.getGridY() && zombiePlace.get(ctrIDX).getGridX() >= plantGrid.getGridX() &&
                                zombiePlace.get(ctrIDX).getGridX() <= plantGrid.getGridX() + 1) {
                            isFound = true;
                        } else ctrIDX++;
                    }
                    if(isFound){
                        chomperEat(zombiePlace.get(ctrIDX),plantGrid);
                    }
                }
                // Bila Chomper sedang makan
                else if (plantGrid.isEating()) {
                    int temp = plantGrid.getCounter() + 1;
                    plantGrid.setCounter(temp);

                    if (plantGrid.getCounter() >= 30) {
                        plantGrid.setCounter(0);
                        plantGrid.setEating(false);
                        plantGrid.displayPlant(thisMapProperty);
                    }
                }
            }
        });
        threadSList.add(chomperThread);
        threadSList.get(threadSList.size()-1).start();
    }

    public void chomperEat(ZombiePlaceholder zombieJos, PlantPlaceholder plantGrid){
        // Tampilkan Chomper menyerang
        plantGrid.displayAtt(thisMapProperty);
        // Delay
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) { }
        zombieJos.setRunThread(false);
        rootPanel.remove(zombieJos.getProtection());
        rootPanel.remove(zombieJos);
        zombiePlace.remove(zombieJos);
        plantGrid.setEating(true);
        plantGrid.displayEat(thisMapProperty);
        rootPanel.repaint();
    }

    // ==== CHEAT RELATED ====
    public void backToMenu(){
        forceClose();
        Main.startMain();
    }
    public void restart(){
        GameMap GM = new GameMap(map,mode);
        GM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }
    public void killAllZombie(){ // BUNUH SEMUA ZOMBIE YANG ADA DI ZOMBIEPLACE
        if(zombiePlace.size() > 0){
            for (ZombiePlaceholder zombiePlaceholder : zombiePlace) {
                if(zombiePlaceholder.isRunThread())
                    zombiePlaceholder.setRunThread(false);
            }
            if(zombiePlace.size() < 3) { onCLickSound("src/com/company/SoundEffect/slainEnemy.wav"); }
            else if(zombiePlace.size() < 6) { onCLickSound("src/com/company/SoundEffect/killingSpree.wav"); }
            else { onCLickSound("src/com/company/SoundEffect/savage.wav"); }
        }else { onCLickSound("src/com/company/SoundEffect/buttonCancel.wav"); }
    }
    public void removeAllPlant(){
        for (PlantPlaceholder plantPlaceHolder : plantGrid) {
            plantPlaceHolder.removePlant();
        }
    }

    // INISIALISASI KOMPONEN MAP GUI
    private void initComponent(String map, MapProperty mapProperty) {

        //======= PlantCard ========
        Sunflower = new JLabel();
        Peashooter = new JLabel();
        Cherrybomb = new JLabel();
        Wallnut = new JLabel();
        Sunshroom = new JLabel();
        Reapeter = new JLabel();
        Chomper = new JLabel();
        Lilypad = new JLabel();
        SunHolder = new JLabel();
        Shovel = new JLabel();

        // GAME CHEAT
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) { // KEYBOARD LISTENER
                super.keyPressed(e);
                if( e.getKeyChar() == 'r' ){ quitButtonMouseClicked(1);} // RESTART CURRENT LEVEL
                else if( e.getKeyChar() == 'c' ){ totalSun+=5000;SunCtr.setText(totalSun+"");} // ADD 5000 SUN
                else if( e.getKeyChar() == 'v' ){ removeAllPlant(); } // REMOVE SEMUA PLANT
                else if( e.getKeyChar() == 'b' ){ killAllZombie(); deleteZombie(); } // KILL SEMUA ZOMBIE
                else if( e.getKeyChar() == 'n' ){ if( counter < 112 ) counter = 112; else if( counter < 225 ) counter = 225;  } // JUMP TO NEXT WAVE
                else if( e.getKeyChar() == 'm' ){ if( counter < 225 ) counter = 225; } // JUMP TO FINAL WAVE

                rootPanel.revalidate();
                rootPanel.repaint();
            }
        });

        //======= Root Panel ========
        rootPanel = new JLayeredPane();
        rootPanel.setSize(mapProperty.getMapWidth(), mapProperty.getMapHeight());
        rootPanel.setLayout(null);

        // ====== READY SET PLANT =========
        RSP = new JLabel();
        RSP.setIcon(null);
        RSP.setBounds(255,180,500,200);
        add(RSP);

        //======== EXIT PANEL ========
        menuButton = new JLabel();
        exitPanel = new JPanel();
        quitButton = new JLabel();
        cancelButton = new JLabel();
        exitMenu = new JLabel();

        menuButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/menuButton.png")));
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuButtonMouseClicked(e);
            }
        });
        mapBackground.add(menuButton);
        menuButton.setBounds(new Rectangle(new Point(800, 0), menuButton.getPreferredSize()));
        menuButton.setVisible(false);
        {
            exitPanel.setBackground(new Color(60, 63, 65, 123));
            exitPanel.setOpaque(true);
            exitPanel.setVisible(false);
            exitPanel.setEnabled(false);
            exitPanel.setLayout(null);

            //---- quitButton ----
            quitButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanelExitButton.png")));
            quitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            quitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    quitButtonMouseClicked(0);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    quitButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    quitButtonMouseExited(e);
                }
            });
            exitPanel.add(quitButton);
            quitButton.setBounds(new Rectangle(new Point(335, 380), quitButton.getPreferredSize()));

            //---- cancelButton ----
            cancelButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanelCancelButton.png")));
            cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cancelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cancelButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    cancelButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    cancelButtonMouseExited(e);
                }
            });
            exitPanel.add(cancelButton);
            cancelButton.setBounds(new Rectangle(new Point(510, 380), cancelButton.getPreferredSize()));

            //---- exitMenu ----
            exitMenu.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanel.png")));
            exitPanel.add(exitMenu);
            exitMenu.setBounds(new Rectangle(new Point(300, 155), exitMenu.getPreferredSize()));
        }
        add(exitPanel);
        exitPanel.setBounds(0, 0, 1400, 600);

        //======== GAME OVER PANEL ========
        gameOverPanel = new JPanel();
        theZombieAteYourBrain = new JLabel();
        restartButton = new JLabel();
        backToMainMenuButton = new JLabel();
        {
            gameOverPanel.setBackground(new Color(60, 63, 65, 123));
            gameOverPanel.setOpaque(true);
            gameOverPanel.setVisible(false);
            gameOverPanel.setLayout(null);

            //---- backToMainMenuButton ----
            backToMainMenuButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanelExitButton.png")));
            backToMainMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backToMainMenuButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { backToMenu(); }
            });
            gameOverPanel.add(backToMainMenuButton);
            backToMainMenuButton.setBounds(new Rectangle(new Point(335, 500), quitButton.getPreferredSize()));

            //---- restartButton ----
            restartButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/restart.png")));
            restartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            restartButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { restart(); }
            });
            gameOverPanel.add(restartButton);
            restartButton.setBounds(new Rectangle(new Point(510, 500), cancelButton.getPreferredSize()));

            //---- theZombieAteYourBrainLabel ----
            theZombieAteYourBrain.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/animation/Lose.png")));
            gameOverPanel.add(theZombieAteYourBrain);
            theZombieAteYourBrain.setBounds(new Rectangle(new Point(240, 30), theZombieAteYourBrain.getPreferredSize()));
        }
        add(gameOverPanel);
        gameOverPanel.setBounds(0, 0, 1400, 600);

        System.out.println(mode);
        if(mode.equals("Adventure")){
            //======= PROGRESS BAR ========
            progressBar = new JLabel();
            progressBar.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/progressBar.png")));
            progressBar.setBounds(new Rectangle(new Point(700, 550), progressBar.getPreferredSize()));
            progressBar.setVisible(false);
            rootPanel.add(progressBar,JLayeredPane.POPUP_LAYER, 0);

            // ==== Progress Bar Color ====
            progressBarColor = new JLabel();
            progressBarColor.setBackground(new Color(255,0 , 0));
            progressBarColor.setOpaque(true);
            progressBarColor.setBounds(progressBar.getX()+240, progressBar.getY()+15, 0, 20);
            progressBar.setVisible(false);
            rootPanel.add(progressBarColor,JLayeredPane.POPUP_LAYER, 1);
        } else {
            //======= TIME HOLDER =======
            timeHolder = new JLabel();
            minuteValue = new JLabel();
            secondValue = new JLabel();
            minuteValue.setText(minute+" : ");
            secondValue.setText(second+"");
            minuteValue.setFont(new Font("Hobo Std", Font.PLAIN, 20));
            secondValue.setFont(new Font("Hobo Std", Font.PLAIN, 20));
            minuteValue.setBounds(55, 5,30, 30);
            secondValue.setBounds(85, 5,30, 30);
            timeHolder.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/timeHolder.png")));
            timeHolder.setBounds(new Rectangle(new Point(800, 550),timeHolder.getPreferredSize()));
            timeHolder.add(secondValue);
            timeHolder.add(minuteValue);
            rootPanel.add(timeHolder,JLayeredPane.POPUP_LAYER);
            timeHolder.setVisible(false);
        }

        //======= Background ========
        ImageIcon bgIcon = new ImageIcon(mapProperty.getMapBackground());
        bgIcon = new ImageScaler().getScaledIcon(bgIcon, mapProperty.getMapWidth(), mapProperty.getMapHeight());
        mapBackground.setIcon(bgIcon);
        mapBackground.setBounds(mapProperty.getMapOffsetX(), mapProperty.getMapOffsetY(), mapProperty.getMapWidth(), mapProperty.getMapHeight());
        mapBackground.setOpaque(false);
        rootPanel.add(mapBackground,0);

        //======= Plant Buttons ========
        /**
         * Block statement ini ditujukan untuk membuat semua JLabel yang akan digunakan untuk menjadi sistem
         * grid tanaman yang dapat ditanam dengan menekan labelnya.
         */
        int ctr = 1;
        for (int i = 0; i < mapProperty.getMapRow(); i++) {
            for (int j = 0; j < 9; j++) {
                PlantPlaceholder newLabel = new PlantPlaceholder(mapProperty);
                newLabel.setBounds(mapProperty.getCoorX()[j], mapProperty.getCoorY()[i], mapProperty.getGridSize(), mapProperty.getGridSize());

                //Unique modifer untuk Map Pool
                if (mapProperty.isHasPool() && (i == 2 || i == 3)) {
                    newLabel.setWater(true);
                }

                newLabel.setOpaque(false);
                newLabel.setName("plantButton"+ctr);
                ctr++;

                // ==== Label Position in grid ====
                newLabel.setGridX(j);
                newLabel.setGridY(i);

                plantGrid.add(newLabel);
                rootPanel.add(newLabel.getLilypadPlaceholder(), i+1, -1);        //Buat menampilkan lilypad Pool
                rootPanel.add(newLabel,i+1, 0);
            }
        }

        //======= Plants Panel =========
        {
            plantsPanel = new JPanel();
            plantsPanel.setSize(100, mapProperty.getMapHeight());
            plantsPanel.setBackground(new Color(0, 0, 0,0));
            plantsPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
                    javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax
                    . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
                    .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
                    . Color. red) ,plantsPanel. getBorder( )) ); plantsPanel. addPropertyChangeListener (new java. beans.
                PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .
                equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            plantsPanel.setLayout(null);

            //---- SunHolder ----
            SunCtr = new JLabel();
            SunCtr.setText(totalSun+"");
            SunHolder.add(SunCtr);
            SunCtr.setFont(new Font("Hobo Std", Font.PLAIN, 20));
            SunCtr.setBounds(46,10,60,20);
            SunHolder.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/sunHolder.jpg")));
            plantsPanel.add(SunHolder);
            SunHolder.setBounds(new Rectangle(new Point(0, 10), SunHolder.getPreferredSize()));

            //---- Shovel ----
            Shovel.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/Shovel.jpg")));
            Shovel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Shovel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    CardMouseReleased(e,0, mapProperty);
                }
            });
            Shovel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
                CardMouseDragged(e,0);
            }
        });
            plantsPanel.add(Shovel);
            Shovel.setBounds(new Rectangle(new Point(0, 60), Shovel.getPreferredSize()));

            //---- Sunflower ----
            Sunflower.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/SFcard.jpg")));
            Sunflower.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Sunflower.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    CardMouseReleased(e,1, mapProperty);
                }
            });
            Sunflower.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e){
                    super.mouseDragged(e);
                    CardMouseDragged(e,1);
                }
            });
            plantsPanel.add(Sunflower);
            Sunflower.setBounds(new Rectangle(new Point(0, 110), Sunflower.getPreferredSize()));

            //---- Peashooter ----
            Peashooter.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/PScard.jpg")));
            Peashooter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Peashooter.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    CardMouseReleased(e,2, mapProperty);
                }
            });
            Peashooter.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e){
                    super.mouseDragged(e);
                    CardMouseDragged(e,2);
                }
            });
            plantsPanel.add(Peashooter);
            Peashooter.setBounds(new Rectangle(new Point(0, 170), Peashooter.getPreferredSize()));

            //---- Cherrybomb ----
            Cherrybomb.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/CBcard.jpg")));
            Cherrybomb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Cherrybomb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    CardMouseReleased(e,3, mapProperty);
                }
            });
            Cherrybomb.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e){
                    CardMouseDragged(e,3);
                }
            });
            plantsPanel.add(Cherrybomb);
            Cherrybomb.setBounds(new Rectangle(new Point(0, 230), Cherrybomb.getPreferredSize()));

            //---- Wallnut ----
            Wallnut.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/WNcard.jpg")));
            Wallnut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Wallnut.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e){
                    CardMouseReleased(e,4, mapProperty);
                }
            });
            Wallnut.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e){
                    CardMouseDragged(e,4);
                }
            });
            plantsPanel.add(Wallnut);
            Wallnut.setBounds(new Rectangle(new Point(0, 290), Wallnut.getPreferredSize()));

            if (map.equals("Night") || map.equals("Pool")) {
                //---- Sunshroom ----
                Sunshroom.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/SScard.jpg")));
                Sunshroom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Sunshroom.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e){
                        CardMouseReleased(e,5, mapProperty);
                    }
                });
                Sunshroom.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e){
                        CardMouseDragged(e,5);
                    }
                });
                plantsPanel.add(Sunshroom);
                Sunshroom.setBounds(new Rectangle(new Point(0, 350), Sunshroom.getPreferredSize()));

                //---- Reapeter ----
                Reapeter.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/RPTcard.jpg")));
                Reapeter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Reapeter.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e){
                        CardMouseReleased(e,6, mapProperty);
                    }
                });
                Reapeter.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e){
                        CardMouseDragged(e,6);
                    }
                });
                plantsPanel.add(Reapeter);
                Reapeter.setBounds(new Rectangle(new Point(0, 410), Reapeter.getPreferredSize()));

                //---- Chomper ----
                Chomper.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/CPcard.jpg")));
                Chomper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Chomper.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e){
                        CardMouseReleased(e,7, mapProperty);
                    }
                });
                Chomper.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e){
                        CardMouseDragged(e,7);
                    }
                });
                plantsPanel.add(Chomper);
                Chomper.setBounds(new Rectangle(new Point(0, 472), Chomper.getPreferredSize()));
            }
            if (map.equals("Pool")) {
                //---- Lilypad ----
                Lilypad.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/PlantCard/LPcard.jpg")));
                Lilypad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Lilypad.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e){
                        CardMouseReleased(e,8, mapProperty);
                    }
                });
                Lilypad.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e){
                        CardMouseDragged(e,8);
                    }
                });
                plantsPanel.add(Lilypad);
                Lilypad.setBounds(new Rectangle(new Point(0, 532), Lilypad.getPreferredSize()));
            }
        }
        rootPanel.add(plantsPanel,JLayeredPane.POPUP_LAYER);
        plantsPanel.setBounds(0, 0, 100, 635);
        plantsPanel.setOpaque(false);

        //======= DRAG DROP CONTAINER ========
        dragContainer = new JLabel();
        dragContainer.setSize(75,75);
        rootPanel.add(dragContainer,JLayeredPane.DRAG_LAYER,0);
    }

    private void forceClose(){
        ActionListener stopThreads = e1 -> {
            if (timerGlobal != null) timerGlobal.stop();
            if (sunTimer != null) sunTimer.stop();
            System.out.println("Closing!");
            // ==== Memberhentikan Thread Zombie ====
            for (ZombiePlaceholder zombiePlaceholder : zombiePlace) {
                if(zombiePlaceholder.isRunThread())
                    zombiePlaceholder.setRunThread(false);
            }

            // ==== Memberhentikan Thread Plants ====
            for (PlantPlaceholder plantPlaceHolder : plantGrid) {
                if(plantPlaceHolder.isRunThread()){
                    plantPlaceHolder.setRunThread(false);
                }
            }

            // ==== Memberhentikan Thread Bullets ====
            for (Bullet bullet : bulletPlace) {
                if(bullet.isRunThread()){
                    bullet.setRunThread(false);
                }
            }
            this.dispose();
        };
        Timer timer = new Timer(5, stopThreads);
        timer.setRepeats(false);
        timer.setInitialDelay(10);
        timer.start();
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) {
        forceClose();
    }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }

}
