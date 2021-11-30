package br.senac.pi4acai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.senac.pi4acai.databinding.CardProdutosItemBinding
import br.senac.pi4acai.databinding.FragmentTelaProdutosBinding
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.services.ProdutoService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaProdutosFragment : Fragment() {
    lateinit var bind: FragmentTelaProdutosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        bind = FragmentTelaProdutosBinding.inflate(inflater)

        atualizarProdutos()
        return bind.root

    }

    fun atualizarProdutos(){
        val retrofit = Retrofit.Builder()
            //porta do simulador: 10.0.2.2
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProdutoService::class.java)

        val call = service.listar()

        val callback = object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if(response.isSuccessful){
                    val listaProdutos = response.body()
                    atualizarIU(listaProdutos)
                    Toast.makeText(getActivity(), "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                    //Toast.makeText(this@TelaProdutosFragment,"Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();
                } else{
                    Snackbar.make(bind.containerBN, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(bind.containerBN, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)
    }

    fun atualizarIU(lista: List<Produto>?){
        bind.containerBN.removeAllViews()

        lista?.forEach{
            val cardBinding = CardProdutosItemBinding.inflate(layoutInflater, bind.containerBN, false)
            cardBinding.editProdNome.text = it.name
            cardBinding.editProdPreco.text = it.price.toString()

            //Picasso.get().load("https://imgur.com/a/DKDrm1A/${it.id}").into(cardBinding.acaiImg)

            bind.containerBN.addView(cardBinding.root)


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TelaProdutosFragment()
    }
}