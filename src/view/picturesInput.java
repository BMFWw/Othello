package view;

import components.ChessGridComponent;

import javax.swing.*;
import java.net.URL;

public class picturesInput extends JFrame {
    public static String winBackground = new String("src/view/pictures/winGround.png");
    public static String tieBackground = new String("src/view/pictures/tieGround.png");
    public static  String loseBackground = new String("src/view/pictures/loseGround.png");
    public static String easyBackground = new String("src/view/pictures/easyBackground.png");
    public static String hardBackground = new String("src/view/pictures/hardBackground.png");
    public static String startBackground = new String("src/view/pictures/background.png");
    public static URL start = StartWindow.class.getResource("pictures/StartGame.png");
    public static  URL rule = StartWindow.class.getResource("pictures/rule.png");
    public static URL easyGame = StartWindow.class.getResource("pictures/easyGame.png");
    public static URL hardGame = StartWindow.class.getResource("pictures/hardGame.png");
    public static  URL whiteChessPiece = ChessGridComponent.class.getResource("pictures/whiteChessPiece.png");
    public static URL error = ErrorWindow.class.getResource("pictures/false.png");
}
