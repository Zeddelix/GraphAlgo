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

        GRAPH g = new GRAPH();
        g.afficherGraph();

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
        /*SUMMIT s1 = new SUMMIT("1");
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
        GRAPH g = new GRAPH(l1,true,bl1);*/





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

}
