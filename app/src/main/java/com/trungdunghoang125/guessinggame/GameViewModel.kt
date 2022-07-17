package com.trungdunghoang125.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf(
        "Android",
        "Activity",
        "Fragment",
        "Jetpack",
        "Navigation",
        "Linear Layout",
        "Gradle",
        "View Model",
        "Binding"
    )
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuess = ""
    var incorrectGuess = ""
    var livesLeft = 8

    init {
        secretWordDisplay = deriveSecretWordDisplay()
    }

    fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    fun checkLetter(str: String): String {
        return when (correctGuess.contains(str)) {
            true -> str
            false -> "_"
        }
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuess += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuess += "$guess"
                livesLeft--
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay, true)

    fun isLost() = (livesLeft <= 0)

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost! Try again"
        message += " The word was $secretWord."
        return message
    }
}