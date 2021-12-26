package view;


import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static GameController controller;
    public static ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;

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
        ImageIcon background = new ImageIcon("Project/src/pictures/ChessBoardBackground.png");
        background.setImage(background.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));
        JLabel jLabel = new JLabel(background);
        jLabel.setIcon(background);
        jLabel.setSize(960, 540);

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

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation(450,70);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");
            chessBoardPanel.clearChessPieces();
            chessBoardPanel.initialGame();
            repaint();
            controller.setCurrentPlayer(ChessPiece.BLACK);
            statusPanel.setPlayerText("BLACK");
            statusPanel.setScoreText(2,2);
        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(450,170);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.readFileData(filePath);
            repaint();
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(450,270);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.writeDataToFile(filePath);
        });

        JButton cheatModelBtn = new JButton("Cheat");
        cheatModelBtn.setSize(120, 50);
        cheatModelBtn.setLocation(450,370);
        cheatModelBtn.addActionListener(e -> {
            ChessGridComponent.cheatModel = - ChessGridComponent.cheatModel;


        });

        this.add(chessBoardPanel);
        this.add(statusPanel);
        this.add(restartBtn);
        this.add(loadGameBtn);
        this.add(saveGameBtn);
        this.add(cheatModelBtn);
        this.add(jLabel,new Integer(Integer.MIN_VALUE));

        this.setVisible(true);
        this.setResizable(false);//固定窗口大小要在可视化之后
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
