import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class GRAPH {
    private List<SUMMIT> summits;
    private List<BRIDGE> bridges;
    private boolean[][] Adj;
    private int[] Aps, Fs;
    private boolean oriented, valued;

    //CONSTRUCTORS
    public GRAPH(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) {
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = valued;
        int Nb = summits.size();
        int fsSize = 0;
        int count = 1;
        boolean[][] adj = new boolean[Nb + 1][Nb + 1];
        for (BRIDGE b : bridges) {
            adj[b.getFirstSummit().getKey()][b.getSecondSummit().getKey()] = true;
            if (oriented == false) {
                adj[b.getSecondSummit().getKey()][b.getFirstSummit().getKey()] = true;
                fsSize++;
            }
            fsSize++;
        }
        for (SUMMIT s : summits) {
            fsSize++;
        }

        this.Adj = adj;

        int[] Aps = new int[Nb + 1];
        int[] Fs = new int[fsSize + 1];
        Aps[0] = Nb + 1;
        Fs[0] = fsSize + 1;
        Aps[1] = 1;

        for (int i = 1; i < adj.length; i++) {
            Aps[i] = count;
            for (int j = 1; j < adj[i].length; j++) {
                if (adj[i][j] == true) {
                    Fs[count] = j;
                    count++;
                }
            }
            Fs[count] = 0;
            count++;
        }

        this.Fs = Fs;
        this.Aps = Aps;
    }

    public GRAPH(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges) {
        this(summits, oriented, bridges, true);
    }

    public GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges, boolean valued) {
        this(summits, true, bridges, valued);
    }

    public GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges) {
        this(summits, true, bridges, true);
    }

    public GRAPH() {
        this.summits = new ArrayList<SUMMIT>();
        this.Adj = null;
        this.Aps = null;
        this.Fs = null;
        this.bridges = new ArrayList<BRIDGE>();
        this.oriented = false;
        this.valued = false;
    }

    public GRAPH(boolean[][] adjacents, boolean oriented) {

        this.Adj = adjacents;

        List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();
        for (int i = 1; i < adjacents.length; i++) { // Ajout des sommets dans la liste
            SUMMIT s = new SUMMIT();
            summits.add(s);
        }
        for (int i = 1; i < adjacents.length; i++) { // Ajout des liens. Doit �tre s�par� puisqu'il nous faut d'abord la totalit� des sommets.
            for (int j = 1; j < adjacents.length; j++) {
                if (adjacents[i][j] == true) {
                    BRIDGE b = new BRIDGE(summits.get(j - 1), summits.get(i - 1));
                    bridges.add(b);
                    if (oriented == false) {
                        b = new BRIDGE(summits.get(i - 1), summits.get(j - 1));
                        if (!bridges.contains(b)) bridges.add(b);
                    }
                }
            }
        }
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = false;

    }

    public GRAPH(int[] Fs, boolean oriented, int[] Aps, boolean valued) {
        this.Aps = Aps;
        this.Fs = Fs;
        int count = 1;
        List<SUMMIT> summits = new ArrayList<>();
        List<BRIDGE> bridges = new ArrayList<>();

        for (int i = 1; i < Aps.length; i++) {
            SUMMIT s = new SUMMIT();
            summits.add(s);
        }

        for (int i = 1; i < Fs.length; i++) {
            if (Fs[i] != 0) {
                BRIDGE b = new BRIDGE(summits.get(count - 1), summits.get(Fs[i] - 1));
                bridges.add(b);
            } else count++;
        }
        this.summits = summits;
        this.bridges = bridges;
        this.oriented = oriented;
        this.valued = valued;
    }

    public GRAPH(int[] Fs, int[] Aps, boolean valued) {
        this(Fs, true, Aps, valued);
    }

    public GRAPH(int[] Fs, boolean oriented, int[] Aps) {
        this(Fs, oriented, Aps, true);
    }

    public GRAPH(int[] Fs, int[] Aps) {
        this(Fs, true, Aps, true);
    }

    public GRAPH(String nomFichier) {   //creation fichier
        String chaine = "";
        String fichier = nomFichier + ".txt";
        System.out.println(fichier);
        ArrayList<SUMMIT> summitstxt = new ArrayList<>();
        ArrayList<BRIDGE> bridgestxt = new ArrayList<>();


        //lecture du fichier texte
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            int ind1, ind2, ind3;
            line = br.readLine();
            int nombreSommet = Integer.parseInt(line);

            line = br.readLine();
            this.oriented = Boolean.parseBoolean(line); // Orienté ?
            line = br.readLine();
            this.valued = Boolean.parseBoolean(line); // valué ?


            for (int i = 0; i < nombreSommet; i++) {
                summitstxt.add(new SUMMIT());
            }
            this.summits = summitstxt;

            while ((line = br.readLine()) != null) {
                if (!line.contains("#")){
                    chaine += line;
                    chaine += ",";
                }

            }

            String[] arrOfStr = chaine.split(",");
            if (this.valued == false) {
                for (int i = 0; i < arrOfStr.length - 1; i += 2) {
                    ind1 = Integer.parseInt(arrOfStr[i]);
                    ind2 = Integer.parseInt(arrOfStr[i + 1]);
                    BRIDGE b = new BRIDGE(summits.get(ind1 - 1), summits.get(ind2 - 1));
                    bridgestxt.add(b);
                }
            } else {
                for (int i = 0; i < arrOfStr.length - 2; i += 3) {
                    ind1 = Integer.parseInt(arrOfStr[i]);
                    ind2 = Integer.parseInt(arrOfStr[i + 1]);
                    ind3 = Integer.parseInt(arrOfStr[i + 2]);
                    BRIDGE b = new BRIDGE(summits.get(ind1 - 1), summits.get(ind2 - 1), ind3);
                    bridgestxt.add(b);
                }
            }
            this.bridges = bridgestxt;
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

    public void sortieFichier(String nomFichier) throws IOException {   //creation fichier pour sortie

        try {
            String chaine = "";
            File file = new File(nomFichier + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            chaine += "Valué : " + this.valued + "\n";
            chaine += "Orienté :" + this.oriented + "\n";
            chaine += "Liste de sommets : " + this.summits.toString() + "\n";
            chaine += "Liste des liens :" + this.bridges + "\n";
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(chaine);
            bw.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


    public GRAPH pruferToGraph(int[] prufer) {
        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> S = new ArrayList<>();
        ArrayList<SUMMIT> summits = new ArrayList<>();
        ArrayList<BRIDGE> bridges = new ArrayList<>();

        for (int i : prufer)
            S.add(i);

        for (int i = 1; i <= prufer.length; ++i) {
            summits.add(new SUMMIT());
            L.add(i);
        }

        for (int i = 0; i < S.size() - 1; ++i) {
            int j = i + 1;
            while (j < L.size() && S.contains(L.get(j)))
                ++j;
            bridges.add(new BRIDGE(summits.get(i), summits.get(j)));
            L.remove(j);
            S.remove(i);
        }

        return new GRAPH(summits, false, bridges);
    }

    public int smallestValue(ArrayList<Integer> list) {
        int smallest = 0;
        for (int i = 1; i < list.size(); ++i) {
            if (smallest > list.get(i))
                smallest = i;
        }
        return smallest;
    }


    //GETTERS
    public List<SUMMIT> getSummits() {
        return summits;
    }

    public SUMMIT getSpecificSummits(int indice) {
        return summits.get(indice);
    }

    public SUMMIT getSpecificSummitByKey(int key) {
        for (SUMMIT s : summits) {
            if (s.getKey() == key)
                return s;
        }
        return null;
    }

    public List<BRIDGE> getBridges() {
        return bridges;
    }

    public List<BRIDGE> getSummitBridges(SUMMIT s) {
        List<BRIDGE> bridgesOfS = new ArrayList<>();
        for (int i = 0; i < bridges.size(); ++i) {
            if (bridges.get(i).getFirstSummit().equals(s) || bridges.get(i).getSecondSummit().equals(s))
                bridgesOfS.add(bridges.get(i));
        }
        return bridgesOfS;
    }

    public int numberOfSummit() {
        return summits.size();
    }

    public int numberOfBridges() {
        return this.bridges.size();
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

    public int[][] matDist() {
        return null;
    }

    public int[] ListSummitRank() {
        return null;
    }

    public void distanceArray() {

    }

    //TARJAN
    private boolean allBridgesProcessed(List<Boolean> processed) {
        for (boolean proc : processed) {
            if (proc == false)
                return false;
        }
        return true;
    }

    private int nextBridge(int summit, List<Boolean>traite, List<Integer>NUM) {
        int secondSummit= Integer.MAX_VALUE;
        for (int i=0; i<bridges.size()-1; i++){ // On va jusqu'au bridges concernant le sommet traité
                    if ((bridges.get(i).getFirstSummit().getKey()==summit) && traite.get(i)==false){
                        if (bridges.get(i).getSecondSummit().getKey()<secondSummit ){
                            if(NUM.get(bridges.get(i).getSecondSummit().getKey())==-1 )
                                secondSummit=i;
                            else
                                traite.set(i, true);
                        }

                    }
        }

        if (secondSummit!= Integer.MAX_VALUE) return secondSummit;
        return -1; //On est à la fin des liens du sommet sans en trouvé un non traité
    }

    private int minFrondeLTFC(int summit, List<Integer>NUM, List<Integer> TARJ) {
        int min = Integer.MAX_VALUE;
        int minimum = Integer.MAX_VALUE;
        for (int i=0; i<bridges.size()-1; i++){
            if ((bridges.get(i).getFirstSummit().getKey()==summit) && (TARJ.contains(bridges.get(i).getSecondSummit().getKey()) || bridges.get(i).getSecondSummit().getKey()<summit) && NUM.get(bridges.get(i).getSecondSummit().getKey())!=-1 && TARJ.contains(bridges.get(i).getSecondSummit().getKey()) ){
                if (min > NUM.get(bridges.get(i).getSecondSummit().getKey())) min = NUM.get(bridges.get(i).getSecondSummit().getKey());
            }

        }


        return min;
    }

    private boolean inPrem(int summit, List<Integer> PREM) {
        for (int i = 1; i < PREM.get(0) + 1; ++i) {
            if (PREM.get(i) == summit)
                return true;
        }
        return false;
    }

    private int prochainSommet(List<Integer> NUM) {
        for(int i = 0; i<NUM.size(); ++i) {
            if(NUM.get(i) == -1)
                return i;
        }
        return -1;
    }



    public GRAPH tarjan() {

        //Déclaration des variables utilisées
        List<Integer> NUM = new ArrayList<>(), MU = new ArrayList<>(), PREM = new ArrayList<>(), PILCH = new ArrayList<>(), CFC = new ArrayList<>(), TARJ = new ArrayList<>();
        List<Boolean> ENTARJ = new ArrayList<>(), traite = new ArrayList<>();
        int size = summits.size();

        //Initialisation de certaines List
        NUM.add(size);
        MU.add(size);
        PILCH.add(size);
        CFC.add(size);
        PREM.add(0);
        ENTARJ.add(false);

        for(int i = 0; i < size; ++i) {
            NUM.add(-1);
            MU.add(2147483647);
            PILCH.add(-1);
            CFC.add(-1);
            ENTARJ.add(false);
        }
        for (int i = 0; i < bridges.size(); ++i) {
            traite.add(false);
        }


        //Boucle de traitement pour remplir les tableaux
        int sommet = summits.get(0).getKey(); //Sommet traité
        int lien, nouveauSommet = 0, groupe =0;
        while(!allBridgesProcessed(traite) && sommet!=-1) {
            lien = nextBridge(sommet, traite, NUM);
            if (lien != -1 && NUM.get(bridges.get(lien).getSecondSummit().getKey())==-1) { //Si il y a 1 lien à traiter
                if(NUM.get(bridges.get(lien).getSecondSummit().getKey())!=-1) {
                    //Marqué le lien comme traité
                    traite.set(lien, true);//c'est une fronde, un LTFC ou un LTFNC
                }else {

                    if (!TARJ.contains(sommet)){
                        NUM.set(sommet, ++nouveauSommet);
                        ENTARJ.set(sommet, true);
                        TARJ.add(sommet);
                        if (TARJ.size() > 1)
                            PILCH.set(sommet, TARJ.get(TARJ.size() - 2));
                        else
                            PILCH.set(sommet, 0);
                    }

                    //Passe au sommet suivant
                    sommet = bridges.get(lien).getSecondSummit().getKey();
                    //System.out.println("passage sur le sommet suivant(if) :" + sommet);

                    //Marqué le lien comme traité
                    traite.set(lien, true);
                }
            }else{

                if (!TARJ.contains(sommet)){
                    NUM.set(sommet, ++nouveauSommet);
                    ENTARJ.set(sommet, true);
                    TARJ.add(sommet);
                    if (TARJ.size() > 1)
                        PILCH.set(sommet, TARJ.get(TARJ.size() - 2));
                    else
                        PILCH.set(sommet, 0);
                }
                if(MU.get(sommet)== Integer.MAX_VALUE){
                    int numSommet = NUM.get(sommet);
                    //Recherche du plus petit mu des sucesseurs direct
                    int pps = Integer.MAX_VALUE;
                    for(BRIDGE b:bridges) {
                        //System.out.println(MU.size());
                        //System.out.println(b.getSecondSummit().getKey());
                        if(b.getFirstSummit().getKey() == sommet && TARJ.contains(b.getSecondSummit().getKey()) && pps>MU.get(b.getSecondSummit().getKey())) {
                            pps = MU.get(b.getSecondSummit().getKey());
                        }
                    }
                    int minFLTFC = minFrondeLTFC(sommet, NUM, TARJ);

                    //Calcul de MU
                    MU.set(sommet,Math.min(Math.min(/*nouvelle numérotation du sommet*/numSommet, /*MU des sucesseurs */pps), /*fronde&LTFC*/minFLTFC));

                    //System.out.println("MU de " + sommet + " : min[numSommet:"+numSommet+" pps:"+pps+" minFLTCF:"+minFLTFC+"] = " + MU.get(sommet));

                }

                if(MU.get(sommet) == NUM.get(sommet)) {
                    //Remplir PREM & CFC, Update TARJ & ENTARJ
                    PREM.add(TARJ.get(TARJ.size() - 1));
                    PREM.set(0, PREM.get(0) + 1);
                    ++groupe;
                    for(int i = TARJ.size()-1; i>= 0; --i) {

                        if(MU.get(TARJ.get(i)) != Integer.MAX_VALUE) {
                            CFC.set(TARJ.get(i), groupe);
                            ENTARJ.set(TARJ.get(i), false);
                            TARJ.remove(TARJ.get(i));
                        }
                    }
                    //passer au sommet suivant non traité
                    if(TARJ.size()==0)
                        sommet = prochainSommet(NUM);
                    else
                        sommet = PILCH.get(sommet);
                    //System.out.println("passage sur le sommet suivant(else) :" +sommet);
                }else {
                    //revenir sur le sommet précédent
                    sommet = PILCH.get(sommet);
                }
            }
        }

        //Creation des nouveaux sommets
        List<SUMMIT> newSummits = new ArrayList<SUMMIT>();
        SUMMIT s;
        for(int i = 1; i<=PREM.get(0); ++i) {
            sommet = PREM.get(i);
            s = summits.get(i-1);
            s.setInfo(" "+sommet+" ");
            newSummits.add(s);
            int next = PILCH.get(sommet);
            while(next != 0 && !inPrem(next, PREM)) {
                newSummits.get(newSummits.size()-1).setInfo(newSummits.get(newSummits.size()-1).getInfo()+Integer.toString(next)+" "); //Ajout de tout les anciens sommet qu'il contient
                next = PILCH.get(next);
            }
        }

        List<BRIDGE> newBridges = new ArrayList<BRIDGE>();
        int s1, s2;
        boolean exist;
        for (BRIDGE b: bridges) {
            s1=CFC.get(b.getFirstSummit().getKey());
            s2=CFC.get(b.getSecondSummit().getKey());
            if(s1 != s2) { //si c'est pas un lien intra sommet
                exist=false;
                int i = 0;
                while (i < newBridges.size() - 1 & !exist) {
                    if (newBridges.get(i).getFirstSummit().getKey() == s1 && newBridges.get(i).getSecondSummit().getKey() == s2)
                        exist = true;
                    else
                        ++i;
                }
                if(!exist) {
                    SUMMIT sommet1 = new SUMMIT(),sommet2 = new SUMMIT();
                    //trouver le sommet CFC.get(b.getFirstSummit().getKey()) dans la nouvelle liste sommet
                    for(SUMMIT summit:newSummits) {
                        if( summit.getKey() == s1)
                            sommet1 = summit;
                    }

                    //trouver le sommet CFC.get(b.getSecondSummit().getKey()) dans la nouvelle liste sommet
                    for(SUMMIT summit:newSummits) {
                        if(summit.getKey() == s2)
                            sommet2 = summit;
                    }

                    //Création du nouveau lien
                    BRIDGE bridge = new BRIDGE(/*s1*/sommet1, /*s2*/sommet2);
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
        for (int i = 0; i <= this.getSummits().size(); ++i) {
            this.distances.add(Integer.MAX_VALUE);
            this.pred.add(0);
        }

        this.distances.set(origin.getKey(), 0);
        this.pred.set(origin.getKey(), 0);

        while (checkedSummits.size() < summits.size()) {
            for (BRIDGE b : bridges) {
                if (b.getFirstSummit() == origin)
                    uncheckSummits.add(b.getSecondSummit()); // Liste les successeurs du sommet
            }
            for (SUMMIT s : uncheckSummits) {
                for (BRIDGE b : bridges) {
                    if (b.getFirstSummit() == origin && b.getSecondSummit() == s) {
                        if (distances.get(s.getKey()) > distances.get(origin.getKey()) + b.getWeight()) {
                            distances.set(s.getKey(), distances.get(origin.getKey()) + b.getWeight());
                            pred.set(s.getKey(), origin.getKey());
                        }
                    }
                }

            }
            checkedSummits.add(origin);
            int mini = 1;
            for (SUMMIT s : summits) {
                if (!checkedSummits.contains(s)) mini = s.getKey();
            }
            for (int i = 2; i < distances.size() - 1; i++) {
                if (distances.get(mini) > distances.get(i) && !checkedSummits.contains(summits.get(i - 1))) {
                    mini = i;
                }

            }
            origin = summits.get(mini - 1);
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
        for (int i = 2; i < pred.size() - 1; i++) {
            BRIDGE b = new BRIDGE(summits.get(pred.get(i) - 1), summits.get(i - 1), (distances.get(i) - distances.get(pred.get(i))));
            newBridges.add(b);
        }
        System.out.println(newBridges.toString());
        return new GRAPH(summits, this.oriented, newBridges, this.valued);
    }

    private void minimalDistances(SUMMIT summit) {
        for (BRIDGE target : this.getSummitBridges(summit)) {
            SUMMIT targetSummit;
            if (target.getFirstSummit() != summit)
                targetSummit = target.getFirstSummit();
            else
                targetSummit = target.getSecondSummit();

            if (getShortestPath(targetSummit) > getShortestPath(summit) + target.getWeight()) {
                this.distances.set(targetSummit.getKey(), getShortestPath(summit) + target.getWeight());
                this.pred.set(summit.getKey(), targetSummit.getKey());
                if (!checkedSummits.contains(targetSummit))
                    this.uncheckSummits.add(targetSummit);
            }
        }

    }

    private int getDistance(SUMMIT departure, SUMMIT arrival) {
        for (BRIDGE bridge : this.bridges) {
            if (bridge.getFirstSummit().equals(departure) && bridge.getFirstSummit().equals(arrival))
                return bridge.getWeight();
        }
        System.out.println("[LOG] Error, this situation shouldn't happen. " + departure.getKey() + " is not connected with " + arrival.getKey() + ".");
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
        int[] prem = new int[n + 1];
        int[] pilch = new int[n + 1];
        int[] cfc = new int[n + 1];
        int[] nbElem = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            prem[i] = i;
            pilch[i] = 0;
            cfc[i] = i;
            nbElem[i] = i;
        }

        //Trie des arêtes
        int p;
        for (int i = 0; i < bridges.size() - 1; i++) {
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
        int i = 0, j = 0;
        while (j < n - 1) {
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

        int[][] gr = matDist();
        int n = summits.size();
        int[][] mat = new int[n][n];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; i <= n; i++) {
                if (i == j) {
                    mat[i][i] = 0;
                } else {
                    mat[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int k = 1; k <= n; k++) {
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
            int t = 0;
            for (int j = k; j >= 1; j--) {
                t = mat[k + 1][j] + mat[j][k + 1];
            }

            if (t < 0) {
                break;
            } else {
                for (int i = 1; i <= k; i++) {
                    for (int j = 1; j <= k; j++) {
                        if (mat[i][j] > (mat[i][k + 1] + mat[k + 1][j])) {
                            mat[i][j] = mat[i][k + 1] + mat[k + 1][j];
                        }
                    }
                }
            }
        }
        return mat;

    }

    public ArrayList<Integer> toPruferCode() {

        ArrayList<Integer> prufer = new ArrayList<>();

        while (prufer.size() < summits.size() - 2) {
            SUMMIT s = this.summits.get(this.smallestLeaf() - 1);
            SUMMIT voisin = getNeighbour(s);
            prufer.add(voisin.getKey());
            bridges.removeIf(b -> b.contains(s));
            //summits.remove(s);

        }
        return prufer;
    }

    private SUMMIT getNeighbour(SUMMIT s) {
        for (BRIDGE bridge : this.bridges) {
            if (bridge.contains(s)) {
                SUMMIT voisin;
                if (bridge.getFirstSummit() == s)
                    voisin = bridge.getSecondSummit();
                else
                    voisin = bridge.getFirstSummit();
                return voisin;
            }
        }
        throw new RuntimeException("[LOG] Error getNeighbour");
    }

    private boolean isLeaf(SUMMIT summit) {
        int i = 0;
        for (BRIDGE bridge : this.bridges) {
            if (bridge.contains(summit))
                i++;
        }
        return (i == 1);
    }

    /*public boolean isLeafWithDeleteSummit(ArrayList<Integer> deleted, SUMMIT summit){
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

    }*/

    private int smallestLeaf() {
        int min = Integer.MAX_VALUE;
        for (SUMMIT s : this.summits) {
            if (this.isLeaf(s)) {
                if (min > s.getKey())
                    min = s.getKey();
            }
        }
        return min;
    }

    public GRAPH decodagePrufer(ArrayList<Integer> d_tableauPrufer) {
        ArrayList Summit = new ArrayList<Integer>();
        ArrayList ListOfSummit = new ArrayList<SUMMIT>();
        ArrayList Bridges = new ArrayList<BRIDGE>();
        ArrayList SummitGraph = new ArrayList<SUMMIT>();
        for (int i = 1; i < d_tableauPrufer.size() + 3; i++) {
            SUMMIT s = new SUMMIT();
            Summit.add(i);
            ListOfSummit.add(s);
        }
        int i = 0, j = 0;

        while (Summit.size() > 2) {
            j = 0;
            while (i < d_tableauPrufer.size() && j < Summit.size() && Contain(d_tableauPrufer, (Integer) Summit.get(j))) {
                j++;
            }
            if (j < Summit.size()) {
                if (!SummitGraph.contains(ListOfSummit.get(d_tableauPrufer.get(i) - 1)))
                    SummitGraph.add(ListOfSummit.get(d_tableauPrufer.get(i) - 1));
                if (!SummitGraph.contains(ListOfSummit.get((int) Summit.get(j) - 1)))
                    SummitGraph.add(ListOfSummit.get((int) Summit.get(j) - 1));
                Bridges.add(new BRIDGE((SUMMIT) ListOfSummit.get(d_tableauPrufer.get(i) - 1), (SUMMIT) ListOfSummit.get((int) Summit.get(j) - 1)));
                d_tableauPrufer.remove(d_tableauPrufer.get(i));
                Summit.remove(Summit.get(j));


            } else i++;
        }
        if (!SummitGraph.contains(ListOfSummit.get((int) Summit.get(0) - 1)))
            SummitGraph.add(ListOfSummit.get((int) Summit.get(0) - 1));
        if (!SummitGraph.contains(ListOfSummit.get((int) Summit.get(1) - 1)))
            SummitGraph.add(ListOfSummit.get((int) Summit.get(1) - 1));
        Bridges.add(new BRIDGE((SUMMIT) ListOfSummit.get((int) Summit.get(0) - 1), (SUMMIT) ListOfSummit.get((int) Summit.get(1) - 1)));
        GRAPH g = new GRAPH(SummitGraph, false, Bridges, false);
        return g;
    }

    public boolean Contain(ArrayList<Integer> A, int B) {
        boolean answer = false;
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) == B) answer = true;
        }
        return answer;
    }


    //Onglet d'explication
    public void help() {
        String input;
        System.out.println("User Manual :\n");
        System.out.println("Addition & Removal: ");
        System.out.println("\t1-addBridge");
        System.out.println("\t2-addSummit");
        System.out.println("\t3-removeBridge");
        System.out.println("\t4-removeSummit");
        System.out.println("Getters :");
        System.out.println("\t5-getBridges");
        System.out.println("\t6-getSummits");
        System.out.println("\t7-isOriented");
        System.out.println("\t8-isValued");
        System.out.println("\t9-getFs");
        System.out.println("\t10-getAps");
        System.out.println("\t11-getAdjacent");
        System.out.println("Methods : ");
        System.out.println("\t12-distanceArray");
        System.out.println("\t13-ListSummitRank");
        System.out.println("\t14-djikstra");
        System.out.println("\t15-Kruskal");
        System.out.println("\t16-tarjan");
        System.out.println("\t17-toPruferCode");
        System.out.println("\t18-pruferToGraph");
        System.out.println("Output :");
        System.out.println("\t19-toString");
        System.out.println("\t20-writeTheGraphInAFile");
        System.out.println("\t21-display");
    }

    @Override
    public String toString() {
        if (this.bridges != null && this.summits != null) {
            return "Summits:" + summits.toString() + "\nBridges:" + bridges.toString() + "\nOriented:" + oriented + "\nValued:" + valued;
        } else return "Mauvaise entrée";
    }

    public void afficherGraph() {
        Graph g;
        if (this.oriented==true) g = new DirectedSparseMultigraph<SUMMIT, BRIDGE>();
        else g = new SparseMultigraph<SUMMIT, BRIDGE>();
        for (int i = 0; i < summits.size(); i++) {
            g.addVertex(summits.get(i));
        }
        for (int i = 0; i < bridges.size(); i++) {
            g.addEdge(bridges.get(i), bridges.get(i).getFirstSummit(), bridges.get(i).getSecondSummit());
        }

        Layout<Integer, String> layout = new CircleLayout(g);
        layout.setSize(new Dimension(800, 800));
        BasicVisualizationServer<Integer, String> vv =
                new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(850, 850));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller() {
            @Override
            public String transform(Object v) {

                return Integer.toString(((SUMMIT) v).getKey());
            }
        });
        if (this.valued==true) {
            vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller() {
                @Override
                public String transform(Object v) {

                    return Integer.toString(((BRIDGE) v).getWeight());
                }
            });
        }

        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setLocationRelativeTo(null);

        JFrame userInterface = new JFrame("Interface utilisateur");
        userInterface.setLayout(new GridLayout(2,1));
        JButton addSummit = new JButton("Ajouter un sommet");
        JButton addBridge = new JButton("Ajouter un lien");
        userInterface.setLocationRelativeTo(frame);
        userInterface.setSize(200,200);
        userInterface.getContentPane().add(addBridge, BorderLayout.NORTH);
        userInterface.getContentPane().add(addSummit, BorderLayout.NORTH);


        addSummit.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                SUMMIT s = new SUMMIT();
                summits.add(s);
                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });

        userInterface.add(addSummit);

        addBridge.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane jop = new JOptionPane();
                String firstQ = jop.showInputDialog(null, "Veuillez entrer un premier point", JOptionPane.QUESTION_MESSAGE);
                String secondQ = jop.showInputDialog(null, "Veuillez entrer le second point", JOptionPane.QUESTION_MESSAGE);
                int firstAnswer = Integer.parseInt(firstQ) - 1;
                int secondAnswer = Integer.parseInt(secondQ)-1;
                if ((firstAnswer>=summits.size())|| (secondAnswer>=summits.size())){
                    JDialog d = new JDialog(frame, "Mauvaise entrée !");
                    JLabel l = new JLabel("Un des sommets n'existe pas");
                    d.add(l);
                    d.setSize(200, 100);
                    d.setVisible(true);

                }
                if (valued==true){
                    String thirdQ = jop.showInputDialog(null, "Veuillez entrer le poids", JOptionPane.QUESTION_MESSAGE);
                    int thirdAnsw = Integer.parseInt(thirdQ);
                    bridges.add(new BRIDGE(summits.get(firstAnswer), summits.get(secondAnswer),thirdAnsw));
                    }
                else {
                    bridges.add(new BRIDGE(summits.get(firstAnswer), summits.get(secondAnswer)));
                }

                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });


        frame.setVisible(true);
        userInterface.setVisible(true);




    }
}


