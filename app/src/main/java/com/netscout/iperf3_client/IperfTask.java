package com.netscout.iperf3_client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by krisarmstrong on 3/9/18.
 */

public class IperfTask extends AsyncTask<Void, String, String> {

    Context context;

    // String appFileDir = context.getApplicationInfo().dataDir + "/files";

    @Override
    protected String doInBackground(Void... voids) {
        // StringBuffer output = new StringBuffer();

        Process process = null;
        // String appFileDir = context.getApplicationInfo().dataDir + "/files";
        String appFileDir = "/data/data/com.netscout.iperf3_client/files";
        System.out.print(appFileDir);
        String commandLine = appFileDir + "/iperf3.5mod";
        String args1 = "-c10.0.150.117";
        String args2 = "-c129.196.197.116";


        try {
            //The process is now being run with the verified parameters.
            process = new ProcessBuilder(commandLine, args1, "-J", "-O3", "P4", "--forceflush")
                    .redirectErrorStream(true)
                    .start();

            // A buffered output of the stdout is being initialized so the
            // iPerf3 output could be displayed on the screen.
            BufferedReader reader = new BufferedReader(new InputStreamReader
                    (process.getInputStream()));
            int read;

            //The output text is accumulated into a string buffer and
            // published to the GUI
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
                Log.v("OUTPUT", String.valueOf(output));
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("IO Exception:", String.valueOf(e));
        }
        return null;
    }

}
