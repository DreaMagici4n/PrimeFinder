package processamentorajadashpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.spi.NumberFormatProvider;
import java.util.ArrayDeque;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class WorkerLeitura extends Thread{
    
    public WorkerLeitura () {
        
    }

    @Override
    public void run() {
        File pastaInicial = selecionaDiretorioRaiz();
        // System.out.println(pastaInicial);
        if(pastaInicial == null){
            JOptionPane.showMessageDialog(null,"Você deve selecionar uma pasta para o processamento",
                    "Selecione o arquivo", JOptionPane.WARNING_MESSAGE);
        }else{
            //...Modifique a partir daqui
            //AQUI você deve explorar a pasta, arquivos e subpastas...
            
            ArrayDeque<File> explorar = new ArrayDeque<>();
            explorar.push(pastaInicial);
            
            //processo de busca pelo arquivo
            while(!explorar.isEmpty()){
                
                //desemp. o diretorio do topo
                File diretorioAtual = explorar.pop();
                
                File arquivosDir[] = diretorioAtual.listFiles();
                
                //passando por todos os arq. e subDir.
                for(File arq:arquivosDir){
                    
                    //verificamos se é uma "pasta", caso afirmativo empilha
                    if(arq.isDirectory()){
                    explorar.push(arq);
                    }else{
                        //encontrei um arquivo
                        // System.out.println(arq.getAbsolutePath());
                        
                        if(arq.getAbsolutePath().endsWith(".txt")){
                        //acessar o conteúdo do arquivo
                        try{
                            FileReader marcaLeitura = new FileReader(arq);
                            
                            BufferedReader bufLeitura = new BufferedReader(marcaLeitura);
                            
                            //leitura das linhas do arquivo
                            String linha = null;
                            
                            do{
                                linha = bufLeitura.readLine();
                                if(linha !=null){

                                    String number;
                                    String newLinha = linha.replaceAll("[^\\d.]", ",");
                                    String[] numbers = newLinha.split("[, ;]+");

                                    for (int i = 0; i < numbers.length; i++) {

                                        if(numbers[i].contains(".")){
                                            String[] numberSplited = numbers[i].split(".");
                                            number =  numberSplited.length >  0 ? numberSplited[0] : "";
                                        } else {
                                            number = numbers[i];
                                        }

                                        if(!number.isEmpty()){
                                            WorkerPrimo.addTarefa(Integer.parseInt(number));
                                            WorkerPrimo.acordaThreads();
                                        }
                                    }
                                }
                                
                            }while(linha != null);
                            
                        }catch(FileNotFoundException ex){
                            System.err.println("Arquivo não existe no dir.");
                        }catch(IOException ex){
                            System.err.println("Seu arquivo esta corrompido");
                        }
                        }
                        
                    }
                }
                
            }
        }
    }

    public static File selecionaDiretorioRaiz() {
        JFileChooser janelaSelecao = new JFileChooser(".");
        //janelaSelecao.setControlButtonsAreShown(false);
        
        //conf. do filtro de selecao
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
        
        //avaliando a acao do usuario na selecao da pasta de inicio da busca
        int acao = janelaSelecao.showOpenDialog(null);
        
        if(acao == JFileChooser.APPROVE_OPTION){
            return janelaSelecao.getSelectedFile();
        }else{
            return null;
        }
    }
}
