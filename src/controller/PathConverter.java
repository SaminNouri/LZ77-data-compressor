package controller;


import java.io.File;
import java.util.ArrayList;

public class PathConverter {



    public static ArrayList<String> covert(String str, String output){

        ArrayList<String> ans=new ArrayList<>();
        String[] arr=str.split("#");
        if(arr.length==1) {
            ans.add(output);
            return ans;
        }
        String mainAd=arr[0];
        String[] temp=mainAd.split("/");
        int siz=mainAd.length();
        int c=0;


        for (String ad:arr)
        {
            String rep="";
            String sb=ad.substring(siz);
            if(!sb.equals("") && sb.charAt(0)=='/')
                rep=output+"/"+temp[temp.length-1] +sb;
            else if(!sb.equals("") && sb.charAt(0)!='/')
                rep=output+"/"+temp[temp.length-1]+"/" +sb;
            else
                rep=output+"/"+temp[temp.length-1];
            arr[c]=rep;
            File prevF=new File(ad);
            File f=new File(rep);
            System.out.println("rep:"+rep);
            if (!f.exists() && prevF.exists() && prevF.isDirectory() ){
                f.mkdirs();
            }
            if(!prevF.isDirectory())
            {
                ans.add(rep);
            }


            c++;
        }
        return ans;

    }


    public static void main(String[] args){

    }



}
