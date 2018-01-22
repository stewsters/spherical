package com.stewsters.spherical

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamPanel
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver
import java.awt.image.BufferedImage
import java.lang.Math.cos
import java.lang.Math.sin
import javax.swing.JFrame

data class Point(val x: Int, val y: Int)

fun main(args: Array<String>) {

    Webcam.setDriver(V4l4jDriver())
    val frame = JFrame("demo")

    frame.add(WebcamPanel(Webcam.getDefault()))
    frame.pack()
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

//    File("input").listFiles().filter { it.name.endsWith(".jpg") }.forEach {
//        ImageIO.write(
//                transform(ImageIO.read(it)),
//                "jpg",
//                File("output/${it.name.split(".")[0]}.jpg")
//        )
//    }

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