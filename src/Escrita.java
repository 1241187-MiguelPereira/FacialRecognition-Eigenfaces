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

    public static void exportarEigenfaces(double[][] matriz, int k, String nomeArquivo) {
        // Criar a matriz RealMatrix a partir da matriz original
        RealMatrix realMatrix = new Array2DRowRealMatrix(matriz);

        // Obter a matriz coluna dos vetores próprios (Eigenfaces)
        double[][] eigenfaces = Calculos.MatrizColunaDosVetoresProprios(realMatrix, k);

        // Normalizar as Eigenfaces
        double[][] eigenfacesNormalizadas = Calculos.normalizeEigenfaces(eigenfaces);

        // Exportar para um arquivo CSV
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (int i = 0; i < eigenfacesNormalizadas.length; i++) {
                for (int j = 0; j < eigenfacesNormalizadas[i].length; j++) {
                    writer.append(String.valueOf(eigenfacesNormalizadas[i][j]));
                    if (j < eigenfacesNormalizadas[i].length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            System.out.println("Eigenfaces exportadas com sucesso para o arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
        }
    }

    public static void guardarImagem(double[][] imagensReconstruidas) {

        try {
            for (int coluna = 0; coluna < imagensReconstruidas[0].length; coluna++) {
                int[][] intimagemReconstruida = new int[imagensReconstruidas.length][1];
                for (int i = 0; i < imagensReconstruidas.length; i++) {
                    intimagemReconstruida[i][0] = (int) (imagensReconstruidas[i][coluna]);
                }

                String nomeImagem = "imagem_" + (coluna + 1) + ".jpg";
                String caminhoImagem = "src\\outputs\\ImagensReconstruidas" + File.separator + nomeImagem;

                double[][] matrizImagemReconstruida = Conversoes.arrayparaMatriz(intimagemReconstruida);

                Conversoes.escreverArraycomoImagem(matrizImagemReconstruida);
                System.out.println("Imagem " + (coluna + 1) + " escrita com sucesso em: " + caminhoImagem);
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever a imagem: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Valores de pixeis inválidos no array: " + e.getMessage());
        }
    }
}

