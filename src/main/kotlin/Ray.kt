class Ray(origin: Point3, direction: Vec3) {
    private val orig = origin
    private val dir = direction

    constructor() : this(Point3(), Vec3())

    fun origin() = this.orig
    fun direction() = this.dir
    fun at(t: Double) = this.orig + this.dir * t
}
