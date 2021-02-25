package Zip;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CFileWriter {
    private String filePath;
    private int position=0;



    public CFileWriter(String filePath,int b,int v,int dLength,String password) {
        this.filePath = filePath;
        try {
            writeStringWithNumberToFile(password);
            writeToFile(b,v,3,5,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public CFileWriter(String filePath,int b,int v,int dLength) {
        this.filePath = filePath;
        try {
            writeToFile(b,v,3,5,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public CFileWriter(String filePath,int b,int v,int dLength,boolean flag) {
        this.filePath = filePath;


    }


    public void writeCharToFile(String data)
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(position);
        byte[] bytes=data.getBytes();
        file.write(bytes);
        position+=bytes.length;
        file.close();
    }

    private void write0ToFile()
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(position);
        byte[] bytes=new byte[1];
        bytes[0]=0;
        file.write(bytes);
        position+=bytes.length;
        file.close();
    }

    private void writeToFile( int a,int t,int b,int v,int dLength)
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(position);
        byte[] bytes=BitConverter.BitSettoByteArray(BitConverter.intToBitSet(a,t,b,v),dLength);
        //System.out.println(Arrays.toString(bytes)+" "+bytes.length);
        file.write(bytes);
        position+=bytes.length;
        file.close();
    }

    public void writeStringToFile( int a,int t,int b,int v,int dLength)
            throws IOException {
        writeCharToFile("0");
        writeToFile(a,t,b,v,dLength);
    }



    public void writeStringWithNumberToFile(String data)
            throws IOException {

        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        file.seek(position);
        byte[] bytes=data.getBytes();
        //System.out.println("wwnumber is;"+" "+position+" "+file.getFilePointer()+" "+bytes.length);
        file.writeInt(bytes.length);
        position+=4;
        file.write(bytes);
        position+=bytes.length;
        //System.out.println("wwnumbersfff is;"+" "+position+" "+file.getFilePointer()+" "+bytes.length);
        file.close();
    }



}
