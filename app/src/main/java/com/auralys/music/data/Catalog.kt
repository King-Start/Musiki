package com.auralys.music.data

/**
 * Katalog demo berisi lagu-lagu seperti pada tampilan referensi.
 * Audio memakai stream uji publik SoundHelix agar pemutaran benar-benar
 * berfungsi tanpa berkas lokal.
 */
object Catalog {

    private fun stream(n: Int) = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-$n.mp3"

    val songs: List<Song> = listOf(
        Song("s01", "Gue Udah Muak", "Dycal", "Gue Udah Muak - Single", 214, stream(1), listOf(0xFFE53E3E, 0xFF9B2C2C), "Pop"),
        Song("s02", "Clown", "John Michael Howell", "Clown - Single", 187, stream(2), listOf(0xFFE2E8F0, 0xFFA0AEC0), "Indie"),
        Song("s03", "Unreal World", "Chybee Mew", "Unreal World", 232, stream(3), listOf(0xFF3182CE, 0xFF2F855A), "Pop"),
        Song("s04", "Astaga Bercanda", "Akbar Chalay dan Mingse", "Astaga Bercanda", 198, stream(4), listOf(0xFFD53F8C, 0xFF805AD5), "Pop"),
        Song("s05", "Nona Kaka Sayang", "Ikl Dano", "Nona Kaka Sayang", 245, stream(5), listOf(0xFF9B2C2C, 0xFF742A2A), "Dangdut"),
        Song("s06", "Mimpi Semata", "Raim Laode", "Mimpi Semata", 209, stream(6), listOf(0xFF63B3ED, 0xFFBEE3F8), "Akustik"),
        Song("s07", "Teh Hijau", "Brisia Jodie", "Teh Hijau", 226, stream(7), listOf(0xFF68D391, 0xFF276749), "Akustik"),
        Song("s08", "Abracadabra", "Mutan Jameela", "Abracadabra", 191, stream(8), listOf(0xFFED64A6, 0xFF97266D), "Rock"),
        Song("s09", "Negoro Angin", "Kharis Sopan", "Negoro Angin", 254, stream(9), listOf(0xFFD69E2E, 0xFF975A16), "Koplo"),
        Song("s10", "DJ Terbelah Jadi Dua - (BLB Edit)", "Awan Axello", "DJ Terbelah Jadi Dua", 372, stream(10), listOf(0xFF1A202C, 0xFF4A5568), "Koplo"),
        Song("s11", "Modal Effect", "Dj Qhelfin", "Modal Effect", 201, stream(11), listOf(0xFFDD6B20, 0xFFC05621), "Koplo"),
        Song("s12", "Kita Deal (feat. Nathh)", "Eno Smaper", "Kita Deal", 188, stream(12), listOf(0xFF2C7A7B, 0xFF234E52), "Hip Hop"),
        Song("s13", "FYP DJ Minang Kuansing Terbaru", "Damonok", "FYP DJ Minang", 262, stream(13), listOf(0xFFB7791F, 0xFF744210), "Koplo"),
        Song("s14", "Lonely", "Akon", "Lonely - Single", 233, stream(14), listOf(0xFFE53E3E, 0xFFC53030), "Hip Hop"),
        Song("s15", "Walker Stations", "Alan Walker", "Walker Stations", 217, stream(15), listOf(0xFF2B6CB0, 0xFF1A365D), "Semangat"),
        Song("s16", "Last Child's Heart Break Station", "Last Child", "Station", 244, stream(16), listOf(0xFF718096, 0xFF4A5568), "Galau"),
        Song("s17", "DJ Amnesia Style Sopan", "DJ Dorus", "DJ Amnesia", 296, stream(1), listOf(0xFF0BC5EA, 0xFF0987A0), "Koplo"),
        Song("s18", "Say Hello", "Chybee Mew", "Say Hello", 183, stream(2), listOf(0xFFFBB6CE, 0xFF97266D), "Santai"),
        Song("s19", "Nona Manis Alis Tipis", "Dj Qhelfin", "Nona Manis", 227, stream(3), listOf(0xFFD6BCFA, 0xFF6B46C1), "Koplo"),
        Song("s20", "DJ Slow Remix Buat Diperjalanan", "Ikyy Pahlevii", "Slow Remix", 326, stream(4), listOf(0xFF4299E1, 0xFF2A4365), "Koplo"),
        Song("s21", "Bajanji Arek (feat. Kojo)", "Eja S.M", "Bajanji Arek", 212, stream(5), listOf(0xFFED8936, 0xFFC05621), "Dangdut"),
        Song("s22", "ADEM!!! Rawi Beat - Forever Young", "Rawi Beat", "ADEM!!!", 258, stream(6), listOf(0xFF2D3748, 0xFF1A202C), "Koplo"),
        Song("s23", "Dj Untuk Pengisi Hatiku Slow Beat Viral", "Dj Komang Rimex", "Pengisi Hatiku", 274, stream(7), listOf(0xFF553C9A, 0xFF2D3748), "Koplo"),
        Song("s24", "DJ Sad! Sa Titip Dia x Biar Sa Sendir", "Dj HarrisNugraha", "DJ Sad!", 241, stream(8), listOf(0xFF1A202C, 0xFF718096), "Galau"),
        Song("s25", "DJ Jatuh Cinta Maimunah New Style", "Dj HarrisNugraha", "Jatuh Cinta", 236, stream(9), listOf(0xFF97266D, 0xFF553C9A), "Koplo"),
        Song("s26", "Hey Kamu", "Chybee Mew", "Hey Kamu", 194, stream(10), listOf(0xFF48BB78, 0xFF2F855A), "Pop"),
        Song("s27", "Bercanda", "Silet Open Up dan SonaOne", "Bercanda", 205, stream(11), listOf(0xFF1A202C, 0xFF4A5568), "Indie"),
        Song("s28", "Dunia Yang Nanti", "Raim Laode", "Dunia Yang Nanti", 231, stream(12), listOf(0xFFED8936, 0xFFDD6B20), "Akustik"),
        Song("s29", "Jangan Paksa Rindu", "Ifan Seventeen", "Jangan Paksa Rindu", 249, stream(13), listOf(0xFF2D3748, 0xFF1A202C), "Galau"),
        Song("s30", "Amnesia (Acoustic Version)", "Whykustik", "Amnesia Acoustic", 202, stream(14), listOf(0xFF805AD5, 0xFF553C9A), "Akustik"),
        Song("s31", "Don Omar", "Mohit Kushwah", "Don Omar", 186, stream(15), listOf(0xFF3182CE, 0xFF2B6CB0), "Semangat"),
        Song("s32", "Sepi Tanpa Ko", "Kharis Sopan", "Sepi Tanpa Ko", 223, stream(16), listOf(0xFF9AE6B4, 0xFF38A169), "Galau"),
        Song("s33", "Hits of 2026 (So Far)", "Justin Bieber", "Hits of 2026", 268, stream(1), listOf(0xFF000000, 0xFF4A5568), "Pop"),
        Song("s34", "Hot Koplo", "Ajeng Febria", "Hot Koplo", 240, stream(2), listOf(0xFFE53E3E, 0xFFDD6B20), "Koplo"),
        Song("s35", "Hot Dangdut", "Laila Ayu", "Hot Dangdut", 251, stream(3), listOf(0xFFD69E2E, 0xFFB7791F), "Dangdut"),
        Song("s36", "DJ Campuran Viral Tik Tok 2024", "DJ Xavier Team", "DJ Campuran", 2141, stream(4), listOf(0xFFB83280, 0xFF702963), "Koplo"),
        Song("s37", "DJ Slow Remix Full Album Cocok", "Tabi Boy", "Slow Remix Full Album", 3364, stream(5), listOf(0xFF6B46C1, 0xFF44337A), "Koplo"),
        Song("s38", "La Hawla", "Sabyan", "La Hawla", 264, stream(6), listOf(0xFF38B2AC, 0xFF285E61), "Santai"),
        Song("s39", "Palestine Will Be Free", "Maher Zain", "Palestine Will Be Free", 246, stream(7), listOf(0xFF2B6CB0, 0xFF2C5282), "Santai"),
        Song("s40", "Smack That (feat. Eminem)", "Akon", "Konvicted", 212, stream(8), listOf(0xFF2F855A, 0xFF22543D), "Hip Hop"),
        Song("s41", "Low (feat. T-Pain)", "Flo Rida", "Mail on Sunday", 239, stream(9), listOf(0xFFDD6B20, 0xFF9C4221), "Hip Hop"),
        Song("s42", "Lose Yourself", "Eminem", "8 Mile", 326, stream(10), listOf(0xFF1A202C, 0xFF000000), "Hip Hop"),
        Song("s43", "Bukan Bukan", "Awan Axello", "Bukan Bukan", 208, stream(11), listOf(0xFF7B5CD6, 0xFF5B3FA8), "Pop"),
        Song("s44", "She Will Be Loved", "Maroon 5", "Songs About Jane", 257, stream(12), listOf(0xFF9B2C2C, 0xFF652B2B), "Rock"),
        Song("s45", "Ocean Eyes (Astronomyy Remix)", "Billie Eilish", "Ocean Eyes Remix", 201, stream(13), listOf(0xFFB794F4, 0xFF805AD5), "Galau"),
        Song("s46", "10,000 Hours", "Dan + Shay dan Justin Bieber", "10,000 Hours", 176, stream(14), listOf(0xFFECC94B, 0xFFB7791F), "Pop"),
        Song("s47", "Perfect", "Ed Sheeran", "Divide", 263, stream(15), listOf(0xFF2D3748, 0xFF4A5568), "Pop"),
        Song("s48", "Balikan", "Dycal", "Balikan", 231, stream(16), listOf(0xFFF687B3, 0xFFD53F8C), "Galau")
    ).map { it }

