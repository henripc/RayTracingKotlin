import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

typealias Point3 = Vec3
typealias Color = Vec3

class Vec3(e0: Double, e1: Double, e2: Double) {
    val e = doubleArrayOf(e0, e1, e2)

    constructor() : this(0.0, 0.0, 0.0)

    fun x(): Double = this.e[0]
    fun y(): Double = this.e[1]
    fun z(): Double = this.e[2]

    operator fun unaryMinus() = Vec3(-this.e[0], -this.e[1], -this.e[2])
    operator fun get(index: Int) = this.e[index]
    operator fun plus(v: Vec3) = Vec3(this.x() + v.x(), this.y() + v.y(), this.z() + v.z())
    operator fun minus(v: Vec3) = Vec3(this.x() - v.x(), this.y() - v.y(), this.z() - v.z())
    operator fun times(v: Vec3) = Vec3(this.x() * v.x(), this.y() * v.y(), this.z() * v.z())
    operator fun times(t: Double) = Vec3(t * this.x(), t * this.y(), t * this.z())
    operator fun div(t: Double) = this * (1 / t)
//    operator fun plusAssign(v: Vec3) {
//        this.e[0] += v.x()
//        this.e[1] += v.y()
//        this.e[2] += v.z()
//    }
    operator fun timesAssign(t: Double) {
        this.e[0] *= t
        this.e[1] *= t
        this.e[2] *= t
    }
    operator fun divAssign(t: Double) {
        this *= 1 / t
    }

    fun lengthSquared() = this.e[0] * this.e[0] + this.e[1] * this.e[1] + this.e[2] * this.e[2]
    fun length() = sqrt(this.lengthSquared())

    fun nearZero(): Boolean {
        // Return true if the vector is close to zero in all dimensions.
        val s = 1E-8
        return (abs(this.x()) < s) && (abs(this.y()) < s) && (abs(this.z()) < s)
    }

    companion object {
        fun dot(u: Vec3, v: Vec3) = u.x() * v.x() + u.y() * v.y() + u.z() * v.z()

        fun cross(u: Vec3, v: Vec3): Vec3 {
            return Vec3(u.y() * v.z() - u.z() * v.y(),
                        u.z() * v.x() - u.x() * v.z(),
                        u.x() * v.y() - u.y() * v.x())
        }

        fun unitVector(v: Vec3) = v / v.length()

        fun random() = Vec3(RtWeekend.randomDouble(), RtWeekend.randomDouble(), RtWeekend.randomDouble())
        fun random(min: Double, max: Double) = Vec3(RtWeekend.randomDouble(min, max), RtWeekend.randomDouble(min, max), RtWeekend.randomDouble(min, max))

        fun randomInUnitSphere() : Vec3 {
            while (true) {
                val p = random(-1.0, 1.0)
                if (p.lengthSquared() >= 1) continue

                return p
            }
        }

        fun randomUnitVector() = unitVector(randomInUnitSphere())
        fun reflect(v: Vec3, n: Vec3) = v - n * (2 * dot(v, n))

        fun refract(uv: Vec3, n: Vec3, etaIOverEtaT: Double): Vec3 {
            val cosTheta = min(dot(-uv, n), 1.0)
            val rOutPerp = (uv + n * cosTheta) * etaIOverEtaT
            val rOutParallel = n * (-sqrt(abs(1 - rOutPerp.lengthSquared())))

            return rOutPerp + rOutParallel
        }

        fun randomInUnitDisk(): Vec3 {
            while (true) {
                val p = Vec3(RtWeekend.randomDouble(-1.0, 1.0), RtWeekend.randomDouble(-1.0, 1.0), 0.0)
                if (p.lengthSquared() >= 1) continue

                return p
            }
        }
    }
}
