package dev.jyotiraditya.echoir.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.jyotiraditya.echoir.R
import dev.jyotiraditya.echoir.domain.model.Download
import dev.jyotiraditya.echoir.domain.model.DownloadStatus
import dev.jyotiraditya.echoir.domain.model.QualityConfig
import dev.jyotiraditya.echoir.presentation.components.TrackCover
import java.util.Locale

@Composable
fun DownloadItem(
    download: Download,
    modifier: Modifier = Modifier
) {
    val quality = when (download.quality) {
        "HI_RES_LOSSLESS" -> QualityConfig.HiRes
        "LOSSLESS" -> QualityConfig.Lossless
        "DOLBY_ATMOS" -> if (download.isAc4) {
            QualityConfig.DolbyAtmosAC4
        } else {
            QualityConfig.DolbyAtmosAC3
        }
        "HIGH" -> QualityConfig.AAC320
        "LOW" -> QualityConfig.AAC96
        else -> null
    }
    ListItem(
        modifier = modifier,
        overlineContent = {
            Text(
                text = (
                    quality?.let { stringResource(it.label) }
                        ?: stringResource(R.string.unknown)
                )
                    .uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        headlineContent = {
            Text(
                text = download.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        supportingContent = {
            Text(
                text = download.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        leadingContent = {
            TrackCover(
                url = download.cover,
                size = 56.dp
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when (download.status) {
                    DownloadStatus.QUEUED -> {
                        Text(
                            text = stringResource(R.string.queued),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    DownloadStatus.DOWNLOADING -> {
                        CircularProgressIndicator(
                            progress = { download.progress / 100f },
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    }

                    DownloadStatus.MERGING -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    }

                    DownloadStatus.COMPLETED -> {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    DownloadStatus.FAILED -> {
                        Icon(
                            imageVector = Icons.Outlined.Error,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Text(
                    text = download.duration,
                    style = MaterialTheme.typography.bodySmall
                )
                if (download.explicit) {
                    Icon(
                        painter = painterResource(R.drawable.ic_explicit),
                        contentDescription = stringResource(R.string.explicit),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    )
}