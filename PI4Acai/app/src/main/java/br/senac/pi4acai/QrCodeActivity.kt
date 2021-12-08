package br.senac.pi4acai

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.senac.pi4acai.databinding.ActivityQrCodeBinding
import br.senac.pi4acai.models.Produto
import br.senac.pi4acai.services.API
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QrCodeActivity : AppCompatActivity() {
    lateinit var binding: ActivityQrCodeBinding
    lateinit var leitorQr: CodeScanner
    var permissaoConcedida = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verificarPermissao()

    }
    fun initLeitorQrCode(){
        leitorQr = CodeScanner(this, binding.scannerView)
        leitorQr.camera = CodeScanner.CAMERA_BACK
        leitorQr.formats = listOf(BarcodeFormat.QR_CODE)
        leitorQr.isAutoFocusEnabled = true
        leitorQr.autoFocusMode = AutoFocusMode.SAFE
        leitorQr.scanMode = ScanMode.SINGLE

        leitorQr.setDecodeCallback {
            runOnUiThread {
                val respIntent = Intent()
                respIntent.putExtra("qrcode", it.text)
                setResult(RESULT_OK, respIntent)
                obterProdutoQrCode(respIntent)
                finish()
            }
        }
        leitorQr.setErrorCallback {
            runOnUiThread {
                mostrarToast(this@QrCodeActivity, "nao foi possivel abrir a câmera")
                Log.e("QrCodeActivity","inicializarLeitorQrCode", it)
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        leitorQr.startPreview()

    }

    fun verificarPermissao(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }else{
            permissaoConcedida = true
            initLeitorQrCode()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissaoConcedida = true
                initLeitorQrCode()
            }
            else if (!shouldShowRequestPermissionRationale(permissions[0])){
                mostrarDialogoPermissaoCamera()
            }
            else{
                permissaoConcedida = false
                mostrarToast(this,"É necessário permitir o uso da câmera para utilizar a função de QRCode.")
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            val idProduto = data.getStringExtra("qrcode") as String
//            var a = idProduto
//    }

    fun obterProdutoQrCode(data: Intent){
        val idProduto = data.getStringExtra("qrcode") as String
        val callback = object : Callback<List<Produto>>{
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                val prod = response.body()
                if (response.isSuccessful && prod != null){
                    mostrarToast(this@QrCodeActivity,
                        """ Produto Escaneado :
                            |
                            |Nome : ${prod[0].name}
                            |Preço : ${prod[0].price}
                        """.trimMargin())
                }
            }
            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                //mostrarToast(context, "falha ao obter o produto" )
                Toast.makeText(this@QrCodeActivity, "falha ao obter o produto", Toast.LENGTH_LONG).show()
                Log.e("QrCodeActivity", "ObterProdutoQrCode", t)
            }
        }
        API(this).produto.obter(idProduto.toInt()).enqueue(callback)


    }





/*
    override fun onResume() {
        if (permissaoConcedida){
            leitorQr.startPreview()
        }
    }

    override fun onPause() {
        if(permissaoConcedida){
            leitorQr.releaseResources()
        }
    }*/

    private fun mostrarDialogoPermissaoCamera(){
        AlertDialog.Builder(this)
            .setTitle("Permissão da camera")
            .setMessage("Habilite a permissão do uso da camêra em configurações para ler o QR CODE")
            .setCancelable(false)
            .setPositiveButton("Ir para Configurações"){ dialogInterface, i ->
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.data = Uri.fromParts("package", packageName, null)
                startActivity(i)
                setResult(RESULT_CANCELED)
                finish()
            }
            .setNegativeButton("Cancelar"){dialogInterface, i ->
                setResult(RESULT_CANCELED)
                finish()
            }
            .create()
            .show()
    }
}