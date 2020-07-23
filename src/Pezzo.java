//file scritto da Cappellotto Lorenzo, matricola 1188257
import java.util.ArrayList;

public class Pezzo {
    private ArrayList<Moltiplicatore> moltiplicatori;
    private char tipologia;
    private double attacco;
    private double difesa;

    public Pezzo(char tipologia){
        //se tipologia non presente? non verifico qui, verifico all'intero della factory dove ho la lista delle tipologie

        this.tipologia = tipologia;
        moltiplicatori = new ArrayList<Moltiplicatore>();

        switch(tipologia){//hardcoded, sarebbe possibile avere anche un file di configurazione o una base dati in cui memorizzarli
            case 'O':
                attacco = 4;
                difesa = 4;
                moltiplicatori.add(new Moltiplicatore('*','G',-0.5,-0.5));
                moltiplicatori.add(new Moltiplicatore('*','N',+0.5,+0.5));
                break;
            case 'E':
                attacco = 5;
                difesa = 2;;
                moltiplicatori.add(new Moltiplicatore('B','*',+1.0,+0.0));
                break;
            case 'N':
                attacco = 2;
                difesa = 5;
                moltiplicatori.add(new Moltiplicatore('M','*',+0.0,+1.0));
                break;
        }
    }

    public double getAttacco(char tipoC, char rifT){
        double res = attacco;
        Moltiplicatore moltTemp;
        for(int i=0;i<moltiplicatori.size();i++){
            moltTemp = moltiplicatori.get(i);
            if((moltTemp.getTipologiaC() == tipoC || moltTemp.getTipologiaC() == '*') && (moltTemp.getRiferimentoT() == rifT || moltTemp.getRiferimentoT() == '*')){
                res *= 1+moltTemp.getMoltAttacco();
                //System.out.println(rifT+" "+tipoC+" "+moltTemp.getMoltAttacco());
            }
        }
        return res;
    }
    public double getDifesa(char tipoC, char rifT){
        double res = difesa;
        Moltiplicatore moltTemp;
        for(int i=0;i<moltiplicatori.size();i++){
            moltTemp = moltiplicatori.get(i);
            if((moltTemp.getTipologiaC() == tipoC || moltTemp.getTipologiaC() == '*') && (moltTemp.getRiferimentoT() == rifT || moltTemp.getRiferimentoT() == '*')){
                res *= 1+moltTemp.getMoltDifesa();
                //System.out.println(rifT+" "+tipoC+" "+moltTemp.getMoltDifesa());
            }
        }
        return res;
    }
    public char getTipologia(){ return tipologia; }

}
