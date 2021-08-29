package com.mayank.security.dynamicprocessname;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mayank.security.dynamicprocessname.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    static int pid;
    static String whoami;
    static String oProcessName;
    static String cProcessName;

    // Used to load the 'dynamicprocessname' library on application startup.
    static {
        System.loadLibrary("dynamicprocessname");
    }

    private ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView tv = binding.sampleText;
        Button btn = binding.button;

        //get current process pid
        pid = android.os.Process.myPid();

        // Get Original Process Name
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid)
            {
                oProcessName = processInfo.processName;
            }
        }

        //Get user
        try {
            whoami = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("whoami").getInputStream())).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get __progname value
        cProcessName = getProcessName();

        tv.setText("User: "+whoami+"\n\n"+
                "PID: "+pid+"\n\n"+
                "Original Process Name:\n"+oProcessName+"\n\n"+
                "Current Process Name:"+"\n"+ cProcessName);

        btn.setOnClickListener(view -> {
            cProcessName = newProcessName();
            tv.setText("User: "+whoami+"\n\n"+
                    "PID: "+pid+"\n\n"+
                    "Original Process Name:\n"+oProcessName+"\n\n"+
                    "Current Process Name:"+"\n"+ cProcessName);
        });
    }

    /**
     * A native method that is implemented by the 'dynamicprocessname' native library,
     * which is packaged with this application.
     */
    public native String newProcessName();
    public native String getProcessName();
}