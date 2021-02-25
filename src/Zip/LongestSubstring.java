package Zip;
import java.util.Arrays;
import java.util.Collections;

public class LongestSubstring
{

    public static class Suffix implements Comparable<Suffix>
    {
        int index;
        int rank;
        int next;

        public Suffix(int ind, int r, int nr)
        {
            index = ind;
            rank = r;
            next = nr;
        }

        public int compareTo(Suffix s)
        {
            if (rank != s.rank) return Integer.compare(rank, s.rank);
            return Integer.compare(next, s.next);
        }

    }

    public static int[] suffixArray(String s)
    {
        int n = s.length();
        Suffix[] su = new Suffix[n];


        for (int i = 0; i < n; i++)
        {
            su[i] = new Suffix(i, s.charAt(i) - '$', 0);
        }
        for (int i = 0; i < n; i++)
            su[i].next = (i + 1 < n ? su[i + 1].rank : -1);
        Arrays.sort(su);
        int[] ind = new int[n];
        for (int length = 4; length < 2 * n; length <<= 1)
        {
            int rank = 0, prev = su[0].rank;
            su[0].rank = rank;
            ind[su[0].index] = 0;
            for (int i = 1; i < n; i++)
            {
                if (su[i].rank == prev &&
                        su[i].next == su[i - 1].next)
                {
                    prev = su[i].rank;
                    su[i].rank = rank;
                }
                else
                {
                    prev = su[i].rank;
                    su[i].rank = ++rank;
                }
                ind[su[i].index] = i;
            }
            for (int i = 0; i < n; i++)
            {
                int nextP = su[i].index + length / 2;
                su[i].next = nextP < n ?
                        su[ind[nextP]].rank : -1;
            }
            Arrays.sort(su);
        }
        int[] suf = new int[n];

        for (int i = 0; i < n; i++)
            suf[i] = su[i].index;
        return suf;
    }

    static void changeArr(int[] suff,int[] lcp,int begin2){
        for (int i=0;i<suff.length-1;i++)
        {
            int num1=suff[i];
            int num2=suff[i+1];
            if(!((num1<begin2 && num2==begin2) || (num2<begin2 && num1==begin2)))
            {
                lcp[i]=0;
            }
        }
    }

    static int[] findindex(int[] suff,int[] lcp,int begin2) {
        int[] arr=new int[2];
        changeArr(suff,lcp,begin2);
        int maxIndex =getIndexOfLargest(lcp) ;
        if(lcp[maxIndex]==0) {
            arr[0]=-1;
            arr[1]=0;
            return arr;
        }
        else
        {
            if(suff[maxIndex]<suff[maxIndex+1]) {
                arr[0]=suff[maxIndex];
                arr[1]=lcp[maxIndex];
                return arr;
            }
            else {
                arr[0]=suff[maxIndex+1];
                arr[1]=lcp[maxIndex];
                return arr;
            }
        }

    }

    public static int getIndexOfLargest( int[] array )
    {
        if ( array == null || array.length == 0 )
            return -1;
        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest;
    }


    static int[] kasai(String txt, int[] suffixArr)
    {
        int n = suffixArr.length;

        int[] lcp=new int[n];

        int[] invSuff=new int[n];

        for (int i=0; i < n; i++)
            invSuff[suffixArr[i]] = i;
        int k = 0;

        for (int i=0; i<n; i++)
        {
            if (invSuff[i] == n-1)
            {
                k = 0;
                continue;
            }
            int j = suffixArr[invSuff[i]+1];

            while (i+k<n && j+k<n && txt.charAt(i+k)==txt.charAt(j+k))
                k++;

            lcp[invSuff[i]] = k;

            if (k>0)
                k--;
        }

        return lcp;
    }

    public static int[] getTheLongestSubstring(String str1,String str2){
        int[] arr=new int[2];
        String txt=str1+"0"+str2;
        int n = str1.length();
        int[] suff_arr = suffixArray(txt);
        int[] lcp=(kasai(txt,suff_arr));
        arr=findindex(suff_arr,lcp,n+1);
        return arr;
    }


    public static void main(String[] args)
    {
        getTheLongestSubstring("ababa","cccccbabaeth");

    }
}
