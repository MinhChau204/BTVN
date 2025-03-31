package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance() // Khởi tạo FirebaseAuth

        setContent {
            MaterialTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    LoginScreen { email, password ->
                        loginUser(email, password)
                    }
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(this, "Đăng nhập thành công: ${user?.email}", Toast.LENGTH_LONG).show()

                    // Chuyển sang màn hình chính sau khi đăng nhập
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Đóng LoginActivity
                } else {
                    Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Đăng nhập Firebase", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = { onLogin(email, password) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Đăng nhập")
        }
    }
}

