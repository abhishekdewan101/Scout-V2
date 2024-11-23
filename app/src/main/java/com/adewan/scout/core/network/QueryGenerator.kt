package com.adewan.scout.core.network

import com.adewan.scout.core.genres.GenreRepository
import com.adewan.scout.core.local.DataStoreRepository
import com.adewan.scout.core.platform.PlatformRepository
import com.adewan.scout.ui.features.preference.SELECTED_GENRES_KEY
import com.adewan.scout.ui.features.preference.SELECTED_PLATFORMS_KEY
import java.time.Instant

class QueryGeneratorRepository(
    private val dataStoreRepository: DataStoreRepository,
    private val platformRepository: PlatformRepository,
    private val genreRepository: GenreRepository
) {

    lateinit var platformQueryString: String
    lateinit var genreQueryString: String

    suspend fun generateUpcomingQuery(): String {
        ensurePreferenceStrings()
        return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id;" +
                "w first_release_date >= ${Instant.now().epochSecond} & platforms =($platformQueryString) & category=(0,1,2,8,9) & genres = ($genreQueryString);" +
                "s hypes desc;" +
                "l 50;"
    }

    suspend fun generateTopRatedQuery(): String {
        ensurePreferenceStrings()
        return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id;" +
                "w rating >= 75 & platforms =($platformQueryString) & category=(0,1,2,8,9) & genres = ($genreQueryString);" +
                "s rating desc;" +
                "l 50;"
    }

    suspend fun generateRecentlyReleasedQuery(): String {
        ensurePreferenceStrings()
        return "f name,slug, rating, hypes,first_release_date, cover.*;" +
                "w rating >= 65 & rating <= 100 & first_release_date < ${Instant.now().epochSecond} & platforms =($platformQueryString) & category=(0,1,2,8,9) & genres = ($genreQueryString);" +
                "s first_release_date desc;" +
                "l 50;"
    }

    suspend fun generatePopularQuery(popularityType: String): String {
        ensurePreferenceStrings()
        return "fields game_id,value,popularity_type; sort value desc; limit 50; where popularity_type = $popularityType; sort value desc;"
    }

    suspend fun generateInfoRowQuery(ids: List<Int>): String {
        ensurePreferenceStrings()
        return "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
                "w id=(${ids.joinToString(",")});" +
                "l 50;"
    }

    suspend fun generateGameDetailQuery(slug: String): String {
        ensurePreferenceStrings()
        return "f name,slug,genres.*, cover.id, first_release_date,platforms.*, cover.image_id, screenshots.id, screenshots.image_id, total_rating, total_rating_count, involved_companies.*, involved_companies.company.*,summary, storyline, videos.*,similar_games.name,similar_games.slug,similar_games.rating,similar_games.first_release_date,similar_games.cover.id,similar_games.cover.image_id,similar_games.genres.slug,similar_games.genres.name,similar_games.involved_companies.developer,similar_games.involved_companies.publisher,similar_games.involved_companies.company.name,similar_games.involved_companies.company.slug,similar_games.platforms.slug,similar_games.platforms.name;" +
                "w slug=\"$slug\";"
    }

    suspend fun generateSearchQuery(searchTerm: String): String {
        ensurePreferenceStrings()
        return "search \"$searchTerm\"; " +
                "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
                "w platforms =($platformQueryString) & category=(0,1,2,8,9) & genres = ($genreQueryString);"
    }

    private suspend fun ensurePreferenceStrings() {
        platformQueryString = platformRepository.getPlatformsFromString(
            dataStoreRepository.getStringPreference(SELECTED_PLATFORMS_KEY)!!
        ).map { it.id }.joinToString(separator = ",")
        genreQueryString = genreRepository.getGenresFromString(
            dataStoreRepository.getStringPreference(
                SELECTED_GENRES_KEY
            )!!
        ).map { it.id }.joinToString(",")
    }
}

