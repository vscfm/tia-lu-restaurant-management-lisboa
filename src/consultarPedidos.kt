fun consultarPedidos() {
    if (pedidos.isEmpty()) {
        println("Nenhum pedido registrado.")
        return
    }

    println("Consultar pedidos por status:")
    println("0 - Cancelar")
    println("1 - Todos")
    StatusPedido.values().forEachIndexed { i, s -> println("${i + 2} - $s") }

    val opc = readln().toIntOrNull()
    if (opc == null || opc == 0) return

    val status = when (opc) {
        1 -> null
        in 2..(StatusPedido.values().size + 1) -> StatusPedido.values()[opc - 2]
        else -> null
    }

    val lista = consultarPedidosPorStatus(status)
    if (lista.isEmpty()) {
        println("Nenhum pedido encontrado para este status.")
    } else {
        lista.forEach { pedido ->
            println("Pedido ${pedido.codigo} - Status: ${pedido.status}")
            println("Itens:")
            pedido.itens.forEach { itemPedido ->
                println("  - ${itemPedido.item.nome} (${itemPedido.quantidade} unid)")
            }
            println("Valor final: R$ ${"%.2f".format(pedido.precoFinal)}\n")
        }
    }
}
