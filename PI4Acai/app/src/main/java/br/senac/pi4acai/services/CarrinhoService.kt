package br.senac.pi4acai.services

import br.senac.pi4acai.models.Carrinho
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.models.RespostaCarrinho
import br.senac.pi4acai.models.RespostaProduto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface CarrinhoService {

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/carrinho/")
    fun listarCarrinho(): Call<RespostaProduto>

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @PUT("/api/carrinho/{id}")
    fun addProduto(@Path("id") id: Int): Call<RespostaCarrinho>
}

