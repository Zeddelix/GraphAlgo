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
        return info.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + key;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SUMMIT other = (SUMMIT) obj;
		
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (key != other.key)
			return false;
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
    
	
    

}
