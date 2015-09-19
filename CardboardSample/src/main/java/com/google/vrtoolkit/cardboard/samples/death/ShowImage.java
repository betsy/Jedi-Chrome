package main.java.com.google.vrtoolkit.cardboard.samples.death;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class ShowImage {

    public static void main(String[] args) throws IOException {
        String directory = "/Users/kara/Documents/lexhack/Jedi-Chrome/CardboardSample/src/main/java/com/google/vrtoolkit/cardboard/samples/death/strawberry.jpg";
        BufferedImage img = ImageIO.read(new File(directory));
       // ShowImage abc = new ShowImage(img);
        toPic initialized = new toPic(img);
        BufferedImage finalImg = initialized.prepShow(img);
        ShowImage finalPrint = new ShowImage(finalImg);
    }

    public ShowImage(BufferedImage img) throws IOException {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(), img.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
