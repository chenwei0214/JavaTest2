package com.hand;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private Integer port;

    private String filePath;

    private ServerSocket serverSocket;

    public Server(Integer port, String filePath) {
        this.port = port;
        this.filePath = filePath;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        while (true) {
            try {
                //等待client的请求
                System.out.println("waiting...");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("开始发送文件...");
                    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                    FileInputStream fis = new FileInputStream(new File(filePath));
                    bos.write(FileUtils.readInputStream(fis));
                    bos.flush();
                    bos.close();
                    socket.close();
                    System.out.println("发送完毕！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

       /* System.out.println( System.getProperty("user.dir"));

        System.out.println(Client.class.getClassLoader().getResource("/"));

        String path = Client.class.getClassLoader().getResource("").getPath();
        String rootPath = new File(path).getParentFile().getParentFile().getParentFile().getPath();*/
        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
        String filePath = rootPath + File.separator + "Exam1" +
                File.separator + "tmp" + File.separator + "SampleChapter1.pdf";
        new Server(12345, filePath).start();
    }
}
