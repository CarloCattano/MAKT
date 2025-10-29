import kotlinx.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi
import miniaudio.*
import okio.Path.Companion.toPath
import okio.FileSystem
import platform.posix.getchar

@OptIn(ExperimentalForeignApi::class)
fun main() = memScoped {

    val fileName = "sound.wav"
    if (!FileSystem.SYSTEM.exists(fileName.toPath())) {
        println("File not found: $fileName")
        return@memScoped
    }

    val engine = alloc<ma_engine>()
    val result = ma_engine_init(null, engine.ptr)

    if (result != MA_SUCCESS) {
        println("Failed to initialize audio engine")
        return@memScoped
    }

    ma_engine_play_sound(engine.ptr, fileName, null)
    println("Press Enter to quit...")
    getchar()
    ma_engine_uninit(engine.ptr)
}