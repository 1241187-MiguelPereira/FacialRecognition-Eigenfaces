public class UI {
    public static void mostrarMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Reconstrução de Matriz");
        System.out.println("2 - Reconstrução de imagens");
        System.out.println("3 - Reconhecimento facial");
        System.out.println("END - Encerrar o programa");
    }

    public static void imprimirMatriz(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.2f ", value);
            }
            System.out.println();
        }
    }

}
