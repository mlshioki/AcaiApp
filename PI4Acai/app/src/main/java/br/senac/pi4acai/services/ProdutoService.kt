package br.senac.pi4acai.services

import br.senac.pi4acai.models.Produto
import retrofit2.Call
import retrofit2.http.*


interface ProdutoService {
    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/product")
    fun listar(): Call<List<Produto>>
/*
    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/carrinho/{product}")
    fun listarCarrinho(): Call<List<Produto>>

    @GET("/api/produto/{product}")
    //Parâmetro de URL
    fun pesquisar(@Path("product") produto: String): Call<List<Produto>>

    @GET("/api/product")
    //Parâmetro de consulta (query)
    fun pesquisar2(@Query("produto") produto: String): Call<List<Produto>>

    @POST("/api/product")
    fun inserir(@Body produto: Produto): Call<Produto>

    @PUT("/api/product")
    fun atualizar(@Body produto: Produto): Call<Produto>

    @DELETE("/api/product")
    fun excluir(@Query("id") id: Int): Call<Produto>*/

}