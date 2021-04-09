import java.util.ArrayList;
import java.util.List;

public class GRAPHALGO {

    public static void main(String args[]) {
        /*SUMMIT s1 = new SUMMIT("1");
        SUMMIT s2 = new SUMMIT("2");
        SUMMIT s3 = new SUMMIT ("3");
        SUMMIT s4 = new SUMMIT ("4");
        SUMMIT s5 = new SUMMIT ("5");

        BRIDGE b1 = new BRIDGE(s1,s2);
        BRIDGE b2 = new BRIDGE(s1,s3);
        BRIDGE b3 = new BRIDGE(s1,s4);
        BRIDGE b4 = new BRIDGE (s2,s2);
        BRIDGE b5 = new BRIDGE (s3,s5);
        BRIDGE b6 = new BRIDGE (s5,s4);
        BRIDGE b7 = new BRIDGE (s2,s4);
        BRIDGE b8 = new BRIDGE (s2,s3);


        List<SUMMIT> l1 = new ArrayList<>() ;
        List<BRIDGE> bl1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        l1.add(s4);
        l1.add(s5);
        bl1.add(b1);
        bl1.add(b2);
        bl1.add(b3);
        bl1.add(b4);
        bl1.add(b5);
        bl1.add(b6);
        bl1.add(b7);
        bl1.add(b8);
        GRAPH g = new GRAPH(l1,false,bl1,false);*/// TEST CONSTRUCTEUR SOMMET+LIENS OK + GESTION DU PASSAGE ORIENTE / NON ORIENTE


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


        int[] Fs = new int[14];
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

        GRAPH g = new GRAPH(Fs, true, APS, false);
        System.out.println(g.toString());



    }
}
