package com.vcc.aprender.ui.editor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.cards.CardDraft
import com.vcc.aprender.data.cards.CustomCardEntity
import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.model.LearningTopic

@Composable
fun ActivityEditorScreen(
    cards: List<CustomCardEntity>,
    message: String?,
    onSave: (CardDraft, Long) -> Unit,
    onDelete: (Long) -> Unit,
    onImport: (android.content.Context, android.net.Uri) -> Unit,
    onExport: (android.content.Context, android.net.Uri) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) onImport(context, uri)
    }
    val exportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri ->
        if (uri != null) onExport(context, uri)
    }
    var editingId by remember { mutableStateOf(0L) }
    var question by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf(LearningTopic.LETTERS) }
    var difficulty by remember { mutableStateOf(CardDifficulty.EASY) }
    var first by remember { mutableStateOf("") }
    var second by remember { mutableStateOf("") }
    var third by remember { mutableStateOf("") }
    var correct by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Editor de atividades", style = MaterialTheme.typography.headlineMedium)
        Text("Cria cartões personalizados, guardados apenas neste dispositivo.")
        Button(onClick = { importLauncher.launch(arrayOf("application/json", "text/plain")) }) { Text("Importar JSON") }
        Button(onClick = { exportLauncher.launch("vcc-aprender-atividades.json") }) { Text("Exportar JSON") }
        Text("Categoria")
        LearningTopic.entries.forEach { item ->
            FilterChip(selected = topic == item, onClick = { topic = item }, label = { Text(item.title) })
        }
        Text("Dificuldade")
        CardDifficulty.entries.forEach { level ->
            FilterChip(selected = difficulty == level, onClick = { difficulty = level }, label = { Text(level.name) })
        }
        OutlinedTextField(question, { question = it.take(200) }, label = { Text("Pergunta") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(first, { first = it.take(80) }, label = { Text("Resposta A") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(second, { second = it.take(80) }, label = { Text("Resposta B") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(third, { third = it.take(80) }, label = { Text("Resposta C") }, modifier = Modifier.fillMaxWidth())
        Text("Resposta correta")
        listOf("A", "B", "C").forEachIndexed { index, label ->
            FilterChip(selected = correct == index, onClick = { correct = index }, label = { Text(label) })
        }
        Button(onClick = {
            onSave(CardDraft(topic, difficulty, question, listOf(first, second, third), correct), editingId)
        }) { Text(if (editingId == 0L) "Criar atividade" else "Guardar alterações") }
        OutlinedButton(onClick = {
            editingId = 0; question = ""; first = ""; second = ""; third = ""; correct = 0
        }) { Text("Novo cartão") }
        message?.let { Text(it) }
        Text("Atividades personalizadas: ${cards.size}", style = MaterialTheme.typography.titleMedium)
        cards.forEach { item ->
            Text(item.question)
            OutlinedButton(onClick = {
                editingId = item.id
                question = item.question
                topic = LearningTopic.entries.firstOrNull { it.name == item.topic } ?: LearningTopic.LETTERS
                difficulty = CardDifficulty.entries.firstOrNull { it.name == item.difficulty } ?: CardDifficulty.EASY
                first = item.firstOption; second = item.secondOption; third = item.thirdOption
                correct = item.correctIndex
            }) { Text("Editar cartão ${item.id}") }
            OutlinedButton(onClick = { onDelete(item.id) }) { Text("Eliminar cartão ${item.id}") }
        }
        Button(onClick = onBack) { Text("Voltar ao início") }
    }
}
