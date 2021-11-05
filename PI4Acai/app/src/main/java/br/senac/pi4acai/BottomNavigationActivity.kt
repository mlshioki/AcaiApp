package br.senac.pi4acai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.senac.pi4acai.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.collapsingToolbar.title = ""
        binding.toolbar.title = ""

        setSupportActionBar(binding.toolbar)

        var frag: Fragment = HomeFragment.newInstance()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.perfil ->{
                    binding.appbar.visibility = View.GONE
                    frag = AlterarDadosFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.containerProdutos, frag).commit()
                }
                R.id.home ->{
                    binding.appbar.visibility = View.VISIBLE
                    frag = TelaProdutosFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.containerProdutos, frag).commit()
                }
            }

            true
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }*/

}