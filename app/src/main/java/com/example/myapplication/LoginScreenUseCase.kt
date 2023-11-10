package com.example.myapplication

class LoginScreenUseCase {
    fun onValidate(
        userName: String,
        password: String,
        onLoginClicked: () -> Unit
    ): String {
        if (userName.isEmpty() && password.isEmpty()) {
            return "Enter an username \nEnter a password"
        } else if (userName.isEmpty()) {
            return "Enter an username"
        } else if (password.isEmpty()) {
            return "Enter a password"
        }
        onLoginClicked.invoke()
        return ""
    }
}