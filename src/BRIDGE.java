import java.util.ArrayList;

public class BRIDGE {
    private int weight;
    private SUMMIT summit1, summit2;

    //CONSTRUCTORS
    public BRIDGE(SUMMIT summit1, SUMMIT summit2, int weight) {
        this.summit1 = summit1;
        this.summit2 = summit2;
        this.weight = weight;
    }

    public BRIDGE(SUMMIT summit1, SUMMIT summit2) {
        this(summit1,summit2,1);
    }

    //GETTERS & SETTERS
    public SUMMIT getFirstSummit() {return summit1;}
    public SUMMIT getSecondSummit() {return summit2;}
    public int getWeight() {return weight;}

    public void setFirstSummit(SUMMIT summit) {
        this.summit1 = summit;
    }
    public void setSecondSummit(SUMMIT summit) {
        this.summit2 = summit;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean contains(SUMMIT summit){
        return this.summit2 == summit || this.summit1 == summit;
    }

    public boolean isLeafWithDeleteSummit(ArrayList<Integer> deleted){
        if(this.isLeaf())
            return false;

        if(deleted.contains(this.summit1.getKey()) && deleted.contains(this.summit2.getKey()))
            return false;

        return deleted.contains(this.summit1.getKey()) && this.summit2 != null || deleted.contains(this.summit2.getKey()) && this.summit1 != null;

    }


    public boolean isLeaf(){
        return this.getFirstSummit() == null || this.getSecondSummit() == null;
    }

    //OUTPUT
    @Override
    public String toString() {
        return summit1+"---"+weight+"---"+summit2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((summit1 == null) ? 0 : summit1.hashCode());
        result = prime * result + ((summit2 == null) ? 0 : summit2.hashCode());
        result = prime * result + weight;
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
        BRIDGE other = (BRIDGE) obj;
        if (summit1 == null) {
            if (other.summit1 != null)
                return false;
        } else if (!summit1.equals(other.summit1))
            return false;
        if (summit2 == null) {
            if (other.summit2 != null)
                return false;
        } else if (!summit2.equals(other.summit2))
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }



}
