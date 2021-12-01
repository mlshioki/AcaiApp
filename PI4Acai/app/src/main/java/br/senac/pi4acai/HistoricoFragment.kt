package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.pi4acai.databinding.CardCarrinhoBinding
import br.senac.pi4acai.databinding.CardHistoricoBinding

import br.senac.pi4acai.databinding.CardProdutosItemBinding
import br.senac.pi4acai.databinding.FragmentHistoricoBinding
import br.senac.pi4acai.models.ListaPedidos
import br.senac.pi4acai.models.ListaPedidosMessage
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
        listarPedidos()
        return binding.root

    }


    fun listarPedidos(){
        val retrofit = Retrofit.Builder()
            //porta do simulador: 10.0.2.2
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PedidosService::class.java)
        val call = service.listarPedidos()



        val callback = object : Callback<ListaPedidos> {
            override fun onResponse(call: Call<ListaPedidos>, response: Response<ListaPedidos>) {
                if(response.isSuccessful){
                    val listaPedidos = response.body()?.message
                    atualizarIU(listaPedidos)
                } else{
                    Snackbar.make(binding.containerHistorico, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()
                    Log.e("ERROR", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<ListaPedidos>, t: Throwable) {
                Snackbar.make(binding.containerHistorico, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)
    }



    fun atualizarIU(lista: List<ListaPedidosMessage>?){
        binding.containerHistorico.removeAllViews()

        lista?.forEach{
            val cardBinding = CardHistoricoBinding.inflate(layoutInflater, binding.containerHistorico, false)
            cardBinding.textProdNomeHist.text = it.id.toString()
            cardBinding.textTotal.text = "R$ " + it.total

            var itens = ""
            it.itens.forEach{
                itens += it.product.name + " | Preço: " + it.product.price.toString() + " | Qtd: " + it.quantity.toString() + "\n"
            }

            cardBinding.TextItens.text = itens
            //Picasso.get().load("https://imgur.com/a/DKDrm1A/${it.id}").into(cardBinding.acaiImg)


            binding.containerHistorico.addView(cardBinding.root)


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoricoFragment()
    }
}