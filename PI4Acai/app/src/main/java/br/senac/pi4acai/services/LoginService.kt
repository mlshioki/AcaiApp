package br.senac.pi4acai.services

import br.senac.pi4acai.models.Token
import br.senac.pi4acai.models.login
import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    @POST("/api/login")
    fun fazerLogin(@Body login: login):Call<Token>
}