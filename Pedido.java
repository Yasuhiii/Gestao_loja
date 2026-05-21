// ============================================================
// Classe Pedido
// Representa um pedido feito por um cliente
// Contém uma lista de produtos e as suas quantidades
// ============================================================

import java.util.ArrayList; // Precisamos importar o ArrayList para usá-lo

public class Pedido {

    // --- ATRIBUTOS PRIVADOS ---
    private Cliente cliente;                    // O cliente que fez o pedido
    private ArrayList<Produto> produtos;        // Lista dos produtos no pedido
    private ArrayList<Integer> quantidades;     // Lista das quantidades (uma por produto)
    // Nota: produtos.get(0) e quantidades.get(0) estão relacionados - são do mesmo produto!

    private static int contadorPedidos = 1;     // Para numerar os pedidos automaticamente
    private int numeroPedido;

    // --- CONSTRUTOR ---
    public Pedido(Cliente cliente) {

        // Validação: tem de ter um cliente
        if (cliente == null) {
            System.out.println("ERRO: Pedido precisa de um cliente!");
            return;
        }

        this.cliente = cliente;
        this.produtos = new ArrayList<>();     // Cria lista vazia de produtos
        this.quantidades = new ArrayList<>();  // Cria lista vazia de quantidades
        this.numeroPedido = contadorPedidos;
        contadorPedidos++;
    }

    // --- GETTERS ---
    public Cliente getCliente() {
        return this.cliente;
    }

    public int getNumeroPedido() {
        return this.numeroPedido;
    }

    // --- MÉTODOS ---

    // Adiciona um produto ao pedido com uma certa quantidade
    public boolean adicionarProduto(Produto produto, int quantidade) {

        // Validações básicas
        if (produto == null) {
            System.out.println("ERRO: Produto inválido!");
            return false;
        }
        if (quantidade <= 0) {
            System.out.println("ERRO: A quantidade tem de ser maior que 0!");
            return false;
        }

        // Verifica se há stock suficiente
        if (produto.getQuantidadeEmStock() < quantidade) {
            System.out.println("ERRO: Stock insuficiente para '" + produto.getNome() + "'!");
            System.out.println("       Disponível: " + produto.getQuantidadeEmStock() + " | Pedido: " + quantidade);
            return false;
        }

        // Verifica se este produto já está no pedido
        // Se estiver, apenas atualiza a quantidade em vez de adicionar duplicado
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(produto.getNome())) {
                int novaQtd = quantidades.get(i) + quantidade;

                // Verifica se o stock aguenta a quantidade total
                if (produto.getQuantidadeEmStock() < novaQtd) {
                    System.out.println("ERRO: Stock insuficiente para essa quantidade total!");
                    return false;
                }

                quantidades.set(i, novaQtd); // Atualiza a quantidade
                System.out.println("Quantidade de '" + produto.getNome() + "' atualizada para " + novaQtd);
                return true;
            }
        }

        // Adiciona o produto e a quantidade nas listas
        produtos.add(produto);
        quantidades.add(quantidade);

        // Reduz o stock do produto (já foi "reservado" para este pedido)
        produto.atualizarStock(-quantidade);

        System.out.println("Produto '" + produto.getNome() + "' (x" + quantidade + ") adicionado ao pedido!");
        return true;
    }

    // Remove um produto do pedido pelo nome
    public boolean removerProduto(String nomeProduto) {

        // Percorre a lista à procura do produto
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equalsIgnoreCase(nomeProduto)) {

                // Devolve o stock ao produto antes de remover
                int qtdDevolver = quantidades.get(i);
                produtos.get(i).atualizarStock(qtdDevolver);

                // Remove das duas listas (mesmo índice)
                produtos.remove(i);
                quantidades.remove(i);

                System.out.println("Produto '" + nomeProduto + "' removido do pedido.");
                return true;
            }
        }

        System.out.println("ERRO: Produto '" + nomeProduto + "' não encontrado no pedido.");
        return false;
    }

    // Calcula o valor total do pedido (soma preço * quantidade de cada produto)
    public double calcularTotalPedido() {
        double total = 0;

        // Percorre todos os produtos e multiplica preço pela quantidade
        for (int i = 0; i < produtos.size(); i++) {
            total += produtos.get(i).getPreco() * quantidades.get(i);
        }

        return total;
    }

    // Calcula o total com desconto (se o cliente tiver desconto)
    public double calcularTotalComDesconto() {
        double total = calcularTotalPedido();
        double desconto = cliente.calcularDesconto(total); // Polimorfismo aqui!
        return total - desconto;
    }

    // Mostra o resumo completo do pedido no ecrã
    public void exibirResumoPedido() {
        System.out.println("\n========================================");
        System.out.println("         RESUMO DO PEDIDO #" + numeroPedido);
        System.out.println("========================================");
        System.out.println("Cliente: " + cliente.getNome() + " (ID: " + cliente.getId() + ")");
        System.out.println("Tipo: " + cliente.getTipoCliente());
        System.out.println("----------------------------------------");

        // Verifica se o pedido tem produtos
        if (produtos.isEmpty()) {
            System.out.println("  (Pedido vazio - nenhum produto adicionado)");
        } else {
            System.out.println("Produtos:");

            // Mostra cada produto com a sua quantidade e subtotal
            for (int i = 0; i < produtos.size(); i++) {
                Produto p = produtos.get(i);
                int qtd = quantidades.get(i);
                double subtotal = p.getPreco() * qtd;

                System.out.printf("  - %-20s x%d  @ %.2f€  =  %.2f€%n",
                        p.getNome(), qtd, p.getPreco(), subtotal);
            }
        }

        System.out.println("----------------------------------------");

        double total = calcularTotalPedido();
        double desconto = cliente.calcularDesconto(total);
        double totalFinal = total - desconto;

        System.out.printf("Subtotal:   %.2f€%n", total);

        // Só mostra desconto se existir
        if (desconto > 0) {
            System.out.printf("Desconto:  -%.2f€%n", desconto);
        }

        System.out.printf("TOTAL:      %.2f€%n", totalFinal);
        System.out.println("========================================\n");
    }
}
