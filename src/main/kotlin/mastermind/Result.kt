package mastermind

class Result(positionPoints: Int, valuePoints: Int) {

    val positionPoints: Int = if (arePointsValid(positionPoints, valuePoints)) positionPoints
    else throw IllegalResultException()

    val valuePoints: Int = if (arePointsValid(positionPoints, valuePoints)) valuePoints
    else throw IllegalResultException()



    private fun arePointsValid(positionPoints: Int, valuePoints: Int): Boolean =
        valuePoints+positionPoints in 0..4
}