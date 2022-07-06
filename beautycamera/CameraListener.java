package com.qhb;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CameraListener implements ActionListener{
    private Graphics g;//声明g对象用来接收showPanel中graphics对象
    private FilterTools filterTools = new FilterTools ();
    private int[][] oriImgArr;//原图数组
    private int[][] scrImgArr;//当前屏幕显示的图片数组
    private ShowPanel showPanel;
    boolean flag = true;
    public void setShowPanel(ShowPanel showPanel){
        this.showPanel =  showPanel;
    }

    //将showPanel中graphics传递过来
    public void setGraphics(Graphics g){
        this.g = g;
    }


    private ArrayList<BufferedImage> list = new ArrayList<> ();
    public ArrayList<BufferedImage> getImageList(){
        return list;
    }

    public void fileAction(String fileMethod){
        switch (fileMethod){
            case "打开":
            {
                JFileChooser chooser = new JFileChooser();
                // 文件过滤器
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg","png","jpeg","gif");
                chooser.setFileFilter(filter);

                // 显示打开文件对话框
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file  = chooser.getSelectedFile ();
                    System.out.println ("Path:"+file.getPath ());
                    oriImgArr = filterTools.getImagePixels(file);
                    scrImgArr = filterTools.getImagePixels(file);
                    list.add (filterTools.drawImage (oriImgArr));
                    showPanel.repaint ();
                    System.out.println("You chose to open this file: " +
                            chooser.getSelectedFile().getName());
                }

            }
            break;
            case "保存":{

                JFileChooser chooser = new JFileChooser();

                // 文件过滤器
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg","png","jpeg","gif");
                chooser.setFileFilter(filter);

                // 显示打开文件对话框
                int returnVal = chooser.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile ();
                    // File 实际上一个文件的管理对象，不能操作文件的内容
                    // file 可以获取文件的名字 路径 等
                    // 将文件作为目标地址输入
                    // 图片对象
                    BufferedImage image = list.get (list.size () - 1);
                    try {
                        ImageIO.write (image,"png",file);
                    } catch (IOException ex) {
                        throw new RuntimeException (ex);
                    }
                }
            }
            break;
        }

    }
    public void drawAction(String drawMethod){
        switch (drawMethod) {
            case "相机":
                Webcam webcam = Webcam.getDefault ();
                webcam.setViewSize (WebcamResolution.VGA.getSize ());
                webcam.open ();
                while(flag){// 线程
                    BufferedImage image = webcam.getImage ();
                    g.drawImage (image, 0, 0, null);
                }
                break;
            case "原图":
                BufferedImage img = filterTools.drawImage (oriImgArr);
                list.add (img);// 存入List中 没有显示
                break;
            case "马赛克":
                //所有方法都是在前一张图的基础上绘制
                list.add(filterTools.drawMosaicImage (list.get(list.size()-1)));
                break;
            case "灰度":
                list.add(filterTools.drawGrayImage (list.get(list.size()-1)));
                break;
            case "撤回":
                break;
            case "缩小":
                list.add(filterTools.drawMinImage(list.get(list.size()-1)));
                break;
            case "二值化":
                list.add(filterTools.drawBlackWhiteImage (list.get(list.size()-1)));
                break;
            case "锐化"://图像锐化是将图像中的R,G,B的值分别从原图像中提出，然后将分别将这三个R,G,B的值分别与卷积核进行卷积，
                       // 最终再将最后的三个卷积的结果合成为一个像素值，从而实现图像的锐化效果。
                list.add(filterTools.drawSharpenImage (list.get(list.size()-1)));
                break;
            default:
                break;
        }
        // 主动调用paint方法，绘制图片
        showPanel.repaint ();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand ();
        if(action.equals("fileAction")) {
            JMenuItem source = (JMenuItem) e.getSource();
            String fileMethod = source.getText();
            this.fileAction(fileMethod);
        } else if (action.equals("drawAction")) {
            JButton source =(JButton)e.getSource();
            String drawMethod = source.getText();
            this.drawAction(drawMethod);
        }
    }


}
