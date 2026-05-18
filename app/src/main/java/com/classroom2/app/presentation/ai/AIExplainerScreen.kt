package com.classroom2.app.presentation.ai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.domain.model.ExplanationMode
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.EmptyStateCard
import com.classroom2.app.presentation.components.LoadingCard
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.presentation.components.StatusChip
import com.classroom2.app.ui.theme.ClassroomPurple
import com.classroom2.app.ui.theme.ClassroomPurpleSoft
import com.classroom2.app.ui.theme.ClassroomShapes
import com.classroom2.app.ui.theme.ClassroomSpacing
import com.classroom2.app.util.AIExplainer
import kotlinx.coroutines.delay

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun AIExplainerScreen(onBack: () -> Unit) {
    var concept by remember { mutableStateOf("polymorphism") }
    var mode by remember { mutableStateOf(ExplanationMode.LIKE_I_AM_12) }
    var result by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(loading) {
        if (loading) {
            delay(550) // gentle simulated thinking time so the shimmer is visible
            result = AIExplainer.explain(concept, mode)
            loading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "AI concept explainer", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ClassroomSpacing.lg, vertical = ClassroomSpacing.sm),
            verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.md)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(ClassroomPurpleSoft, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = ClassroomPurple, modifier = Modifier.size(20.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Ask Classroom AI",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Clear, classroom-friendly explanations of any concept.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                StatusChip(
                    label = "Offline-ready",
                    accent = ClassroomPurple,
                    softBackground = ClassroomPurpleSoft,
                    showDot = false
                )
            }

            OutlinedTextField(
                value = concept,
                onValueChange = { concept = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Concept") },
                placeholder = { Text("e.g. polymorphism") },
                singleLine = true,
                shape = ClassroomShapes.Card
            )

            SectionHeader(title = "Explanation style")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ExplanationMode.entries.forEach { m ->
                    FilterChip(
                        selected = mode == m,
                        onClick = { mode = m },
                        label = { Text(m.label) },
                        colors = FilterChipDefaults.filterChipColors()
                    )
                }
            }

            PrimaryActionButton(
                text = if (loading) "Thinking…" else "Explain",
                onClick = {
                    if (concept.isNotBlank()) {
                        result = null
                        loading = true
                    }
                },
                enabled = concept.isNotBlank() && !loading
            )

            when {
                loading -> LoadingCard(label = "Generating explanation…")
                result == null -> EmptyStateCard(
                    title = "Pick a concept and a style",
                    message = "Type any term above, choose a mode, then tap Explain to see a friendly walkthrough.",
                    emoji = "💡"
                )
                else -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(280)) + slideInVertically(tween(280)) { it / 8 }
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = ClassroomShapes.LargeCard,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Column(
                                modifier = Modifier.padding(ClassroomSpacing.md),
                                verticalArrangement = Arrangement.spacedBy(ClassroomSpacing.sm)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(ClassroomPurple, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                        }
                                        Text(
                                            mode.label,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                    IconButton(onClick = {
                                        result?.let { text ->
                                            val cb = context.getSystemService(android.content.ClipboardManager::class.java)
                                            cb?.setPrimaryClip(android.content.ClipData.newPlainText("Classroom AI", text))
                                        }
                                    }) {
                                        Icon(
                                            Icons.Filled.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                }
                                Text(
                                    result!!,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.size(ClassroomSpacing.md))
        }
    }
}
