//file scritto da Cappellotto Lorenzo, matricola 1188257
import java.io.*;
import java.util.*;

public class Main {
    private static String fileTipologiaCelle = "mappa_tipologie.txt";
    private static String filePosizionePezzi = "mappa_pezzi.txt";

    private static Map<String,Character> mappaTipologiaPezzi;//non e' final ma e' una unmodifiableMap
    private static Map<String,Character> mappaTipologiaCaselle;
    private static Map<String,Character> mappaRiferimentoTemporale;

    static{
        //creo mappa di tipologie e riferimenti temporali...
        //sia di caselle che di pezzi
        //...si puo' migliorare
        HashMap<String,Character> mappaTemporanea = new HashMap<String,Character>();
        mappaTemporanea.put("pianura",'P');
        mappaTemporanea.put("bosco",'B');
        mappaTemporanea.put("montagna",'M');
        mappaTipologiaCaselle = Collections.unmodifiableMap(mappaTemporanea);

        mappaTemporanea = new HashMap<String, Character>();
        mappaTemporanea.put("orco",'O');
        mappaTemporanea.put("nano",'N');
        mappaTemporanea.put("elfo",'E');
        mappaTipologiaPezzi = Collections.unmodifiableMap(mappaTemporanea);

        mappaTemporanea = new HashMap<String, Character>();
        mappaTemporanea.put("giorno",'G');
        mappaTemporanea.put("notte",'N');
        mappaRiferimentoTemporale = Collections.unmodifiableMap(mappaTemporanea);
    }

    public static void main(String[] args) {
        int N, M;
        char[][] tipoCaselle;
        Mappa tavoloDiGioco;

        FileReader fr;
        BufferedReader br;
        String[] lines = new String[3];
        try {
            String line;
            fr = new FileReader(fileTipologiaCelle);
            br = new BufferedReader(fr);

            //System.out.println(fileTipologiaCelle);

            //lettura dimensioni mappa
            if ((line = br.readLine()) != null && line.matches("\\d+\\s\\d+")) {
                //System.out.println(line);
                String[] parti = line.split("\\s");
                M = Integer.parseInt(parti[0]);
                N = Integer.parseInt(parti[1]);

                if (N <= 0 || M <= 0) {/*errore!!*/
                    System.out.println("ERRORE! LE DIMENSIONI DELLA MAPPA NON POSSONO ESSERE NEGATIVE");
                    return;
                }
            } else {/*errore!!*/
                System.out.println("ERRORE! IMPOSSIBILE LEGGERE LE DIMENSIONI DELLA MAPPA");
                return;
            }
            //M e N inizializzati

            tipoCaselle = new char[N][M];

            //genero random la tipologia di tutte le caselle prima di inserire quelle specificate dall'utente
            //in seguito sovrascrivero' solo quelle desiderate dall'utente
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    tipoCaselle[n][m] = Casella.listaTipologieCaselle.get((int) (Math.round(Math.random() * (Casella.listaTipologieCaselle.size() - 1))));
                }
            }

