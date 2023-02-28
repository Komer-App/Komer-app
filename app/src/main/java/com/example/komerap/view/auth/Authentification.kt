package com.example.komerap.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

class AuthScreen {
    @Composable
    fun AuthContent(navController : NavHostController) {
        val (currentPage, setCurrentPage) = remember { mutableStateOf(AuthPage.SignUp) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Komer App",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                TextButton(
                    onClick = {
                        if (currentPage == AuthPage.SignUp) {
                            setCurrentPage(AuthPage.LogIn)
                        } else {
                            setCurrentPage(AuthPage.SignUp)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = if (currentPage == AuthPage.SignUp) {
                            "Log In"
                        } else {
                            "Sign Up"
                        },
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }

            when (currentPage) {
                AuthPage.SignUp -> SignUpScreen().SignUpContent(navController)
                AuthPage.LogIn -> LogInScreen().LoginContent(navController)
            }
        }
    }
}

enum class AuthPage {
    SignUp,
    LogIn
}


