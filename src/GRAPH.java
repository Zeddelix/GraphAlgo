import java.util.*;

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
        boolean[][] adj = new boolean[Nb+1][Nb+1];
        for (BRIDGE b : bridges ) {
            adj[b.getFirstSummit().getKey()][b.getSecondSummit().getKey()] = true;
            if (oriented==false) {
                adj[b.getSecondSummit().getKey()][b.getFirstSummit().getKey()] = true;
                fsSize++;
            }
            fsSize++;
        }
        for (SUMMIT s : summits){
            fsSize++;
        }

        this.Adj=adj;

        int[] Aps = new int[Nb+1];
        int[] Fs = new int[fsSize+1];
        Aps[0]= Nb+1;
        Fs[0] = fsSize+1;
        Aps[1]=1;

        for (int i=1; i< adj.length; i++){
            Aps[i]=count;
            for (int j=1; j<adj[i].length;j++){
                if (adj[i][j]==true){
                    Fs[count]=j;
                    count++;
                }
            }
            Fs[count]=0;
            count++;
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
        for (int i = 1; i<adjacents.length; i++) { // Ajout des sommets dans la liste
            SUMMIT s = new SUMMIT();
            summits.add(s);
        }
        for (int i = 1; i<adjacents.length; i++) { // Ajout des liens. Doit �tre s�par� puisqu'il nous faut d'abord la totalit� des sommets.
            for (int j=1 ; j<adjacents.length; j++) {
                if (adjacents[i][j]==true) {
                    BRIDGE b = new BRIDGE(summits.get(j-1), summits.get(i-1));
                    bridges.add(b);
                    if (oriented==false) {
                        b = new BRIDGE(summits.get(i-1), summits.get(j-1));
                        if (!bridges.contains(b)) bridges.add(b);
                    }
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
        int count=1;
        List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();

        for (int i=1; i<Aps.length; i++) {
            SUMMIT s = new SUMMIT();
            summits.add(s);
        }

        for (int i=1 ; i<Fs.length; i++) {
            if (Fs[i]!=0) {
                BRIDGE b = new BRIDGE(summits.get(count-1), summits.get(Fs[i]-1) );
                bridges.add(b);
            }
            else count++;
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



    public GRAPH pruferToGraph(int[] prufer){
        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> S = new ArrayList<>();
        ArrayList<SUMMIT> summits = new ArrayList<>();
        ArrayList<BRIDGE> bridges = new ArrayList<>();

        for(int i : prufer)
            S.add(i);

        for (int i = 1; i <= prufer.length; ++i) {
            summits.add(new SUMMIT());
            L.add(i);
        }

        for(int i  = 0; i < S.size()-1; ++i){
            int j = i+1;
            while (j < L.size() && S.contains(L.get(j)))
                ++j;
            bridges.add(new BRIDGE(summits.get(i), summits.get(j)));
            L.remove(j);
            S.remove(i);
        }

        return new GRAPH(summits, false, bridges);
    }

    public int smallestValue(ArrayList<Integer> list){
        int smallest = 0;
        for (int i = 1; i < list.size(); ++i){
            if(smallest > list.get(i))
                smallest = i;
        }
        return smallest;
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
        while(bridges.get(i).getFirstSummit().getKey() != summit && i < bridges.size()-1) { ++i;}// On va jusqu'au bridges concernant le sommet traité
        while(bridges.get(i).getFirstSummit().getKey() == summit && traite.get(i) == true && NUM.get(bridges.get(i).getSecondSummit().getKey()) == 0) {
            ++i;
        }

        if(bridges.get(i).getFirstSummit().getKey() == summit)//On est sorti de la boucle en trouvant un lien non traité
            return i;
        return -1; //On est à la fin des liens du sommet sans en trouvé un non traité
    }

    private int minFrondeLTFC(int summit, List<Integer>NUM) {
        int min = Integer.MIN_VALUE, i = 0;
        while(i< bridges.size() && bridges.get(i).getFirstSummit().getKey() != summits.get(summit).getKey()) { ++i;}// On va jusqu'au bridges concernant le sommet traité
        while(i< bridges.size() && bridges.get(i).getFirstSummit().getKey() == summits.get(summit).getKey() && bridges.get(i).getSecondSummit().getKey() < summit && NUM.get(bridges.get(i).getSecondSummit().getKey()) != 0) {
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

    private int prochainSommet(List<Integer> NUM) {
        for(int i = 0; i<NUM.size(); ++i) {
            if(NUM.get(i) == 0)
                return i;
        }
        return -1;
    }

    List<Integer> NUM = new ArrayList<Integer>(), MU = new ArrayList<Integer>(), PREM = new ArrayList<Integer>(), PILCH = new ArrayList<Integer>(), CFC = new ArrayList<Integer>(), TARJ = new ArrayList<Integer>();
    List<Boolean> ENTARJ = new ArrayList<Boolean>(), traite = new ArrayList<Boolean>();

    public GRAPH tarjan() {

        //Déclaration des variables utilisées

        int size = summits.size()+1;
        //Initialisation de certaines List
        NUM.add(size);
        MU.add(size);
        PILCH.add(size);
        CFC.add(size);
        PREM.add(0);
        ENTARJ.add(false);
        for(int i = 1; i < size; ++i) {
            NUM.add(0);
            MU.add(2147483647);
            PILCH.add(0);
            CFC.add(0);
            ENTARJ.add(false);
        }
        for(int i =0; i<bridges.size(); ++i) {
            traite.add(false);
        }


        //Boucle de traitement pour remplir les tableaux
        int sommet = summits.get(0).getKey(); //Sommet traité
        int lien, nouveauSommet = 0, groupe =0;
        while(!allBridgesProcessed(traite) && sommet!=-1) {
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
                System.out.println(sommet);
            }else{
                //Recherche du plus petit mu des sucesseurs direct
                int pps = Integer.MAX_VALUE;
                for(BRIDGE b:bridges) {
                    //System.out.println(MU.size());
                    //System.out.println(b.getSecondSummit().getKey());
                    if(b.getFirstSummit().getKey() == sommet && pps>MU.get(b.getSecondSummit().getKey())) {
                        pps = MU.get(b.getSecondSummit().getKey());
                    }
                }

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
                    //passer au sommet suivant non traité
                    sommet = prochainSommet(NUM);
                    System.out.println(sommet);

                }else {
                    //revenir sur le sommet précédent
                    int i = TARJ.size();
                    if(i != 0) {
                        while (TARJ.get(i) != sommet) {
                            --i;
                        }
                        sommet = TARJ.get(i - 1);
                        System.out.println(sommet);
                    }
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
                //newSummits.get(newSummits.size()-1).setInfo(newSummits.get(newSummits.size()-1).getInfo()+Integer.toString(next)+" ");
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

    private ArrayList<Integer> distances = new ArrayList<>();
    private ArrayList<Integer> pred = new ArrayList<>();
    private ArrayList<SUMMIT> uncheckSummits = new ArrayList<>();
    private ArrayList<SUMMIT> checkedSummits = new ArrayList<>();

    /*public GRAPH djikstra() {
        return this.djikstra(this.getSpecificSummits(0));
    }
    public GRAPH djikstra(int summit) {
        return this.djikstra(this.getSpecificSummits(summit));
    }*/
    public GRAPH djikstra(SUMMIT origin) {
        for (BRIDGE bridge : this.bridges) {
            if (bridge.getWeight() < 0) {
                throw new RuntimeException("djikstra can't use negative values.");
            }
        }
        for (int i = 0; i <= this.getSummits().size(); ++i){
            this.distances.add(Integer.MAX_VALUE);
            this.pred.add(0);
        }

        this.distances.set(origin.getKey(), 0);
        this.pred.set(origin.getKey(),0);

        while (checkedSummits.size()<summits.size()){
            for (BRIDGE b : bridges){
                if (b.getFirstSummit()==origin) uncheckSummits.add(b.getSecondSummit()); // Liste les successeurs du sommet
            }
            for (SUMMIT s : uncheckSummits){
                for (BRIDGE b : bridges){
                    if (b.getFirstSummit()==origin && b.getSecondSummit()==s){
                        if (distances.get(s.getKey()) > distances.get(origin.getKey())+b.getWeight() ){
                            distances.set(s.getKey(), distances.get(origin.getKey())+b.getWeight());
                            pred.set(s.getKey(),origin.getKey());
                        }
                    }
                }

            }
            checkedSummits.add(origin);
            int mini=1;
            for (SUMMIT s : summits){
                if (!checkedSummits.contains(s)) mini=s.getKey();
            }
            for (int i=2; i<distances.size()-1;i++){
                if(distances.get(mini)>distances.get(i) && !checkedSummits.contains(summits.get(i-1))){
                    mini=i;
                }

            }
            origin=summits.get(mini-1);
        }


        /*this.uncheckSummits.add(departure);
        while (this.uncheckSummits.size() > 0){
            SUMMIT summit = this.getMinimum();
            this.checkedSummits.add(summit);
            this.uncheckSummits.remove(summit);
            minimalDistances(summit);
        }*/


        //retour duchemin le plus court en graph
        /*List<SUMMIT> newSummits = summits;
        List<BRIDGE> newBriges = new ArrayList<>();
        for(SUMMIT s: summits) {
            if(s!=path.get(s))
                newBriges.add( new BRIDGE( s,path.get(s),( distances.get( path.get(s).getKey())-(distances.get(s.getKey())))));
        }
        */
        List<BRIDGE> newBridges = new ArrayList<>();
        for (int i=2; i< pred.size()-1;i++){
            BRIDGE b = new BRIDGE(summits.get(pred.get(i)-1), summits.get(i-1),(distances.get(i)-distances.get(pred.get(i))));
            newBridges.add(b);
        }
        System.out.println(newBridges.toString());
        return new GRAPH(summits, this.oriented, newBridges, this.valued);
    }

    private void minimalDistances(SUMMIT summit) {
        for (BRIDGE target : this.getSummitBridges(summit)) {
            SUMMIT targetSummit;
            if(target.getFirstSummit() != summit)
                targetSummit = target.getFirstSummit();
            else
                targetSummit = target.getSecondSummit();

            if (getShortestPath(targetSummit) > getShortestPath(summit) + target.getWeight()) {
                this.distances.set(targetSummit.getKey(), getShortestPath(summit) + target.getWeight());
                this.pred.set(summit.getKey(), targetSummit.getKey());
                if(!checkedSummits.contains(targetSummit))
                    this.uncheckSummits.add(targetSummit);
            }
        }

    }

    private int getDistance(SUMMIT departure, SUMMIT arrival) {
        for (BRIDGE bridge : this.bridges) {
            if (bridge.getFirstSummit().equals(departure) && bridge.getFirstSummit().equals(arrival))
                return bridge.getWeight();
        }
        System.out.println("[LOG] Error, this situation shouldn't happen. " + departure.getKey() + " is not connected with " + arrival.getKey()+".");
        return Integer.MAX_VALUE;
    }

    private SUMMIT getMinimum() {
        SUMMIT minimum = summits.get(0);

        for (SUMMIT summit : this.summits) {
            if (getShortestPath(summit) < getShortestPath(minimum))
                minimum = summit;
        }
        return minimum;
    }

    private int getShortestPath(SUMMIT arrival) {
        return this.distances.get(arrival.getKey());
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
        return mat;

    }
    public ArrayList<Integer> toPruferCode(){
        ArrayList<Integer> prufer = new ArrayList<>();
        for (int i = 0; i < this.summits.size()-1; ++i)
            prufer.add(-1);

        ArrayList<Integer> deleted = new ArrayList<>();

        while (prufer.contains(-1)){
            SUMMIT s = this.summits.get(this.smallestLeaf(deleted));
            SUMMIT voisin = getNeighbour(s, deleted);
            prufer.add(voisin.getKey());
        }
        return prufer;
    }

    private SUMMIT getNeighbour(SUMMIT s, ArrayList<Integer> deleted){
        for(BRIDGE bridge : this.bridges){
            if(bridge.contains(s) && !deleted.contains(bridge.getFirstSummit().getKey()) && !deleted.contains(bridge.getSecondSummit().getKey())){
                SUMMIT voisin;
                if(bridge.getFirstSummit() == s)
                    voisin = bridge.getSecondSummit();
                else
                    voisin = bridge.getFirstSummit();
                return voisin;
            }
        }
        throw new RuntimeException("[LOG] Error getNeighbour");
    }

    private boolean isLeaf(SUMMIT summit){
        for (BRIDGE bridge : this.bridges) {
            if(bridge.getFirstSummit() == summit)
                return false;
        }
        return true;
    }

    public boolean isLeafWithDeleteSummit(ArrayList<Integer> deleted, SUMMIT summit){
        if(this.isLeaf(summit))
            return false;

        if(deleted.contains(summit))
            return false;

        for (BRIDGE bridge : this.bridges) {
            if(bridge.contains(summit)) {
                if(bridge.getFirstSummit() == summit && deleted.contains(bridge.getSecondSummit()))
                    return true;
            }
        }
        return false;

    }

    private int smallestLeaf(ArrayList<Integer> deleted){
        int min = Integer.MAX_VALUE;
        for (SUMMIT s : this.summits){
            if(this.isLeaf(s) || this.isLeafWithDeleteSummit(deleted, s) ){
                if(min > s.getKey() && !deleted.contains(s.getKey()))
                    min = s.getKey();
            }
        }
        return min;
    }


    void decodagePrufer(int[] d_tableauPrufer)
    {
        int size = this.summits.size()+2;
        ArrayList<Integer> aps = new ArrayList<>();
        ArrayList<Integer> fs = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            aps.add(0);
            fs.add(0);
        }

        ArrayList<Boolean> summitUnchecked = new ArrayList<>();
        for (int i = 0; i < size; ++i)
            summitUnchecked.add(true);



        ArrayList<Integer> iteration = new ArrayList<>();
        for (int i = 0; i < size; ++i)
            iteration.add(0);

        for(int i=1; i<=d_tableauPrufer[0]; i++)
            iteration.set(d_tableauPrufer[i], iteration.get(d_tableauPrufer[0])+1);

        for(int i=1; i<= d_tableauPrufer[0]; i++) {

            for(int j=1; j<= size; j++) {
                if((summitUnchecked.get(j)) && iteration.get(j)==0) {
                    summitUnchecked.set(j, false);
                    iteration.set(d_tableauPrufer[i], iteration.get(d_tableauPrufer[i]-1));
                    fs.add(j, d_tableauPrufer[i]);

                    break;
                }
            }

        }
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

