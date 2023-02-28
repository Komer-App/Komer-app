package com.example.komerap.view.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class SignUpScreen {
    @Composable
    fun SignUpContent(navController: Any) {
        var emailValue by remember { mutableStateOf("") }
        var passwordValue by remember { mutableStateOf("") }
        var confirmPasswordValue by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false) }
        var showSuccess by remember { mutableStateOf(false) }
        var errorText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                singleLine = true,
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )

            OutlinedTextField(
                singleLine = true,
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text("Mot de passe") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Filled.Lock else Icons.Filled.Done,
                            contentDescription = if (passwordVisibility) "Cacher le mot de passe" else "Afficher le mot de passe"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            OutlinedTextField(
                singleLine = true,
                value = confirmPasswordValue,
                onValueChange = { confirmPasswordValue = it },
                label = { Text("Confirmer mot de passe") },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                        Icon(
                            imageVector = if (confirmPasswordVisibility) Icons.Filled.Lock else Icons.Filled.Done,
                            contentDescription = if (confirmPasswordVisibility) "Cacher le mot de passe" else "Afficher le mot de passe"
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            if (showError) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            if (showSuccess) {
                Text(
                    text = "Inscription réussie !",
                    color = Color.Green,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            val auth = FirebaseAuth.getInstance()

            Button(
                onClick = {
                    if (passwordValue != confirmPasswordValue) {
                        showError = true
                        errorText = "Les deux mots de passe doivent être identiques"
                    } else if (passwordValue.length < 6 || passwordValue.uppercase() == passwordValue) {
                        showError = true
                        errorText = "Le mot de passe doit contenir au moins 6 caractères dont une majuscule"
                    } else {
                        auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showSuccess = true
                                } else {
                                    showError = true
                                    errorText = task.exception?.message ?: "Erreur inconnue"
                                }
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("S'inscrire")
            }

        }
    }
}
