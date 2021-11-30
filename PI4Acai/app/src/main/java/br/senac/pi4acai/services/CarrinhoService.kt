package br.senac.pi4acai.services

import br.senac.pi4acai.models.*
import retrofit2.Call
import retrofit2.http.*

interface CarrinhoService {

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/carrinho/")
    fun listarCarrinho(): Call<RespostaProduto>

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @PUT("/api/carrinho/{id}")
    fun addProduto(@Path("id") id: Int): Call<RespostaCarrinho>

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @DELETE("/api/carrinho/removerProduto/{id}")
    fun excluirProdutoCarrinho(@Path("id") id: Int): Call<RespostaExclusao>

}

