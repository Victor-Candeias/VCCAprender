package com.vcc.aprender.ui.profiles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.profile.ChildProfile

@Composable
fun ProfileScreen(
    profiles: List<ChildProfile>,
    error: String?,
    onClearError: () -> Unit,
    onSave: (Long, String, Int, String) -> Unit,
    onSelect: (Long) -> Unit,
    onDelete: (ChildProfile) -> Unit
) {
    var editing by remember { mutableStateOf(false) }
    var id by remember { mutableLongStateOf(0L) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(5) }
    var avatar by remember { mutableStateOf("🌟") }
    var pendingDelete by remember { mutableStateOf<ChildProfile?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Quem vai aprender?") }
        items(profiles, key = { it.id }) { profile ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("${profile.avatar} ${profile.name} · ${profile.age} anos")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { onSelect(profile.id) }) { Text("Entrar") }
                        OutlinedButton(onClick = {
                            id = profile.id; name = profile.name
                            age = profile.age; avatar = profile.avatar; editing = true
                        }) { Text("Editar") }
                        TextButton(onClick = { pendingDelete = profile }) { Text("Eliminar") }
                    }
                }
            }
        }
        item {
            Button(onClick = {
                id = 0L; name = ""; age = 5; avatar = "🌟"; editing = true
            }) { Text("Adicionar criança") }
        }
    }

    if (editing) {
        AlertDialog(
            onDismissRequest = { editing = false },
            title = { Text(if (id == 0L) "Novo perfil" else "Editar perfil") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it.take(40) },
                        label = { Text("Nome") }, singleLine = true)
                    Text("Idade: $age anos")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { if (age > 3) age-- }) { Text("−") }
                        OutlinedButton(onClick = { if (age < 8) age++ }) { Text("+") }
                    }
                    Text("Avatar")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("🌟", "🐻", "🦊", "🐼").forEach { option ->
                            TextButton(onClick = { avatar = option }) {
                                Text(if (avatar == option) "✓$option" else option)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(enabled = name.isNotBlank(), onClick = {
                    onSave(id, name, age, avatar); editing = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { editing = false }) { Text("Cancelar") } }
        )
    }
    pendingDelete?.let { profile ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Eliminar perfil?") },
            text = { Text("Eliminar o perfil de ${profile.name}?") },
            confirmButton = {
                TextButton(onClick = { onDelete(profile); pendingDelete = null }) { Text("Eliminar") }
            },
            dismissButton = { TextButton(onClick = { pendingDelete = null }) { Text("Cancelar") } }
        )
    }
    if (error != null) {
        AlertDialog(onDismissRequest = onClearError, title = { Text("Erro") },
            text = { Text(error) },
            confirmButton = { TextButton(onClick = onClearError) { Text("OK") } })
    }
}
