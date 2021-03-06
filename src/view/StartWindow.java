package view;

import music.bgm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.picturesInput.rule;

public class StartWindow extends JFrame {
    public static JFrame gameWindow = new JFrame("2021F CS102A Project Reversi");//创建面板标题
    public static int easy = 0;
    public static int hard = 0;

    public void gameWindow() {

        //插入背景图片
        ImageIcon background = new ImageIcon(picturesInput.startBackground);
        background.setImage(background.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));
        JLabel jLabel = new JLabel(background);
        jLabel.setIcon(background);
        jLabel.setSize(960, 540);

        ImageIcon background2 = new ImageIcon(picturesInput.startBackground);
        background.setImage(background2.getImage().getScaledInstance(960, 540, Image.SCALE_DEFAULT));
        JLabel jLabel2 = new JLabel(background);
        jLabel2.setIcon(background);
        jLabel2.setSize(960, 540);

        //把按钮图片变成图标
        Icon icon01 = new ImageIcon(picturesInput.start);
        Icon icon02 = new ImageIcon(rule);
        Icon easy = new ImageIcon(picturesInput.easyGame);
        Icon hard = new ImageIcon(picturesInput.hardGame);

        //开始游戏按钮
        JButton button1 = new JButton();
        button1.setIcon(icon01);
        button1.setToolTipText("开始游戏 图片按钮");
        button1.setBounds(165, 270, 200, 50);

        //游戏规则按钮
        JButton button2 = new JButton();
        button2.setIcon(icon02);
        button1.setToolTipText("游戏规则 图片按钮");
        button2.setBounds(165, 370, 200, 50);

        //简单游戏按钮
        JButton easyButton = new JButton();
        easyButton.setIcon(easy);
        easyButton.setToolTipText("简单模式 图片按钮");

        //困难游戏按钮
        JButton hardButton = new JButton();
        hardButton.setIcon(hard);
        hardButton.setToolTipText("困难模式 图片按钮");

        //初始化面板
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setSize(960, 540);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);

        //设置面板中容器，并设置自由布局类型
        Container container = gameWindow.getContentPane();
        container.setLayout(null);

        //向容器中添加组件（jLabel为背景的组件）
        container.add(button1);
        container.add(button2);
        container.add(jLabel);
        new bgm().run();

        //开始游戏按钮的监听器
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                button1.setVisible(false);
                button2.setVisible(false);
                jLabel.setVisible(false);

                easyButton.setToolTipText("简单模式 图片按钮");
                easyButton.setBounds(165, 270, 200, 50);
                container.add(easyButton);

                hardButton.setToolTipText("困难模式 图片按钮");
                hardButton.setBounds(165, 370, 200, 50);
                container.add(hardButton);
                container.add(jLabel2);

            }
        });

        //游戏规则按钮的监听器
        //插入游戲槼則
        button2.addActionListener(e -> new rule());

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow.easy =1;

                gameWindow.setVisible(false);
                GameFrame mainFrame = new GameFrame(800);
                mainFrame.setVisible(true);
                //点击开始游戏后关闭主页面
                //插入游戲棋盤界面

            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow.hard =1;

                gameWindow.setVisible(false); //游戏开始后主界面消失
                GameFrame mainFrame = new GameFrame(800);
                mainFrame.setVisible(true);
                //点击开始游戏后关闭主页面
                //插入游戲棋盤界面

            }
        });


    }

    //游戏规则弹窗内容
    class rule extends JDialog {
        public rule() {
            this.setVisible(true);
            this.setBounds(550, 450, 600, 400);
            Container container = this.getContentPane();
            JLabel jLabel = new JLabel("<html>1、如果玩家在棋盘上没有地方可以下子，则该玩家对手可以连下。双方都没有棋子可以下时棋局结束，以棋子数目来计算胜负，棋子多的一方获胜。\n" +
                    "<br>" +
                    "2、在棋盘还没有下满时，如果一方的棋子已经被对方吃光，则棋局也结束。将对手棋子吃光的一方获胜。翻转棋类似于棋盘游戏“奥赛罗 (Othello)”，是一种得分会戏剧性变化并且需要长时间思考的策略性游戏。\n" +
                    "<br>" +
                    "3、翻转棋的棋盘上有 64 个可以放置黑白棋子的方格(类似于国际象棋和跳棋)。游戏的目标是使棋盘上自己颜色的棋子数超过对手的棋子数。\n" +
                    "<br>" +
                    "4、当游戏双方都不能再按规则落子时，游戏就结束了。通常，游戏结束时棋盘上会摆满了棋子。结束时谁的棋子最多谁就是赢家。</html>");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font("仿宋", Font.PLAIN, 20);
            jLabel.setFont(font);
            jLabel.setForeground(new Color(215, 16, 16, 255));
            container.add(jLabel);
        }
    }
}


