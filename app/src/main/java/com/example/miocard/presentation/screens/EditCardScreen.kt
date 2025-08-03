package com.example.miocard.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.miocard.R
import com.example.miocard.presentation.viewmodel.EditCardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Navigate back when card is updated
    LaunchedEffect(uiState.isCardUpdated) {
        if (uiState.isCardUpdated) {
            onNavigateBack()
        }
    }

    // Show error snackbar
    uiState.error?.let { error ->
        LaunchedEffect(error) {
            // In a real app, you'd show a SnackBar here
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(stringResource(R.string.edit_card_title)) 
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        
        if (uiState.isLoading && uiState.originalCard == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                
                // Card ID field (editable)
                OutlinedTextField(
                    value = uiState.id,
                    onValueChange = viewModel::updateId,
                    label = { Text(stringResource(R.string.card_id_label)) },
                    placeholder = { Text(stringResource(R.string.card_id_placeholder)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.idError != null,
                    supportingText = uiState.idError?.let { { Text(it) } }
                )
                
                // Prefix field (optional)
                OutlinedTextField(
                    value = uiState.prefix,
                    onValueChange = viewModel::updatePrefix,
                    label = { Text(stringResource(R.string.card_prefix_label)) },
                    placeholder = { Text(stringResource(R.string.card_prefix_placeholder)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Suffix field (optional)
                OutlinedTextField(
                    value = uiState.suffix,
                    onValueChange = viewModel::updateSuffix,
                    label = { Text(stringResource(R.string.card_suffix_label)) },
                    placeholder = { Text(stringResource(R.string.card_suffix_placeholder)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Name field (required)
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::updateName,
                    label = { Text(stringResource(R.string.card_name_label)) },
                    placeholder = { Text(stringResource(R.string.card_name_placeholder)) },
                    isError = uiState.nameError != null,
                    supportingText = uiState.nameError?.let { { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Position field
                OutlinedTextField(
                    value = uiState.position.toString(),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { viewModel.updatePosition(it) }
                    },
                    label = { Text(stringResource(R.string.card_position_label)) },
                    placeholder = { Text(stringResource(R.string.card_position_placeholder)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = uiState.positionError != null,
                    supportingText = uiState.positionError?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Save button
                Button(
                    onClick = viewModel::saveCard,
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        if (uiState.isLoading) stringResource(R.string.saving_card)
                        else stringResource(R.string.save_changes)
                    )
                }
                
                // Error message
                uiState.error?.let { error ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}