    fun byId(id: String): Song? = songs.firstOrNull { it.id == id }

    // ---- Bagian-bagian Beranda ----
    val moodChips = listOf("Relax", "Commute", "Energize", "Sleep", "Sad", "Feel good", "Focus", "Workout")

    val andalanmu: List<Song> = listOf("s01", "s02", "s03", "s04", "s05", "s06", "s07", "s08", "s09").map { byId(it)!! }

    val quickPicks: List<Song> = listOf("s01", "s02", "s03", "s04").map { byId(it)!! }

    val musicVideos: List<Song> = listOf("s11", "s12", "s13", "s14").map { byId(it)!! }

    val stations: List<Song> = listOf("s15", "s16").map { byId(it)!! }

    val newReleases: List<Song> = listOf("s17", "s18", "s19").map { byId(it)!! }

    val forgottenFavorites: List<Song> = listOf("s01", "s20", "s21").map { byId(it)!! }

    val coversRemixes: List<Song> = listOf("s22", "s23", "s24", "s25").map { byId(it)!! }

    val trendingPlaylists: List<MixCard> = listOf(
        MixCard("dugem", "Mahestri Nirwan", listOf(0xFFD53F8C, 0xFF553C9A)),
        MixCard("galau inggris", "Resti Laora Cahyeny", listOf(0xFFED8936, 0xFF9B2C2C)),
        MixCard("Sesi Potret (feat. Idgitaf)", "Fauzia Rachmah", listOf(0xFF2D3748, 0xFF718096))
    )

