package com.dmtest.netty_learn.chapter04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by deming on 2018/9/13.
 */
public class PlainOioServer4_1 {

    public void serve(int port) throws IOException {
        System.out.println(" --- > start server on port:" + port);
        final ServerSocket socket = new ServerSocket(port);

        try{

            while (true) {

                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(() ->{

                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }

                }).start();

            }

        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception{
        PlainOioServer4_1 serve = new PlainOioServer4_1();

        serve.serve(1234);


    }

}
