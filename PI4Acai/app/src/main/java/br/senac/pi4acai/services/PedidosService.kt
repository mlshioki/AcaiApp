package br.senac.pi4acai.services

import br.senac.pi4acai.models.*

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface PedidosService {
    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @GET("/api/pedidos/")
    fun listarPedidos(): Call<ListaPedidos>

    @Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @POST("/api/pedidos/finalizarPedido")
    fun finalizarPedido(@Body pedidos: Pedidos): Call<RespostaPedido>

    /*@Headers("Authorization: Bearer 1|un6tFYvtuCaiQYUz1WpwzgaBpVVCAgpJaUgGVEWS")
    @PUT("/api/pedidos/{id}")
    fun addProduto(@Path("id") id: Int): Call<RespostaCarrinho>*/
}