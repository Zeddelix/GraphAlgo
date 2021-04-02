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

    //OUTPUT
    @Override
    public String toString() {
        return Integer.toString(weight);
    }
}
