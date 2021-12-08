package br.senac.pi4acai

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import br.senac.pi4acai.models.Produto
import retrofit2.Callback

fun mostrarToast(context: Context, msg: String){
    Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
}
fun mostrarDialogo (context : Context, msg: String){
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setPositiveButton("OK", null)
        .create()
        .show()
}