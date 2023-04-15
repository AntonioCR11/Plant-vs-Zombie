package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MainMenu extends JFrame {
    // ==== NON-GUI PROPERTIES ====
    private String gameMode;

    // ==== GUI PROPERTIES ====
    private JPanel mainMenu;
    private JPanel subMenu;
    private JPanel exitPanel;
    private JLabel quitButton;
    private JLabel cancelButton;
    private JLabel exitMenu;
    private JPanel HelpPanel;
    private JLabel helpBackButton;
    private JLabel HelpBG;
    private JPanel AlmanacPanel;
    private JLabel EngineerPhoto;
    private JLabel almanackBackButton;
    private JPanel Bonus;
    private JLabel cilukBA;
    private JPanel changeNamePanel;
    private JTextField inputName;
    private JLabel okButton;
    private JLabel changeNameMenu;
    private JLabel Name;
    private JLabel exitButton;
    private JLabel bonusButton;
    private JLabel HelpButton;
    private JLabel AlmanacButton;
    private JLabel nameBoardButton;
    private JLabel quickplayButton;
    private JLabel adventureButton;
    private JLabel MainMenuBG;
    private JPanel chooseMap;
    private JLabel chooseMapLogo;
    private JLabel chooseMapBackButton;
    private JLabel DayCard;
    private JLabel NightCard;
    private JLabel PoolCard;
    private JLabel chooseMapBG1;
    private JLabel chooseMapBG2;
    private JLabel chooseMapBG3;

    Clip clip; // LOCAL VARIABLE CLIP FOR BGM
    Clip onClick; // LOCAL VARIABLE FOR ON-CLICK EVENT SOUND

    public MainMenu(){ // CONSTRUCTOR
        playSound("src/com/company/Soundtrack/MainMenu.wav");
        setVisible(true);
        initComponents();
        setOffPanelVisibility();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    public void setOffPanelVisibility(){ // MEMATIKAN SEMUA PANEL WAKTU MASUK KE GAME
        mainMenu.setVisible(true);
        chooseMap.setVisible(false);
        Bonus.setVisible(false);
        exitPanel.setVisible(false);
        HelpPanel.setVisible(false);
        AlmanacPanel.setVisible(false);
        changeNamePanel.setVisible(false);
    }
    public void setOffButtonVisibility(){
        exitButton.setVisible(false);
        bonusButton.setVisible(false);
        HelpButton.setVisible(false);
        AlmanacButton.setVisible(false);
        nameBoardButton.setVisible(false);
        quickplayButton.setVisible(false);
        adventureButton.setVisible(false);
    }
    public void setOnButtonVisibility(){
        exitButton.setVisible(true);
        bonusButton.setVisible(true);
        HelpButton.setVisible(true);
        AlmanacButton.setVisible(true);
        nameBoardButton.setVisible(true);
        quickplayButton.setVisible(true);
        adventureButton.setVisible(true);
    }

    // ======= SOUND EFFECT & BGM ========
    public void playSound(String soundName) { // MUSIC PLAYER
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).toURI().toURL());
            clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception ex) { ex.printStackTrace( ); }
    }
    public void onCLickSound(String soundName) { // MUSIC PLAYER
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            onClick = AudioSystem.getClip( );
            onClick.open(audioInputStream);
            onClick.start( );
        } catch(Exception ex) { ex.printStackTrace( ); }
    }

    // ====== ENABLE & DISABLE VOID ======
    public void enableSubMenu(){ mainMenu.setVisible(false);subMenu.setVisible(true); }
    public void disableSubMenu(){ mainMenu.setVisible(true);subMenu.setVisible(false); }

    // ADVENTURE BUTTON
    private void adventureButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        mainMenu.setVisible(false); chooseMap.setVisible(true);
        gameMode = "Adventure";
    }
    private void adventureButtonMouseEntered(MouseEvent e) { adventureButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/AdventureButtonHover.png"))); }
    private void adventureButtonMouseExited(MouseEvent e) { adventureButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/AdventureButton.png"))); }
    // QUICK PLAY BUTTON
    private void quickplayButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav")
        ;mainMenu.setVisible(false); chooseMap.setVisible(true);
        gameMode = "Survival";
    }
    private void quickplayButtonMouseEntered(MouseEvent e) { quickplayButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuickplayButtonHover.png"))); }
    private void quickplayButtonMouseExited(MouseEvent e) { quickplayButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuickplayButton.png"))); }
    // EXIT BUTTON
    private void exitButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/menuClick.wav");
        exitPanel.setVisible(true);
        setOffButtonVisibility();
    }
    private void exitButtonMouseEntered(MouseEvent e) { exitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/ExitButtonHover.png"))); }
    private void exitButtonMouseExited(MouseEvent e) { exitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/ExitButton.png"))); }
    // EXIT PANEL BUTTONS
    private void quitButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        this.dispose();
    }
    private void quitButtonMouseEntered(MouseEvent e) { quitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelExitButtonHover.png"))); }
    private void quitButtonMouseExited(MouseEvent e) { quitButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelExitButton.png"))); }
    private void cancelButtonMouseClicked(MouseEvent e) { setOnButtonVisibility(); onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");exitPanel.setVisible(false); }
    private void cancelButtonMouseEntered(MouseEvent e) { cancelButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelCancelButtonHover.png"))); }
    private void cancelButtonMouseExited(MouseEvent e) { cancelButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/QuitPanelCancelButton.png"))); }
    // HELP BUTTON
    private void HelpButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        HelpPanel.setVisible(true);
        enableSubMenu();
    }
    private void HelpButtonMouseEntered(MouseEvent e) { HelpButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/HelpButtonHover.png")));}
    private void HelpButtonMouseExited(MouseEvent e) { HelpButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/HelpButton.png")));}
    private void helpBackButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");
        HelpPanel.setVisible(false);
        disableSubMenu();
    }
    // ALMANAC
    private void AlmanacButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        AlmanacPanel.setVisible(true);
        enableSubMenu();
    }
    private void AlmanacButtonMouseEntered(MouseEvent e) { AlmanacButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/AlmanacHover.png")));}
    private void AlmanacButtonMouseExited(MouseEvent e) { AlmanacButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/Almanac.png"))); }
    private void almanackBackButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");
        AlmanacPanel.setVisible(false);
        disableSubMenu();
    }
    // BONUS BUTTON
    private void bonusButtonMouseClicked(MouseEvent e) {
        clip.stop();
        enableSubMenu();
        onCLickSound("src/com/company/SoundEffect/Bonus.wav");
        Bonus.setVisible(true);
    }
    // NAME BOARD BUTTON
    private void nameBoardButtonMouseClicked(MouseEvent e) {
        setOffButtonVisibility();
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        changeNamePanel.setVisible(true);
    }
    private void nameBoardButtonMouseEntered(MouseEvent e) { nameBoardButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/nameBoardButtonHover.png"))); }
    private void nameBoardButtonMouseExited(MouseEvent e) { nameBoardButton.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/nameBoardButton.png"))); }
    private void okButtonMouseClicked(MouseEvent e) {
        setOnButtonVisibility();
        onCLickSound("src/com/company/SoundEffect/buttonSelect.wav");
        Name.setText(inputName.getText());changeNamePanel.setVisible(false);
    }
    // CHOOSE MAP PANEL
    private void chooseMapBackButtonMouseClicked(MouseEvent e) {
        onCLickSound("src/com/company/SoundEffect/buttonCancel.wav");
        chooseMap.setVisible(false);
        mainMenu.setVisible(true);
    }
    private void DayCardMouseEntered(MouseEvent e)  {
        chooseMapBG1.setVisible(true);
        DayCard.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/DayCardHover.png")));
    }
    private void DayCardMouseExited(MouseEvent e) { DayCard.setIcon(new ImageIcon(getClass().getResource("Asset/MainMenu/DayCard.png"))); }
    private void NightCardMouseEntered(MouseEvent e){
        chooseMapBG1.setVisible(false);
        chooseMapBG2.setVisible(true);
        NightCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/NightCardHover.png")));
    }
    private void NightCardMouseExited(MouseEvent e) { NightCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/NightCard.png"))); }
    private void PoolCardMouseEntered(MouseEvent e) {
        chooseMapBG1.setVisible(false);
        chooseMapBG2.setVisible(false);
        PoolCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/PoolCardHover.png")));
    }
    private void PoolCardMouseExited(MouseEvent e) { PoolCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/PoolCard.png"))); }

    private void DayCardMouseClicked(MouseEvent e) {
        // PLAY GAME
        clip.stop();
        Main.startGame("Day", gameMode);
        this.dispose();
    }
    private void NightCardMouseClicked(MouseEvent e) {
        // PLAY GAME
        clip.stop();
        Main.startGame("Night", gameMode);
        this.dispose();
    }
    private void PoolCardMouseClicked(MouseEvent e) {
        // PLAY GAME
        clip.stop();
        Main.startGame("Pool", gameMode);
        this.dispose();
    }

    private void initComponents() {
        mainMenu = new JPanel();
        subMenu = new JPanel();
        exitPanel = new JPanel();
        quitButton = new JLabel();
        cancelButton = new JLabel();
        exitMenu = new JLabel();
        HelpPanel = new JPanel();
        helpBackButton = new JLabel();
        HelpBG = new JLabel();
        AlmanacPanel = new JPanel();
        almanackBackButton = new JLabel();
        Bonus = new JPanel();
        cilukBA = new JLabel();
        changeNamePanel = new JPanel();
        inputName = new JTextField();
        okButton = new JLabel();
        changeNameMenu = new JLabel();
        Name = new JLabel();
        exitButton = new JLabel();
        bonusButton = new JLabel();
        HelpButton = new JLabel();
        AlmanacButton = new JLabel();
        EngineerPhoto = new JLabel();
        nameBoardButton = new JLabel();
        quickplayButton = new JLabel();
        adventureButton = new JLabel();
        MainMenuBG = new JLabel();
        chooseMap = new JPanel();
        chooseMapLogo = new JLabel();
        chooseMapBackButton = new JLabel();
        DayCard = new JLabel();
        NightCard = new JLabel();
        PoolCard = new JLabel();
        chooseMapBG1 = new JLabel();
        chooseMapBG2 = new JLabel();
        chooseMapBG3 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        // ====== SUB MENU ======
        {
            subMenu.setLayout(null);
            subMenu.setVisible(false);

            //======== HelpPanel ========
            {
                HelpPanel.setVisible(false);
                HelpPanel.setLayout(null);

                //---- helpBackButton ----
                helpBackButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/HelpBackButton.png")));
                helpBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                helpBackButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        helpBackButtonMouseClicked(e);
                    }
                });
                HelpPanel.add(helpBackButton);
                helpBackButton.setBounds(new Rectangle(new Point(325, 520), helpBackButton.getPreferredSize()));

                //---- HelpBG ----
                HelpBG.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/Help.png")));
                HelpPanel.add(HelpBG);
                HelpBG.setBounds(new Rectangle(new Point(0, 0), HelpBG.getPreferredSize()));
            }
            subMenu.add(HelpPanel);
            HelpPanel.setBounds(0, 0, 800, 600);

            //======== AlmanacPanel ========
            {
                AlmanacPanel.setVisible(false);
                AlmanacPanel.setLayout(null);

                //---- almanackBackButton ----
                almanackBackButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/HelpBackButton.png")));
                almanackBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                almanackBackButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        almanackBackButtonMouseClicked(e);
                    }
                });
                AlmanacPanel.add(almanackBackButton);
                almanackBackButton.setBounds(new Rectangle(new Point(320, 530), almanackBackButton.getPreferredSize()));

                //---- EngineerPhoto ----
                EngineerPhoto.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/EngineerPhoto.png")));
                AlmanacPanel.add(EngineerPhoto);
                EngineerPhoto.setBounds(new Rectangle(new Point(0, 0), EngineerPhoto.getPreferredSize()));
            }
            subMenu.add(AlmanacPanel);
            AlmanacPanel.setBounds(0, 0, 800, 600);

            //======== Bonus ========
            {
                Bonus.setVisible(false);
                Bonus.setLayout(null);

                //---- cilukBA ----
                cilukBA.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/Bonus.jpg")));
                Bonus.add(cilukBA);
                cilukBA.setBounds(new Rectangle(new Point(0, 0), cilukBA.getPreferredSize()));
            }
            subMenu.add(Bonus);
            Bonus.setBounds(0, 0, 800, 600);
        }
        contentPane.add(subMenu);
        subMenu.setBounds(0, -30, 800, 600);

        //======== mainMenu ========
        {
            mainMenu.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
                    border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER
                    ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font
                    . BOLD ,12 ) ,java . awt. Color .red ) ,mainMenu. getBorder () ) ); mainMenu. addPropertyChangeListener(
                new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r"
                        .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            mainMenu.setLayout(null);

            //======== exitPanel ========
            {
                exitPanel.setVisible(false);
                exitPanel.setBackground(new Color(60, 63, 65, 123));
                exitPanel.setLayout(null);

                //---- quitButton ----
                quitButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanelExitButton.png")));
                quitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                quitButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        quitButtonMouseClicked(e);
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
                quitButton.setBounds(new Rectangle(new Point(235, 380), quitButton.getPreferredSize()));

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
                cancelButton.setBounds(new Rectangle(new Point(410, 380), cancelButton.getPreferredSize()));

                //---- exitMenu ----
                exitMenu.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuitPanel.png")));
                exitPanel.add(exitMenu);
                exitMenu.setBounds(new Rectangle(new Point(200, 155), exitMenu.getPreferredSize()));

            }
            mainMenu.add(exitPanel);
            exitPanel.setBounds(0, 0, 800, 600);

            //======== changeNamePanel ========
            {
                changeNamePanel.setBackground(new Color(60, 63, 65, 123));
                changeNamePanel.setVisible(false);
                changeNamePanel.setLayout(null);

                //---- inputName ----
                inputName.setBackground(new Color(99, 65, 16));
                inputName.setForeground(new Color(255, 250, 148));
                inputName.setFont(new Font("Hobo Std", Font.PLAIN, 13));
                changeNamePanel.add(inputName);
                inputName.setBounds(230, 302, 335, 26);

                //---- okButton ----
                okButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/okButton.png")));
                okButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });
                changeNamePanel.add(okButton);
                okButton.setBounds(new Rectangle(new Point(315, 365), okButton.getPreferredSize()));

                //---- changeNameMenu ----
                changeNameMenu.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/changeNamePanel.png")));
                changeNamePanel.add(changeNameMenu);
                changeNameMenu.setBounds(new Rectangle(new Point(195, 180), changeNameMenu.getPreferredSize()));
            }
            mainMenu.add(changeNamePanel);
            changeNamePanel.setBounds(0, 0, 800, 600);

            //---- Name ----
            Name.setHorizontalAlignment(SwingConstants.CENTER);
            Name.setFont(new Font("Hobo Std", Font.PLAIN, 20));
            Name.setForeground(new Color(255, 255, 162));
            mainMenu.add(Name);
            Name.setBounds(40, 155, 290, 30);

            //---- exitButton ----
            exitButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/ExitButton.png")));
            exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    exitButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    exitButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    exitButtonMouseExited(e);
                }
            });
            mainMenu.add(exitButton);
            exitButton.setBounds(new Rectangle(new Point(18, 352), exitButton.getPreferredSize()));

            //---- bonusButton ----
            bonusButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/Bonus.png")));
            bonusButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bonusButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bonusButtonMouseClicked(e);
                }
            });
            mainMenu.add(bonusButton);
            bonusButton.setBounds(new Rectangle(new Point(357, 387), bonusButton.getPreferredSize()));

            //---- HelpButton ----
            HelpButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/HelpButton.png")));
            HelpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            HelpButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HelpButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    HelpButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    HelpButtonMouseExited(e);
                }
            });
            mainMenu.add(HelpButton);
            HelpButton.setBounds(new Rectangle(new Point(695, 373), HelpButton.getPreferredSize()));

            //---- AlmanacButton ----
            AlmanacButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/Almanac.png")));
            AlmanacButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            AlmanacButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    AlmanacButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    AlmanacButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    AlmanacButtonMouseExited(e);
                }
            });
            mainMenu.add(AlmanacButton);
            AlmanacButton.setBounds(new Rectangle(new Point(428, 452), AlmanacButton.getPreferredSize()));

            //---- nameBoardButton ----
            nameBoardButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/nameBoardButton.png")));
            nameBoardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            nameBoardButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    nameBoardButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    nameBoardButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    nameBoardButtonMouseExited(e);
                }
            });
            mainMenu.add(nameBoardButton);
            nameBoardButton.setBounds(new Rectangle(new Point(1, 209), nameBoardButton.getPreferredSize()));

            //---- quickplayButton ----
            quickplayButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/QuickplayButton.png")));
            quickplayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            quickplayButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    quickplayButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    quickplayButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    quickplayButtonMouseExited(e);
                }
            });
            mainMenu.add(quickplayButton);
            quickplayButton.setBounds(new Rectangle(new Point(394, 209), quickplayButton.getPreferredSize()));

            //---- adventureButton ----
            adventureButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/AdventureButton.png")));
            adventureButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            adventureButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    adventureButtonMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    adventureButtonMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    adventureButtonMouseExited(e);
                }
            });
            mainMenu.add(adventureButton);
            adventureButton.setBounds(new Rectangle(new Point(383, 43), adventureButton.getPreferredSize()));

            //---- MainMenuBG ----
            MainMenuBG.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/MainMenu BG.png")));
            mainMenu.add(MainMenuBG);
            MainMenuBG.setBounds(new Rectangle(new Point(0, 30), MainMenuBG.getPreferredSize()));
        }
        contentPane.add(mainMenu);
        mainMenu.setBounds(0, -30, 800, 600);

        //======== chooseMap ========
        {
            chooseMap.setLayout(null);

            //---- chooseMapLogo ----
            chooseMapLogo.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/chooseMapLogo.png")));
            chooseMap.add(chooseMapLogo);
            chooseMapLogo.setBounds(new Rectangle(new Point(145, 20), chooseMapLogo.getPreferredSize()));

            //---- chooseMapBackButton ----
            chooseMapBackButton.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/HelpBackButton.png")));
            chooseMapBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            chooseMapBackButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chooseMapBackButtonMouseClicked(e);
                }
            });
            chooseMap.add(chooseMapBackButton);
            chooseMapBackButton.setBounds(new Rectangle(new Point(320, 500), chooseMapBackButton.getPreferredSize()));

            //---- DayCard ----
            DayCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/DayCard.png")));
            DayCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            DayCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    DayCardMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    DayCardMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    DayCardMouseExited(e);
                }
            });
            chooseMap.add(DayCard);
            DayCard.setBounds(new Rectangle(new Point(25, 215), DayCard.getPreferredSize()));

            //---- NightCard ----
            NightCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/NightCard.png")));
            NightCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            NightCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NightCardMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    NightCardMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    NightCardMouseExited(e);
                }
            });
            chooseMap.add(NightCard);
            NightCard.setBounds(new Rectangle(new Point(285, 215), NightCard.getPreferredSize()));

            //---- PoolCard ----
            PoolCard.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/PoolCard.png")));
            PoolCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            PoolCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PoolCardMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    PoolCardMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    PoolCardMouseExited(e);
                }
            });
            chooseMap.add(PoolCard);
            PoolCard.setBounds(new Rectangle(new Point(545, 215), PoolCard.getPreferredSize()));

            //---- chooseMapBG1 ----
            chooseMapBG1.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/chooseMapBG1.jpg")));
            chooseMap.add(chooseMapBG1);
            chooseMapBG1.setBounds(new Rectangle(new Point(0, 0), chooseMapBG1.getPreferredSize()));

            //---- chooseMapBG2 ----
            chooseMapBG2.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/chooseMapBG2.jpg")));
            chooseMap.add(chooseMapBG2);
            chooseMapBG2.setBounds(new Rectangle(new Point(0, 0), chooseMapBG2.getPreferredSize()));

            //---- chooseMapBG3 ----
            chooseMapBG3.setIcon(new ImageIcon(getClass().getResource("/com/company/Asset/MainMenu/chooseMapBG3.jpg")));
            chooseMap.add(chooseMapBG3);
            chooseMapBG3.setBounds(new Rectangle(new Point(0, 0), chooseMapBG3.getPreferredSize()));
        }
        contentPane.add(chooseMap);
        chooseMap.setBounds(0, -30, 800, 600);

        {
            // ===== SETTING PREFERED SIZE =====
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
    }
}