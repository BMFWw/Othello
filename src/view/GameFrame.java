package view;


import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    public static GameController controller;
    public static ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    public static JButton restartBtn = new JButton("Restart");
    public static JButton returnBtn = new JButton("Return");
    public static List<String> step = new ArrayList<String>();
    public static int stepCount = 0;

    private int AI = -1;
    private int cheatModel = -1;

    public GameController getController() {
        return controller;
    }

    public ChessBoardPanel getChessBoardPanel() {
        return chessBoardPanel;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public static void setController(GameController controller) {
        GameFrame.controller = controller;
    }

    public  void setChessBoardPanel() {
        this.chessBoardPanel = GameFrame.controller.getGamePanel();
    }

    public void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }


    public GameFrame(int frameSize) {

        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //插入背景图片
        ImageIcon easyBackground = new ImageIcon(picturesInput.easyBackground);
        easyBackground.setImage(easyBackground.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));

        ImageIcon hardBackground = new ImageIcon(picturesInput.hardBackground);
        hardBackground.setImage(hardBackground.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));


        JLabel easyJLabel = new JLabel(easyBackground);
        easyJLabel.setIcon(easyBackground);
        easyJLabel.setSize(960, 540);

        JLabel hardJLabel = new JLabel(hardBackground);
        hardJLabel.setIcon(hardBackground);
        hardJLabel.setSize(960, 540);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(960, 540);

        //固定大小
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation(50,50);


        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.08));
        statusPanel.setLocation(10,0);
        statusPanel.setBackground(null);
        statusPanel.setOpaque(false);


        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);



        restartBtn.setSize(120, 50);
        restartBtn.setLocation(450,65);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");
            chessBoardPanel.clearChessPieces();
            chessBoardPanel.initialGame();
            repaint();
            controller.setCurrentPlayer(ChessPiece.BLACK);
            statusPanel.setPlayerText("BLACK");
            statusPanel.setScoreText(2,2);

            ChessGridComponent.countWindow=0;//重新开始使其归零
        });

        returnBtn.setSize(120, 50);
        returnBtn.setLocation(600,65);
        returnBtn.addActionListener(e -> {
            dispose();
           StartWindow.gameWindow.setVisible(true);
           ChessGridComponent.AI = -1;
           ChessGridComponent.cheatModel=-1;//重置cheat判断
           AI = -1;
           cheatModel = -1;
           StartWindow.easy=0;//重置判断使下次点击简单或困难模式时判断正确
           StartWindow.hard=0;
        });

        JButton aiBtn = new JButton("AI Off");
        aiBtn.setSize(120, 50);//与AI相关操作
        aiBtn.setLocation(600,165);
        String AiOn = "AI On";
        String AiOff = "AI Off";
        aiBtn.addActionListener(e -> {//ai模式开启关闭操作可效仿cheat模式，cheat位于下方
            AI = - AI;
            ChessGridComponent.AI = -ChessGridComponent.AI;
            if(AI == -1)aiBtn.setText(AiOff);
            if(AI == 1)aiBtn.setText(AiOn);

            //放具体操作
        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(450,165);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.readFileData(filePath);
            statusPanel.refresh();
            repaint();
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(450,265);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.writeDataToFile(filePath);
        });

        JButton cheatModelBtn = new JButton("Cheat Off");
        String cheatOff = "Cheat Off";
        String cheatOn = "Cheat On";
        cheatModelBtn.setSize(120, 50);
        cheatModelBtn.setLocation(450,365);
        cheatModelBtn.addActionListener(e -> {
            cheatModel = -cheatModel;
            ChessGridComponent.cheatModel = - ChessGridComponent.cheatModel;//cheat模式开启关闭判断，-1则关闭，1则开启，将cheatModel=-1以public static的形式放在需要调用方法的类里
            if(cheatModel == -1)cheatModelBtn.setText(cheatOff);
            if(cheatModel == 1)cheatModelBtn.setText(cheatOn);
        });

        this.add(chessBoardPanel);
        this.add(statusPanel);
        this.add(restartBtn);
        this.add(returnBtn);
        this.add(aiBtn);
        this.add(loadGameBtn);
        this.add(saveGameBtn);
        this.add(cheatModelBtn);
        if(StartWindow.easy==1) {
            this.add(easyJLabel, new Integer(Integer.MIN_VALUE));
        }else if(StartWindow.hard==1) {
            this.add(hardJLabel,new Integer(Integer.MIN_VALUE));
        }
        this.setVisible(true);
        this.setResizable(false);//固定窗口大小要在可视化之后
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
