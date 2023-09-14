
import kotlin.test.*

class TestDate {
    @Test
    fun testDate() {
        val sut = Date(2023, 4, 1)
        assertEquals(2023, sut.year, "error in year")
        assertEquals(4, sut.month)
        assertEquals(1, sut.day)
    }
    @Test
    fun `create Date without day or month` () {
        val sut = Date(2023)
        assertEquals(2023, sut.year)
        assertEquals(1, sut.month)
        assertEquals(1, sut.day)
        val sut2 = Date(2023, 4)
        assertEquals(2023, sut2.year)
        assertEquals(4, sut2.month)
    }
    @Test fun `verify leap year`() {
        val sut = Date(2020, 2, 29)
        assertTrue(sut.leapYear)
        assertFalse(Date(2021).leapYear)
        assertTrue(2020.isLeapYear)
    }
    @Test fun `last day of month`() {
        val sut = Date(2020, 2)
        assertEquals(29,sut.lastDayOfMonth)
        val sut2 = Date(2023,1,1)
        assertEquals(31,sut2.lastDayOfMonth)
    }
    @Test fun `try create invalid dates`(){
        val ex = assertFailsWith<IllegalArgumentException> {
            Date(2020, 13, 1)
        }
        assertEquals("month must be in range 1..12", ex.message)
        val ex2 = assertFailsWith<IllegalArgumentException> {
            Date(2020, 2, 30)
        }
        assertEquals("day must be in range 1..29", ex2.message)
        val ex3 = assertFailsWith<IllegalArgumentException> {
            Date(1400, 2, 29)
        }
        assertEquals("year must be in range 1582..3000", ex3.message)
    }
    @Test fun `Add days to dates`() {
        val sut = Date(2023, 9, 14)
        val res = sut + 2
        assertEquals(16, res.day)
        val res2 = sut + 20
        assertEquals(4, res2.day)
        assertEquals(10, res2.month)
        val res3 = sut + 366
        assertEquals(14, res3.day)
        assertEquals(9, res3.month)
        assertEquals(2024, res3.year)
        val res4 = 2 + sut
        assertEquals(16, res4.day)
        val res5 = sut + 350000   // Throw StackOverflow if not tailrec
    }
}