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

import static org.fusesource.hawtjni.runtime.Callback.reset;

public class GRAPH {
    private List<SUMMIT> summits;
    private List<BRIDGE> bridges;
    private boolean[][] Adj;
    private int[] Aps, Fs;
    private boolean oriented, valued;
    private static List<SUMMIT> savedSummits;
    private static List<BRIDGE> savedBridges;
    private static int autocountSaved;

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

    public GRAPH(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges) { // Si on ne précise pas s'il est valué, par défault non valué
        this(summits, oriented, bridges, false);
    }

    public GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges, boolean valued) { // Si on ne précise pas s'il est orienté, par défault non orienté
        this(summits, false, bridges, valued);
    }

    public GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges) { // Aucune précision, ni orienté ni valué
        this(summits, false, bridges, false);
    }

    public GRAPH() { // Constructeur par défaut
        this.summits = new ArrayList<SUMMIT>();
        this.Adj = null;
        this.Aps = null;
        this.Fs = null;
        this.bridges = new ArrayList<BRIDGE>();
        this.oriented = false;
        this.valued = false;
    }

    public GRAPH(boolean[][] adjacents, boolean oriented) { // Constructeur matrice d'adjacence
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
                    BRIDGE b = new BRIDGE(summits.get(i - 1), summits.get(j - 1));
                    bridges.add(b);

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
        this(Fs, false, Aps, valued);
    }

    public GRAPH(int[] Fs, boolean oriented, int[] Aps) {
        this(Fs, oriented, Aps, false);
    }

    public GRAPH(int[] Fs, int[] Aps) {
        this(Fs, false, Aps, false);
    }

    public GRAPH(String nomFichier) {   //constructeur en lisant un fichier "nomFichier" (sans l'extension dans le nom)
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
            if (line.charAt(0)=='#') line = br.readLine();
            int nombreSommet = Integer.parseInt(line);

            line = br.readLine();
            if (line.charAt(0)=='#') line = br.readLine();
            this.oriented = Boolean.parseBoolean(line); // Orienté ?
            line = br.readLine();
            if (line.charAt(0)=='#') line = br.readLine();
            this.valued = Boolean.parseBoolean(line); // valué ?

            if (this.summits==null ||this.summits.size()< nombreSommet){
                for (int i = 0; i < nombreSommet; i++) {
                    summitstxt.add(new SUMMIT());
                }
                this.summits = summitstxt;
            }
            else {
                for (int i = 0; i < nombreSommet; i++) {
                    summitstxt.add(summits.get(i));
                }
                this.summits = summitstxt;
            }


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

    public void sortieFichier(String nomFichier) throws IOException {   //creation fichier pour sortie nom : "sortieGraph"
        // ATTENTION si le fichier n'existe pas, il est crée
        try {
            String chaine = "";
            String saveText = "";
            File file = new File(nomFichier + ".txt");
            File save = new File("Sauvegarde.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!save.exists()) {
                save.createNewFile();
            }
            chaine += "Valué : " + this.valued + "\n";
            chaine += "Orienté :" + this.oriented + "\n";
            chaine += "Liste de sommets : " + this.summits.toString() + "\n";
            chaine += "Liste des liens :" + this.bridges + "\n";
            saveText += this.summits.size() + "\n" +  this.oriented + "\n" + this.valued + "\n";
            if (this.valued==true){
                for (int i=0 ; i< bridges.size(); i++){
                    saveText+= bridges.get(i).getFirstSummit().getKey() + "," + bridges.get(i).getSecondSummit().getKey() + "," + bridges.get(i).getWeight()+"\n";
                }
            }
            else {
                for (int i = 0; i < bridges.size(); i++) {
                    saveText += bridges.get(i).getFirstSummit().getKey() + "," + bridges.get(i).getSecondSummit().getKey() + "\n";
                }
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(chaine);
            bw.close();

            FileWriter fw2 = new FileWriter(save.getAbsoluteFile());
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write(saveText);
            bw2.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

    public GRAPH (ArrayList<Integer> d_tableauPrufer) {
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
        this.summits=SummitGraph;
        this.bridges=Bridges;
        this.oriented=false;
        this.valued=false;
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

    public void setValued (boolean b){
        this.valued=b;
    }

    public void setOriented (boolean b){
        this.oriented=b;
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
    public int[] distance(int r){ // Affiche les distances à partir d'un sommet du graph
        int nb_som = summits.size();
        int i =0, j = 1,k=0,ifin,s,t,it;
        int[] fil = new int[nb_som+1];
        fil[0] = nb_som;
        int[] dist = new int[nb_som+1];
        dist[0] = nb_som;
        fil[1] = r;
        for (int h =1; h <= nb_som;h++){
            dist[h] = -1;
        }

        dist[r] = 0;

        while(i < j){
            k++;
            ifin = j;
            while(i < ifin){
                i++;
                s = fil[i];
                it = Aps[s];
                t = Fs[it];
                while(t > 0){
                    if(dist[t] == -1){
                        j++;
                        fil[j]=t;
                        dist[t] = k;
                    }
                    t = Fs[++it];
                }
            }
        }
        return dist;
    }

    public int[][] matDist() // Matrice des distances
    {
        int n = summits.size();
        int[][]res = new int[n][n];

        for(int i = 0; i < n;i++)
        {
            res[i]  = distance(summits.get(i).getKey());
        }

        return res;
    }

    public int[] ListSummitRank() {
        return null;
    }


    //TARJAN
    private boolean allBridgesProcessed(List<Boolean> processed) { // Vérifie si tous les liens ont été traité
        for (boolean proc : processed) {
            if (proc == false)
                return false;
        }
        return true;
    }

    private int nextBridge(int summit, List<Boolean>traite, List<Integer>NUM) { // Trouve le prochain lien à traiter
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

    private int minFrondeLTFC(int summit, List<Integer>NUM, List<Integer> TARJ) { // Trouver la fronde/LTFC la plus petite du sommet
        int min = Integer.MAX_VALUE;
        int minimum = Integer.MAX_VALUE;
        for (int i=0; i<bridges.size()-1; i++){
            if ((bridges.get(i).getFirstSummit().getKey()==summit) && (TARJ.contains(bridges.get(i).getSecondSummit().getKey()) || bridges.get(i).getSecondSummit().getKey()<summit) && NUM.get(bridges.get(i).getSecondSummit().getKey())!=-1 && TARJ.contains(bridges.get(i).getSecondSummit().getKey()) ){
                if (min > NUM.get(bridges.get(i).getSecondSummit().getKey())) min = NUM.get(bridges.get(i).getSecondSummit().getKey());
            }

        }


        return min;
    }

    private boolean inPrem(int summit, List<Integer> PREM) { // Retourne vrai si le sommet est dans le tableau PREM
        for (int i = 1; i < PREM.get(0) + 1; ++i) {
            if (PREM.get(i) == summit)
                return true;
        }
        return false;
    }

    private int prochainSommet(List<Integer> NUM) { // Trouver le prochain sommet à traiter
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

    public GRAPH djikstra() {
        return this.djikstra(this.getSpecificSummits(0));
    }
    public GRAPH djikstra(int summit) {
        return this.djikstra(this.getSpecificSummits(summit));
    }
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

        List<BRIDGE> newBridges = new ArrayList<>();
        for (int i = 2; i < pred.size(); i++) {
            BRIDGE b = new BRIDGE(summits.get(pred.get(i) - 1), summits.get(i - 1), (distances.get(i) - distances.get(pred.get(i))));
            newBridges.add(b);
        }
        System.out.println(newBridges.toString());
        return new GRAPH(summits, this.oriented, newBridges, this.valued);
    }

    private void fusionner(int i,int j,int[] prem,int[]pilch,int[]cfc,int[] nbElem)
    {
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

    public void Kruskal(GRAPH reduit) {
        if (this.valued=false) reduit.valued=false;
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
        for(int i = 0; i < bridges.size()-1;i++) {
            for (int j = i + 1; j < bridges.size(); j++) {
                if (bridges.get(j).getWeight() < bridges.get(i).getWeight() ||
                        (bridges.get(j).getWeight() == bridges.get(i).getWeight() && bridges.get(j).getFirstSummit().getKey() < bridges.get(i).getSecondSummit().getKey()) ||
                        (bridges.get(j).getWeight() == bridges.get(i).getWeight() && bridges.get(j).getSecondSummit().getKey() < bridges.get(i).getSecondSummit().getKey())) {
                    p = bridges.get(j).getWeight();
                    SUMMIT s1 = bridges.get(j).getFirstSummit();
                    SUMMIT s2 = bridges.get(j).getSecondSummit();
                    bridges.get(j).setWeight(bridges.get(i).getWeight());
                    bridges.get(j).setFirstSummit(bridges.get(i).getFirstSummit());
                    bridges.get(j).setSecondSummit(bridges.get(i).getSecondSummit());
                    bridges.get(i).setWeight(p);
                    bridges.get(i).setFirstSummit(s1);
                    bridges.get(i).setSecondSummit(s2);
                }
            }
        }

        //kruskal
        int x;
        int y;
        int i = 0, j = 0;
        while (j < n-1) {
            BRIDGE ar = bridges.get(i);
            x = cfc[ar.getFirstSummit().getKey()];
            y = cfc[ar.getSecondSummit().getKey()];
            if (x != y) {
                reduit.bridges.add(bridges.get(i));
                j++;
                fusionner(x,y,prem,pilch,cfc,nbElem);
            }
            i++;
        }
    }


    public int[] tabDistDantzig(int src) {

        int n = summits.size();
        int m = bridges.size();
        int[] mat = new int[n];
        for (int i = 0; i < n; ++i)
            mat[i] = Integer.MAX_VALUE;
        mat[src] = 0;

        //int[][] gr = matDist();;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                int u = bridges.get(j).getFirstSummit().getKey();
                int v =  bridges.get(j).getSecondSummit().getKey();
                int wei = bridges.get(j).getWeight();
                if (mat[u-1] != Integer.MAX_VALUE && mat[u-1] + wei < mat[v-1])
                    mat[v-1] = mat[u-1] + wei;
            }
        }

        return mat;
    }

    public int[][] Dantzig(){
        int n = summits.size();
        int m = bridges.size();
        int [][] res = new int[n][n];
        for (int i = 0; i < res.length;i++){
            res[i] = tabDistDantzig(i);
        }

        return res;
    }


    public ArrayList<Integer> toPruferCode() {

        ArrayList<Integer> prufer = new ArrayList<>();

        while (prufer.size() < summits.size() - 2) {
            SUMMIT s = this.summits.get(this.smallestLeaf() - 1);
            SUMMIT voisin = getNeighbour(s);
            prufer.add(voisin.getKey());
            bridges.removeIf(b -> b.contains(s));

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



    public boolean Contain(ArrayList<Integer> A, int B) {
        boolean answer = false;
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) == B) answer = true;
        }
        return answer;
    }

    public boolean Contain(int[] A, int B) {
        boolean answer = false;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == B) answer = true;
        }
        return answer;
    }

    public int[] Rank(){
        int[] rank = new int[summits.size()];
        boolean[] Pred = new boolean[summits.size()];
        Arrays.fill(Pred,false);
        Arrays.fill(rank, Integer.MAX_VALUE);

        for (SUMMIT s : summits){
            for (BRIDGE b : bridges){
                if (b.getSecondSummit()==s) Pred[s.getKey()-1] = true;
            }
        }
        for (int i=0 ; i<Pred.length;i++){
            if (Pred[i]==false) rank[i]= 0;
        }
        while (Contain(rank, Integer.MAX_VALUE)){
            for (BRIDGE b : bridges){
                if (rank[b.getFirstSummit().getKey()-1] != Integer.MAX_VALUE){
                    if (rank[b.getFirstSummit().getKey()-1] +1 < rank[b.getSecondSummit().getKey()-1]) rank[b.getSecondSummit().getKey()-1]=rank[b.getFirstSummit().getKey()-1]+1;
                }
            }

        }
        return rank;

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
        frame.setLocation(100,100);

        JFrame userInterface = new JFrame("Interface utilisateur");
        userInterface.setLayout(new GridLayout(4,2));
        JButton addSummit = new JButton("Ajouter un sommet");
        JButton addBridge = new JButton("Ajouter un lien");
        JButton save = new JButton("Sauvegarder le Graphe");
        JButton load = new JButton("Charger le Graphe");
        JButton tarjan = new JButton("Appliquer Tarjan");
        JButton dijkstra = new JButton("Appliquer Dijkstra");
        JButton kruskal = new JButton("Appliquer Kruskal");
        JButton reset = new JButton("Remise à zero");
        JButton rank = new JButton("Rang des sommets");
        JButton valued = new JButton("Changer valué ou non");
        JButton oriented = new JButton("Changer orienté ou non");
        JButton help = new JButton("Manuel d'utilisation");
        JButton exit = new JButton("Quitter");

        userInterface.setSize(800,300);
        userInterface.getContentPane().add(addSummit);
        userInterface.getContentPane().add(addBridge);
        userInterface.getContentPane().add(save);
        userInterface.getContentPane().add(load);
        userInterface.getContentPane().add(dijkstra);
        userInterface.getContentPane().add(tarjan);
        userInterface.getContentPane().add(kruskal);
        userInterface.getContentPane().add(rank);
        userInterface.getContentPane().add(reset);
        userInterface.getContentPane().add(help);
        userInterface.getContentPane().add(valued);
        userInterface.getContentPane().add(oriented);
        userInterface.getContentPane().add(exit);
        userInterface.setLocation(1000,300);


        addSummit.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                summits.add(new SUMMIT(summits.size()+1));
                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });

        save.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                savedSummits=List.copyOf(summits);
                savedBridges=List.copyOf(bridges);
                autocountSaved=summits.size();
            }
        });

        load.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                SUMMIT.setAutoCount(autocountSaved);
                summits=List.copyOf(savedSummits);
                bridges=List.copyOf(savedBridges);
                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });

        valued.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValued()==true) setValued(false);
                else setValued(true);
                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });

        oriented.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                if(isOriented()==true) setOriented(false);
                else setOriented(true);
                frame.dispose();
                userInterface.dispose();
                afficherGraph();
            }
        });

        reset.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {

                SUMMIT.setAutoCount(0);
                bridges.clear();
                summits.clear();
                frame.dispose();
                userInterface.dispose();
                afficherGraph();

            }
        });

        dijkstra.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                userInterface.dispose();
                djikstra().afficherGraph();
            }
        });

        kruskal.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                userInterface.dispose();
                List<BRIDGE> blred = new ArrayList<>();
                GRAPH red = new GRAPH(summits,false,blred,true);
                Kruskal(red);
                red.afficherGraph();
            }
        });

        rank.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "";
                int[] rank = Rank();
                JDialog d = new JDialog(frame, "Rang des sommets");
                d.setLocation(600,600);
                for (int i=0; i<rank.length;i++){
                    s+="Le sommet "+ (i+1) +" a pour rang "+rank[i]+"\n";
                    JTextArea l = new JTextArea(s);
                    d.add(l);
                }
                d.setSize(800, 400);
                d.setVisible(true);
            }
        });

        help.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                String s="";
                s+="Manuel d'utilisation :\n";
                s+="Constructeurs: \n";
                s+="\t- GRAPH() : Constructeur par défaut. Créé un graphique vide, sans sommets ni liens qui n'est pas orienté ni valué.\n";
                s+="\t- GRAPH​(List<SUMMIT> summits, List<BRIDGE> bridges) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique ne sera ni orienté ni valué.\n";
                s+="\t- GRAPH​(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.\n";
                s+="\t- GRAPH​(List<SUMMIT> summits, List<BRIDGE> bridges, boolean valued) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera valué ou non en fonction du bouléen donnée en paramètre. Il ne sera pas orienté.\n";
                s+="\t- GRAPH​(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera orienté/valué ou non en fonction des deux bouléen donnée en paramètre.\n";
                s+="\t- GRAPH​(int[] Fs, int[] Aps) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Il ne sera ni orienté ni valué.\n";
                s+="\t- GRAPH​(int[] Fs, boolean oriented, int[] Aps) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.\n";
                s+="\t- GRAPH​(int[] Fs, int[] Aps, boolean valued) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera valué ou non en fonction du bouléen donnée en paramètre. Il ne sera pas orienté.\n";
                s+="\t- GRAPH​(int[] Fs, boolean oriented, int[] Aps, boolean valued) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera orienté/valué ou non en fonction des deux bouléen donnée en paramètre.\n";
                s+="\t- GRAPH​(boolean[][] adjacents, boolean oriented) : Constructeur qui créé un graphique à partir de la matrice d'adjacence. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.\n";
                s+="\t- GRAPH​(String nomFichier) : Constructeur qui créé un graphique à partir d'un fichier texte.\n";
                s+="Ajout & suppression: \n";
                s+="\t- addBridge : GRAPH.addBridge(BRIDGE lienÀAjouter);\n\t\tCette méthode permet d'ajouter un lien au tableau de liens du graphique par le biais de la classe BRIDGE. L'objet BRIDGE contient le sommet d'origine, le sommet cible ainsi que le poids de la liaison.\n";
                s+="\t- addSummit : GRAPH.addSummit(SUMMIT sommetÀAjouter;\n\t\tCette méthode permet d'ajouter un sommet au tableau de sommet du graphique par le biais de la classe SUMMIT. L'objet SUMMIT contien le numéro du sommet ainsi que l'information qu'on lui attribue.\n";
                s+="\t- removeBridge : GRAPH.removeBridge(BRIDGE lienÀRetier;\n\t\tCette méthode permet de retirer un lien au tableau de liens du graphique en lui donnant celui à supprimer.\n";
                s+="\t- removeSummit : GRAPH.removeSummit(SUMMIT sommetÀRetirer;\n\t\tCette méthode permet de retirer un sommet au tableau de sommets du graphique en lui donnant celui à supprimer.\n";
                s+="Accesseurs :\n";
                s+="\t- getBridges : GRAPH.getBridges();\n\t\tCette méthode nous permet de récupérer le tableau complet de lien de notre graphique.\n";
                s+="\t- getSummits : GRAPH.getSummits();\n\t\tCette méthode nous permet de récupérer le tableau complet de sommet de notre graphique.\n";
                s+="\t- isOriented : GRAPH.isOriented();\n\t\tCette méthode nous renvoie un boolean qui traduit son orientation.\n";
                s+="\t- isValued : GRAPH.isValued();\n\t\tCette méthode renvoie un boolean qui traduit sa valuation.\n";
                s+="\t- getFs : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau FS de notre graphique.\n";
                s+="\t- getAps : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau APS de notre graphique.\n";
                s+="\t- getAdjacent : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau d'adjacences de notre graphique.\n";
                s+="Méthodes : \n";
                s+="\t- djikstra : GRAPH.djikstra();\n\t\tCette méthode permet de calculer le chemin le plus court selon l'agorithme de djikstra. Il nous renvoie le graphique avec seulement les liens utiles du graphique d'ou est appliqué la méthode.\n";
                s+="\t- kruskal : GRAPH.kruskal()\n\t\tCette méthode permet de calculer l'arbre couvrant minimum du graphique. Il nous renvoie le graphique sans boucle du graphique d'ou est appliqué la méthode.\n";
                s+="\t- tarjan : GRAPH.tarjan();\n\t\tCette méthode permet de faire un graph réduit selon l'agorithme de tarjan. Il nous renvoie le graphique réduit du graphique d'ou est appliqué la méthode.\n";
                s+="\t- toPruferCode : GRAPH.toPruferCode();\n\t\tCette méthode permet de convertir le graphique en un tableau de compressions.\n";
                s+="\t- pruferToGraph : GRAPH.pruferToGraph();\n\t\tCette méthode permet de créé un graphique à partir du tableau de compression d'un graphique.\n";
                s+="Sorties:\n";
                s+="\t- toString : GRAPH.toString();\n\t\tCette méthode permet d'afficher le status du graphique. Il nous affiche donc son tableau de sommets et de liens.\n";
                s+="\t- sortieFichier : sortieFichier(STRING \"cheminDuFicherAinsiQueSonNomEtSonExtension\");\n\t\tCette méthode sauvegarde le graphique dans un fichier passé en paramètre sous le format défini pour la lecture de graphique depuis un fichier (voir le fichier\"entreeAuClavier.txt\").\n";
                s+="\t- afficherGraph : GRAPH.afficherGraph()\n\t\tCette méthode nous permet d'afficher l'interface graphique de notre graphique ainsi que l'interface utilisateur afin que l'on puisse interagir avec celui-ci.\n";
                JDialog d = new JDialog(frame, "Manuel d'utilisation");
                JTextArea l = new JTextArea(s);
                d.add(l);
                d.setSize(1700, 1000);
                d.setVisible(true);
            }
        });

        tarjan.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                userInterface.dispose();
                tarjan().afficherGraph();
            }
        });

        exit.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("Merci d'avoir utilisé notre programme !");
                System.exit(0);
            }
        });



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
                if (isValued()){
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


