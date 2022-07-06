package com.qhb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FilterTools {
    // 获取导入图片像素数组
    public int[][] getImagePixels(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            int[][] imgArr = new int[img.getWidth()][img.getHeight()];
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    // 取出对应位置上的像素值
                    int pixel = img.getRGB(i, j);
                    // pixel 拆为 ARGB 四个 字节0-255
                    imgArr[i][j] = pixel;
                }
            }
            return imgArr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //原图
    public BufferedImage drawImage(int [][] imgArr) {
        BufferedImage img = new BufferedImage(imgArr.length, imgArr[0].length, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < imgArr.length; i++) {
            for (int j = 0; j < imgArr[i].length; j++) {
                int pixels = imgArr[i][j];
                // 计算过程中只设置像素 进缓存图
                img.setRGB(i, j, pixels);
            }
        }
        return img;
    }
    //马赛克
    public BufferedImage drawMosaicImage(BufferedImage bufImg) {
        BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = img.getGraphics();// 绘制的内容就存在 缓存图中
        for (int i = 0; i < bufImg.getWidth(); i += 10) {
            for (int j = 0; j < bufImg.getHeight(); j += 10) {
                int pix = bufImg.getRGB(i, j);
                // 这里的绘制 还是内存操作
                buffg.setColor(new Color(pix));
                buffg.fillOval(i, j, 10, 10);
            }
        }
        return img;
    }
    //二值化
    public BufferedImage drawBlackWhiteImage(BufferedImage bufImg) {
        BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = img.getGraphics();
        for (int i = 0; i < bufImg.getWidth(); i++) {
            for (int j = 0; j < bufImg.getHeight(); j++) {
                int pix = bufImg.getRGB(i, j);
                int red = (pix >> 16) & 0xFF;
                int green = (pix >> 8) & 0xFF;
                int blue = (pix) & 0xFF;
                int gray = (red + blue + green) / 3;
                if (gray < 100) {
                    buffg.setColor(Color.BLACK);
                } else {
                    buffg.setColor(Color.WHITE);
                }
                buffg.fillRect(i, j, 1, 1);
            }
        }
        return img;

    }
    //灰度
    public BufferedImage drawGrayImage(BufferedImage bufImg) {
        BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < bufImg.getWidth(); i++) {
            for (int j = 0; j < bufImg.getHeight(); j++) {
                int pix = bufImg.getRGB(i, j);
                int red = (pix >> 16) & 0xFF;
                int green = (pix >> 8) & 0xFF;
                int blue = (pix) & 0xFF;
                int gray = (red + green + blue) / 3;
                // 灰度 r = g = b
                int value = ((255 & 0xFF) << 24) |
                        ((gray & 0xFF) << 16) |
                        ((gray & 0xFF) << 8) |
                        ((gray & 0xFF) << 0);
                img.setRGB(i, j, value);
            }
        }
        return img;
    }
    //缩小
    public BufferedImage drawMinImage(BufferedImage bufImg) {
        BufferedImage img = new BufferedImage(bufImg.getWidth()/2, bufImg.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < bufImg.getWidth(); i++) {
            for (int j = 0; j < bufImg.getHeight(); j++) {
                int pix = bufImg.getRGB(i,j);
                // 坐标换算
                img.setRGB(i / 2, j / 2, pix);
            }
        }
        return img;

    }
//    //锐化
//    public BufferedImage drawSharpenImage(BufferedImage bufImg) {
//        BufferedImage img = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        for (int i = 0; i < bufImg.getWidth(); i++) {
//            for (int j = 0; j < bufImg.getHeight(); j++) {
//                for(int m = 0;m < bufImg.getHeight();m++){
//                    for(int n = 0;n< bufImg.getHeight();n++){
//                    int pix = bufImg.getRGB(i, j);
//                    int red = (pix >> 16) & 0xFF;
//                    int green = (pix >> 8) & 0xFF;
//                    int blue = (pix) & 0xFF;
//                    //锐化卷积核
//                    int[][] sharpening = {{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
//                img.setRGB(i, j, value);
//            }
//        }
//        return img;
//    }


}
