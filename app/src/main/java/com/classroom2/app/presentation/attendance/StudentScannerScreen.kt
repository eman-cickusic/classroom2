package com.classroom2.app.presentation.attendance

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StudentScannerScreen(
    onBack: () -> Unit,
    onScanned: () -> Unit,
    vm: AttendanceViewModel = viewModel()
) {
    val outcome by vm.scanOutcome.collectAsState()
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    var manualPayload by remember { mutableStateOf("") }

    LaunchedEffect(outcome) {
        if (outcome?.isSuccess == true) onScanned()
    }

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) cameraPermission.launchPermissionRequest()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "Scan attendance QR", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Point your camera at the professor's QR code, or use Demo Scan below.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color.Black)
            ) {
                if (cameraPermission.status.isGranted) {
                    CameraPreview(onPayload = { vm.handleScannedPayload(it) })
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📷", style = MaterialTheme.typography.displayLarge, color = Color.White)
                        Text(
                            "Camera permission needed",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Allow camera, or use Demo Scan below.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            PrimaryActionButton(text = "🎯 Demo Scan", onClick = { vm.demoScan() })

            SecondaryActionButton(
                text = "Open camera permission",
                onClick = { cameraPermission.launchPermissionRequest() },
                enabled = !cameraPermission.status.isGranted
            )

            Text("Or paste payload", style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = manualPayload,
                onValueChange = { manualPayload = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("QR JSON payload") },
                singleLine = false,
                minLines = 2
            )
            SecondaryActionButton(
                text = "Submit pasted payload",
                onClick = {
                    val p = manualPayload.trim()
                    if (p.isNotEmpty()) vm.handleScannedPayload(p)
                },
                enabled = manualPayload.isNotBlank()
            )

            outcome?.error?.let { msg ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        msg,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CameraPreview(onPayload: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scanner = remember { BarcodeScanning.getClient() }
    var alreadyHandled by remember { mutableStateOf(false) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val analyzer = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                val executor = Executors.newSingleThreadExecutor()
                analyzer.setAnalyzer(executor) { proxy ->
                    val media = proxy.image
                    if (media == null) {
                        proxy.close()
                        return@setAnalyzer
                    }
                    val image = InputImage.fromMediaImage(media, proxy.imageInfo.rotationDegrees)
                    scanner.process(image)
                        .addOnSuccessListener { codes ->
                            val raw = codes
                                .firstOrNull { it.format == Barcode.FORMAT_QR_CODE }
                                ?.rawValue
                            if (raw != null && !alreadyHandled) {
                                alreadyHandled = true
                                onPayload(raw)
                            }
                        }
                        .addOnCompleteListener { proxy.close() }
                }
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analyzer
                    )
                } catch (_: Throwable) { /* camera unavailable — fallback to Demo Scan */ }
            }, ContextCompat.getMainExecutor(context))
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}
