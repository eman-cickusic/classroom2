package com.classroom2.app.presentation.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classroom2.app.domain.model.AttendanceRecord
import com.classroom2.app.presentation.components.ClassroomCard
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyState
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.util.QRCodeUtil
import com.classroom2.app.util.TimeUtil
import kotlinx.coroutines.delay

@Composable
fun ProfessorAttendanceScreen(
    onBack: () -> Unit,
    onEnded: () -> Unit,
    vm: AttendanceViewModel = viewModel()
) {
    val state by vm.professorUiState.collectAsState()
    val context = LocalContext.current
    val session = state.activeSession

    LaunchedEffect(Unit) {
        if (vm.activeSession.value == null) vm.startSession()
    }

    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(session?.id) {
        while (session != null) {
            now = System.currentTimeMillis()
            delay(1_000)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "QR Attendance", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val payload = vm.currentQrPayload()

            ClassroomCard(
                title = "Computer Science 101",
                subtitle = if (session != null)
                    "Session live • ${TimeUtil.formatCountdown((session.expiresAt - now).coerceAtLeast(0))} remaining"
                else "No active session"
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (payload != null) {
                        val bitmap = remember(payload) { QRCodeUtil.generateImageBitmap(payload, 1024) }
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Attendance QR",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            "Generating QR…",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatPill(
                        label = "Present",
                        value = state.records.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatPill(
                        label = "Expected",
                        value = vm.expectedClassSize.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (session == null) {
                    PrimaryActionButton(
                        text = "Start session",
                        onClick = { vm.startSession() },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    PrimaryActionButton(
                        text = "End session",
                        onClick = {
                            vm.endSession()
                            onEnded()
                        },
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.error
                    )
                    SecondaryActionButton(
                        text = "Copy payload",
                        onClick = {
                            payload?.let {
                                val cb = context.getSystemService(android.content.ClipboardManager::class.java)
                                cb?.setPrimaryClip(android.content.ClipData.newPlainText("Classroom QR", it))
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            SectionHeader(title = "Live check-ins")

            if (state.records.isEmpty()) {
                EmptyState(
                    title = "No students yet",
                    message = "Share the QR code and students will appear here in real time.",
                    emoji = "📭"
                )
            } else {
                LiveAttendanceList(state.records)
            }

            Spacer(Modifier.size(16.dp))
        }
    }
}

@Composable
private fun StatPill(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = ClassroomGreenSoft
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = ClassroomGreen,
                fontWeight = FontWeight.SemiBold
            )
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun LiveAttendanceList(records: List<AttendanceRecord>) {
    // Use Column instead of LazyColumn inside a verticalScroll parent.
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        records.forEach { r -> AttendanceRow(r) }
    }
}

@Composable
private fun AttendanceRow(record: AttendanceRecord) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ClassroomGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(record.studentName.ifBlank { "Student" }, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(
                    "Checked in at ${TimeUtil.formatTime(record.timestamp)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text("✅", style = MaterialTheme.typography.titleMedium)
        }
    }
}
