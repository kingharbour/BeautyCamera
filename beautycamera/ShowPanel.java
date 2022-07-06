package com.qhb;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ShowPanel extends JPanel{
    ArrayList<BufferedImage> imageList;
    public ShowPanel(ArrayList<BufferedImage> imageList){
        this.imageList=imageList;
    }
    public void setImageList(ArrayList<BufferedImage> imageList){
        this.imageList=imageList;
    }

    // 所有的可视化组件 都有这个方法
    @Override
    public void paint(Graphics g){
        super.paint (g);
        // 取出数组中的最后一张 绘制
        if(imageList.size ()==0)
            return;
        BufferedImage img = imageList.get (imageList.size () - 1);
        // 将处理之后的图片 绘制在这里
        if(img==null)
            return;
        g.drawImage (img, (int) (this.getWidth ()*0.1), (int) (this.getHeight ()*0.1),  null);
    }
}
