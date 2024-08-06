package `in`.planckstudio.filters.util

import android.graphics.Bitmap
import `in`.planckstudio.filters.util.Filter

class NormalFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyNormalFilter(src)
    }
}

class SepiaFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySepiaFilter(src)
    }
}

class GrayscaleFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyGrayscaleFilter(src)
    }
}

class BlackAndWhiteFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBlackAndWhiteFilter(src)
    }
}

class InvertFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyInvertFilter(src)
    }
}

class BrightenFilter(private val brightness: Int = 30) : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBrightenFilter(src, brightness)
    }
}

// Instagram Filters

class ClarendonFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyClarendonFilter(src)
    }
}

class GinghamFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyGinghamFilter(src)
    }
}

class JunoFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyJunoFilter(src)
    }
}

class LarkFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyLarkFilter(src)
    }
}

class LoFinFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyLoFiFilter(src)
    }
}

class PerpetuaFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyPerpetuaFilter(src)
    }
}

class ReyesFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyReyesFilter(src)
    }
}

class SierraFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySierraFilter(src)
    }
}

class ValenciaFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyValenciaFilter(src)
    }
}

class XProIIFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyXProIIFilter(src)
    }
}

class WillowFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyWillowFilter(src)
    }
}

class HefeFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyHefeFilter(src)
    }
}

class AmaroFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyAmaroFilter(src)
    }
}

class InkwellFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyInkwellFilter(src)
    }
}

class ParisFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyParisFilter(src)
    }
}

class LosAngelesFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyLosAngelesFilter(src)
    }
}

class FadeFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyFadeFilter(src)
    }
}

class FadeWarmFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyFadeWarmFilter(src)
    }
}

class FadeCoolFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyFadeCoolFilter(src)
    }
}

class SimpleFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySimpleFilter(src)
    }
}

class SimpleWarmFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySimpleWarmFilter(src)
    }
}

class SimpleCoolFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySimpleCoolFilter(src)
    }
}

class BoostFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBoostFilter(src)
    }
}

class BoostWarmFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBoostWarmFilter(src)
    }
}

class BoostCoolFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBoostCoolFilter(src)
    }
}

class GraphiteFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyGraphiteFilter(src)
    }
}

class HyperFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyHyperFilter(src)
    }
}

class RosyFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyRosyFilter(src)
    }
}

class EmeraldFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyEmeraldFilter(src)
    }
}

class MidnightFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyMidnightFilter(src)
    }
}

class GrainyFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyGrainyFilter(src)
    }
}

class GrittyFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyGrittyFilter(src)
    }
}

class HaloFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyHaloFilter(src)
    }
}

class ColorLeakFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyColorLeakFilter(src)
    }
}

class SoftLightFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySoftLightFilter(src)
    }
}

class ZoomBlurFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyZoomBlurFilter(src)
    }
}

class HandheldFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyHandheldFilter(src)
    }
}

class MoireFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyMoireFilter(src)
    }
}

class LoResFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyLoResFilter(src)
    }
}

class WavyFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyWavyFilter(src)
    }
}

class WideAngleFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyWideAngleFilter(src)
    }
}

class OsloFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyOsloFilter(src)
    }
}

class MelbourneFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyMelbourneFilter(src)
    }
}

class JakartaFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyJakartaFilter(src)
    }
}

class AbuDhabiFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyAbuDhabiFilter(src)
    }
}

class BuenosAiresFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyBuenosAiresFilter(src)
    }
}

class NewYorkFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyNewYorkFilter(src)
    }
}

class JaipurFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyJaipurFilter(src)
    }
}

class CairoFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyCairoFilter(src)
    }
}

class TokyoFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyTokyoFilter(src)
    }
}

class RioDeJaneiroFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyRioDeJaneiroFilter(src)
    }
}

class MoonFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyMoonFilter(src)
    }
}

class SlumberFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applySlumberFilter(src)
    }
}

class CremaFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyCremaFilter(src)
    }
}

class LudwigFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyLudwigFilter(src)
    }
}

class AdenFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyAdenFilter(src)
    }
}

class MayfairFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyMayfairFilter(src)
    }
}

class RiseFilter : Filter {
    override fun apply(src: Bitmap): Bitmap {
        return FilterUtils.applyRiseFilter(src)
    }
}

//class FantasyFilter : Filter {
//    override fun apply(src: Bitmap): Bitmap {
//        return FilterUtils.applyFantasyFilter(src)
//    }
//}
