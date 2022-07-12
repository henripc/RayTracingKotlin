import kotlin.math.tan

class Camera(lookFrom: Point3,
             lookAt: Point3,
             vUp: Vec3,
             vFov: Double,  // vFov = vertical field-of-view in degrees
             aspectRatio: Double,
             aperture: Double,
             focusDist: Double,
) {
    private val origin: Point3
    private val lowerLeftCorner: Point3
    private val horizontal: Vec3
    private val vertical: Vec3
    private val u: Vec3
    private val v: Vec3
    private val w: Vec3
    private val lensRadius: Double

    init {
        val theta = RtWeekend.degreesToRadians(vFov)
        val h = tan(theta / 2)
        val viewportHeight = 2.0 * h
        val viewportWidth = aspectRatio * viewportHeight

        this.w = Vec3.unitVector(lookFrom - lookAt)
        this.u = Vec3.unitVector(Vec3.cross(vUp, this.w))
        this.v = Vec3.cross(this.w, this.u)

        this.origin = lookFrom
        this.horizontal = this.u * (viewportWidth * focusDist)
        this.vertical = this.v * (viewportHeight * focusDist)
        this.lowerLeftCorner = this.origin - this.horizontal / 2.0 - this.vertical / 2.0 - this.w * focusDist

        this.lensRadius = aperture / 2
    }

    fun getRay(s: Double, t: Double) : Ray {
        val rd = Vec3.randomInUnitDisk() * this.lensRadius
        val offset = this.u * rd.x() + this.v * rd.y()

        return Ray(this.origin + offset, this.lowerLeftCorner + this.horizontal * s + this.vertical * t - this.origin - offset)
    }
}
