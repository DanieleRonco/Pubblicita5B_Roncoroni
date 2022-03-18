package botpubblicita;

import FilePackage.*;
import OpenStreetMapPackage.*;
import TelegramPackage.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Daniele Roncoroni
 */
public class CPubblicita {
    //ATTRIBUTI
    private TelegramAPI tBot;
    private OpenStreetMapAPI osmAPI;
    private FFile fileAPI;
    private List<CUtente> ListaUtenti;
    
    //COSTRUTTORE
    //Costruttore di default
    public CPubblicita(){
        this.tBot = null;
        this.osmAPI = null;
        this.fileAPI = null;
        this.ListaUtenti = null;
    }
    //Costruttore parametrico - TelegramAPI
    public CPubblicita(TelegramAPI tBot, OpenStreetMapAPI osmBot, FFile fileAPI) {
        this.tBot = tBot;
        this.osmAPI = osmBot;
        this.fileAPI = fileAPI;
        this.ListaUtenti = new ArrayList<CUtente>();
        this.CaricaSuLista();
    }
    
    //GET
    public TelegramAPI gettBot() {
        return tBot;
    }
    public OpenStreetMapAPI getOsmBot() {
        return osmAPI;
    }
    public FFile getFileAPI () {
        return fileAPI;
    }
    
    //SET
    public void settBot(TelegramAPI tBot) {
        this.tBot = tBot;
    }
    public void setOsmBot(OpenStreetMapAPI osmBot) {
        this.osmAPI = osmBot;
    }
    public void setFileAPI(FFile fileAPI) {
        this.fileAPI = fileAPI;
    }
    
    //METODI
    //metodo per salvare su file le informazioni degli utenti che scrivono "/citta ..."
    synchronized public void VerificaESalva(List<TUpdate> listaPassata) throws UnsupportedEncodingException, MalformedURLException, IOException, FileNotFoundException, ParserConfigurationException, SAXException{
        for(int i = 0; i < listaPassata.size(); i++){
            TUpdate updateTemp = listaPassata.get(i);
            String testo = updateTemp.getMessaggio().getText();
            
            if(testo.startsWith("/citta")){
                System.out.println("CPubblicita: un elemento contiene '/citta'");
                String citta = testo.substring(7, testo.length());
                
                if(!citta.equals("")){
                    OCoordinate coordinate = osmAPI.TrovaCoordinate(citta);
                
                    CUtente daSalvare = new CUtente(updateTemp.getMessaggio().getChat().getID(), updateTemp.getMessaggio().getFrom().getFirstName(), coordinate.getLatitudine(), coordinate.getLongitudine());

                    int posizione = this.IsPresente(daSalvare);
                    if(posizione != -1){
                        System.out.println("CPubblicita: utente già presente, aggiorno le coordinate");
                        ListaUtenti.get(posizione).setLatitudine(daSalvare.getLatitudine());
                        ListaUtenti.get(posizione).setLongitudine(daSalvare.getLongitudine());
                        
                        try {
                            tBot.sendMessage("Ciao " + ListaUtenti.get(posizione).getNome() + "\nCoordinate aggiornate!", Long.toString(ListaUtenti.get(posizione).getIDChat()));
                        } catch (IOException ex) {
                            Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        String daScrivere = "";
                        for(int j = 0; j < ListaUtenti.size(); j++){
                            daScrivere += ListaUtenti.get(j).toCsv();
                        }
                        fileAPI.AppendOSovrascriviFile(daScrivere, false);
                    } else {
                        System.out.println("CPubblicita: utente non presente, lo salvo");
                        ListaUtenti.add(daSalvare);
                        fileAPI.AppendOSovrascriviFile(daSalvare.toCsv(), true);
                        try {
                            tBot.sendMessage("Ciao " + daSalvare.getNome() + "\nTi do il benvenuto! Da questo momento in poi, riceverai le promozioni nella tua zona", Long.toString(daSalvare.getIDChat()));
                        } catch (IOException ex) {
                            Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                }
            } else System.out.println("CPubblicita: un elemento non contiene citta");
        }
    }
    
    private int IsPresente(CUtente daCercare){
        for(int i = 0; i < ListaUtenti.size(); i++){
            if(ListaUtenti.get(i).getIDChat() == daCercare.getIDChat()) return i;
        }
        return -1;
    }
    
    private void CaricaSuLista(){
        String tutto = fileAPI.Leggi();
        if(!tutto.equals("")){
            String righe[] = tutto.split("\n");
            for(int i = 0; i < righe.length; i++){
                String campi[] = righe[i].split(";");
                CUtente daInserire = new CUtente(Long.parseLong(campi[0]), campi[1], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]));
                ListaUtenti.add(daInserire);
            }
        }
    }
    
    synchronized public void InviaPubblicita(String citta, double raggio, String testo) throws UnsupportedEncodingException, MalformedURLException, IOException, FileNotFoundException, ParserConfigurationException, SAXException{
        System.out.println("CPubblicita: invio la pubblicità agli utenti");
        
        OCoordinate coordinateCitta = osmAPI.TrovaCoordinate(citta);
        
        for(int i = 0; i < ListaUtenti.size(); i++){
            OCoordinate cTemp = new OCoordinate(ListaUtenti.get(i).getLatitudine(), ListaUtenti.get(i).getLongitudine());
            double distanza = osmAPI.DistanzaTraDuePunti(coordinateCitta, cTemp);
            
            if(distanza < raggio){
                System.out.println("\tCPubblicita: pubblicità inviata ad un utente");
                String testoPubblicita = "Ciao, " + ListaUtenti.get(i).getNome() + ",\nTi propongo questa offerta:\n" + testo;
                try {
                    tBot.sendMessage(testoPubblicita, Long.toString(ListaUtenti.get(i).getIDChat()));
                } catch (InterruptedException ex) {
                    Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}