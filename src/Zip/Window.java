package Zip;

public class Window {

    private static   int i=0;
    private static   int j=-1;
    private static   int w;


    public Window(int w) {
        this.w = w;
    }

    public static   int getI() {
        return i;
    }

    public static   int getJ() {
        return j;
    }

    public static void increaseW(int a){
        if(j+a-i+1>w)
        {
            j+=a;
            i=j-w+1;
        }
        else
        {
            j+=a;
        }

    }


}
