package br.senac.pi4acai

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.senac.pi4acai.databinding.ActivityLoginBinding
import br.senac.pi4acai.models.Token
import br.senac.pi4acai.services.API
import br.senac.pi4acai.services.ARQUIVO_LOGIN
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            val usuario = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val callback = object: Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    val token = response.body()
                    if (response.isSuccessful && token != null){
                        val editor = getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE).edit()
                        editor.putString("usuario", usuario)
                        editor.putString("senha", senha)
                        editor.putString("token", token.token)
                        editor.apply()
                        Toast.makeText(this@LoginActivity, "Login efetuado", Toast.LENGTH_LONG).show()
                    }else{
                        var msg = response.message().toString()
                            if(msg == ""){
                                msg = "não foi possivel efetuar login"
                            }
                        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
                        response.errorBody()?.let {
                            Log.e("LoginActivity", it.string())
                        }
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(this@LoginActivity,"",Toast.LENGTH_LONG).show()
                    Log.e("LoginActivity","onCreate",t)
                }

            }
            API(this).login.fazerLogin(usuario, senha).enqueue(callback)
        }
    }
}