import java.io.*;
import java.util.*;

class Livro {
    private String titulo;
    private String autor;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + ", Autor: " + autor;
    }
}

public class SistemaCRUD {

    private static final String ARQUIVO_DADOS = "livros.txt";
    private static List<Livro> livros = new ArrayList<>();

    public static void main(String[] args) {
        carregarDados();
        exibirMenu();
    }

    private static void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n====== Menu ======");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Atualizar Livro");
            System.out.println("4. Excluir Livro");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    adicionarLivro(scanner);
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    atualizarLivro(scanner);
                    break;
                case 4:
                    excluirLivro(scanner);
                    break;
                case 5:
                    salvarDados();
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);

        scanner.close();
    }

    private static void adicionarLivro(Scanner scanner) {
        System.out.println("\nAdicionar Livro");
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        Livro livro = new Livro(titulo, autor);
        livros.add(livro);
        System.out.println("Livro adicionado com sucesso.");
    }

    private static void listarLivros() {
        System.out.println("\nLista de Livros:");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private static void atualizarLivro(Scanner scanner) {
        System.out.println("\nAtualizar Livro");
        listarLivros();
        System.out.print("Digite o número do livro que deseja atualizar: ");
        int indice = scanner.nextInt();

        if (indice >= 0 && indice < livros.size()) {
            scanner.nextLine(); // Limpar o buffer
            System.out.print("Novo Título: ");
            String novoTitulo = scanner.nextLine();
            System.out.print("Novo Autor: ");
            String novoAutor = scanner.nextLine();

            Livro livro = livros.get(indice);
            livro = new Livro(novoTitulo, novoAutor);
            livros.set(indice, livro);
            System.out.println("Livro atualizado com sucesso.");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private static void excluirLivro(Scanner scanner) {
        System.out.println("\nExcluir Livro");
        listarLivros();
        System.out.print("Digite o número do livro que deseja excluir: ");
        int indice = scanner.nextInt();

        if (indice >= 0 && indice < livros.size()) {
            livros.remove(indice);
            System.out.println("Livro excluído com sucesso.");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private static void carregarDados() {
        try (Scanner scanner = new Scanner(new File(ARQUIVO_DADOS))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    Livro livro = new Livro(partes[0], partes[1]);
                    livros.add(livro);
                }
            }
            System.out.println("Dados carregados do arquivo.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        }
    }

    private static void salvarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (Livro livro : livros) {
                writer.println(livro.getTitulo() + "," + livro.getAutor());
            }
            System.out.println("Dados salvos no arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}

