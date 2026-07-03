package framework.ui

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import kotlin.math.exp
import kotlin.math.sin

/**
 * Erzeugt Spiel-Sounds synthetisch zur Laufzeit (kein Audio-Asset nötig) über
 * javax.sound.sampled. Jeder Ton wird in einem Daemon-Thread abgespielt, damit
 * die UI-/Tick-Schleife nicht blockiert. Fehler (z.B. kein Audio-Gerät auf dem
 * Rechner) werden bewusst verschluckt - Sound ist reines Beiwerk, kein Muss.
 */
object SoundPlayer {
    private const val SAMPLE_RATE = 44100f

    /** Kurzer "Pew"-Schuss-Sound: abfallender Ton mit schnellem Ausklang. */
    fun playShot() {
        playTone(startHz = 880.0, endHz = 180.0, durationMs = 120, volume = 0.25)
    }

    private fun playTone(startHz: Double, endHz: Double, durationMs: Int, volume: Double) {
        Thread {
            try {
                val samples = (SAMPLE_RATE * durationMs / 1000).toInt()
                val buffer = ByteArray(samples * 2)
                var phase = 0.0
                for (i in 0 until samples) {
                    val t = i.toDouble() / samples
                    val freq = startHz + (endHz - startHz) * t
                    phase += 2.0 * Math.PI * freq / SAMPLE_RATE
                    // exponentielles Abklingen, damit der Ton nicht hart abbricht.
                    val envelope = exp(-3.0 * t)
                    val sample = (sin(phase) * envelope * volume * Short.MAX_VALUE).toInt().toShort()
                    buffer[i * 2] = (sample.toInt() and 0xFF).toByte()
                    buffer[i * 2 + 1] = (sample.toInt() shr 8 and 0xFF).toByte()
                }
                val format = AudioFormat(SAMPLE_RATE, 16, 1, true, false)
                val line: SourceDataLine = AudioSystem.getSourceDataLine(format)
                line.open(format)
                line.start()
                line.write(buffer, 0, buffer.size)
                line.drain()
                line.close()
            } catch (_: Exception) {
                // Kein Audio-Gerät o.ä. -> Sound einfach überspringen.
            }
        }.apply { isDaemon = true }.start()
    }
}
