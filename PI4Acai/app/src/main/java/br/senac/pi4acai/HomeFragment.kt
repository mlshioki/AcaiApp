package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.pi4acai.databinding.CardItemBinding
import br.senac.pi4acai.databinding.FragmentHomeBinding
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.services.ProdutoService
import com.google.android.material.snackbar.Snackbar
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
        return bind.root
    }

    override fun onResume() {
        super.onResume()

        //atualizarProdutos()
    }

    fun atualizarProdutos(){
        //Obter instância do Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProdutoService::class.java)

        val call = service.listar()

        val callback = object : Callback<List<Produto>>{
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if(response.isSuccessful){
                  val listaProdutos = response.body()
                    atualizarIU(listaProdutos)
                } else{
                    Snackbar.make(bind.container1, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(bind.container1, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)
    }

    fun atualizarIU(lista: List<Produto>?){
        bind.container1.removeAllViews()

        lista?.forEach{
            val cardBinding = CardItemBinding.inflate(layoutInflater)

            cardBinding.editAcaiTitulo.text = it.prodNome
            cardBinding.editAcaiPreco.text = it.prodPreco.toString()

            bind.container1.addView(cardBinding.root)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = CardAdapter(listaDeProdutos)
        }*/

    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }

    /*private fun popularProdutos() {
        val prod1 = Produto(
            "Açaí 2L sabores", R.drawable.acai1, "25,99","Açaí de 2 litros em sabores variados", listOf("Natural", "Morango", "Banana")
        )
        listaDeProdutos.add(prod1)

        val prod2 = Produto(
            "Açaí 3,6kg sabores", R.drawable.acai2, "7,99", "Açaí de 3,6 quilos em sabores variados", listOf("Natural", "Morango", "Banana")
        )
        listaDeProdutos.add(prod2)

        val prod3 = Produto(
            "Açaí 700ml sabores", R.drawable.acai3, "19,99", "Açaí de 700ml em vários sabores", listOf("Natural", "Morango", "Banana")
        )
        listaDeProdutos.add(prod3)
    }*/


}