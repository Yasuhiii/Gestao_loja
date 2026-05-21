// ============================================================
// Classe Produto
// Representa um produto que existe na loja
// ============================================================

public class Produto {

    // --- ATRIBUTOS (privados, por causa do encapsulamento) ---
    // "private" significa que só esta classe pode aceder diretamente a estes valores
    private String nome;
    private double preco;
    private int quantidadeEmStock;

    // --- CONSTRUTOR ---
    // O construtor é chamado quando criamos um novo Produto com "new Produto(...)"
    // Recebe os valores iniciais e guarda-os nos atributos
    public Produto(String nome, double preco, int quantidadeEmStock) {

        // Validação: não deixar criar produto com nome vazio
        if (nome == null || nome.isEmpty()) {
            System.out.println("ERRO: O nome do produto não pode estar vazio!");
            this.nome = "Produto Sem Nome";
        } else {
            this.nome = nome;
        }

        // Validação: preço não pode ser negativo
        if (preco < 0) {
            System.out.println("ERRO: O preço não pode ser negativo! A usar 0.");
            this.preco = 0;
        } else {
            this.preco = preco;
        }

        // Validação: quantidade não pode ser negativa
        if (quantidadeEmStock < 0) {
            System.out.println("ERRO: A quantidade não pode ser negativa! A usar 0.");
            this.quantidadeEmStock = 0;
        } else {
            this.quantidadeEmStock = quantidadeEmStock;
        }
    }

    // --- GETTERS (métodos para LER os atributos privados) ---
    // Como os atributos são privados, precisamos de métodos públicos para os ler

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public int getQuantidadeEmStock() {
        return this.quantidadeEmStock;
    }

    // --- SETTERS (métodos para ALTERAR os atributos privados) ---

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        } else {
            System.out.println("ERRO: Nome inválido, não foi alterado.");
        }
    }

    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        } else {
            System.out.println("ERRO: Preço inválido, não foi alterado.");
        }
    }

    // --- MÉTODOS ---

    // Atualiza o stock do produto (pode ser positivo para adicionar, negativo para remover)
    public void atualizarStock(int quantidade) {
        int novaQuantidade = this.quantidadeEmStock + quantidade;

        // Verifica se o resultado não ficaria negativo
        if (novaQuantidade < 0) {
            System.out.println("ERRO: Stock insuficiente para '" + this.nome + "'!");
        } else {
            this.quantidadeEmStock = novaQuantidade;
            System.out.println("Stock de '" + this.nome + "' atualizado para: " + this.quantidadeEmStock);
        }
    }

    // Calcula o valor total de todos os itens deste produto em stock
    // Exemplo: 10 unidades a 5.00€ = 50.00€
    public double calcularValorTotalEmStock() {
        return this.preco * this.quantidadeEmStock;
    }

    // Método toString: quando fazemos System.out.println(produto), chama este método
    @Override
    public String toString() {
        return "Produto: " + nome + " | Preço: " + String.format("%.2f", preco) + "€ | Stock: " + quantidadeEmStock;
    }
}
