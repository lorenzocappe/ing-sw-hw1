//file scritto da Cappellotto Lorenzo, matricola 1188257
public class Mappa {

    private int N;
    private int M;
    private Casella[][] griglia;
    private PezzoFactory factoryDiPezzi;


    public Mappa(int numRighe,int numColonne,char[][] tipologiaCaselle) throws Casella.TipologiaNotFoundException{
        if(numRighe<=0 || numColonne <=0){/*errore*/}

        N = numRighe;
        M = numColonne;
        griglia = new Casella[N][M];

        //if(){} controllo che tipologia caselle abbia dimensione nxm
        for(int m=0;m<M;m++){
            for(int n=0;n<N;n++){
                griglia[n][m] = new Casella(tipologiaCaselle[n][m]);
            }
        }

        factoryDiPezzi = new PezzoFactory();
        //se tutto finisce bene segnalo!
    }
    public void addPezzo(char p,int x,int y){
        //non posso avere scacchiera non inizializzata perche' avviene nel costruttore

        //if(xm,yn,N,M)
        try{
            griglia[y][x].addPezzo(factoryDiPezzi.getIstanza(p));
        }catch(Casella.maxNumOfPiecesException ce){
            System.out.println("max raggiunto, errore!");
        }catch(PezzoFactory.TipologiaNotFoundException pe){
            System.out.println("tipo non esistente, errore!");
        }
    }

    public int[] getNumPezzi(){
        int[] ris = new int[PezzoFactory.NUM_TIPOLOGIE_PEZZI+1];
        for(int i=0;i<PezzoFactory.NUM_TIPOLOGIE_PEZZI;i++){
            ris[1+i]=PezzoFactory.getNumPezziPerTipo(PezzoFactory.listaTipologiePezzi.get(i));
            ris[0]+=PezzoFactory.getNumPezziPerTipo(PezzoFactory.listaTipologiePezzi.get(i));
        }
        return ris;
    }
    public int[] maxDifesa(char rifTemporale) throws Casella.RifTemporaleNotFoundException{
        int[] ris = {0,0};
        double maxi=0;
        double temp;
        for(int m=0;m<M;m++) {
            for(int n=0;n<N;n++){
                temp = griglia[n][m].valDif(rifTemporale);
                //System.out.println(m+" "+n+" "+temp);
                if(temp>maxi) {
                    maxi = temp;
                    ris[0] = m;
                    ris[1] = n;
                }
            }
        }
        //System.out.println(maxi);
        return ris;
    }
    public int[] maxAttacco(char rifTemporale) throws Casella.RifTemporaleNotFoundException{
        int[] ris = {0,0};
        double maxi=0;
        double temp;
        for(int m=0;m<M;m++) {
            for (int n = 0; n < N; n++) {
                temp = griglia[n][m].valAtt(rifTemporale);
                //System.out.println(m+" "+n+" "+temp);
                if (temp > maxi) {
                    maxi = temp;
                    ris[0] = m;
                    ris[1] = n;
                }
            }
        }
        //System.out.println(maxi);
        return ris;
    }

    public int[] maxNumPezziUguali(){
        int[] ris = {0,0};
        int maxi=0;
        int temp;
        for(int m=0;m<M;m++) {
            for(int n=0;n<N;n++){
                temp = griglia[n][m].maxNumPezziUguali();
                if(temp > maxi) {
                    maxi = temp;
                    ris[0] = m;
                    ris[1] = n;
                }
            }
        }
        //System.out.println(maxi);
        return ris;
    }

    /*public char[][] getTipologiaCaselle(){
        char[][] res = new char[N][M];
        for(int n=0;n<N;n++) {
            for(int m=0;m<M;m++) {
                res[n][m] = griglia[n][m].getTipologia();
            }
        }
        return res;
    }*/

    public String toString(){
        String stringa="";
        for(int n=0;n<N;n++) {
            for(int m=0;m<M;m++) {
                stringa+=griglia[n][m].getTipologia();
            }stringa+="\n";
        }
        return stringa;
    }
}
