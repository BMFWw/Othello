package controller;

import model.ChessPiece;
import view.ChessBoardPanel;
import view.ErrorWindow;
import view.GameFrame;
import view.StatusPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class GameController {


    private ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    public static int blackScore;
    public static int whiteScore;

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
    }

    public GameController() {

    }

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }


    public void countScore() {
        //todo: modify the countScore method
        blackScore = 0;
        whiteScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK) {
                    blackScore++;
                } else if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE) {
                    whiteScore++;
                }
            }
        }
    }

    public int countScoreWhite(){
        whiteScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                 if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE) {
                    whiteScore++;
                }
            }
        }
        return  whiteScore;
    }

    public int countScoreBlack(){
        blackScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamePanel.getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK) {
                    blackScore++;
                }
            }
        }
        return  blackScore;
    }


    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(ChessPiece currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }


    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void readFileData(String fileName) {
        //todo: read date from file
        int[][] fileInt = new int[8][8];
        int counter = 0;
        int counterW = 0;
        int counterB = 0;
        int counterN = 0;
        GameFrame.chessBoardPanel.clearChessPieces();
        GameFrame.chessBoardPanel.initialGame();
        GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            GameFrame.step = new ArrayList<>();
            Scanner input = new Scanner(new File(fileName));
            if(bufferedReader.readLine()==null){
                ErrorWindow.code = 106;
                ErrorWindow errorWindow = new ErrorWindow();
                return;
            }
            String line;
            while((line=bufferedReader.readLine()) != null){
                int stepCount = input.nextInt();
                if(stepCount == 0);
                for(int l=0;l<stepCount;l++){
                    int r = input.nextInt();
                    int c = input.nextInt();
                    int cheat = input.nextInt();
                    int p = input.nextInt();
                    ChessPiece currentPlayer = null;
                    if(p == 1){
                        currentPlayer = ChessPiece.WHITE;
                    }else if(p==-1){
                        currentPlayer = ChessPiece.BLACK;
                    }else{
                        ErrorWindow.code = 103;
                        ErrorWindow error = new ErrorWindow();
                        return;
                    }
                    if(GameFrame.chessBoardPanel.canPut(GameFrame.chessBoardPanel.getChessGrids(),p,r,c)){
                        String s = r + " " + c + p;
                        GameFrame.step.add(s);
                        GameFrame.chessBoardPanel.changePanel(currentPlayer,r,c);
                    }else if(GameFrame.chessBoardPanel.canPut(GameFrame.chessBoardPanel.getChessGrids(),p,r,c)==false && cheat == -1){
                        ErrorWindow.code = 105;
                        ErrorWindow error = new ErrorWindow();
                        return;
                    }else if(GameFrame.chessBoardPanel.canPut(GameFrame.chessBoardPanel.getChessGrids(),p,r,c)==false && cheat == 1){
                        String s = r + " " + c + p;
                        GameFrame.step.add(s);
                        GameFrame.chessBoardPanel.changePanel(currentPlayer,r,c);
                    }
                }
                for(int i=0;i<8;i++){
                    for(int e=0;e<8;e++){
                        int k = input.nextInt();
                        if(k != -1 && k != 0 && k != 1){
                            ErrorWindow.code = 102;
                            ErrorWindow error = new ErrorWindow();
                            return;
                        }
                        if(k == 1){
                            counterW++;
                        }else if(k == -1){
                            counterB++;
                        }else if(k == 0){
                            counterN++;
                        }
                        fileInt[i][e] = k;
                        counter++;
                    }
                }
                if(counterB == 0 || counterN == 0 || counterW == 0){
                    ErrorWindow.code = 102;
                    ErrorWindow error = new ErrorWindow();
                    return;
                }
                if(counter != 64){
                    ErrorWindow.code = 101;
                    ErrorWindow error = new ErrorWindow();
                    return;
                }
                int k = input.nextInt();
                if(k==1){
                    GameFrame.controller.setCurrentPlayer(ChessPiece.WHITE);
                }else if(k==-1){
                    GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e){
            if(counter != 64){
                ErrorWindow.code = 101;
                ErrorWindow error = new ErrorWindow();
                return;
            }
            ErrorWindow.code = 103;
            ErrorWindow error = new ErrorWindow();
            return;
        } catch (Exception e){
            ErrorWindow.code = 106;
            ErrorWindow error = new ErrorWindow();
            return;
        }
    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file
        try {
            int[][] content = gamePanel.setBoardToArray(gamePanel.getChessGrids());
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(GameFrame.stepCount + "");
            bufferedWriter.newLine();
            for(int i=0;i<GameFrame.step.size();i++){
                bufferedWriter.write(GameFrame.step.get(i));
                bufferedWriter.newLine();
            }
            for(int i=0;i<8;i++){
                for(int e=0;e<8;e++){
                    bufferedWriter.write(content[i][e]+" ");
                }
                bufferedWriter.newLine();
            }
            int k=0;
            if(GameFrame.controller.getCurrentPlayer()==ChessPiece.BLACK){
                k =-1;
            }else if(GameFrame.controller.getCurrentPlayer()==ChessPiece.WHITE){
                k = 1;
            }
            bufferedWriter.write( k +"");
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }
}