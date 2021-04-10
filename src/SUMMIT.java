public class SUMMIT {
    private int key;
    private static int autoCount = 0;
    private Object info;

    //CONSTRUCTORS
    public SUMMIT(Object info) {
        ++autoCount;
        key = autoCount;
        this.info = info;
    }

    public SUMMIT(String s) {
        ++autoCount;
        key = autoCount;
        this.info = s;
    }

    public SUMMIT() {
        this(null);
    }

    //GETTERS
    public int getKey() {
        return key;
    }

    public Object getInfo() {
        return info;
    }

    //OUTPUT
    @Override
    public String toString() {
        String s="(";
        if (info ==null) s+=String.valueOf(key);
        else s+= info.toString();
        s+=")";
        return s;
    }




}