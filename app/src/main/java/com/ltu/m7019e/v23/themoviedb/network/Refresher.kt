import android.content.Context
import androidx.work.*
import com.ltu.m7019e.v23.themoviedb.database.*
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieListViewModel


class Refresher(ctx: Context, params: WorkerParameters, private val vm : MovieListViewModel) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        return try {
            vm.getMovies(null)
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }

    }
}