package br.senac.pi4acai.services

import br.senac.pi4acai.models.Produto
import retrofit2.Call
import retrofit2.http.*


interface ProdutoService {

    @GET("/api/product")
    fun listar(): Call<List<Produto>>

    @GET("/api/produto/{produto}")
    //Parâmetro de URL
    fun pesquisar(@Path("produto") produto: String): Call<List<Produto>>

    @GET("/api/produto")
    //Parâmetro de consulta (query)
    fun pesquisar2(@Query("produto") produto: String): Call<List<Produto>>

    @POST("/api/produto")
    fun inserir(@Body produto: Produto): Call<Produto>

    @PUT("/api/produto")
    fun atualizar(@Body produto: Produto): Call<Produto>

    @DELETE("/api/produto")
    fun excluir(@Query("id") id: Int): Call<Produto>

}