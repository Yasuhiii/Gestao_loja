// ============================================================
// Classe Loja
// Gere todos os produtos e clientes da loja
// ============================================================

import java.util.ArrayList;

public class Loja {

    // --- ATRIBUTOS ---
    private String nomeLoja;
    private ArrayList<Produto> produtos;    // Lista de todos os produtos da loja
    private ArrayList<Cliente> clientes;    // Lista de todos os clientes da loja
    private ArrayList<Pedido> pedidos;      // Lista de todos os pedidos feitos

    // --- CONSTRUTOR ---
    public Loja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
        this.produtos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.pedidos  = new ArrayList<>();
        System.out.println("Loja '" + nomeLoja + "' criada com sucesso!");
    }

    // --- MÉTODOS DE GESTÃO DE PRODUTOS ---

    // Adiciona um produto ao catálogo da loja
    public void adicionarProduto(Produto produto) {
        if (produto != null) {
            produtos.add(produto);
            System.out.println("Produto '" + produto.getNome() + "' registado na loja.");
        }
    }

    // Procura um produto pelo nome (ignora maiúsculas/minúsculas)
    public Produto procurarProduto(String nome) {
        for (Produto p : produtos) { // for-each: percorre cada produto da lista
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p; // Encontrou! Devolve o produto
            }
        }
        return null; // Não encontrou
    }

    // Mostra todos os produtos disponíveis
    public void listarProdutos() {
        System.out.println("\n===== CATÁLOGO DE PRODUTOS =====");
        if (produtos.isEmpty()) {
            System.out.println("  Sem produtos registados.");
        } else {
            for (Produto p : produtos) {
                System.out.println("  " + p); // Chama o toString() de Produto
            }
        }
        System.out.println("================================\n");
    }

    // --- MÉTODOS DE GESTÃO DE CLIENTES ---

    // Regista um cliente na loja
    public void adicionarCliente(Cliente cliente) {
        if (cliente != null) {
            clientes.add(cliente);
            System.out.println("Cliente '" + cliente.getNome() + "' registado. ID: " + cliente.getId());
        }
    }

    // Mostra todos os clientes
    public void listarClientes() {
        System.out.println("\n===== CLIENTES REGISTADOS =====");
        if (clientes.isEmpty()) {
            System.out.println("  Sem clientes registados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println("  " + c); // Chama o toString() de Cliente
            }
        }
        System.out.println("===============================\n");
    }

    // --- MÉTODOS DE GESTÃO DE PEDIDOS ---

    // Regista um pedido concluído na loja
    public void registarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
        }
    }

    // Mostra o valor total de todos os produtos em stock
    public void mostrarValorTotalStock() {
        double valorTotal = 0;
        for (Produto p : produtos) {
            valorTotal += p.calcularValorTotalEmStock();
        }
        System.out.printf("%nValor total em stock na loja: %.2f€%n%n", valorTotal);
    }
}
