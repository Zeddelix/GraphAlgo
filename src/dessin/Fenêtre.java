package dessin;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Fenêtre extends JFrame {

    public Fenêtre() {
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(1600, 1600);
        this.setTitle("Représentation du GRAPH");
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().add(new Zone());

    }


    public static void main(String[] args) {
        new Fenêtre().setVisible(true);
    }
}

class Zone extends JComponent {

    public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
        g.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
    }

    public void paintComponent(Graphics g) {
        lectureFichierGraph("entreeAuClavier");
        ArrayList<Integer> Random = new ArrayList<>();
        for (int i = 1; i < summits.size()+1; i++){
            int r = (int)(10 + Math.random() * ( 20));
            int i1,y1;
            i1 = (int)(500 + (r*(-i)*Math.pow(-1,i)));
            y1 = (int)(10 + 50*i);
            drawCircle(g,i1, y1, 25);
            g.drawString(Integer.toString(i),i1,y1);
            Random.add(r);

        }


        int aux=0;
        if (this.valued==false){
            for (int j=0; j<bridges.size();j+=2){
                int i1,y1,i2,y2;
                int rand1 = Random.get((summits.get(bridges.get(j)-1)-1));
                int rand2 = Random.get(summits.get(bridges.get(j+1)-1)-1);
                i1 = (int) (500 + (rand1*(-(bridges.get(j)))*Math.pow(-1,bridges.get(j))));
                y1 = (int) (10 + 50*bridges.get(j));
                i2 = (int) (500 + (rand2*(-(bridges.get(j+1)))*Math.pow(-1,bridges.get(j+1))));
                y2 = (int) (10 + 50*bridges.get(j+1));
                g.drawLine(i1,y1,i2,y2);
                aux++;
            }
        }
        else{
                for (int j=0; j<bridges.size();j+=3){
                    int i1,y1,i2,y2;
                    int weight;
                    int rand1 = Random.get((summits.get(bridges.get(j)-1)-1));
                    int rand2 = Random.get(summits.get(bridges.get(j+1)-1)-1);
                    i1 = (int) (500+rand1*(-(bridges.get(j)))*Math.pow(-1,bridges.get(j)));
                    y1 = (int) (10 + 50*bridges.get(j));
                    i2 = (int) (500+rand2*(-(bridges.get(j+1)))*Math.pow(-1,bridges.get(j+1)));
                    y2 = (int) (10 + 50*bridges.get(j+1));
                    weight = bridges.get(j+2);
                    g.drawLine(i1,y1,i2,y2);
                    g.drawString(Integer.toString(weight), ((i1+i2)/2), ((y1+y2)/2)) ;
                    aux++;
                }
        }


    }


    private List<Integer> summits;
    private List<Integer> bridges;
    private boolean oriented, valued;

    public void lectureFichierGraph(String nomFichier) {
        String chaine = "";
        String fichier = nomFichier + ".txt";
        System.out.println(fichier);
        ArrayList<Integer> summitstxt = new ArrayList<>();
        ArrayList<Integer> bridgestxt = new ArrayList<>();


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


            for (int i = 1; i < nombreSommet+1; i++) {
                summitstxt.add(i);
            }
            summits = summitstxt;

            while ((line = br.readLine()) != null) {
                chaine += line;
                chaine += ",";
            }
            System.out.println(chaine);
            String[] arrOfStr = chaine.split(",");
            if (this.valued == false) {
                for (int i = 0; i < arrOfStr.length - 1; i += 2) {
                    ind1 = Integer.parseInt(arrOfStr[i]);
                    ind2 = Integer.parseInt(arrOfStr[i + 1]);
                    bridgestxt.add(ind1);
                    bridgestxt.add(ind2);

                }
            } else {
                for (int i = 0; i < arrOfStr.length - 2; i += 3) {
                    ind1 = Integer.parseInt(arrOfStr[i]);
                    ind2 = Integer.parseInt(arrOfStr[i + 1]);
                    ind3 = Integer.parseInt(arrOfStr[i + 2]);
                    bridgestxt.add(ind1);
                    bridgestxt.add(ind2);
                    bridgestxt.add(ind3);
                }
            }
            bridges = bridgestxt;
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}