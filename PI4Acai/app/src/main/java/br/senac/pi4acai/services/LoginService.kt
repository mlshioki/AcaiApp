package br.senac.pi4acai.services

import br.senac.pi4acai.models.Token
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
    @GET("/api/users")
    fun fazerLogin(@Query("name")usuario:String,
                   @Query("password")password:String):Call<Token>
}