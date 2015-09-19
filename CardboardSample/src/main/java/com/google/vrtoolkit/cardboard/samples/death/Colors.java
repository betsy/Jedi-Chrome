package main.java.com.google.vrtoolkit.cardboard.samples.death;

/**
 * Created by kara on 9/19/15.
 */
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Colors {
    public int height, width;
    public BufferedImage image;
    public int screenW = 960, screenH = 1080;

    public Colors(BufferedImage image)
    {
        this.image = image;
        this.height = image.getHeight();
        this.width = image.getWidth();
    }

    /*private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }*/

    public int[][] initialize(int[] pixels)
    {
        final int[][] result = new int[this.height][this.width];
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength){
            if(row > screenW*2/5 && row < screenW*3/5 && col > screenH*2/5 && col < screenH*3/5)
            {
                result[row][col] = pixels[pixel];
            }
            else
            {
                result[row][col] = BWPix(pixels[pixel]);
            }
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return result;
    }


    public int [][] ProcessImage (String orig, int[] pixels) {
        final int[][] result = new int[this.height][this.width];
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength){
            if((row < 432 || row > 648) && (col < 384 || col > 576))
            {
                String color = containsColor(pixels[pixel]);
                if (color.equals(orig))
                {
                    result[row][col] = pixels[pixel];
                }
                else
                {
                    result[row][col] = BWPix(pixels[pixel]);
                }
            }
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return result;
    }

    public String findOrig(int[] pixels)
    {
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        int redC = 0, greenC = 0, blueC = 0, yellowC = 0, cyanC = 0, magentaC = 0, max = 0;
        String color = "red";
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength){
            if((row > 432 && row < 648) && (col > 384 && col < 576))
            {
                color = containsColor(pixels[pixel]);
                //System.out.println(color);
                if(color.equals("red")){redC++;}
                if(color.equals("green")){greenC++;}
                if(color.equals("blue")){blueC++;}
                if(color.equals("yellow")){yellowC++;}
                if(color.equals("cyanC")){cyanC++;}
                if(color.equals("magenta")){magentaC++;}
                else{};
            }
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }
        hm.put("red", redC);
        hm.put("green", greenC);
        hm.put("blue", blueC);
        hm.put("yellow", yellowC);
        hm.put("cyan", cyanC);
        hm.put("magenta", magentaC);

        for (String k : hm.keySet()) {
            if(hm.get(k)> max)
            {
                color = k;
                max = hm.get(k);
            }
            //System.out.println(color);
        }

        return color;
    }

    private int BWPix (int pixel)
    {
        int r = (pixel >> 16) & 0xFF;
        int g = (pixel >> 8) & 0xFF;
        int b = (pixel & 0xFF);

        int grayLevel = (r + g + b) / 3;
        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
        return gray;
        //return (int) (0.2989 * (((int) pixel & 0xff) >> 16) + 0.5870 * (((int) pixel & 0xff) >> 8) + 0.114 * ((int) pixel & 0xff));
    }

    private boolean containsRed(int pixel)
    {
        if((int) (pixel>>16 & 0x0ff) >=100 && (int) (int) (pixel>>16 & 0x0ff) <= 255)
        {
            return true;
        }
        return false;
    }

    private boolean containsGreen(int pixel)
    {
        if((int) (pixel>>8 & 0x0ff) >=100 && (int) (int) (pixel>>8 & 0x0ff) <= 255)
        {
            return true;
        }
        return false;
    }

    private boolean containsBlue(int pixel)
    {
        if((int) (pixel & 0x0ff) >=100 && (int) (int) (pixel & 0x0ff) <= 255)
        {
            return true;
        }
        return false;
    }

    private String containsColor(int pixel)
    {
        boolean red = containsRed(pixel), green = containsGreen(pixel), blue = containsBlue(pixel);

        if(red && green && blue)
        {
            return "null";
        }
        else if (red == false && green == false && blue == false)
        {
            return "null";
        }
        else if(red && green)
        {
            return "yellow";
        }
        else if (red && blue)
        {
            return "magenta";
        }
        else if (blue && green)
        {
            return "cyan";
        }
        else if (red)
        {
            return "red";
        }
        else if (green)
        {
            return "green";
        }
        else
        {
            return "blue";
        }
    }
}
