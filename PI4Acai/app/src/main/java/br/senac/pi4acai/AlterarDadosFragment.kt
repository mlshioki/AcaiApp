package br.senac.pi4acai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AlterarDadosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alterar_dados, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AlterarDadosFragment()
    }
}