import java.util.ArrayList;
import java.util.List;

public class GRAPH  {
    private List<SUMMIT> summits;
    private List<BRIDGE> bridges;
    private boolean oriented, valued;

    //CONSTRUCTORS
    public GRAPH (List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) {
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = valued;
    }
    public GRAPH (List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges) {
        this(summits, oriented, bridges, true);
    }
    public GRAPH (List<SUMMIT> summits, List<BRIDGE> bridges, boolean valued) {
        this(summits, true, bridges,  valued);
    }
    public GRAPH (List<SUMMIT> summits, List<BRIDGE> bridges) {
        this(summits, true, bridges, true);
    }
    public GRAPH () {
        this(null, true, null,true);
    }
    public GRAPH (double []fs, double []aps) {
        //Transformation des tableau fs aps en list de sommets et arcs
    }
    public GRAPH (double [][]adjacents) {
        //Transforamtion de la matrice d'adjacences en list de sommets et d'arcs
    }

    //GETTERS
    public List<SUMMIT> getSummits(){return summits;}
    public List<BRIDGE> getBridges(SUMMIT s) {
        List<BRIDGE> bridgeDeS = new ArrayList<>();
        for(int i=0; i<bridges.size();++i) {
            if(bridges.get(i).getFirstSummit()==s ||bridges.get(i).getSecondSummit()==s)
                bridgeDeS.add(bridges.get(i));
        }
        return bridgeDeS;
    }

    public int numberOfSummit() {
        return summits.size();
    }

    //ADD&REMOVE
    public void addSummit(SUMMIT s) {
        boolean exist = false;
        for (int i = 0; i < summits.size(); ++i) {
            if (summits.get(i) == s)
                exist = true;
        }
        if (!exist) {
            summits.add(s);
        }
    }

    public void removeSummit(SUMMIT s) {
        summits.remove(s);
    }

    //METHODS
    public int distance(SUMMIT s1, SUMMIT s2) {
    	return 0;
    }

    public int[] ListSummitRank(){
    	return null;
    }

    public void tarjan() {

    }

    public void djikstra() {

    }

    public void Kruskal() {

    }

    //OUTPUT
    public void writeTheGraphInAFile(String[] args) {

    }

    public void display() {

    }

    public String ofString() {
        return "Summits:"+summits.toString()+"\nBridges:"+bridges.toString()+"\nOriented:"+oriented+"\nValued:"+valued;
    }
}

