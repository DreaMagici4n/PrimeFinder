package processamentorajadashpc;

import java.util.ArrayList;

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
     * Esse metodo verifica se o numero
     * @param num
     */
    public static void setGreaterPrime(long num) {
        if (isPrime(num)) {
            greaterPrime = greaterPrime < num ? num : greaterPrime;
        }
    }

    public void aguarde() {
        synchronized (chaveRecurso) {
            try {
                chaveRecurso.wait();
            } catch (InterruptedException ex) {
                System.err.println("existe threads aguardando recurso");
            }
        }
    }

    public static void addTarefa(long novoValor) {
        synchronized (chaveTarefas) {
            tarefas.add(novoValor);
        }
    }

    public static void acordaThreads() {
        synchronized (chaveRecurso) {
            chaveRecurso.notifyAll();
        }
    }

    public static void termina() {
        existeTrabalho = false;
        acordaThreads();
    }
}
