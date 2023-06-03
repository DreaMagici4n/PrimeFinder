
package processamentorajadashpc;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessamentoRajadasHPC {


    public static void main(String[] args) {
        WorkerPrimo worker = new WorkerPrimo();
        worker.primeArray(100);
        worker.primeArray(1000);

        // Random gerador = new Random();
        
        // WorkerPrimo threads[] = 
        //     new WorkerPrimo[Runtime.getRuntime().
        //             availableProcessors()];
        
        // //insta. e executa as threads
        // for(int i = 0; i < threads.length;i++){
        //     threads[i] = new WorkerPrimo();
        //     threads[i].start();
        // }
        
        // for(int i = 0; i < 3;i++){
        //     WorkerPrimo.addTarefa(
        //             gerador.nextInt(1000000000, Integer.MAX_VALUE));
        // }
        
        // WorkerPrimo.acordaThreads();
        
        // try {
        //     Thread.sleep(60000);
        // } catch (InterruptedException ex) {
        //     System.err.println("Thread principal dormindo...");
        // }
        
        // for(int i = 0; i < 3;i++){
        //     WorkerPrimo.addTarefa(
        //             gerador.nextInt(1000000000, Integer.MAX_VALUE));
        // }
        
        // WorkerPrimo.acordaThreads();
        
        // WorkerPrimo.termina();
        
        // for(WorkerPrimo w : threads){
        //     try {
        //         w.join();
        //     } catch (InterruptedException ex) {
        //         System.err.println("Alguma thread ainda em execução");
        //     }
        // }
        
        
    }
    
}
