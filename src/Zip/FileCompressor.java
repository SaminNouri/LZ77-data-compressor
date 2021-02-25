package Zip;

import java.io.IOException;

public class FileCompressor {

    private static Window window;
    private Integer b=0;
    private Integer v=0;
    private Integer w=1;
    private Integer B=1;
    private Integer numberOfBytes=0;
    private String mainString="";
    private Integer a=1;
    private Integer t=0;
    private String str=null;
    private String outputFilePath="";
    private CFileWriter cFileWriter;
    private CFileReader cFileReader;

    public FileCompressor(Integer b,Integer v,String str,String outputFilePath) {
        this.b = b;
        this.v=v;
        this.B=power(2,b);
        this.w=power(2,v);
        window=new Window(w);
        this.mainString=str;
        if((b+v)%8==0)
            this.numberOfBytes=(b+v)/8;
        else
            this.numberOfBytes=(b+v)/8+1;
        this.outputFilePath=outputFilePath;
        this.cFileWriter=new CFileWriter(outputFilePath,b,v,numberOfBytes);
    }

    public FileCompressor(Integer b,Integer v,String inputpath,String outputFilePath,boolean flag) {
        this.b = b;
        this.v=v;
        this.B=power(2,b);
        this.w=power(2,v);
        window=new Window(w);
        this.cFileReader=new CFileReader(inputpath);
        this.mainString=readInputFileContent();
        if((b+v)%8==0)
            this.numberOfBytes=(b+v)/8;
        else
            this.numberOfBytes=(b+v)/8+1;
        this.outputFilePath=outputFilePath;
        this.cFileWriter=new CFileWriter(outputFilePath,b,v,numberOfBytes);

    }

    public FileCompressor(Integer b,Integer v,String inputpath) {
        this.b =b;
        this.v=v;
        this.B=power(2,b);
        this.w=power(2,v);
        window=new Window(w);
        this.cFileReader=new CFileReader(inputpath);
        this.mainString=readInputFileContent();
        if((b+v)%8==0)
            this.numberOfBytes=(b+v)/8;
        else
            this.numberOfBytes=(b+v)/8+1;
        this.outputFilePath="";
        this.cFileWriter=null;

    }


    public String readInputFileContent(){
        try {
            return cFileReader.readFromAllFileString(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String compressFile(boolean flag){

        String finalResult="";

        while (window.getJ()+1<mainString.length()) {

            a = 1;
            t = window.getJ() + 1;
            str = mainString.charAt(window.getJ() + 1) + "";

            int f1 = window.getJ() + 2 + numberOfBytes;
            int f2 = Math.min(mainString.length(), window.getJ() + B);


            // System.out.println(f1+" "+f2);

            String str1 = mainString.substring(window.getI(), window.getJ() + 1);
            String str2 = mainString.substring(window.getJ() + 1, f2);

            if (str1.length() > numberOfBytes + 1) {
                int[] arr = LongestSubstring.getTheLongestSubstring(str1, str2);
                int index = arr[0];
                if (index != -1 && arr[1]> numberOfBytes + 1) {
                    a = arr[1];
                    t = index + Window.getI();
                    str = null;

                    finalResult+=mainString.substring(t,a+t);

                }
            }
            if(str!=null)
                finalResult+=str;

            func(a,t,str,flag);


        }
        return finalResult;

    }

    private void func(Integer a, Integer t, String str,boolean flag) {
        if(str==null)
        {
            window.increaseW(a);
            try {
                if(flag && cFileWriter!=null)
                    cFileWriter.writeStringToFile(a,t,b,v,numberOfBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                if(flag && cFileWriter!=null)
                    cFileWriter.writeCharToFile(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            window.increaseW(a);
        }

    }


    public static void main(String[] args){
        FileCompressor compressor=new FileCompressor(2,3,"t.txt","m.txt",true);
        compressor.compressFile(true);
    }

    public static int power(int base, int exponent){

        int result = 1;

        while (exponent != 0)
        {
            result *= base;
            --exponent;
        }

        return result;
    }


}
