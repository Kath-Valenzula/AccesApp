package com.dsy2204.accesapp.a11y

import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics

fun Modifier.a11yButton(labelRole: Role = Role.Button): Modifier =
    this.then(Modifier.minimumInteractiveComponentSize())
        .then(Modifier.semantics { role = labelRole })

@Composable
fun ScreenHeading(text: String) {
    Text(text = text, modifier = Modifier.semantics { heading() })
}

@Composable
fun AccessibleMessage(message: String?) {
    val view = LocalView.current
    LaunchedEffect(message) { if (message != null) view.announceForAccessibility(message) }
    if (message != null) {
        Text(text = message, modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive })
    }
}
