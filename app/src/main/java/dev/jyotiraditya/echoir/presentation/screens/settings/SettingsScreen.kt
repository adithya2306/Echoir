package dev.jyotiraditya.echoir.presentation.screens.settings

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jyotiraditya.echoir.BuildConfig
import dev.jyotiraditya.echoir.R
import dev.jyotiraditya.echoir.data.utils.extensions.toDisplayPath
import dev.jyotiraditya.echoir.domain.model.Region
import dev.jyotiraditya.echoir.presentation.components.preferences.PreferenceCategory
import dev.jyotiraditya.echoir.presentation.components.preferences.PreferenceItem
import dev.jyotiraditya.echoir.presentation.components.preferences.PreferencePosition
import dev.jyotiraditya.echoir.presentation.screens.settings.components.CrucialSettingsDialog
import dev.jyotiraditya.echoir.presentation.screens.settings.components.FileNamingFormatDialog
import dev.jyotiraditya.echoir.presentation.screens.settings.components.RegionDialog

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var showFormatDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showClearDataDialog by remember { mutableStateOf(false) }
    var showRegionDialog by remember { mutableStateOf(false) }

    val dirPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            viewModel.updateOutputDirectory(it.toString())
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            dirPicker.launch(null)
        }
    }

    if (showFormatDialog) {
        FileNamingFormatDialog(
            selectedFormat = state.fileNamingFormat,
            onSelectFormat = { format ->
                viewModel.updateFileNamingFormat(format)
                showFormatDialog = false
            },
            onDismiss = { showFormatDialog = false }
        )
    }

    if (showResetDialog) {
        CrucialSettingsDialog(
            onDismiss = { showResetDialog = false },
            icon = Icons.Outlined.RestartAlt,
            title = stringResource(R.string.reset_settings),
            description = stringResource(R.string.reset_settings_desc),
            confirmText = stringResource(R.string.reset),
            onConfirm = {
                viewModel.resetSettings()
                showResetDialog = false
            }
        )
    }

    if (showClearDataDialog) {
        CrucialSettingsDialog(
            onDismiss = { showClearDataDialog = false },
            icon = Icons.Outlined.Delete,
            title = stringResource(R.string.clear_data),
            description = stringResource(R.string.clear_data_desc),
            confirmText = stringResource(R.string.clear),
            onConfirm = {
                viewModel.clearData()
                showClearDataDialog = false
            }
        )
    }

    if (showRegionDialog) {
        RegionDialog(
            selectedRegion = state.region,
            onSelectRegion = { region ->
                viewModel.updateRegion(region)
                showRegionDialog = false
            },
            onDismiss = { showRegionDialog = false }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            PreferenceCategory(title = stringResource(R.string.content))
        }

        item {
            val regionName = Region.fromCode(state.region).getDisplayName(context)
            PreferenceItem(
                title = stringResource(R.string.region),
                subtitle = "$regionName [${state.region}]",
                icon = Icons.Outlined.Public,
                onClick = { showRegionDialog = true },
                position = PreferencePosition.Single
            )
        }

        item {
            PreferenceCategory(title = stringResource(R.string.storage))
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.download_location),
                subtitle = state.outputDirectory.toDisplayPath(context),
                icon = Icons.Outlined.Folder,
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        dirPicker.launch(null)
                    } else {
                        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                },
                position = PreferencePosition.Top
            )
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.file_naming_format),
                subtitle = state.fileNamingFormat.displayName,
                icon = Icons.Outlined.TextFormat,
                onClick = { showFormatDialog = true },
                position = PreferencePosition.Bottom
            )
        }

        item {
            PreferenceCategory(title = stringResource(R.string.data))
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.clear_data),
                subtitle = stringResource(R.string.clear_data_subtitle),
                icon = Icons.Outlined.Delete,
                onClick = { showClearDataDialog = true },
                position = PreferencePosition.Top
            )
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.reset_settings),
                subtitle = stringResource(R.string.reset_settings_subtitle),
                icon = Icons.Outlined.RestartAlt,
                onClick = { showResetDialog = true },
                position = PreferencePosition.Bottom
            )
        }

        item {
            PreferenceCategory(title = stringResource(R.string.about))
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.app_name),
                subtitle = stringResource(R.string.about_version, BuildConfig.VERSION_NAME),
                icon = Icons.Outlined.Info,
                position = PreferencePosition.Top,
            )
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.panda_title),
                subtitle = stringResource(R.string.panda_subtitle),
                icon = painterResource(R.drawable.ic_panda),
                position = PreferencePosition.Middle
            )
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.tg_channel),
                subtitle = stringResource(R.string.tg_channel_at),
                icon = Icons.Outlined.Public,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.tg_channel_url))
                    )
                    context.startActivity(intent)
                },
                position = PreferencePosition.Middle
            )
        }

        item {
            PreferenceItem(
                title = stringResource(R.string.donate_title),
                subtitle = stringResource(R.string.donate_subtitle),
                icon = Icons.Outlined.LocalCafe,
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(context.getString(R.string.donate_url))
                    )
                    context.startActivity(intent)
                },
                position = PreferencePosition.Bottom
            )
        }
    }
}