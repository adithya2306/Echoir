package dev.jyotiraditya.echoir.presentation.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import dev.jyotiraditya.echoir.domain.model.SearchResult
import dev.jyotiraditya.echoir.presentation.components.TrackCover
import java.util.Locale

@Composable
fun SearchResultItem(
    result: SearchResult,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        overlineContent = {
            result.formats?.let { formats ->
                Text(
                    text = formats.mapNotNullTo(mutableSetOf()) {
                        when (it) {
                            "HIRES_LOSSLESS" -> stringResource(R.string.hires_short)
                            "LOSSLESS" -> stringResource(R.string.lossless_short)
                            "DOLBY_ATMOS" -> stringResource(R.string.dolby)
                            "HIGH", "LOW" -> stringResource(R.string.aac)
                            else -> null
                        }
                            ?.uppercase(Locale.getDefault())
                    }.apply {
                        if (formats.any { it == "HIRES_LOSSLESS" || it == "LOSSLESS" }) {
                            add(stringResource(R.string.aac))
                        }
                    }.joinToString(" / "),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        headlineContent = {
            Text(
                text = result.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        supportingContent = {
            Text(
                text = result.artists.joinToString(", "),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        leadingContent = {
            TrackCover(
                url = result.cover,
                size = 56.dp
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = result.duration,
                    style = MaterialTheme.typography.bodySmall
                )
                if (result.explicit) {
                    Icon(
                        painter = painterResource(R.drawable.ic_explicit),
                        contentDescription = stringResource(R.string.explicit),
                        modifier = Modifier.size(16.dp),
                    )
                }
                result.formats?.let { formats ->
                    if (formats.contains("DOLBY_ATMOS")) {
                        Icon(
                            painter = painterResource(R.drawable.ic_dolby),
                            contentDescription = stringResource(R.string.dolby_atmos),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    )
}