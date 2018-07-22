package com.hand;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Test {

    private String[] info;
    private String code;

    public Test(String code) {
        System.out.println("股票编码：");
        System.out.println(code);
        this.code = code;
        this.info = getInfo(code);
    }

    public static void saveFile(String str, String fileName) {
        try {
            String rootPath = System.getProperty("user.dir");
            String saveDirName = rootPath + File.separator + "Exam3" + File.separator + "tmp";
            File saveDir = new File(saveDirName);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File fileJson = new File(saveDir + File.separator + fileName);
            FileWriter fileWriter = new FileWriter(fileJson);
            fileWriter.write(str);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String[] getInfo(String code) {
        String strContent = HttpClientUtil.doGet("http://hq.sinajs.cn/list=" + code);
        return strContent.split(",");
    }

    class MyThread1 extends Thread {


        @Override
        public void run() {
            System.out.println("解析json");
            JsonObject jsonObject = new JsonObject();
            String name = info[0];
            String open = info[1];
            String close = info[2];
            String current = info[3];
            String high = info[4];
            String low = info[5];
            jsonObject.addProperty("name", name);
            jsonObject.addProperty("open", open);
            jsonObject.addProperty("close", close);
            jsonObject.addProperty("current", current);
            jsonObject.addProperty("high", high);
            jsonObject.addProperty("low", low);
            saveFile(jsonObject.toString(), code + ".json");

            System.out.println("解析json成功");

        }
    }

    class MyThread2 extends Thread {

        @Override
        public void run() {
            System.out.println("解析Xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            Document document = null;
            try {
                documentBuilder = factory.newDocumentBuilder();
                document = documentBuilder.newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            String name = info[0];
            String open = info[1];
            String close = info[2];
            String current = info[3];
            String high = info[4];
            String low = info[5];
            Element root = document.createElement("stock");
            Element nameNode = document.createElement("name");
            nameNode.setTextContent(name);
            Element openNode = document.createElement("open");
            openNode.setTextContent(open);
            Element closeNode = document.createElement("close");
            closeNode.setTextContent(close);
            Element currentNode = document.createElement("current");
            currentNode.setTextContent(close);
            Element highNode = document.createElement("high");
            highNode.setTextContent(high);
            Element lowNode = document.createElement("low");
            lowNode.setTextContent(low);
            root.appendChild(nameNode);
            root.appendChild(openNode);
            root.appendChild(closeNode);
            root.appendChild(currentNode);
            root.appendChild(highNode);
            root.appendChild(lowNode);
            document.appendChild(root);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
                StringWriter stringWriter = new StringWriter();
                transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
                saveFile(stringWriter.toString(), code + ".xml");
                System.out.println("解析xml成功");
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {

        Test test = new Test("sh601006");
        test.new MyThread1().start();
        test.new MyThread2().start();

    }
}

