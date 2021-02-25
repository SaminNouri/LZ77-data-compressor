package Unzip;

import Zip.*;

public class DeCompressor {

    private CFileReader cFileReader1;
    private CFileWriter cFileWriter;
    private String decompressedStr;
    private int v;
    private int b;
    private int numberOfBytes;


    public DeCompressor(String inputPath,String outputPath){
        try {
            cFileReader1 = new CFileReader(inputPath);
            decompressedStr=cFileReader1.readAllTheFile();
            b = cFileReader1.getB();
            v = cFileReader1.getV();
            if((b+v)%8==0)
                this.numberOfBytes=(b+v)/8;
            else
                this.numberOfBytes=(b+v)/8+1;
            cFileWriter = new CFileWriter(outputPath, b, v,numberOfBytes);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public DeCompressor(String str,String outputPath,int b,int v){
        try {
            decompressedStr=str;
            this.b=b;
            this.v=v;
            if((b+v)%8==0)
                this.numberOfBytes=(b+v)/8;
            else
                this.numberOfBytes=(b+v)/8+1;
            cFileWriter = new CFileWriter(outputPath, b, v,numberOfBytes);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int getB() {
        return b;
    }

    public int getV() {
        return v;
    }

    public void decompress(){
        try {
            cFileWriter.writeCharToFile(decompressedStr);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String decompressWS(){
        return (decompressedStr);
    }






    public static void main(String[] args){



    }



}
