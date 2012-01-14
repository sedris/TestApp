package com.sample.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class TestActivity extends Activity {
    Button btnStart;
    Button btnSend;
    TextView textStatus;
    NetworkTask networktask;
    final String hostname = "mitprint.mit.edu";
    final String printername = "bw";
//    final String filename = "res/drawable-hdpi/pdfs/go_club_midway.pdf";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnSend = (Button)findViewById(R.id.btnSend);
        textStatus = (TextView)findViewById(R.id.textStatus);
        btnStart.setOnClickListener(btnStartListener);
        btnSend.setOnClickListener(btnSendListener);
        networktask = new NetworkTask(); //Create initial instance so SendDataToNetwork doesn't throw an error.

//        try {
//            lpr.printFile("res/drawable-hdpi/pdfs/go_club_midway.pdf", "mitprint.mit.edu", "bw", "bob");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
    
    private OnClickListener btnStartListener = new OnClickListener() {
        public void onClick(View v){
            btnStart.setVisibility(View.INVISIBLE);
            networktask = new NetworkTask(); //New instance of NetworkTask
            networktask.execute();
        }
    };
    
    private OnClickListener btnSendListener = new OnClickListener() {
        public void onClick(View v){
            textStatus.setText("Sending Message to AsyncTask.");
//            networktask.SendDataToNetwork("GET / HTTP/1.1\r\n\r\n");
        }
    };
    
    public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
//        Socket nsocket; //Network Socket
//        InputStream nis; //Network Input Stream
//        OutputStream nos; //Network Output Stream

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            Lpr lpr = new Lpr();

            try {
//                InputStream is = getResources().openRawResource(R.raw.go_club_midway);
//                File f = new File("/sdcard/download/possible.pdf");
//                f.createNewFile();
//                OutputStream os = new FileOutputStream(f);
//                byte buf[]=new byte[1024];
//                int len;
//                while((len=is.read(buf))>0) {
//                    os.write(buf,0,len);
//                    os.close();
//                    is.close();
//                }
                File f = new File("/sdcard/download/Star_Market_Shuttle-1.pdf");

                lpr.printFile(f, "mitprint.mit.edu", "bw", "bob");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();      
                Log.i("AsyncTask", "doInBackground: IOException");
                result = true;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: Exception");
                result = true;
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: URISyntaxException e");
                result = true;
            }
            return result;

            
//            try {
//                Log.i("AsyncTask", "doInBackground: Creating socket");
//                
//                SocketAddress sockaddr = new InetSocketAddress("192.168.1.1", 80);
//                nsocket = new Socket();
//                nsocket.connect(sockaddr, 5000); //10 second connection timeout
//                if (nsocket.isConnected()) { 
//                    nis = nsocket.getInputStream();
//                    nos = nsocket.getOutputStream();
//                    Log.i("AsyncTask", "doInBackground: Socket created, streams assigned");
//                    Log.i("AsyncTask", "doInBackground: Waiting for inital data...");
//                    byte[] buffer = new byte[4096];
//                    int read = nis.read(buffer, 0, 4096); //This is blocking
//                    while(read != -1){
//                        byte[] tempdata = new byte[read];
//                        System.arraycopy(buffer, 0, tempdata, 0, read);
//                        publishProgress(tempdata);
//                        Log.i("AsyncTask", "doInBackground: Got some data");
//                        read = nis.read(buffer, 0, 4096); //This is blocking
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.i("AsyncTask", "doInBackground: IOException");
//                result = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("AsyncTask", "doInBackground: Exception");
//                result = true;
//            } finally {
//                try {
//                    nis.close();
//                    nos.close();
//                    nsocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Log.i("AsyncTask", "doInBackground: Finished");
//            }
//            return result;
            
        }

//        public void SendDataToNetwork(String cmd) { //You run this from the main thread.
//            try {
//                if (nsocket.isConnected()) {
//                    Log.i("AsyncTask", "SendDataToNetwork: Writing received message to socket");
//                    nos.write(cmd.getBytes());
//                } else {
//                    Log.i("AsyncTask", "SendDataToNetwork: Cannot send message. Socket is closed");
//                }
//            } catch (Exception e) {
//                Log.i("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception");
//            }
//        }

//        @Override
//        protected void onProgressUpdate(byte[]... values) {
//            if (values.length > 0) {
//                Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
//                textStatus.setText(new String(values[0]));
//            }
//        }
        @Override
        protected void onCancelled() {
            Log.i("AsyncTask", "Cancelled.");
            btnStart.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
                textStatus.setText("There was a connection error.");
            } else {
                Log.i("AsyncTask", "onPostExecute: Completed.");
                textStatus.setText("task completed. successful?");
            }
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networktask.cancel(true); //In case the task is currently running
    }
}