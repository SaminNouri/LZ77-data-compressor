package Zip;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;

public class CFileReader {
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

    public CFileReader(String filePath) {
        this.filePath = filePath;

    }


    public CFileReader() {


    }


    public String readFromAllFileString(int position)
            throws IOException {
        System.out.println("filePath:"+filePath);
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

    public String readAllTheFile() throws Exception {

        String str="";

        RandomAccessFile file = new RandomAccessFile(filePath, "r");

        byte[] bt=readFromFile(1,file);

        BitSet bitSet1=BitConverter.ByteArraytoBitSet(bt);
        b=BitConverter.BitSettoInt1(bitSet1,3);
        v=BitConverter.BitSettoInt2(bitSet1,3);

        System.out.println("arrbv:"+Arrays.toString(bt));

        System.out.println("b,v:"+b+" "+v);


        if((b+v)%8==0)
            this.numberOfBytes=(b+v)/8;
        else
            this.numberOfBytes=(b+v)/8+1;

        boolean flag=false;



        while (position<file.length())
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

                    System.out.println("char is:"+s);
                    str+=s;
                }
            }
            else
            {
                // bb=readFromFile(defaultLength,file);
                BitSet bitSet=BitConverter.ByteArraytoBitSet(bb);
                System.out.println("bb:"+Arrays.toString(bb));
                int a=BitConverter.BitSettoInt1(bitSet,b);
                int t=BitConverter.BitSettoInt2(bitSet,b);
                defaultLength=1;
                System.out.println("a,t:"+a+" "+t);
                String temp=str.substring(t,t+a);
                System.out.println("temp is:"+temp);
                str+=temp;
                flag=false;
            }

        }
        System.out.println("str:"+str);
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
