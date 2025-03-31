package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        setContent {
            MaterialTheme {
                if (user != null) {
                    MainScreen(user.email ?: "Không có email") {
                        logoutUser()
                    }
                } else {
                    Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun logoutUser() {
        mAuth.signOut()
        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

@Composable
fun MainScreen(email: String, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Xin chào, $email!", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Đăng xuất")
        }
    }
}
