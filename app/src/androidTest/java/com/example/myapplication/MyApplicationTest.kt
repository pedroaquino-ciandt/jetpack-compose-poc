package com.example.myapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyApplicationTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private var continueButton = String()
    private var greetingDescription = String()

    @Before
    fun setUp() {
        continueButton = rule.activity.getString(R.string.continue_button)
        greetingDescription = rule.activity.getString(R.string.greeting_description)
    }

    @Test
    fun should_display_continue_button_WHEN_app_is_loaded_GIVEN_onboarding_page() {
        rule.setContent { MyApp() }
        rule.onNode(hasTestTag(continueButton)).assertIsDisplayed()
    }
    @Test
    fun shouldDisplaySignInWhenContinueButtonIsClickedGivenOnboardingScreen() {
        rule.setContent { MyApp() }
        rule.onNode(hasTestTag(continueButton)).performClick()
        rule.onNodeWithText("Sign In").assertIsDisplayed()
    }
    @Test
    fun shouldDisplayGreetingsWhenLoginIsCompleteGivenValidUser() {
        rule.setContent { MyApp() }
        rule.onNode(hasTestTag(continueButton)).performClick()
        rule.onNodeWithText("Username").performTextInput("testUser")
        rule.onNodeWithText("Password").performTextInput("password")
        rule.onNodeWithText("Login").performClick()
        rule.onNodeWithText("Greetings").assertIsDisplayed()
    }
    @Test
    fun shouldShowErrorWhenWhenUserNameIsEmptyGivenLoginScreen() {
        rule.setContent { LoginScreen({}) }
        rule.onNodeWithText("Username").performTextInput("testUser")
        rule.onNodeWithText("Login").performClick()
        rule.onNodeWithText("Enter a password").assertIsDisplayed()
    }
    @Test
    fun shouldShowErrorWhenWhenPasswordIsEmptyGivenLoginScreen() {
        rule.setContent { LoginScreen({}) }
        rule.onNodeWithText("Password").performTextInput("password")
        rule.onNodeWithText("Login").performClick()
        rule.onNodeWithText("Enter an username").assertIsEnabled()
    }
    @Test
    fun shouldShowErrorWhenWhenPasswordAndUsernameAreEmptyGivenLoginScreen() {
        rule.setContent { LoginScreen({}) }
        rule.onNodeWithText("Login").performClick()
        rule.onNodeWithText("Enter an username \nEnter a password").assertIsDisplayed()
    }
    @Test
    fun shouldDisplayGreetingsDescriptionWhenGreetingsDescriptionIsEnabledGivenGreetingsPage() {
        rule.setContent { Greetings() }
        rule.onAllNodesWithContentDescription("Show more").onFirst().performClick()
        rule.onNode(hasTestTag(greetingDescription))
            .assertIsDisplayed()
            .assertTextContains("Composem ipsum color sit lazy, ")
    }
    @Test
    fun shouldNotDisplayGreetingsDescriptionWhenGreetingsDescriptionIsNotEnabledGivenGreetingsPage() {
        rule.setContent { Greetings() }
        rule.onNode(hasTestTag(greetingDescription)).assertDoesNotExist()
    }

    @Test
    fun shouldNotDisplayGreetingsDescriptionWhenGreetingsDescriptionIsDisabledGivenGreetingsPage() {
        rule.setContent { Greetings() }
        rule.onAllNodesWithContentDescription("Show more").onFirst().performClick()
        rule.onAllNodesWithContentDescription("Show less").onFirst().performClick()
        rule.onNode(hasTestTag(greetingDescription)).assertDoesNotExist()
    }
}