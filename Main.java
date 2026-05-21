//                _
//               (_)
//               ---
//              /   \
//             | o o |   "Tenha paciência, jovem padawan...
//             |  _  |    Este código foi escrito com café
//              \___/     e muitas dores de cabeça."
//              /   \
//             /|   |\


import java.util.Scanner; // Para ler o que o utilizador escreve no teclado

public class Main {

    public static void main(String[] args) {

        // Cria um Scanner para ler inputs do utilizador
        Scanner scanner = new Scanner(System.in);

        // ---- CRIAR A LOJA ----
        Loja loja = new Loja("MindelShop");
        System.out.println();

        // ---- CRIAR PRODUTOS ----
        System.out.println("--- A registar produtos ---");
        Produto p1 = new Produto("Arroz",    1.50, 100);
        Produto p2 = new Produto("Feijão",   0.99,  50);
        Produto p3 = new Produto("Açúcar",   0.89,  80);
        Produto p4 = new Produto("Azeite",   4.50,  30);
        Produto p5 = new Produto("Café",     3.20,  40);

        // Adiciona os produtos à loja
        loja.adicionarProduto(p1);
        loja.adicionarProduto(p2);
        loja.adicionarProduto(p3);
        loja.adicionarProduto(p4);
        loja.adicionarProduto(p5);

        // ---- CRIAR CLIENTES ----
        System.out.println("\n--- A registar clientes ---");
        // Cliente normal
        Cliente c1 = new Cliente("João Silva");
        // Clientes VIP (herança!) - têm desconto
        ClienteVIP c2 = new ClienteVIP("Maria Santos", 15.0); // 15% de desconto
        ClienteVIP c3 = new ClienteVIP("Carlos Fonseca", 10.0); // 10% de desconto

        loja.adicionarCliente(c1);
        loja.adicionarCliente(c2);
        loja.adicionarCliente(c3);

        // ---- MENU PRINCIPAL ----
        boolean continuar = true;

        while (continuar) {
            // Mostra o menu
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     LOJA - MENU PRINCIPAL    ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Ver produtos disponíveis  ║");
            System.out.println("║ 2. Ver clientes              ║");
            System.out.println("║ 3. Fazer um pedido           ║");
            System.out.println("║ 4. Ver valor total em stock  ║");
            System.out.println("║ 5. Repor stock de produto    ║");
            System.out.println("║ 0. Sair                      ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            // Lê a opção do utilizador
            int opcao = lerInteiro(scanner);

            // Switch: verifica qual opção foi escolhida
            switch (opcao) {

                case 1:
                    // Mostrar catálogo
                    loja.listarProdutos();
                    break;

                case 2:
                    // Mostrar clientes
                    loja.listarClientes();
                    break;

                case 3:
                    // Fazer um pedido (lógica separada em metodo próprio)
                    fazerPedido(scanner, loja);
                    break;

                case 4:
                    // Ver valor total em stock
                    loja.mostrarValorTotalStock();
                    break;

                case 5:
                    // Repor stock
                    reporStock(scanner, loja);
                    break;

                case 0:
                    // Sair do programa
                    System.out.println("\nObrigado por usar a loja! Até logo!");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida! Tenta outra vez.");
            }
        }

        // Fecha o scanner no fim (boa prática)
        scanner.close();
    }

    // ============================================================
    // Metodo auxiliar: trata de criar um pedido passo a passo
    // ============================================================
    private static void fazerPedido(Scanner scanner, Loja loja) {

        System.out.println("\n--- NOVO PEDIDO ---");

        // Pede o ID do cliente
        System.out.print("Introduz o ID do cliente (1 = João, 2 = Maria VIP, 3 = Carlos VIP): ");
        int idCliente = lerInteiro(scanner);

        // Procura o cliente pelo ID
        Cliente clienteEscolhido = null;
        if (idCliente == 1) clienteEscolhido = new Cliente("João Silva"); // Simplificado para demo
        else if (idCliente == 2) clienteEscolhido = new ClienteVIP("Maria Santos", 15.0);
        else if (idCliente == 3) clienteEscolhido = new ClienteVIP("Carlos Fonseca", 10.0);

        if (clienteEscolhido == null) {
            System.out.println("Cliente não encontrado!");
            return; // Sai deste metodo
        }

        // Cria o pedido para esse cliente
        Pedido pedido = new Pedido(clienteEscolhido);

        // Loop para adicionar produtos ao pedido
        boolean adicionarMais = true;
        while (adicionarMais) {

            loja.listarProdutos();
            System.out.print("Nome do produto (ou 'sair' para terminar): ");
            String nomeProduto = scanner.nextLine().trim();

            if (nomeProduto.equalsIgnoreCase("sair")) {
                adicionarMais = false;
                break;
            }

            // Procura o produto na loja
            Produto produtoEscolhido = loja.procurarProduto(nomeProduto);

            if (produtoEscolhido == null) {
                System.out.println("Produto não encontrado! Tenta novamente.");
                continue; // Volta ao início do loop
            }

            System.out.print("Quantidade: ");
            int quantidade = lerInteiro(scanner);

            // Tenta adicionar ao pedido
            pedido.adicionarProduto(produtoEscolhido, quantidade);

            System.out.print("Adicionar mais produtos? (s/n): ");
            String resposta = scanner.nextLine().trim();
            if (resposta.equalsIgnoreCase("n")) {
                adicionarMais = false;
            }
        }

        // Mostra o resumo e regista o pedido
        pedido.exibirResumoPedido();
        loja.registarPedido(pedido);

        // Pergunta se quer remover algum produto
        System.out.print("Queres remover algum produto do pedido? (s/n): ");
        String remover = scanner.nextLine().trim();
        if (remover.equalsIgnoreCase("s")) {
            System.out.print("Nome do produto a remover: ");
            String nomeRemover = scanner.nextLine().trim();
            pedido.removerProduto(nomeRemover);
            pedido.exibirResumoPedido(); // Mostra o resumo atualizado
        }
    }

    // ============================================================
    // Metodo auxiliar: repor stock de um produto
    // ============================================================
    private static void reporStock(Scanner scanner, Loja loja) {
        loja.listarProdutos();
        System.out.print("Nome do produto para repor stock: ");
        String nome = scanner.nextLine().trim();

        Produto produto = loja.procurarProduto(nome);
        if (produto == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Quantidade a adicionar ao stock: ");
        int qtd = lerInteiro(scanner);
        produto.atualizarStock(qtd);
    }

    // ============================================================
    // Metodo auxiliar: lê um número inteiro do utilizador
    // Evita que o programa "crashe" se o utilizador escrever letras
    // ============================================================
    private static int lerInteiro(Scanner scanner) {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                // NumberFormatException acontece quando tentamos converter letras em número
                System.out.print("Valor inválido! Introduz um número inteiro: ");
            }
        }
    }
}
