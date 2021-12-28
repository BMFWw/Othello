package view;

import components.ChessGridComponent;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

import static components.ChessGridComponent.chessSize;
import static components.ChessGridComponent.gridSize;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    public static ChessGridComponent[][] chessGrids;
    public static ChessGridComponent[][] tempBoard;

    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        gridSize = length / CHESS_COUNT;
        chessSize = (int) (gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, gridSize, chessSize);

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
                gridComponent.setLocation(j * gridSize, i * gridSize);
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
        repaint();
    }

    public void clearChessPieces(){
        for(int i=0;i<8;i++){
            for(int e=0;e<8;e++){
                chessGrids[i][e].setChessPiece(null);
            }
        }
    }

    public void hardAI(){
        int[][] tempBoard = new int[8][8];
        int[][] aftBoard = new int[8][8];
        ChessGridComponent[][] TempBoard = new ChessGridComponent[8][8];
        for(int i=0;i<8;i++) {
            for (int e = 0; e < 8; e++) {
                tempBoard[i][e] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[i][e];
                System.out.print(tempBoard[i][e]);
            }
            System.out.println();
        }

        for(int i=0;i<8;i++) {
            for (int e = 0; e < 8; e++) {
                aftBoard[i][e] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[i][e];
                System.out.print(aftBoard[i][e]);
            }
            System.out.println();
        }

        int max=0;
        int maxRow=0;
        int maxCol=0;
        int p = 0;
        if(GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK){
            p = -1;
        }else if(GameFrame.controller.getCurrentPlayer() == ChessPiece.WHITE){
            p = 1;
        }

        for(int i=0;i<8;i++){
            for(int e=0;e<8;e++){
                int counter = 0;
                if(GameFrame.chessBoardPanel.canPut(GameFrame.chessBoardPanel.getChessGrids(),p,i,e)){

                    for(int w=0;w<8;w++) {
                        for (int s = 0; s < 8; s++) {
                            aftBoard[w][s] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[w][s];
                            System.out.print(aftBoard[w][s]);
                        }
                        System.out.println();
                    }

                    aftBoard = Move(aftBoard,GameFrame.controller.getCurrentPlayer(),i,e);

                    for(int j=0;j<8;j++){
                        for (int k = 0; k < 8; k++){
                            if(aftBoard[j][k] != tempBoard[j][k]){
                                counter++;
                            }
                        }
                    }

                }
                if(counter>max){
                    max = counter;
                    maxRow = i;
                    maxCol = e;
                }
            }
        }

        System.out.println(max);
        System.out.println(maxCol);
        GameFrame.chessBoardPanel.changePanel(GameFrame.controller.getCurrentPlayer(),maxRow,maxCol);
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(),maxRow,maxCol);
        String s = maxRow + " " + maxCol + " " + ChessGridComponent.cheatModel + " " + p;
        GameFrame.step.add(s);
        GameFrame.stepCount++;
    }

    public void easyAI(){
        int[][] tempBoard = new int[8][8];
        int[][] aftBoard = new int[8][8];
        ChessGridComponent[][] TempBoard = new ChessGridComponent[8][8];
        for(int i=0;i<8;i++) {
            for (int e = 0; e < 8; e++) {
                tempBoard[i][e] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[i][e];
                System.out.print(tempBoard[i][e]);
            }
            System.out.println();
        }

        for(int i=0;i<8;i++) {
            for (int e = 0; e < 8; e++) {
                aftBoard[i][e] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[i][e];
                System.out.print(aftBoard[i][e]);
            }
            System.out.println();
        }

        int min=0;
        int minRow=0;
        int minCol=0;
        int p = 0;
        if(GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK){
            p = -1;
        }else if(GameFrame.controller.getCurrentPlayer() == ChessPiece.WHITE){
            p = 1;
        }

        for(int i=0;i<8;i++){
            for(int e=0;e<8;e++){
                int counter = 64;
                if(GameFrame.chessBoardPanel.canPut(GameFrame.chessBoardPanel.getChessGrids(),p,i,e)){

                    for(int w=0;w<8;w++) {
                        for (int s = 0; s < 8; s++) {
                            aftBoard[w][s] = setBoardToArray(GameFrame.chessBoardPanel.getChessGrids())[w][s];
                            System.out.print(aftBoard[w][s]);
                        }
                        System.out.println();
                    }

                    aftBoard = Move(aftBoard,GameFrame.controller.getCurrentPlayer(),i,e);

                    for(int j=0;j<8;j++){
                        for (int k = 0; k < 8; k++){
                            if(aftBoard[j][k] != tempBoard[j][k]){
                                counter--;
                            }
                        }
                    }

                }

                if(counter>min && counter != 64){
                    min = counter;
                    minRow = i;
                    minCol = e;
                }
            }
        }

        System.out.println(min);
        System.out.println(minCol);
        GameFrame.chessBoardPanel.changePanel(GameFrame.controller.getCurrentPlayer(),minRow,minCol);
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(),minRow,minCol);
        String s = minRow + " " + minCol + " " + ChessGridComponent.cheatModel + " " + p;
        GameFrame.step.add(s);
        GameFrame.stepCount++;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(211, 13, 108, 204));
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

    public static void setArrayToBoard(int[][] array){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(array[i][j] == -1){
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                }if(array[i][j] == 1){
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                }if(array[i][j] == 0){
                    chessGrids[i][j].setChessPiece(null);
                }
            }
        }
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

    public static ChessGridComponent[][] Move(ChessGridComponent[][] board, ChessPiece currentPlayer , int x, int y) {

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

    public static int[][] Move(int[][] board, ChessPiece currentPlayer , int x, int y) {
        int player=0;
        int[] xdirection = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] ydirection = {0, 1, 1, 1, 0, -1, -1, -1};

        if(currentPlayer==ChessPiece.BLACK){
            board[x][y]=-1;
            player = -1;
        }if(currentPlayer==ChessPiece.WHITE){
            board[x][y]=1;
            player=1;
        }else{
            return board;
        }

        for (int k = 0; k < 8; k++) {
            int tempx = x + xdirection[k];
            int tempy = y + ydirection[k];
            if ((tempx < 0 || tempx > 7) || (tempy < 0 || tempy > 7)) {
                continue;
            }
            if (board[tempx][tempy] == 0) {
                continue;
            }
            if (board[tempx][tempy] != player) {
                while (tempx >= 0 && tempy >= 0 && tempx <= 7 && tempy <= 7 && board[tempx][tempy] != 0 && board[tempx][tempy] != player) {

                    tempx += xdirection[k];
                    tempy += ydirection[k];
                }
                if (tempx < 0 || tempy > 7 || tempy < 0 || tempx > 7) {
                    continue;
                }
                if (board[tempx][tempy] == player) {
                    tempx = x + xdirection[k];
                    tempy = y + ydirection[k];
                    while (tempx >= 0 && tempy >= 0 && tempx <= 7 && tempy <= 7 && board[tempx][tempy] != 0 && board[tempx][tempy] != player) {
                        board[tempx][tempy]=player;
                        tempx += xdirection[k];
                        tempy += ydirection[k];
                    }
                }
            }
        }
        return board;
    }
}
