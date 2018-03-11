package com.netscout.iperf3_client;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    IperfTask iperfTask = null;

    TextView tv;
    //A global pointer for instances of of iperf (only one at a time is allowed).
    // IperfTask iperfTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyFile("iperf3");
        // startApp();
    }

    private static final String TAG = "MainActivity";

    //This function is used to copy the iperf3 executable to a directory which execute permissions for this application, and then gives it execute permissions.
    //It runs on every initiation of an iperf3 test, but copies the file only if it's needed.
    private void copyFile(String filename) {
        AssetManager assetManager = getAssets();
        String appFileDir = getApplicationInfo().dataDir + "/files";
        String executableFile = appFileDir + "/iperf3";
        try {
            InputStream in = assetManager.open(filename);
            File outFile = new File(appFileDir, filename);
            OutputStream out = new FileOutputStream(outFile);

            Log.v(TAG, "Attempting to copy this file: " + filename); // + " to: " +       assetCopyDestination);
            int read;
            byte[] buffer = new byte[4096];
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
            out.flush();
            out.close();
            in.close();

            //After the copy operation is finished we give execute permissions to iPerf3
            File execFile = new File(executableFile);
            execFile.setExecutable(true);
            execFile.setWritable(true);
            execFile.setReadable(true);

        } catch (IOException e) {
            Log.v(TAG, "Failed to copy asset file: " + filename, e);
        }

    }

    // FUNCTION: start iPerf3 and read stdout
    public void onButtonClick(View v) throws IOException {
        try {
            iperfTask = new IperfTask();
            iperfTask.execute();
        } catch (Exception e) {
            Log.v("Iperf Task Exception:", String.valueOf(e));
        }

    }

}