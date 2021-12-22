package components;

import model.ChessPiece;
import view.GameFrame;

import java.awt.*;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(0, 255, 255, 204);
    private ChessPiece chessPiece;
    private int row;
    private int col;

    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
    }

    @Override
    public void onMouseClicked() {
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method
        if (GameFrame.controller.canClick(row, col)) {
            if (this.chessPiece == null) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
            }
            GameFrame.chessBoardPanel.changePanel(GameFrame.controller.getCurrentPlayer(),row,col);
            repaint();
            GameFrame.controller.swapPlayer();
        }else{
            System.out.printf("Cannot click on this square\n");
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

    public void drawPieceCanPut(Graphics g){//能画的地方显示高光
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

        for (int i = 0; i < 8; i++) {//遍历棋盘，能画的地方显示高光
            for (int j = 0; j < 8; j++) {
                if(GameFrame.controller.canClick(row, col)){
                 drawPieceCanPut(g);
                }
            }
        }
    }


}
