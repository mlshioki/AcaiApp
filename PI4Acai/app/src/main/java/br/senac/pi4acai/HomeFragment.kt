package br.senac.pi4acai

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import br.senac.pi4acai.databinding.CardItemBinding
import br.senac.pi4acai.databinding.FragmentHomeBinding
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.models.RespostaCarrinho
import br.senac.pi4acai.models.login
import br.senac.pi4acai.services.API
import br.senac.pi4acai.services.CarrinhoService
import br.senac.pi4acai.services.ProdutoService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var bind: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        bind = FragmentHomeBinding.inflate(inflater)

        atualizarProdutos()

        return bind.root
    }

    fun atualizarProdutos(){
        val callback = object : Callback<List<Produto>>{
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if(response.isSuccessful){
                  val listaProdutos = response.body()
                    atualizarIU(listaProdutos)
                } else{
                    Snackbar.make(bind.container1, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(bind.container1, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        API(requireContext()).produto.listar().enqueue(callback)
    }

    fun atualizarIU(lista: List<Produto>?){
        bind.container1.removeAllViews()

        lista?.forEach{
            val cardBinding = CardItemBinding.inflate(layoutInflater)

            cardBinding.editAcaiTitulo.text = it.name
            cardBinding.editAcaiPreco.text = "R$" + it.price.toString()
            cardBinding.imgAcai
            cardBinding.ratingBar.rating = 5F
            cardBinding.comprarBtn.setOnClickListener {
                    view->
                addProdutoCarrinho(it.id)
            }
            //Picasso.get().load("https://i.imgur.com/wcNZsqi.jpg").into(cardBinding.imgAcai)
            bind.container1.addView(cardBinding.root)

        }
    }

    fun addProdutoCarrinho(id: Int){


        val callback = object : Callback<RespostaCarrinho>{
            override fun onResponse(call: Call<RespostaCarrinho>, response: Response<RespostaCarrinho>) {
                if(response.isSuccessful){
                    //val success = response.body()
                    Toast.makeText(context, "Produto adicionado ao carrinho", Toast.LENGTH_LONG).show()
                } else{
                    Snackbar.make(bind.container1, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<RespostaCarrinho>, t: Throwable) {
                Snackbar.make(bind.container1, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        API(requireContext()).carrinho.addProduto(id).enqueue(callback)

    }



    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}