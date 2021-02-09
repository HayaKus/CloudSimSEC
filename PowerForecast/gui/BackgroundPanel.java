package gui;

import javax.swing.*;
import java.awt.*;

/**
 * 给upperPanel用的特殊JPanel
 * Author：HayaKus
 */
public class BackgroundPanel extends JPanel {

    private static final long serialVersionUID = -6352788025440244338L;

    private Image image = null;

    // 构造器
    public BackgroundPanel(Image image) {
        this.image = image;
    }

    // 固定背景图片，允许这个JPanel可以在图片上添加其他组件
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 100, 5, 550, 50, this);
    }

}
