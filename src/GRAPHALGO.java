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

        /* ### TEST CONSTRUCTEUR DE BASE    ######

        SUMMIT s1 = new SUMMIT("1"); // Creation d'un graph pour les tests - 7 sommets et 8 liens
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
        GRAPH g = new GRAPH(l1,bl1);
        g.afficherGraph();
        */


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

        /* #### TEST ECRITURE GRAPH DANS UN FICHIER TEXT ####
        GRAPH g = new GRAPH("entreeAuClavier");
        g.afficherGraph();
        g.sortieFichier("sortieGraph2");
        */

        int[] prufer = new int[7];
        prufer[0]=2;
        prufer[1]=2;
        prufer[2]=2;
        prufer[3]=5;
        prufer[4]=6;
        prufer[5]=5;
        prufer[6]=7;
        GRAPH g = new GRAPH();
        g.pruferToGraph(prufer).afficherGraph();


        //g.djikstra(g.getSpecificSummitByKey(1)).afficherGraph();

        //GRAPH g1 = new GRAPH("entreeAuClavier");
        //GRAPH g1 = new GRAPH();
        //g1.afficherGraph();





        //GRAPH g = new GRAPH(l1,false,bl1,false);// TEST CONSTRUCTEUR SOMMET+LIENS OK + GESTION DU PASSAGE ORIENTE / NON ORIENTE


        /*boolean[][] Adj = new boolean[6][6];
        for (int i=0; i<6;i++){
            for(int j=0; j<6; j++){
                Adj[i][j]=false;
            }
        }
        Adj[2][1]=true;
        Adj[2][2]=true;
        Adj[3][1]=true;
        Adj[3][2]=true;
        Adj[4][1]=true;
        Adj[4][2]=true;
        Adj[5][3]=true;
        Adj[4][5]=true;

        GRAPH g = new GRAPH(Adj,false);
        System.out.println(g.toString());*/ // TEST CONSTRUCTEUR ADJACENT OK + GESTION DU PASSAGE ORIENTE / NON ORIENTE


        /*int[] Fs = new int[14];
        Fs[0]=14;
        Fs[1]=2;
        Fs[2]=3;
        Fs[3]=4;
        Fs[4]=0;
        Fs[5]=2;
        Fs[6]=3;
        Fs[7]=4;
        Fs[8]=0;
        Fs[9]=5;
        Fs[10]=0;
        Fs[11]=0;
        Fs[12]=4;
        Fs[13]=0;

        int[] APS = new int[6];
        APS[0]=6;
        APS[1]=1;
        APS[2]=5;
        APS[3]=9;
        APS[4]=11;
        APS[5]=12;

        GRAPH g = new GRAPH(Fs, true, APS, false);*/

        /*GRAPH g = new GRAPH("entreeAuClavier");
        System.out.println(g.toString());
        g.sortieFichier("sortieGraph");*/
        /*

        System.out.println("\nDjikstra :");
        g.djikstra(s1);


        System.out.println("\nTarjan : :");
        System.out.println(g.tarjan());
        g.sortieFichier("sortieGraph");
        */


    }
}
