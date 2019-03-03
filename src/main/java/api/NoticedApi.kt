package api

import io.reactivex.Observable
import model.notify.NotifyObject
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticedApi {
    @GET("notices/get")
    fun noticedGet(@Query("limit") limit: Int = 100,
                   @Query("before_id") beforeId: Int = 0): Observable<List<NotifyObject>>
}