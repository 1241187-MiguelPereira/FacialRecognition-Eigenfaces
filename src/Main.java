import org.apache.commons.math3.linear.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import org.apache.commons.math3.linear.*;
import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) {
        System.out.print("|Bem vindo ao programa de reconstrução de imagens entre outros|");
        System.out.println("Insira a funcionalidade que quer utilizar");
        Scanner sc = new Scanner(System.in);
        int funcionalidade;
        funcionalidade = sc.nextInt();
        String filePath = "exampleInputFunc1.csv";
        String pastaImagens = "baseImagens";
        //ler a matriz do ficheiro
        double[][] matriz = lerMatriz(filePath);
        System.out.println("digite o número de valores próprios a utilizar na reconstrução da matriz");
        //ler o valor de eigenfaces
        int k = sc.nextInt();
        //Estabelecer o indice da matriz
        int colunas = matriz[0].length;
        int linhas = matriz.length;
        //Verificar se é simétrica, so prosseguindo se for simétrica
        while (!isSymmetric(matriz, colunas, linhas))
            System.out.println("A matriz inserida é inválida");
        //atualizar o k
        k = verificarValoresProprios(colunas, k);
        // criar realMatrix
        RealMatrix matrizOriginal = new Array2DRowRealMatrix(matriz);
        //estabelecer a matriz dos vetores
        double[][] matrizP = MatrizColunaDosVetoresProprios(matrizOriginal, k);
        //estabelecer a matriz dos valores
        double[][] matrizD = getEigenvalueDiagonal(matrizOriginal, k);
        //estabelecer a matriz coluna dos vetores transposta
        double[][] matrizTransposta = criarTransposta(matrizP);
        //estabelecer a matriz reconstruida
        double[][] reconstruida = reconstruirMatriz(matrizP, matrizD, matrizTransposta);
        System.out.println("Matriz reconstruída:");
        imprimirMatriz(reconstruida);
        System.out.println("Número de valores utilizados: " + k);
        System.out.println("Matriz dos vetores:");
        imprimirMatriz(matrizP);
        System.out.println("Matriz dos valores:");
        imprimirMatriz(matrizD);
        double ErroMedio = calcularErroAbsolutoMedio(matriz, reconstruida, colunas, linhas);
        System.out.print("Erro Absoluto Médio: " + ErroMedio);
        exportarMatriz(reconstruida);

    }

    public static double[][] lerMatriz(String filePath) {
        try {
            // First pass to determine matrix dimensions
            Scanner scanner = new Scanner(new File(filePath));
            int rows = 0, cols = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    rows++;
                    if (cols == 0) cols = line.split(",").length; // Get column count from first valid line
                }
            }
            scanner.close();

            // Initialize the matrix
            double[][] matriz = new double[rows][cols];

            // Second pass to populate the matrix
            scanner = new Scanner(new File(filePath));
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] values = line.split(",");
                    for (int col = 0; col < cols; col++) {
                        matriz[row][col] = Double.parseDouble(values[col].trim());
                    }
                    row++;
                }
            }
            scanner.close();

            return matriz;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return null;
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

    public static boolean isSymmetric(double[][] matrix, int rows, int cols) {
        if (rows != cols) {
            return false;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int verificarValoresProprios(int cols, int k) {
        if (k > cols || k <= 0) {
            k = cols;
        }
        return k;
    }

    public static double[][] multiplicarMatrizes(double[][] A, double[][] B) {
        int linhasA = A.length;
        int colunasA = A[0].length;
        int colunasB = B[0].length;

        double[][] resultado = new double[linhasA][colunasB];

        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return resultado;
    }

    public static double[][] reconstruirMatriz(double[][] matrizP, double[][] matrizD, double[][] matrizTransposta) {
        double[][] matrizPD = multiplicarMatrizes(matrizP, matrizD);
        double[][] matrizReconstruida = multiplicarMatrizes(matrizPD, matrizTransposta);
        return matrizReconstruida;

    }

    public static double[][] MatrizColunaDosVetoresProprios(RealMatrix matrizOriginal, int k) {
        EigenDecomposition decomposition = new EigenDecomposition(matrizOriginal);
        double[] eigenvalues = decomposition.getRealEigenvalues(); // Valores próprios
        double[][] eigenvectors = decomposition.getV().getData();  // Vetores próprios (colunas da matriz P)

        // Criar um array de índices para associar os valores próprios aos vetores próprios
        int[] indices = new int[eigenvalues.length];
        for (int i = 0; i < eigenvalues.length; i++) {
            indices[i] = i;
        }

        // Ordenar os índices com base nos valores próprios (ordem decrescente)
        for (int i = 0; i < eigenvalues.length - 1; i++) {
            for (int j = i + 1; j < eigenvalues.length; j++) {
                if (eigenvalues[indices[i]] < eigenvalues[indices[j]]) {
                    int temp = indices[i];
                    indices[i] = indices[j];
                    indices[j] = temp;
                }
            }
        }

        // Construir a matriz coluna com os \(k\) maiores vetores próprios
        double[][] matrizColuna = new double[eigenvectors.length][k];
        for (int col = 0; col < k; col++) {
            int index = indices[col]; // Índice do vetor próprio correspondente
            for (int row = 0; row < eigenvectors.length; row++) {
                matrizColuna[row][col] = eigenvectors[row][index];
            }
        }

        return matrizColuna;
    }


    public static double[][] getEigenvalueDiagonal(RealMatrix matrizOriginal, int k) {
        // Calcular os valores próprios usando EigenDecomposition
        EigenDecomposition eigenDecomposition = new EigenDecomposition(matrizOriginal);
        double[] eigenvalues = eigenDecomposition.getRealEigenvalues();
        // Ordenar os valores próprios em ordem decrescente
        Double[] sortedEigenvalues = Arrays.stream(eigenvalues)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Double[]::new);

        // Selecionar os k maiores valores próprios
        double[][] diagonalMatrix = new double[k][k];
        for (int i = 0; i < k; i++) {
            diagonalMatrix[i][i] = sortedEigenvalues[i];
        }
        return diagonalMatrix;
    }

    public static double[][] criarTransposta(double[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        double[][] transposta = new double[colunas][linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                transposta[j][i] = matriz[i][j];
            }
        }

        return transposta;
    }

    public static double calcularErroAbsolutoMedio(double[][] matriz, double[][] matrizReconstruida, int colunas, int linhas) {
        double errorSum = 0.0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                errorSum += Math.abs(matriz[i][j] - matrizReconstruida[i][j]);
            }
        }

        return errorSum / (linhas * colunas);
    }


    public static void exportarMatriz(double[][] matriz) {
        // Nome do arquivo CSV
        String nomeArquivo = "matrizReconstruida.csv";

        // Escrever a matriz no arquivo CSV
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    writer.append(String.valueOf(matriz[i][j])); // envia o valor absoluto como double (34.231241213)
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

    public static double[] VetorColunaMedias(double[][] matrizA) {
        int linhas = matrizA.length; // Número de linhas
        int colunas = matrizA[0].length; // Número de colunas

        double[] vetorColuna = new double[colunas]; // Vetor para armazenar as médias de cada coluna

        // Loop para calcular a soma de cada coluna
        for (int j = 0; j < colunas; j++) {
            double soma = 0; // Resetar a soma para cada coluna
            for (int i = 0; i < linhas; i++) {
                soma += matrizA[i][j]; // Somar os valores da coluna atual
            }
            vetorColuna[j] = soma / linhas; // Calcular a média da coluna
        }
        return vetorColuna;
    }
    public static double[][] CálculoDoDesvio(double [] vetorColuna,double[][] matrizA){
        int linhas=matrizA.length;
        int colunas=matrizA[0].length;
        double[][] desvio = new double[linhas][colunas];
        for (int j = 0; j < colunas; j++) {
            for (int i = 0; i < linhas; i++) {
                desvio[i][j]=matrizA[i][j]-vetorColuna[j];
            }
        }return desvio;
    }

    public static double[][] normalizeEigenfaces(double[][] eigenvectors) {
        int rows = eigenvectors.length;      // Número de linhas (dimensão dos vetores)
        int cols = eigenvectors[0].length;   // Número de vetores próprios (colunas)

        double[][] normalizedEigenvectors = new double[rows][cols];

        // Iterar sobre cada vetor próprio (coluna)
        for (int j = 0; j < cols; j++) {
            double norm = 0.0;

            // 1. Calcular a norma L2 do vetor próprio (coluna j)
            for (int i = 0; i < rows; i++) {
                norm += eigenvectors[i][j] * eigenvectors[i][j]; // Soma dos quadrados dos elementos
            }
            norm = Math.sqrt(norm); // Raiz quadrada para obter a norma

            // 2. Normalizar o vetor próprio (dividir cada elemento pela norma)
            for (int i = 0; i < rows; i++) {
                normalizedEigenvectors[i][j] = eigenvectors[i][j] / norm;
            }
        }
        return normalizedEigenvectors;
    }
    public static double[][] MatrizdasCovariancias(double[][] matrizDosDesvios, double imagens) {
        int linhas = matrizDosDesvios.length;
        int colunas = matrizDosDesvios[0].length;

        // Criar a matriz transposta
        double[][] MatrizTransformada = criarTransposta(matrizDosDesvios);

        // Multiplicar a matriz transposta pela matriz original dos desvios
        double[][] matrizCovariancias = multiplicarMatrizes(MatrizTransformada, matrizDosDesvios);

        // Dividir cada elemento da matriz resultante pelo número de imagens
        for (int i = 0; i < matrizCovariancias.length; i++) {
            for (int j = 0; j < matrizCovariancias[i].length; j++) {
                matrizCovariancias[i][j] /= imagens;
            }
        }

        return matrizCovariancias;
    }
    //Metodo para criar um csv com eigenfaces normalizadas
    public static void exportarEigenfaces(double[][] matriz, int k, String nomeArquivo){
        // Criar a matriz RealMatrix a partir da matriz original
        RealMatrix realMatrix = new Array2DRowRealMatrix(matriz);

        // Obter a matriz coluna dos vetores próprios (Eigenfaces)
        double[][] eigenfaces = Main.MatrizColunaDosVetoresProprios(realMatrix, k);

        // Normalizar as Eigenfaces
        double[][] eigenfacesNormalizadas = Main.normalizeEigenfaces(eigenfaces);

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
    //Metodo para Calcular vetor com a média das imagens presentes na matriz
    public static double[] calcularVetorMedia(double[][] matriz) {
        int linhas = matriz.length;    // Número de linhas (imagens)
        int colunas = matriz[0].length; // Número de colunas (pixels ou características)

        double[] vetorMedia = new double[colunas]; // Vetor para armazenar a média de cada coluna

        // Iterar sobre cada coluna para calcular a média
        for (int j = 0; j < colunas; j++) {
            double soma = 0.0;
            for (int i = 0; i < linhas; i++) {
                soma += matriz[i][j]; // Somar os valores da coluna
            }
            vetorMedia[j] = soma / linhas; // Calcular a média da coluna
        }

        return vetorMedia;
    }

    //Conversão matriz de imagem para vetor

    public static double[] converterMatrizParaVetor(double[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        double[] vetor = new double[linhas * colunas];
        int index = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                vetor[index++] = matriz[i][j];
            }
        }

        return vetor;
    }

    //Construção Matriz M

    public static double[][] construirMatrizDeImagens(String pasta) {
        File folder = new File(pasta);
        File[] arquivos = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma imagem encontrada na pasta: " + pasta);
            return null;
        }

        List<double[]> listaDeVetores = new ArrayList<>();

        // Ler cada arquivo e transformar em vetor
        for (File arquivo : arquivos) {
            System.out.println("Lendo imagem: " + arquivo.getName());
            double[][] matriz = lerMatriz(arquivo.getAbsolutePath());
            double[] vetor = converterMatrizParaVetor(matriz);
            listaDeVetores.add(vetor);
        }

        // Construir matriz M (cada coluna é uma imagem)
        int linhas = listaDeVetores.get(0).length;
        int colunas = listaDeVetores.size();
        double[][] matrizM = new double[linhas][colunas];

        for (int j = 0; j < colunas; j++) {
            double[] vetor = listaDeVetores.get(j);
            for (int i = 0; i < linhas; i++) {
                matrizM[i][j] = vetor[i];
            }
        }

        return matrizM;
    }


}
