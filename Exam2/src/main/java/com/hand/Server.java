package com.hand;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;

    private List<Socket> clients;

    public Server(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            Socket socket = serverSocket.accept();
            clients.add(socket);
            new SendMsgThread(socket).start();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class SendMsgThread extends Thread {

        private Socket socket;

        public SendMsgThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                File file = new File(".." + File.separator + "Exam1" +
                        File.separator + "tmp" + File.separator + "SampleChapter1.pdf");
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                OutputStream outputStream = socket.getOutputStream();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                byte data[] = new byte[1024];
                int len = 0;
                while ((len = bufferedInputStream.read(data)) != -1) {
                    bufferedOutputStream.write(data, 0, len);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                outputStream.close();
                fileInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Server(12344);

    }
}
