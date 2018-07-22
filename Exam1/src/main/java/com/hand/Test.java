package com.hand;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = bufferedInputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.flush();
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            String urlStr = "http://192.168.11.205:18080/trainning/SampleChapter1.pdf";
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] data = readInputStream(inputStream);

            String rootPath = System.getProperty("user.dir");
            String saveDirName = rootPath + File.separator + "Exam1" + File.separator + "tmp";
            File saveDir = new File(saveDirName);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(saveDir + File.separator + "SampleChapter1.pdf");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            System.out.println("Exam1 done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}