package mastermind

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MasterMindTest {
    private lateinit var masterMind: MasterMind

    @BeforeEach
    fun setUp() {
        val code = "ABCD"
        masterMind = MasterMind(code)
    }

    @Test
    fun whenCreateCode_CodeStaysTheSame() {

        assertEquals("ABCD", masterMind.code)

    }


    @Test
    fun whenPassedTotallyWrongCode_GiveResultZeroZero(){

        val guess = "FFFF"
        val result = masterMind.guess(guess)
        assertEquals(0,result.positionPoints)
        assertEquals(0,result.valuePoints)
    }
    @Test
    fun whenPassedOneGoodInWrongPlaceGiveOneZero(){
        val guess = "FBBB"
        val result = masterMind.guess(guess)
        assertEquals(0,result.positionPoints)
        assertEquals(1,result.valuePoints)
    }
    @Test
    fun whenCodeShouldGet2PosAndOneVal(){
        val guess = "ABDE"
        val result = masterMind.guess(guess)
        assertEquals(2,result.positionPoints)
        assertEquals(1,result.valuePoints)
    }
    @Test
    fun whenCorrectCode_GiveFourPosition(){
        val guess = "ABCD"
        val result = masterMind.guess(guess)
        assertEquals(4,result.positionPoints)
        assertEquals(0,result.valuePoints)
    }
}