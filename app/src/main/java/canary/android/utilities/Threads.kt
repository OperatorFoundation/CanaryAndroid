package canary.android.utilities
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import canary.android.numberTimesRunTest
import org.OperatorFoundation.CanaryLibrary.Canary
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Threads: Application(){
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())

}

class TestThread(
    //private val callbackHandler: Handler,
    private val executor: Executor
) {
    public fun runTests(canaryConfigDirectory: File) {
        executor.execute {
            val canaryInstance = Canary(
                configDirectoryFile = canaryConfigDirectory,
                timesToRun = numberTimesRunTest
            )
            canaryInstance.runTest()
        }
    }
}

