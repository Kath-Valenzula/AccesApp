package com.dsy2204.accesapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dsy2204.accesapp.a11y.AccessibleMessage
import com.dsy2204.accesapp.a11y.a11yButton
import com.dsy2204.accesapp.auth.AuthViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistScreen(onBack: () -> Unit, onOpenSettings: () -> Unit, auth: AuthViewModel) {
    val context = LocalContext.current
    val snackbar = remember { SnackbarHostState() }
    val haptic = LocalHapticFeedback.current
    val clipboard = LocalClipboardManager.current
    val mainHandler = remember { Handler(Looper.getMainLooper()) }

    var input by remember { mutableStateOf("") }
    var ttsReady by remember { mutableStateOf(false) }
    var fontSize by remember { mutableFloatStateOf(20f) }
    var speechRate by remember { mutableFloatStateOf(1.0f) }
    var pitch by remember { mutableFloatStateOf(1.0f) }
    var isListening by remember { mutableStateOf(false) }
    var currentSentenceIndex by remember { mutableIntStateOf(-1) }

    val tts = remember { TextToSpeech(context) { status -> ttsReady = status == TextToSpeech.SUCCESS } }
    LaunchedEffect(ttsReady) { if (ttsReady) tts.language = Locale("es", "ES") }
    DisposableEffect(Unit) { onDispose { tts.stop(); tts.shutdown() } }

    val sentences: List<String> = remember(input) { input.split(Regex("(?<=[.!?¡¿])\\s+")).filter { it.isNotBlank() } }
    val sentencesState by rememberUpdatedState(sentences)

    LaunchedEffect(Unit) {
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                utteranceId?.removePrefix("s_")?.toIntOrNull()?.let { idx ->
                    mainHandler.post { currentSentenceIndex = idx }
                }
            }
            override fun onDone(utteranceId: String?) {
                val idx = utteranceId?.removePrefix("s_")?.toIntOrNull() ?: -1
                if (idx == sentencesState.lastIndex) mainHandler.post { currentSentenceIndex = -1 }
            }
            override fun onError(utteranceId: String?) {}
        })
    }

    val openTxt = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { stream ->
                val txt = BufferedReader(InputStreamReader(stream, Charset.defaultCharset())).readText()
                input = txt
            }
        }
    }

    val speechRecognizer = remember {
        if (SpeechRecognizer.isRecognitionAvailable(context)) SpeechRecognizer.createSpeechRecognizer(context) else null
    }
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }
    val askMicPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            isListening = true
            speechRecognizer?.startListening(recognizerIntent)
        }
    }
    DisposableEffect(speechRecognizer) {
        speechRecognizer?.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onResults(results: android.os.Bundle?) {
                val list = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).orEmpty()
                if (list.isNotEmpty()) input = if (input.isBlank()) list[0] else "$input ${list[0]}"
            }
            override fun onPartialResults(partialResults: android.os.Bundle?) {
                val list = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).orEmpty()
                if (list.isNotEmpty()) input = if (input.isBlank()) list[0] else "$input ${list[0]}"
            }
            override fun onError(error: Int) { isListening = false }
            override fun onReadyForSpeech(params: android.os.Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onBeginningOfSpeech() {}
            override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
        })
        onDispose { speechRecognizer?.destroy() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asistente de lectura y dictado") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics { contentDescription = "Volver" }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onOpenSettings, modifier = Modifier.semantics { contentDescription = "Abrir ajustes" }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AccessibleMessage(auth.lastMessage.value)

            Text("Tamaño de texto: ${fontSize.toInt()}sp")
            Slider(value = fontSize, valueRange = 16f..32f, onValueChange = { fontSize = it })

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Texto") },
                textStyle = TextStyle(fontSize = fontSize.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .semantics { contentDescription = "Área de texto para leer o dictar" }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        currentSentenceIndex = -1
                        tts.setSpeechRate(speechRate)
                        tts.setPitch(pitch)
                        sentences.forEachIndexed { idx, s ->
                            val mode = if (idx == 0) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
                            tts.speak(s.trim(), mode, null, "s_$idx")
                        }
                    },
                    enabled = ttsReady && sentences.isNotEmpty(),
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Leer en voz alta" }
                ) { Text("Leer") }
                Button(
                    onClick = { tts.stop() },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Pausar" }
                ) { Text("Pausar") }
                Button(
                    onClick = {
                        tts.setSpeechRate(speechRate)
                        tts.setPitch(pitch)
                        val start = currentSentenceIndex.coerceAtLeast(0)
                        for (i in start until sentences.size) {
                            val mode = if (i == start) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
                            tts.speak(sentences[i].trim(), mode, null, "s_$i")
                        }
                    },
                    enabled = ttsReady && sentences.isNotEmpty(),
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Reanudar lectura" }
                ) { Text("Reanudar") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        if (granted) { isListening = true; speechRecognizer?.startListening(recognizerIntent) }
                        else { askMicPermission.launch(Manifest.permission.RECORD_AUDIO) }
                    },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Iniciar dictado continuo" }
                ) { Text(if (isListening) "Escuchando..." else "Dictado continuo") }
                Button(
                    onClick = { isListening = false; speechRecognizer?.stopListening() },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Detener dictado" }
                ) { Text("Detener dictado") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { openTxt.launch(arrayOf("text/*")) },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Cargar archivo de texto" }
                ) { Text("Cargar .txt") }
                Button(
                    onClick = { input = "" },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Limpiar texto" }
                ) { Text("Limpiar") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { clipboard.setText(AnnotatedString(input)); haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress) },
                    enabled = input.isNotBlank(),
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Copiar al portapapeles" }
                ) { Text("Copiar") }
                Button(
                    onClick = {
                        val paste = clipboard.getText()?.text.orEmpty()
                        if (paste.isNotBlank()) input = if (input.isBlank()) paste else "$input $paste"
                    },
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Pegar desde portapapeles" }
                ) { Text("Pegar") }
                Button(
                    onClick = {
                        if (input.isNotBlank()) {
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"; putExtra(Intent.EXTRA_TEXT, input)
                            }
                            context.startActivity(Intent.createChooser(send, "Compartir texto"))
                        }
                    },
                    enabled = input.isNotBlank(),
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Compartir texto" }
                ) { Text("Compartir") }
                Button(
                    onClick = { auth.saveNote(input); haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress) },
                    enabled = input.isNotBlank(),
                    modifier = Modifier.weight(1f).a11yButton().semantics { contentDescription = "Guardar nota" }
                ) { Text("Guardar") }
            }

            Text("Vista de lectura")
            val highlighted: AnnotatedString = remember(sentences, currentSentenceIndex) {
                buildAnnotatedString {
                    sentences.forEachIndexed { idx, s ->
                        if (idx == currentSentenceIndex) withStyle(SpanStyle(background = Color.Yellow, color = Color.Black)) { append(s) }
                        else append(s)
                        append(" ")
                    }
                }
            }
            Text(highlighted, style = TextStyle(fontSize = (fontSize + 2).sp), modifier = Modifier.fillMaxWidth())

            Text("Notas guardadas")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth().weight(1f, false)) {
                itemsIndexed(auth.notes) { index, note ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Text(note, modifier = Modifier.weight(1f))
                        Button(onClick = { tts.speak(note, TextToSpeech.QUEUE_FLUSH, null, "n_$index") }, modifier = Modifier.a11yButton()) { Text("Leer") }
                        Button(onClick = { auth.deleteNote(index) }, modifier = Modifier.a11yButton()) { Text("Eliminar") }
                    }
                }
            }
        }
    }
}
