package com.classroom2.app.presentation.attendance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Lock
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
import com.classroom2.app.presentation.components.AnimatedCounter
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SecondaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomGreen
import com.classroom2.app.ui.theme.ClassroomGreenSoft
import com.classroom2.app.ui.theme.ClassroomOrange
import com.classroom2.app.ui.theme.ClassroomOrangeSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing
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
        ClassroomTopBar(title = "QR attendance", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            val payload = vm.currentQrPayload()

            // Premium QR card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = ClassroomShapes.Qr,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(ClassroomSpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Computer Science 101",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Tap any student's phone to check in",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (session != null) {
                            StatusChip(
                                label = "Active • ${TimeUtil.formatCountdown((session.expiresAt - now).coerceAtLeast(0))}",
                                accent = ClassroomGreen,
                                softBackground = ClassroomGreenSoft
                            )
                        } else {
                            StatusChip(
                                label = "Idle",
                                accent = ClassroomOrange,
                                softBackground = ClassroomOrangeSoft
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.White, ClassroomShapes.Qr)
                            .padding(ClassroomSpacing.md),
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Session-specific QR expires automatically.",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Present count + Expected
            Row(horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = ClassroomShapes.Card,
                    color = ClassroomGreenSoft
                ) {
                    Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            "PRESENT",
                            style = MaterialTheme.typography.labelSmall,
                            color = ClassroomGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        AnimatedCounter(
                            value = state.records.size,
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                            color = ClassroomGreen
                        )
                    }
                }
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = ClassroomShapes.Card,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(ClassroomSpacing.md), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            "EXPECTED",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            vm.expectedClassSize.toString(),
                            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
            ) {
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
                EmptyStateCard(
                    title = "No students yet",
                    message = "Keep the QR visible and check-ins will appear live.",
                    emoji = "📭"
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)) {
                    state.records.forEachIndexed { index, record ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(tween(220, delayMillis = index * 40)) +
                                slideInVertically(tween(220, delayMillis = index * 40)) { it / 6 }
                        ) {
                            AttendanceRow(record)
                        }
                    }
                }
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}

@Composable
private fun AttendanceRow(record: AttendanceRecord) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = ClassroomShapes.Card,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = ClassroomSpacing.md, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm + 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(ClassroomGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    record.studentName.ifBlank { "Student" },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Checked in at ${TimeUtil.formatTime(record.timestamp)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusChip(
                label = "+10 pts",
                accent = ClassroomGreen,
                softBackground = ClassroomGreenSoft,
                showDot = false
            )
        }
    }
}
