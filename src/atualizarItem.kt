fun atualizarItem(){
    if (itens.isEmpty()) {
        println("Nenhum item cadastrado.")
        return
    }

    println("Itens cadastrados:")
    itens.forEach { println("Código ${it.codigo} - ${it.nome} (R$ ${"%.2f".format(it.preco)}, ${it.quantidade} unid)") }

    print("Digite o código do item para atualizar (ou 0 para cancelar): ")
    val cod = readln().toIntOrNull()
    if (cod == 0 || cod == null) return

    val item = itens.find { it.codigo == cod }
    if (item == null) {
        println("Item não encontrado!")
        return
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