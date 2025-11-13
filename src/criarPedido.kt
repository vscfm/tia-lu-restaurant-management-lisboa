fun criarPedido() {
    if (itens.isEmpty()) {
        println("Nenhum item disponível.")
        return
    }

    val itensPedido = mutableListOf<ItemPedido>()

    while (true) {
        println("\nItens disponíveis:")
        itens.forEach {
            println("Código ${it.codigo} - ${it.nome} (R$ ${"%.2f".format(it.preco)}, ${it.quantidade} unid)")
        }

        print("Digite o código do item (ou 0 para finalizar): ")
        val cod = readln().toIntOrNull()
        if (cod == null || cod == 0) break

        val item = itens.find { it.codigo == cod }
        if (item == null || item.quantidade <= 0) {
            println("Item inválido ou sem estoque!")
            continue
        }

        print("Quantidade (ou 0 para cancelar): ")
        val qtd = readln().toIntOrNull()
        if (qtd == null || qtd == 0) continue

        if (qtd > item.quantidade) {
            println("Quantidade indisponível em estoque!")
            continue
        }

        // 🔽 Atualiza o estoque do item
        item.quantidade -= qtd

        // 🔽 Verifica se o item já está no pedido (para somar quantidades)
        val itemExistente = itensPedido.find { it.item.codigo == cod }
        if (itemExistente != null) {
            itemExistente.quantidade += qtd
        } else {
            itensPedido.add(ItemPedido(item, qtd))
        }
    }

    if (itensPedido.isEmpty()) {
        println("Nenhum item selecionado.")
        return
    }

    print("Possui cupom de desconto? (S/N): ")
    val cupom = readln()
    val desconto = if (cupom.equals("S", true)) 0.1 else 0.0

    val pedido = criarPedido(itensPedido, desconto)
    if (pedido == null) {
        println("Erro ao criar pedido!")
    } else {
        println("Pedido criado com sucesso! Valor final: R$ ${"%.2f".format(pedido.precoFinal)}")
    }
}
