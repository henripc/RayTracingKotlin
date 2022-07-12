class Metal(private val albedo: Color, fuzz: Double) : Material {
    private val fuzz = if (fuzz < 1) fuzz else 1.0

    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        val reflected = Vec3.reflect(Vec3.unitVector(rIn.direction()), rec.normal)

        val ray = Ray(rec.p, reflected + Vec3.randomInUnitSphere() * this.fuzz)
        scattered.origin = ray.origin()
        scattered.direction = ray.direction()

        attenuation.e[0] = this.albedo.x()
        attenuation.e[1] = this.albedo.y()
        attenuation.e[2] = this.albedo.z()

        return Vec3.dot(scattered.direction(), rec.normal) > 0
    }
}
