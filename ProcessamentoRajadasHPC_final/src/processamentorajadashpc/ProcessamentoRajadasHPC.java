
package processamentorajadashpc;

public class ProcessamentoRajadasHPC {

    //  ESTA LENDO TUOD PRIMEIRO E DEPOIS VENDO SE E PRIMO
    //  MUITO MAIS LENTO E NAO ACHA O MAIOR PRIMA, PARAM ANTES.
    //  POSIVELMENTE ENTA DANDO UM DEADLOCK


    public static void main(String[] args) {

        // WorkerLeitura threadLeitura = new WorkerLeitura();
        WorkerPrimo threads[] = 
            new WorkerPrimo[Runtime.getRuntime().
                    availableProcessors()];
        
        //insta. e executa as threads

        // threadLeitura.start();

        for(int i = 1; i < threads.length;i++){
            threads[i] = new WorkerPrimo();
            threads[i].start();
        }

        WorkerPrimo.addTarefa(1920912);
        WorkerPrimo.addTarefa(3);
        WorkerPrimo.addTarefa(29);

        WorkerPrimo.acordaThreads();


        for(WorkerPrimo w : threads){
            if (w !=null){
                try {
                    w.join();
                } catch (InterruptedException ex) {
                    System.err.println("Alguma thread ainda em execução");
                }
            }
           
        }

        System.out.println(WorkerPrimo.greaterPrime);
    }
    
}
