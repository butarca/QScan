package com.emp.qscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.emp.qscan.ui.PackageScreen
import com.emp.qscan.ui.ScanScreen
import com.emp.qscan.ui.theme.QScanTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QScanTheme {
                Surface(Modifier.fillMaxSize()) { QScanApp() }
            }
        }
    }
}

@Composable
fun QScanApp() {
    val context = LocalContext.current
    // Raw payload survives rotation; parsing is cheap so it is redone on read.
    var payload by rememberSaveable { mutableStateOf<String?>(null) }
    var error by rememberSaveable { mutableStateOf<Int?>(null) }
    val info = payload?.let(::parsePackage)

    fun scan() {
        GmsBarcodeScanning.getClient(
            context,
            GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build(),
        ).startScan()
            .addOnSuccessListener { code ->
                val raw = code.rawValue
                if (raw != null && parsePackage(raw) != null) {
                    payload = raw
                    error = null
                } else {
                    error = R.string.error_invalid
                }
            }
            .addOnFailureListener { error = R.string.error_scanner }
        // Cancel (back in scanner) keeps the current screen with no message.
    }

    if (info == null) {
        ScanScreen(error, ::scan)
    } else {
        PackageScreen(
            info,
            onBack = { payload = null },
            onDone = { payload = null; scan() },
        )
    }
}
