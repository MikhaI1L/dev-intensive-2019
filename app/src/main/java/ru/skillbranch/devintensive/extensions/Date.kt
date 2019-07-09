package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern : String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time +=
        when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff() : String {
    val parsedVal : String
    val diffVal : Long = Date().time - this.time

    parsedVal =
            when {
                abs(diffVal) in 0..SECOND                    -> "только что"
                abs(diffVal) in SECOND..(45 * SECOND)        ->(if(diffVal > 0) "" else "через ") + "несколько секунд" + if(diffVal > 0) " назад" else ""
                abs(diffVal) in (45 * SECOND)..(75 * SECOND) -> if(diffVal > 0) "минуту назад" else "через минуту"
                abs(diffVal) in (75 * SECOND)..(45 * MINUTE) ->(if(diffVal > 0) "" else "через ") + TimeUnits.MINUTE.plural(abs(diffVal/MINUTE).toInt()) + if(diffVal > 0) " назад" else ""
                abs(diffVal) in (45 * MINUTE)..(75 * MINUTE) -> if(diffVal > 0) "час назад" else "через час"
                abs(diffVal) in (75 * MINUTE)..(22 * HOUR)   ->(if(diffVal > 0) "" else "через ") + TimeUnits.HOUR.plural(abs(diffVal/HOUR).toInt()) + if(diffVal > 0) " назад" else ""
                abs(diffVal) in (22 * HOUR)..(26 * HOUR)     -> if(diffVal > 0) "день назад" else "через день"
                abs(diffVal) in (26 * HOUR)..(360 * DAY)     ->(if(diffVal > 0) "" else "через ") + TimeUnits.DAY.plural(abs(diffVal/DAY).toInt()) + if(diffVal > 0) " назад" else ""
                abs(diffVal) > 360 * DAY                     -> if(diffVal > 0) "более года назад" else "более чем через год"
                else -> "impossible!!!"
            }
    return parsedVal
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return value.toString() +
                    when {
                value%10 == 1 && value%100 != 11            -> " сукунду"
                value%10 in 2..4 && value%100 !in 12..14    -> " секунды"
                else                                        -> " секунд"
            }
        }
    }
    ,
    MINUTE {
        override fun plural(value: Int): String {
            return value.toString() +
                    when {
                value%10 == 1 && value%100 != 11            -> " минуту"
                value%10 in 2..4 && value%100 !in 12..14    -> " минуты"
                else                                        -> " минут"
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return value.toString() +
                    when {
                value%10 == 1 && value%100 != 11            -> " час"
                value%10 in 2..4 && value%100 !in 12..14    -> " часа"
                else                                        -> " часов"
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return value.toString() +
                    when {
                value%10 == 1 && value%100 != 11            -> " день"
                value%10 in 2..4 && value%100 !in 12..14    -> " дня"
                else                                        -> " дней"
            }
        }
    }
    ;

    abstract fun plural(value : Int) : String
}