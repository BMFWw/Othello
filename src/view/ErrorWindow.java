package view;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JDialog {
    public static int code;
    public ErrorWindow(){
        String text = null;
        if(code == 101){
            text = "  101";//棋盘并非8*8
        }else if(code == 102){
            text = "  102";//棋盘内棋子并非包含黑方、白方、空白 3种。 少于3种，多于3种都有问题
        }else if(code == 103){
            text = "  103";//只有棋盘，没有下一步行棋的方的提示
        }else if(code == 104){
            text = "  104";//比如支持存储文件是txt，导入的是json
        }else if(code == 105){
            text = "  105";//判断先前步骤合法性，而且要包含步骤是cheating模式还是普通模式
        }else if(code == 106){
            text = "  106";//以上4种错误外其他的错误
        }

        JLabel jLabel = new JLabel(text);

        ImageIcon imageIcon = new ImageIcon(picturesInput.error);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        jLabel.setIcon(imageIcon);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("宋", Font.PLAIN, 23);//文字
        jLabel.setFont(font);
        jLabel.setForeground(new Color(255, 0, 0));

        Container container = getContentPane();
        container.add(jLabel);
        this.setSize(250,120);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
