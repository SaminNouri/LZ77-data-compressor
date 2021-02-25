package Zip;

import java.util.BitSet;

public class BitConverter {


    public static byte[] reverse(byte[] a)
    {
        int n=a.length;
        byte[] b = new byte[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        return b;

    }

    public static byte[] BitSettoByteArray(BitSet bits,int dLength) {
        byte[] bytes = new byte[dLength];
        for (int i=0; i<bits.length(); i++) {
            if (i<bits.length() && bits.get(i)) {
                bytes[bytes.length-i/8-1] |= 1<<(i%8);
            }
        }
        for (int j= bits.length();j<dLength;j++){
            bytes[j]=0;
        }
        return bytes;
    }



    public static BitSet ByteArraytoBitSet(byte[] bytes) {
        bytes=reverse(bytes);
        BitSet ans=BitSet.valueOf(bytes);
        return ans;
    }


    public static BitSet intToBitSet(int value1,int value2, int length1,int lenght2) {
        BitSet bits = new BitSet(length1+lenght2);
        int index = 0;
        while (value1 != 0) {
            if (value1 % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value1 = value1 >>> 1;
        }
        index=length1;
        while (value2 != 0) {
            if (value2 % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value2 = value2 >>> 1;
        }
        System.out.println(bits);
        return bits;
    }

    public static int BitSettoInt1(BitSet bitSet, int lenght1) {
        int intValue1 = 0;
        for (int bit = 0; bit < lenght1; bit++) {
            if (bitSet.get(bit)) {
                intValue1 |= (1 << bit);
            }
        }
        //System.out.println(intValue1);
        return intValue1;
    }

    public static int BitSettoInt2(BitSet bitSet,int lenght1) {
        int intValue1 = 0;
        for (int bit = lenght1; bit < bitSet.length(); bit++) {
            if (bitSet.get(bit)) {
                intValue1 |= (1 << (bit-lenght1));
            }
        }
        // System.out.println(intValue1);
        return intValue1;
    }

    public static void main(String[] args)
    {
        BitSet b=intToBitSet(10,5,4,5);
        BitSettoInt2(b,4);
    }













}
