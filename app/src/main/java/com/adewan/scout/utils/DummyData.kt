package com.adewan.scout.utils

val imageList = listOf(
    "https://i.ebayimg.com/images/g/2uAAAOSwrZZkPy4M/s-l1200.jpg",
    "https://cdn.mobygames.com/covers/8437198-the-legend-of-zelda-breath-of-the-wild-wii-u-front-cover.jpg",
    "https://assets.vg247.com/current//2018/05/red_dead_redemption_2_cover_art_1.jpg",
    "https://cdn1.epicgames.com/offer/14ee004dadc142faaaece5a6270fb628/EGS_TheWitcher3WildHuntCompleteEdition_CDPROJEKTRED_S2_1200x1600-53a8fb2c0201cd8aea410f2a049aba3f",
    "https://i1.sndcdn.com/artworks-000626929138-cnxr2z-t500x500.jpg",
    "https://static.wikia.nocookie.net/darksouls/images/b/bc/Dark_Souls_III_cover_art.jpg/revision/latest?cb=20150721102658",
    "https://image.api.playstation.com/cdn/UP2611/CUSA05877_00/S9mV1Ye62EQBXVa96tffknkA4EIEqSBa.png",
    "https://upload.wikimedia.org/wikipedia/commons/0/0f/Celeste_box_art_full.png",
    "https://i.ebayimg.com/images/g/aNgAAOSwVChgEZxF/s-l1200.jpg",
    "https://image.api.playstation.com/vulcan/img/rnd/202011/1021/X3WIAh63yKhRRiMohLoJMeQu.png"
)

val screenshots = listOf(
    "https://sm.ign.com/t/ign_it/screenshot/default/gfqrvcgxkagbn3g_pfc8.1280.jpg",
    "https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1091500/ss_7924f64b6e5d586a80418c9896a1c92881a7905b.1920x1080.jpg?t=1734434803",
    "https://imageio.forbes.com/specials-images/imageserve/650d918cddc149fda9157454/photomode-21092023-200217/960x0.jpg?format=jpg&width=960",
    "https://xxboxnews.blob.core.windows.net/prod/sites/2/2024/12/Cyberpunk-2.2-HERO-a4a4cf2d36cdeb91b2f0.jpg",
)

const val dummyStoryline =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. \n\n Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

data class Game(
    val slug: String,
    val name: String,
    val platform: String,
    val releaseYear: Int,
    val rating: Double,
    val publisher: String,
    val posterUrl: String
)


val downloadbleContent = listOf(
    Game(
        slug = "gow",
        name = "Cyberpunk 2077: Phantom Liberty",
        platform = "PlayStation 5",
        releaseYear = 2019,
        rating = 94.5,
        publisher = "CD Projekt Red",
        posterUrl = "https://cdn1.epicgames.com/offer/77f2b98e2cef40c8a7437518bf420e47/EGS_Cyberpunk2077PhantomLiberty_CDPROJEKTRED_DLC_S2_1200x1600-3acec02a8b11a2c50ba8dd3cb217f1c6"
    ),
    Game(
        slug = "gow",
        name = "Cyberpunk 2077: Rising Tide",
        platform = "PlayStation 5",
        releaseYear = 2024,
        rating = 91.2,
        publisher = "CD Projekt Red",
        posterUrl = "https://i.ebayimg.com/images/g/OgAAAOSwK5tgDyUz/s-l1200.jpg"
    )

)


val games = listOf(
    Game(
        slug = "mario-galaxy",
        name = "Super Mario Galaxy",
        platform = "Nintendo Switch",
        releaseYear = 2000,
        rating = 87.6,
        publisher = "Nintendo",
        posterUrl = "https://i.ebayimg.com/images/g/2uAAAOSwrZZkPy4M/s-l1200.jpg"
    ),
    Game(
        slug = "legend-of-zelda-botw",
        name = "The Legend of Zelda: Breath of the Wild",
        platform = "Nintendo Switch",
        releaseYear = 2017,
        rating = 97.1,
        publisher = "Nintendo",
        posterUrl = "https://cdn.mobygames.com/covers/8437198-the-legend-of-zelda-breath-of-the-wild-wii-u-front-cover.jpg"
    ),
    Game(
        slug = "rdr2",
        name = "Red Dead Redemption 2",
        platform = "PlayStation 4",
        releaseYear = 2018,
        rating = 96.7,
        publisher = "Rockstar Games",
        posterUrl = "https://assets.vg247.com/current//2018/05/red_dead_redemption_2_cover_art_1.jpg"
    ),
    Game(
        slug = "witcher3",
        name = "The Witcher 3: Wild Hunt",
        platform = "PC",
        releaseYear = 2015,
        rating = 94.2,
        publisher = "CD Projekt Red",
        posterUrl = "https://cdn1.epicgames.com/offer/14ee004dadc142faaaece5a6270fb628/EGS_TheWitcher3WildHuntCompleteEdition_CDPROJEKTRED_S2_1200x1600-53a8fb2c0201cd8aea410f2a049aba3f"
    ),
    Game(
        slug = "hollow-knight",
        name = "Hollow Knight",
        platform = "Nintendo Switch",
        releaseYear = 2018,
        rating = 90.5,
        publisher = "Team Cherry",
        posterUrl = "https://i1.sndcdn.com/artworks-000626929138-cnxr2z-t500x500.jpg"
    ),
    Game(
        slug = "ds3",
        name = "Dark Souls III",
        platform = "PlayStation 4",
        releaseYear = 2016,
        rating = 89.4,
        publisher = "Bandai Namco Entertainment",
        posterUrl = "https://static.wikia.nocookie.net/darksouls/images/b/bc/Dark_Souls_III_cover_art.jpg/revision/latest?cb=20150721102658"
    ),
    Game(
        slug = "persona5",
        name = "Persona 5 Royal",
        platform = "PlayStation 4",
        releaseYear = 2020,
        rating = 93.9,
        publisher = "Atlus",
        posterUrl = "https://image.api.playstation.com/cdn/UP2611/CUSA05877_00/S9mV1Ye62EQBXVa96tffknkA4EIEqSBa.png"
    ),
    Game(
        slug = "celeste",
        name = "Celeste",
        platform = "PC",
        releaseYear = 2018,
        rating = 91.7,
        publisher = "Matt Makes Games",
        posterUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0f/Celeste_box_art_full.png"
    ),
    Game(
        slug = "animal-crossing",
        name = "Animal Crossing: New Horizons",
        platform = "Nintendo Switch",
        releaseYear = 2020,
        rating = 88.2,
        publisher = "Nintendo",
        posterUrl = "https://i.ebayimg.com/images/g/aNgAAOSwVChgEZxF/s-l1200.jpg"
    ),
    Game(
        slug = "gow",
        name = "God of War",
        platform = "PlayStation 4",
        releaseYear = 2018,
        rating = 94.5,
        publisher = "Santa Monica Studio",
        posterUrl = "https://image.api.playstation.com/vulcan/img/rnd/202011/1021/X3WIAh63yKhRRiMohLoJMeQu.png"
    )
)
