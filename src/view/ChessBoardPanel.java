package view;

import components.ChessGridComponent;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    private ChessGridComponent[][] chessGrids;

    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);
        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess
        repaint();
    }

    public void changePanel(ChessPiece currentPlayer,int row,int col){
        setChessGrids(Move(chessGrids,currentPlayer,row,col));
        repaint();
    }


    public void setChessGrids(ChessGridComponent[][] chessGrids) {
        this.chessGrids = chessGrids;
    }

    public ChessGridComponent[][] getChessGrids() {
        return chessGrids;
    }

    /**
     * set an empty chessboard
     */
    public void initialChessGrids() {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];
        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                this.add(chessGrids[i][j]);
            }
        }
    }


    /**
     * initial origin four chess
     */
    public void initialGame() {

        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
    }

   public void clearChessPieces(){
        for(int i=0;i<8;i++){
            for(int e=0;e<8;e++){
                chessGrids[i][e].setChessPiece(null);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
        //todo: complete this method
        int nextmove;
        if(currentPlayer == ChessPiece.BLACK){
            nextmove = -1;
        }else{
            nextmove = 1;
        }
        if(canPut(chessGrids,nextmove,row,col)) {
            return true;
        }else{
            return false;
        }
    }

    public static int[][] setBoardToArray(ChessGridComponent[][] board){
        int[][] arrayBoard = new int[8][8];
        for(int i = 0;i<8;i++){
            for(int e=0;e<8;e++){
                if(board[i][e].getChessPiece() == ChessPiece.BLACK){
                    arrayBoard[i][e] = -1;
                }else if(board[i][e].getChessPiece() == ChessPiece.WHITE){
                    arrayBoard[i][e] = 1;
                }else{
                    arrayBoard[i][e] = 0;
                }
            }
        }
        return arrayBoard;
    }

    public static ChessGridComponent[][] setArrayToBoard(int[][] array){
        ChessGridComponent[][] board = new ChessGridComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                board[i][j] = gridComponent;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(array[i][j] == -1){
                    board[i][j].setChessPiece(ChessPiece.BLACK);
                }if(array[i][j] == 1){
                    board[i][j].setChessPiece(ChessPiece.WHITE);
                }else{
                    board[i][j].setChessPiece(null);
                }
            }
        }
        return board;
    }

    public static boolean canPut(ChessGridComponent[][] board, int nextMove, int row, int col){
        int[][] arrayBoard = setBoardToArray(board);
        int[][] directions = new int[][]{
                {0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int i = 0; i < 8; i++){
            if (canPutPerDirection(arrayBoard, nextMove, row, col, directions[i])){
                return true;
            }
        }
        return false;
    }

    public static boolean canPutPerDirection(int[][] board, int nextMove, int row, int col, int[] direction){
        if (board[row][col] != 0){
            return false;
        }
        int dx = direction[0];
        int dy = direction[1];
        int count = 0;
        boolean hasEnd = false;
        while (row + dx < 8 && row + dx >= 0 && col + dy < 8 && col + dy >= 0){
            row += dx;
            col += dy;
            if (board[row][col] == 0){
                break;
            } else if (board[row][col] == nextMove) {
                hasEnd = true;
                break;
            } else {
                count += 1;
            }
        }
        if (count > 0 && hasEnd){
            return true;
        } else {
            return false;
        }
    }

    public static ChessGridComponent[][]  Move(ChessGridComponent[][] board, ChessPiece currentPlayer ,int x,int y) {

        int[] xdirection = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] ydirection = {0, 1, 1, 1, 0, -1, -1, -1};

        board[x][y].setChessPiece(currentPlayer);

        for (int k = 0; k < 8; k++) {
            int tempx = x + xdirection[k];
            int tempy = y + ydirection[k];
            if ((tempx < 0 || tempx > 7) || (tempy < 0 || tempy > 7)) {
                continue;
            }
            if (board[tempx][tempy].getChessPiece() == null) {
                continue;
            }
            if (board[tempx][tempy].getChessPiece() != currentPlayer) {
                while (tempx >= 0 && tempy >= 0 && tempx <= 7 && tempy <= 7 && board[tempx][tempy].getChessPiece() != null && board[tempx][tempy].getChessPiece() != currentPlayer) {

                    tempx += xdirection[k];
                    tempy += ydirection[k];
                }
                if (tempx < 0 || tempy > 7 || tempy < 0 || tempx > 7) {
                    continue;
                }
                if (board[tempx][tempy].getChessPiece() == currentPlayer) {
                    tempx = x + xdirection[k];
                    tempy = y + ydirection[k];
                    while (tempx >= 0 && tempy >= 0 && tempx <= 7 && tempy <= 7 && board[tempx][tempy].getChessPiece() != null && board[tempx][tempy].getChessPiece() != currentPlayer) {
                        board[tempx][tempy].setChessPiece(currentPlayer);
                        tempx += xdirection[k];
                        tempy += ydirection[k];
                    }
                }
            }
        }
        return board;
    }

}
