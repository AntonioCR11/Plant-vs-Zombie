package com.company.Asset.units.plants;

import java.io.File;
import java.util.ArrayList;

public class Sunshroom extends Plant implements Generator{
    ArrayList<Production> generate;
    public Sunshroom(int x, int y) {
        super("SunShroom", 40, 4, x, y, 80, 80);
        price = 25;
        generate = new ArrayList<>();
    }
    public Sunshroom(int x, int y, String gambarGif) {
        super("SunShroom", 40, 4, x, y, 80, 80, gambarGif);
        price = 25;
        generate = new ArrayList<>();
    }
    public Sunshroom(int x, int y, ArrayList<File> gambarFrame) {
        super("SunShroom", 40, 4, x, y, 80, 80, gambarFrame);
        price = 25;
        generate = new ArrayList<>();
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
            generate.add(new Production(x,y,25,1));
        }
        else{
            int index=0;
            while(index<generate.size()){
                if(generate.get(index).getTimer()==8)generate.remove(index);
                else index++;
            }
        }
    }
}
