package br.senac.pi4acai.services

import android.content.Context
import br.senac.pi4acai.models.Token
import okhttp3.*

const val ARQUIVO_LOGIN = "login"

class AutenticadorToken(private val context:Context): Interceptor, Authenticator
{
    override fun intercept(chain: Interceptor.Chain): Response {
        val prefs = context.getSharedPreferences(ARQUIVO_LOGIN,  Context.MODE_PRIVATE)

        val token = prefs.getString("token","") as String

        var request = chain.request()

        request = request?.newBuilder()?.addHeader("token", token)?.build()

        return chain.proceed(request)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val prefs = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)

        val usuario = prefs.getString("usuario", "") as String

        val password = prefs.getString("senha", "") as String


        val respostaRetrofit = API(context).login.fazerLogin(usuario, password).execute()

        var token = respostaRetrofit.body()
        if(respostaRetrofit.isSuccessful && token != null){

            val editor = prefs.edit()

            editor.putString("token", token.token)

            editor.apply()

            return response?.request()?.newBuilder()?.header("token", token.token)?.build()
        }
    return null
    }

}