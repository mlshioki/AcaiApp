package br.senac.pi4acai.services

import br.senac.pi4acai.models.Cliente
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ClienteService {
    @GET("/api/users")
    fun listar(): Call<List<Cliente>>
}