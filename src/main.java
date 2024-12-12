import org.apache.commons.math3.linear.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String filePath = "exampleInputFunc1.csv";
        //ler a matriz do ficheiro
        double[][] matriz = lerMatriz(filePath);
        System.out.println("digite o número de valores próprios a utilizar na reconstrução da matriz");
        Scanner sc = new Scanner(System.in);
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
    }


    public static double[][] lerMatriz(String filePath) {
        double[][] matriz = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int rows = 0;
            int cols = 0;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    rows++;
                    if (cols == 0) {
                        cols = line.split(",").length;
                    }
                }
            }

            matriz = new double[rows][cols];

            try (BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
                int row = 0;
                while ((line = br2.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] values = line.split(",");
                        for (int col = 0; col < cols; col++) {
                            matriz[row][col] = Double.parseDouble(values[col].trim());
                        }
                        row++;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }

        return matriz;
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
}
