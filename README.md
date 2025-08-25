# AccesApp

Asistente de comunicación inclusiva para personas con **discapacidad visual** (baja visión/ceguera). Facilita **leer** (TTS) y **escribir** (dictado), con una UI clara y **accesible** basada en **Material 3 + Jetpack Compose**.

## Tabla de contenido

* [Objetivo](#objetivo)
* [Alcance de la entrega (S2)](#alcance-de-la-entrega-s2)
* [Características](#características)
* [Arquitectura y stack](#arquitectura-y-stack)
* [Requisitos](#requisitos)
* [Cómo ejecutar](#cómo-ejecutar)
* [Estructura del proyecto](#estructura-del-proyecto)
* [Accesibilidad](#accesibilidad)
* [Rendimiento esperado](#rendimiento-esperado)
* [Versionado](#versionado)
* [Pruebas manuales sugeridas](#pruebas-manuales-sugeridas)
* [Limitaciones conocidas](#limitaciones-conocidas)
* [Roadmap (futuras iteraciones)](#roadmap-futuras-iteraciones)
* [Licencia y uso](#licencia-y-uso)
* [Créditos](#créditos)


## Objetivo

Entregar una base sólida y evaluable de una app Android que mejore la **comunicación diaria** de personas con discapacidad visual, priorizando **accesibilidad**, **claridad** y **simplicidad**.

## Alcance de la entrega (S2)

* Vistas: **Login**, **Registro**, **Recuperar contraseña**, **Asistencia (leer/escribir)** y **Ajustes**.
* **Arreglo en memoria** (máx. **5** usuarios) creado desde Registro; **Login** valida contra esa lista.
* **Navegación** con Navigation-Compose.
* **Conectividad:** banner “**Sin conexión**” (no bloqueante).
* **Tema alto contraste** y prácticas de accesibilidad (TalkBack, foco, targets ≥48dp).
* Sin backend ni BD; recuperación de contraseña **simulada**.

## Características

* **Lectura en voz alta (TTS):** controles de **Leer / Pausar / Reanudar**, velocidad/tono y **resaltado** de la oración.
* **Dictado continuo (ASR):** voz a texto en el área de edición.
* **UI accesible Material 3:** TextField, Button, Checkbox, RadioButton, Dropdown, Links, y **lista** (LazyColumn) como “tabla”.
* **Estados claros:** feedback textual y avisos accesibles.
* **Adaptatividad:** diseño que escala entre teléfono y tablet.

## Arquitectura y stack

* **Lenguaje:** Kotlin
* **UI:** Jetpack **Compose** + **Material 3**
* **Navegación:** Navigation-Compose
* **Arquitectura:** **MVVM**, estado centralizado en `ViewModel`
* **Mínimos de plataforma:** `minSdk 21`, `target/compileSdk 34`
* **Herramientas:** Android Studio (Ladybug), Gradle, JDK 17

## Requisitos

* **Android Studio 2024.2.1 (Ladybug)** o superior, **JDK 17**.
* Emulador recomendado: **Pixel 6 – API 34 (Google APIs)**.
* Permisos:

  * `android.permission.INTERNET` (banner de conectividad).
  * Para **dictado**, el dispositivo debe tener servicio de voz activo y permitir acceso al micrófono (el sistema solicitará el permiso en tiempo de ejecución si aplica).
* Paquetes de voz instalados para **es-CL/es-ES** (para TTS/ASR).

## Cómo ejecutar

1. **Clonar**

   ```bash
   git clone https://github.com/Kath-Valenzula/AccesApp.git
   cd AccesApp
   ```
2. **Abrir en Android Studio** (selecciona la carpeta del proyecto).
3. **Sincronizar Gradle** (AS lo hace automáticamente).
4. **Crear/arrancar AVD**: Pixel 6 (API 34) → *Cold Boot Now* si el emulador ya estaba abierto.
5. **Ejecutar**: botón ▶ “app” (Build Variant: `debug`).

   * Si aparece “device already running/activating”, detén el AVD y haz *Cold Boot/Wipe Data*.

> **Nota de uso**
>
> * En **Registro**, crea hasta **5** usuarios.
> * Vuelve a **Login** y valida con esos usuarios.
> * En **Asistencia**, prueba **Leer** (TTS) y **Dictado** (ASR).
> * Desactiva la red del emulador para ver el **banner “Sin conexión”**.

## Estructura del proyecto

```
app/
 └─ src/main/
    ├─ AndroidManifest.xml
    ├─ java/…/accesapp/
    │  ├─ ui/
    │  │  ├─ LoginScreen.kt
    │  │  ├─ RegisterScreen.kt
    │  │  ├─ RecoverPasswordScreen.kt
    │  │  ├─ AssistScreen.kt
    │  │  └─ SettingsScreen.kt
    │  ├─ nav/ AppNavHost.kt
    │  ├─ data/ User.kt
    │  ├─ viewmodel/ AuthViewModel.kt
    │  └─ widgets/ ConnectivityBanner.kt
    └─ res/
       ├─ values/ (colores, temas, strings)
       └─ drawable/
```


## Accesibilidad

* **Semantics/`contentDescription`** en controles y mensajes.
* **Foco inicial** en campos de entrada; orden lógico de navegación.
* **Tamaños táctiles ≥ 48dp**; tipografías legibles y **alto contraste**.
* Mensajes y cambios de estado **anunciados** para TalkBack.

## Rendimiento esperado

* Arranque (debug, emulador API 34) ≤ **2.5 s**.
* Navegación entre pantallas ≤ **300 ms**.
* Inicio de **TTS** ≤ **1.5 s**; **Dictado** primer resultado ≈ **1–2 s**.
* Scroll en listas fluido (sin jank perceptible).

## Versionado

* **Rama principal:** `main`
* **Tags de hito:** `v0.1`, `v0.2`, `v0.3`, `v0.4`, `v1.0-S2`
* Histórico de cambios en el PDF/tabla de revisiones de la entrega.

## Pruebas manuales sugeridas

* **Registro**: crear 5 usuarios; verificar rechazo del 6.º.
* **Login**: éxito/error con feedback accesible.
* **Recuperar**: confirmación de envío (simulado).
* **Asistencia**: TTS lee el texto; Dictado ingresa texto.
* **Conectividad**: alternar red y verificar banner.
* **Accesibilidad**: activar **TalkBack** y recorrer todos los controles.

## Limitaciones conocidas

* Datos **no persistentes** (en memoria); **sin backend** ni recuperación real.
* **Precisión** del dictado depende del dispositivo y del paquete de voz.
* Soporte de **idiomas** centrado en español (no i18n completa).
* **Sin** base de datos, sincronización, notificaciones ni analíticas en S2.

## Roadmap (futuras iteraciones)

* **OCR** para leer texto desde imágenes/PDF.
* **Persistencia** local (Room) y/o **sincronización** en la nube.
* **Perfiles** de usuario; compartir y exportar notas.
* Integración con **hardware** de accesibilidad (p. ej., línea braille).

## Licencia y uso

Proyecto con fines **académicos** (DSY2204 — Desarrollo de Aplicaciones Móviles).
Los textos/usuarios de prueba deben ser **ficticios**.

## Créditos

Proyecto desarrollado por **Kath Stark** en el marco de **DSY2204**.
Agradecimientos al equipo docente y a las pautas del curso por el enfoque en accesibilidad.
