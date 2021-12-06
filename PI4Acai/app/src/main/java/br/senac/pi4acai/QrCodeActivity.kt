package br.senac.pi4acai

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.senac.pi4acai.databinding.ActivityQrCodeBinding
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat


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
                finish()
            }
        }
        leitorQr.setErrorCallback {
            runOnUiThread {
                mostrarToast(this, "nao foi possivel abrir a câmera")
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
            }else{
                permissaoConcedida = false
                mostrarToast(this,"É necessário permitir o uso da câmera para utilizar a função de QRCode.")
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
}