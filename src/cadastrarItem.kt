fun cadastrarItem(){
    print("Nome (ou 0 para cancelar): ")
    val nome = readln()
    if (nome == "0") return

    print("Descrição: ")
    val descricao = readln()

    print("Preço (ou 0 para cancelar): ")
    val preco = readln().replace(',', '.').toDoubleOrNull()
    if (preco == null) {
        println("Preço inválido. Operação cancelada.")
        return
    }

    print("Quantidade em estoque (ou 0 para cancelar): ")
    val quantidade = readln().toIntOrNull()
    if (quantidade == null) {
        println("Quantidade inválida. Operação cancelada.")
        return
    }

    val item = cadastrarItem(nome.uppercase(), descricao, preco, quantidade)
    println("Item cadastrado com sucesso! Código: ${item.codigo}")
}
