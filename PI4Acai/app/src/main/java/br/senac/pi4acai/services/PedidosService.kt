package br.senac.pi4acai.services

import br.senac.pi4acai.models.Pedidos
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.models.RespostaCarrinho

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface PedidosService {
    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/pedidos/")
    fun listarPedidos(): Call<Pedidos>

    /*@Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @PUT("/api/pedidos/{id}")
    fun addProduto(@Path("id") id: Int): Call<RespostaCarrinho>*/
}