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
    var permissaoConcecida = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verificarPermissaoCamera()

    }
    fun incializarLeitorQrCode(){
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
                mostrarToast(this, "nao foi possivel abrir a camera")
                Log.e("QrCodeActivity","inicializarLeitorQrCode", it)
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        leitorQr.startPreview()

    }

    fun verificarPermissaoCamera(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }else{
            permissaoConcecida = true
            incializarLeitorQrCode()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissaoConcecida = true
                incializarLeitorQrCode()
            }else{
                permissaoConcecida = false
                mostrarToast(this,"Sem permissao de uso de camera")
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
}