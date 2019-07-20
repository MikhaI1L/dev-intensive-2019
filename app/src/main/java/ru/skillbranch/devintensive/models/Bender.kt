package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status : Status = Status.NORMAL, var question: Question = Question.NAME, var wrongCount : Int = 0) {

    fun askQuestion() : String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer : String) : Pair<String, Triple<Int, Int, Int>> {

        val (msg, isCorrect) = validateAnswer(answer)
        return when {
            question.answers.contains(answer.toLowerCase()) && isCorrect-> {
                question = question.nextQuestion()
                "$msg\n${question.question}" to status.color
            }
            question == Question.IDLE ->
                question.question to status.color
            !isCorrect ->
                "$msg\n${question.question}" to status.color
            wrongCount > 1 -> {
                this.resetBender()
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            else -> {
                status = status.nextStatus()
                wrongCount++
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    private fun validateAnswer(answer : String) : Pair<String, Boolean> {

        return when {
            question == Question.NAME && answer[0].isLowerCase()->
                        "Имя должно начинаться с заглавной буквы" to false
            question == Question.PROFESSION && !answer[0].isLowerCase() ->
                        "Профессия должна начинаться со строчной буквы" to false
            question == Question.MATERIAL && answer.contains(Regex("[0-9]")) ->
                        "Материал не должен содержать цифр" to false
            question == Question.BDAY && !answer.contains(Regex("^[0-9]+$")) ->
                        "Год моего рождения должен содержать только цифры" to false
            question == Question.SERIAL && !answer.contains(Regex("^[0-9]{7}$")) ->
                "Серийный номер содержит только цифры, и их 7" to false
            else -> "Отлично - ты справился" to true
        }
    }

    private fun resetBender() {
        status = Status.NORMAL
        question = Question.NAME
        wrongCount = 0
    }

    enum class Status(val color : Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus() : Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal +1]
            } else {
                values()[0]
            }
        }
    }

    enum class  Question(val question : String, val answers : List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion() : Question

    }
}