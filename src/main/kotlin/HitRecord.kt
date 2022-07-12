class HitRecord {
    var p = Point3()
    var normal = Vec3()
    var t = 0.0
    var frontFace = false

    fun setFaceNormal(r: Ray, outwardNormal: Vec3) {
        this.frontFace = Vec3.dot(r.direction(), outwardNormal) < 0
        this.normal = if (this.frontFace) outwardNormal else -outwardNormal
    }
}
