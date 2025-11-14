package com.example.archive.ui.outfits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.example.archive.data.local.entities.OutfitWithItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutfitsScreen(
    viewModel: OutfitsViewModel = koinViewModel()
) {
    val outfits by viewModel.outfits.collectAsState()
    val availableItems by viewModel.availableItems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Outfits") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Crear outfit")
            }
        }
    ) { padding ->
        if (outfits.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay outfits. Crea uno con el botón +")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(outfits) { outfitWithItems ->
                    OutfitCard(
                        outfitWithItems = outfitWithItems,
                        onDelete = { viewModel.deleteOutfit(outfitWithItems.outfit) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        CreateOutfitDialog(
            availableItems = availableItems,
            onDismiss = { showDialog = false },
            onConfirm = { name, selectedIds ->
                viewModel.createOutfit(name, selectedIds)
                showDialog = false
            }
        )
    }
}

@Composable
fun OutfitCard(
    outfitWithItems: OutfitWithItems,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = outfitWithItems.outfit.name,
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(onClick = onDelete) {
                    Text("Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Items (${outfitWithItems.items.size}):",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            outfitWithItems.items.forEach { item ->
                Text(
                    text = "• ${item.name} (${item.category})",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }
        }
    }
}

@Composable
fun CreateOutfitDialog(
    availableItems: List<com.example.archive.data.local.entities.ItemEntity>,
    onDismiss: () -> Unit,
    onConfirm: (String, List<String>) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedItemIds by remember { mutableStateOf(setOf<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear Outfit") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del outfit") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Selecciona items:",
                    style = MaterialTheme.typography.labelMedium
                )

                if (availableItems.isEmpty()) {
                    Text(
                        text = "No hay items disponibles. Añade items primero en la pantalla de Guardarropa.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    availableItems.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.id in selectedItemIds,
                                onCheckedChange = { checked ->
                                    selectedItemIds = if (checked) {
                                        selectedItemIds + item.id
                                    } else {
                                        selectedItemIds - item.id
                                    }
                                }
                            )
                            Text(
                                text = "${item.name} (${item.category})",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, selectedItemIds.toList()) },
                enabled = name.isNotBlank() && selectedItemIds.isNotEmpty()
            ) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}