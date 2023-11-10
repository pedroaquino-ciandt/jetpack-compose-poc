package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.coroutines.jvm.internal.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClicked: () -> Unit, modifier: Modifier = Modifier
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }


    Column(
        modifier = modifier
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        Text(
            text = stringResource(id = R.string.sign_in),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it },
            placeholder = { Text(text = stringResource(id = R.string.username_name)) },
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = stringResource(id = R.string.password_name)) },
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                errorMessage = LoginScreenUseCase().onValidate(
                    userName,
                    password,
                    onLoginClicked
                )
            },
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        errorMessage?.let {
            Snackbar(modifier = Modifier.padding(16.dp), action = {
                TextButton(onClick = { errorMessage = null }) {
                    Text("Dismiss")
                }
            }) {
                Text(text = errorMessage!!)
            }
        }
    }
}

