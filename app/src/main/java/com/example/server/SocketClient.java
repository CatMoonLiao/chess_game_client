package com.example.server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class SocketClient  {
    public Socket socket;
    Thread threadW,threadCreate;
    Handler handler;
    BufferedWriter bw;
    BufferedReader br;
    boolean first=true;
    public SocketClient( final Handler handler){
        this.handler=handler;

        threadCreate = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket=new Socket();
                    SocketAddress socketAddress=new InetSocketAddress("10.115.49.27",8080);
                    socket.connect(socketAddress);
                    if(socket.isConnected())
                        Log.e("client","connected");
                    String send_msg="";
                    send_msg+="-1";
                    bw = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(send_msg+"\n");
                    bw.flush();
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(socket.isConnected()) {
                        String temp = br.readLine();
                        if (temp != null) {
                            int a=Integer.valueOf(temp);
                            Message msg = Message.obtain();
                            if(first){
                                msg.what = 0;
                                first=false;
                            }
                           else if(a==1000 || a==-1000) {
                                msg.what = 2;
                                socket.close();
                                msg.obj = temp;
                                handler.sendMessage(msg);
                                break;
                            }
                            else
                                msg.what=1;
                            msg.obj = temp;
                            handler.sendMessage(msg);
                        }
                        else
                            break;
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        threadCreate.start();

    }

    public void sendMsg(final int a){
        threadW=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bw.write(String.valueOf(a)+"\n");
                    bw.flush();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        threadW.start();
    }
}

