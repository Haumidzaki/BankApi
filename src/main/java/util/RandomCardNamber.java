package util;

public class RandomCardNamber {

    private RandomCardNamber() {
    }

    public static String getNamber(){
        String res = String.format("%d-%d-%d-%d",
                (0 + (long) (Math.random() * 10000)),
                (0 + (long) (Math.random() * 10000)),
                (0 + (long) (Math.random() * 10000)),
                (0 + (long) (Math.random() * 10000)));
        return res;
    }
}
