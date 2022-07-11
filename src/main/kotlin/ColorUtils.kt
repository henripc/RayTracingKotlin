class ColorUtils {
    companion object {
        fun writeColor(pixelColor: Color): String {
            // Write the translated [0,255] value of each color component.
            return "${(255.999 * pixelColor.x()).toInt()} ${(255.999 * pixelColor.y()).toInt()} ${(255.999 * pixelColor.z()).toInt()}\n"
        }
    }
}
