package processamentorajadashpc;

import java.util.ArrayList;

/**
* Essa classe é responsável por verificar se os números são primos. Essa classe extende a classe Thread.
*/
public class WorkerPrimo extends Thread {

    private static ArrayList<Long> tarefas = new ArrayList<>();
    private static Object chaveTarefas = new Object();
    private static Object chaveRecurso = new Object();
    private static boolean existeTrabalho = true;
    public static long greaterPrime;

    public WorkerPrimo() {

    }

    @Override
    public void run() {
        long num = 0;

        while (existeTrabalho || !tarefas.isEmpty()) {
            num = 0;

            synchronized (chaveTarefas) {
                if (!tarefas.isEmpty()) {
                    num = tarefas.remove(0);
                }
            }

            if (num != 0) {
                setGreaterPrime(num);
            }

            if (num == 0 && existeTrabalho) {
                aguarde();
            }
        }
    }

    /**
     * Esse metodo verica se o numero e primo.
     * @param num
     * @return bool
     */
    private static boolean isPrime(long num) {
        if (num > 2 && num % 2 == 0) {
            return false;
        }
        int top = (int) Math.sqrt(num) + 1;
        for (int i = 3; i < top; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Esse metodo verifica se numero primo e o maior numero primo encontrado
     * @param num
     */
    public static void setGreaterPrime(long num) {
        if (isPrime(num)) {
            greaterPrime = greaterPrime < num ? num : greaterPrime;
        }
    }

    /**
     * Esse metodo faz com a threads entrem em estado de espera.
     */
    public void aguarde() {
        synchronized (chaveRecurso) {
            try {
                chaveRecurso.wait();
            } catch (InterruptedException ex) {
                System.err.println("existe threads aguardando recurso");
            }
        }
    }

    /**
     * Adiciona @param novoValor no array de tarefas.
     * @param novoValor
     */
    public static void addTarefa(long novoValor) {
        synchronized (chaveTarefas) {
            tarefas.add(novoValor);
        }
    }

    /**
     * Esse metodo tira todos as threads do estado de espera.
     */
    public static void acordaThreads() {
        synchronized (chaveRecurso) {
            chaveRecurso.notifyAll();
        }
    }

    /**
     * Esse metodo muda a variavel existeTrabalho para "false", e chama o metodo acordaThreads.
     */
    public static void termina() {
        existeTrabalho = false;
        acordaThreads();
    }
}
