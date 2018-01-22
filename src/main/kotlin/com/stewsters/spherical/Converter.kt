package com.stewsters.spherical

import java.awt.image.BufferedImage
import java.io.File
import java.lang.Math.cos
import java.lang.Math.sin
import javax.imageio.ImageIO

data class Point(val x: Int, val y: Int)

fun main(args: Array<String>) {

    File("input").listFiles().filter { it.name.endsWith(".jpg") }.forEach {
        ImageIO.write(
                transform(ImageIO.read(it)),
                "jpg",
                File("output/${it.name.split(".")[0]}.jpg")
        )
    }

}


fun transform(input: BufferedImage): BufferedImage {
    val center = Point(input.width / 2, input.height / 2)
    val radius = minOf(center.x, center.y)

    val out = BufferedImage(1980, 512, BufferedImage.TYPE_INT_RGB)

    // Loop over each pixel in the new image, read the pixel value from the old one and set it
    for (x in 0 until out.width) {
        for (y in 0 until out.height) {

            val xPercent = x.toDouble() / out.width.toDouble()
            val yPercent = y.toDouble() / out.height.toDouble()

            val xOld = center.x + radius * yPercent * cos((xPercent * Math.PI * 2))
            val yOld = center.y + radius * yPercent * sin((xPercent * Math.PI * 2))

            val oldColor = input.getRGB(
                    xOld.toInt(),
                    yOld.toInt()
            )
            out.setRGB(x, y, oldColor)
        }
    }
    return out
}