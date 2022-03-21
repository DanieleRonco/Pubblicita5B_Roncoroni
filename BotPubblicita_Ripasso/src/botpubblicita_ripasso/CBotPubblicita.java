package botpubblicita_ripasso;

import FilePackage.*;
import HTTPPackage.*;
import OpenStreetMapPackage.*;
import TelegramPackage.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Roncoroni
 */
public class CBotPubblicita {
    private OpenStreetMapAPI osmAPI;
    private TelegramAPI tAPI;
    private FFile fileHelper;
    private HHttp httpHelper;
    private List<CUtente> ListaUtenti;
    
    public CBotPubblicita(){
        this.osmAPI = null;
        this.tAPI = null;
        this.fileHelper = null;
        this.httpHelper = null;
        this.ListaUtenti = null;
    }

    public CBotPubblicita(OpenStreetMapAPI osmAPI, TelegramAPI tAPI, String percorso) {
        this.osmAPI = osmAPI;
        this.tAPI = tAPI;
        this.fileHelper = new FFile(percorso);
        this.httpHelper = new HHttp();
        this.CaricaLista();
        String ok = "";
    }

    public OpenStreetMapAPI getOsmAPI() {
        return osmAPI;
    }
    public TelegramAPI gettAPI() {
        return tAPI;
    }
    public FFile getFileHelper() {
        return fileHelper;
    }
    public HHttp getHttpHelper() {
        return httpHelper;
    }

    public void setOsmAPI(OpenStreetMapAPI osmAPI) {
        this.osmAPI = osmAPI;
    }
    public void settAPI(TelegramAPI tAPI) {
        this.tAPI = tAPI;
    }
    public void setFileHelper(FFile fileHelper) {
        this.fileHelper = fileHelper;
    }
    public void setHttpHelper(HHttp httpHelper) {
        this.httpHelper = httpHelper;
    }
    
    //METODI
    private void CaricaLista(){
        ListaUtenti = new ArrayList<CUtente>();
        
        String lista = fileHelper.Leggi();
        if(lista != null){
            String righe[] = lista.split("\n");
            for(int i = 0; i < righe.length; i++){
                String campi[] = righe[i].split(";");
                
                CUtente utenteDaInserire = new CUtente(Long.parseLong(campi[0]), campi[1], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]));
                ListaUtenti.add(utenteDaInserire);
            }
        }
    }
    
    synchronized public void GestisciUpdates(List<TUpdate> ListaUpdate){
        for(int i = 0; i < ListaUpdate.size(); i++){
            TUpdate update = ListaUpdate.get(i);
            
            if(update.getMessaggio().getText().startsWith("/citta ")){
                System.out.println("CBotPubblicita: il messaggio contiene '/citta '");
                
                String citta = update.getMessaggio().getText().substring(7, update.getMessaggio().getText().length());
                OCoordinate coordinateCitta = osmAPI.TrovaCoordinate(citta);
                
                String visualizzaPosizione = citta + ": " + coordinateCitta.getLatitudine() + " " + coordinateCitta.getLongitudine();
                System.out.println(visualizzaPosizione);
                
                boolean presente = false;
                for(int j = 0; j < ListaUtenti.size(); j++){
                    if(ListaUtenti.get(j).getChatId() == update.getMessaggio().getChat().getID()){
                        try {
                            presente = true;
                            
                            //utente già registrato, perciò aggiorno le coordinate > salvo sul file > invio il messaggio
                            // aggiorno le coordinate
                            ListaUtenti.get(j).setLatitudine(coordinateCitta.getLatitudine());
                            ListaUtenti.get(j).setLongitudine(coordinateCitta.getLongitudine());
                            
                            // salvo su file
                            this.SovrascriviListaFile();
                            
                            // invio il messaggio
                            String nome = update.getMessaggio().getFrom().getFirstName();
                            if(update.getMessaggio().getFrom().getNickname() != "") nome = update.getMessaggio().getFrom().getNickname();
                            
                            String testoMessaggio = "Ciao, " + nome + "\nLa tua posizione è stata aggiornata a queste coordinate:\n" + visualizzaPosizione;
                            tAPI.sendMessage(testoMessaggio, String.valueOf(update.getMessaggio().getChat().getID()));
                        } catch (IOException ex) {
                            Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(!presente){
                    try {
                        //utente non ancora registrato, perciò lo aggiungo alla lista > lo salvo sul file > invio il messaggio
                        // aggiungo alla lista
                        String nome = update.getMessaggio().getFrom().getFirstName();
                        if(update.getMessaggio().getFrom().getNickname() != "") nome = update.getMessaggio().getFrom().getNickname();
                        CUtente daInserire = new CUtente(update.getMessaggio().getChat().getID(), nome, coordinateCitta.getLatitudine(), coordinateCitta.getLongitudine());
                        ListaUtenti.add(daInserire);
                        
                        // salvo sul file
                        this.AggiungiListaFile();
                        
                        // invio il messaggio
                        String testoMessaggio = "Ciao, " + nome + "\nRegistrazione effettuata!\nLa tua posizione è stata salvata a queste coordinate:\n" + visualizzaPosizione;
                        tAPI.sendMessage(testoMessaggio, String.valueOf(update.getMessaggio().getChat().getID()));
                    } catch (IOException ex) {
                        Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else System.out.println("CBotPubblicita: il messaggio non contiene '/citta '");
        }
    }
    
    private void SovrascriviListaFile(){
        String daScrivere = "";
        for(int i = 0; i < ListaUtenti.size(); i++){
            daScrivere += ListaUtenti.get(i).toCsv();
        }
        fileHelper.AppendOSovrascriviFile(daScrivere, false);
    }
    
    private void AggiungiListaFile(){
        String daScrivere = ListaUtenti.get(ListaUtenti.size() - 1).toCsv();
        fileHelper.AppendOSovrascriviFile(daScrivere, true);
    }
    
    synchronized public void InviaPubblicita(String citta, Double raggio, String testo){
        int c = 0;
        this.CaricaLista();
        OCoordinate coordinateCentro = osmAPI.TrovaCoordinate(citta);
        String testoMessaggio;
        for(int i = 0; i < ListaUtenti.size(); i++){
            OCoordinate coodinateTemp = new OCoordinate(ListaUtenti.get(i).getLatitudine(), ListaUtenti.get(i).getLongitudine());
            if(osmAPI.DistanzaTraDuePunti(coordinateCentro, coodinateTemp) < raggio) {
                try {
                    testoMessaggio = "Ciao " + ListaUtenti.get(i).getNome() + "\nTi propongo questa offerta:\n" + testo;
                    tAPI.sendMessage(testoMessaggio, String.valueOf(ListaUtenti.get(i).getChatId()));
                    c++;
                } catch (IOException ex) {
                    Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CBotPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        System.out.println("CBotPubblicita: pubblicità inviata a " + c + " utenti");
    }
}