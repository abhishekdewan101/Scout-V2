package com.adewan.scout.core.network

import java.time.Instant

fun generateUpcomingQuery(): String {
    return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id;" +
            "w first_release_date >= ${Instant.now().epochSecond} & platforms =(167,169,130,48, 49, 1, 9,165,390,471) & category=(0,1,2,8,9) & genres = (4,5,8,10,12,13,14,24,25,31,32,33);" +
            "s hypes desc;" +
            "l 50;"
}

fun generateTopRatedQuery(): String {
    return "f name,slug, rating, hypes, first_release_date, cover.id, cover.image_id, screenshots.id, screenshots.image_id;" +
            "w rating >= 75 & platforms =(167,169,130,48, 49, 1, 9,165,390,471) & category=(0,1,2,8,9) & genres = (4,5,8,10,12,13,14,24,25,31,32,33);" +
            "s rating desc;" +
            "l 50;"
}

fun generateRecentlyReleasedQuery(): String {
    return "f name,slug, rating, hypes,first_release_date, cover.*;" +
            "w rating >= 65 & rating <= 100 & first_release_date < ${Instant.now().epochSecond} & platforms =(167,169,130,48, 49, 1, 9,165,390,471) & category=(0,1,2,8,9) & genres = (4,5,8,10,12,13,14,24,25,31,32,33);" +
            "s first_release_date desc;" +
            "l 50;"
}

fun generatePopularQuery(popularityType: String): String {
    return "fields game_id,value,popularity_type; sort value desc; limit 50; where popularity_type = $popularityType; sort value desc;"
}

fun generateInfoRowQuery(ids: List<Int>): String {
    return "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
            "w id=(${ids.joinToString(",")});" +
            "l 50;"
}

fun generateGameDetailQuery(slug: String): String {
    return "f name,slug,genres.*, cover.id, first_release_date,platforms.*, cover.image_id, screenshots.id, screenshots.image_id, total_rating, total_rating_count, involved_companies.*, involved_companies.company.*,summary, storyline, videos.*,similar_games.name,similar_games.slug,similar_games.rating,similar_games.first_release_date,similar_games.cover.id,similar_games.cover.image_id,similar_games.genres.slug,similar_games.genres.name,similar_games.involved_companies.developer,similar_games.involved_companies.publisher,similar_games.involved_companies.company.name,similar_games.involved_companies.company.slug,similar_games.platforms.slug,similar_games.platforms.name;" +
            "w slug=\"$slug\";"
}

fun generateSearchQuery(searchTerm: String): String {
    return "search \"$searchTerm\"; " +
            "f name,slug, rating, first_release_date, cover.id, cover.image_id, genres.slug, genres.name, involved_companies.developer, involved_companies.publisher, involved_companies.company.name, involved_companies.company.slug, platforms.slug, platforms.name;" +
            "w platforms =(167,169,130,48, 49, 1, 9,165,390,471) & category=(0,1,2,8,9) & genres = (4,5,8,10,12,13,14,24,25,31,32,33);"
}