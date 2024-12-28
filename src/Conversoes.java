import java.io.IOException;

public class Conversoes {
      public static double[] converterMatrizParaVetor(double[][] matriz) {
            if (matriz == null || matriz.length == 0 || matriz[0].length == 0) {
                  System.out.println("Erro: Matriz inválida ou vazia.");
                  return null;
            }

            int linhas = matriz.length;
            int colunas = matriz[0].length;

            // Vetor de tamanho total das entradas da matriz (linhas * colunas)
            double[] vetor = new double[linhas * colunas];
            int index = 0;

            for (int i = 0; i < linhas; i++) {
                  for (int j = 0; j < colunas; j++) {
                        vetor[index++] = matriz[i][j];
                  }
            }

            return vetor;
      }

      public static int[][] matrizDoubleParaInt(double[][] matriz) {
            int[][] intMatriz = new int[matriz.length][matriz[0].length];

            // Convert each element from double to int (truncating decimals)
            for (int i = 0; i < matriz.length; i++) {
                  for (int j = 0; j < matriz[i].length; j++) {
                        intMatriz[i][j] = (int) matriz[i][j]; // Cast from double to int
                  }
            }
            return intMatriz;
      }

      public static void matrizParaJPG(double[][] matriz) {
            // Define a sample 2D array of pixel intensities (0-255)

            int[][] intMatriz = matrizDoubleParaInt(matriz);

            // Create an instance of GreyscaleImageWriter
            GreyscaleImageWriter imageWriter = new GreyscaleImageWriter();

            // Define the output file path
            String outputPath = "ImagensReconstruidas/output_image.jpg";

            // Write the 2D array as a greyscale image
            try {
                  imageWriter.writeArrayAsImage(intMatriz, outputPath);
                  System.out.println("Image successfully written to " + outputPath);
            } catch (IOException e) {
                  System.err.println("Failed to write the image: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                  System.err.println("Invalid pixel values in the array: " + e.getMessage());
            }
      }

      public static double[][] vetorParaMatriz(double[] vetor) {
            // Calcula a largura e a altura assumindo que a matriz é quadrada
            int tamanho = vetor.length;
            int largura = (int) Math.sqrt(tamanho);

            // Verifica se é possível formar uma matriz quadrada
            if (largura * largura != tamanho) {
                  throw new IllegalArgumentException("O vetor não representa uma matriz quadrada.");
            }

            int altura = largura; // Para uma matriz quadrada, altura = largura

            // Converte o vetor para uma matriz 2D
            double[][] matriz = new double[altura][largura];
            for (int i = 0; i < altura; i++) {
                  for (int j = 0; j < largura; j++) {
                        matriz[i][j] = vetor[i * largura + j];
                  }
            }
            return matriz;
      }





      public static double[] reescalonarParaImagem(double[] vetorReconstruido) {
            // Encontre os valores mínimo e máximo no vetor
            double min = Double.MAX_VALUE;
            double max = -Double.MAX_VALUE;

            for (double valor : vetorReconstruido) {
                  if (valor < min) min = valor;
                  if (valor > max) max = valor;
            }

            // Reescalonar os valores para o intervalo [0, 255]
            double[] vetorReescalonado = new double[vetorReconstruido.length];
            for (int i = 0; i < vetorReconstruido.length; i++) {
                  // Fórmula para reescalonamento
                  vetorReescalonado[i] = (vetorReconstruido[i] - min) / (max - min) * 255;
                  vetorReescalonado[i] = Math.max(0, Math.min(255, vetorReescalonado[i]));  // Garantir que está entre 0 e 255
            }

            return vetorReescalonado;
      }
}
