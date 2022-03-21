package FilePackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Roncoroni
 */
public class FFile {
    //ATTRIBUTI
    // percorso del file
    private String percorso;

    //COSTRUTTORI
    //Costruttore parametrico
    public FFile(String percorso) {
        this.percorso = percorso;
    }

    //GET
    public String getPercorso() {
        return percorso;
    }

    //SET
    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }
    
    //METODI
    //Metodo per scrivere sul file in append o sovrascrivere il file
    // N.B: true => aggiunge in append, false => sovrascrive
    public boolean AppendOSovrascriviFile(String daScrivere, boolean operazione){
        try {
            File fileLoaded = new File(percorso);
            if(!fileLoaded.exists()) {
                fileLoaded.createNewFile();
            }

            FileWriter w;
            w = new FileWriter(percorso, operazione);
            BufferedWriter fileBuffer;
            fileBuffer = new BufferedWriter(w);

            fileBuffer.write(daScrivere);

            fileBuffer.flush();
            fileBuffer.close();
            w.close();

            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    //Metodo per leggere dal file e ritornare il contenuto in una stringa
    public String Leggi() {
        try {
            File fileCheck = new File(percorso);
            if(fileCheck.exists()){
                FileReader fileLoaded;
                fileLoaded = new FileReader(percorso);

                BufferedReader fileBuffer;
                fileBuffer = new BufferedReader(fileLoaded);

                String rigaLetta, ritorno = "";

                while(true) {
                    rigaLetta = fileBuffer.readLine();
                    if(rigaLetta == null) break;

                    ritorno += rigaLetta + "\n";
                }
                fileBuffer.close();
                fileLoaded.close();
                
                return ritorno;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}