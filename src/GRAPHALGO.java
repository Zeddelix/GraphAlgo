import java.util.ArrayList;
import java.util.List;

public class GRAPHALGO {

    public static void main(String args[]) {
        SUMMIT s1 = new SUMMIT("Français");
        SUMMIT s2 = new SUMMIT("Math");
        SUMMIT s3 = new SUMMIT ("Anglais");
        BRIDGE b1 = new BRIDGE(s1,s2);
        BRIDGE b2 = new BRIDGE (s1,s3,5);

        List<SUMMIT> l1 = new ArrayList<>() ;
        List<BRIDGE> bl1 = new ArrayList<>();
        l1.add(s1);
        l1.add(s2);
        l1.add(s3);
        bl1.add(b1);
        bl1.add(b2);
        GRAPH g = new GRAPH(l1,true,bl1,false);


        System.out.println(s1.toString());
        System.out.println(s2.toString());
        System.out.println(s3.toString());
        System.out.println(b1.toString());
        System.out.println(b2.toString());
        System.out.println(g.toString());

    }
}
