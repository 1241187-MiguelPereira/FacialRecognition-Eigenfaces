import org.apache.commons.math3.linear.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class main {
    public static void main(String[] args) {

        String filePath = "exampleInputFunc1.csv";

        double[][] matriz = lerMatriz(filePath);

        if (matriz != null) {
            boolean simetrica = isSymmetric(matriz, matriz.length, matriz[0].length);
            imprimirMatriz(matriz);
            System.out.println("Simétrica = " + simetrica);

            if (simetrica) {
                int k = verificarValoresProprios(matriz[0].length, 3); // Escolhe os 3 maiores valores próprios
                double[][] reconstruida = reconstruirMatriz(matriz, k);
                System.out.println("Matriz reconstruída:");
                imprimirMatriz(reconstruida);
            }
        }
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
        RealMatrix realMatrix = new Array2DRowRealMatrix(matriz);
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix);

        double[] eigenvalues = eigenDecomposition.getRealEigenvalues();
        Double[] sortedIndices = new Double[eigenvalues.length];
        for (int i = 0; i < eigenvalues.length; i++) {
            sortedIndices[i] = (double) i;
        }

        Arrays.sort(sortedIndices, Comparator.comparingDouble((Double i) -> -eigenvalues[i.intValue()]));

        double[][] Pk = new double[matriz.length][k];
        double[][] Dk = new double[k][k];

        for (int i = 0; i < k; i++) {
            int index = sortedIndices[i].intValue();
            Dk[i][i] = eigenvalues[index];
            double[] eigenvector = eigenDecomposition.getEigenvector(index).toArray();
            for (int j = 0; j < matriz.length; j++) {
                Pk[j][i] = eigenvector[j];
            }
        }

        double[][] PkT = criarTransposta(Pk);
        double[][] temp = multiplicarMatrizes(Pk, Dk);
        return multiplicarMatrizes(temp, PkT);
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
}
