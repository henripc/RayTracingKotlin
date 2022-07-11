import java.io.File

fun rayColor(r: Ray): Color {
    val unitDirection = Vec3.unitVector(r.direction())
    val t = 0.5 * (unitDirection.y() + 1.0)

    return Color(1.0, 1.0, 1.0) * (1.0 - t) + Color(0.5, 0.7, 1.0) * t
}

fun main() {
    // Image
    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()

    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Point3(0.0, 0.0, 0.0)
    val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    val vertical = Vec3(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)

    // Render
    val stringBuilder = StringBuilder()
    stringBuilder.append("P3\n$imageWidth $imageHeight\n255\n")

    for (j in imageHeight - 1 downTo 0) {
        System.err.println("Scanline's remaining: $j")
        for (i in 0 until imageWidth) {
            val u = i.toDouble() / (imageWidth - 1)
            val v = j.toDouble() / (imageHeight - 1)
            val r = Ray(origin, lowerLeftCorner + horizontal * u + vertical * v - origin)
            val colorPixel = rayColor(r)
            stringBuilder.append(ColorUtils.writeColor(colorPixel))
        }
    }

    File("images/image_3.ppm").writeText(stringBuilder.toString())
    System.err.println("Done")
}
