import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escrita {
    public static void exportarMatriz(double[][] matriz) {
        // Nome do arquivo CSV
        String nomeArquivo = "matrizReconstruida.csv";

        // Escrever a matriz no arquivo CSV
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    writer.append(String.format("%.2f", matriz[i][j]));// envia o valor com 2 casas decimais (34,23)
                    if (j < matriz[i].length - 1) {
                        writer.append(","); // Adiciona a vírgula entre os valores
                    }
                }
                writer.append("\n"); // Nova linha após cada linha da matriz
            }
            System.out.println("\nMatriz exportada com sucesso para o arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("\nErro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }



    public static void guardarImagem(double[][] inputCSV, String caminhoOutput, String nome) {

        try {
            for (int coluna = 0; coluna < inputCSV[0].length; coluna++) {
                int[][] intimagemReconstruida = new int[inputCSV.length][1];
                for (int i = 0; i < inputCSV.length; i++) {
                    intimagemReconstruida[i][0] = (int) (inputCSV[i][coluna]);
                }

                String nomeImagem = nome + "_" + (coluna + 1) + ".jpg";
                String caminhoImagem = caminhoOutput ;

                double[][] matrizImagemReconstruida = Conversoes.arrayparaMatriz(intimagemReconstruida);

                Conversoes.escreverArraycomoImagem(matrizImagemReconstruida, caminhoImagem);
                System.out.println("Imagem " + (coluna + 1) + " escrita com sucesso em: " + caminhoImagem);
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever a imagem: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Valores de pixeis inválidos no array: " + e.getMessage());
        }
    }

    public static void ImprimirPesos(double[][] pesos, int k) {
        for (int i = 0; i < pesos[0].length; i++) {
            System.out.println("Imagem " + (i + 1) + ":");
            System.out.print("Pesos w: ");
            for (int p = 0; p < k; p++) {
                System.out.printf("%.2f ", pesos[p][i]);
            }
            System.out.println();
        }
    }

    public static void ImprimirDistancias(double[] pesos) {
        for (int i = 0; i < pesos.length; i++) {
            System.out.println("Imagem " + (i + 1) + ":");
            System.out.print("Distancia Euclidiana : ");
            System.out.printf("%.2f ", pesos[i]);
            System.out.println(); // Salta para a próxima linha
        }
    }
    public static void ImprimirVetor(double[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i]);
        }
    }
}


