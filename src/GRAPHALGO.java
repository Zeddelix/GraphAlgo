import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.*;

import java.awt.Dimension;
import javax.swing.JFrame;




public class GRAPHALGO {

    public static void main(String args[]) throws IOException {

         //### TEST CONSTRUCTEUR DE BASE    ######

        /*SUMMIT s1 = new SUMMIT("1"); // Creation d'un graph pour les tests - 7 sommets et 8 liens
        SUMMIT s2 = new SUMMIT("2");
        SUMMIT s3 = new SUMMIT ("3");
        SUMMIT s4 = new SUMMIT ("4");
        SUMMIT s5 = new SUMMIT ("5");
        SUMMIT s6 = new SUMMIT ("6");
        SUMMIT s7 = new SUMMIT ("7");


        BRIDGE b1 = new BRIDGE(s1,s2,1);
        BRIDGE b2 = new BRIDGE(s1,s5,2);
        BRIDGE b3 = new BRIDGE(s2,s5,1);
        BRIDGE b4 = new BRIDGE (s2,s6,4);
        BRIDGE b5 = new BRIDGE (s3,s1,0);
        BRIDGE b6 = new BRIDGE (s3,s4,2);
        BRIDGE b7 = new BRIDGE (s4,s7,4);
        BRIDGE b8 = new BRIDGE (s5,s4,6);

        List<SUMMIT> l1 = new ArrayList<>() ; // Liste de sommmets
        List<BRIDGE> bl1 = new ArrayList<>(); // Liste de liens

        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        l1.add(s4);
        l1.add(s5);
        l1.add(s6);
        l1.add(s7);
        bl1.add(b1);
        bl1.add(b2);
        bl1.add(b3);
        bl1.add(b4);
        bl1.add(b5);
        bl1.add(b6);
        bl1.add(b7);
        bl1.add(b8);
        GRAPH g = new GRAPH(l1,true,bl1,true);
        g.afficherGraph();*/



        /* #### TEST CONSTRUCTEUR MATRICE D'ADJACENCE ####

        boolean[][] adjacents = new boolean[5][5];
        adjacents[1][3] = true; // 1 -> 3
        adjacents[1][2] = true; // 1 -> 2
        adjacents[2][0] = true; // 2 -> 0
        adjacents[3][4] = true; // 3 -> 4
        GRAPH g = new GRAPH(adjacents,false);
        g.afficherGraph();
        */

        /* #### TEST CONSTRUCTEUR MATRICE FS ET APS ####

        int[] FS = new int[14]; // nombre de liens + nombre de sommets +1 a cause de la case 0
        int[] APS = new int[6]; // nombre de sommets +1

        FS[1]=2;
        FS[2]=3;
        FS[3]=4;
        FS[4]=0;
        FS[5]=2;
        FS[6]=3;
        FS[7]=4;
        FS[8]=0;
        FS[9]=5;
        FS[10]=0;
        FS[11]=0;
        FS[12]=4;
        FS[13]=0;

        APS[1]=1;
        APS[2]=5;
        APS[3]=9;
        APS[4]=11;
        APS[5]=12;

        GRAPH g = new GRAPH(FS, APS);
        g.afficherGraph();
        */

        /* #### TEST CONSTRUCTEUR AVEC FICHIER TEXT ####
        GRAPH g = new GRAPH("entreeAuClavier");
        g.afficherGraph();
        */

        /* #### TEST ECRITURE GRAPH DANS UN FICHIER TEXT POUR SAUVEGARDE ####
        GRAPH g = new GRAPH("entreeAuClavier");
        g.afficherGraph();
        g.sortieFichier("AfficheGrph");
        */

        /* #### TEST CONSTRUCTEUR DECODAGE PRUFER ####
        ArrayList<Integer> prufer = new ArrayList<Integer>();
        prufer.add(2);
        prufer.add(2);
        prufer.add(2);
        prufer.add(5);
        prufer.add(6);
        prufer.add(5);
        prufer.add(7);
        GRAPH g = new GRAPH(prufer);
        g.afficherGraph();
        */

        // #### TEST ALGORITHME DE TARJAN ####
        SUMMIT s1 = new SUMMIT("1");
        SUMMIT s2 = new SUMMIT("2");
        SUMMIT s3 = new SUMMIT ("3");
        SUMMIT s4 = new SUMMIT ("4");
        SUMMIT s5 = new SUMMIT ("5");
        SUMMIT s6 = new SUMMIT ("6");
        SUMMIT s7 = new SUMMIT ("7");
        SUMMIT s8 = new SUMMIT ("8");

        BRIDGE b1 = new BRIDGE(s1,s4);
        BRIDGE b2 = new BRIDGE(s4,s1);
        BRIDGE b3 = new BRIDGE(s4,s7);
        BRIDGE b4 = new BRIDGE(s4,s8);
        BRIDGE b5 = new BRIDGE(s7,s1);
        BRIDGE b6 = new BRIDGE(s8,s1);
        BRIDGE b7 = new BRIDGE(s3,s2);
        BRIDGE b8 = new BRIDGE(s3,s6);
        BRIDGE b9 = new BRIDGE(s6,s2);
        BRIDGE b10 = new BRIDGE(s6,s5);
        BRIDGE b11= new BRIDGE(s2,s5);
        BRIDGE b12= new BRIDGE(s5,s8);
        BRIDGE b13= new BRIDGE(s2,s1);
        BRIDGE b14 = new BRIDGE(s6,s8);
        BRIDGE b15 = new BRIDGE(s7,s8);


        List<SUMMIT> l1 = new ArrayList<>() ; // Liste de sommmets
        List<BRIDGE> bl1 = new ArrayList<>(); // Liste de liens

        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        l1.add(s4);
        l1.add(s5);
        l1.add(s6);
        l1.add(s7);
        l1.add(s8);
        bl1.add(b1);
        bl1.add(b2);
        bl1.add(b3);
        bl1.add(b4);
        bl1.add(b5);
        bl1.add(b6);
        bl1.add(b7);
        bl1.add(b8);
        bl1.add(b9);
        bl1.add(b10);
        bl1.add(b11);
        bl1.add(b12);
        bl1.add(b13);
        bl1.add(b14);
        bl1.add(b15);
        GRAPH g = new GRAPH(l1,true,bl1);

        g.afficherGraph();


        // #### TEST ALGORITHME DE DJIKSTRA ####
        /*SUMMIT s1 = new SUMMIT("1");
        SUMMIT s2 = new SUMMIT("2");
        SUMMIT s3 = new SUMMIT ("3");
        SUMMIT s4 = new SUMMIT ("4");
        SUMMIT s5 = new SUMMIT ("5");
        SUMMIT s6 = new SUMMIT ("6");
        SUMMIT s7 = new SUMMIT ("7");

        BRIDGE b1 = new BRIDGE(s1,s2,1);
        BRIDGE b2 = new BRIDGE(s1,s5,2);
        BRIDGE b3 = new BRIDGE(s2,s5,1);
        BRIDGE b4 = new BRIDGE(s2,s6,4);
        BRIDGE b5 = new BRIDGE(s3,s4,2);
        BRIDGE b6 = new BRIDGE(s3,s1,0);
        BRIDGE b7 = new BRIDGE(s4,s7,4);
        BRIDGE b8 = new BRIDGE(s5,s3,1);
        BRIDGE b9 = new BRIDGE(s5,s4,6);
        BRIDGE b10 = new BRIDGE(s6,s5,1);
        BRIDGE b11= new BRIDGE(s7,s6,1);
        BRIDGE b12 = new BRIDGE(s5,s7,0);

        List<SUMMIT> l1 = new ArrayList<>() ; // Liste de sommmets
        List<BRIDGE> bl1 = new ArrayList<>(); // Liste de liens

        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        l1.add(s4);
        l1.add(s5);
        l1.add(s6);
        l1.add(s7);
        bl1.add(b1);
        bl1.add(b2);
        bl1.add(b3);
        bl1.add(b4);
        bl1.add(b5);
        bl1.add(b6);
        bl1.add(b7);
        bl1.add(b8);
        bl1.add(b9);
        bl1.add(b10);
        bl1.add(b11);
        bl1.add(b12);

        GRAPH g = new GRAPH(l1,true,bl1,true);
        g.afficherGraph();
        */

        /* #### TEST ALGORITHME DE KRUSKAL ####

        SUMMIT s1 = new SUMMIT("1");
        SUMMIT s2 = new SUMMIT("2");
        SUMMIT s3 = new SUMMIT ("3");
        SUMMIT s4 = new SUMMIT ("4");
        SUMMIT s5 = new SUMMIT ("5");
        SUMMIT s6 = new SUMMIT ("6");
        SUMMIT s7 = new SUMMIT ("7");

        BRIDGE b1 = new BRIDGE(s1,s2,1);
        BRIDGE b2 = new BRIDGE(s2,s3,2);
        BRIDGE b3 = new BRIDGE(s3,s1,2);
        BRIDGE b4 = new BRIDGE(s1,s4,1);
        BRIDGE b5 = new BRIDGE(s3,s4,3);
        BRIDGE b6 = new BRIDGE(s3,s5,2);
        BRIDGE b7 = new BRIDGE(s4,s6,5);
        BRIDGE b8 = new BRIDGE(s6,s5,1);
        BRIDGE b9 = new BRIDGE(s5,s7,0);
        BRIDGE b10 = new BRIDGE(s7,s6,1);

        List<SUMMIT> l1 = new ArrayList<>() ; // Liste de sommmets
        List<BRIDGE> bl1 = new ArrayList<>(); // Liste de liens

        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        l1.add(s4);
        l1.add(s5);
        l1.add(s6);
        l1.add(s7);
        bl1.add(b1);
        bl1.add(b2);
        bl1.add(b3);
        bl1.add(b4);
        bl1.add(b5);
        bl1.add(b6);
        bl1.add(b7);
        bl1.add(b8);
        bl1.add(b9);
        bl1.add(b10);

        GRAPH g = new GRAPH(l1,false,bl1,true);
        List<BRIDGE> blred = new ArrayList<>();
        GRAPH red = new GRAPH(l1,false,blred,true);
        g.Kruskal(red);
        red.afficherGraph();
        */


    }
    //Onglet d'explication
    public static void help() {
        System.out.println("Manuel d'utilisation :\n");
        System.out.println("Constructeurs: ");
        System.out.println("\t- GRAPH() : Constructeur par défaut. Créé un graphique vide, sans sommets ni liens qui n'est pas orienté ni valué.");
        System.out.println("\t- GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique ne sera ni orienté ni valué.");
        System.out.println("\t- GRAPH(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.");
        System.out.println("\t- GRAPH(List<SUMMIT> summits, List<BRIDGE> bridges, boolean valued) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera valué ou non en fonction du bouléen donnée en paramètre. Il ne sera pas orienté.");
        System.out.println("\t- GRAPH(List<SUMMIT> summits, boolean oriented, List<BRIDGE> bridges, boolean valued) : Constructeur qui créé un graphique avec la liste de sommets et de liens donnée en paramètre. Le graphique sera orienté/valué ou non en fonction des deux bouléen donnée en paramètre.");
        System.out.println("\t- GRAPH(int[] Fs, int[] Aps) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Il ne sera ni orienté ni valué.");
        System.out.println("\t- GRAPH(int[] Fs, boolean oriented, int[] Aps) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.");
        System.out.println("\t- GRAPH(int[] Fs, int[] Aps, boolean valued) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera valué ou non en fonction du bouléen donnée en paramètre. Il ne sera pas orienté.");
        System.out.println("\t- GRAPH(int[] Fs, boolean oriented, int[] Aps, boolean valued) : Constructeur qui créé un graphique a partir des tableaux FS et APS. Le graphique sera orienté/valué ou non en fonction des deux bouléen donnée en paramètre.");
        System.out.println("\t- GRAPH(boolean[][] adjacents, boolean oriented) : Constructeur qui créé un graphique à partir de la matrice d'adjacence. Le graphique sera orienté ou non en fonction du bouléen donnée en paramètre. Il ne sera pas valué.");
        System.out.println("\t- GRAPH(ArrayList<Integer> d_tableauPrufer) : Constructeur qui créé un graphique a partir d'un tableau de prufer. Le tableau de prufer continue un graphique compressé.");
        System.out.println("\t- GRAPH(String nomFichier) : Constructeur qui créé un graphique à partir d'un fichier texte.");
        System.out.println("Ajout & suppression: ");
        System.out.println("\t- addBridge : GRAPH.addBridge(BRIDGE lienÀAjouter);\n\t\tCette méthode permet d'ajouter un lien au tableau de liens du graphique par le biais de la classe BRIDGE. L'objet BRIDGE contient le sommet d'origine, le sommet cible ainsi que le poids de la liaison.");
        System.out.println("\t- addSummit : GRAPH.addSummit(SUMMIT sommetÀAjouter;\n\t\tCette méthode permet d'ajouter un sommet au tableau de sommet du graphique par le biais de la classe SUMMIT. L'objet SUMMIT contien le numéro du sommet ainsi que l'information qu'on lui attribue.");
        System.out.println("\t- removeBridge : GRAPH.removeBridge(BRIDGE lienÀRetier;\n\t\tCette méthode permet de retirer un lien au tableau de liens du graphique en lui donnant celui à supprimer.");
        System.out.println("\t- removeSummit : GRAPH.removeSummit(SUMMIT sommetÀRetirer;\n\t\tCette méthode permet de retirer un sommet au tableau de sommets du graphique en lui donnant celui à supprimer.");
        System.out.println("Accesseurs :");
        System.out.println("\t- getBridges : GRAPH.getBridges();\n\t\tCette méthode nous permet de récupérer le tableau complet de lien de notre graphique.");
        System.out.println("\t- getSummits : GRAPH.getSummits();\n\t\tCette méthode nous permet de récupérer le tableau complet de sommet de notre graphique.");
        System.out.println("\t- isOriented : GRAPH.isOriented();\n\t\tCette méthode nous renvoie un boolean qui traduit son orientation.");
        System.out.println("\t- isValued : GRAPH.isValued();\n\t\tCette méthode renvoie un boolean qui traduit sa valuation.");
        System.out.println("\t- getFs : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau FS de notre graphique.");
        System.out.println("\t- getAps : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau APS de notre graphique.");
        System.out.println("\t- getAdjacent : GRAPH.getFS();\n\t\tCette méthode permet de récupérer le tableau d'adjacences de notre graphique.");
        System.out.println("Méthodes : ");
        System.out.println("\t- djikstra : GRAPH.djikstra();\n\t\tCette méthode permet de calculer le chemin le plus court selon l'agorithme de djikstra. Il nous renvoie le graphique avec seulement les liens utiles du graphique d'ou est appliqué la méthode.");
        System.out.println("\t- kruskal : GRAPH.kruskal()\n\t\tCette méthode permet de calculer l'arbre couvrant minimum du graphique. Il nous renvoie le graphique sans boucle du graphique d'ou est appliqué la méthode.");
        System.out.println("\t- tarjan : GRAPH.tarjan();\n\t\tCette méthode permet de faire un graph réduit selon l'agorithme de tarjan. Il nous renvoie le graphique réduit du graphique d'ou est appliqué la méthode.");
        System.out.println("\t- toPruferCode : GRAPH.toPruferCode();\n\t\tCette méthode permet de convertir le graphique en un tableau de compressions.");
        System.out.println("\t- pruferToGraph : GRAPH.pruferToGraph();\n\t\tCette méthode permet de créé un graphique à partir du tableau de compression d'un graphique.");
        System.out.println("Sorties:");
        System.out.println("\t- toString : GRAPH.toString();\n\t\tCette méthode permet d'afficher le status du graphique. Il nous affiche donc son tableau de sommets et de liens");
        System.out.println("\t- sortieFichier : sortieFichier(STRING \"cheminDuFicherAinsiQueSonNomEtSonExtension\");\n\t\tCette méthode sauvegarde le graphique dans un fichier passé en paramètre sous le format défini pour la lecture de graphique depuis un fichier (voir le fichier\"entreeAuClavier.txt\")");
        System.out.println("\t- afficherGraph : GRAPH.afficherGraph()\n\t\tCette méthode nous permet d'afficher l'interface graphique de notre graphique ainsi que l'interface utilisateur afin que l'on puisse interagir avec celui-ci.");
    }
}
