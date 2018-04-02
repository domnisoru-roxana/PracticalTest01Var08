package practicaltest01var08.eim.systems.cs.pub.ro.practicaltest01var08;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Random;

/**
 * Created by student on 02.04.2018.
 */

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    String answer;

    private Random random = new Random();


    public ProcessingThread(Context context, String answer) {
        this.context = context;
        this.answer = answer;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        String crypt = answer;
        int pos = random.nextInt(answer.length());

        for (int i = 0; i < answer.length(); i++) {
            if (i != pos) {
                crypt = crypt.replace(crypt.charAt(i), '*');
            }
        }

        intent.setAction(Constants.actionTypes[0]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, crypt);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread destroyed!");
        isRunning = false;
    }
}