    val fromCommunity: List<MixCard> = listOf(
        MixCard("Pop Punk", "WildMyths", listOf(0xFFECC94B, 0xFF2B6CB0)),
        MixCard("mab", "hanz channel", listOf(0xFFE53E3E, 0xFF1A202C)),
        MixCard("indo jamet", "IL Bloger (ILo Mancay)", listOf(0xFFED64A6, 0xFF805AD5))
    )

    val genreChips = listOf(
        "Hanukkah", "Energize", "J-Rock", "Feel good", "Workout", "1950s",
        "Indonesian Pop", "Party", "German", "Family", "Sleep", "UK rap"
    )

    val similarArtist = "Dj HarrisNugraha"

    val similarMixes: List<MixCard> = listOf(
        MixCard("Dugem Mix", "rafi7 ahma7 (Origin's)", listOf(0xFF38A169, 0xFF1A202C)),
        MixCard("playlist dj angkot", "Bubble gum", listOf(0xFFD69E2E, 0xFF2F855A)),
        MixCard("Volume Mashup Hey Cowo", "Dj Lokal", listOf(0xFFE11D74, 0xFF9F1239))
    )

    val albumsForYou: List<Song> = listOf("s24", "s27", "s29").map { byId(it)!! }

    val hipHop: List<Song> = listOf("s40", "s41", "s42").map { byId(it)!! }

    val dailyDiscover: List<Song> = listOf("s26", "s27", "s28", "s29").map { byId(it)!! }

    val trendingSongs: List<Song> = listOf("s30", "s19", "s31", "s32").map { byId(it)!! }

    val indoHits: List<Song> = listOf("s33", "s34", "s35").map { byId(it)!! }

    val longListens: List<Song> = listOf("s36", "s37", "s20", "s22").map { byId(it)!! }

    val mixedForYou: List<MixCard> = listOf(
        MixCard("My Mix 1", "Adim MF", listOf(0xFF4A5568, 0xFF1A202C), "My Mix 01"),
        MixCard("My Supermix", "Don Omar", listOf(0xFFB7791F, 0xFF2D3748), "My Supermix"),
        MixCard("My Mix 3", "Illenium", listOf(0xFF9B2C2C, 0xFF1A202C), "My Mix 03")
    )

    val freshFinds: List<MixCard> = listOf(
        MixCard("Archive Mix", "Adim MF", listOf(0xFF276749, 0xFF1C4532), "Archive Mix"),
        MixCard("Replay Mix", "Crisbe", listOf(0xFF805AD5, 0xFFD53F8C), "Replay Mix"),
        MixCard("New Release Mix", "Jesxy", listOf(0xFF9F1239, 0xFF553C9A), "New Release Mix")
    )

    val livePerformances: List<Song> = listOf("s38", "s39", "s15", "s16").map { byId(it)!! }

    // ---- Pencarian ----
    val exploreGenres = listOf(
        "Pop", "Dangdut", "Hip Hop", "Rock", "Indie", "Santai",
        "Semangat", "Galau", "Akustik", "K-Pop", "Jazz", "Koplo", "Fokus", "Tidur"
    )

    fun search(query: String): List<Song> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return songs.filter {
            it.title.lowercase().contains(q) ||
                it.artist.lowercase().contains(q) ||
                it.album.lowercase().contains(q) ||
                it.genre.lowercase().contains(q)
        }
    }
}
