package com.example.farmcab.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.farmcab.viewmodel.AuthState
import com.example.farmcab.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

//        authViewModel.login("jaydeepprince1234@gmail.com", password="12345678")

        Text("Login", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }else{
//                Toast.makeText(context, "Else condition", Toast.LENGTH_SHORT).show()
                authViewModel.login("jaydeepprince1234@gmail.com", "12345678")
            }
        }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick={
               navController.navigate("signup")
        })
        {
            Text("Dont have an account? Signup")
        }

        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> {
                val userId = (authState as AuthState.Success).userId
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                LaunchedEffect(userId) {
                    navController.navigate("MainScreen") {
                        popUpTo("login") { inclusive = true }
                    }
                    authViewModel.resetAuthState()
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}
