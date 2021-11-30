package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.senac.pi4acai.databinding.CardProdutosItemBinding
import br.senac.pi4acai.databinding.FragmentHistoricoBinding
import br.senac.pi4acai.models.Pedidos
import br.senac.pi4acai.services.PedidosService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HistoricoFragment : Fragment(R.layout.fragment_historico) {

    lateinit var binding : FragmentHistoricoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoricoBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    fun atualizarProdutos(){
        val retrofit = Retrofit.Builder()
            //porta do simulador: 10.0.2.2
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PedidosService::class.java)
        val call = service.listarPedidos()



        val callback = object : Callback<List<Pedidos>> {
            override fun onResponse(call: Call<List<Pedidos>>, response: Response<List<Pedidos>>) {
                if(response.isSuccessful){
                    val listaPedidos = response.body()
                    atualizarIU(listaPedidos)
                } else{
                    Snackbar.make(binding.containerHistorico, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()
                    Log.e("ERROR", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<List<Pedidos>>, t: Throwable) {
                Snackbar.make(binding.containerHistorico, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        //call.enqueue(callback)
    }

    fun atualizarIU(lista: List<Pedidos>?){
        binding.containerHistorico.removeAllViews()

        lista?.forEach{
            val cardBinding = CardProdutosItemBinding.inflate(layoutInflater, binding.containerHistorico, false)
            cardBinding.editProdNome.text = it.name
            cardBinding.editProdPreco.text = it.price.toString()

            //Picasso.get().load("https://imgur.com/a/DKDrm1A/${it.id}").into(cardBinding.acaiImg)


            //cardBinding.conteinerHistorico.addView(binding.root)


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoricoFragment()
    }
}