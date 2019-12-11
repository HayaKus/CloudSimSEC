package gui;

import java.awt.*;

public class LogThreadSendToArea extends Thread {
    String logString;

    public LogThreadSendToArea(String s) {
        logString = s;
    }
    public void run() {
        GUI.logTextArea.append(logString+"\n");
        GUI.logScroll.getViewport().setViewPosition(new Point(0, GUI.logScroll.getVerticalScrollBar().getMaximum()));
    }
}