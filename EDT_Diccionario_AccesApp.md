AccesApp — Diccionario de la EDT (WBS)

¿Para qué sirve?
Este documento explica cada parte del trabajo en la EDT de AccesApp (S2). Así, todos entendemos lo mismo, podemos seguir el progreso y controlar que todo vaya bien.

Gestión del proyecto

1.1 Planificación y tags
De qué se trata: Plan del trabajo S2, fechas importantes, cómo nombrar las ramas y las versiones del código.
Qué se entrega: Calendario S2, nombres Git (v0.1… v1.0-S2), un README que explique de qué va el proyecto.
Qué se necesita: Un estudiante, Android Studio, Git/GitHub.
Quién se encarga: El equipo del curso (el estudiante).
Cuánto tiempo: Semana 2 (4 horas).
Cuánto cuesta: Nada; esfuerzo 4 horas.
Cómo sabemos que está bien: Los nombres de las versiones se ven en GitHub; el README dice qué se busca y qué se necesita.
Qué puede salir mal: Nombres de versiones con fechas incorrectas. Cómo lo arreglamos: Volver a nombrar las versiones con la fecha correcta del código.

1.2 Entrega ZIP/APK/PDF
De qué se trata: Preparar el proyecto, el archivo APK para probar, y la documentación en PDF.
Qué se entrega: AccesApp.zip, app-debug.apk, un PDF con las respuestas de la tarea S2.
Qué se necesita: Gradle, Android Studio, una impresora PDF.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: El APK se instala, el ZIP se compila, el PDF está completo.
Qué puede salir mal: El APK no se instala por problemas de versión. Cómo lo arreglamos: Probar en un emulador API 34 y en un celular real.

Diseño y mockups

2.1 Flujos de usuario
De qué se trata: Definir cómo el usuario hace: Entrar → Registrarse → Entrar, Entrar → Recuperar clave, ir a Asistencia y Ajustes.
Qué se entrega: Un diagrama de flujo (PNG/PDF).
Qué se necesita: Canva o Draw.io.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: Los flujos muestran los casos principales y cómo volver atrás.
Qué puede salir mal: Olvidar casos importantes. Cómo lo arreglamos: Revisar con una lista de verificación.

2.2 Mockups para celular/tablet
De qué se trata: Hacer mockups de Entrar, Registrarse, Recuperar clave, Asistencia y Ajustes (que se vean bien en cualquier tamaño).
Qué se entrega: Mockups exportados (PNG).
Qué se necesita: Canva o Figma.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (4 horas).
Cuánto cuesta: Nada; esfuerzo 4 horas.
Cómo sabemos que está bien: Se usan componentes M3, se ve bien y hay buen contraste.
Qué puede salir mal: Que no se vea igual en todos lados. Cómo lo arreglamos: Usar un sistema de diseño (tipos de letra/espacios).

UI accesible (Compose + Material 3)

3.1 Login
De qué se trata: Campos para email, clave, “Recordarme”, rol (radio), idioma (dropdown), enlaces para Registrarse/Recuperar, lista tipo “tabla”.
Qué se entrega: LoginScreen.kt.
Qué se necesita: Compose, Material3.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (6 horas).
Cuánto cuesta: Nada; esfuerzo 6 horas.
Cómo sabemos que está bien: Valida lo básico, guarda estados, el foco está bien.
Qué puede salir mal: Avisos de la API. Cómo lo arreglamos: Usar APIs más nuevas y linters.

3.2 Registro
De qué se trata: Nombre, email, clave/confirmación, rol, idioma, términos; agregar a una lista (máximo 5).
Qué se entrega: RegisterScreen.kt.
Qué se necesita: Compose, ViewModel.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (5 horas).
Cuánto cuesta: Nada; esfuerzo 5 horas.
Cómo sabemos que está bien: No deja agregar más de 5 usuarios; avisa con un Snackbar.
Qué puede salir mal: Emails repetidos. Cómo lo arreglamos: Verificar que el email no exista en el ViewModel.

3.3 Recuperar contraseña
De qué se trata: Ingresar email y método; avisar que se envió el correo (simulado).
Qué se entrega: RecoverPasswordScreen.kt.
Qué se necesita: Compose, SnackbarHost.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: Muestra un mensaje de confirmación; se puede volver atrás.
Qué puede salir mal: Usar iconos antiguos. Cómo lo arreglamos: Usar variantes AutoMirrored.

3.4 Asistencia (leer y escribir)
De qué se trata: Editor con TTS (lectura) y dictado por voz (ASR); controles de velocidad/tono y opciones para copiar, pegar y guardar.
Qué se entrega: AssistScreen.kt.
Qué se necesita: TextToSpeech, SpeechRecognizer, permisos de micrófono.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (8 horas).
Cuánto cuesta: Nada; esfuerzo 8 horas.
Cómo sabemos que está bien: TTS funciona en español; el dictado escribe; accesible con TalkBack.
Qué puede salir mal: Permisos o idioma del TTS. Cómo lo arreglamos: Manejar errores y alternativas.

