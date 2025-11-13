fun atualizarPedido() {
    if (pedidos.isEmpty()) {
        println("Nenhum pedido registrado.")
        return
    }

    println("Pedidos:")
    pedidos.forEach { pedido ->
        println("Código ${pedido.codigo} - Status: ${pedido.status}")
        println("Itens do pedido:")
        pedido.itens.forEach { itemPedido ->
            println("  - ${itemPedido.item.nome} (${itemPedido.quantidade} unid)")
        }
        println("Valor final: R$ ${"%.2f".format(pedido.precoFinal)}\n")
    }

    print("Digite o código do pedido (ou 0 para cancelar): ")
    val cod = readln().toIntOrNull()
    if (cod == null || cod == 0) return

    val pedido = pedidos.find { it.codigo == cod }
    if (pedido == null) {
        println("Pedido não encontrado!")
        return
    }

    println("Escolha novo status (ou 0 para cancelar):")
    StatusPedido.values().forEachIndexed { i, s -> println("${i + 1} - $s") }
    val escolha = readln().toIntOrNull()
    if (escolha == null || escolha == 0) return

    val novoStatus = StatusPedido.values().getOrNull(escolha - 1)
    if (novoStatus == null) {
        println("Opção inválida!")
    } else {
        atualizarStatusPedido(pedido, novoStatus)
        println("Status atualizado com sucesso!")
    }
}
