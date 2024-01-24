package com.example.myapplication

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.StateRestorationTester
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
    var rule = createAndroidComposeRule<ComponentActivity>()
    private var continueButton = String()
    private var greetingDescription = String()

    @Before
    fun setUp() {
        continueButton = rule.activity.getString(R.string.continue_button)
        greetingDescription = rule.activity.getString(R.string.greeting_description)
    }

    @Test
    fun shouldDisplayContinueButton_WhenAppIsLoaded_GivenOnboardingPage() {
        rule.setContent { MyApp() }

        rule.onNode(hasTestTag(continueButton))
            .assertIsDisplayed()
//            .assertHeightIsEqualTo(40.dp)
            .assertHasClickAction()
//            .assertWidthIsAtLeast(105.dp)
//            .assertPositionInRootIsEqualTo(144.dp, 369.dp)
    }
    @Test
    fun shouldDisplaySignIn_WhenContinueButtonIsClicked_GivenOnboardingScreen() {
        //Arrange
        rule.setContent { MyApp() }

        //Act
        rule.onNode(hasTestTag(continueButton)).performClick()

        //Assert
        rule.onNodeWithText("Sign In").assertIsDisplayed()
    }
    @Test
    fun shouldDisplayGreetings_WhenLoginIsComplete_GivenValidUser() {
        rule.setContent { MyApp() }

        rule.onNode(hasTestTag(continueButton)).performClick()
        rule.onNodeWithText("Username").performTextInput("testUser")
        rule.onNodeWithText("Password").performTextInput("password")
        rule.onNodeWithText("Login").performClick()

        rule.onNodeWithText("Greetings").assertIsDisplayed()
    }
    @Test
    fun shouldShowError_WhenUserNameIsEmpty_GivenLoginScreen() {
        rule.setContent { LoginScreen({}) }

        rule.onNodeWithText("Username").performTextInput("testUser")
        rule.onNodeWithText("Login").performClick()

        rule.onNodeWithText("Enter a password").assertIsDisplayed()
    }
    @Test
    fun shouldShowError_WhenPasswordIsEmpty_GivenLoginScreen() {
        rule.setContent { LoginScreen({}) }

        rule.onNodeWithText("Password").performTextInput("password")
        rule.onNodeWithText("Login").performClick()

        rule.onNodeWithText("Enter an username").assertIsEnabled()
    }
    @Test
    fun shouldShowError_WhenPasswordAndUsernameAreEmpty_GivenLoginScreen() {
        rule.setContent { LoginScreen({}) }

        rule.onNodeWithText("Login").performClick()

        rule.onNodeWithText("Enter an username \nEnter a password").assertIsDisplayed()
    }
    @Test
    fun shouldDisplayGreetingsDescription_WhenGreetingsDescriptionIsEnabled_GivenGreetingsPage() {
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

    @Test
    fun shouldDisplayErrorMessageWhenStateIsRestoredGivenSignInPage() {
        val restorationTester = StateRestorationTester(rule)
        restorationTester.setContent { LoginScreen({}) }

        rule.onNodeWithText("Login").performClick()
        restorationTester.emulateSavedInstanceStateRestore()

        rule.onNodeWithText("Enter an username \nEnter a password").assertIsDisplayed()
    }
}