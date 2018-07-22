package com.hand;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private Integer port;
    private Socket socket;

    private String saveDirName;

    private String fileName;

    public Client(Integer port, String saveDirName, String fileName) {
        this.port = port;
        this.saveDirName = saveDirName;
        this.fileName = fileName;
        try {
            this.socket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            File saveDir = new File(saveDirName);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File newFile = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream inputStream = socket.getInputStream();
            byte[] data = FileUtils.readInputStream(inputStream);
            bos.write(data);
            bos.flush();
            inputStream.close();
            bos.close();
            fos.close();
            System.out.println("下载文件成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*String path = Client.class.getClassLoader().getResource("").getPath();

        String saveDirName = new File(path).getParentFile().getParentFile().getPath()+File.separator+"tmp";*/

        String rootPath = System.getProperty("user.dir");
        String saveDirName=rootPath+File.separator+"Exam2"+File.separator+"tmp";
        System.out.println(saveDirName);
        new Client(12345, saveDirName,
                "SampleChapter1.pdf").start();
    }
}
