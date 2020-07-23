//file scritto da Cappellotto Lorenzo, matricola 1188257
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Casella {
    public static final int NUM_TIPOLOGIE_CASELLE = 3;
    public static final int NUM_RIF_TEMPORALI = 2;
    public static final int NUM_MAX_PEZZI = 5;

    public static List<Character> listaTipologieCaselle;//non e' final ma e' una unmodifiableList
    public static List<Character> listaRiferimentiTemporali;//non e' final ma e' una unmodifiableList
    static{
        //creo la lista contenente i riferimenti temporali...
        ArrayList<Character> lista = new ArrayList<Character>();
        lista.add('N');
        lista.add('G');
        listaRiferimentiTemporali = Collections.unmodifiableList(lista);

        //creo la lista contenente le tipologie...
        lista = new ArrayList<Character>();
        lista.add('P');
        lista.add('B');
        lista.add('M');
        listaTipologieCaselle = Collections.unmodifiableList(lista);
    }

    private char tipologia;
    private Pezzo[] listaPezzi;
    private int numeroPezziPresenti;

    public Casella(char tipologiaC) throws TipologiaNotFoundException{
        if(listaTipologieCaselle.indexOf(tipologiaC) == -1) throw new TipologiaNotFoundException();

        tipologia = tipologiaC;
        numeroPezziPresenti = 0;
        listaPezzi = new Pezzo[NUM_MAX_PEZZI];
    }

    public boolean isEmpty(){ return (numeroPezziPresenti == 0); }

    public void addPezzo(Pezzo p) throws maxNumOfPiecesException{
        if(numeroPezziPresenti >= NUM_MAX_PEZZI) throw new maxNumOfPiecesException();

        if(p != null){
            listaPezzi[numeroPezziPresenti] = p;
            numeroPezziPresenti++;
        }
    }

    public char getTipologia(){return tipologia;}

    /*public int[] totalePezziPerTipo(){
        int[] temp = new int[PezzoFactory.NUM_TIPOLOGIE_PEZZI+1];
        for(int i=0;i<numeroPezziPresenti;i++){
            temp[1+PezzoFactory.listaTipologiePezzi.indexOf(listaPezzi[i].getTipologia())]++;
            temp[0]++;
        }
        return temp;
    }*/
    public double valDif(char rifTemporale) throws RifTemporaleNotFoundException{
        if(listaRiferimentiTemporali.indexOf(rifTemporale) == -1) throw new RifTemporaleNotFoundException();

        double res=0;
        for(int i=0;i<numeroPezziPresenti;i++){
            res += listaPezzi[i].getDifesa(tipologia,rifTemporale);//attento riferimento temporale
        }
        return res;
    }
    public double valAtt(char rifTemporale) throws RifTemporaleNotFoundException{
        if(listaRiferimentiTemporali.indexOf(rifTemporale) == -1) throw new RifTemporaleNotFoundException();

        double res=0;
        for(int i=0;i<numeroPezziPresenti;i++){
            res += listaPezzi[i].getAttacco(tipologia,rifTemporale);//attento riferimento temporale
        }
        return res;
    }
    public int maxNumPezziUguali(){
        int[] temp = new int[PezzoFactory.NUM_TIPOLOGIE_PEZZI];
        for(int i=0;i<numeroPezziPresenti;i++){
            temp[PezzoFactory.listaTipologiePezzi.indexOf(listaPezzi[i].getTipologia())]++;
        }
        int maxi = 0;
        for(int i=0;i<PezzoFactory.NUM_TIPOLOGIE_PEZZI;i++){
            if(temp[i] > maxi) maxi=temp[i];
        }
        return maxi;
    }

    public class maxNumOfPiecesException extends Exception{}
    public class TipologiaNotFoundException extends Exception{}
    public class RifTemporaleNotFoundException extends Exception{}
}
