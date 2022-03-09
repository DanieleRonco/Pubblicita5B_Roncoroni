package botpubblicita;

import OpenStreetMapAPIPackage.*;
import TelegramAPIPackage.*;
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
    private OpenStreetMapAPI osmBot;
    private List<CUtente> ListaUtenti;
    
    //COSTRUTTORE
    //Costruttore di default
    public CPubblicita(){
        this.tBot = null;
        this.osmBot = null;
        this.ListaUtenti = null;
    }
    //Costruttore parametrico - TelegramAPI
    public CPubblicita(TelegramAPI tBot, OpenStreetMapAPI osmBot) throws IOException{
        this.tBot = tBot;
        this.osmBot = osmBot;
        this.ListaUtenti = new ArrayList<CUtente>();
        this.CaricaSuLista();
    }
    
    //GET
    public TelegramAPI gettBot() {
        return tBot;
    }
    public OpenStreetMapAPI getOsmBot() {
        return osmBot;
    }
    
    //SET
    public void settBot(TelegramAPI tBot) {
        this.tBot = tBot;
    }
    public void setOsmBot(OpenStreetMapAPI osmBot) {
        this.osmBot = osmBot;
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
                    OCoordinate coordinate = osmBot.TrovaCoordinate(citta);
                
                    CUtente daSalvare = new CUtente(updateTemp.getMessaggio().getChat().getID(), updateTemp.getMessaggio().getFrom().getFirstName(), coordinate.getLatitudine(), coordinate.getLongitudine());

                    if(this.IsPresente(daSalvare)){
                        System.out.println("CPubblicita: utente già presente, aggiorno le coordinate");
                        this.SovrascriviFile();
                    } else {
                        System.out.println("CPubblicita: utente non presente, lo salvo");
                        ListaUtenti.add(daSalvare);
                        this.SalvaSuFile(daSalvare.toCsv());
                    }  
                }                             
            } else System.out.println("CPubblicita: un elemento non contiene citta");
        }
    }
    
    private boolean IsPresente(CUtente daCercare){
        for(int i = 0; i < ListaUtenti.size(); i++){
            if(ListaUtenti.get(i).getIDChat() == daCercare.getIDChat()){
                ListaUtenti.get(i).setLatitudine(daCercare.getLatitudine());
                ListaUtenti.get(i).setLongitudine(daCercare.getLongitudine());
                
                try {
                    tBot.sendMessage("Ciao " + ListaUtenti.get(i).getNome() + "\nCoordinate aggiornate!", Long.toString(ListaUtenti.get(i).getIDChat()));
                } catch (IOException ex) {
                    Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CPubblicita.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
        }
        return false;
    }
    
    private void SalvaSuFile(String daScrivere) throws IOException{
        File fileLoaded = new File("lista.csv");
	if(!fileLoaded.exists()) {
            fileLoaded.createNewFile();
	}
		
	FileWriter w;
	w = new FileWriter("lista.csv", true);
	BufferedWriter fileBuffer;
	fileBuffer = new BufferedWriter(w);
        
        fileBuffer.write(daScrivere);
		
	fileBuffer.flush();
	fileBuffer.close();
	w.close();
        
        System.out.println("CPubblicita: elemento salvato su file");
    }
    
    private void SovrascriviFile() throws IOException{
        File fileLoaded = new File("lista.csv");
	if(fileLoaded.exists()) {
            fileLoaded.delete();
	}
	fileLoaded.createNewFile();
		
	FileWriter w;
	w = new FileWriter("lista.csv");
	BufferedWriter fileBuffer;
	fileBuffer = new BufferedWriter(w);
		
	for(int i = 0; i < ListaUtenti.size(); i++){
            fileBuffer.write(ListaUtenti.get(i).toCsv());
	}
	
        fileBuffer.flush();
	fileBuffer.close();
        w.close();
    }
    
    private void CaricaSuLista() throws FileNotFoundException, IOException{
        File fileCheck = new File("lista.csv");
	if(fileCheck.exists()){
            FileReader fileLoaded;
            fileLoaded = new FileReader("lista.csv");

            BufferedReader fileBuffer;
            fileBuffer = new BufferedReader(fileLoaded);

            String rigaLetta;

            while(true) {
                rigaLetta = fileBuffer.readLine();
		if(rigaLetta == null) break;
                
                String campi[] = rigaLetta.split(";");
                CUtente daInserire = new CUtente(Long.parseLong(campi[0]), campi[1], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]));
                ListaUtenti.add(daInserire);
            }
            fileBuffer.close();
            fileLoaded.close();
        }
    }
    
    synchronized public void InviaPubblicita(String citta, double raggio, String testo) throws UnsupportedEncodingException, MalformedURLException, IOException, FileNotFoundException, ParserConfigurationException, SAXException{
        System.out.println("CPubblicita: invio la pubblicità agli utenti");
        
        OCoordinate coordinateCitta = osmBot.TrovaCoordinate(citta);
        
        for(int i = 0; i < ListaUtenti.size(); i++){
            //double distanza = osmBot.DistanzaTraDuePunti(coordinateCitta, ListaUtenti.get(i).getLatitudine(), ListaUtenti.get(i).getLongitudine());
            double distanza = osmBot.getDistanceFromLatLonInKm(coordinateCitta.getLatitudine(), coordinateCitta.getLongitudine(), ListaUtenti.get(i).getLatitudine(), ListaUtenti.get(i).getLongitudine());
            
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