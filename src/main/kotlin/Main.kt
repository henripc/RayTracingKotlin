import java.io.File
import kotlin.system.measureTimeMillis

fun randomScene(): HittableList {
    val world = HittableList()

    val groundMaterial = Lambertian(Color(0.5, 0.5, 0.5))
    world.add(Sphere(Point3(0.0, -1000.0, 0.0), 1000.0, groundMaterial))

    for (a in -11..10) {
        for (b in -11..10) {
            val chooseMaterial = RtWeekend.randomDouble()
            val center = Point3(a + 0.9 * RtWeekend.randomDouble(), 0.2, b + 0.9 * RtWeekend.randomDouble())

            if ((center - Point3(4.0, 0.2, 0.0)).length() > 0.9) {
                var sphereMaterial: Material

                if (chooseMaterial < 0.8) {
                    // diffuse
                    val albedo = Color.random() * Color.random()
                    sphereMaterial = Lambertian(albedo)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else if (chooseMaterial < 0.95) {
                    // metal
                    val albedo = Color.random(0.5, 1.0)
                    val fuzz = RtWeekend.randomDouble(0.0, 0.5)
                    sphereMaterial = Metal(albedo, fuzz)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else {
                    // glass
                    sphereMaterial = Dielectric(1.5)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                }
            }
        }
    }

    val material1 = Dielectric(1.5)
    world.add(Sphere(Point3(0.0, 1.0, 0.0), 1.0, material1))

    val material2 = Lambertian(Color(0.4, 0.2, 0.1))
    world.add(Sphere(Point3(-4.0, 1.0, 0.0), 1.0, material2))

    val material3 = Metal(Color(0.7, 0.6, 0.5), 0.0)
    world.add(Sphere(Point3(4.0, 1.0, 0.0), 1.0, material3))

    return world
}

fun rayColor(r: Ray, world: HittableList, depth: Int): Color {
    val rec = HitRecord()

    // If we've exceeded the ray bounce limit, no more light is gathered.
    if (depth <= 0) return Color(0.0, 0.0, 0.0)

    if (world.hit(r, 0.001, RtWeekend.INFINITY, rec)) {
        val scattered = Ray()
        val attenuation = Color()

        if (rec.material!!.scatter(r, rec, attenuation, scattered)) {
            return attenuation * rayColor(scattered, world, depth - 1)
        }

        return Color(0.0, 0.0, 0.0)
    }

    val unitDirection = Vec3.unitVector(r.direction())
    val t = 0.5 * (unitDirection.y() + 1.0)

    return Color(1.0, 1.0, 1.0) * (1.0 - t) + Color(0.5, 0.7, 1.0) * t
}

fun main() {
    val executionTime = measureTimeMillis {
        // Image
        val aspectRatio = 3.0 / 2.0
        val imageWidth = 1200
        val imageHeight = (imageWidth / aspectRatio).toInt()
        val samplesPerPixel = 500
        val maxDepth = 50

        // World
        val world = randomScene()

        // Camera
        val lookFrom = Point3(13.0, 2.0, 3.0)
        val lookAt = Point3(0.0, 0.0, 0.0)
        val vUp = Vec3(0.0, 1.0, 0.0)
        val distToFocus = 10.0
        val aperture = 0.1

        val camera = Camera(lookFrom, lookAt, vUp, 20.0, aspectRatio, aperture, distToFocus)

        // Render
        val stringBuilder = StringBuilder()
        stringBuilder.append("P3\n$imageWidth $imageHeight\n255\n")

        for (j in imageHeight - 1 downTo 0) {
            System.err.println("Scanline's remaining: $j")
            for (i in 0 until imageWidth) {
                var pixelColor = Color(0.0, 0.0, 0.0)
                for (s in 0 until samplesPerPixel) {
                    val u = (i + RtWeekend.randomDouble()) / (imageWidth - 1)
                    val v = (j + RtWeekend.randomDouble()) / (imageHeight - 1)
                    val r = camera.getRay(u, v)
                    pixelColor += rayColor(r, world, maxDepth)
                }
                stringBuilder.append(ColorUtils.writeColor(pixelColor, samplesPerPixel))
            }
        }

        File("images/image_test.ppm").writeText(stringBuilder.toString())
    }

    System.err.println("Done")
    System.err.println("Time of Execution: $executionTime ms")
    System.err.println("Time of Execution: ${executionTime / 1000} seconds")
    System.err.println("Time of Execution: ${executionTime / 1000 / 60} minutes")
    System.err.println("Time of Execution: ${executionTime / 1000 / 60 / 60} hours")
}
