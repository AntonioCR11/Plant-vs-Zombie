package com.company;

import com.company.Asset.units.plants.*;
import com.company.Asset.units.zombies.*;

import java.net.URL;
import java.util.ArrayList;

/**
 * Kelas ini digunakan untuk menentukan properti-properti dari map yang akan digunakan.
 */

public class MapProperty {
    // ======= Properti basic Map =======
    private URL mapBackground;
    private int mapWidth;
    private int mapHeight;
    private int mapRow;
    private int mapOffsetX;
    private int mapOffsetY;

    // ======= Unique Map Modifer ========
    private boolean generateSun;
    private boolean hasPool;
    private ArrayList<Zombie> zombieList = new ArrayList();
    private ArrayList<Plant> plantList = new ArrayList();

    // ======= Sistem Grid ========
    private int[] coorX;
    private int[] coorY;
    private int gridMarginX;
    private int gridMarginY;
    private int gridSize;

    public MapProperty(String map,GameMap GM) {
        this.mapBackground = initializeMapBackground(map);
        this.mapWidth = 1400;
        this.mapHeight = 600;
        this.mapOffsetX = -60;
        this.mapOffsetY = 0;
        this.gridMarginX = 265 + mapOffsetX;
        this.gridMarginY = 95 + mapOffsetY;
        this.mapRow = initializeMapRow(map);
        initializeGrid(map);
        this.gridSize = initializeGridSize(map);
        this.generateSun = initializeGenerateSun(map);
        this.hasPool = initializePool(map);

        // ==== Entity Available ====
        initializeZombieList(map);
        initializePlantList(map);
    }

    // Beberapa function dan procedure yang menentukan properti dari map

    private URL initializeMapBackground(String map) {
        URL background = null;

        if (map.equals("Day")) {
            background = getClass().getResource("Asset/Background/Dayfrontyard.jpg");
        }
        else if (map.equals("Night")) {
            background = getClass().getResource("Asset/Background/Nightfrontyard.jpg");
        }
        else if (map.equals("Pool")) {
            background = getClass().getResource("Asset/Background/pool.jpg");
        }
        return background;
    }

    private int initializeMapRow(String map) {
        int rows = 5;
        if (map.equals("Pool")) {
            rows = 6;
        }
        else rows = 5;
        return rows;
    }

    private void initializeGrid(String map) {
        // X Coordinates
        int[] coorX5Row = { gridMarginX-10, gridMarginX+70, gridMarginX+150, gridMarginX+230, gridMarginX+310, gridMarginX+390, gridMarginX+470, gridMarginX+550, gridMarginX+630};
        int[] coorX6Row = { gridMarginX, gridMarginX+80, gridMarginX+165, gridMarginX+245, gridMarginX+320, gridMarginX+405, gridMarginX+480, gridMarginX+565, gridMarginX+645};
        // Y Coordinates
        int[] coorY5Row = { gridMarginY, gridMarginY+95, gridMarginY+190, gridMarginY+295, gridMarginY+385 };
        int[] coorY6Row = { gridMarginY+10, gridMarginY+90, gridMarginY+195, gridMarginY+270, gridMarginY+350, gridMarginY+435};

        // Penghubungan map dengan sistem grid
        if (map.equals("Pool")) {
            coorY = coorY6Row;
            coorX = coorX6Row;
        } else {
            coorX = coorX5Row;
            coorY = coorY5Row;
        }
    }

    private int initializeGridSize(String map) {
        int size = 80;

        //Jika ada kondisi masukan sini

        if (map.equals("Pool")) size = 70;
        return size;
    }

    private boolean initializeGenerateSun(String map) {
        boolean generate = true;

        if (map.equals("Night")) {
            generate = false;
        }

        return generate;
    }

    private boolean initializePool(String map) {
        boolean isPool = false;

        if (map.equals("Pool")) {
            isPool = true;
        }

        return isPool;
    }

