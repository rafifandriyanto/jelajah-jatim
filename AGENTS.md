# AGENTS.md: Aturan Pengembangan untuk Coding Agent

1. **Baca Spesifikasi Terlebih Dahulu:**
   Wajib membaca `tokens.css`, `Color.kt`/`Theme.kt`, `design.md`, dan `content.md` sebelum membuat atau mengubah komponen UI.
2. **Disiplin Warna & Token:**
   - Dilarang keras memakai warna heksadesimal baru di luar yang telah ditentukan di `Color.kt` (`JelajahColorTokens`).
   - Dilarang menggunakan palet bawaan di luar token semantik.
   - Gunakan semantic token: `--accent` (`colors.accent`) untuk aksi primer, `--bg-canvas` (`colors.bgCanvas`) untuk kanvas dasar.
3. **Standar Ergonomi Mobile:**
   - Seluruh elemen sentuh (tombol, chip kategori, ikon aksi) wajib memiliki luas sentuh minimal 44x44 pt (`>= 48.dp` pada Android).
   - Perhatikan notch, dynamic island, dan home indicator dengan `SafeAreaWrapper` (`WindowInsets.safeDrawing`).
4. **Tipografi & Format Angka:**
   - Seluruh angka (harga rupiah, tanggal, rating, durasi) wajib menerapkan `tabular-nums` (`tabularNums()`).
   - Format mata uang: `Rp 1.450.000` (menggunakan spasi dan titik pemisah ribuan).
5. **Anti-Slop & Larangan Desain:**
   - Dilarang menggunakan emoji Unicode langsung dalam kode antarmuka.
   - Dilarang membuat tombol dengan efek bayangan berpendar (glowing shadow), gradien pelangi, atau animasi memantul (bounce scale).
   - Label tombol wajib menggunakan gaya penulisan sentence case (contoh: `"Pesan sekarang"`, bukan `"PESAN SEKARANG"` atau `"Pesan Sekarang!"`).
6. **Cakupan Fitur:**
   - Jangan menambahkan halaman yang berada di luar cakupan MVP (misal: sistem tiket pesawat, multi-tier loyalty, obrolan AI palsu).
