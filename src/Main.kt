fun main() {
    do {
      menu()
        val opcao = readln()
        when (opcao) {

            // CADASTRAR ITEM
            "1" -> cadastrarItem()
            // ATUALIZAR ITEM
            "2" -> atualizarItem()

            // CRIAR PEDIDO
            "3" -> criarPedido()

            // ATUALIZAR PEDIDO
            "4" -> atualizarPedido()
            // CONSULTAR PEDIDOS
            "5" -> consultarPedidos()

            // SAIR
            "0" -> {
                println("Encerrando o sistema...")
                break
            }

            else -> println("Opção inválida!")
        }
    } while (opcao != "0")
}
