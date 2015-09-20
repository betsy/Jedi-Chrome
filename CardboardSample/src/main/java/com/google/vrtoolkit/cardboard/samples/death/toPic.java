package main.java.com.google.vrtoolkit.cardboard.samples.death;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Created by kara on 9/19/15.
 */
public class toPic {
    public BufferedImage img;
    public int width, height;

    public toPic(BufferedImage img) {
        this.img = img;
        width = img.getWidth();
        height = img.getHeight();
    }

    public BufferedImage prepShow(BufferedImage image) {
        Colors toBeInit = new Colors(image);
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int[] result = new int[image.getHeight() * image.getWidth()*3];
        final int pixelLength = 3;
        for (int pixel = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += ((int) pixels[pixel] & 0xff); // blue
            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
            result[pixel] = argb;
        }

        // 1D array, to be initialized
        int[][] Initialized = toBeInit.initialize(result);
        // initialized into 2D array

        int[] final1D = new int[width * height];
        int index = 0;
        for (int n = 0; n < Initialized.length; n++) {
            for (int i = 0; i < Initialized[0].length; i++) {
                final1D[index++] = Initialized[n][i];
            }
        }

        int[][] Processed = toBeInit.ProcessImage(toBeInit.findOrig(result), result);
        int[] finalProcess = new int[width * height];
        int index1 = 0;
        for (int n = 0; n < Processed.length; n++) {
            for (int i = 0; i < Processed[0].length; i++) {
                finalProcess[index1++] = Processed[n][i];
            }
        }
        // 1D, to be converted back to RGB

        //BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage processedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //newImage.setRGB(0, 0, width, height, final1D, 0, width);
        processedImg.setRGB(0, 0, width, height, finalProcess, 0, width);
        File outputfile = new File("initialized.jpg");
        //try {
            //ImageIO.write(newImage, "jpg", outputfile);
            //ShowImage finalPic = new ShowImage(newImage);
            //ShowImage finalPic = new ShowImage(newImage);
        //} catch (IOException e) {
        //}
        return processedImg;
        //return newImage;

    }
}

