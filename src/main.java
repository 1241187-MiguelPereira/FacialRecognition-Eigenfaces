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
        double[][] matriz = new double[8][8];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < 8) {
                line = line.trim(); // Remove extra whitespace
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
            return null; // Return null in case of an error
        }

        return matriz;
    }

    // Method to print a matrix
    public static void imprimirMatriz(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
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
