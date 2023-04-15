package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        startMain();

        //====== Debugging Purposes =======
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                GameMap mainGame = new GameMap("Pool", "Adventure");
//                mainGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            }
//        });
    }
    public static void startMain(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu MM = new MainMenu();
                MM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
    public static void startGame(String map, String mode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameMap mainGame = new GameMap(map, mode);
                mainGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
}
