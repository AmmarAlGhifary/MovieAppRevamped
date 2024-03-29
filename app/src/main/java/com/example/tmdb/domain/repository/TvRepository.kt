package com.example.tmdb.domain.repository

import com.example.tmdb.domain.model.*
import com.example.tmdb.util.Resource

interface TvRepository {
    suspend fun getTvList(listId: String, page: Int): Resource<TvList>
    suspend fun getTrendingTvs(): Resource<TvList>
    suspend fun getTrendingTvTrailers(tvId: Int): Resource<VideoList>
    suspend fun getTvsByGenre(genreId: Int, page: Int): Resource<TvList>
    suspend fun getTvSearchResults(query: String, page: Int): Resource<TvList>
    suspend fun getTvDetails(tvId: Int): Resource<TvDetail>
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonDetail>
    suspend fun getEpisodeDetails(tvId: Int, seasonNumber: Int, episodeNumber: Int): Resource<EpisodeDetail>
    suspend fun getFavoriteTvs(): List<FavoriteTv>
    suspend fun tvExists(tvId: Int): Boolean
    suspend fun insertTv(tv: FavoriteTv)
    suspend fun deleteTv(tv: FavoriteTv)
}