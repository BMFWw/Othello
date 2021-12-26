package controller;

import model.ChessPiece;
import view.ChessBoardPanel;
import view.GameFrame;
import view.StatusPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        List<String> fileData = new ArrayList<>();
        StringBuilder fileString = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            for(int e=0;e<fileData.size();e++){
                fileString.append(fileData.get(e));
            }
            Scanner input = new Scanner(new File(fileName));
            for(int i=0;i<8;i++){
                for(int e=0;e<8;e++){
                    fileInt[i][e] = input.nextInt();
                }
            }
            int k = input.nextInt();
            if(k==1){
                GameFrame.controller.setCurrentPlayer(ChessPiece.WHITE);
            }else if(k==-1){
                GameFrame.controller.setCurrentPlayer(ChessPiece.BLACK);
            }
            for(int i=0;i<8;i++){
                for(int e=0;e<8;e++){
                    System.out.println(fileInt[i][e]);
                }
            }
            fileData.forEach(System.out::println);
            GameFrame.chessBoardPanel.clearChessPieces();
            GameFrame.chessBoardPanel.setArrayToBoard(fileInt);
        } catch (IOException e) {
            e.printStackTrace();
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