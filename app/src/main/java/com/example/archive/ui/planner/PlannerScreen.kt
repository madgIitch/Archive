package com.example.archive.ui.planner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.example.archive.data.local.entities.PlannerWithOutfit
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(
    viewModel: PlannerViewModel = koinViewModel()
) {
    val plannedOutfits by viewModel.plannedOutfits.collectAsState()
    val availableOutfits by viewModel.availableOutfits.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador") },
                actions = {
                    IconButton(onClick = { viewModel.clearPastPlannedOutfits() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Limpiar pasados")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Planificar outfit")
            }
        }
    ) { padding ->
        if (plannedOutfits.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay outfits planificados. Añade uno con el botón +")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(plannedOutfits) { plannedOutfit ->
                    PlannedOutfitCard(
                        plannedOutfit = plannedOutfit,
                        onDelete = { viewModel.deletePlannedOutfit(plannedOutfit.planner) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        PlanOutfitDialog(
            availableOutfits = availableOutfits,
            onDismiss = { showDialog = false },
            onConfirm = { outfitId, date, notes ->
                viewModel.planOutfit(outfitId, date, notes)
                showDialog = false
            }
        )
    }
}

@Composable
fun PlannedOutfitCard(
    plannedOutfit: PlannerWithOutfit,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dateFormat.format(Date(plannedOutfit.planner.date)),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = plannedOutfit.outfit.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                TextButton(onClick = onDelete) {
                    Text("Eliminar")
                }
            }

            plannedOutfit.planner.notes?.let { notes ->
                if (notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Notas: $notes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanOutfitDialog(
    availableOutfits: List<com.example.archive.data.local.entities.OutfitEntity>,
    onDismiss: () -> Unit,
    onConfirm: (String, Long, String?) -> Unit
) {
    var selectedOutfitId by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Planificar Outfit") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Selector de outfit
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = availableOutfits.find { it.id == selectedOutfitId }?.name ?: "Selecciona un outfit",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Outfit") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (availableOutfits.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No hay outfits disponibles") },
                                onClick = { },
                                enabled = false
                            )
                        } else {
                            availableOutfits.forEach { outfit ->
                                DropdownMenuItem(
                                    text = { Text(outfit.name) },
                                    onClick = {
                                        selectedOutfitId = outfit.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Selector de fecha (simplificado - muestra timestamp)
                OutlinedTextField(
                    value = dateFormat.format(Date(selectedDate)),
                    onValueChange = {},
                    label = { Text("Fecha") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Usa los botones para ajustar la fecha:",
                    style = MaterialTheme.typography.bodySmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = selectedDate
                            add(Calendar.DAY_OF_MONTH, -1)
                        }
                        selectedDate = calendar.timeInMillis
                    }) {
                        Text("-1 día")
                    }
                    Button(onClick = {
                        selectedDate = Calendar.getInstance().timeInMillis
                    }) {
                        Text("Hoy")
                    }
                    Button(onClick = {
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = selectedDate
                            add(Calendar.DAY_OF_MONTH, 1)
                        }
                        selectedDate = calendar.timeInMillis
                    }) {
                        Text("+1 día")
                    }
                }

                // Notas
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedOutfitId?.let { outfitId ->
                        onConfirm(outfitId, selectedDate, notes.ifBlank { null })
                    }
                },
                enabled = selectedOutfitId != null
            ) {
                Text("Planificar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}