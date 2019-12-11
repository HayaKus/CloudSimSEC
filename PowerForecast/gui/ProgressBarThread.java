package gui;

public class ProgressBarThread extends Thread {
    int startValue;
    int stopValue;
    long moveSpeed;

    ProgressBarThread(int start, int stop, long speed) {
        startValue = start;
        stopValue = stop;
        moveSpeed = speed;

    }
    public void run() {
        try {
            for (int i = startValue; i <= stopValue; i++) {
                this.sleep(moveSpeed);
                GUI.progressBar.setValue(i);
            }
        }catch (Exception e){
            System.out.println("sleep interrupted");
        }
    }
}


