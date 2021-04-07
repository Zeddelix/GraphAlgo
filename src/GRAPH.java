import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GRAPH  {
    private List<SUMMIT> summits;
    private List<BRIDGE> bridges;
    private boolean[][] Adj;
    private int[] Aps, Fs;
    private boolean oriented, valued;

    //CONSTRUCTORS
    public GRAPH (List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) {
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = valued;
        int Nb = summits.size();
        int fsSize = 0;
        int count = 1;
    	boolean[][] adj = new boolean[Nb][Nb];
    	for (BRIDGE b : bridges ) {
    			adj[b.getFirstSummit().getKey()][b.getSecondSummit().getKey()] = true;
    			fsSize++;
    	}

    	this.Adj=adj;
    	
    	int[] Aps = new int[Nb];
    	int[] Fs = new int[fsSize];
    	Aps[0]= Nb+1;
    	Fs[0] = fsSize+1;
    	Fs[1]=1;
    	
    	for (int i=0; i<Nb; i++) {
    		Fs[i]= count;
    		for (int j=0; j<Nb; j++) {
    			if (adj[i][j]==true) {
    				Fs[count]=j;
    				count++;
    			}
    			Fs[count]=0;
    			count++;
    		}
    	}
    	
    	this.Fs=Fs;
    	this.Aps=Aps;
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
        this.summits=null;
        this.Adj=null;
        this.Aps=null;
        this.Fs=null;
        this.bridges=null;
        this.oriented=false;
        this.valued=false;
    }

    public GRAPH (boolean [][]adjacents, boolean oriented) {
    	
    	this.Adj=adjacents;
    	
        List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();
        for (int i = 0; i<adjacents.length; i++) { // Ajout des sommets dans la liste
        	SUMMIT s = new SUMMIT();
        	summits.add(s);
        }
        for (int i = 0; i<adjacents.length; i++) { // Ajout des liens. Doit �tre s�par� puisqu'il nous faut d'abord la totalit� des sommets.
        	for (int j=0 ; j<adjacents.length; j++) {
        		if (adjacents[i][j]==true) {
        			BRIDGE b = new BRIDGE(summits.get(i), summits.get(j));
        			bridges.add(b);
        		}
        	}
        }
        this.summits=summits;
        this.bridges=bridges;
        this.oriented=oriented;
        this.valued=false;
        
    }
    
    public GRAPH (int[] Fs, boolean oriented, int[] Aps, boolean valued) {
    	this.Aps=Aps;
    	this.Fs=Fs;
    	List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();
    	
    	for (int i=1; i<Aps.length; i++) {
    		SUMMIT s = new SUMMIT();
    		summits.add(s);
    	}
    	
    	for (int i=1 ; i<Fs.length; i++) {
    			if (Fs[i]!=0) {
    				BRIDGE b = new BRIDGE(summits.get(i), summits.get(Fs[i]) );
    				bridges.add(b);
    		}
    	}
    	this.summits=summits;
    	this.bridges=bridges;
    	this.oriented=oriented;
    	this.valued=valued;
    }

    public GRAPH (int []fs, int []aps) {
        this(fs,1,aps,1);
        //Transformation des tableau fs aps en list de sommets et arcs
    }
    
    public GRAPH (int[] Fs, int[] Aps, boolean valued) {
    	this(Fs, true, Aps, valued);
    }
    
    public GRAPH (int[] Fs, boolean oriented, int[] Aps) {
    	this(Fs, oriented, Aps, true);
    }
    
    public GRAPH (int[] Fs, int[] Aps) {
    	this(Fs, true, Aps, true);
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
    
    public int[] getAps() {
    	return Aps;
    }
    
    public int[] getFs() {
    	return Fs;
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
        //Déclaration des variables utilisées
        List<Integer> NUM = new ArrayList<Integer>(), MU = new ArrayList<Integer>(), PREM = new ArrayList<Integer>(), PILCH = new ArrayList<Integer>(), CFC = new ArrayList<Integer>(), TARJ = new ArrayList<Integer>();
        List<Boolean> ENTARJ = new ArrayList<Boolean>();
        List<BRIDGE> bridge = bridges;
        int size = summits.size();

        //Initialisation de certaines List
        NUM.add(size);
        MU.add(size);
        PILCH.add(size);
        CFC.add(size);
        ENTARJ.add(false);
        for(int i = 1; i < size; ++i) {
            NUM.add(0);
            MU.add(0);
            PILCH.add(0);
            CFC.add(0);
            ENTARJ.add(false);
        }

        int sommet = summits.get(0).getKey(); //Sommet traité
        //Boucle de traitement
        for(int i = 0; i < size-1; ++i) {

            //Actualisation des Lists
            NUM.set(sommet, i + 1);
            ENTARJ.set(sommet, true);
            TARJ.add(sommet);
            if (TARJ.size()>1)
                PILCH.set(sommet, TARJ.get(TARJ.size()-1));
            else
                PILCH.set(sommet,0);

            //Vérification d'un lien suivant
            int j = 0;
            while(bridge.get(j).getFirstSummit() == sommet && ENTARJ.get(bridge.get(j).getSecondSummit().getKey()) == true) { //tant qu'il y a des liens avec le sommet traité && que le lien a été traité
                ++j;
            }

            if(ENTARJ.get(bridge.get(j).getSecondSummit().getKey()) != true) { //Si on a trouvé un lien pas traité on le défini en sommet courant et on fait la boucle de traitement
                sommet = bridge.get(j).getSecondSummit().getKey();
            }else{ //Si on ne trouve pas de lien, on calcul MU
                MU.set(sommet,Math.min(Math.min( /*nouvelle numérotation du sommet*/NUM.get(sommet), /* */b), /*fronde&LTFC*/c ));
            }

        }



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

    @Override
    public String toString() {
        return "Summits:"+summits.toString()+"\nBridges:"+bridges.toString()+"\nOriented:"+oriented+"\nValued:"+valued;
    }
}

