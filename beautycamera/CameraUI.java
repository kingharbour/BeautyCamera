package com.qhb;

import javax.swing.*;
import java.awt.*;

public class CameraUI{
    // 监听器对象 整个程序只创建一个
    // 这种只有一个对象的模式称为 单例模式 是Java设计中最简单的设计模式，值得去了解一下

    CameraListener cl = new CameraListener ();


    public  void initUI(){
        // 创建窗体 并采用边框布局
        JFrame jf = new JFrame();
        jf.setTitle ("美颜相机");
        jf.setSize (1000,800);
        jf.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        jf.setLayout (new BorderLayout ());

        // 创建绘图面板
        ShowPanel showPanel = new ShowPanel (cl.getImageList ());
//      showPanel.setImageList (cl.getImageList ());
        cl.setShowPanel (showPanel);
        showPanel.setBackground (Color.DARK_GRAY);

        // 创建按钮面板
        JPanel btnPanel = new JPanel ();
        btnPanel.setBackground (Color.LIGHT_GRAY);
        btnPanel.setPreferredSize (new Dimension (200,0));
        // 添加按钮，需要添加的功能按钮很多，封装initBtns方法再调用
        this.initBtns (btnPanel);

        // 面板添加到窗体 图像显示面板添加到中心区域，按钮工具面板添加到西区域
        jf.add (showPanel,BorderLayout.CENTER);
        jf.add (btnPanel,BorderLayout.WEST);

        // 菜单添加 创建菜单栏、菜单、菜单项
        JMenuBar jMenuBar = new JMenuBar ();
        JMenu jMenu = new JMenu ("File");
        JMenu jMenu_help = new JMenu ("Help");
        JMenuItem menuItem_open=new JMenuItem ("打开");
        JMenuItem menuItem_save = new JMenuItem ("保存");
        menuItem_open.setActionCommand("fileAction");
        menuItem_save.setActionCommand("fileAction");
        menuItem_open.addActionListener (cl);
        menuItem_save.addActionListener (cl);
        jMenu.add (menuItem_open);
        jMenu.add (menuItem_save);
        jMenuBar.add (jMenu);
        jMenuBar.add (jMenu_help);
        jf.setJMenuBar (jMenuBar);


        jf.setVisible (true);

        // 先从绘图面板上获取Graphics
        Graphics graphics = showPanel.getGraphics();
        // 将面板的graphics对象引用地址传递给监听器中g
        cl.setGraphics(graphics);
    }


    //编写方法 添加按钮
    public void initBtns(JPanel btnpanel){
        String[] strs = {"相机","原图","马赛克","灰度","油画","二值化","锐化","融合","放大","缩小"};
        for(int i = 0; i < strs.length; i++){
            JButton btn = new JButton (strs[i]);
//          btn.setBackground (Color.white);感觉默认颜色更好看
            btn.setPreferredSize (new Dimension(90,35));
            btn.setActionCommand("drawAction");
            btn.addActionListener (cl);
            btnpanel.add (btn);
        }

    }

    public static void main(String[] args){
        new CameraUI ().initUI ();
    }
}
