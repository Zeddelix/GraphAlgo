import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    public GRAPH (double []fs, double []aps) {
        //Transformation des tableau fs aps en list de sommets et arcs
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

    }


    private ArrayList<Integer> distances = new ArrayList<>();
    private HashMap<SUMMIT, SUMMIT> path = new HashMap<>();
    private ArrayList<SUMMIT> uncheckSummits = new ArrayList<>();
    private ArrayList<SUMMIT> checkedSummits = new ArrayList<>();
    public void djikstra() {
        this.djikstra(this.getSpecificSummits(0));
    }
    public void djikstra(int summit) {
        this.djikstra(this.getSpecificSummits(summit));
    }
    public void djikstra(SUMMIT origin) {
        for (BRIDGE bridge : this.bridges) {
            if (bridge.getWeight() < 0) {
                throw new RuntimeException("djikstra can't use negative values.");
            }
        }
        for (int i = 0; i < this.getSummits().size(); ++i)
            this.distances.add(Integer.MAX_VALUE);
        this.distances.set(origin.getKey(), 0);

        this.path.put(this.getSpecificSummits(origin.getKey()), this.getSpecificSummits(origin.getKey()));


        SUMMIT departure = this.getSpecificSummits(origin.getKey());

        this.uncheckSummits.add(departure);
        while (this.uncheckSummits.size() > 0){
            SUMMIT summit = this.getMinimum();
            this.checkedSummits.add(summit);
            this.uncheckSummits.remove(summit);
            minimalDistances(summit);
        }
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
                this.path.put(summit, targetSummit);
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
        SUMMIT minimum = null;

        for (SUMMIT summit : this.summits) {
            if (minimum != null) {
                if (getShortestPath(summit) < getShortestPath(minimum))
                    minimum = summit;
            }
            else minimum = summit;
        }
        return minimum;
    }

    private int getShortestPath(SUMMIT arrival) {
        return this.distances.get(arrival.getKey());
    }


    public void Kruskal() {

    }

    public void Dantzig() {

    }

    public void Prufer() {

    }



    static void printTreeEdges(ArrayList<Integer> prufer)
    {
        int size = prufer.size()+2;
        ArrayList<Integer> tree = new ArrayList<>(size);

        for (int i = 0; i < size; i++)
            tree.set(i, 0);

        for (int i = 0; i < size - 2; i++)
            tree.set(prufer.get(i) - 1, tree.get(prufer.get(i) - 1)+1);

        int j;
        for (int i = 0; i < size - 2; i++) {
            for (j = 0; j < size; j++) {
                if (tree.get(j) == 0) {
                    tree.set(j, -1);
                    tree.set(prufer.get(i)-1, tree.get(prufer.get(i)-1)-1);

                    break;
                }
            }
        }

        j = 0;
        for (int i = 0; i < size; i++) {
            if (tree.get(i) == 0 && j == 0) {
                j++;
            }
        }
    }

    static void pruferDfs(List<Integer>[] tree, int[] parent, int v)
    {
        for (int i = 0; i < tree[v].size(); ++i)
        {
            int to = tree[v].get(i);
            if (to != parent[v])
            {
                parent[to] = v;
                pruferDfs(tree, parent, to);
            }
        }
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


    private int smallestLeaf(ArrayList<Integer> deleted){
        int min = Integer.MAX_VALUE;
        for (BRIDGE bridge : this.bridges){
            if(bridge.isLeaf() || bridge.isLeafWithDeleteSummit(deleted) ){
                SUMMIT s;
                if(bridge.getSecondSummit() != null)
                    s = bridge.getSecondSummit();
                else
                    s = bridge.getFirstSummit();

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

    public String ofString() {
        return "Summits:"+summits.toString()+"\nBridges:"+bridges.toString()+"\nOriented:"+oriented+"\nValued:"+valued;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(Adj);
        result = prime * result + Arrays.hashCode(Aps);
        result = prime * result + Arrays.hashCode(Fs);
        result = prime * result + ((bridges == null) ? 0 : bridges.hashCode());
        result = prime * result + (oriented ? 1231 : 1237);
        result = prime * result + ((summits == null) ? 0 : summits.hashCode());
        result = prime * result + (valued ? 1231 : 1237);
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
        GRAPH other = (GRAPH) obj;
        if (!Arrays.deepEquals(Adj, other.Adj))
            return false;
        if (!Arrays.equals(Aps, other.Aps))
            return false;
        if (!Arrays.equals(Fs, other.Fs))
            return false;
        if (bridges == null) {
            if (other.bridges != null)
                return false;
        } else if (!bridges.equals(other.bridges))
            return false;
        if (oriented != other.oriented)
            return false;
        if (summits == null) {
            if (other.summits != null)
                return false;
        } else if (!summits.equals(other.summits))
            return false;
        if (valued != other.valued)
            return false;
        return true;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
}





