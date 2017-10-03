package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        if (this.year != other.year) return this.year - other.year
        else if (this.month != other.month) return this.month - other.month
        else return this.dayOfMonth - other.dayOfMonth
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate)
    : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override fun contains(value: MyDate): Boolean = start <= value && value <= endInclusive

}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {

    var current: MyDate = dateRange.start

    override fun hasNext(): Boolean = current <= dateRange.endInclusive

    override fun next(): MyDate {
        val date = current
        current = current.nextDay()
        return date
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun TimeInterval.times(multiplicand: Int): RepeatedTimeInterval =
        RepeatedTimeInterval(this, multiplicand)

operator fun MyDate.plus(interval: TimeInterval): MyDate =
        addTimeIntervals(interval, 1)

operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate =
        addTimeIntervals(interval.ti, interval.n)