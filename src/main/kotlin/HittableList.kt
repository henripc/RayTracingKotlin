class HittableList() : Hittable {
    private val objects: MutableList<Hittable> = ArrayList()

    constructor(obj: Hittable) : this() {
        this.add(obj)
    }

    fun clear() = this.objects.clear()
    fun add(obj: Hittable) = this.objects.add(obj)

    override fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean {
        val tempRec = HitRecord()
        var hitAnything = false
        var closestSoFar = tMax

        for (obj in this.objects) {
            if (obj.hit(r, tMin, closestSoFar, tempRec)) {
                hitAnything = true
                closestSoFar = tempRec.t
                rec.p = tempRec.p
                rec.normal = tempRec.normal
                rec.material = tempRec.material
                rec.t = tempRec.t
                rec.frontFace = tempRec.frontFace
            }
        }

        return hitAnything
    }
}
