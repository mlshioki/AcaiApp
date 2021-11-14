package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.pi4acai.databinding.CardCarrinhoBinding
import br.senac.pi4acai.databinding.FragmentCarrinhoBinding
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.services.ProdutoService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarrinhoFragment : Fragment(R.layout.fragment_carrinho) {
    lateinit var binding: FragmentCarrinhoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarrinhoBinding.inflate(inflater)
        //atualizarProdutos()
        return binding.root
    }
    fun atualizarProdutosCarrinho(){
        //obter instancia
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProdutoService::class.java)
        val call = service.listar()

        val callback = object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful){
                    val listarCarrinho = response.body()
                    atualizarUI(listarCarrinho)
                }else{
                    //val error = response.errorBody().toString()
                    Snackbar.make(binding.containerCarrinho, "Não deu certo", Snackbar.LENGTH_LONG).show()
                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(binding.containerCarrinho, "Deu ruim ", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR","Falha ao executar o serviço", t)
            }
        }
        call.enqueue(callback)
    }

    override fun onResume() {
        super.onResume()

        atualizarProdutosCarrinho()
    }
    fun atualizarUI(lista : List<Produto>?){
        binding.containerCarrinho.removeAllViews()
        lista?.forEach {
            val cardCarrinhoBinding = CardCarrinhoBinding.inflate(layoutInflater)
            cardCarrinhoBinding.textTitulo.text = it.name
            cardCarrinhoBinding.textDesc.text = it.description

            binding.containerCarrinho.addView(cardCarrinhoBinding.root)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = CarrinhoFragment()
    }
}