            while (true) {
                lines[0] = br.readLine();
                lines[1] = br.readLine();
                lines[2] = br.readLine();

                if (lines[0] != null && lines[1] != null && lines[2] != null) {
                    if (lines[0].matches("\\d+") && lines[1].matches("\\d+") && lines[2].matches("\\w+")) {
                        //System.out.println(lines[0]);
                        //System.out.println(lines[1]);
                        //System.out.println(lines[2]);

                        int x = Integer.parseInt(lines[0]);
                        int y = Integer.parseInt(lines[1]);

                        if (!mappaTipologiaCaselle.containsKey(lines[2])) {/*errore!!*/
                            System.out.println("ERRORE! LA TIPOLOGIA DI CASELLA INSERITA NON ESISTE");
                            return;
                        } else if (x < 0 || y < 0) {/*errore!!*/
                            System.out.println("ERRORE! LE COORDINATE DELLA CASELLA DEVONO ESSERE POSITIVE");
                            return;
                        } else if (x >= M || y >= N) {/*errore!!*/
                            System.out.println("ERRORE! LE COORDINATE DELLA CASELLA NON POSSONO ESSERE SUPERARE LE DIMENSIONI DELLA MAPPA");
                            return;
                        } else {
                            tipoCaselle[y][x] = mappaTipologiaCaselle.get(lines[2]);
                        }
                    } else {/*errore!!*/
                        System.out.println("ERRORE! IMPOSSIBILE LEGGERE LA SPECIFICA DELLE CASELLE");
                        return;
                    }
                } else {
                    break;
                }
            }
            br.close();
            fr.close();
        }catch (FileNotFoundException fnfe) {
            //fnfe.printStackTrace();
            System.out.println("ERRORE! FILE "+fileTipologiaCelle+" NON ESISTENTE");
            return;
        }catch (IOException ioe) {
            //ioe.printStackTrace();
            System.out.println("ERRORE! E' AVVENUTO UN PROBLEMA DURANTE LA LETTURA DEL FILE");
            return;
        }

        try{
            tavoloDiGioco = new Mappa(N, M, tipoCaselle);
        }catch (Casella.TipologiaNotFoundException ce){
            //ce.printStackTrace();
            System.out.println("ERRORE!");
            return;
        }

        try{
            //System.out.println(filePosizionePezzi);
            fr = new FileReader(filePosizionePezzi);
            br = new BufferedReader(fr);

            while (true) {
                lines[0] = br.readLine();
                lines[1] = br.readLine();
                lines[2] = br.readLine();

                if(lines[0] != null && lines[1] != null && lines[2] != null){
                    if(lines[0].matches("\\d+") && lines[1].matches("\\d+") && lines[2].matches("\\w+")){
                        //System.out.println(lines[0]);
                        //System.out.println(lines[1]);
                        //System.out.println(lines[2]);

                        int x = Integer.parseInt(lines[0]);
                        int y = Integer.parseInt(lines[1]);

                        if(!mappaTipologiaPezzi.containsKey(lines[2])){/*errore!!*/
                            System.out.println("ERRORE! LA TIPOLOGIA DI PEZZO INSERITA NON ESISTE");
                            return;
                        }
                        else if(x<0 || y<0){/*errore!!*/
                            System.out.println("ERRORE! LE COORDINATE DELLA CASELLA DEVONO ESSERE POSITIVE");
                            return;
                        }
                        else if(x>=M || y>=N){/*errore!!*/
                            System.out.println("ERRORE! LE COORDINATE DELLA CASELLA NON POSSONO SUPERARE LE DIMENSIONI DELLA MAPPA");
                            return;
                        }
                        else{
                            tavoloDiGioco.addPezzo(mappaTipologiaPezzi.get(lines[2]),x,y);
                        }
                    }
                    else{/*errore!!*/
                        System.out.println("ERRORE! IMPOSSIBILE LEGGERE LA SPECIFICA DEI PEZZI");
                        return;
                    }
                }
                else{
                    break;
                }
            }
            br.close();
            fr.close();

        } catch (FileNotFoundException fnfe) {
            //fnfe.printStackTrace();
            System.out.println("ERRORE! FILE "+fileTipologiaCelle+" NON ESISTENTE");
            return;
        }catch (IOException ioe) {
            //ioe.printStackTrace();
            System.out.println("ERRORE! E' AVVENUTO UN PROBLEMA DURANTE LA LETTURA DEL FILE");
            return;
        }

        int[] numeroPezzi;
        int[] coordinate;

        numeroPezzi = tavoloDiGioco.getNumPezzi();
        System.out.println("Totale:"+numeroPezzi[0]+" Orchi:"+numeroPezzi[1]+" Nani:"+numeroPezzi[2]+" Elfi:"+numeroPezzi[3]+"\n");

        try {
            coordinate = tavoloDiGioco.maxDifesa(mappaRiferimentoTemporale.get("giorno"));
            System.out.println("la cella con difesa massimo di giorno e': ("+coordinate[0]+","+coordinate[1]+")\n");

            coordinate = tavoloDiGioco.maxDifesa(mappaRiferimentoTemporale.get("notte"));
            System.out.println("la cella con difesa massimo di notte e': ("+coordinate[0]+","+coordinate[1]+")\n");

            coordinate = tavoloDiGioco.maxAttacco(mappaRiferimentoTemporale.get("giorno"));
            System.out.println("la cella con attacco massima di giorno e': ("+coordinate[0]+","+coordinate[1]+")\n");

            coordinate = tavoloDiGioco.maxAttacco(mappaRiferimentoTemporale.get("notte"));
            System.out.println("la cella con attacco massima di notte e': ("+coordinate[0]+","+coordinate[1]+")\n");

            coordinate = tavoloDiGioco.maxNumPezziUguali();
            System.out.println("la cella con il massimo di pezzi dello stesso tipo e': ("+coordinate[0]+","+coordinate[1]+")\n");

            System.out.println("Questa e' la matrice rappresentante la tipologia delle caselle della mappa ");
            System.out.print(tavoloDiGioco.toString());
        }catch(Casella.RifTemporaleNotFoundException rtnfe){
            //rtnfe.printStackTrace();
            System.out.println("ERRORE! IL RIFERIMENTO TEMPORALE INSERITO NON ESISTE");
            return;
        }
    }
}
