import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GRAPH  {
    private List<SUMMIT> summits;
    private List<BRIDGE> bridges;
    private boolean[][] Adj;
    private boolean oriented, valued;

    //CONSTRUCTORS
    public GRAPH (List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) {
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = valued;
        
        int Nb = summits.size();
    	boolean[][] adj = new boolean[Nb][Nb];
    	for (BRIDGE b : bridges ) {
    			adj[b.getFirstSummit().getKey()][b.getSecondSummit().getKey()] = true;
    	}
    	if ( this.oriented==false) {	// Si le graph est non orienté, la matrice est symetrique
    		for (int i=0 ; i<Nb ; i++) {
    			for (int j=0 ; j<Nb ; j++) {
    				if (adj[i][j] == true) adj[j][i]=true;
    			}
    		}
    	}
    	
    	this.Adj=adj;
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
    public GRAPH (boolean [][]adjacents, boolean oriented) {
        int numberOfSummit = adjacents.length;
        List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();
        for (int i = 0; i<adjacents.length; i++) { // Ajout des sommets dans la liste
        	SUMMIT s = new SUMMIT();
        	summits.add(s);
        }
        for (int i = 0; i<adjacents.length; i++) { // Ajout des liens. Doit être séparé puisqu'il nous faut d'abord la totalité des sommets.
        	for (int j=0 ; j<adjacents.length; j++) {
        		if (adjacents[i][j]==true) {
        			BRIDGE b = new BRIDGE(summits.get(i), summits.get(j));
        		}
        	}
        }
        this.summits=summits;
        this.bridges=bridges;
        this.oriented=oriented;
        this.valued=false;
        
    }

    //GETTERS
    public List<SUMMIT> getSummits(){return summits;}
    
    public SUMMIT getSpecificSummits(int i){return summits.get(i);}
    
    public List<BRIDGE> getBridges(){
    	return bridges;
    }
    
    public List<BRIDGE> getSummitBridges(SUMMIT s) {
        List<BRIDGE> bridgesOfS = new ArrayList<>();
        for(int i=0; i<bridges.size();++i) {
            if(bridges.get(i).getFirstSummit().equals(s) ||bridges.get(i).getSecondSummit().equals(s))
                bridgesOfS.add(bridges.get(i));
        }
        return bridgesOfS;
    }

    public int numberOfSummit() {
        return summits.size();
    }
    
    public boolean isOriented() {
    	return oriented;
    }
    
    public boolean isValued() {
    	return valued;
    }
    
    public boolean[][] getAdjacent() {
    	return Adj;
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
    
    public void addBridge(BRIDGE b) {
        if (!bridges.contains(b)) bridges.add(b);
        else System.out.println("Ce lien existe deja");
    }

    public void removeBridge(BRIDGE b) {
    	 if (bridges.contains(b)) bridges.remove(b);
    	 else System.out.println("Ce lien n'existe pas");
        
    }

    //METHODS
    public int distance(SUMMIT s1) {
    	return 0;
    }

    public int[] ListSummitRank(){
    	return null;
    }
    
    public void distanceArray() {
    	
    }

    public void tarjan() {

    }

    public void djikstra() {

    }

    public void Kruskal() {

    }
    
    public void Dantzig() {

    }
    
    public void Prufer() {
    	
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

