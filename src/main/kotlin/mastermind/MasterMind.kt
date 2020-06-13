package mastermind

 class MasterMind(code:String) {
     companion object{
         val regex = Regex("[A-F]{4}")
     }
    val code: String = if(isCodeIllegal(code)) throw IllegalCodeException() else code

    private fun isCodeIllegal(value: String?): Boolean =
        value == null || !regex.matches(value)

     fun guess(guess: String): Result {
        val positionPoints = getPositionPoints(guess)
         val valuePoints = getValuePoints(guess) - positionPoints
         return Result(positionPoints,valuePoints)
     }

     private fun getPositionPoints(guess: String): Int {
         var sum = 0
        for((index,c) in guess.withIndex())
            if(c == code[index])
                sum++
         return sum

     }

     private fun getValuePoints(guess: String): Int =
         guess.filter { code.contains(it) }.count()



 }
