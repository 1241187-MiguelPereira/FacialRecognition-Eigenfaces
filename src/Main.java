import org.apache.commons.math3.linear.*;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {


        if (args.length == 0) {
            System.out.println("Modo Interativo Iniciado.");
            executarModoInterativo();
        } else {
            System.out.println("Modo Não Interativo Iniciado.");
            executarModoNaoInterativo(args);

        }
    }

    public static void executarModoInterativo() throws IOException {
        String escolha;
        do {
            // Exibir menu
            UI.mostrarMenu();

            // Ler a escolha do usuário
            System.out.println("Escolha uma opção:");
            escolha = sc.nextLine().trim().toUpperCase();

            // Processar escolha
            switch (escolha) {
                case "1":
                    System.out.println("Indique o caminho para o ficheiro CSV");
                    String filePath = sc.nextLine();
                    System.out.println("Digite o número de valores próprios a utilizar na reconstrução da matriz:");
                    int k = sc.nextInt();
                    sc.nextLine();
                    funcionalidade1(filePath, k); // Funcionalidade 1: Reconstrução de matriz
                    break;

                case "2":
                    System.out.println("Indique o nome da pasta:");
                    String pastaImagens = sc.nextLine();
                    System.out.println("Digite o número de eigenfaces a utilizar:");
                    int i = sc.nextInt();

                    funcionalidade2(i, pastaImagens); // Funcionalidade 2: Construção da matriz de imagens
                    break;

                case "3":
                    System.out.println("Indique o nome da pasta:");
                    String PastaImagens = sc.nextLine();
                    System.out.println("Indique o nome do ficheiro CSV");
                    String CaminhoFicheiro = "exemploImagensInputFuncionalidade3/" + sc.nextLine();
                    System.out.println("Digite o número de eigenfaces a usar:");
                    int e = sc.nextInt();
                    funcionalidade3(e, PastaImagens, CaminhoFicheiro);
                    break;
                case "4":
                    System.out.println("Indique o nome da pasta:");
                    String BaseDeImagens = sc.nextLine();
                    System.out.println("Digite o número de eigenfaces a usar:");
                    int f = sc.nextInt();
                    funcionalidade4(BaseDeImagens, f);
                case "END":
                    System.out.println("Programa encerrado. Obrigado!");
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");

            }
        } while (!escolha.equals("END"));
    }

    public static void executarModoNaoInterativo(String[] args) throws IOException {
        int f = -1;
        int k = -1;
        String i = null;
        String d = null;

        for (int j = 0; j < args.length; j++) {
            switch (args[j]) {
                case "-f":
                    f = Integer.parseInt(args[++j]);
                    break;
                case "-k":
                    k = Integer.parseInt(args[++j]);
                    break;
                case "-i":
                    i = args[++j];
                    break;
                case "-d":
                    d = args[++j];
                    break;
                default:
                    System.out.println("Parâmetro inválido: " + args[j]);
                    return;
            }
        }

        if (f < 1 || f > 4) {
            System.out.println("Erro: A funcionalidade (f) deve ser 1, 2 ou 3.");
            return;
        }
        if ((f == 1 || f == 3) && i == null) {
            System.out.println("Erro: O parâmetro '-i' é obrigatório para as funcionalidades 1 e 3.");
            return;
        }
        if ((f == 2 || f == 3 || f == 4) && d == null) {
            System.out.println("Erro: O parâmetro '-d' é obrigatório para as funcionalidades 2 e 3.");
            return;
        }

        switch (f) {
            case 1:
                System.out.println("A executar Funcionalidade 1...");
                funcionalidade1(i, k);
                break;
            case 2:
                System.out.println("A executar Funcionalidade 2...");
                funcionalidade2(k, d);
                break;
            case 3:
                System.out.println("A executar Funcionalidade 3...");
                funcionalidade3(k, d, i);
                break;
            default:
                System.out.println("Erro: Funcionalidade inválida.");
        }
    }


    public static void funcionalidade1(String filePath, int k) {


        // Ler a matriz do ficheiro
        double[][] matriz = Leitura.lerMatriz(filePath);
        if (matriz == null) {
            System.out.println("Erro: Matriz não pôde ser carregada. Verifique o arquivo e tente novamente.");

        }


        // Estabelecer o índice da matriz
        int colunas = matriz[0].length;
        int linhas = matriz.length;

        // Verificar se é simétrica; prosseguir somente se for simétrica
        while (!Validacoes.isSymmetric(matriz, colunas, linhas)) {
            System.out.println("A matriz inserida é inválida. Tente novamente.");
            matriz = Leitura.lerMatriz(filePath); // Solicitar novamente a matriz
        }

        // Atualizar o k
        k = Validacoes.verificarValoresProprios(colunas, k);

        // Criar RealMatrix
        RealMatrix matrizOriginal = new Array2DRowRealMatrix(matriz);

        // Estabelecer a matriz dos vetores
        double[][] matrizP = Calculos.MatrizColunaDosVetoresProprios(matrizOriginal, k);

        // Estabelecer a matriz dos valores
        double[][] matrizD = Calculos.getEigenvalueDiagonal(matrizOriginal, k);

        // Estabelecer a matriz coluna dos vetores transposta
        double[][] matrizTransposta = Calculos.criarTransposta(matrizP);

        // Estabelecer a matriz reconstruída
        double[][] reconstruida = Calculos.reconstruirMatriz(matrizP, matrizD, matrizTransposta);

        // Exibir resultados
        System.out.println("Matriz reconstruída:");
        Escrita.imprimirMatriz(reconstruida);

        System.out.println("Número de valores utilizados: " + k);
        System.out.println("Matriz dos vetores:");
        Escrita.imprimirMatriz(matrizP);
        System.out.println("Matriz dos valores:");
        Escrita.imprimirMatriz(matrizD);

        double ErroMedio = Calculos.calcularErroAbsolutoMedio(matriz, reconstruida, colunas, linhas);
        System.out.printf("Erro Absoluto Médio: %.2f\n", ErroMedio);

        // Exportar matriz reconstruída
        Escrita.ExportarMatriz(reconstruida,"matriz_Reconstruida","src\\outputs\\Funcionalidade1\\MatrizReconstruida");


    }

    public static void funcionalidade2(int i, String pastaImagens) throws IOException {


        //criação da matrizDeImagens a partir do CSV
        double[][] matrizImagens = Leitura.construirMatrizDeImagens(pastaImagens);
        int colunas = matrizImagens[0].length;

        if (matrizImagens == null) {
            System.out.println("Erro ao construir a matriz de imagens. Verifique a pasta de imagens.");
            return;
        }


        //estabelecer número de EIGENFACES a serem utilizadas

        int x = Validacoes.verificarValoresProprios(colunas, i);
        System.out.println("Numero de eigenfaces: ");
        System.out.println(x);

        //estabelecer vetor com as médias

        double[] vetorMedias = Calculos.VetorLinhaMedias(matrizImagens);
        System.out.println("Vetor coluna das médias:");
        Escrita.ImprimirVetor(vetorMedias);
        int linhas = matrizImagens.length;

        double[][] matrizA = Calculos.CálculoDoDesvio(vetorMedias, matrizImagens);
        double[][] matrizCov = Calculos.MatrizdasCovarianciasTeorema(matrizA);

        RealMatrix matrizCovReal = new Array2DRowRealMatrix(matrizCov);
        double[][] matrizVetores = Calculos.MatrizColunaDosVetoresProprios(matrizCovReal, x);
        double[][] matrizVetoresC = Calculos.calcularEigenfaces(matrizA, matrizVetores);
        double[][] normalizados = Calculos.normalizeEigenfaces(matrizVetoresC);


        double[][] matrizPesos = Calculos.calcularPesos(matrizA, normalizados, x);
        Escrita.ImprimirPesos(matrizPesos, x);
        double[][] eigenRecalibradas = Calculos.reescalonarEigenfaces(normalizados);
        double[][] imagensReconstruidas = Calculos.reconstruirImagens(vetorMedias, normalizados, matrizPesos, x, -1);
        double[][] imagensRescalonadas = Calculos.reescalonarEigenfaces(imagensReconstruidas);
        int[] indicesDiferentesImagens = Validacoes.compararMatrizes(imagensRescalonadas, imagensReconstruidas);


        //-----OUTPUTS FUNCIONALIDADE 2-----//

        Escrita.guardarImagem(imagensRescalonadas, "src\\outputs\\Funcionalidade2\\ImagensReconstruidas", "Imagem");
        Escrita.guardarImagem(eigenRecalibradas, "src\\outputs\\Funcionalidade2\\Eigenfaces", "Eigenface");
        Escrita.guardarCSV(imagensReconstruidas, "src\\outputs\\Funcionalidade2\\ImagensReconstruidas", "Imagem", new int[0]);
        Escrita.guardarCSV(imagensRescalonadas, "src\\outputs\\Funcionalidade2\\ImagensReconstruidas", "Imagem_Transformada", indicesDiferentesImagens);
        Escrita.guardarCSV(normalizados, "src\\outputs\\Funcionalidade2\\Eigenfaces", "Eigenface_Original", new int[0]);
        Escrita.guardarCSV(eigenRecalibradas, "src\\outputs\\Funcionalidade2\\Eigenfaces", "Eigenface_Transformada", new int[0]);
        double ErroMedio = Calculos.calcularErroAbsolutoMedio(matrizImagens, imagensRescalonadas, colunas, linhas);
        System.out.printf("Erro Absoluto Médio: %.2f\n", ErroMedio);

    }


    public static void funcionalidade3(int k, String pastaImagens, String filePath) {


        double[][] matrizImagens = Leitura.construirMatrizDeImagens(pastaImagens);
        int colunas = matrizImagens[0].length;


        // Ler a matriz do ficheiro
        double[][] ImagemAlvo = Leitura.lerMatriz(filePath);
        if (ImagemAlvo == null) {
            System.out.println("Erro: Matriz não pôde ser carregada. Verifique o arquivo e tente novamente.");
        }


        int x = Validacoes.verificarValoresProprios(colunas, k);
        System.out.println("Número de Eigenfaces/Pesos utilizados na identificação da imagem:");
        System.out.println(x);


        if (matrizImagens != null) {
            System.out.println("Matriz de imagens construída com sucesso:");
            //imprimirMatriz(matrizImagens);
        } else {
            System.out.println("Erro ao construir a matriz de imagens. Verifique a pasta de imagens.");
        }
        //calcular o vetor com as medias de cada coluna
        double[] vetorMedias = Calculos.VetorLinhaMedias(matrizImagens);

        //calcular o desvio de cada elemento à média
        double[][] matrizA = Calculos.CálculoDoDesvio(vetorMedias, matrizImagens);
        //calcular A^T.AT
        double[][] matrizCov = Calculos.MatrizdasCovarianciasTeorema(matrizA);
        //transformar numa matriz reconhecivel pela libraria
        RealMatrix matrizCovReal = new Array2DRowRealMatrix(matrizCov);
        // calcular os vetores próprios
        double[][] matrizVetores = Calculos.MatrizColunaDosVetoresProprios(matrizCovReal, x);

        // calcular as eigenfaces, e armazená-las numa matriz
        double[][] matrizVetoresC = Calculos.calcularEigenfaces(matrizA, matrizVetores);
        double[][] normalizados = Calculos.normalizeEigenfaces(matrizVetoresC);
        //UI.imprimirMatriz(normalizados);
        double[][] matrizpesos = Calculos.calcularPesos(matrizA, normalizados, x);
        Escrita.ImprimirPesos(matrizpesos, x);


        double[] vetorAlvo = Conversoes.converterMatrizParaVetor(ImagemAlvo);
        double[] vetorCentralizado = Calculos.CalculoDoDesvio(vetorMedias, vetorAlvo);
        double[] vetorPesosAlvo = Calculos.calcularPesosImagemAlvo(vetorCentralizado, normalizados, x);
        double[] VetorDasDistanciasEuclidianas = Calculos.calcularDistanciaEuclidiana(vetorPesosAlvo, matrizpesos);
        Escrita.ImprimirDistancias(VetorDasDistanciasEuclidianas);
        int indice = Validacoes.IndicedoMenor(VetorDasDistanciasEuclidianas);
        double[][] ImagemMaisParecida = Calculos.RemoverImagemDeUmaMatriz(matrizImagens, indice);
        if (indice == -1) {
            System.out.print("Ocorreu um empate entre duas ou mais imagens da identificação");
            Escrita.guardarImagem(matrizImagens, "src/outputs/Funcionalidade3/Identificação", "ImagemParecida");
        } else
            System.out.println("A imagem mais parecida na base de imagens fornecida é:");
        int numeroAssociadoImagem = (indice * 10) + 1;
        System.out.println("image_" + numeroAssociadoImagem + ".png");
        Escrita.guardarImagem(ImagemMaisParecida, "src/outputs/Funcionalidade3/Identificação", "ImagemParecida");
    }

    public static void funcionalidade4(String pastaImagens, int k) {

        //criação da matrizDeImagens a partir do CSV
        double[][] matrizImagens = Leitura.construirMatrizDeImagens(pastaImagens);
        int colunas = matrizImagens[0].length;

        if (matrizImagens == null) {
            System.out.println("Erro ao construir a matriz de imagens. Verifique a pasta de imagens.");
            return;
        }


        //estabelecer número de EIGENFACES a serem utilizadas

        int x = Validacoes.verificarValoresProprios(colunas, k);

        //estabelecer vetor com as médias

        double[] vetorMedias = Calculos.VetorLinhaMedias(matrizImagens);
        int linhas = matrizImagens.length;

        double[][] matrizA = Calculos.CálculoDoDesvio(vetorMedias, matrizImagens);
        double[][] matrizCov = Calculos.MatrizdasCovarianciasTeorema(matrizA);


        RealMatrix matrizCovReal = new Array2DRowRealMatrix(matrizCov);
        double[][] matrizVetores = Calculos.MatrizColunaDosVetoresProprios(matrizCovReal, x);
        double[][] matrizVetoresC = Calculos.calcularEigenfaces(matrizA, matrizVetores);
        double[][] normalizados = Calculos.normalizeEigenfaces(matrizVetoresC);
        double[][] valoresproprios = Calculos.getEigenvalueDiagonal(matrizCovReal, x);
        Escrita.imprimirMatriz(valoresproprios);
        double[][] imagemGerada = Calculos.gerarImagem(vetorMedias, valoresproprios, normalizados, x);
        double[][] imagemRescalonada = Calculos.reescalonarEigenfaces(imagemGerada);
        Escrita.guardarImagem(imagemRescalonada, "src/outputs/Funcionalidade4/ImagemGerada", "ImagemGerada");


    }
//java -jar out\artifacts\lapr1_24_25_DIJ_10_jar\lapr1-24-25_DIJ_10.jar -f 2 -k 40 -d "baseImagens"
//out\artifacts\lapr1_24_25_DIJ_10_jar\lapr1-24-25_DIJ_10.jar
}

