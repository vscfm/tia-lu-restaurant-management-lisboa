fun main() {
    do {
        println("\n=== LU DELIVERY ===")
        println("1. Cadastrar Item")
        println("2. Atualizar Item")
        println("3. Criar Pedido")
        println("4. Atualizar Pedido")
        println("5. Consultar Pedidos")
        println("0. Sair")
        print("Escolha uma opção: ")

        val opcao = readln()
        when (opcao) {

            // CADASTRAR ITEM
            "1" -> {
                print("Nome (ou 0 para cancelar): ")
                val nome = readln()
                if (nome == "0") continue

                print("Descrição: ")
                val descricao = readln()

                print("Preço (ou 0 para cancelar): ")
                val preco = readln().replace(',', '.').toDoubleOrNull()
                if (preco == null) {
                    println("Preço inválido. Operação cancelada.")
                    continue
                }

                print("Quantidade em estoque (ou 0 para cancelar): ")
                val quantidade = readln().toIntOrNull()
                if (quantidade == null) {
                    println("Quantidade inválida. Operação cancelada.")
                    continue
                }

                val item = cadastrarItem(nome.uppercase(), descricao, preco, quantidade)
                println("Item cadastrado com sucesso! Código: ${item.codigo}")
            }

            // ATUALIZAR ITEM
            "2" -> {
                if (itens.isEmpty()) {
                    println("Nenhum item cadastrado.")
                    continue
                }

                println("Itens cadastrados:")
                itens.forEach { println("Código ${it.codigo} - ${it.nome} (R$ ${"%.2f".format(it.preco)}, ${it.quantidade} unid)") }

                print("Digite o código do item para atualizar (ou 0 para cancelar): ")
                val cod = readln().toIntOrNull()
                if (cod == 0 || cod == null) continue

                val item = itens.find { it.codigo == cod }
                if (item == null) {
                    println("Item não encontrado!")
                    continue
                }

                print("Novo nome (${item.nome}) ou Enter para manter: ")
                val novoNome = readln().ifBlank { null }

                print("Nova descrição (${item.descricao}) ou Enter para manter: ")
                val novaDesc = readln().ifBlank { null }

                print("Novo preço (${item.preco}) ou Enter para manter: ")
                val novoPreco = readln().replace(',', '.').toDoubleOrNull()

                print("Nova quantidade (${item.quantidade}) ou Enter para manter: ")
                val novaQtd = readln().toIntOrNull()

                atualizarItem(item, novoNome, novaDesc, novoPreco, novaQtd)
                println("Item atualizado com sucesso!")
            }

            // CRIAR PEDIDO
            "3" -> {
                if (itens.isEmpty()) {
                    println("Nenhum item disponível.")
                    continue
                }

                println("Itens disponíveis:")
                itens.forEach { println("Código ${it.codigo} - ${it.nome} (R$ ${"%.2f".format(it.preco)}, ${it.quantidade} unid)") }

                print("Digite o código do item (ou 0 para cancelar): ")
                val cod = readln().toIntOrNull()
                if (cod == 0 || cod == null) continue

                val item = itens.find { it.codigo == cod }
                if (item == null || item.quantidade <= 0) {
                    println("Item inválido ou sem estoque!")
                    continue
                }

                print("Quantidade (ou 0 para cancelar): ")
                val qtd = readln().toIntOrNull()
                if (qtd == null || qtd == 0) continue

                print("Possui cupom de desconto? (S/N): ")
                val cupom = readln()
                val desconto = if (cupom.equals("S", true)) 0.1 else 0.0

                val pedido = criarPedido(item, qtd, desconto)
                if (pedido == null) {
                    println("Erro ao criar pedido!")
                } else {
                    println("Pedido criado com sucesso! Valor final: R$ ${"%.2f".format(pedido.precoFinal)}")
                }
            }

            // ATUALIZAR PEDIDO
            "4" -> {
                if (pedidos.isEmpty()) {
                    println("Nenhum pedido registrado.")
                    continue
                }

                println("Pedidos:")
                pedidos.forEach { println("Código ${it.codigo} - ${it.item.nome} (${it.quantidade} unid) - Status: ${it.status}") }

                print("Digite o código do pedido (ou 0 para cancelar): ")
                val cod = readln().toIntOrNull()
                if (cod == 0 || cod == null) continue

                val pedido = pedidos.find { it.codigo == cod }
                if (pedido == null) {
                    println("Pedido não encontrado!")
                    continue
                }

                println("Escolha novo status (ou 0 para cancelar):")
                StatusPedido.values().forEachIndexed { i, s -> println("${i + 1} - $s") }
                val escolha = readln().toIntOrNull()
                if (escolha == 0 || escolha == null) continue

                val novoStatus = StatusPedido.values().getOrNull(escolha - 1)
                if (novoStatus == null) {
                    println("Opção inválida!")
                } else {
                    atualizarStatusPedido(pedido, novoStatus)
                    println("Status atualizado com sucesso!")
                }
            }

            // CONSULTAR PEDIDOS
            "5" -> {
                if (pedidos.isEmpty()) {
                    println("Nenhum pedido registrado.")
                    continue
                }

                println("Consultar pedidos por status:")
                println("0 - Cancelar")
                println("1 - Todos")
                StatusPedido.values().forEachIndexed { i, s -> println("${i + 2} - $s") }

                val opc = readln().toIntOrNull()
                if (opc == 0 || opc == null) continue

                val status = when (opc) {
                    1 -> null
                    in 2..(StatusPedido.values().size + 1) -> StatusPedido.values()[opc - 2]
                    else -> null
                }

                val lista = consultarPedidosPorStatus(status)
                if (lista.isEmpty()) {
                    println("Nenhum pedido encontrado para este status.")
                } else {
                    lista.forEach { println("Pedido ${it.codigo} - ${it.item.nome} (${it.quantidade} unid) - Status: ${it.status}") }
                }
            }

            // SAIR
            "0" -> {
                println("Encerrando o sistema...")
                break
            }

            else -> println("Opção inválida!")
        }
    } while (opcao != "0")
}
