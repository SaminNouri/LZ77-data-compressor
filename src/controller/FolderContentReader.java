package controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import Zip.*;

public class FolderContentReader {

    private String filePath;
    private int position=0;
    private int numberOfBytes=0;
    private int defaultLength=1;
    private Integer b=0;
    private Integer v=0;




    public Integer getB() {
        return b;
    }

    public Integer getV() {
        return v;
    }

    public FolderContentReader(String filePath) {
        this.filePath = filePath;

    }




    public String readFromAllFileString(int position)
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        file.seek(position);
        byte[] bytes = new byte[(int) file.length()];
        file.read(bytes);
        this.position+=file.length();
        file.close();
        String s = new String(bytes, StandardCharsets.US_ASCII);
        return s;
    }



    //////////////////////////////////////////////////////

    private byte[] readFromFile( int size,RandomAccessFile file)
            throws IOException {
        file.seek(position);
        byte[] bytes = new byte[size];
        file.read(bytes);
        this.position+=size;
        return bytes;
    }

    public String readAllTheFile(String output,String password) throws Exception {


        String str="";


        RandomAccessFile file = new RandomAccessFile(filePath, "r");

        String pass=readPassword(file);

        System.out.println(pass);

        if(!password.equals(pass))
            return null;


        readBV(file);



        String addresses=readAddressContent(file);



        ArrayList<String> paths=PathConverter.covert(addresses,output);


        for(String ps:paths)
        {

            String fc=readFileContent(file);


            System.out.println("ffccc:"+(ps)+" "+(paths));


            CFileWriter cFileWriter=new CFileWriter(ps,b,v,0,true);

            cFileWriter.writeCharToFile(fc);



        }

        return str;


    }

    private String readPassword(RandomAccessFile file) {
        byte[] bt=null;

        try {

            int size=file.readInt();
            position+=4;
            bt=readFromFile(size,file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = new String(bt, StandardCharsets.US_ASCII);

        return s;


    }


    public void readBV(RandomAccessFile file) throws Exception{

        byte[] bt=readFromFile(1,file);

        BitSet bitSet1=BitConverter.ByteArraytoBitSet(bt);
        b=BitConverter.BitSettoInt1(bitSet1,3);
        v=BitConverter.BitSettoInt2(bitSet1,3);

        if((b+v)%8==0)
            this.numberOfBytes=(b+v)/8;
        else
            this.numberOfBytes=(b+v)/8+1;


    }



    public String readAddressContent(RandomAccessFile file)throws Exception{


        int num=file.readInt();

        position+=4;

        System.out.println("number isss:"+num+" "+position);

        byte[] bb=null;

        bb=readFromFile(num,file);

        String s = new String(bb, StandardCharsets.US_ASCII);

        System.out.println("ssss:"+s);


        return s;


    }




    public String readFileContent(RandomAccessFile file)throws Exception{

        String str="";

        boolean flag=false;


        int num=file.readInt();

        position+=4;


        int initialPosition=position;

        while (position<initialPosition+num)
        {
            byte[] bb=null;
            bb=readFromFile(defaultLength,file);

            if(!flag)
            {
                String s = new String(bb, StandardCharsets.US_ASCII);
                if(s.equals("0")) {
                    flag = true;
                    defaultLength=numberOfBytes;
                }
                else
                {

                    str+=s;
                }
            }
            else
            {
                BitSet bitSet=BitConverter.ByteArraytoBitSet(bb);
                int a=BitConverter.BitSettoInt1(bitSet,b);
                int t=BitConverter.BitSettoInt2(bitSet,b);
                defaultLength=1;
                String temp=str.substring(t,t+a);
                str+=temp;
                flag=false;
            }

        }

        System.out.println("ret:"+str);
        return str;

    }



    public static void main(String[] args)
    {
        CFileReader cFileReader=new CFileReader("m.txt");
        try {
            cFileReader.readAllTheFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
