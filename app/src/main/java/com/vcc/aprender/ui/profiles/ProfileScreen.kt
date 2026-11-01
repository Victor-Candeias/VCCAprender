package com.vcc.aprender.ui.profiles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
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
import com.vcc.aprender.ui.components.FamilyHeader
import com.vcc.aprender.ui.components.FamilySection
import com.vcc.aprender.ui.theme.AprenderColors
import androidx.compose.material3.MaterialTheme

@Composable
fun ProfileScreen(
    profiles: List<ChildProfile>,
    error: String?,
    onClearError: () -> Unit,
    onSave: (Long, String, Int, String) -> Unit,
    onSelect: (Long) -> Unit,
    onDelete: (ChildProfile) -> Unit,
    onDeleteAll: () -> Unit = {}
) {
    var editing by remember { mutableStateOf(false) }
    var id by remember { mutableLongStateOf(0L) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableIntStateOf(5) }
    var avatar by remember { mutableStateOf("🌟") }
    var pendingDelete by remember { mutableStateOf<ChildProfile?>(null) }
    var confirmDeleteAll by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.TopCenter) {
    LazyColumn(
        modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { FamilyHeader("👧 🧒", "Quem vai aprender?", "Escolhe o teu perfil para começar a aventura!", AprenderColors.Lavender) }
        item { Text("🔒 Os perfis e resultados ficam neste dispositivo. Não é necessária conta nem Internet.", style = MaterialTheme.typography.bodyMedium) }
        items(profiles, key = { it.id }) { profile ->
            FamilySection(profile.name, profile.avatar, AprenderColors.Sky) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("${profile.age} anos", style = MaterialTheme.typography.bodyLarge)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { onSelect(profile.id) }, modifier = Modifier.fillMaxWidth()) { Text("Entrar") }
                        OutlinedButton(onClick = {
                            id = profile.id; name = profile.name
                            age = profile.age; avatar = profile.avatar; editing = true
                        }, modifier = Modifier.fillMaxWidth()) { Text("Editar") }
                        TextButton(onClick = { pendingDelete = profile }, modifier = Modifier.fillMaxWidth()) { Text("Eliminar") }
                    }
                }
            }
        }
        item {
            Button(onClick = {
                id = 0L; name = ""; age = 5; avatar = "🌟"; editing = true
            }, modifier = Modifier.fillMaxWidth()) { Text("➕ Adicionar criança") }
        }
        if (profiles.isNotEmpty()) {
            item { TextButton(onClick = { confirmDeleteAll = true }) { Text("Eliminar todos os perfis e resultados") } }
        }
    }
    }

    if (confirmDeleteAll) {
        AlertDialog(
            onDismissRequest = { confirmDeleteAll = false },
            title = { Text("Eliminar todos os dados das crianças?") },
            text = { Text("Todos os perfis, resultados e históricos de respostas serão eliminados definitivamente deste dispositivo. Os cartões personalizados do editor não serão eliminados.") },
            confirmButton = { TextButton(onClick = { onDeleteAll(); confirmDeleteAll = false }) { Text("Eliminar tudo") } },
            dismissButton = { TextButton(onClick = { confirmDeleteAll = false }) { Text("Cancelar") } }
        )
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
            text = { Text("Eliminar definitivamente o perfil de ${profile.name}, o progresso e todo o histórico de respostas desta criança? Esta ação não pode ser anulada.") },
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
