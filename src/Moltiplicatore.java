//file scritto da Cappellotto Lorenzo, matricola 1188257
public class Moltiplicatore {//classe di sola descrizione

    private char tipologiaCasella;
    private char riferimentoTemporale;
    private double moltiplicatoreAttacco;
    private double moltiplicatoreDifesa;

    public Moltiplicatore(char tipoC,char rifT, double moltA, double moltD){
        //se char == '*' allora significa qualsiasi caso
        tipologiaCasella=tipoC;
        riferimentoTemporale=rifT;
        moltiplicatoreAttacco=moltA;
        moltiplicatoreDifesa=moltD;
    }

    public char getTipologiaC(){
        return tipologiaCasella;
    }
    public char getRiferimentoT(){
        return riferimentoTemporale;
    }
    public double getMoltAttacco(){
        return moltiplicatoreAttacco;
    }
    public double getMoltDifesa(){
        return moltiplicatoreDifesa;
    }
}
