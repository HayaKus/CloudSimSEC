package gui;

import main.Main;
import org.cloudbus.cloudsim.Log;

public class ResultBtnThread extends Thread{
    public void run(){
        try {
            //update form
            SubJPanelClass.setConstantParam();
            //computing
            Main.main2();
            try {
                String path = System.getProperty("user.dir");
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+path+"/output/index.html");
            } catch (Exception e) {
                e.printStackTrace() ;
            }


        }catch (Exception ei) {
            ei.printStackTrace();
            Log.printLine("Click GUI");
        }
    }

}