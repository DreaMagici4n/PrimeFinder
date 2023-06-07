package processamentorajadashpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

public class WorkerPath {
    private static File pastaInicial = WorkerLeitura.pastaInicial;
    private static String greaterPrime = String.valueOf(WorkerPrimo.greaterPrime);

    public WorkerPath() {

    }

    public static String getGreaterPrimePath() {
        ArrayDeque<File> explorar = new ArrayDeque<>();

        explorar.push(pastaInicial);

        while (!explorar.isEmpty()) {

            File diretorioAtual = explorar.pop();

            File arquivosDir[] = diretorioAtual.listFiles();

            for (File arq : arquivosDir) {
                if (arq.isDirectory()) {
                    explorar.push(arq);
                } else {
                    if (arq.getAbsolutePath().endsWith(".txt")) {
                        try {
                            FileReader marcaLeitura = new FileReader(arq);

                            BufferedReader bufLeitura = new BufferedReader(marcaLeitura);

                            String linha = null;

                            do {
                                linha = bufLeitura.readLine();
                                if (linha != null) {
                                    if(linha.contains(greaterPrime)) {
                                        return arq.getAbsolutePath();
                                    }
                                }
                            } while (linha != null);

                        } catch (FileNotFoundException ex) {
                            System.err.println("Arquivo não existe no dir.");
                        } catch (IOException ex) {
                            System.err.println("Seu arquivo esta corrompido");
                        }
                    }

                }
            }

        }
        return "";
    }
}
