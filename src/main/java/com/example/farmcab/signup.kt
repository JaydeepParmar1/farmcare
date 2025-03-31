package com.example.farmcab.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmcab.viewmodel.AuthState
import com.example.farmcab.viewmodel.AuthViewModel

@Composable
fun Signup(navController: NavController, authViewModel: AuthViewModel) {
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  val context = LocalContext.current
  val authState by authViewModel.authState.collectAsState()

  Box(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Create Account", fontSize = 24.sp, fontWeight = FontWeight.Bold)

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
          } else {
            authViewModel.signUp(email, password)
          }
        },
        shape = RoundedCornerShape(12.dp),

      ) {
        Text("Sign Up")
      }


      Spacer(modifier = Modifier.height(8.dp))

      when (authState) {
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Success -> {
          Toast.makeText(context, "Signup Successful!", Toast.LENGTH_SHORT).show()
          navController.navigate("login")
        }
        is AuthState.Error -> Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
        else -> {}
      }

      TextButton(onClick = { navController.navigate("login") }) {
        Text("Already have an account? Log in")
      }
    }
  }
}
