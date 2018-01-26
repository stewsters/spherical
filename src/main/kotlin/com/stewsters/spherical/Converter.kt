package com.stewsters.spherical

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver
import spark.kotlin.Http
import spark.kotlin.ignite
import java.awt.image.BufferedImage
import java.lang.Math.cos
import java.lang.Math.sin

data class Point(val x: Int, val y: Int)

var center:Double = 0.0;

fun main(args: Array<String>) {

    Webcam.setDriver(V4l4jDriver())

    val second = 1000000000
    val webcam = Webcam.getDefault()

    if (!webcam.isOpen) {
        webcam.open()
    }


    val http: Http = ignite()
    http.port(5805)
    http.get("/") {
        center
    }

    var lastTimeNs = System.nanoTime()
    var frames = 0;

    while (true) {
        val currentTime = System.nanoTime()
        frames++;
        if (currentTime - lastTimeNs >= second) {
            println("fps " + frames)
            frames = 0
            lastTimeNs += second
        }

        if (webcam.isImageNew) {
            val image = webcam.image

            for (x in 0..image.width - 1) {
                for (y in 0..image.height - 1) {
                    val rgb = image.getRGB(x, y)

                    val red = rgb shr 16 and 0xFF
                    val green = rgb shr 8 and 0xFF
                    val blue = rgb and 0xFF

                    val yellow = Math.min(red,green) * (1-blue)
                }
            }

        }
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