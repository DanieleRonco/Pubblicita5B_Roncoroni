package botpubblicita;

import TelegramPackage.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Daniele Roncoroni
 */
public class ThreadGetUpdates extends Thread {
    //ATTRIBUTI
    CPubblicita pubblicita;
    
    //COSTRUTTORE
    //Costruttore di default
    public ThreadGetUpdates(){
        this.pubblicita = null;
    }
    //Costruttore parametrico - TelegramAPI
    public ThreadGetUpdates(CPubblicita pubblicita){
        this.pubblicita = pubblicita;
    }

    //METODI
    @Override
    public void run() {
        System.out.println("ThreadGetUpdates: THREAD AVVIATO!");
        try{
            List<TUpdate> listaUpdates;
            while(true){
                listaUpdates = pubblicita.gettBot().getUpdates();
                
                
                if(listaUpdates != null){
                    //c'è qualcosa all'interno della lista
                    System.out.println("ThreadGetUpdates: c'è qualcosa all'interno della lista");
                    pubblicita.VerificaESalva(listaUpdates);
                } else {
                    //non c'è nulla all'interno della lista
                    System.out.println("ThreadGetUpdates: non c'è nulla all'interno della lista");
                }
                
                Thread.sleep(10000); //TODO: da cambiare con 60000
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}