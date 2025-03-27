package com.adewan.scout.core.network

import java.time.Instant

class QueryGeneratorRepository {
    suspend fun generateUpcomingQuery(): String {
        return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id;" +
                "w first_release_date >= ${Instant.now().epochSecond} & category=(0,1,2,8,9);" +
                "s hypes desc;" +
                "l 50;"
    }

    suspend fun generateTopRatedQuery(): String {
        return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id, platforms.slug, platforms.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug;" +
                "w rating >= 75 & category=(0,1,2,8,9);" +
                "s rating desc;" +
                "l 50;"
    }

    suspend fun generateRecentlyReleasedQuery(): String {
        return "f name,slug, rating, hypes,first_release_date, cover.*;" +
                "w rating >= 65 & rating <= 100 & first_release_date < ${Instant.now().epochSecond} & category=(0,1,2,8,9);" +
                "s first_release_date desc;" +
                "l 50;"
    }

    suspend fun generatePopularQuery(popularityType: String): String {
        return "fields game_id,value,popularity_type; sort value desc; limit 50; where popularity_type = $popularityType; sort value desc;"
    }

    suspend fun generateInfoRowQuery(ids: List<Int>): String {
        return "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
                "w id=(${ids.joinToString(",")});" +
                "l 50;"
    }

    suspend fun generateGameDetailQuery(slug: String): String {
        return "f name,slug,genres.*, cover.id, first_release_date,platforms.*, cover.image_id, screenshots.id, screenshots.image_id, total_rating, total_rating_count, involved_companies.*, involved_companies.company.*,summary, storyline, videos.*,similar_games.name,similar_games.slug,similar_games.rating,similar_games.first_release_date,similar_games.cover.id,similar_games.cover.image_id,similar_games.genres.slug,similar_games.genres.name,similar_games.involved_companies.developer,similar_games.involved_companies.publisher,similar_games.involved_companies.company.name,similar_games.involved_companies.company.slug,similar_games.platforms.slug,similar_games.platforms.name;" +
                "w slug=\"$slug\";"
    }

    suspend fun generateSearchQuery(searchTerm: String): String {
        return "search \"$searchTerm\"; " +
                "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
                "w category=(0,1,2,8,9);"
    }
}

