class Lambertian(private val albedo: Color) : Material {
    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        var scatterDirection = rec.normal + Vec3.randomUnitVector()

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero()) {
            scatterDirection = rec.normal
        }

        val ray = Ray(rec.p, scatterDirection)
        scattered.origin = ray.origin()
        scattered.direction = ray.direction()

        attenuation.e[0] = this.albedo.x()
        attenuation.e[1] = this.albedo.y()
        attenuation.e[2] = this.albedo.z()

        return true
    }
}
