
package processamentorajadashpc;

public class ProcessamentoRajadasHPC {
    public static void main(String[] args) {

        WorkerLeitura threadLeitura = new WorkerLeitura();
        WorkerPrimo threads[] = new WorkerPrimo[Runtime.getRuntime().availableProcessors()];

        for (int i = 1; i < threads.length; i++) {
            threads[i] = new WorkerPrimo();
            threads[i].start();
        }

        threadLeitura.start();

        for (WorkerPrimo w : threads) {
            if (w != null) {
                try {
                    w.join();
                } catch (InterruptedException ex) {
                    System.err.println("Alguma thread ainda em execução");
                }
            }
        }

        System.out.println("Maior numero primo encontrado: " + WorkerPrimo.greaterPrime);
    }

}
