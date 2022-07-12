import kotlin.random.Random

class RtWeekend {
    companion object {
        // Constants
        const val INFINITY = Double.POSITIVE_INFINITY
        private const val PI = 3.141592653589793

        // Utility Functions
        fun degreesToRadians(degrees: Double) = degrees * PI / 180
        fun randomDouble() = Random.nextDouble()    // Returns a random real in [0,1).
        fun randomDouble(min: Double, max: Double) = min + (max - min) * randomDouble()     // Returns a random real in [min,max).
        fun clamp(x: Double, min: Double, max: Double) : Double {
            if (x < min) return min
            if (x > max) return max

            return x
        }
    }
}
