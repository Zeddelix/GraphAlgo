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
    
    public SUMMIT getSpecificSummits(int indice){return summits.get(indice);}

    public SUMMIT getSpecificSummitByKey(int key) {
        for(SUMMIT s:summits) {
            if (s.getKey() == key)
                return s;
        }
        return null;
    }
    
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

    public int[][]matDist(){
        return null;
    }

    public int[] ListSummitRank(){
    	return null;
    }
    
    public void distanceArray() {
    	
    }

    //TARJAN
    private boolean allBridgesProcessed(List<Boolean> processed) {
        for(boolean proc: processed) {
            if(proc == false)
                return false;
        }
        return true;
    }

    private int nextBridge(int summit, List<Boolean>traite, List<Integer>NUM) {
        int i = 0;
        while(bridges.get(i).getFirstSummit().getKey() != summits.get(summit).getKey()) { ++i;}// On va jusqu'au bridges concernant le sommet traité
        while(bridges.get(i).getFirstSummit().getKey() == summits.get(summit).getKey() && traite.get(i) == true && NUM.get(bridges.get(i).getSecondSummit().getKey()) == 0) {
            ++i;
        }

        if(bridges.get(i).getFirstSummit().getKey() == summits.get(summit).getKey())//On est sorti de la boucle en trouvant un lien non traité
            return i;
        return -1; //On est à la fin des liens du sommet sans en trouvé un non traité
    }

    private int minFrondeLTFC(int summit, List<Integer>NUM) {
        int min = Integer.MIN_VALUE, i = 0;
        while(bridges.get(i).getFirstSummit().getKey() != summits.get(summit).getKey()) { ++i;}// On va jusqu'au bridges concernant le sommet traité
        while(bridges.get(i).getFirstSummit().getKey() == summits.get(summit).getKey() && bridges.get(i).getSecondSummit().getKey() < summit && NUM.get(bridges.get(i).getSecondSummit().getKey()) != 0) {
            if(min > NUM.get(bridges.get(i).getSecondSummit().getKey())) {
                min = NUM.get(bridges.get(i).getSecondSummit().getKey());
            }
        }
        return min;
    }

    private boolean inPrem(int summit, List<Integer> PREM) {
        for(int i = 1; i < PREM.get(0)+1; ++i) {
            if(PREM.get(i) == summit)
                return true;
        }
        return false;
    }

    public GRAPH tarjan() {
        //Déclaration des variables utilisées
        List<Integer> NUM = new ArrayList<Integer>(), MU = new ArrayList<Integer>(), PREM = new ArrayList<Integer>(), PILCH = new ArrayList<Integer>(), CFC = new ArrayList<Integer>(), TARJ = new ArrayList<Integer>();
        List<Boolean> ENTARJ = new ArrayList<Boolean>(), traite = new ArrayList<Boolean>();

        int size = summits.size();
        //Initialisation de certaines List
        NUM.add(size);
        MU.add(size);
        PILCH.add(size);
        CFC.add(size);
        PREM.add(0);
        ENTARJ.add(false);
        for(int i = 1; i < size; ++i) {
            NUM.add(0);
            MU.add(Integer.MAX_VALUE);
            PILCH.add(0);
            CFC.add(0);
            ENTARJ.add(false);
        }
        for(int i =0; i<bridges.size()-1; ++i) {
            traite.add(false);
        }


        //Boucle de traitement pour remplir les tableaux
        int sommet = summits.get(0).getKey(); //Sommet traité
        int lien, nouveauSommet = 0, groupe =0;
        while(!allBridgesProcessed(traite)) {
            if ((lien = nextBridge(sommet, traite, NUM)) != -1) { //Si il y a 1 lien à traiter
                //MAJ des tableaux
                NUM.set(sommet, ++nouveauSommet);
                ENTARJ.set(sommet, true);
                TARJ.add(sommet);
                if (TARJ.size()>1)
                    PILCH.set(sommet, TARJ.get(TARJ.size()-2));
                else
                    PILCH.set(sommet,0);

                //Marqué le lien comme traité
                traite.set(lien, true);

                //Passe au sommet suivant
                sommet = bridges.get(lien).getSecondSummit().getKey();
            }else{
                //Recherche du plus petit mu des sucesseurs direct
                int pps = Integer.MAX_VALUE;
                for(BRIDGE b:bridges) {
                    if(b.getFirstSummit().getKey() == sommet && pps>MU.get(b.getSecondSummit().getKey())) {
                        pps = MU.get(b.getSecondSummit().getKey());
                    }
                }

                //Recherche du plus petit mu des frondes&LTFC


                //Calcul de MU
                MU.set(sommet,Math.min(Math.min(/*nouvelle numérotation du sommet*/NUM.get(sommet), /*MU des sucesseurs */pps), /*fronde&LTFC*/minFrondeLTFC(sommet, NUM)));

                if(MU.get(sommet) == NUM.get(sommet)) {
                    //Remplir PREM & CFC, Update TARJ & ENTARJ
                    PREM.add(TARJ.get(TARJ.size()-1));
                    PREM.set(0,PREM.get(0)+1);
                    ++groupe;
                    for(int i = TARJ.size()-1; i>= 0; --i) {
                        if(MU.get(TARJ.get(i)) != Integer.MAX_VALUE) {
                            CFC.set(TARJ.get(i), groupe);
                            ENTARJ.set(TARJ.get(i), false);
                            TARJ.remove(TARJ.get(i));
                        }
                    }

                }else {
                    //revenir sur le sommet précédent
                    int i = TARJ.size()-1;
                    while(TARJ.get(i) != sommet) {
                        --i;
                    }
                    sommet = TARJ.get(i-1);

                }
            }
        }

        //Creation des nouveaux sommets
        List<SUMMIT> newSummits = new ArrayList<SUMMIT>();
        SUMMIT s;
        for(int i = 1; i<=PREM.get(0)+1; ++i) {
            sommet = PREM.get(i);
            s = new SUMMIT(" "+Integer.toString(sommet)+" ");
            newSummits.add(s);
            int next = PILCH.get(sommet);
            while(next != 0 && !inPrem(next, PREM)) {
                newSummits.get(newSummits.size()-1).setInfo(newSummits.get(newSummits.size()-1).getInfo()+Integer.toString(next)+" ");
                next = PILCH.get(next);
            }
        }

        List<BRIDGE> newBridges = new ArrayList<BRIDGE>();
        int s1, s2;
        boolean exist;
        for (BRIDGE b: bridges) {
            s1=CFC.get(b.getFirstSummit().getKey());
            s2=CFC.get(b.getSecondSummit().getKey());
            if(s1 != s2) {
                exist=false;
                int i = 0;
                while(i < newBridges.size()-1 & !exist) {
                    if(newBridges.get(i).getFirstSummit().getKey()==s1 && newBridges.get(i).getSecondSummit().getKey()==s2)
                        exist = true;
                    else
                        ++i;
                }
                if(!exist) {
                    BRIDGE bridge = new BRIDGE(/*s1*/b.getFirstSummit(), /*s2*/b.getSecondSummit());
                    newBridges.add(bridge);
                }
            }
        }

        //création du graph
        GRAPH g = new GRAPH(newSummits, true, newBridges, false);

        return g;
    }

    public void djikstra() {

    }

    public void Kruskal(GRAPH reduit) {

        int n = summits.size();
        int[] prem = new int[n+1];
        int[] pilch = new int[n+1];
        int[] cfc= new int[n+1];
        int[] nbElem= new int[n+1];

        for(int i = 1;i <= n;i++){
            prem[i] = i;
            pilch[i] = 0;
            cfc[i] = i;
            nbElem[i] = i;
        }

        //Trie des arêtes
        int p;
        for(int i = 0; i < bridges.size()-1;i++) {
            for (int j = i + 1; j < bridges.size(); j++) {
                if (bridges.get(j).getWeight() < bridges.get(i).getWeight() ||
                        (bridges.get(j).getWeight() == bridges.get(j).getWeight() && bridges.get(j).getFirstSummit().getKey() < bridges.get(i).getSecondSummit().getKey()) ||
                        (bridges.get(j).getWeight() == bridges.get(i).getWeight() && bridges.get(j).getSecondSummit().getKey() < bridges.get(i).getSecondSummit().getKey())) {
                    p = bridges.get(j).getWeight();
                    bridges.get(j).setWeight(bridges.get(j).getWeight());
                    bridges.get(i).setWeight(p);
                }
            }
        }

        //kruskal
        reduit.bridges.clear();
        int x;
        int y;
        int i = 0, j =0;
        while (j < n-1) {
            BRIDGE ar = bridges.get(i);
            x = cfc[ar.getFirstSummit().getKey()];
            y = cfc[ar.getSecondSummit().getKey()];
            if (x != y) {
                reduit.bridges.add(bridges.get(i));
                j++;
                /////////////// fusionner////////////////
                if (nbElem[i] < nbElem[j]) {
                    int aux = i;
                    i = j;
                    j = aux;
                }
                int s = prem[j];
                cfc[s] = i;
                while (pilch[s] != 0) {
                    s = pilch[s];
                    cfc[s] = i;
                }
                pilch[s] = prem[i];
                prem[i] = prem[j];
                nbElem[i] += nbElem[j];
            }
            i++;
        }
    }


    public int[][] Dantzig() {

        int [][] gr = matDist();
        int n = summits.size();
        int[][] mat = new int[n][n];
        for (int i = 1;i <= n ;i++) {
            for (int j = 1; i <= n; i++) {
                if (i == j) {
                    mat[i][i] = 0;
                } else {
                    mat[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for(int k = 1 ; k <= n;k++) {
            for (int i = 1; i < k; i++) {
                int min1 = 0, min2 = 0;
                for (int j = k; j >= 1; j--) {
                    int valMin1 = gr[k + 1][j] + mat[j][i];
                    int valMin2 = gr[i][j] + mat[j][k + 1];
                    if (valMin1 < min1) {
                        min1 = valMin1;
                    }
                    if (valMin2 < min2) {
                        min2 = valMin2;
                    }
                }
                mat[k + 1][i] = min1;
                mat[i][k + 1] = min2;
            }
            int t=0;
            for (int j = k; j >= 1; j--) {
                t = mat[k + 1][j] + mat[j][k + 1];
            }

            if (t < 0) {
                break;
            } else {
                for(int i = 1;i <= k;i++) {
                    for(int j = 1;j <= k;j++) {
                        if (mat[i][j] > (mat[i][k+1] + mat[k+1][j])) {
                            mat[i][j] = mat[i][k + 1] + mat[k + 1][j];
                        }
                    }
                }
            }
        }

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

