

private const val DAY_BITS = 5
private const val MONTH_BITS = 4

@JvmInline
value class Date private constructor(private val bits: Int) {
    constructor(year: Int, month: Int = 1, day: Int = 1) : this(
        year shl (DAY_BITS+MONTH_BITS) or (month shl DAY_BITS) or day
    ) {
        require(year in 1582..3000) { "year must be in range 1582..3000" }
        require(month in 1..12) { "month must be in range 1..12" }
        require(day in 1..lastDayOfMonth) { "day must be in range 1..$lastDayOfMonth" }
    }
    val day: Int get() = bits and ((1 shl DAY_BITS)-1)
    val month: Int get() = (bits shr DAY_BITS) and ((1 shl MONTH_BITS)-1)
    val year: Int get() = bits shr (DAY_BITS+MONTH_BITS)

//    override fun equals(other: Any?) = other is Date && bits == other.bits
//    override fun hashCode() = bits

    override fun toString() : String = "$year-%02d-%02d".format(month, day)
        // this::class.simpleName + "@" + hashCode().toString(16)

    operator fun compareTo(other: Date): Int = bits - other.bits
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

