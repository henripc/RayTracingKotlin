import kotlin.math.sqrt

class Sphere(private val center: Point3, private val radius: Double) : Hittable {
    override fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean {
        val oc = r.origin() - this. center
        val a = r.direction().lengthSquared()
        val halfB = Vec3.dot(oc, r.direction())
        val c = oc.lengthSquared() - this.radius * this.radius
        val discriminant = halfB * halfB - a * c

        if (discriminant < 0) return false
        val sqrtD = sqrt(discriminant)

        // Find the nearest root that lies in the acceptable range.
        var root = (-halfB - sqrtD) / a
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtD) / a
            if (root < tMin || tMax < root) {
                return false
            }
        }

        rec.t = root
        rec.p = r.at(rec.t)
        val outwardNormal = (rec.p - this.center) / this.radius
        rec.setFaceNormal(r, outwardNormal)

        return true
    }
}
