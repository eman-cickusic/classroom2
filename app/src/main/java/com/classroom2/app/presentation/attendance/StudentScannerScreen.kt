package com.classroom2.app.presentation.attendance

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import com.classroom2.app.ui.icons.ClassroomIcons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing
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
    val activeSession by vm.activeSession.collectAsState()
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (activeSession != null) {
                    StatusChip(
                        label = "Session live",
                        accent = ClassroomGreen,
                        softBackground = ClassroomGreenSoft
                    )
                } else {
                    StatusChip(label = "Waiting for professor")
                }
            }

            Text(
                "Point your camera at the professor's QR, or use Demo Scan below.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(ClassroomShapes.LargeCard)
                    .background(Color.Black)
            ) {
                if (cameraPermission.status.isGranted) {
                    CameraPreview(onPayload = { vm.handleScannedPayload(it) })
                    // viewfinder frame overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ClassroomSpacing.xl)
                            .clip(ClassroomShapes.Card)
                            .background(Color.Transparent)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ClassroomSpacing.lg),
                        verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = ClassroomIcons.cameraOff,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Text(
                            "Camera off",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Use Demo scan below. Same flow, no camera required.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            PrimaryActionButton(
                text = "Demo scan (works without camera)",
                icon = ClassroomIcons.scanner,
                onClick = { vm.demoScan() }
            )

            SecondaryActionButton(
                text = "Enable camera",
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
                minLines = 2,
                shape = ClassroomShapes.Card
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
                    shape = ClassroomShapes.Card,
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Text(
                        msg,
                        modifier = Modifier.padding(ClassroomSpacing.md),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(Modifier.height(ClassroomSpacing.md))
        }
    }
}

@Composable
private fun CameraPreview(onPayload: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scanner = remember { BarcodeScanning.getClient() }
    var alreadyHandled by remember { mutableStateOf(false) }
    val executor = remember { Executors.newSingleThreadExecutor() }

    val controller = remember(lifecycleOwner) {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            setImageAnalysisAnalyzer(executor) { proxy ->
                val media = proxy.image
                if (media == null) {
                    proxy.close()
                    return@setImageAnalysisAnalyzer
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
                bindToLifecycle(lifecycleOwner)
            } catch (_: Throwable) { /* camera unavailable — fallback to Demo Scan */ }
        }
    }

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).also { it.controller = controller }
        },
        modifier = Modifier.fillMaxSize()
    )
}
