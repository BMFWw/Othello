package view;

import components.ChessGridComponent;

import javax.swing.*;
import java.net.URL;

public class picturesInput extends JFrame {
    public static String winBackground = new String("CHESS/src/view/pictures/winGround.png");
    public static String tieBackground = new String("CHESS/src/view/pictures/tieGround.png");
    public static  String loseBackground = new String("CHESS/src/view/pictures/loseGround.png");
    public static String chessBoardBackground = new String("untitled/src/pictures/ChessBoardBackground.png");
    public static String startBackground = new String("CHESS/src/view/pictures/background.png");
    public static URL start = StartWindow.class.getResource("pictures/StartGame.png");
    public static  URL rule = StartWindow.class.getResource("pictures/rule.png");
    public static  URL whiteChessPiece = ChessGridComponent.class.getResource("pictures/whiteChessPiece.png");
}
