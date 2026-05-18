package com.classroom2.app.presentation.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.classroom2.app.domain.model.ExplanationMode
import com.classroom2.app.presentation.components.ClassroomTopBar
import com.classroom2.app.presentation.components.PrimaryActionButton
import com.classroom2.app.presentation.components.SectionHeader
import com.classroom2.app.util.AIExplainer

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun AIExplainerScreen(onBack: () -> Unit) {
    var concept by remember { mutableStateOf("polymorphism") }
    var mode by remember { mutableStateOf(ExplanationMode.LIKE_I_AM_12) }
    var result by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        ClassroomTopBar(title = "AI concept explainer", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Get a clear, classroom-friendly explanation of any concept.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = concept,
                onValueChange = { concept = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Concept") },
                placeholder = { Text("e.g. polymorphism") },
                singleLine = true
            )

            SectionHeader(title = "Explanation style")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                text = "Explain",
                onClick = { result = AIExplainer.explain(concept, mode) },
                enabled = concept.isNotBlank()
            )

            if (result != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "🤖 ${mode.label}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            result!!,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(Modifier.size(16.dp))
        }
    }
}
