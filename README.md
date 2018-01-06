# spherical

Converts fish eye images into panorama images

# RUN

* Add your images (in jpg format) to the input folder

* ./gradlew run

* check the output folder


## Bugs
There is some distortion and loss of image quality near the center, or top of the image, as the distance goes to infinity.  
Usually this is not a big issue if the center is the sky, but may be worth looking into a scaling function.
