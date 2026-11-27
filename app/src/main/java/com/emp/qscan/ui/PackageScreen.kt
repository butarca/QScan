package com.emp.qscan.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emp.qscan.PackageInfo
import com.emp.qscan.R
import com.emp.qscan.Task
import com.emp.qscan.ui.theme.CodeStyle
import com.emp.qscan.ui.theme.QScanTheme

@Composable
fun PackageScreen(info: PackageInfo, onBack: () -> Unit, onDone: () -> Unit) {
    BackHandler(onBack = onBack)
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp),
    ) {
        TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 48.dp)) {
            Text("← " + stringResource(R.string.back), style = MaterialTheme.typography.titleSmall)
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(16.dp))
            Label(R.string.package_eyebrow)
            Text(info.id, style = CodeStyle.copy(fontSize = MaterialTheme.typography.headlineMedium.fontSize))
            Divider()
            Label(R.string.package_task)
            Text(
                stringResource(info.task.label),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(4.dp))
            Text(stringResource(info.task.hint), style = MaterialTheme.typography.bodyLarge)
            Divider()
            info.location?.let { Field(R.string.package_location, it, CodeStyle) }
            info.date?.let { Field(R.string.package_date, formatDate(it)) }
            info.note?.let { Field(R.string.package_note, it) }
        }
        Button(onClick = onDone, shape = MaterialTheme.shapes.small, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).heightIn(min = 56.dp)) {
            Text(stringResource(R.string.package_done), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun Label(res: Int) = Text(
    stringResource(res).uppercase(),
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = Modifier.padding(bottom = 6.dp),
)

@Composable
private fun Divider() = HorizontalDivider(Modifier.padding(vertical = 24.dp), color = MaterialTheme.colorScheme.outlineVariant)

@Composable
private fun Field(label: Int, value: String, style: TextStyle = MaterialTheme.typography.titleLarge) {
    Row(Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
        Column(Modifier.width(96.dp)) { Label(label) }
        Text(value, style = style, modifier = Modifier.weight(1f))
    }
}

/** 2026-11-26 → 26. 11. 2026 (Slovenian convention). Input is already ISO-validated by the parser. */
private fun formatDate(iso: String): String = iso.split("-").let { (y, m, d) -> "${d.toInt()}. ${m.toInt()}. $y" }

@Preview(showSystemUi = true)
@Composable
private fun PackagePreview() = QScanTheme {
    Surface { PackageScreen(PackageInfo("PKG-000123", "2026-11-26", Task.MOVE, "A-03-2", "Krhko, ne zlagaj."), {}, {}) }
}