    private void initializeZombieList(String map) {
        //=== Default Zombie ===
        //Basic Zombie
        zombieList.add(new BasicZombie(0, 0,"src/com/company/Asset/ZombieGIF/Basic Walking.gif","src/com/company/Asset/ZombieGIF/Zombie-Eat.gif"));
        //Cone Head Zombie
        zombieList.add(new ConeheadZombie(0, 0,"src/com/company/Asset/ZombieGIF/Basic Walking.gif","src/com/company/Asset/ZombieGIF/Zombie-Eat.gif"));
        //Bucket Zombie
        zombieList.add(new BucketZombie(0,0,"src/com/company/Asset/ZombieGIF/Basic Walking.gif","src/com/company/Asset/ZombieGIF/Zombie-Eat.gif"));
        //=== Added Zombie ===
        if (map.equals("Night") || map.equals("Pool")) {
            //Newspaper Zombie
            zombieList.add(new NewspaperZombie(0, 0,"src/com/company/Asset/ZombieGIF/newspaper-finis.gif","src/com/company/Asset/ZombieGIF/newsEating.gif","src/com/company/Asset/ZombieGIF/newsEating.gif"));
            //Football Zombie
            zombieList.add(new FootballZombie(0, 0,"src/com/company/Asset/ZombieGIF/football.gif","src/com/company/Asset/ZombieGIF/FootballEating.gif"));
        }
        if (map.equals("Pool")) {
            //Ducky Zombie
            zombieList.add(new DuckyZombie(0, 0,"src/com/company/Asset/ZombieGIF/DuckyTube.gif","src/com/company/Asset/ZombieGIF/DuckyByur.gif","src/com/company/Asset/ZombieGIF/DuckyEat.gif"));
        }
    }

    private void initializePlantList(String map) {
        //=== Default Plants ===
        // ==== Sunflower ====
        plantList.add(new Sunflower(0, 0, "src/com/company/Asset/animation/plants/Sunflower/Sunflower.gif"));

        // ==== Peashooter ====
        plantList.add(new Peashooter(0, 0, "src/com/company/Asset/animation/plants/Peashooter/Peashooter.gif"));

        // ==== Cherry Bomb ====
        plantList.add(new Cherrybomb(0, 0, "src/com/company/Asset/animation/plants/CherryBomb/CherryBomb.gif"));

        // ==== Wallnut ====
        plantList.add(new Wallnutt(0, 0, "src/com/company/Asset/animation/plants/wallnut/Wallnut-finis.gif"));

        //=== Added Zombie ===
        if (map.equals("Night") || map.equals("Pool")) {
            // ==== Sunshroom ====
            plantList.add(new Sunshroom(0, 0, "src/com/company/Asset/animation/plants/Sunshroom/SunShroom.gif"));

            // ==== Repeater ====
            plantList.add(new Repeater(0, 0, "src/com/company/Asset/animation/plants/Repeater/Repeater.gif"));

            // ==== Chomper ====
            plantList.add(new Chomper(0, 0, "src/com/company/Asset/animation/plants/Chomper/Basic/Chomper.gif"));
        }
        if (map.equals("Pool")) {
            plantList.add(new Lilypad(0, 0, "src/com/company/Asset/animation/plants/Lilypad/Lilypad.png"));
        }
    }

    //Accessor dan Mutator
    public URL getMapBackground() {
        return mapBackground;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapRow() {
        return mapRow;
    }

    public int getMapOffsetX() {
        return mapOffsetX;
    }

    public int getMapOffsetY() {
        return mapOffsetY;
    }

    public int getGridMarginX() {
        return gridMarginX;
    }

    public int getGridMarginY() {
        return gridMarginY;
    }

    public int[] getCoorX() {
        return coorX;
    }

    public int[] getCoorY() {
        return coorY;
    }

    public int getGridSize() {
        return gridSize;
    }

    public boolean isGenerateSun() {
        return generateSun;
    }

    public boolean isHasPool() {
        return hasPool;
    }

    public ArrayList<Zombie> getZombieList() {
        return zombieList;
    }
    public ArrayList<Plant> getPlantList() {
        return plantList;
    }
}
