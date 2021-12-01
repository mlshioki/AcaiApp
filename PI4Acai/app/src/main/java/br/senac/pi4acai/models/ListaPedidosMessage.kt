package br.senac.pi4acai.models

data class ListaPedidosMessage(
    var id: Int,
    var user_id: Int,
    var status: String,
    var itens: List<ProdutoComQuantidadePedido>,
    var total : Float,
)

