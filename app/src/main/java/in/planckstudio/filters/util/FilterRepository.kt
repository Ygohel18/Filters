package `in`.planckstudio.filters.util

import `in`.planckstudio.filters.util.Filter

object FilterRepository {

    private val filters = listOf(
        "Normal" to NormalFilter(),
        "Clarendon" to ClarendonFilter(),
        "B&W" to BlackAndWhiteFilter(),
        "Grayscale" to GrayscaleFilter(),
        "Rosy" to RosyFilter(),
        "Lo-Res" to LoResFilter(),
        "Sepia" to SepiaFilter(),
        "Invert" to InvertFilter(),
        "Brighten" to BrightenFilter(),
        "Gingham" to GinghamFilter(),
        "Juno" to JunoFilter(),
        "Lark" to LarkFilter(),
        "LoFi" to LoFinFilter(),
        "Perpetua" to PerpetuaFilter(),
        "Reyes" to ReyesFilter(),
        "Sierra" to SierraFilter(),
        "Valencia" to ValenciaFilter(),
        "XPro II" to XProIIFilter(),
        "Willow" to WillowFilter(),
        "Hefe" to HefeFilter(),
        "Amaro" to AmaroFilter(),
        "Inkwell" to InkwellFilter(),
        "Paris" to ParisFilter(),
        "Los Angeles" to LosAngelesFilter(),
        "Fade" to FadeFilter(),
        "Fade Warm" to FadeWarmFilter(),
        "Fade Cool" to FadeCoolFilter(),
        "Simple" to SimpleFilter(),
        "Simple Cool" to SimpleCoolFilter(),
        "Simple Warn" to SimpleWarmFilter(),
        "Boost" to BoostFilter(),
        "Boost Warm" to BoostWarmFilter(),
        "Boost Cool" to BoostCoolFilter(),
        "Graphite" to GraphiteFilter(),
        "Hyper" to HyperFilter(),
        "Emerald" to EmeraldFilter(),
        "Midnight" to MidnightFilter(),
        "Grainy" to GrainyFilter(),
        "Gritty" to GrittyFilter(),
        "Halo" to HaloFilter(),
        "Color Leak" to ColorLeakFilter(),
        "Soft Light" to SoftLightFilter(),
        "Zoom Blur" to ZoomBlurFilter(),
        "Handheld" to HandheldFilter(),
        "Moire" to MoireFilter(),
        "Wavy" to WavyFilter(),
        "Wide Angle" to WideAngleFilter(),
        "Oslo" to OsloFilter(),
        "Melbourne" to MelbourneFilter(),
        "Jakarta" to JakartaFilter(),
        "AbuDhabi" to AbuDhabiFilter(),
        "Buenos Aires" to BuenosAiresFilter(),
        "New York " to NewYorkFilter(),
        "Jaipur" to JaipurFilter(),
        "Cairo" to CairoFilter(),
        "Tokyo" to TokyoFilter(),
        "Rio De Janeiro" to RioDeJaneiroFilter(),
        "Moon" to MoonFilter(),
        "Slumber" to SlumberFilter(),
        "Crema" to CremaFilter(),
        "Ludwig" to LudwigFilter(),
        "Aden" to AdenFilter(),
        "Mayfair" to MayfairFilter(),
        "Rise" to RiseFilter()
    )

    fun getFilterByName(name: String): Filter? {
        return filters.find { it.first == name }?.second
    }

    fun getFilterNames(): List<String> {
        return filters.map { it.first }
    }
}
