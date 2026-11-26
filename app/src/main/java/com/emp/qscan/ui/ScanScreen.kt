package com.emp.qscan.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emp.qscan.R
import com.emp.qscan.ui.theme.QScanTheme

@Composable
fun ScanScreen(@StringRes error: Int?, onScan: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(40.dp))
        Text(stringResource(R.string.scan_title), style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(12.dp))
        Text(
            stringResource(R.string.scan_body),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            ViewfinderMark(MaterialTheme.colorScheme.outlineVariant)
        }
        if (error != null) {
            Row(
                Modifier
                    .padding(bottom = 16.dp)
                    .height(IntrinsicSize.Min)
                    .semantics { liveRegion = LiveRegionMode.Polite },
            ) {
                Box(Modifier.width(3.dp).fillMaxHeight().background(MaterialTheme.colorScheme.error))
                Text(
                    stringResource(error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
        }
        Button(onClick = onScan, shape = MaterialTheme.shapes.small, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
            Text(
                stringResource(if (error == null) R.string.scan_button else R.string.retry_button),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

/** Four corner brackets: the shape of a QR viewfinder without a fake camera feed. */
@Composable
private fun ViewfinderMark(color: Color) {
    Box(
        Modifier
            .fillMaxWidth(0.55f)
            .aspectRatio(1f)
            .drawBehind {
                val arm = size.minDimension * 0.18f
                val w = 3.dp.toPx()
                fun corner(x: Float, y: Float, dx: Float, dy: Float) {
                    drawLine(color, Offset(x, y), Offset(x + dx * arm, y), w, StrokeCap.Square)
                    drawLine(color, Offset(x, y), Offset(x, y + dy * arm), w, StrokeCap.Square)
                }
                corner(0f, 0f, 1f, 1f)
                corner(size.width, 0f, -1f, 1f)
                corner(0f, size.height, 1f, -1f)
                corner(size.width, size.height, -1f, -1f)
            },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ScanPreview() = QScanTheme { Surface { ScanScreen(R.string.error_invalid) {} } }
