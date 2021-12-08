package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.senac.pi4acai.databinding.CardCarrinhoBinding
import br.senac.pi4acai.databinding.FragmentCarrinhoBinding
import br.senac.pi4acai.models.*
import br.senac.pi4acai.services.API
import br.senac.pi4acai.services.CarrinhoService
import br.senac.pi4acai.services.PedidosService
import br.senac.pi4acai.services.ProdutoService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
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
        val callback = object : Callback<RespostaProduto>{
            override fun onResponse(call: Call<RespostaProduto>, response: Response<RespostaProduto>) {
                if (response.isSuccessful){
                    val carrinho = response.body()
                    atualizarUI(carrinho)
                }else{
                    //val error = response.errorBody().toString()
                    Snackbar.make(binding.containerCarrinho, "Não deu certo", Snackbar.LENGTH_LONG).show()
                    Log.e("ERROR", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<RespostaProduto>, t: Throwable) {
                Snackbar.make(binding.containerCarrinho, "Deu ruim ", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR","Falha ao executar o serviço", t)
            }
        }

        API(requireContext()).carrinho.listarCarrinho().enqueue(callback)

    }

    override fun onResume() {
        super.onResume()

        atualizarProdutosCarrinho()
    }

    fun atualizarUI(carrinho: RespostaProduto?){

        binding.containerCarrinho.removeAllViews()
        carrinho?.data?.carrinho?.forEach {

            val cardCarrinhoBinding = CardCarrinhoBinding.inflate(layoutInflater)
            cardCarrinhoBinding.textTitulo.text = it.name
            cardCarrinhoBinding.textPrice.text = "R$" + it.price
            cardCarrinhoBinding.textQuantidadeProduto.text ="Qtd: " + it.cart_qtd.toString()
            binding.containerCarrinho.addView(cardCarrinhoBinding.root)
            //Picasso.get().load("https://i.imgur.com/wcNZsqi.jpg").into(cardCarrinhoBinding.imagemCarrinho)

            cardCarrinhoBinding.imageLixeira.setOnClickListener { view ->
                excluirProduto(it.id)

            }

            binding.btnFinalizarPedido.setOnClickListener {
                finalizarPedidos()
                atualizarProdutosCarrinho()
            }
        }
    }

    fun finalizarPedidos(){
        val callback = object : Callback<RespostaPedido>{
            override fun onResponse(call: Call<RespostaPedido>,response: Response<RespostaPedido>) {
                if (response.isSuccessful){
                    Toast.makeText(context,"Pedido finalizado, vá ao historico de compras para mais informações", Toast.LENGTH_LONG).show()
                }else{
                    Snackbar.make(binding.containerCarrinho, "Não deu certo", Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }

                }
            }
            override fun onFailure(call: Call<RespostaPedido>, t: Throwable) {
                Snackbar.make(binding.containerCarrinho, "Deu ruim ", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR","Falha ao executar o serviço", t)
            }
        }


        API(requireContext()).pedidos.finalizarPedido(Pedidos(endereco = "Rua a", forma_pagamento = "PIX", observacoes = "teste")).enqueue(callback)
    }

    fun excluirProduto(id: Int){
        val callback = object : Callback<RespostaExclusao>{
            override fun onResponse(call: Call<RespostaExclusao>,response: Response<RespostaExclusao>) {
                if (response.isSuccessful){
                    atualizarProdutosCarrinho()
                    Toast.makeText(context, "Produto deletado com sucesso", Toast.LENGTH_LONG).show()
                }else{
                    Snackbar.make(binding.containerCarrinho, "Não deu certo", Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }

                }
            }
            override fun onFailure(call: Call<RespostaExclusao>, t: Throwable) {
                Snackbar.make(binding.containerCarrinho, "Deu ruim ", Snackbar.LENGTH_LONG).show()
                Log.e("ERROR","Falha ao executar o serviço", t)
            }
        }

        API(requireContext()).carrinho.excluirProdutoCarrinho(id).enqueue(callback)
    }
    companion object {
        @JvmStatic
        fun newInstance() = CarrinhoFragment()
    }
}

