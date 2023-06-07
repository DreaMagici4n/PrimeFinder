package PrimeFinder;
/**
 * Essa classe executa todos os processos do codigo.
 */
public class PrimeFinder {

    /**
     * O metodo main cria instancias da classe WorkerPrimo, WorkerPath e WorkerLeitura. 
     * Inicia uma Thread somente para a instacia da classe WorkerLeitura, e o restante das Threads são destinadas as instacias da class WorkerPrimo. 
     * Apos a execucão das Threads é printado e o maior numero encontrado, o seu caminho absoluto e o tempo gasto na execucão.
     * 
     * @param args StringArray
     * @return void
     */
    public static void main(String[] args) {

        WorkerLeitura threadLeitura = new WorkerLeitura();
        WorkerPrimo threads[] = new WorkerPrimo[Runtime.getRuntime().availableProcessors()];

        for (int i = 1; i < threads.length; i++) {
            threads[i] = new WorkerPrimo();
            threads[i].start();
        }

        threadLeitura.start();

        long startTime = System.currentTimeMillis();

        for (WorkerPrimo w : threads) {
            if (w != null) {
                try {
                    w.join();
                } catch (InterruptedException ex) {
                    System.err.println("Alguma thread ainda em execução");
                }
            }
        }
        System.out.println("Maior numero primo encontrado: " + WorkerPrimo.greaterPrime + "\n" + "Path: "
                + WorkerPath.getGreaterPrimePath());

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Tempo de execuçao: " + executionTime + " milissegundos");
    }
}
