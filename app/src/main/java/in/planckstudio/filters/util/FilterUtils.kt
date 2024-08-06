package `in`.planckstudio.filters.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Build
import java.util.Random
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object FilterUtils {

    fun applyNormalFilter(src: Bitmap): Bitmap {
        return src
    }

    private fun processCommonFilter(
        src: Bitmap,
        redValue: Double,
        greenValue: Double,
        blueValue: Double
    ): Bitmap {
        val width = src.width
        val height = src.height

        val commonBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val tr = (red * redValue).toInt().coerceAtMost(255)
                val tg = (green * greenValue).toInt().coerceAtMost(255)
                val tb = (blue * blueValue).toInt().coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                commonBitmap.setPixel(x, y, newPixel)
            }
        }

        return commonBitmap
    }

//    private fun processImageAdjustments(
//        src: Bitmap,
//        exposure: Float = 0.0f,
//        brilliance: Float = 0.0f,
//        highlights: Float = 0.0f,
//        shadows: Float = 0.0f,
//        brightness: Float = 0.0f,
//        blackPoint: Float = 0.0f,
//        saturation: Float = 1.0f,
//        vibrance: Float = 1.0f,
//        warmth: Float = 0.0f,
//        tint: Float = 0.0f,
//        definition: Float = 0.0f,
//        contrast: Float = 1.0f,
//        grain: Float = 0.0f,
//        clarity: Float = 0.0f,
//        skinTone: Float = 1.0f,
//        noiseReduction: Float = 0.0f,
//        sharpness: Float = 0.0f,
//        vignette: Float = 0.0f
//    ): Bitmap {
//        val width = src.width
//        val height = src.height
//
//        val adjustedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        val centerX = width / 2.0
//        val centerY = height / 2.0
//        val maxDistance = sqrt(centerX * centerX + centerY * centerY)
//
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                val pixel = src.getPixel(x, y)
//                var red = Color.red(pixel).toFloat()
//                var green = Color.green(pixel).toFloat()
//                var blue = Color.blue(pixel).toFloat()
//                val alpha = Color.alpha(pixel)
//                val grayscale = 0.299 * red + 0.587 * green + 0.114 * blue
//
//                // Apply exposure adjustment
//                if (exposure != 0.0f) {
//                    red = (red * (1.0 + exposure)).toFloat().coerceIn(0f, 255f)
//                    green = (green * (1.0 + exposure)).toFloat().coerceIn(0f, 255f)
//                    blue = (blue * (1.0 + exposure)).toFloat().coerceIn(0f, 255f)
//                }
//
//                // Apply brightness adjustment
//                if (brightness != 0.0f) {
//                    red += brightness * 255
//                    green += brightness * 255
//                    blue += brightness * 255
//                }
//
//                // Apply contrast adjustment
//                if (contrast != 1.0f) {
//                    red = ((red - 128) * contrast + 128).coerceIn(0f, 255f)
//                    green = ((green - 128) * contrast + 128).coerceIn(0f, 255f)
//                    blue = ((blue - 128) * contrast + 128).coerceIn(0f, 255f)
//                }
//
//                // Apply saturation adjustment
//                if (saturation != 1.0f) {
//                    red = (grayscale + (red - grayscale) * saturation).toFloat().coerceIn(0f, 255f)
//                    green =
//                        (grayscale + (green - grayscale) * saturation).toFloat().coerceIn(0f, 255f)
//                    blue =
//                        (grayscale + (blue - grayscale) * saturation).toFloat().coerceIn(0f, 255f)
//                }
//
//                // Apply vibrance adjustment
//                if (vibrance != 1.0f) {
//                    val maxComponent = red.coerceAtLeast(max(green, blue))
//                    if (maxComponent > 0) {
//                        red += (maxComponent - red) * (1 - vibrance)
//                        green += (maxComponent - green) * (1 - vibrance)
//                        blue += (maxComponent - blue) * (1 - vibrance)
//                    }
//                }
//
//                // Apply warmth adjustment
//                if (warmth != 0.0f) {
//                    red += warmth * 100
//                    blue -= warmth * 100
//                }
//
//                // Apply tint adjustment
//                if (tint != 0.0f) {
//                    red += tint * 100
//                    green += tint * 50
//                }
//
//                // Apply highlights adjustment
//                if (highlights != 0.0f) {
//                    if (red > 128) red += highlights * (255 - red)
//                    if (green > 128) green += highlights * (255 - green)
//                    if (blue > 128) blue += highlights * (255 - blue)
//                }
//
//                // Apply shadows adjustment
//                if (shadows != 0.0f) {
//                    if (red < 128) red += shadows * red
//                    if (green < 128) green += shadows * green
//                    if (blue < 128) blue += shadows * blue
//                }
//
//                // Apply black point adjustment
//                if (blackPoint != 0.0f) {
//                    red = (red * (1 - blackPoint)).coerceIn(0f, 255f)
//                    green = (green * (1 - blackPoint)).coerceIn(0f, 255f)
//                    blue = (blue * (1 - blackPoint)).coerceIn(0f, 255f)
//                }
//
//                // Apply brilliance adjustment
//                if (brilliance != 0.0f) {
//                    val luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue
//                    val factor = 1 + brilliance
//                    red = (red + factor * (luminance - red)).toFloat().coerceIn(0f, 255f)
//                    green = (green + factor * (luminance - green)).toFloat().coerceIn(0f, 255f)
//                    blue = (blue + factor * (luminance - blue)).toFloat().coerceIn(0f, 255f)
//                }
//
//                // Apply definition adjustment
//                if (definition != 0.0f) {
//                    red = ((red - 128) * definition + 128).coerceIn(0f, 255f)
//                    green = ((green - 128) * definition + 128).coerceIn(0f, 255f)
//                    blue = ((blue - 128) * definition + 128).coerceIn(0f, 255f)
//                }
//
//                // Apply grain adjustment
//                if (grain != 0.0f) {
//                    val noise = (Math.random() * grain * 255).toFloat()
//                    red = (red + noise).coerceIn(0f, 255f)
//                    green = (green + noise).coerceIn(0f, 255f)
//                    blue = (blue + noise).coerceIn(0f, 255f)
//                }
//
//                // Apply clarity adjustment
//                if (clarity != 0.0f) {
//                    val clarityFactor = clarity * 0.01f
//                    red = (red + (clarityFactor * (red - grayscale))).toFloat().coerceIn(0f, 255f)
//                    green =
//                        (green + (clarityFactor * (green - grayscale))).toFloat().coerceIn(0f, 255f)
//                    blue =
//                        (blue + (clarityFactor * (blue - grayscale))).toFloat().coerceIn(0f, 255f)
//                }
//
//                // Apply sharpness adjustment
//                if (sharpness != 0.0f) {
//                    // Apply a simple sharpening kernel
//                    // More advanced kernels can be used for better results
//                    val sharpenKernel = arrayOf(
//                        floatArrayOf(0f, -1f, 0f),
//                        floatArrayOf(-1f, 5f * sharpness, -1f),
//                        floatArrayOf(0f, -1f, 0f)
//                    )
//
//                    val matrix = ColorMatrix()
//                    matrix.set(
//                        floatArrayOf(
//                            sharpenKernel[0][0], sharpenKernel[0][1], sharpenKernel[0][2], 0f, 0f,
//                            sharpenKernel[1][0], sharpenKernel[1][1], sharpenKernel[1][2], 0f, 0f,
//                            sharpenKernel[2][0], sharpenKernel[2][1], sharpenKernel[2][2], 0f, 0f,
//                            0f, 0f, 0f, 1f, 0f
//                        )
//                    )
//
//                    val paint = Paint()
//                    paint.colorFilter = ColorMatrixColorFilter(matrix)
//
//                    val canvas = Canvas(adjustedBitmap)
//                    canvas.drawBitmap(src, 0f, 0f, paint)
//                }
//
//                // Apply vignette effect
//                if (vignette != 0.0f) {
//                    val distance = sqrt(
//                        (centerX - x).toDouble().pow(2.0) + (centerY - y).toDouble().pow(2.0)
//                    )
//                    val vignetteEffect =
//                        (1 - (distance / maxDistance) * vignette).toFloat().coerceIn(0f, 1f)
//                    red *= vignetteEffect
//                    green *= vignetteEffect
//                    blue *= vignetteEffect
//                }
//
//                // Ensure color values are within bounds
//                red = red.coerceIn(0f, 255f)
//                green = green.coerceIn(0f, 255f)
//                blue = blue.coerceIn(0f, 255f)
//
//                val adjustedPixel = Color.argb(alpha, red.toInt(), green.toInt(), blue.toInt())
//                adjustedBitmap.setPixel(x, y, adjustedPixel)
//            }
//        }
//
//        return adjustedBitmap
//    }

    fun applySepiaFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height

