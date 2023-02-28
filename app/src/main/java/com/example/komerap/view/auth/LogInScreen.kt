package com.example.komerap.view.auth

import android.util.Log
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
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

class LogInScreen {
    @Composable
    fun LoginContent(navController: NavHostController) {
        var emailValue by remember { mutableStateOf("") }
        var passwordValue by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false) }
        var errorText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                singleLine = true,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            OutlinedTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                singleLine = true,
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

            if (showError) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("AuthScreen", "User successfully logged in with email: $emailValue")
                                navController.navigate("Home")
                            } else {
                                // La connexion a échoué
                                showError = true
                                errorText = task.exception?.message ?: "Une erreur inconnue s'est produite"
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Se connecter")
            }

        }
    }

}