package kr.techit.lion.network.service

import kr.techit.lion.network.response.bookmark.PlaceBookmarkListResponse
import kr.techit.lion.network.response.bookmark.PlaceBookmarkResponse
import kr.techit.lion.network.response.bookmark.PlanBookmarkResponse
import kr.techit.lion.network.response.bookmark.PlanDetailBookmarkResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface BookmarkService {
    @GET("bookmark/names")
    suspend fun getPlaceBookmarkList() : PlaceBookmarkListResponse

    @GET("bookmark/place")
    suspend fun getPlaceBookmark() : PlaceBookmarkResponse

    @GET("bookmark/plan")
    suspend fun getPlanBookmark() : PlanBookmarkResponse

    @PATCH("bookmark/place/{placeId}")
    suspend fun updatePlaceBookmark(
        @Path("placeId") placeId: Long
    )

    @PATCH("bookmark/plan/{planId}")
    suspend fun updatePlanBookmark(
        @Path("planId") planId: Long
    )

    @GET("bookmark/plan/{planId}")
    suspend fun getPlanDetailBookmark(
        @Path("planId") planId: Long
    ): PlanDetailBookmarkResponse
}