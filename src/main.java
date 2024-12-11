import org.apache.commons.math3.linear.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {

        String filePath = "exampleInputFunc1.csv";

        double[][] matriz = lerMatriz(filePath);

        if (matriz != null) {
            Boolean simetrica = isSymmetric(matriz, matriz.length, matriz[0].length);
            imprimirMatriz(matriz);
            System.out.println("simetrica = " + simetrica);

            verificarValoresProprios(matriz);
        }
    }

    public static double[][] lerMatriz(String filePath) {
        double[][] matriz = new double[8][8];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < 8) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] values = line.split(",");
                    for (int col = 0; col < 8; col++) {
                        matriz[row][col] = Integer.parseInt(values[col].trim());
                    }
                    row++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }

        return matriz;
    }

    public static void imprimirMatriz(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
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

    public static void verificarValoresProprios(double[][] matriz) {
        RealMatrix matrix = MatrixUtils.createRealMatrix(matriz);

        EigenDecomposition decomposition = new EigenDecomposition(matrix);
        double[] eigenvalues = decomposition.getRealEigenvalues();

        System.out.println("Valores próprios:");
        for (double eigenvalue : eigenvalues) {
            System.out.println(eigenvalue);
            if (eigenvalue > 0) {
                System.out.println("Este valor próprio é maior que 0.");
            } else if (eigenvalue == 0) {
                System.out.println("Este valor próprio é igual a 0.");
            } else {
                System.out.println("Este valor próprio é menor que 0.");
            }
        }

        double maxEigenvalue = Arrays.stream(eigenvalues).max().getAsDouble();
        System.out.println("O maior valor próprio é: " + maxEigenvalue);
    }
}
