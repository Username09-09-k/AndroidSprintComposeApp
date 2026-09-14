package com.example.androidsprintcomposeapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS

@Composable
fun RegistrationScreen() {

    var userEmail by remember { mutableStateOf("") }
    var isEmailFormatValid by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }

    val testEmail = "test@gmail.com"

    Spacer(Modifier.height(30.dp))
    StudyAppHeader(title = "Email screen", subtitle = "Please enter your email")
    Spacer(Modifier.height(50.dp))
    CheckEmailField(
        email = userEmail,
        isEmailValid = isEmailFormatValid,
        onEmailChange = {
            userEmail = it
            isEmailFormatValid = EMAIL_ADDRESS.matcher(it).matches()
            validationMessage = if (!isEmailFormatValid) {
                "Wrong email address"
            } else {
                ""
            }
        },
        onClearClicked = {
            userEmail = ""
            isEmailFormatValid = true
            validationMessage = ""
        }
    )
    Spacer(Modifier.height(30.dp))
    PrimaryButton(
        text = "Register",
        onRegisterClick = { it: String ->
            validationMessage =
                if (userEmail.isEmpty() || !isEmailFormatValid) {
                    "Wrong email"
                } else if (userEmail == testEmail) {
                    "Email already exists"
                } else {
                    "Registration successful"
                }

            Log.i("Button", "Button pressed $it")
        }
    )
    Spacer(Modifier.height(30.dp))
    Text(
        text = validationMessage
    )
}

@Composable
fun CheckEmailField(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,
) {


    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
//            textState = it
//            errorState = if (
//                EMAIL_ADDRESS.matcher(it).matches()) "" else "Wrong email"
        },
        shape = RoundedCornerShape(13.dp),
        placeholder = {
            Text(
                text = "example@mail.com"
            )
        },
        singleLine = true,
        label = {
            Text(
                text = if (isEmailValid) "Email" else "Wrong email"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClicked()
//                    textState = ""
//                    errorState = ""
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                )
            }
        },
        isError = !isEmailValid && email.isNotEmpty(),
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onRegisterClick: (String) -> Unit,
) {
    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = {
            onRegisterClick(text)
        },
        modifier = Modifier
            .height(56.dp)
            .padding(40.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 22.sp,
        )
    }
}