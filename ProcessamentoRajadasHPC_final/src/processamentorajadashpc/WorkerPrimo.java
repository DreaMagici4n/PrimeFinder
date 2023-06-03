package processamentorajadashpc;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerPrimo extends Thread {

    private static ArrayList<Integer> tarefas = new ArrayList<>();
    private static ArrayList<Integer> primeNumbers = new ArrayList<>();
    private static Object chaveTarefas = new Object();
    private static Object chaveRecurso = new Object();
    private static boolean existeTrabalho = true;
    private static int large_sqr = 0;

    private int quantPrimos;

    public WorkerPrimo() {
        this.quantPrimos = 0;
    }

    @Override
    public void run() {
        Integer valor = null;

        while (existeTrabalho || !tarefas.isEmpty()) {
            valor = null;
            synchronized (chaveTarefas) {
                if (!tarefas.isEmpty()) {
                    //"peguei" o valor da primeira posicao
                    valor = tarefas.remove(0);

                }
            }


            if (valor != null && isPrimo(valor)) {
                this.quantPrimos++;
            }
            
            if(valor == null && existeTrabalho) {
                /*deve ocorrer a modificação do status da thread
                    para "aguandado" novas tarefas
                 */
                aguarde();
            }
        }
    }

    /**
     * Essa funcao verifica se o tamanho da raiz do @param num e maior do que a maior raiz ja registrada na variavel large_sqr. Caso seja maior, adiciona os primos faltando ate a nova raiz, que sera o novo limite.
     * @param num numero
     * @return ArrayList (Integer)
     */
    public ArrayList<Integer> primeArray (int num) {
        int ceil = (int) Math.sqrt(num);

        if (ceil > large_sqr) {
            int top = 3;

            if (!primeNumbers.isEmpty()) {
                top = primeNumbers.get(primeNumbers.size() -1);
            } else {
                //carga inicial do array
                primeNumbers.add(2);
            }
            
            for(int i = top ; i < ceil; i+=2){
                if(isPrime(i)) {
                    primeNumbers.add(i);
                }

            }

            large_sqr = ceil;
        }

        return primeNumbers;

       
    }

    private static boolean isPrime(int num){
        if ( num > 2 && num%2 == 0 ) {
            return false;
        }
        int top = (int)Math.sqrt(num) + 1;
        for(int i = 3; i < top; i+=2){
            if(num % i == 0){
                return false;
            }
        }
        return true; 
    }

    public void aguarde() {
        synchronized (chaveRecurso) {
            try {
                //não temos mais trabalho vamos aguardar novos dados...
                chaveRecurso.wait();
            } catch (InterruptedException ex) {
                System.err.println("existe threads aguardando recurso");
            }
        }
    }

    public static void addTarefa(int novoValor) {
        synchronized (chaveTarefas) {
            tarefas.add(novoValor);
        }
    }
    
    public static void acordaThreads(){
        synchronized (chaveRecurso) {
            chaveRecurso.notifyAll();
        }
    }
    
    public static void termina(){
        existeTrabalho = false;
        acordaThreads();
    }

}
