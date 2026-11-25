# Auralys Music 🎧

Aplikasi musik **native Android** (bukan WebView) bergaya YouTube Music / Spotify
dengan tema terang, dibangun penuh menggunakan **Kotlin** dan **Jetpack**:

| Teknologi | Kegunaan |
|---|---|
| Jetpack Compose + Material 3 | Seluruh UI (4 tab + layar pemutar + pengaturan) |
| Jetpack Navigation (Compose) | Navigasi antar layar |
| Jetpack Lifecycle + ViewModel + StateFlow | Arsitektur MVVM reaktif |
| Jetpack Room | Lagu disukai & riwayat pemutaran (persisten) |
| Jetpack DataStore | Preferensi pengaturan (sinkronisasi, crossfade, timer tidur, dll.) |
| AndroidX Media3 (ExoPlayer + MediaSession) | Pemutaran audio latar belakang + notifikasi media |

## Fitur sesuai tampilan referensi

- **Beranda** — sapaan waktu nyata, chip suasana, rak *Andalanmu*, *Mix untukmu*,
  kartu *Liked Music*, *Quick picks*, *Music videos for you*, *New releases*,
  *Forgotten favorites*, *Covers and remixes*, *Trending community playlists*,
  *Suasana dan genre*, *Albums for you*, *Hip-hop*, *Your daily discover*,
  *Trending songs*, *Today's Indonesian Hits*, *Long listens*, *Mixed for you*,
  *Fresh finds*, *Live performances* + tombol melayang mikrofon/cast.
- **Bilah pemutar mini** melayang dengan garis progres, tombol suka, putar/jeda,
  dan lewati — persis seperti referensi; ketuk untuk membuka **layar pemutar penuh**.
- **Navigasi bawah bentuk pil** dengan indikator aktif (Beranda, Cari, Pustaka, Kamu).
- **Cari** — toggle *YouTube/Perangkat*, riwayat pencarian, grid *Jelajahi semua*,
  pencarian langsung ke katalog, empty state ilustrasi.
- **Pustaka** — tab *Semua/Playlist/Lagu/Album/Artis/Podcast*, sortir, kartu playlist,
  daftar *Koleksi* (Disukai, Diunduh, Cache, Di perangkat, Teratas 50, Diunggah), FAB +.
- **Kamu** — Riwayat, Statistik & recap, Dengar bersama, Kenali lagu, Ekualiser,
  akun + sakelar sinkronisasi, Pengaturan, Tentang.
- **Layar pendukung** — Riwayat (Lokal/Online, grup bulan), Statistik (recap, periode,
  jumlah lagu/artis/album), Bersama (hubungkan sesi), Kenali musik (mikrofon besar),
  Ekualiser (grafik frekuensi), Pengaturan lengkap, **Pemutar dan audio**
  (kualitas audio, hemat data, crossfade, slider durasi riwayat, timer tidur, alarm…).

## Menjalankan proyek

1. Instal **Android Studio** (Koala / Ladybug atau lebih baru).
2. Buka proyek ini: *File → Open* → pilih folder hasil ekstrak ZIP.
3. Biarkan Gradle sync (butuh JDK 17 yang sudah dibundel Android Studio).
4. Jalankan di emulator/perangkat **Android 7.0 (API 24)+**.

Atau via terminal:

```bash
./gradlew assembleDebug     # menghasilkan app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug      # pasang langsung ke perangkat terhubung
```

> Katalog demo memutar stream audio uji publik (SoundHelix) sehingga pemutaran,
> notifikasi media, riwayat, dan antrean benar-benar berfungsi tanpa berkas lokal.
> Sampul album digambar native sebagai gradien (tanpa aset gambar eksternal).

## Upload ke GitHub

```bash
git init
git add .
git commit -m "Auralys Music: aplikasi musik native Kotlin + Jetpack"
git branch -M main
git remote add origin https://github.com/USERNAME/REPO.git
git push -u origin main
```

## Struktur paket

```
com.auralys.music
├── AuralysApp.kt            # Application: inisialisasi repositori
├── MainActivity.kt          # Activity tunggal (edge-to-edge, izin notifikasi)
├── data/                    # Model, katalog demo, Room, DataStore, MusicRepository
├── playback/                # PlaybackService (Media3 MediaSessionService)
└── ui/
    ├── MainScreen.kt        # NavHost + bilah pemutar mini + nav bawah pil
    ├── theme/               # Warna & tipografi tema terang
    ├── components/          # Sampul gradien, rak, baris lagu, empty state
    ├── home/ search/ library/ profile/ player/
    └── subscreens/          # Riwayat, Statistik, Bersama, Kenali, Ekualiser, Pengaturan
```
# Musiki
