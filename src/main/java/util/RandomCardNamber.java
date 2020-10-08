package util;

public class RandomCardNamber {

    private RandomCardNamber() {
    }

    public static String getNamber(){
        String res = String.format("%d-%d-%d-%d",
                (1000 + (long) (Math.random() * 8999)),
                (1000 + (long) (Math.random() * 8999)),
                (1000 + (long) (Math.random() * 8999)),
                (1000 + (long) (Math.random() * 8999)));
        return res;
    }
}
