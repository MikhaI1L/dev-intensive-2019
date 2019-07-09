package ru.skillbranch.devintensive.utils

object Utils {

    private val charMap = mapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya")

    fun parseFullName(fullName : String?) : Pair<String?, String?> {

        val parts : List<String>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        firstName = if(firstName == null || firstName.isEmpty()) null else firstName
        var lastName = parts?.getOrNull(1)
        lastName = if(lastName == null || lastName.isEmpty()) null else lastName

        return firstName to lastName //Pair
    }

    fun transliteration(payload: String, divider : String = " ") :String{
        var resStr = ""

        for (i in 0 until payload.length) {
            resStr +=
                    when (payload[i].toLowerCase()) {
                            ' '     -> divider
                            else    ->  if(charMap.get(payload[i].toLowerCase().toString()) == null)
                                            payload[i].toString()
                                        else if(payload[i].isLowerCase())
                                            charMap.get(payload[i].toLowerCase().toString())
                                        else
                                            charMap.get(payload[i].toLowerCase().toString()).toString().capitalize()
                }

        }

        return resStr
    }

    fun toInitials(firstName : String?, lastName : String?) : String? {

        return when {
                     firstName?.trimIndent().isNullOrEmpty() &&
                     lastName?.trimIndent().isNullOrEmpty() -> null
                     else -> (
                             if(firstName?.trimIndent().isNullOrEmpty()) ""
                             else firstName!!.trimIndent()[0].toString().toUpperCase()
                         +   if(lastName?.trimIndent().isNullOrEmpty()) ""
                             else lastName!!.trimIndent()[0].toString().toUpperCase())
        }
    }
}