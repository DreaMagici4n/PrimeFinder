package PrimeFinder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Essa classe e responsavel por procuarar os arquivos.txt e fazer uma varredura no seu conteudo separando todos os digitos encotrados.
 * Ao encontrar os numeros, adiciona as tarefas da classe WorkerPrimo e muda o estado das threads das instancias WorkerPrimo com o metodo acordaThreads().
 */
public class WorkerLeitura extends Thread {
    public static File pastaInicial;

    /**
     * Metodo construtor da classe WorkerLeitura
     */
    public WorkerLeitura() {
    }

    /**
     * Metodo que define o que será executado em cada Thread da instacia WorkerLeitura
     * @return void
     */
    @Override
    public void run() {
        pastaInicial = selecionaDiretorioRaiz();
        if (pastaInicial == null) {
            JOptionPane.showMessageDialog(null, "Você deve selecionar uma pasta para o processamento",
                    "Selecione o arquivo", JOptionPane.WARNING_MESSAGE);
        } else {
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
                                        String number;
                                        String newLinha = linha.replaceAll("[^\\d.]", ",");
                                        String[] numbers = newLinha.split("[, ;]+");

                                        for (int i = 0; i < numbers.length; i++) {
                                            if (numbers[i].contains(".")) {
                                                String[] numberSplited = numbers[i].split("\\.");
                                                number = numberSplited.length > 0 ? numberSplited[0] : "";
                                            } else {
                                                number = numbers[i];
                                            }
                                            if (!number.isEmpty()) {
                                                WorkerPrimo.addTarefa(Long.parseLong(number));
                                                WorkerPrimo.acordaThreads();
                                            }
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
            WorkerPrimo.termina();
        }
    }

    /**
     * Esse metodo seleciona um diretorio para iniciar a busca por arquivos.txt.
     * @return File
     */
    public static File selecionaDiretorioRaiz() {
        JFileChooser janelaSelecao = new JFileChooser(".");

        janelaSelecao.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File arquivo) {
                return arquivo.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Diretório";
            }
        });

        janelaSelecao.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int acao = janelaSelecao.showOpenDialog(null);

        if (acao == JFileChooser.APPROVE_OPTION) {
            return janelaSelecao.getSelectedFile();
        } else {
            return null;
        }
    }
}
