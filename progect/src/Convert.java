package ru.netology.graphics;

import ru.netology.graphics.image.BadImageSizeException;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Convert implements TextGraphicsConverter {
    private double maxRatio;
    private TextColorSchema schema;
    private int width;
    private int height;
    private int ratio;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        double scale = height > img.getHeight() ? height / img.getHeight() : img.getHeight() / height;
        int newHeight =  (int)(scale * img.getHeight());
        int newWidth =  (int) (scale * img.getWidth());
        if (ratio > maxRatio) {
            BadImageSizeException badImageSizeException = new BadImageSizeException(ratio, maxRatio);
            throw badImageSizeException;
        }
        if(img.getWidth() > width) {
            int ratio1 = img.getWidth() / width;
            newWidth = img.getWidth() / ratio1;
            newHeight = img.getHeight() / ratio1;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        String[][] colors = new String[bwImg.getWidth()][bwImg.getHeight()];
        TextColorSchema schema = new ColorSchema(new char[]{'%', '#', '@', '$', '*', '+', '-', '.'});

        for (int h = 0; h < bwImg.getHeight(); h++) {
            for (int w = 0; w < bwImg.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                colors[w][h] = String.valueOf(c);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int w = 0; w < bwImg.getWidth(); w++) {
            for (int h = 0; h < bwImg.getHeight(); h++) {
                String str = colors[w][h] + colors[w][h];
                stringBuilder.append(str);
            }
            stringBuilder.append("\n");
        }
        return String.valueOf(stringBuilder);
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double ratio) {
        this.maxRatio = ratio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}