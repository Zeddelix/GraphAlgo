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




    //GETTERS & SETTERS
    public int getKey() {
        return key;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    //OUTPUT
    @Override
    public String toString() {
        String s="(";
        s+=String.valueOf(key);
        s+=")";
        return s;
    }




}
