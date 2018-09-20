package com.dmtest.netty_learn.chapter01;

import com.dmtest.netty_learn.utils.CloseUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by deming on 2018/9/1.
 */
public class PlainEchoServer131 {



    /*
     blocking - io 模式的代码
     每个连接都需要一个单独的线程，即使用到了线程池来避免频繁创建线程也起不了多大作用。
     因为能服务的client数，取决于线程数，或者是线程池中的线程数。如果要处理成千的并发client,那就有大问题了。
      */

    public void serve(int port) throws Exception  {

        final ServerSocket socket = new ServerSocket(port);

        try {
            while (true) {
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from : "+ clientSocket);


                new Thread(()->{
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);
                        while (true){
                            writer.println(reader.readLine());
                            writer.flush();
                        }

                    }catch (Exception e) {
                        CloseUtil.close(clientSocket);
                    }
                }).start();

            }

        }catch (Exception e) {

        }


    }

}
