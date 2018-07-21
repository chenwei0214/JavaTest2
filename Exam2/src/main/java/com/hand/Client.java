package com.hand;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client(Integer port) {
        try {
            this.socket = new Socket("127.0.0.1", port);
            InputStream inputStream = socket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            File saveDir = new File("." + File.separator + "Exam2" +
                    File.separator + "tmp");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + "SampleChapter1.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte data[] = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(data)) != -1) {
                bufferedOutputStream.write(data, 0, len);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.close();
            inputStream.close();
            bufferedInputStream.close();
            socket.close();
            System.out.println("保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new Client(12344);
    }

}
