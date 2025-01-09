import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escrita {

    public static void ExportarMatriz(double[][] Ak, String nome, String outputFolder) {
        String caminho = outputFolder + "/" + nome;
        File ficheiro = new File(caminho);
        int contador = 1;

        while (ficheiro.exists()) {
            ficheiro = new File(outputFolder + "/" + nome.replace(".csv", "(" + contador + ").csv"));
            contador++;
        }

        try (PrintWriter printwriter = new PrintWriter(ficheiro)) {
            for (int i = 0; i < Ak.length; i++) {
                for (int j = 0; j < Ak[i].length; j++) {
                    printwriter.printf("%.2f ", Ak[i][j]);
                    if (j == Ak[i].length - 1) {
                        printwriter.printf("");
                    } else {
                        printwriter.print("; ");
                    }
                }
                printwriter.println();
            }
        } catch (IOException e) {
            System.out.println("Erro ao guardar ficheiro CSV: " + e.getMessage());
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
                String caminhoImagem = caminhoOutput + File.separator + nomeImagem;

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
            System.out.print(" ");
        }
    }

    public static void guardarCSV(double[][] inputCSV, String caminhoOutput, String nome, int[] indices) {
        int largura = inputCSV[0].length; // Número de colunas
        int dimensaoImagem = 64;         // Dimensão da imagem (assumindo 64x64)

        // Verificar se o array de índices está vazio (ou seja, guardar todas as colunas)
        boolean salvarTudo = (indices == null || indices.length == 0);

        // Iterar pelas colunas que precisam ser salvas
        for (int coluna = 0; coluna < largura; coluna++) {
            // Verificar se a coluna atual está nos índices ou se devemos salvar tudo
            if (salvarTudo || Validacoes.contemIndice(indices, coluna)) {
                String nomeCSV = nome + "_" + (coluna + 1) + ".csv";
                String caminhoCSV = caminhoOutput + File.separator + nomeCSV;

                try (PrintWriter printwriter = new PrintWriter(caminhoCSV)) {
                    for (int i = 0; i < dimensaoImagem; i++) {
                        for (int j = 0; j < dimensaoImagem; j++) {
                            printwriter.printf("%.2f", inputCSV[i * dimensaoImagem + j][coluna]);
                            if (j == dimensaoImagem - 1) {
                                printwriter.println(); // Fim da linha
                            } else {
                                printwriter.print("; "); // Separador entre valores
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao guardar ficheiro CSV: " + e.getMessage());
                }
            }
        }
    }


    public static void ImprimirVetorInt(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i]);
            System.out.print(" ");
        }
    }

    public static void imprimirMatriz(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.2f ", value);
            }
            System.out.println();
        }
    }

}



