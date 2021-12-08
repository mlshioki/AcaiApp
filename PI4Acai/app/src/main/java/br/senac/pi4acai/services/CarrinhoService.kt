package br.senac.pi4acai.services

import br.senac.pi4acai.models.*
import retrofit2.Call
import retrofit2.http.*

interface CarrinhoService {
    @GET("/api/carrinho/")
    fun listarCarrinho(): Call<RespostaProduto>

    @PUT("/api/carrinho/{id}")
    fun addProduto(@Path("id") id: Int): Call<RespostaCarrinho>

    @DELETE("/api/carrinho/removerProduto/{id}")
    fun excluirProdutoCarrinho(@Path("id") id: Int): Call<RespostaExclusao>

}

