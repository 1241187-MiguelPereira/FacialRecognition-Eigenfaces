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


    public static void escreverArraycomoImagem(double[][] array, String caminhoOutput) throws IOException {
        int alturaImagem = array.length;
        int larguraImagem = array[0].length;
        BufferedImage imagem = new BufferedImage(larguraImagem, alturaImagem, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < alturaImagem; y++) {
            for (int x = 0; x < larguraImagem; x++) {
                int intensidade = Math.abs((int) array[y][x]);
                if (intensidade < 0 || intensidade > 255) {
                    throw new IllegalArgumentException("A intensidade do pixel deve estar entre 0 e 255.");
                }
                int rgb = (intensidade << 16) | (intensidade << 8) | intensidade;
                imagem.setRGB(x, y, rgb);
            }
        }
        File outputFile = new File(caminhoOutput);
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
}





