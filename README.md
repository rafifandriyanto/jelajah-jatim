# 🏝️ Jelajah Jatim — Mobile App Eksplorasi Wisata Jawa Timur

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Gemini AI](https://img.shields.io/badge/AI-Google%20Gemini-orange.svg)](https://ai.google.dev/)

**Jelajah Jatim** adalah aplikasi Android modern berbasis Jetpack Compose untuk mengeksplorasi destinasi wisata, paket tur terkurasi, serta informasi kondisi alam terkini di wilayah Jawa Timur (Jember, Banyuwangi, Bromo, Malang, dan sekitarnya).

---

## 📱 App UI Mockups & Screen Showcase

Aplikasi dirancang dengan standar desain editorial minimalis, ramah seluler, dan responsif.

| 🏠 Beranda (Screen S-01) | 🏔️ Detail Destinasi (Screen S-02) | 🗺️ Itinerary & Tur (Screen S-03) |
| :---: | :---: | :---: |
| <pre>┌───────────────────────────┐<br>│ 👤 Halo, Vanessa          │<br>│ 🔍 Cari destinasi...      │<br>│ 🏷️ [Semua] [Jember] [Bromo]│<br>│ 📸 <b>Tanjung Papuma</b>       │<br>│    🟢 Kondisi Jalur Aman  │<br>│    ⭐ 5.0 (184 ulasan)    │<br>│    [Lihat detail  ➔]      │<br>└───────────────────────────┘</pre> | <pre>┌───────────────────────────┐<br>│ 🌊 <b>Tanjung Papuma</b>        │<br>│ 📍 Jember, Jawa Timur     │<br>│ 📄 Deskripsi & Galeri     │<br>│ 🌊 <i>Status Kondisi Alam</i>   │<br>│    • Gelombang 0.8-1.2m   │<br>│    • Simulasi Pasang      │<br>│ 📦 <b>Paket Tur Populer</b>    │<br>└───────────────────────────┘</pre> | <pre>┌───────────────────────────┐<br>│ 🚗 <b>Eksplor Jember</b>         │<br>│ 📅 3-Hari Tur Eksotis     │<br>│ ─────────────             │<br>│ 🔽 <b>Hari 1</b>: Kedatangan    │<br>│ 🔽 <b>Hari 2</b>: Sunrise Papuma│<br>│ 🔽 <b>Hari 3</b>: Agrowisata    │<br>│ 🔘 Add-on Jeep 4WD (+350k)│<br>│ [Pesan tur ini]           │<br>└───────────────────────────┘</pre> |

---

## ✨ Fitur Unggulan

- **🔍 Eksplorasi Destinasi & Tur**:
  - Filter interaktif berdasarkan wilayah (*Jember, Banyuwangi, Bromo, Malang*).
  - *Stacked Peeking Hero Carousel* dengan dukungan pencarian terpadu.

- **🌊 Informasi & Monitoring Kondisi Alam Real-time**:
  - Peringatan pasang laut & keamanan jalur trekking.
  - Rekomendasi aksesibilitas kendaraan (misal: *Akses Roda 4 Standar vs 4WD Off-road*).

- **📴 Mode Luring (Offline Vault)**:
  - Kemampuan menyimpan rincian tur dan destinasi untuk diakses tanpa koneksi internet saat berada di lokasi terpencil.

- **🤖 Google Gemini AI Integration**:
  - Rekomendasi perjalanan cerdas dan rekomendasi tur kustom berbasis AI Studio Gemini API.

- **🎨 Standar Desain Ergonomis**:
  - Dibangun sesuai token semantik tanpa warna liar.
  - Format angka & mata uang rapi dengan `tabular-nums` (`Rp 1.450.000`).
  - *Touch target* minimal 44x44 pt (`>= 48.dp`).

---

## 🚀 Cara Menjalankan Project

### Prerequisites
- [Android Studio Ladybug (atau versi lebih baru)](https://developer.android.com/studio)
- JDK 11 atau lebih baru
- Android SDK 36

### Langkah Setup & Run Local

1. **Clone Repository**:
   ```bash
   git clone https://github.com/rafifandriyanto/jelajah-jatim.git
   cd jelajah-jatim
   ```

2. **Konfigurasi Gemini API Key**:
   Buka file `.env` di folder utama project dan masukkan API Key Gemini Anda:
   ```env
   GEMINI_API_KEY=API_KEY_GEMINI_ANDA
   ```

3. **Buka Project di Android Studio**:
   - Buka Android Studio -> **Open** -> Pilih direktori project `jelajah-jatim`.
   - Lakukan **Gradle Sync** (`File` -> `Sync Project with Gradle Files`).

4. **Jalankan Aplikasi**:
   - Pilih emulator Android atau hubungkan perangkat fisik via USB Debugging.
   - Tekan tombol **Run** (Play Hijau) atau gunakan shortcut `Shift + F10`.

---

## 🛠️ Arsitektur & Teknologi

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **Local Storage / Offline Vault**: [Room Database](https://developer.android.com/training/data-storage/room) & Coroutines Flow
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Configuration & Secrets**: Secrets Gradle Plugin (Membaca `.env`)
- **AI Engine**: Google Gemini API via `BuildConfig.GEMINI_API_KEY`

---

## 📄 Lisensi

Project ini dikembangkan untuk eksplorasi pariwisata Jawa Timur. All rights reserved.
