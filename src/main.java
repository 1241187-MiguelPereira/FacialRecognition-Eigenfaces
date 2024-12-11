import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    public static void main(String[] args) {

        String filePath = "exampleInputFunc1.csv"; // Replace with your file path

        // Read the matrix
        double[][] matriz = lerMatriz(filePath);

        Boolean simetrica = isSymmetric(matriz, matriz.length, matriz[0].length);



        // Print the matrix
        if (matriz != null) {
            imprimirMatriz(matriz);
        }

        System.out.println("simetrica = " + simetrica);




    }

    // Method to read a matrix from a CSV file
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


    public static boolean isSymmetric ( double[][] matrix, int rows, int cols){

        // Verifica se a matriz é quadrada
        if (rows != cols) {
            return false; // Apenas matrizes quadradas podem ser simétricas
        }

        // Verifica a simetria: A[i][j] deve ser igual a A[j][i]
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != matrix[j][i]) {
                    return false; // Não é simétrica
                }
            }
        }

        return true; // É simétrica
    }
}
