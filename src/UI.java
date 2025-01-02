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
      public static void imprimirVetor(double[] vetor) {
            System.out.print("[");
            for (int i = 0; i < vetor.length; i++) {
                  System.out.printf("%.2f", vetor[i]); // Formata com 2 casas decimais
                  if (i < vetor.length - 1) {
                        System.out.print(", "); // Adiciona vírgula entre os elementos
                  }
            }
            System.out.println("]");
      }
}
