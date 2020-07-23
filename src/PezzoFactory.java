//file scritto da Cappellotto Lorenzo, matricola 1188257
import java.util.*;

public class PezzoFactory {
    public static final int NUM_TIPOLOGIE_PEZZI = 3;

    public static List<Character> listaTipologiePezzi;//non e' final ma e' una unmodifiableList
    //deve poter essere scritto solo dalla classe ma letto da tutti

    private static int[] numPezziPerTipo = new int[NUM_TIPOLOGIE_PEZZI];
    private static Pezzo[] istanzaPerTipo = new Pezzo[NUM_TIPOLOGIE_PEZZI];

    static{
        //creo la lista contenente le tipologie... i caratteri per ora... si puo' migliorare
        ArrayList<Character> lista = new ArrayList<Character>();
        lista.add('O');
        lista.add('N');
        lista.add('E');
        listaTipologiePezzi = Collections.unmodifiableList(lista);
    }
    public PezzoFactory(){
        //eager initialization singleton
        //ho decido di adottare questa polica dato che i pezzi non sono oggetti pesanti e sono pochi
        //dal mio punto di vista e' la soluzione piu' pulita
        for(int ist=0;ist < NUM_TIPOLOGIE_PEZZI;ist++) {
            istanzaPerTipo[ist] = new Pezzo(listaTipologiePezzi.get(ist));
            //numPezziPerTipo[ist]=0;
        }
    }

    public Pezzo getIstanza (char tipologia) throws TipologiaNotFoundException{//un insieme tra factory e singleton
        if(listaTipologiePezzi.indexOf(tipologia) == -1) throw new TipologiaNotFoundException();

        int indice = listaTipologiePezzi.indexOf(tipologia);
        numPezziPerTipo[indice]++;
        return istanzaPerTipo[indice];
        //singleton, eager initialization, di conseguenza qui non vi possone essere pezzi non gia' inizializzati
    }


    public static int getNumPezziPerTipo(char tipologia) {
        if(listaTipologiePezzi.indexOf(tipologia) == -1) return 0; //non lancio errore

        return numPezziPerTipo[listaTipologiePezzi.indexOf(tipologia)];
    }

    public class TipologiaNotFoundException extends Exception{}
}
