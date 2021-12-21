package view;


import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static GameController controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;

    public GameFrame(int frameSize) {

        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //插入背景图片///////////////
        ImageIcon background = new ImageIcon("untitled/src/pictures/ChessBoardBackground.png");
        background.setImage(background.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));
        JLabel jLabel = new JLabel(background);
        jLabel.setIcon(background);
        jLabel.setSize(960, 540);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(960, 540);/////////
        //this.setSize(frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
        //固定大小
        this.setResizable(true);////////////
        this.setLocationRelativeTo(null);

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation(50,50);//////////////
        //chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation(10,0);//////////////////
        statusPanel.setBackground(null);//////////
        statusPanel.setOpaque(false);//////
        //statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);

        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);
        
        this.add(chessBoardPanel);
        this.add(statusPanel);
        this.add(jLabel,new Integer(Integer.MIN_VALUE));//////////

        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
        add(restartBtn);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");
        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(loadGameBtn);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.readFileData(filePath);
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.writeDataToFile(filePath);
        });


        this.setVisible(true);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
