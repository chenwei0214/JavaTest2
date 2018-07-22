package com.hand;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len;
        while ((len = bis.read(data)) != -1) {
            bos.write(data, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
