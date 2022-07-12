class Ray(private val origin: Point3, private val direction: Vec3) {
    constructor() : this(Point3(), Vec3())

    fun origin() = this.origin
    fun direction() = this.direction
    fun at(t: Double) = this.origin + this.direction * t
}
