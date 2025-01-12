import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Leitura {
      public static double[][] lerMatriz(String filePath, boolean Simetria) {
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
                  if(!Validacoes.verificarMatriz(matriz,Simetria)){
                              System.out.println("Erro: A matriz não é válida. O programa será encerrado.");
                              System.exit(1); // Encerra o programa
                  }
                  return matriz;
            } catch (FileNotFoundException e) {
                  System.out.println("Error: File not found.");
                  return null;
            }
      }

      public static double[][] construirMatrizDeImagens(String pasta,boolean Simetria) {
            File folder = new File(pasta);
            File[] arquivos = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

            if (arquivos == null || arquivos.length == 0) {
                  System.out.println("Nenhuma imagem encontrada na pasta: " + pasta);
                  return null;
            }

            // Criar um array para armazenar os vetores de imagens
            int quantidadeDeImagens = arquivos.length;
            double[][] matrizM = null;
            int tamanhoDoVetor = -1;

            for (int i = 0; i < quantidadeDeImagens; i++) {
                  File arquivo = arquivos[i];
                  System.out.println("Lendo imagem: " + arquivo.getName());
                  double[][] matriz = Leitura.lerMatriz(arquivo.getAbsolutePath(),Simetria);

                  if (matriz == null) {
                        System.out.println("Erro ao ler a matriz do arquivo: " + arquivo.getName());
                        return null; // Retorna null se algum erro ocorrer
                  }

                  double[] vetor = Conversoes.converterMatrizParaVetor(matriz);

                  // Verificar consistência das dimensões
                  if (tamanhoDoVetor == -1) {
                        tamanhoDoVetor = vetor.length;
                        matrizM = new double[tamanhoDoVetor][quantidadeDeImagens]; // Inicializa matrizM com as dimensões corretas
                  } else if (vetor.length != tamanhoDoVetor) {
                        System.out.println("Erro: Imagem " + arquivo.getName() + " tem dimensões diferentes.");
                        return null; // Retorna null se as dimensões forem inconsistentes
                  }

                  // Adicionar o vetor à matriz M
                  for (int j = 0; j < tamanhoDoVetor; j++) {
                        matrizM[j][i] = vetor[j];
                  }
            }

            return matrizM;
      }

}