//        val sepiaBitmap =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && src.config == Bitmap.Config.HARDWARE) {
//                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//            } else {
//                Bitmap.createBitmap(width, height, src.config ?: Bitmap.Config.ARGB_8888)
//            }

        val sepiaBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val tr = (0.393 * red + 0.769 * green + 0.189 * blue).toInt().coerceAtMost(255)
                val tg = (0.349 * red + 0.686 * green + 0.168 * blue).toInt().coerceAtMost(255)
                val tb = (0.272 * red + 0.534 * green + 0.131 * blue).toInt().coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                sepiaBitmap.setPixel(x, y, newPixel)
            }
        }

        return sepiaBitmap
    }

    fun applyGrayscaleFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()
                val newPixel = Color.rgb(gray, gray, gray)
                grayscaleBitmap.setPixel(x, y, newPixel)
            }
        }

        return grayscaleBitmap
    }

    fun applyBlackAndWhiteFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val bwBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                // Calculate the average value of the RGB channels
                val average = ((red + green + blue) / 3).toInt()

                // Set the new pixel color to the average value
                val newPixel = Color.rgb(average, average, average)
                bwBitmap.setPixel(x, y, newPixel)
            }
        }

        return bwBitmap
    }


    fun applyInvertFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val invertBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = 255 - Color.red(pixel)
                val green = 255 - Color.green(pixel)
                val blue = 255 - Color.blue(pixel)

                val newPixel = Color.rgb(red, green, blue)
                invertBitmap.setPixel(x, y, newPixel)
            }
        }

        return invertBitmap
    }

    fun applyBrightenFilter(src: Bitmap, brightness: Int = 30): Bitmap {
        val width = src.width
        val height = src.height
        val brightenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = (Color.red(pixel) + brightness).coerceIn(0, 255)
                val green = (Color.green(pixel) + brightness).coerceIn(0, 255)
                val blue = (Color.blue(pixel) + brightness).coerceIn(0, 255)

                val newPixel = Color.rgb(red, green, blue)
                brightenBitmap.setPixel(x, y, newPixel)
            }
        }

        return brightenBitmap
    }

    // Instagram Filters

    fun applyClarendonFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.2, 1.2)
    }

    fun applyGinghamFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.9, 0.9)
    }

    fun applyJunoFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.1, 0.9)
    }

    fun applyLarkFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.9, 0.9)
    }

    fun applyLoFiFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.2, 1.2)
    }

    fun applyNashvilleFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, (0.9 + 20), (0.8 + 10), (0.7 + 5))
    }

    fun applyPerpetuaFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 1.2, 1.2)
    }

    fun applyReyesFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.9, 0.9)
    }

    fun applySierraFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 1.1, 1.1)
    }

    fun applyValenciaFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.9, 0.8)
    }

    fun applyWillowFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val willowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()
                val newPixel = Color.rgb(gray, gray, gray)
                willowBitmap.setPixel(x, y, newPixel)
            }
        }

        return willowBitmap
    }

    fun applyXProIIFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.5, 1.5, 1.5)
    }

    fun applyHefeFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.2, 0.9)
    }

    fun applyAmaroFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.1, 0.1, 1.1)
    }

    fun applyInkwellFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val inkwellBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()
                val newPixel = Color.rgb(gray, gray, gray)
                inkwellBitmap.setPixel(x, y, newPixel)
            }
        }

        return inkwellBitmap
    }

    fun applyParisFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.1, 0.9)
    }

    fun applyLosAngelesFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.9, 1.2)
    }

    fun applyFadeFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 0.8, 0.7)
    }

    fun applyFadeWarmFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 0.8, 0.7)
    }

    fun applyFadeCoolFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.7, 0.8, 0.9)
    }

    fun applySimpleFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 1.1, 1.1)
    }

    fun applySimpleWarmFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.1, 0.9)
    }

    fun applySimpleCoolFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 1.1, 1.2)
    }

    fun applyBoostFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.5, 1.5, 1.5)
    }

    fun applyBoostWarmFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.5, 1.3, 1.1)
    }

    fun applyBoostCoolFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 1.3, 1.5)
    }

    fun applyGraphiteFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val graphiteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (0.3 * red + 0.59 * green + 0.11 * blue).toInt().coerceAtMost(255)

                val newPixel = Color.rgb(gray, gray, gray)
                graphiteBitmap.setPixel(x, y, newPixel)
            }
        }

        return graphiteBitmap
    }

    fun applyHyperFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.5, 1.5, 1.5)
    }

    fun applyRosyFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.3, 1.0, 1.0)
    }

    fun applyEmeraldFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 1.4, 0.8)
    }

    fun applyMidnightFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.6, 0.6, 1.2)
    }

    fun applyGrainyFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val grainyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val random = Random()

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val noise = (random.nextFloat() * 50).toInt()
                val tr = (red + noise).coerceIn(0, 255)
                val tg = (green + noise).coerceIn(0, 255)
                val tb = (blue + noise).coerceIn(0, 255)

                val newPixel = Color.rgb(tr, tg, tb)
                grainyBitmap.setPixel(x, y, newPixel)
            }
        }

        return grainyBitmap
    }

    fun applyGrittyFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val grittyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val gray = (0.3 * red + 0.59 * green + 0.11 * blue).toInt()
                val noise = (Math.random() * 100).toInt() - 50
                val newGray = (gray + noise).coerceIn(0, 255)

                val newPixel = Color.rgb(newGray, newGray, newGray)
                grittyBitmap.setPixel(x, y, newPixel)
            }
        }

        return grittyBitmap
    }

    fun applyHaloFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val haloBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val centerX = width / 2.0
        val centerY = height / 2.0
        val maxDist = sqrt((centerX * centerX + centerY * centerY))

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val dist = sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY))
                val factor = (1.0 - (dist / maxDist)).coerceIn(0.0, 1.0)

                val tr = (red + factor * 100).toInt().coerceAtMost(255)
                val tg = (green + factor * 100).toInt().coerceAtMost(255)
                val tb = (blue + factor * 100).toInt().coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                haloBitmap.setPixel(x, y, newPixel)
            }
        }

        return haloBitmap
    }

    fun applyColorLeakFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val colorLeakBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val tr = (red + 50).coerceAtMost(255)
                val tg = (green + 20).coerceAtMost(255)
                val tb = (blue + 80).coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                colorLeakBitmap.setPixel(x, y, newPixel)
            }
        }

        return colorLeakBitmap
    }

    fun applySoftLightFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.2, 1.2)
    }

    fun applyZoomBlurFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val zooBlurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val blurRadius = 3
        for (x in 0 until width) {
            for (y in 0 until height) {
                var redSum = 0
                var greenSum = 0
                var blueSum = 0
                var count = 0

                for (kx in -blurRadius..blurRadius) {
                    for (ky in -blurRadius..blurRadius) {
                        val nx = x + kx
                        val ny = y + ky

                        if (nx in 0 until width && ny in 0 until height) {
                            val pixel = src.getPixel(nx, ny)
                            redSum += Color.red(pixel)
                            greenSum += Color.green(pixel)
                            blueSum += Color.blue(pixel)
                            count++
                        }
                    }
                }

                val tr = (redSum / count).coerceAtMost(255)
                val tg = (greenSum / count).coerceAtMost(255)
                val tb = (blueSum / count).coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                zooBlurBitmap.setPixel(x, y, newPixel)
            }
        }

        return zooBlurBitmap
    }

    fun applyHandheldFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 0.9, 1.1)
    }

    fun applyMoireFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val moireBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val tr = (red + (sin(x.toDouble() / 10) * 50).toInt()).coerceAtMost(255)
                val tg = (green + (sin(y.toDouble() / 10) * 50).toInt()).coerceAtMost(255)
                val tb = (blue + (sin((x + y).toDouble() / 20) * 50).toInt()).coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                moireBitmap.setPixel(x, y, newPixel)
            }
        }
        return moireBitmap
    }

    fun applyLoResFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val loResBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val pixelSize = 10 // Size of the pixelation

        for (x in 0 until width step pixelSize) {
            for (y in 0 until height step pixelSize) {
                var redSum = 0
                var greenSum = 0
                var blueSum = 0
                var count = 0

                for (kx in 0 until pixelSize) {
                    for (ky in 0 until pixelSize) {
                        val nx = x + kx
                        val ny = y + ky

                        if (nx in 0 until width && ny in 0 until height) {
                            val pixel = src.getPixel(nx, ny)
                            redSum += Color.red(pixel)
                            greenSum += Color.green(pixel)
                            blueSum += Color.blue(pixel)
                            count++
                        }
                    }
                }

                val avgRed = (redSum / count).coerceAtMost(255)
                val avgGreen = (greenSum / count).coerceAtMost(255)
                val avgBlue = (blueSum / count).coerceAtMost(255)

                for (kx in 0 until pixelSize) {
                    for (ky in 0 until pixelSize) {
                        val nx = x + kx
                        val ny = y + ky

                        if (nx in 0 until width && ny in 0 until height) {
                            val newPixel = Color.rgb(avgRed, avgGreen, avgBlue)
                            loResBitmap.setPixel(nx, ny, newPixel)
                        }
                    }
                }
            }
        }

        return loResBitmap
    }

    fun applyWavyFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val wavyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val amplitude = 10
        val frequency = 0.1

        for (x in 0 until width) {
            for (y in 0 until height) {
                val waveOffset = (sin(y * frequency) * amplitude).toInt()
                val newX = (x + waveOffset).coerceIn(0, width - 1)
                val newPixel = src.getPixel(newX, y)

                val tr = Color.red(newPixel)
                val tg = Color.green(newPixel)
                val tb = Color.blue(newPixel)

                val newPixelColor = Color.rgb(tr, tg, tb)
                wavyBitmap.setPixel(x, y, newPixelColor)
            }
        }

        return wavyBitmap
    }

    fun applyWideAngleFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val wideAngleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val centerX = width / 2.0
        val centerY = height / 2.0
        val maxDist = sqrt((centerX * centerX + centerY * centerY))

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                val dist = sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY))
                val factor = 1.0 + ((dist / maxDist) * 0.5)

                val tr = (red * factor).toInt().coerceAtMost(255)
                val tg = (green * factor).toInt().coerceAtMost(255)
                val tb = (blue * factor).toInt().coerceAtMost(255)

                val newPixel = Color.rgb(tr, tg, tb)
                wideAngleBitmap.setPixel(x, y, newPixel)
            }
        }

        return wideAngleBitmap
    }

    fun applyOsloFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.0, 0.8)
    }

    fun applyMelbourneFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 1.2, 1.0)
    }

    fun applyJakartaFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 0.9, 0.8)
    }

    fun applyAbuDhabiFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 1.2, 1.1)
    }

    fun applyBuenosAiresFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 1.1, 1.3)
    }

    fun applyNewYorkFilter(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val newYorkBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val random = Random()

        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = src.getPixel(x, y)
                var red = Color.red(pixel)
                var green = Color.green(pixel)
                var blue = Color.blue(pixel)

                // Darken the colors
                red = (red * 0.7).toInt().coerceAtMost(255)
                green = (green * 0.7).toInt().coerceAtMost(255)
                blue = (blue * 0.7).toInt().coerceAtMost(255)

                // Add more pronounced grain effect
                val grain = (random.nextInt(64) - 32)
                red = (red + grain).coerceIn(0, 255)
                green = (green + grain).coerceIn(0, 255)
                blue = (blue + grain).coerceIn(0, 255)

                val newPixel = Color.rgb(red, green, blue)
                newYorkBitmap.setPixel(x, y, newPixel)
            }
        }

        return newYorkBitmap
    }

    fun applyJaipurFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.3, 0.9, 0.8)
    }

    fun applyCairoFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 1.1, 0.9)
    }

    fun applyTokyoFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.9, 1.2, 1.0)
    }

    fun applyRioDeJaneiroFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.0, 0.9)
    }

    fun applyMoonFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 0.8, 0.8, 1.2)
    }

    fun applySlumberFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.0, 0.8, 0.9)
    }

    fun applyCremaFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.1, 1.0, 0.9)
    }

    fun applyLudwigFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.1, 0.8)
    }

    fun applyAdenFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.0, 1.2, 1.1)
    }

    fun applyMayfairFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.2, 1.0, 0.8)
    }

    fun applyRiseFilter(src: Bitmap): Bitmap {
        return processCommonFilter(src, 1.3, 1.1, 0.9)
    }

//    Custom Filters

//    fun applyFantasyFilter(srcImg: Bitmap): Bitmap {
//        return processImageAdjustments(
//            src = srcImg,
//            exposure = -20f,
//            brilliance = 10f,
//            brightness = 20f,
//            highlights = -90f,
//            shadows = -5f,
//            blackPoint = 15f,
//            saturation = 35f,
//            vibrance = -10f,
//            warmth = -50f,
//            tint = 65f,
//            definition = 30f
//        )
//    }

//    fun applyFantasyFilter(srcImg: Bitmap): Bitmap {
//        return processImageAdjustments(
//            src = srcImg,
//            brightness = 20f
//        )
//    }
}
