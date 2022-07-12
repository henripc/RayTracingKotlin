interface Material {
    fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean
}
