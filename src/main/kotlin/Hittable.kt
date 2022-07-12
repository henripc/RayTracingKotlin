interface Hittable {
    fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean
}
