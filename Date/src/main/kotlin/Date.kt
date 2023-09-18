

class Date(val year: Int, val month: Int = 1, val day: Int = 1): Any() {
    init {
        require(year in 1582..3000) { "year must be in range 1582..3000" }
        require(month in 1..12) { "month must be in range 1..12" }
        require(day in 1..lastDayOfMonth) { "day must be in range 1..$lastDayOfMonth" }
    }

    override fun equals(other: Any?) = other is Date &&
        year == other.year && month == other.month && day == other.day

    override fun hashCode() = year * (12*31) + month * 31 + day

    operator fun compareTo(other: Date): Int =
        when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> day - other.day
        }
}

val Date.leapYear get() = year.isLeapYear

val Int.isLeapYear: Boolean
    get() = this % 4 == 0 && this % 100 != 0 || this % 400 == 0

private val daysOfMonth = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

val Date.lastDayOfMonth: Int
    get() = if (month == 2 && leapYear) 29 else daysOfMonth[month - 1]

operator fun Date.plus(days: Int) = addDays(days)
operator fun Int.plus(date: Date) = date.addDays(this)

tailrec fun Date.addDays(days: Int): Date {
    val d = day + days
    return if (d <= lastDayOfMonth)
        Date(year, month, d)
    else {
        val nextDate = if (month < 12)
            Date(year, month + 1, 1)
        else
            Date(year + 1, 1, 1)
        nextDate.addDays(d - lastDayOfMonth - 1)
    }
}

