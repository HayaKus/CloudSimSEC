package gui;

import structure.Constant;
import structure.InitialConstant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SubJPanelClass {
    static JTextField j1Text = new JTextField("");
    static JTextField j2Text = new JTextField("");
    static JTextField j3Text = new JTextField("");
    static JTextField j4Text = new JTextField("");
    static JTextField j5Text = new JTextField("");
    static JTextField j6Text = new JTextField("");
    static JTextField j7Text = new JTextField("");

    // p2 textField
    static JTextField j211Text = new JTextField("");
    static JTextField j212Text = new JTextField("");
    static JTextField j213Text = new JTextField("");
    static JTextField j214Text = new JTextField("");
    static JTextField j215Text = new JTextField("");

    static JTextField j221Text = new JTextField("");
    static JTextField j222Text = new JTextField("");
    static JTextField j223Text = new JTextField("");
    static JTextField j224Text = new JTextField("");
    static JTextField j225Text = new JTextField("");

    static JTextField j231Text = new JTextField("");
    static JTextField j232Text = new JTextField("");
    static JTextField j233Text = new JTextField("");
    static JTextField j234Text = new JTextField("");
    static JTextField j235Text = new JTextField("");

    static JTextField j241Text = new JTextField("");
    static JTextField j242Text = new JTextField("");
    static JTextField j243Text = new JTextField("");
    static JTextField j244Text = new JTextField("");
    static JTextField j245Text = new JTextField("");

    static JTextField j251Text = new JTextField("");
    static JTextField j252Text = new JTextField("");
    static JTextField j253Text = new JTextField("");
    static JTextField j254Text = new JTextField("");
    static JTextField j255Text = new JTextField("");

    static JTextField j261Text = new JTextField("");
    static JTextField j271Text = new JTextField("");

    // p3
    static JTextField j311Text = new JTextField("");
    static JTextField j312Text = new JTextField("");
    static JTextField j313Text = new JTextField("");
    static JTextField j314Text = new JTextField("");

    static JTextField j321Text = new JTextField("");
    static JTextField j322Text = new JTextField("");
    static JTextField j323Text = new JTextField("");
    static JTextField j324Text = new JTextField("");

    static JTextField j331Text = new JTextField("");
    static JTextField j341Text = new JTextField("");

    //p4
    static JTextField j411Text = new JTextField("");
    static JTextField j412Text = new JTextField("");
    static JTextField j413Text = new JTextField("");
    static JTextField j414Text = new JTextField("");

    static JTextField j421Text = new JTextField("");
    static JTextField j422Text = new JTextField("");
    static JTextField j423Text = new JTextField("");
    static JTextField j424Text = new JTextField("");

    static JTextField j431Text = new JTextField("");
    static JTextField j432Text = new JTextField("");
    static JTextField j433Text = new JTextField("");
    static JTextField j434Text = new JTextField("");

    //p5
    static JTextField j511Text = new JTextField("");
    static JTextField j512Text = new JTextField("");
    static JTextField j513Text = new JTextField("");
    static JTextField j514Text = new JTextField("");

    static String[] taskType = new String[]{"0", "1", "2", "3"};
    static JComboBox<String> j521Text = new JComboBox<String>(taskType);
    static JComboBox<String> j522Text = new JComboBox<String>(taskType);
    static JComboBox<String> j523Text = new JComboBox<String>(taskType);
    static JComboBox<String> j524Text = new JComboBox<String>(taskType);

    //p6
    static JTextField j611Text = new JTextField("");
    static JTextField j612Text = new JTextField("");
    static JTextField j613Text = new JTextField("");

    static JTextField j621Text = new JTextField("");
    static JTextField j622Text = new JTextField("");
    static JTextField j623Text = new JTextField("");

    static JTextField j631Text = new JTextField("");
    static JTextField j632Text = new JTextField("");
    static JTextField j633Text = new JTextField("");

    static JTextField j641Text = new JTextField("");
    static JTextField j642Text = new JTextField("");
    static JTextField j643Text = new JTextField("");

    // p1
    protected static JPanel getDataCenterPanel() {
        JPanel p1 = new JPanel();

        p1.setLayout(null);

        JLabel j1 = new JLabel("数据中心名称:");
        JLabel j2 = new JLabel("IT系统能耗模型:");
        JLabel j3 = new JLabel("模拟间隔(s):");
        JLabel j4 = new JLabel("IT维护系统能耗模型:");
        JLabel j5 = new JLabel("消防系统功耗(W):");
        JLabel j6 = new JLabel("基础设施系统功耗(W):");
        JLabel j7 = new JLabel("IT维护系统最大功耗(W):");

        JButton openFile = new JButton("浏览文件");
        JButton openFile2 = new JButton("浏览文件");

        j1.setBounds(150, 50, 100, 30);
        j2.setBounds(140, 100, 120, 30);
        j3.setBounds(160, 350, 100, 30);
        j4.setBounds(115, 150, 130, 30);
        j5.setBounds(135, 250, 130, 30);
        j6.setBounds(111, 300, 150, 30);
        j7.setBounds(100, 200, 150, 30);
        j1Text.setBounds(240, 50, 280, 30);
        j2Text.setBounds(240, 100, 280, 30);
        openFile.setBounds(525, 100, 70, 30);
        j3Text.setBounds(240, 350, 280, 30);
        j3Text.setEditable(false);
        j4Text.setBounds(240, 150, 280, 30);
        openFile2.setBounds(525, 150, 70, 30);
        j5Text.setBounds(240, 250, 280, 30);
        j6Text.setBounds(240, 300, 280, 30);
        j7Text.setBounds(240, 200, 280, 30);

        JFileChooser fc = new JFileChooser();
        openFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(GUI.frame);
                File file = fc.getSelectedFile();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    j2Text.setText(file.getAbsolutePath());
                }
            }
        });

        JFileChooser fc2 = new JFileChooser();
        openFile2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc2.showOpenDialog(GUI.frame);
                File file = fc2.getSelectedFile();
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    j4Text.setText(file.getAbsolutePath());
                }
            }
        });

        p1.add(j1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);
        p1.add(j5);
        p1.add(j6);
        p1.add(j7);
        p1.add(j1Text);
        p1.add(j2Text);
        p1.add(j3Text);
        p1.add(j4Text);
        p1.add(j5Text);
        p1.add(j6Text);
        p1.add(j7Text);
        p1.add(openFile);
        p1.add(openFile2);

        return p1;
    }

    // p2
    protected static JPanel getHostPanel() {
        JPanel p1 = new JPanel();

        p1.setLayout(null);
        // 主机名
        JLabel j1 = new JLabel("第 0 类服务器", JLabel.CENTER);
        JLabel j2 = new JLabel("第 1 类服务器", JLabel.CENTER);
        JLabel j3 = new JLabel("第 2 类服务器", JLabel.CENTER);
        JLabel j4 = new JLabel("第 3 类服务器", JLabel.CENTER);
        JLabel j5 = new JLabel("第 4 类服务器", JLabel.CENTER);

        j1.setBounds(105, 50, 100, 30);
        j2.setBounds(205, 50, 100, 30);
        j3.setBounds(305, 50, 100, 30);
        j4.setBounds(405, 50, 100, 30);
        j5.setBounds(505, 50, 100, 30);
        p1.add(j1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);
        p1.add(j5);
        // 数量

        JLabel j210 = new JLabel("数量:", JLabel.RIGHT);

        j210.setBounds(0, 90, 100, 30);
        j211Text.setBounds(110, 90, 90, 30);
        j212Text.setBounds(210, 90, 90, 30);
        j213Text.setBounds(310, 90, 90, 30);
        j214Text.setBounds(410, 90, 90, 30);
        j215Text.setBounds(510, 90, 90, 30);

        p1.add(j210);
        p1.add(j211Text);
        p1.add(j212Text);
        p1.add(j213Text);
        p1.add(j214Text);
        p1.add(j215Text);

        // 运算能力
        JLabel j220 = new JLabel("运算能力(MIPS):", JLabel.RIGHT);

        j220.setBounds(0, 130, 100, 30);
        j221Text.setBounds(110, 130, 90, 30);
        j222Text.setBounds(210, 130, 90, 30);
        j223Text.setBounds(310, 130, 90, 30);
        j224Text.setBounds(410, 130, 90, 30);
        j225Text.setBounds(510, 130, 90, 30);

        p1.add(j220);
        p1.add(j221Text);
        p1.add(j222Text);
        p1.add(j223Text);
        p1.add(j224Text);
        p1.add(j225Text);
        // 空闲功耗
        JLabel j230 = new JLabel("空闲功耗(W):", JLabel.RIGHT);

        j230.setBounds(0, 170, 100, 30);
        j231Text.setBounds(110, 170, 90, 30);
        j232Text.setBounds(210, 170, 90, 30);
        j233Text.setBounds(310, 170, 90, 30);
        j234Text.setBounds(410, 170, 90, 30);
        j235Text.setBounds(510, 170, 90, 30);

        p1.add(j230);
        p1.add(j231Text);
        p1.add(j232Text);
        p1.add(j233Text);
        p1.add(j234Text);
        p1.add(j235Text);

        // 最大功耗
        JLabel j240 = new JLabel("最大功耗(W):", JLabel.RIGHT);

        j240.setBounds(0, 210, 100, 30);
        j241Text.setBounds(110, 210, 90, 30);
        j242Text.setBounds(210, 210, 90, 30);
        j243Text.setBounds(310, 210, 90, 30);
        j244Text.setBounds(410, 210, 90, 30);
        j245Text.setBounds(510, 210, 90, 30);

        p1.add(j240);
        p1.add(j241Text);
        p1.add(j242Text);
        p1.add(j243Text);
        p1.add(j244Text);
        p1.add(j245Text);

        // 内存
        JLabel j250 = new JLabel("内存(MB):", JLabel.RIGHT);

        j250.setBounds(0, 250, 100, 30);
        j251Text.setBounds(110, 250, 90, 30);
        j252Text.setBounds(210, 250, 90, 30);
        j253Text.setBounds(310, 250, 90, 30);
        j254Text.setBounds(410, 250, 90, 30);
        j255Text.setBounds(510, 250, 90, 30);

        p1.add(j250);
        p1.add(j251Text);
        p1.add(j252Text);
        p1.add(j253Text);
        p1.add(j254Text);
        p1.add(j255Text);

        // 带宽
        JLabel j260 = new JLabel("带宽(bit/s):", JLabel.RIGHT);

        j260.setBounds(0, 300, 100, 30);
        j261Text.setBounds(110, 300, 90, 30);

        p1.add(j260);
        p1.add(j261Text);

        // 存储
        JLabel j270 = new JLabel("存储(GB):", JLabel.RIGHT);

        j270.setBounds(0, 350, 100, 30);
        j271Text.setBounds(110, 350, 90, 30);

        p1.add(j270);
        p1.add(j271Text);

        return p1;

    }

    // p3
    protected static JPanel getVMPanel() {
        JPanel p1 = new JPanel();

        p1.setLayout(null);
        // 主机名
        JLabel j1 = new JLabel("虚拟机0", JLabel.CENTER);
        JLabel j2 = new JLabel("虚拟机1", JLabel.CENTER);
        JLabel j3 = new JLabel("虚拟机2", JLabel.CENTER);
        JLabel j4 = new JLabel("虚拟机3", JLabel.CENTER);

        j1.setBounds(155, 50, 100, 30);
        j2.setBounds(255, 50, 100, 30);
        j3.setBounds(355, 50, 100, 30);
        j4.setBounds(455, 50, 100, 30);
        p1.add(j1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);
        // 运算能力
        JLabel j310 = new JLabel("运算能力(MIPS):", JLabel.RIGHT);

        j310.setBounds(40, 90, 100, 30);
        j311Text.setBounds(160, 90, 90, 30);
        j312Text.setBounds(260, 90, 90, 30);
        j313Text.setBounds(360, 90, 90, 30);
        j314Text.setBounds(460, 90, 90, 30);

        p1.add(j310);
        p1.add(j311Text);
        p1.add(j312Text);
        p1.add(j313Text);
        p1.add(j314Text);

        // 内存
        JLabel j320 = new JLabel("内存(MB):", JLabel.RIGHT);

        j320.setBounds(40, 130, 100, 30);
        j321Text.setBounds(160, 130, 90, 30);
        j322Text.setBounds(260, 130, 90, 30);
        j323Text.setBounds(360, 130, 90, 30);
        j324Text.setBounds(460, 130, 90, 30);

        p1.add(j320);
        p1.add(j321Text);
        p1.add(j322Text);
        p1.add(j323Text);
        p1.add(j324Text);

        // 带宽
        JLabel j330 = new JLabel("带宽(bit/s):", JLabel.RIGHT);

        j330.setBounds(40, 170, 100, 30);
        j331Text.setBounds(160, 170, 90, 30);

        p1.add(j330);
        p1.add(j331Text);

        // 存储
        JLabel j340 = new JLabel("存储(GB):", JLabel.RIGHT);

        j340.setBounds(40, 210, 100, 30);
        j341Text.setBounds(160, 210, 90, 30);

        p1.add(j340);
        p1.add(j341Text);

        return p1;
    }

    // p4
    protected static JPanel getCloudTaskPanel() {
        JPanel p1 = new JPanel();

        p1.setLayout(null);
        // 主机名
        JLabel j1 = new JLabel("云任务0", JLabel.CENTER);
        JLabel j2 = new JLabel("云任务1", JLabel.CENTER);
        JLabel j3 = new JLabel("云任务2", JLabel.CENTER);
        JLabel j4 = new JLabel("云任务3", JLabel.CENTER);

        j1.setBounds(155, 50, 100, 30);
        j2.setBounds(255, 50, 100, 30);
        j3.setBounds(355, 50, 100, 30);
        j4.setBounds(455, 50, 100, 30);
        p1.add(j1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);
        // 云任务量(MI）
        JLabel j410 = new JLabel("云任务量(MI):", JLabel.RIGHT);


        j410.setBounds(40, 90, 110, 30);
        j411Text.setBounds(160, 90, 90, 30);
        j412Text.setBounds(260, 90, 90, 30);
        j413Text.setBounds(360, 90, 90, 30);
        j414Text.setBounds(460, 90, 90, 30);

        p1.add(j410);
        p1.add(j411Text);
        p1.add(j412Text);
        p1.add(j413Text);
        p1.add(j414Text);

        // 内存
        JLabel j420 = new JLabel("文件数量:", JLabel.RIGHT);


        j420.setBounds(40, 130, 110, 30);
        j421Text.setBounds(160, 130, 90, 30);
        j422Text.setBounds(260, 130, 90, 30);
        j423Text.setBounds(360, 130, 90, 30);
        j424Text.setBounds(460, 130, 90, 30);

        p1.add(j420);
        p1.add(j421Text);
        p1.add(j422Text);
        p1.add(j423Text);
        p1.add(j424Text);

        // 输出文件数量(：
        JLabel j430 = new JLabel("输出文件数量:", JLabel.RIGHT);


        j430.setBounds(40, 170, 110, 30);
        j431Text.setBounds(160, 170, 90, 30);
        j432Text.setBounds(260, 170, 90, 30);
        j433Text.setBounds(360, 170, 90, 30);
        j434Text.setBounds(460, 170, 90, 30);

        p1.add(j430);
        p1.add(j431Text);
        p1.add(j432Text);
        p1.add(j433Text);
        p1.add(j434Text);

        return p1;
    }

    // p5
    protected static JPanel getUserPanel() {
        JPanel p1 = new JPanel();
        p1.setLayout(null);

        JLabel userCount = new JLabel("用户数量:", JLabel.RIGHT);
        userCount.setBounds(40, 10, 110, 30);
        JTextField userCountText = new JTextField("1");
        userCountText.setEditable(false);
        userCountText.setBounds(170, 10, 280, 30);
        p1.add(userCount);
        p1.add(userCountText);

        // 主机名
        JLabel j1 = new JLabel("虚拟机0", JLabel.CENTER);
        JLabel j2 = new JLabel("虚拟机1", JLabel.CENTER);
        JLabel j3 = new JLabel("虚拟机2", JLabel.CENTER);
        JLabel j4 = new JLabel("虚拟机3", JLabel.CENTER);

        j1.setBounds(155, 50, 100, 30);
        j2.setBounds(255, 50, 100, 30);
        j3.setBounds(355, 50, 100, 30);
        j4.setBounds(455, 50, 100, 30);
        p1.add(j1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);

        // 虚拟机数量
        JLabel j510 = new JLabel("虚拟机数量:", JLabel.RIGHT);

        j510.setBounds(40, 90, 110, 30);
        j511Text.setBounds(160, 90, 90, 30);
        j512Text.setBounds(260, 90, 90, 30);
        j513Text.setBounds(360, 90, 90, 30);
        j514Text.setBounds(460, 90, 90, 30);

        p1.add(j510);
        p1.add(j511Text);
        p1.add(j512Text);
        p1.add(j513Text);
        p1.add(j514Text);
        // 内存
        JLabel j520 = new JLabel("云任务种类(名称):", JLabel.RIGHT);


        j520.setBounds(40, 130, 110, 30);
        j521Text.setBounds(160, 130, 90, 30);
        j522Text.setBounds(260, 130, 90, 30);
        j523Text.setBounds(360, 130, 90, 30);
        j524Text.setBounds(460, 130, 90, 30);

        p1.add(j520);
        p1.add(j521Text);
        p1.add(j522Text);
        p1.add(j523Text);
        p1.add(j524Text);

        return p1;
    }

    // p6
    protected static JPanel getQoSPanel() {
        JPanel p1 = new JPanel();

        p1.setLayout(null);

        // QoS参数的相关指标
        JLabel j1 = new JLabel("最佳值", JLabel.CENTER);
        JLabel j2 = new JLabel("最差值", JLabel.CENTER);
        JLabel j3 = new JLabel("权重", JLabel.CENTER);

        j1.setBounds(190, 50, 100, 30);
        j2.setBounds(330, 50, 100, 30);
        j3.setBounds(470, 50, 100, 30);

        p1.add(j1);
        p1.add(j2);
        p1.add(j3);

        // cpu
        JLabel j610 = new JLabel("CPU利用率:", JLabel.RIGHT);

        j610.setBounds(65, 90, 110, 30);
        j611Text.setBounds(190, 90, 100, 30);
        j612Text.setBounds(330, 90, 100, 30);
        j613Text.setBounds(470, 90, 100, 30);

        JLabel jLast11 = new JLabel("%", JLabel.CENTER);
        JLabel jLast12 = new JLabel("%", JLabel.CENTER);
        JLabel jLast13 = new JLabel("%", JLabel.CENTER);

        jLast11.setBounds(250, 90, 100, 30);
        jLast12.setBounds(390, 90, 100, 30);
        jLast13.setBounds(530, 90, 100, 30);

        p1.add(j610);
        p1.add(j611Text);
        p1.add(j612Text);
        p1.add(j613Text);
        p1.add(jLast11);
        p1.add(jLast12);
        p1.add(jLast13);


        // 主机负载
        JLabel j620 = new JLabel("主机负载:", JLabel.RIGHT);

        j620.setBounds(65, 130, 110, 30);
        j621Text.setBounds(190, 130, 100, 30);
        j622Text.setBounds(330, 130, 100, 30);
        j623Text.setBounds(470, 130, 100, 30);

        JLabel jLast21 = new JLabel("台", JLabel.CENTER);
        JLabel jLast22 = new JLabel("台", JLabel.CENTER);
        JLabel jLast23 = new JLabel("%", JLabel.CENTER);

        jLast21.setBounds(250, 130, 100, 30);
        jLast22.setBounds(390, 130, 100, 30);
        jLast23.setBounds(530, 130, 100, 30);

        p1.add(j620);
        p1.add(j621Text);
        p1.add(j622Text);
        p1.add(j623Text);
        p1.add(jLast21);
        p1.add(jLast22);
        p1.add(jLast23);

        // 带宽
        JLabel j630 = new JLabel("带宽:", JLabel.RIGHT);

        j630.setBounds(65, 170, 110, 30);
        j631Text.setBounds(190, 170, 100, 30);
        j632Text.setBounds(330, 170, 100, 30);
        j633Text.setBounds(470, 170, 100, 30);

        JLabel jLast31 = new JLabel("GB", JLabel.CENTER);
        JLabel jLast32 = new JLabel("GB", JLabel.CENTER);
        JLabel jLast33 = new JLabel("%", JLabel.CENTER);

        jLast31.setBounds(250, 170, 100, 30);
        jLast32.setBounds(390, 170, 100, 30);
        jLast33.setBounds(530, 170, 100, 30);

        p1.add(j630);
        p1.add(j631Text);
        p1.add(j632Text);
        p1.add(j633Text);
        p1.add(jLast31);
        p1.add(jLast32);
        p1.add(jLast33);

        // 内存
        JLabel j640 = new JLabel("内存利用率:", JLabel.RIGHT);

        j640.setBounds(65, 210, 110, 30);
        j641Text.setBounds(190, 210, 100, 30);
        j642Text.setBounds(330, 210, 100, 30);
        j643Text.setBounds(470, 210, 100, 30);

        JLabel jLast41 = new JLabel("%", JLabel.CENTER);
        JLabel jLast42 = new JLabel("%", JLabel.CENTER);
        JLabel jLast43 = new JLabel("%", JLabel.CENTER);

        jLast41.setBounds(250, 210, 100, 30);
        jLast42.setBounds(390, 210, 100, 30);
        jLast43.setBounds(530, 210, 100, 30);

        p1.add(j640);
        p1.add(j641Text);
        p1.add(j642Text);
        p1.add(j643Text);
        p1.add(jLast41);
        p1.add(jLast42);
        p1.add(jLast43);

        return p1;
    }

    public static void initAndResetSubJPanelData() {
        // p1
        j1Text.setText(InitialConstant.DATACENTER_NAME);
        j2Text.setText(InitialConstant.IT_ENERGYMODEL);
        j3Text.setText(InitialConstant.SCHEDULING_INTERVAL + "");
        j4Text.setText(InitialConstant.ITENVIRONMENT_ENERGYMODEL);
        j5Text.setText(InitialConstant.FIREENERGY + "");
        j6Text.setText(InitialConstant.INFRASTRUCTUREENERGY + "");
        j7Text.setText(InitialConstant.ITENVIRONMENTMAXENERGY + "");
        // p2
        j211Text.setText(InitialConstant.VM_HOST_NUMBERS[0] + "");
        j212Text.setText(InitialConstant.VM_HOST_NUMBERS[1] + "");
        j213Text.setText(InitialConstant.VM_HOST_NUMBERS[2] + "");
        j214Text.setText(InitialConstant.VM_HOST_NUMBERS[3] + "");
        j215Text.setText(InitialConstant.VM_HOST_NUMBERS[4] + "");

        j221Text.setText(InitialConstant.HOST_MIPS[0] + "");
        j222Text.setText(InitialConstant.HOST_MIPS[1] + "");
        j223Text.setText(InitialConstant.HOST_MIPS[2] + "");
        j224Text.setText(InitialConstant.HOST_MIPS[3] + "");
        j225Text.setText(InitialConstant.HOST_MIPS[4] + "");

        j231Text.setText(InitialConstant.HOST_MIN_POWER[0] + "");
        j232Text.setText(InitialConstant.HOST_MIN_POWER[1] + "");
        j233Text.setText(InitialConstant.HOST_MIN_POWER[2] + "");
        j234Text.setText(InitialConstant.HOST_MIN_POWER[3] + "");
        j235Text.setText(InitialConstant.HOST_MIN_POWER[4] + "");

        j241Text.setText(InitialConstant.HOST_MAX_POWER[0] + "");
        j242Text.setText(InitialConstant.HOST_MAX_POWER[1] + "");
        j243Text.setText(InitialConstant.HOST_MAX_POWER[2] + "");
        j244Text.setText(InitialConstant.HOST_MAX_POWER[3] + "");
        j245Text.setText(InitialConstant.HOST_MAX_POWER[4] + "");

        j251Text.setText(InitialConstant.HOST_RAM[0] + "");
        j252Text.setText(InitialConstant.HOST_RAM[1] + "");
        j253Text.setText(InitialConstant.HOST_RAM[2] + "");
        j254Text.setText(InitialConstant.HOST_RAM[3] + "");
        j255Text.setText(InitialConstant.HOST_RAM[4] + "");

        j261Text.setText(InitialConstant.HOST_BW + "");
        j271Text.setText(InitialConstant.HOST_STORAGE/1000000 + "");

        // p3
        j311Text.setText(InitialConstant.VM_MIPS[0] + "");
        j312Text.setText(InitialConstant.VM_MIPS[1] + "");
        j313Text.setText(InitialConstant.VM_MIPS[2] + "");
        j314Text.setText(InitialConstant.VM_MIPS[3] + "");

        j321Text.setText(InitialConstant.VM_RAM[0] + "");
        j322Text.setText(InitialConstant.VM_RAM[1] + "");
        j323Text.setText(InitialConstant.VM_RAM[2] + "");
        j324Text.setText(InitialConstant.VM_RAM[3] + "");

        j331Text.setText(InitialConstant.VM_BW + "");
        j341Text.setText(InitialConstant.VM_SIZE/1000 + "");

        //p4
        j411Text.setText(InitialConstant.CLOUDLET_LENGTH[0] + "");
        j412Text.setText(InitialConstant.CLOUDLET_LENGTH[1] + "");
        j413Text.setText(InitialConstant.CLOUDLET_LENGTH[2] + "");
        j414Text.setText(InitialConstant.CLOUDLET_LENGTH[3] + "");

        j421Text.setText(InitialConstant.CLOUDLET_FILESIZE[0] + "");
        j422Text.setText(InitialConstant.CLOUDLET_FILESIZE[1] + "");
        j423Text.setText(InitialConstant.CLOUDLET_FILESIZE[2] + "");
        j424Text.setText(InitialConstant.CLOUDLET_FILESIZE[3] + "");

        j431Text.setText(InitialConstant.CLOUDLET_OUTPUTSIZE[0] + "");
        j432Text.setText(InitialConstant.CLOUDLET_OUTPUTSIZE[1] + "");
        j433Text.setText(InitialConstant.CLOUDLET_OUTPUTSIZE[2] + "");
        j434Text.setText(InitialConstant.CLOUDLET_OUTPUTSIZE[3] + "");

        //p5
        j511Text.setText(InitialConstant.USR_VMNUM_OWNED[0][0] + "");
        j512Text.setText(InitialConstant.USR_VMNUM_OWNED[0][1] + "");
        j513Text.setText(InitialConstant.USR_VMNUM_OWNED[0][2] + "");
        j514Text.setText(InitialConstant.USR_VMNUM_OWNED[0][3] + "");

        j521Text.setSelectedIndex(InitialConstant.USR_CLOUDLET_TYPE[0][0]);
        j522Text.setSelectedIndex(InitialConstant.USR_CLOUDLET_TYPE[0][1]);
        j523Text.setSelectedIndex(InitialConstant.USR_CLOUDLET_TYPE[0][2]);
        j524Text.setSelectedIndex(InitialConstant.USR_CLOUDLET_TYPE[0][3]);

        // p6
        j611Text.setText(InitialConstant.BEST_CPU + "");
        j612Text.setText(InitialConstant.WORST_CPU + "");
        j613Text.setText(InitialConstant.WEIGHT_CPU + "");

        j621Text.setText(InitialConstant.BEST_HOSTLOAD + "");
        j622Text.setText(InitialConstant.WORST_HOSTLOAD + "");
        j623Text.setText(InitialConstant.WEIGHT_HOSTLOAD + "");

        j631Text.setText(InitialConstant.BEST_BANDWIDTH + "");
        j632Text.setText(InitialConstant.WORST_BANDWIDTH + "");
        j633Text.setText(InitialConstant.WEIGHT_BANDWIDTH + "");

        j641Text.setText(InitialConstant.BEST_RAM + "");
        j642Text.setText(InitialConstant.WORST_RAM + "");
        j643Text.setText(InitialConstant.WEIGHT_RAM + "");

        //textArea
        GUI.setRightTextAreaClear();

        GUI.progressBar.setValue(0);

    }

    public static void inputSetClear() {
        j1Text.setText("");
        j2Text.setText("");
        j4Text.setText("");
        j5Text.setText("");
        j6Text.setText("");
        j7Text.setText("");
        // p2 textField
        j211Text.setText("");
        j212Text.setText("");
        j213Text.setText("");
        j214Text.setText("");
        j215Text.setText("");

        j221Text.setText("");
        j222Text.setText("");
        j223Text.setText("");
        j224Text.setText("");
        j225Text.setText("");

        j231Text.setText("");
        j232Text.setText("");
        j233Text.setText("");
        j234Text.setText("");
        j235Text.setText("");

        j241Text.setText("");
        j242Text.setText("");
        j243Text.setText("");
        j244Text.setText("");
        j245Text.setText("");

        j251Text.setText("");
        j252Text.setText("");
        j253Text.setText("");
        j254Text.setText("");
        j255Text.setText("");

        j261Text.setText("");
        j271Text.setText("");

        // p3
        j311Text.setText("");
        j312Text.setText("");
        j313Text.setText("");
        j314Text.setText("");

        j321Text.setText("");
        j322Text.setText("");
        j323Text.setText("");
        j324Text.setText("");

        j331Text.setText("");
        j341Text.setText("");

        //p4
        j411Text.setText("");
        j412Text.setText("");
        j413Text.setText("");
        j414Text.setText("");

        j421Text.setText("");
        j422Text.setText("");
        j423Text.setText("");
        j424Text.setText("");

        j431Text.setText("");
        j432Text.setText("");
        j433Text.setText("");
        j434Text.setText("");

        //p5
        j511Text.setText("");
        j512Text.setText("");
        j513Text.setText("");
        j514Text.setText("");

        GUI.setRightTextAreaClear();

        GUI.progressBar.setValue(0);

    }

    public static void setConstantParam() {

        // p1
        Constant.DATACENTER_NAME = j1Text.getText();
        Constant.IT_ENERGYMODEL = j2Text.getText();
        Constant.SCHEDULING_INTERVAL = Double.valueOf(j3Text.getText());
        Constant.ITENVIRONMENT_ENERGYMODEL = j4Text.getText();
        Constant.FIREENERGY = Double.valueOf(j5Text.getText());
        Constant.INFRASTRUCTUREENERGY = Double.valueOf(j6Text.getText());
        Constant.ITENVIRONMENTMAXENERGY = Double.valueOf(j7Text.getText());

        // p2
        Constant.VM_HOST_NUMBERS[0] = Integer.valueOf(j211Text.getText());
        Constant.VM_HOST_NUMBERS[1] = Integer.valueOf(j212Text.getText());
        Constant.VM_HOST_NUMBERS[2] = Integer.valueOf(j213Text.getText());
        Constant.VM_HOST_NUMBERS[3] = Integer.valueOf(j214Text.getText());
        Constant.VM_HOST_NUMBERS[4] = Integer.valueOf(j215Text.getText());

        Constant.HOST_MIPS[0] = Integer.valueOf(j221Text.getText());
        Constant.HOST_MIPS[1] = Integer.valueOf(j222Text.getText());
        Constant.HOST_MIPS[2] = Integer.valueOf(j223Text.getText());
        Constant.HOST_MIPS[3] = Integer.valueOf(j224Text.getText());
        Constant.HOST_MIPS[4] = Integer.valueOf(j225Text.getText());

        Constant.HOST_MIN_POWER[0] = Integer.valueOf(j231Text.getText());
        Constant.HOST_MIN_POWER[1] = Integer.valueOf(j232Text.getText());
        Constant.HOST_MIN_POWER[2] = Integer.valueOf(j233Text.getText());
        Constant.HOST_MIN_POWER[3] = Integer.valueOf(j234Text.getText());
        Constant.HOST_MIN_POWER[4] = Integer.valueOf(j235Text.getText());

        Constant.HOST_MAX_POWER[0] = Integer.valueOf(j241Text.getText());
        Constant.HOST_MAX_POWER[1] = Integer.valueOf(j242Text.getText());
        Constant.HOST_MAX_POWER[2] = Integer.valueOf(j243Text.getText());
        Constant.HOST_MAX_POWER[3] = Integer.valueOf(j244Text.getText());
        Constant.HOST_MAX_POWER[4] = Integer.valueOf(j245Text.getText());

        // make the maximum power of one host
        int MAXPOWER = 0;
        for(int host_max_power:Constant.HOST_MAX_POWER){
            if(MAXPOWER < host_max_power)
                MAXPOWER = host_max_power;
        }
        Constant.MAXPOWERS = MAXPOWER;

        Constant.HOST_RAM[0] = Integer.valueOf(j251Text.getText());
        Constant.HOST_RAM[1] = Integer.valueOf(j252Text.getText());
        Constant.HOST_RAM[2] = Integer.valueOf(j253Text.getText());
        Constant.HOST_RAM[3] = Integer.valueOf(j254Text.getText());
        Constant.HOST_RAM[4] = Integer.valueOf(j255Text.getText());

        Constant.HOST_BW = Long.valueOf(j261Text.getText());
        Constant.HOST_STORAGE = Long.valueOf(j271Text.getText())*1000000;

        // p3
        Constant.VM_MIPS[0] = Integer.valueOf(j311Text.getText());
        Constant.VM_MIPS[1] = Integer.valueOf(j312Text.getText());
        Constant.VM_MIPS[2] = Integer.valueOf(j313Text.getText());
        Constant.VM_MIPS[3] = Integer.valueOf(j314Text.getText());

        Constant.VM_RAM[0] = Integer.valueOf(j321Text.getText());
        Constant.VM_RAM[1] = Integer.valueOf(j322Text.getText());
        Constant.VM_RAM[2] = Integer.valueOf(j323Text.getText());
        Constant.VM_RAM[3] = Integer.valueOf(j324Text.getText());

        Constant.VM_BW = Long.valueOf(j331Text.getText());
        Constant.VM_SIZE = Long.valueOf(j341Text.getText())*1000;


        // p4
        Constant.CLOUDLET_LENGTH[0] = Long.valueOf(j411Text.getText());
        Constant.CLOUDLET_LENGTH[1] = Long.valueOf(j412Text.getText());
        Constant.CLOUDLET_LENGTH[2] = Long.valueOf(j413Text.getText());
        Constant.CLOUDLET_LENGTH[3] = Long.valueOf(j414Text.getText());

        Constant.CLOUDLET_FILESIZE[0] = Integer.valueOf(j421Text.getText());
        Constant.CLOUDLET_FILESIZE[1] = Integer.valueOf(j422Text.getText());
        Constant.CLOUDLET_FILESIZE[2] = Integer.valueOf(j423Text.getText());
        Constant.CLOUDLET_FILESIZE[3] = Integer.valueOf(j424Text.getText());

        Constant.CLOUDLET_OUTPUTSIZE[0] = Integer.valueOf(j431Text.getText());
        Constant.CLOUDLET_OUTPUTSIZE[1] = Integer.valueOf(j432Text.getText());
        Constant.CLOUDLET_OUTPUTSIZE[2] = Integer.valueOf(j433Text.getText());
        Constant.CLOUDLET_OUTPUTSIZE[3] = Integer.valueOf(j434Text.getText());

        // p5
        Constant.USR_VMNUM_OWNED[0][0] = Integer.valueOf(j511Text.getText());
        Constant.USR_VMNUM_OWNED[0][1] = Integer.valueOf(j512Text.getText());
        Constant.USR_VMNUM_OWNED[0][2] = Integer.valueOf(j513Text.getText());
        Constant.USR_VMNUM_OWNED[0][3] = Integer.valueOf(j514Text.getText());

        Constant.USR_CLOUDLET_TYPE[0][0] = Integer.valueOf((String) j521Text.getSelectedItem());
        Constant.USR_CLOUDLET_TYPE[0][1] = Integer.valueOf((String) j522Text.getSelectedItem());
        Constant.USR_CLOUDLET_TYPE[0][2] = Integer.valueOf((String) j523Text.getSelectedItem());
        Constant.USR_CLOUDLET_TYPE[0][3] = Integer.valueOf((String) j524Text.getSelectedItem());

        // p6
        Constant.BEST_CPU = Integer.valueOf(j611Text.getText());
        Constant.WORST_CPU = Integer.valueOf(j612Text.getText());
        Constant.WEIGHT_CPU = Integer.valueOf(j613Text.getText());
        Constant.BEST_HOSTLOAD = Integer.valueOf(j621Text.getText());
        Constant.WORST_HOSTLOAD = Integer.valueOf(j622Text.getText());
        Constant.WEIGHT_HOSTLOAD = Integer.valueOf(j623Text.getText());
        Constant.BEST_BANDWIDTH = Integer.valueOf(j631Text.getText());
        Constant.WORST_BANDWIDTH = Integer.valueOf(j632Text.getText());
        Constant.WEIGHT_BANDWIDTH = Integer.valueOf(j633Text.getText());
        Constant.BEST_RAM = Integer.valueOf(j641Text.getText());
        Constant.WORST_RAM = Integer.valueOf(j642Text.getText());
        Constant.WEIGHT_RAM  = Integer.valueOf(j643Text.getText());

        //右侧文本框
        GUI.logTextArea.setText("");
    }

}