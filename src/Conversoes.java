import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

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

      public static void escreverArraycomoImagem(double[][] array) throws IOException {
            int alturaImagem = array.length;
            int larguraImagem = array[0].length;
            BufferedImage imagem = new BufferedImage(larguraImagem, alturaImagem, BufferedImage.TYPE_BYTE_GRAY);

            for (int y = 0; y < alturaImagem; y++) {
                  for (int x = 0; x < larguraImagem; x++) {
                        int intensidade = (int) array[y][x];
                        if (intensidade < -10 || intensidade > 255) {
                              System.out.println(intensidade);
                              throw new IllegalArgumentException("A intensidade do pixel deve estar entre 0 e 255.");
                        }
                        int rgb = (intensidade << 16) | (intensidade << 8) | intensidade;
                        imagem.setRGB(x, y, rgb);
                  }
            }
            File outputFile = new File("outputs\\ImagensReconstruidas");
            ImageIO.write(imagem, "jpg", outputFile);

      }

      public static double[][] arrayparaMatriz(int[][] array) {
            int colunas = 64;
            int linhas = 64;
            if (array.length != linhas * colunas) {
                  throw new IllegalArgumentException("O vetor não equivale às dimensões da matriz");
            }
            double[][] arrayConvertido = new double[linhas][colunas];
            for (int i = 0; i < linhas; i++) {
                  for (int j = 0; j < colunas; j++) {
                        arrayConvertido[i][j] = array[i * colunas + j][0];
                  }
            }
            return arrayConvertido;
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
