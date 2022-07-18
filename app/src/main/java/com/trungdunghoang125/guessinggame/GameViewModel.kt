package com.trungdunghoang125.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val secretWordDisplay = MutableLiveData<String>()
    var correctGuess = ""
    val incorrectGuess = MutableLiveData<String>("")
    val livesLeft = MutableLiveData<Int>(8)

    init {
        secretWordDisplay.value = deriveSecretWordDisplay()
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
                secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                incorrectGuess.value += "$guess"
                livesLeft.value = livesLeft.value?.minus(1)
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    fun isLost() = livesLeft.value ?: 0 <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost! Try again"
        message += " The word was $secretWord."
        return message
    }
}