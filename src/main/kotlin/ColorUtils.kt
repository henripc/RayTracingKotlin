import kotlin.math.sqrt

class ColorUtils {
    companion object {
        fun writeColor(pixelColor: Color, samplesPerPixel: Int): String {
            var r = pixelColor.x()
            var g = pixelColor.y()
            var b = pixelColor.z()

            // Divide the color by the number of samples and gamma-correct for gamma=2.
            val scale = 1.0 / samplesPerPixel
            r = sqrt(scale * r)
            g = sqrt(scale * g)
            b = sqrt(scale * b)

            // Write the translated [0,255] value of each color component.
            return "${(256 * RtWeekend.clamp(r, 0.0, 0.999)).toInt()} ${(256 * RtWeekend.clamp(g, 0.0, 0.999)).toInt()} ${(256 * RtWeekend.clamp(b, 0.0, 0.999)).toInt()}\n"
        }
    }
}
