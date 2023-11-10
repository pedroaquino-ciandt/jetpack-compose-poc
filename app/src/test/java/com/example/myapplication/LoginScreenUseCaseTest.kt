package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

class LoginScreenUseCaseTest {
    private val loginScreenUseCase = LoginScreenUseCase()
    private var errorMessage: String = ""
    @Test
    fun `Should return no error message When valid username and password`() {
        errorMessage = loginScreenUseCase.onValidate(userName = "testUserName", password = "password", onLoginClicked = {})

        assertEquals("", errorMessage)
    }
    @Test
    fun `Should return error message When username is empty`() {
        errorMessage = loginScreenUseCase.onValidate(userName = "", password = "password", onLoginClicked = {})

        assertEquals("Enter an username", errorMessage)
    }
    @Test
    fun `Should return error message When password is empty`() {
        errorMessage = loginScreenUseCase.onValidate(userName = "testUserName", password = "", onLoginClicked = {})

        assertEquals("Enter a password", errorMessage)
    }

    @Test
    fun `Should return error message When password and username are empty`() {
        errorMessage = loginScreenUseCase.onValidate(userName = "", password = "", onLoginClicked = {})

        assertEquals("Enter an username \n" +
                "Enter a password", errorMessage)
    }
}