import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class Dielectric(private val indexOfRefraction: Double): Material {
    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        attenuation.e[0] = 1.0
        attenuation.e[1] = 1.0
        attenuation.e[2] = 1.0

        val refractionRatio = if (rec.frontFace) 1.0 / this.indexOfRefraction else this.indexOfRefraction
        val unitDirection = Vec3.unitVector(rIn.direction())
        val cosTheta = min(Vec3.dot(-unitDirection, rec.normal), 1.0)
        val sinTheta = sqrt(1 - cosTheta * cosTheta)

        val cannotRefract = refractionRatio * sinTheta > 1.0
        val direction = if (cannotRefract || reflectance(cosTheta, refractionRatio) > RtWeekend.randomDouble()) {
            Vec3.reflect(unitDirection, rec.normal)
        } else {
            Vec3.refract(unitDirection, rec.normal, refractionRatio)
        }

        val ray = Ray(rec.p, direction)
        scattered.origin = ray.origin()
        scattered.direction = ray.direction()

        return true
    }

    companion object {
        private fun reflectance(cosine: Double, refIdx: Double): Double {
            // Use Schlick's approximation for reflectance.
            var r0 = (1 - refIdx) / (1 + refIdx)
            r0 *= r0

            return r0 + (1 - r0) * ((1 - cosine).pow(5))
        }
    }
}
