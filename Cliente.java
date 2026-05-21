// ============================================================
// Classe Cliente
// Representa um cliente da loja
// Esta é a classe "mãe" (superclasse) - serve de base para
// ClienteNormal e ClienteVIP (herança!)
// ============================================================

public class Cliente {

    // --- ATRIBUTOS PRIVADOS ---
    private String nome;
    private int id;

    // Contador estático: é partilhado por TODOS os objetos Cliente
    // Serve para gerar IDs únicos automaticamente (1, 2, 3, ...)
    private static int contadorId = 1;

    // --- CONSTRUTOR ---
    public Cliente(String nome) {

        // Validação do nome
        if (nome == null || nome.isEmpty()) {
            System.out.println("ERRO: Nome do cliente inválido! A usar 'Desconhecido'.");
            this.nome = "Desconhecido";
        } else {
            this.nome = nome;
        }

        // Atribui o ID atual e incrementa o contador para o próximo cliente
        this.id = contadorId;
        contadorId++;
    }

    // --- GETTERS ---

    public String getNome() {
        return this.nome;
    }

    public int getId() {
        return this.id;
    }

    // --- SETTER ---

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        } else {
            System.out.println("ERRO: Nome inválido.");
        }
    }

    // Método que calcula o desconto - será sobrescrito nas subclasses (polimorfismo!)
    // Na classe base, não há desconto nenhum
    public double calcularDesconto(double total) {
        return 0.0; // Clientes normais não têm desconto
    }

    // Devolve o tipo do cliente - também pode ser sobrescrito
    public String getTipoCliente() {
        return "Cliente Normal";
    }

    // toString do cliente
    @Override
    public String toString() {
        return "[ID: " + id + "] " + nome + " (" + getTipoCliente() + ")";
    }
}
