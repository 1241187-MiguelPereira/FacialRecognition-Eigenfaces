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
        int k=sc.nextInt();
        //Estabelecer o indice
        int colunas=matriz[0].length;
        int linhas=matriz.length;
        //Verificar se é simétrica, so prosseguindo se for simétrica
        while (!isSymmetric(matriz,colunas,linhas))
            System.out.println("A matriz inserida é inválida");
        //atualizar o k
        k=verificarValoresProprios(colunas,k);
        //estabelecer a matriz dos vetores
        double[][] matrizP=MatrizColunaDosVetoresProprios(matriz,k);
        //estabelecer a matriz dos valores
        double[][] matrizD=getEigenvalueDiagonal(matriz,k);
        //estabelecer a matriz coluna dos vetores transposta
        double[][] matrizTransposta=criarTransposta(matrizP);
        //estabelecer a matriz reconstruida
        double[][] reconstruida = reconstruirMatriz(matrizP,matrizD,matrizTransposta);
        System.out.println("Matriz reconstruída:");
        imprimirMatriz(reconstruida);
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

    public static double[][] reconstruirMatriz(double[][] matriz, int k) {


    }
    public static double[][] MatrizColunaDosVetoresProprios(RealMatrix matriz, int k) {
        EigenDecomposition decomposition = new EigenDecomposition(matriz);
        double[] eigenvectors = decomposition.getRealEigenvalues();
        Double[] sortedEigenvectors = Arrays.stream(eigenvectors)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Double[]::new);
        double[][] MatrizColuna = new double[k][1];
        for (int i = 0; i < k; i++) {
            MatrizColuna[i][0] = sortedEigenvectors[i];
        }return MatrizColuna;
    }
    public static double[][] getEigenvalueDiagonal(double[][] matrix, int k) {
        // Verificar se a matriz é quadrada
        if (matrix.length != matrix[0].length) {
            System.err.println("A matriz fornecida não é quadrada.");
            return null;
        }
        // Criar uma matriz RealMatrix a partir do array bidimensional
        RealMatrix realMatrix = new Array2DRowRealMatrix(matrix);
        // Calcular os valores próprios usando EigenDecomposition
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);
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
    public static double calcularErroAbsolutoMedio(double[][] matriz, double[][] approximatedMatrix, int k) {
        if (matriz.length != approximatedMatrix.length || matriz[0].length != approximatedMatrix[0].length) {
            throw new IllegalArgumentException("As matrizes devem ter as mesmas dimensões.");
        }
        double errorSum = 0.0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                errorSum += Math.abs(matriz[i][j] - reconstruida);
            }
        }

        return errorSum / (rows * cols);
    }
}
