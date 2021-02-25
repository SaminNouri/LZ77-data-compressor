package controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import Zip.*;

public class PathRecognizer {


    public static String listFilesForFolder(File folder, String str, ArrayList<File> arr) {
        str+=folder.getAbsolutePath()+"#";
        if(!folder.isDirectory()) {
            arr.add(folder);
            return str;
        }
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                str=listFilesForFolder(fileEntry,str,arr);
            } else {
                if(fileEntry.getAbsolutePath().contains(".DS_Store"))
                    continue;
                str+=(fileEntry.getAbsolutePath())+"#";
                arr.add(fileEntry);
            }
        }
        return str;
    }

    public static void writeDataToFile(File file,String output,int b,int v,String pass) throws Exception{
        CFileWriter cFileWriter=new CFileWriter(output,b,v,0,pass);
        ArrayList<File> arr=new ArrayList<>();
        String ans=listFilesForFolder(file,"",arr);
        System.out.println("absss:"+ans);
        cFileWriter.writeStringWithNumberToFile(ans);
        System.out.println("bb:"+ans+" "+ans.length());
        for (File file1:arr)
        {
            if(file1.isDirectory())
                continue;
            FileCompressor fileCompressor=new FileCompressor(b,v,file1.getAbsolutePath());
            String str=fileCompressor.compressFile(false);
            cFileWriter.writeStringWithNumberToFile(str);
        }


    }



    public static void main(String[] args)
    {
        File theDir = new File("ps");
        try {
            writeDataToFile(theDir,"m.ddd",3,5,"hellooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FolderContentReader folderContentReader=new FolderContentReader("m.ddd");
        try {
            folderContentReader.readAllTheFile("js","hellooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}
