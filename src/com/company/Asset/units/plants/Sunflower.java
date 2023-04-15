package com.company.Asset.units.plants;

import com.company.GameMap;

import java.io.File;
import java.util.ArrayList;

public class Sunflower extends Plant implements Generator{
    ArrayList<Production> generate = new ArrayList<>();
    private int counter;
    private GameMap thisGameMap;
    private boolean producting = true;

    public Sunflower(int x, int y) {
        super("Sun Flower", 40, 4, x, y, 80, 80);
        price = 50;
    }

    public Sunflower(int x, int y, String gambarGif) {
        super("Sun Flower", 60, 4, x, y, 80, 80, gambarGif);
        price = 50;
    }

    public Sunflower(int x, int y, ArrayList<File> gambarFrame) {
        super("Sun Flower", 40, 4, x, y, 80, 80, gambarFrame);
        price = 50;
    }

    @Override
    public int getGenerate(int x,int y){
        int index=-1,temp=0;
        for (int i = 0; i < generate.size(); i++) {
            if(generate.get(i).getY() == y && generate.get(i).getX() == x){ index = i; break; }
        }
        if(index!=-1){
            temp = generate.get(index).getValue();
            generate.remove(index);
        }

        return temp;
    }

    @Override
    public void generate(int time) {
        for (Production production : generate) {
            int temp = production.getTimer() + 1;
            production.setTimer(temp);
        }
        if(time%cooldown==0){
            generate.add(new Production(x,y,50,1));
        }
        else{
            int index=0;
            while(index<generate.size()){
                if(generate.get(index).getTimer()==8)generate.remove(index);
                else index++;
            }
        }
    }

//    @Override
//    public void run() {
//        while(!Thread.interrupted() && producting){
//            try{
//                Thread.sleep(1000);
//            }catch (InterruptedException e){}
//            counter++;
//            System.out.println("->"+counter);
//            if(this.counter == 12){
//                counter = 0;
//                sunflower();
//            }
//        }
//    }
//
//    public void sunflower(){
//        System.out.println("SUNFLOWER JOS!");
//        Production newSun = new Production(x+15,y,25);
//        newSun.setLocation(x+15,y);
//        newSun.displaySun();
//        thisGameMap.getSunDrop().add(newSun);
//        newSun.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        newSun.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e){
//                if(!thisGameMap.getSunDrop().isEmpty()){
//                    JLabel temp = (JLabel) e.getSource();
//                    int xPos=temp.getX(),yPos=temp.getY(),idxSun;
//                    System.out.println("KETIK : " +xPos + " - " + yPos);
//                    idxSun = thisGameMap.searchSunIdx(xPos,yPos);
//                    if(idxSun!=-1){
//                        thisGameMap.onCLickSound("src/com/company/SoundEffect/sunCollected.wav");
//                        thisGameMap.getSunDrop().get(idxSun).setIcon(null);
//                        thisGameMap.setTotalSun(thisGameMap.getTotalSun()+25);
//                        thisGameMap.getSunCtr().setText(thisGameMap.getTotalSun()+"");
//                        System.out.println("IDXSUN : " + idxSun);
//                        thisGameMap.getRootPanel().remove(thisGameMap.getSunDrop().get(idxSun));
//                        thisGameMap.getSunDrop().remove(idxSun);
//                        thisGameMap.getRootPanel().revalidate();
//                        thisGameMap.getRootPanel().repaint();
//                    }
//                }
//            }
//        });
//        thisGameMap.getRootPanel().add(newSun,JLayeredPane.MODAL_LAYER);
//        thisGameMap.getRootPanel().revalidate();
//        thisGameMap.getRootPanel().repaint();
//    }
//
//    public boolean isProducting() {
//        return producting;
//    }
//
//    public void setProducting(boolean producting) {
//        this.producting = producting;
//    }
}
