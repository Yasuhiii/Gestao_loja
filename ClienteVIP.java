// ============================================================
// Classe ClienteVIP
// HERDA de Cliente (usa a palavra "extends")
// Um ClienteVIP TEM TUDO que um Cliente normal tem,
// MAS também tem desconto especial!
// ============================================================

public class ClienteVIP extends Cliente {

    // Atributo extra só para clientes VIP
    private double percentagemDesconto; // ex: 10.0 significa 10% de desconto

    // --- CONSTRUTOR ---
    // "super(nome)" chama o construtor da classe mãe (Cliente)
    public ClienteVIP(String nome, double percentagemDesconto) {
        super(nome); // Chama o construtor de Cliente primeiro!

        // Validação da percentagem de desconto
        if (percentagemDesconto < 0 || percentagemDesconto > 100) {
            System.out.println("ERRO: Percentagem inválida! A usar 10%.");
            this.percentagemDesconto = 10.0;
        } else {
            this.percentagemDesconto = percentagemDesconto;
        }
    }

    // --- GETTER ---
    public double getPercentagemDesconto() {
        return percentagemDesconto;
    }

    // --- OVERRIDE (sobrescrever métodos da classe mãe) ---
    // @Override diz ao Java: "este método substitui o da classe mãe"

    @Override
    public double calcularDesconto(double total) {
        // Calcula quanto o cliente VIP poupa
        return total * (percentagemDesconto / 100.0);
    }

    @Override
    public String getTipoCliente() {
        return "Cliente VIP (" + percentagemDesconto + "% desconto)";
    }
}
