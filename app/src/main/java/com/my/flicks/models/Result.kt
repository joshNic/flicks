package com.my.flicks.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class Result(
//    var adult: Boolean,
    @PrimaryKey
    var id: Int,
//    @Ignore
    @Json(name = "backdrop_path") var backdropPath: String? = "",
//    @Ignore
    @ColumnInfo(name = "genreIds")
    @Json(name = "genre_ids") var genreIds: List<Int> = emptyList(),
//    @Ignore
    @Json(name = "original_language") var originalLanguage: String? = "",
//    @Ignore
    @Json(name = "original_title") var originalTitle: String? = "",
    @ColumnInfo(name = "overview")
    var overview: String? = "",
//    @Ignore
    var popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path") var posterPath: String? = "",
//    @Ignore
    @ColumnInfo(name = "releaseDate")
    @Json(name = "release_date") var releaseDate: String? = "",
    @ColumnInfo(name = "title")
    var title: String? = "",
//    var video: Boolean,
//    @Ignore
    @Json(name = "vote_average") var voteAverage: Double? = 0.0,
//    @Ignore
    @Json(name = "vote_count") var voteCount: Int? = 0
) : Parcelable
