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
            Boolean simetrica = isSymmetric(matriz, matriz.length, matriz[0].length);
            imprimirMatriz(matriz);
            System.out.println("simetrica = " + simetrica);

            verificarValoresProprios(matriz);
        }
    }

    public static double[][] lerMatriz(String filePath) {
        double[][] matriz = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // First pass: Count rows and columns
            int rows = 0;
            int cols = 0;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    rows++;
                    if (cols == 0) {
                        cols = line.split(",").length; // Determine number of columns from first line
                    }
                }
            }

            // Initialize the matrix with the correct size
            matriz = new double[rows][cols];

            // Second pass: Fill the matrix
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
            return null; // Return null in case of an error
        }

        return matriz;
    }

    // Method to print a matrix
    public static void imprimirMatriz(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%8.2f ", matrix[i][j]); // Formata cada elemento com largura fixa de 8 e 2 casas decimais
            }
            System.out.println(); // Quebra de linha após cada linha da matriz
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
}
