package botpubblicita_ripasso;

import TelegramPackage.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 *
 * @author Daniele Roncoroni
 */
public class ThreadGetUpdates extends Thread {
    private CBotPubblicita pubblicita;
    
    public ThreadGetUpdates(CBotPubblicita pubblicita){
        this.pubblicita = pubblicita;
    }

    @Override
    public void run() {
        List<TUpdate> ListaUpdate;
        while(true){
            try {
                ListaUpdate = pubblicita.gettAPI().getUpdates();
                if(ListaUpdate != null){
                    System.out.println("ThreadGetUpdates: c'è qualcosa all'interno della lista");
                    pubblicita.GestisciUpdates(ListaUpdate);
                } else System.out.println("ThreadGetUpdates: non c'è nulla all'interno della lista");
                
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}