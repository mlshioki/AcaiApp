package br.senac.pi4acai.models

data class Pedidos(
    //Pedidos
    val success: Boolean,
    val message : String,
    val erro : String,

    //CARINHO
    //val pedidosCarrinho: List<Carrinho>
    val id: Int,
    val created_at : String,
    val updated_at : String,
    val user_id : Int,
    val product_id: Int,
    //val quantity : Int,
    val cart_qtd : Int,
    val name : String,
    val EAN : String,
    val unit : String,
    val deleted_at : Boolean,
    val descritption : String,
    val price : String,
    val category_id : Int,
    val image : String,

)