3.5 Ajustes
De qué se trata: Cambiar a alto contraste y opciones básicas.
Qué se entrega: SettingsScreen.kt y persistencia de estado en el ViewModel.
Qué se necesita: Compose, Material3.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: El estado se guarda; el tema cambia toda la interfaz.
Qué puede salir mal: Que no se apliquen los cambios. Cómo lo arreglamos: Guardar el estado en MainActivity.

Lógica y datos

4.1 Modelo User y reglas (≤5)
De qué se trata: Clase User(name, email, password, role, language) y lista en memoria con máximo 5 usuarios.
Qué se entrega: User.kt.
Qué se necesita: Kotlin, mutableStateListOf.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (2 horas).
Cuánto cuesta: Nada; esfuerzo 2 horas.
Cómo sabemos que está bien: Estructura correcta; límite y sin emails duplicados.
Qué puede salir mal: Definición duplicada. Cómo lo arreglamos: Un solo archivo y package correcto.

4.2 AuthViewModel validación
De qué se trata: Registrar, entrar, mensajes, notas de asistencia.
Qué se entrega: AuthViewModel.kt.
Qué se necesita: AndroidX Lifecycle ViewModel.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (4 horas).
Cuánto cuesta: Nada; esfuerzo 4 horas.
Cómo sabemos que está bien: Métodos register() y login() y Snackbar con resultado.
Qué puede salir mal: Estado inconsistente. Cómo lo arreglamos: Una sola fuente de datos.

Navegación

5.1 NavHost y rutas
De qué se trata: login, register, recover, assist, settings con rememberNavController().
Qué se entrega: AppNavHost.kt.
Qué se necesita: Navigation-Compose.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: Se navega con botones y enlaces; la navegación funciona bien.
Qué puede salir mal: Rutas mal escritas. Cómo lo arreglamos: Centralizar las rutas.

5.2 Botón Atrás/Arriba
De qué se trata: TopAppBar con navegación lógica en pantallas secundarias.
Qué se entrega: Acciones en cada pantalla.
Qué se necesita: Material3 TopAppBar, navController.popBackStack().
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (1 hora).
Cuánto cuesta: Nada; esfuerzo 1 hora.
Cómo sabemos que está bien: Accesible con TalkBack y anuncia “Volver”.
Qué puede salir mal: Iconos sin descripción. Cómo lo arreglamos: Usar Semantics.

Accesibilidad y adaptatividad

6.1 Semantics y TalkBack
De qué se trata: contentDescription, roles, encabezados, orden del foco y announceForAccessibility para mensajes.
Qué se entrega: utilidades en a11y/AccessibilityHelpers.kt.
Qué se necesita: Compose Semantics, AccessibilityManager.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: Se navega con TalkBack; se leen los mensajes.
Qué puede salir mal: APIs incompatibles. Cómo lo arreglamos: Helpers compatibles.

6.2 Tema de alto contraste y tamaños ≥48dp
De qué se trata: Paleta M3 de alto contraste, tipografías legibles y controles táctiles grandes.
Qué se entrega: theme/Color.kt y Theme.kt.
Qué se necesita: Material Theme 3.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (2 horas).
Cuánto cuesta: Nada; esfuerzo 2 horas.
Cómo sabemos que está bien: Contraste ≥ 4.5:1 y controles ≥48dp.
Qué puede salir mal: Inconsistencia visual. Cómo lo arreglamos: Revisión visual y herramientas de contraste.

Conectividad

7.1 Banner “Sin conexión”
De qué se trata: Detectar conexión y mostrar un aviso discreto.
Qué se entrega: ConnectivityBanner.kt.
Qué se necesita: ConnectivityManager, Compose.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (2 horas).
Cuánto cuesta: Nada; esfuerzo 2 horas.
Cómo sabemos que está bien: El banner solo aparece sin red; es accesible.
Qué puede salir mal: Estado incorrecto. Cómo lo arreglamos: Observar cambios del sistema.

QA y documentación

8.1 Pruebas y lista de verificación a11y
De qué se trata: Probar navegación manual, límite de usuarios, TalkBack y contraste.
Qué se entrega: Lista de verificación (MD/PDF).
Qué se necesita: Emulador Pixel 6 API 34 y TalkBack.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (3 horas).
Cuánto cuesta: Nada; esfuerzo 3 horas.
Cómo sabemos que está bien: Todos los puntos están “OK”.
Qué puede salir mal: Cobertura insuficiente. Cómo lo arreglamos: Probar en dos tamaños de pantalla.

8.2 PRD y Formato de respuesta
De qué se trata: Documento con alcance, WBS, riesgos, entregables y criterios de evaluación.
Qué se entrega: PDF “Formato de respuesta S2” y este Diccionario EDT.
Qué se necesita: Editor (Word/Docs) y Canva para diagramas.
Quién se encarga: El estudiante.
Cuánto tiempo: Semana 2 (4 horas).
Cuánto cuesta: Nada; esfuerzo 4 horas.
Cómo sabemos que está bien: Documento completo y consistente con la app.
Qué puede salir mal: No seguir la guía. Cómo lo arreglamos: Lista de verificación final.