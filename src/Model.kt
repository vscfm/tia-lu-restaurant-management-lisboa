// Representação de um item do cardápio
data class Item(
    val codigo: Int,
    var nome: String,
    var descricao: String,
    var preco: Double,
    var quantidade: Int
)

// Representação do status do pedido
enum class StatusPedido {
    ACEITO, FAZENDO, FEITO, ESPERANDO_ENTREGADOR, SAIU_PARA_ENTREGA, ENTREGUE
}

// Representação de um pedido
data class ItemPedido(
    val item: Item,
    var quantidade: Int
)
data class Pedido(
    val codigo: Int,
    val itens: MutableList<ItemPedido>,
    var status: StatusPedido,
    var precoFinal: Double
)



// Listas globais
val itens = mutableListOf<Item>()
val pedidos = mutableListOf<Pedido>()

// Variáveis de controle
var codigoItemAtual = 1
var codigoPedidoAtual = 1

// Função para cadastrar item
fun cadastrarItem(nome: String, descricao: String, preco: Double, quantidade: Int): Item {
    val item = Item(codigoItemAtual++, nome, descricao, preco, quantidade)
    itens.add(item)
    return item
}

// Função para atualizar item
fun atualizarItem(item: Item, nome: String?, descricao: String?, preco: Double?, quantidade: Int?) {
    nome?.takeIf { it.isNotBlank() }?.let { item.nome = it }
    descricao?.takeIf { it.isNotBlank() }?.let { item.descricao = it }
    preco?.takeIf { it >= 0 }?.let { item.preco = it }
    quantidade?.takeIf { it >= 0 }?.let { item.quantidade = it }
}

// Função para criar pedido
fun criarPedido(itensPedido: List<ItemPedido>, desconto: Double): Pedido? {
    if (itensPedido.isEmpty()) return null

    var precoTotal = 0.0
    for (itemPedido in itensPedido) {
        val item = itemPedido.item
        val qtd = itemPedido.quantidade
        if (qtd <= 0 || qtd > item.quantidade) return null
        precoTotal += item.preco * qtd
    }

    val precoFinal = precoTotal - (precoTotal * desconto)
    val pedido = Pedido(codigoPedidoAtual++, itensPedido.toMutableList(), StatusPedido.ACEITO, precoFinal)
    pedidos.add(pedido)

    // Atualiza o estoque
    itensPedido.forEach { it.item.quantidade -= it.quantidade }

    return pedido
}


// Função para atualizar status de um pedido
fun atualizarStatusPedido(pedido: Pedido, novoStatus: StatusPedido) {
    pedido.status = novoStatus
}

// Função para consultar pedidos por status
fun consultarPedidosPorStatus(status: StatusPedido?): List<Pedido> {
    return when (status) {
        null -> pedidos
        else -> pedidos.filter { it.status == status }
    }
}
