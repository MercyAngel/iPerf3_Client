package com.netscout.iperf3_client;

import android.content.Context;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

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
        String appFileDir = getApplicationInfo().dataDir;
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

        } catch (IOException e) {
            Log.v(TAG, "Failed to copy asset file: " + filename, e);
        }

    }

    // FUNCTION: start iPerf3 and read stdout
    public void onButtonClick(View v) {
        boolean isRunning = false;
        if (isRunning == false) {
            startApp();
        }
    }

    public void startApp() {
        StringBuffer output = new StringBuffer();
        Process process = null;
        String appFileDir = getApplicationInfo().dataDir;
        String commandLine = appFileDir + "/iperf3 -c 129.196.197.74 --forceflush";

        //The process is now being run with the verified parameters.
/*        try {
            process = new ProcessBuilder().command(commandLine).redirectErrorStream(true).start();

            //A buffered output of the stdout is being initialized so the iperf output could be displayed on the screen.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int read;

            //The output text is accumulated into a string buffer and published to the GUI
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
                // Log.v(TAG, String.valueOf(output));
                // Log.v(TAG, String.valueOf(reader));
                //This is used to pass the output to the thread running the GUI, since this is separate thread.
                // publishProgress(output.toString());
                //output.delete(0, output.length());
            }

        } catch (Exception e) {
            Log.v(TAG, "Verbose: ", e);
            // e.printStackTrace();*/

        // Code FROM KEVIN B.

       /* Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec("iperf3 --forceflush -c 129.196.197.74");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream stderr = proc.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        Log.e(TAG, String.valueOf(isr));

        String line = null;
        System.out.println("<ERROR>");
        try {
            while ( (line = br.readLine()) != null)
                System.out.println(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("</ERROR>");
        int exitVal = 0;
        try {
            exitVal = proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Process exitValue: " + exitVal);
*/


       try {
            process = Runtime.getRuntime().exec(commandLine);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "n");
                Log.e(TAG, String.valueOf(output));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Output", String.valueOf(e));
        }
    } //Function startApp End ---->


    //Class End ----->
}