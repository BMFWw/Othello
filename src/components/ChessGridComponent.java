package components;

import controller.GameController;
import model.ChessPiece;
import view.ChessBoardPanel;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(0, 255, 255, 204);
    private ChessPiece chessPiece;
    private int row;
    private int col;
    public static int cheatModel = -1;//默认关闭

    public ChessGridComponent() {

    }

    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
    }

    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method

        if (cheatModel == -1) {//cheatModel关闭
            if (GameFrame.controller.canClick(row, col)) {//规定在哪能下
                if (this.chessPiece == null) {
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                }
                GameFrame.chessBoardPanel.changePanel(GameFrame.controller.getCurrentPlayer(), row, col);
                repaint();
                GameFrame.controller.swapPlayer();
            } else {
                System.out.printf("Cannot click on this square\n");
            }

        } else {
            if (this.chessPiece == null) {//只能下空的地方
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                GameFrame.chessBoardPanel.changePanel(GameFrame.controller.getCurrentPlayer(), row, col);
                repaint();
                GameFrame.controller.swapPlayer();
            }
        }


        //判断区域 resultJudge是在没有子下的前提下实现的，白大返回1，黑大返回-1，黑白平返回0
        int resultJudge = new ChessGridComponent().resultJudge();//判断游戏是否结束在resultJudge中实现了 此时返回值
        if (resultJudge == 1) {
            new EndDialogWin();
        } else if (resultJudge == 0) {
            new EndDialogTie();
        } else if (resultJudge == -1) {
            new EndDialogLose();
        }

    }


    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPieceCanPut(Graphics g) {//能画的地方显示高光
        g.setColor(new Color(0, 255, 255));
        g.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);
    }

    public void drawPiece(Graphics g) {//画棋盘格子，每个格子都被分成小组件
        g.setColor(gridColor);
        g.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);

        if(cheatModel==-1) {//cheatModel关闭时显示高光
            if (GameFrame.controller.canClick(row, col)) {//本方法自带遍历棋盘 若能下则显示高光
                drawPieceCanPut(g);
            }
        }

    }

    public boolean judge(ChessPiece chessPiece) {//判断是否结束 返回是或不是
        int count = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (ChessBoardPanel.chessGrids[row][col].chessPiece == null) {
                    if (GameFrame.controller.canClick(row, col)) {
                        count++;
                    }
                }
            }
        }

        if (count == 0) return false;
        return true;
    }

    //用于结束窗口中判断
    public int resultJudge() {//判断最终输赢，如果白色赢则返回1，平局0，黑色赢返回-1，以白色为我方
        int whiteScore = new GameController().getWhiteScore();
        int blackScore = new GameController().getBlackScore();
        if (judge(this.chessPiece = GameFrame.controller.getCurrentPlayer())) {//判断游戏是否结束
        } else {
            if (whiteScore > blackScore) {
                return 1;
            } else if (whiteScore == blackScore) {
                return 0;
            } else if (whiteScore < blackScore) {
                return -1;
            }
        }
        if (whiteScore + blackScore == 64) {
            if (whiteScore > blackScore) {
                return 1;
            } else if (whiteScore == blackScore) {
                return 0;
            } else if (whiteScore < blackScore) {
                return -1;
            }
        }
        return 3;//能下返回3   3无用 只是为了不报错而返回一个值
    }
}


class EndDialogWin extends JDialog {
    public EndDialogWin() {//初始化文字框
        //插入背景图片
        ImageIcon background = new ImageIcon("CHESS/src/view/pictures/winGround.png");
        background.setImage(background.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        JLabel tieGround = new JLabel(background);
        tieGround.setIcon(background);
        tieGround.setSize(500, 300);

        this.setVisible(true);
        this.setFocusable(true);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();

        container.add(tieGround, BorderLayout.CENTER);

    }
}

class EndDialogTie extends JDialog {
    public EndDialogTie() {//初始化文字框
        //插入背景图片
        ImageIcon background = new ImageIcon("CHESS/src/view/pictures/tieGround.png");
        background.setImage(background.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        JLabel tieGround = new JLabel(background);
        tieGround.setIcon(background);
        tieGround.setSize(500, 300);

        this.setVisible(true);
        this.setFocusable(true);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();

        container.add(tieGround, BorderLayout.CENTER);


    }
}

class EndDialogLose extends JDialog {
    public EndDialogLose() {//初始化文字框
        //插入背景图片
        ImageIcon background = new ImageIcon("CHESS/src/view/pictures/loseGround.png");
        background.setImage(background.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT));
        JLabel tieGround = new JLabel(background);
        tieGround.setIcon(background);
        tieGround.setSize(500, 300);

        this.setVisible(true);
        this.setFocusable(true);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();

        container.add(tieGround, BorderLayout.CENTER);
    }
}
