//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gui;

import structure.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class GUI {

    public static String titlename = "边缘计算服务器集群能耗和评估模拟平台";
    public static int frameWidth = 1000;
    public static int frameHeight = 600;
    public static int lowerPanelHeight = 70;
    public static int rightPanelWidth = 300;
    public static int upperPanelHeight = 50;

    public static JFrame frame = new JFrame(titlename);
    public static JTextArea logTextArea = new JTextArea();
    public static JScrollPane logScroll = new JScrollPane(logTextArea);

    // p1 数据中心
    public static String DATACENTER_NAME;
    public static String IT_ENERGYMODEL;
    public static double SCHEDULING_INTERVAL;
    public static String ITENVIRONMENT_ENERGYMODEL;
    public static double FIREENERGY;
    public static double INFRASTRUCTUREENERGY;

    // p2 物理主机
    public static int[] VM_HOST_NUMBERS;
    public static int[] HOST_MIPS;
    public static int[] HOST_MIN_POWER;
    public static int[] HOST_MAX_POWER;
    public static int[] HOST_RAM;
    public static long HOST_BW;
    public static long HOST_STORAGE;

    // p3 虚拟机
    public static int[] VM_MIPS;
    public static int[] VM_RAM;
    public static long VM_BW;
    public static long VM_SIZE;

    // p4 云任务
    public static long[] CLOUDLET_LENGTH;
    public static int[] CLOUDLET_FILESIZE;
    public static int[] CLOUDLET_OUTPUTSIZE;

    // p5 用户
    public static int[][] USR_TYPE_OWNED;
    public static int[][] USR_VMNUM_OWNED;
    public static int[][] USR_CLOUDLET_TYPE;

    // p6 QoS评估
    public static int BEST_CPU;
    public static int WORST_CPU;
    public static int WEIGHT_CPU;
    public static int BEST_HOSTLOAD;
    public static int WORST_HOSTLOAD;
    public static int WEIGHT_HOSTLOAD;
    public static int BEST_BANDWIDTH;
    public static int WORST_BANDWIDTH;
    public static int WEIGHT_BANDWIDTH;
    public static int BEST_RAM;
    public static int WORST_RAM;
    public static int WEIGHT_RAM;

    public static ProgressBarThread t;


    public static JProgressBar progressBar = new JProgressBar();


    public static void showGUI() {

        setLookAndFeel();

        initDefaultData();
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(200, 200);

        frame = setIcon(frame);

        frame.setLayout(new BorderLayout());
        JPanel upperPanel = new JPanel();
        upperPanel.setPreferredSize(new Dimension(frameWidth, upperPanelHeight));
        JTabbedPane middlepanel = getTabbedPane();

        upperPanel.setBorder(BorderFactory.createEtchedBorder());
        JButton b1 = new JButton("训练模型");
        JButton b2 = new JButton("保存结果");
        JButton b3 = new JButton("帮助");
        upperPanel.add(b1);
        upperPanel.add(b2);
        upperPanel.add(b3);
        //b1
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
                frame = setIcon(frame);
                frame.setTitle("训练模型");
                frame.setBounds(500, 200, 400, 350);
                JLabel jl = new JLabel();// 注意类名别写错了。
                jl.setPreferredSize(new Dimension(300,300));
                frame.getContentPane().add(jl);
                jl.setText("<html><body>本软件仅支持Matlab导出的模型，方法如下：<br>"
                        +"首先，将正式能耗数据作为训练数据导入Matlab。<br>"
                        +"格式为：第一列为主机的CPU使用率，第二列为主机的能耗。<br>"
                        +"其次，训练模型。<br>"
                        +"在Matlab命令框输入一下指令：<br>"
                        +"x = data(:,1)';<br>"
                        +"tt = data(:,2)';<br>"
                        +"net = newrbe(x,tt);<br>"
                        +"save rbf net;<br>"
                        +"最后，保存模型。<br>"
                        +"将Matlab根目录文件下的模型rbf.mat移动到将要保存的位置。<br>"
                        +"</body></html>");
                jl.setVerticalAlignment(JLabel.CENTER);
                jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口(它自己的子层次
                frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
                frame.setVisible(true);
            }
        });

        //b2
        UIManager.put("FileChooser.openButtonText", "确定");
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(GUI.frame);
                File file = fc.getSelectedFile();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String targetFilePath = file.getAbsolutePath();
                    String sourceDir = "output";
                    try {
                        copyDir(sourceDir, targetFilePath + file.separator + sourceDir);
                        JOptionPane.showConfirmDialog(null, "保存完成", "提示", JOptionPane.DEFAULT_OPTION);
                    } catch (Exception e1) {
                        JOptionPane.showConfirmDialog(null, "出错", "提示", JOptionPane.DEFAULT_OPTION);
                    }
                }
            }
        });
        //b3
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
                frame = setIcon(frame);
                frame.setTitle("帮助");
                frame.setBounds(600, 250, 250, 250);
                JLabel jl = new JLabel();// 注意类名别写错了。
                jl.setPreferredSize(new Dimension(250,250));
                frame.getContentPane().add(jl);
                jl.setText("<html><body>如果您有任何的问题或者意见，<br>请通过以下邮箱与我联系：<br><br>林皓伟 296329404@qq.com</body></html>");
                jl.setVerticalAlignment(JLabel.CENTER);
                jl.setHorizontalAlignment(JLabel.CENTER);// 注意方法名别写错了。
                // 参数 APPLICATION_MODAL：阻塞同一 Java 应用程序中的所有顶层窗口(它自己的子层次
                frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
                frame.setVisible(true);
            }
        });


        JPanel j = new JPanel();
        j.setPreferredSize(new Dimension(10, 30));
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(j, BorderLayout.WEST);
        frame.add(middlepanel, BorderLayout.CENTER);
        frame.add(getRightPanel(), BorderLayout.EAST);
        frame.add(getLowerPanel(), BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);

        SubJPanelClass.initAndResetSubJPanelData();


    }

    public static void setRightTextAreaClear() {
        logTextArea.setText("");
    }

    private static JTabbedPane getTabbedPane() {
        JTabbedPane tp = new JTabbedPane();

        tp.add(SubJPanelClass.getDataCenterPanel());
        tp.add(SubJPanelClass.getHostPanel());
        tp.add(SubJPanelClass.getVMPanel());
        tp.add(SubJPanelClass.getCloudTaskPanel());
        tp.add(SubJPanelClass.getUserPanel());
        tp.add(SubJPanelClass.getQoSPanel());

        tp.setTitleAt(0, "数据中心");
        tp.setTitleAt(1, "物理主机");
        tp.setTitleAt(2, "虚拟机");
        tp.setTitleAt(3, "云任务");
        tp.setTitleAt(4, "用户");
        tp.setTitleAt(5, "QoS评估");

        return tp;
    }


    private static JPanel getLowerPanel() {
        JPanel wapper1 = new JPanel();
        wapper1.setLayout(new BorderLayout());
        wapper1.setBackground(Color.black);
        wapper1.setPreferredSize(new Dimension(frameWidth, lowerPanelHeight));

        JPanel blankUp = new JPanel();
        JPanel blankDown = new JPanel();
        JPanel blankLeft = new JPanel();
        JPanel blankRight = new JPanel();
        JPanel center = new JPanel();

        wapper1.add(blankUp, BorderLayout.NORTH);
        wapper1.add(blankDown, BorderLayout.SOUTH);
        wapper1.add(blankRight, BorderLayout.EAST);
        wapper1.add(blankLeft, BorderLayout.WEST);
        wapper1.add(center, BorderLayout.CENTER);


        center.setLayout(new BorderLayout());

//        blankUp.setBackground(Color.green);
//        blankDown.setBackground(Color.red);
//        center.setBackground(Color.blue);

        JPanel leftBtn = new JPanel();
        leftBtn.setLayout(new BorderLayout());
        JButton clearBtn = new JButton("清空");
        JButton resetBtn = new JButton("重置");
        JPanel blankBtn = new JPanel();
        blankBtn.setPreferredSize(new Dimension(10, 30));
        leftBtn.add(clearBtn, BorderLayout.WEST);
        leftBtn.add(resetBtn, BorderLayout.EAST);
        leftBtn.add(blankBtn, BorderLayout.CENTER);

        center.add(leftBtn, BorderLayout.WEST);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(600, 30));
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        JPanel outsideProgressBar = new JPanel();

        outsideProgressBar.add(progressBar);
        center.add(outsideProgressBar, BorderLayout.CENTER);
        JButton resultBtn = new JButton("生成结果");
        center.add(resultBtn, BorderLayout.EAST);

        clearBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SubJPanelClass.inputSetClear();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        resetBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                SubJPanelClass.initAndResetSubJPanelData();
            }
        });
        resultBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                Thread t = new ResultBtnThread();
                t.start();
            }
        });


        return wapper1;
    }

    private static JPanel getLowerPanel1() {
        JPanel wapper1 = new JPanel();

        wapper1.setPreferredSize(new Dimension(frameWidth, 50));
        JPanel blankUp = new JPanel();
        JPanel blankDown = new JPanel();
        blankUp.setPreferredSize(new Dimension(frameWidth, 10));
        blankDown.setMinimumSize(new Dimension(frameWidth, 10));
        blankUp.setBackground(Color.black);
        blankDown.setBackground(Color.blue);

        wapper1.add(blankUp, BorderLayout.NORTH);
        wapper1.add(blankDown, BorderLayout.SOUTH);


        JPanel panel = new JPanel();
        JPanel inner = new JPanel();
        JPanel outsideProgressBar = new JPanel();


        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(frameWidth, 30));
        inner.setLayout(new BorderLayout());
        outsideProgressBar.setPreferredSize(new Dimension(600, 30));
        outsideProgressBar.setBackground(Color.red);


        JButton resetBtn = new JButton("重置");
        JButton clearBtn = new JButton("清空");
        JButton resultBtn = new JButton("生成结果");


        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(50);
        progressBar.setStringPainted(true);

        inner.add(resetBtn, BorderLayout.WEST);
        inner.add(clearBtn, BorderLayout.EAST);
        inner.setPreferredSize(new Dimension(100, 20));
        panel.add(inner, BorderLayout.WEST);
        panel.add(resultBtn, BorderLayout.EAST);

        outsideProgressBar.add(progressBar);
        panel.add(outsideProgressBar, BorderLayout.CENTER);

        return wapper1;
    }

    private static JPanel getRightPanel() {
        JPanel wapper = new JPanel();
        wapper.setLayout(new BorderLayout());
        wapper.setBackground(Color.black);
        wapper.setPreferredSize(new Dimension(rightPanelWidth, frameHeight - lowerPanelHeight - upperPanelHeight));

        JPanel blankUp = new JPanel();
//        JPanel blankDown = new JPanel();
        JPanel blankLeft = new JPanel();
        JPanel blankRight = new JPanel();

        blankUp.setPreferredSize(new Dimension(300, 20));
//        blankUp.setBackground(Color.red);
//        blankDown.setPreferredSize(new Dimension(300, 20));
//        blankDown.setBackground(Color.red);
        blankLeft.setPreferredSize(new Dimension(10, frameHeight - lowerPanelHeight - upperPanelHeight));
//        blankLeft.setBackground(Color.red);
        blankRight.setPreferredSize(new Dimension(10, frameHeight - lowerPanelHeight - upperPanelHeight));
//        blankRight.setBackground(Color.red);

        wapper.add(blankUp, BorderLayout.NORTH);
        wapper.add(blankLeft, BorderLayout.EAST);
        wapper.add(blankRight, BorderLayout.WEST);
        wapper.add(logScroll, BorderLayout.CENTER);


        return wapper;
    }

    private static void initDefaultData() {
        // p1
        DATACENTER_NAME = Constant.DATACENTER_NAME;
        IT_ENERGYMODEL = Constant.IT_ENERGYMODEL;
        SCHEDULING_INTERVAL = Constant.SCHEDULING_INTERVAL;
        ITENVIRONMENT_ENERGYMODEL = Constant.ITENVIRONMENT_ENERGYMODEL;
        FIREENERGY = Constant.FIREENERGY;
        INFRASTRUCTUREENERGY = Constant.INFRASTRUCTUREENERGY;

        // p2
        VM_HOST_NUMBERS = Constant.VM_HOST_NUMBERS;
        HOST_MIPS = Constant.HOST_MIPS;
        HOST_MIN_POWER = Constant.HOST_MIN_POWER;
        HOST_MAX_POWER = Constant.HOST_MAX_POWER;
        HOST_RAM = Constant.HOST_RAM;
        HOST_BW = Constant.HOST_BW;
        HOST_STORAGE = Constant.HOST_STORAGE;

        // p3
        VM_MIPS = Constant.VM_MIPS;
        VM_RAM = Constant.VM_RAM;
        VM_BW = Constant.VM_BW;
        VM_SIZE = Constant.VM_SIZE;

        // p4
        CLOUDLET_LENGTH = Constant.CLOUDLET_LENGTH;
        CLOUDLET_FILESIZE = Constant.CLOUDLET_FILESIZE;
        CLOUDLET_OUTPUTSIZE = Constant.CLOUDLET_OUTPUTSIZE;

        // p5
        USR_TYPE_OWNED = Constant.USR_TYPE_OWNED;
        USR_VMNUM_OWNED = Constant.USR_VMNUM_OWNED;
        USR_CLOUDLET_TYPE = Constant.USR_CLOUDLET_TYPE;

        // p6
        BEST_CPU = Constant.BEST_CPU;
        WORST_CPU = Constant.WORST_CPU;
        WEIGHT_CPU = Constant.WEIGHT_CPU;
        BEST_HOSTLOAD = Constant.BEST_HOSTLOAD;
        WORST_HOSTLOAD = Constant.WORST_HOSTLOAD;
        WEIGHT_HOSTLOAD = Constant.WEIGHT_HOSTLOAD;
        BEST_BANDWIDTH = Constant.BEST_BANDWIDTH;
        WORST_BANDWIDTH = Constant.WORST_BANDWIDTH;
        WEIGHT_BANDWIDTH = Constant.WEIGHT_BANDWIDTH;
        BEST_RAM = Constant.BEST_RAM;
        WORST_RAM = Constant.WORST_RAM;
        WEIGHT_RAM = Constant.WEIGHT_RAM;

        for(int tempNumber:VM_HOST_NUMBERS){
            Constant.NUMBER_HOST += tempNumber;
        }
    }


    public static void setProgressBar(String str) {

        long speed0 = 100;
        long speed1 = 1000;

        switch(str){
            case "RR":{
                t = new ProgressBarThread(0, 10, speed0);
                t.start();
                break;
            }
            case "DVFS":{
                t.interrupt();
                t = new ProgressBarThread(11, 20, speed0);
                t.start();
                break;
            }
            case "ST":{
                t.interrupt();
                t = new ProgressBarThread(21, 30, speed0);
                t.start();
                break;
            }
            case "NPA":{
                t.interrupt();
                t = new ProgressBarThread(31, 40, speed0);
                t.start();
                break;
            }
            case "exportHostStats":{
                t.interrupt();
                t = new ProgressBarThread(41, 79, speed1);
                t.start();
                break;
            }
            case "RRmakeHostLine":{
                t.interrupt();
                t = new ProgressBarThread(80, 84, speed0);
                t.start();
                break;
            }
            case "DVFSmakeHostLine":{
                t.interrupt();
                t = new ProgressBarThread(85, 89, speed0);
                t.start();
                break;
            }
            case "STmakeHostLine":{
                t.interrupt();
                t = new ProgressBarThread(90, 94, speed0);
                t.start();
                break;
            }
            case "makeHostCombinedLine":{
                t.interrupt();
                t = new ProgressBarThread(95, 99, speed0);
                t.start();
                break;
            }
            case "Success":{
                t.interrupt();
                t = new ProgressBarThread(99, 100, speed0);
                t.start();
                break;
            }
        }
    }

    public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();


        //如果已存在，删除旧文件
        File dir = new File(newPath);
        if (dir.isDirectory()) {
            if (dir.exists() && dir.isFile()) {
                dir.delete();
            }
        } else if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }


        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

            if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);
        ;

        byte[] buffer = new byte[2097152];
        int readByte = 0;
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }

    private static void setLookAndFeel() {
        try {
            // 1.黑色风格
            javax.swing.UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
//            // 2.Windows风格
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            // 3.Windows经典风格
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
//            // 4.Motif风格
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//            // 5.Mac风格
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
//            // 6.GTK风格
//            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//            // 7.可跨平台的默认风格
//            javax.swing.UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//            // 8.当前系统的风格
//            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JFrame setIcon(JFrame jf){
        Toolkit t = Toolkit.getDefaultToolkit();
        Image image = t.getImage("PowerForecast\\gui\\Logo.png");
        jf.setIconImage(image);
        return  jf;
    }

    public static JDialog setIcon(JDialog jd){
        Toolkit t = Toolkit.getDefaultToolkit();
        Image image = t.getImage("PowerForecast\\gui\\Logo.png");
        jd.setIconImage(image);
        return  jd;
    }
}
