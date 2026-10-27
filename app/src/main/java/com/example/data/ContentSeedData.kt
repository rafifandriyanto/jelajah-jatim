package com.example.data

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Bank Data Konten & Model Data Lengkap (14 Destinasi Jember & Jawa Timur + Paket Tur + Metode Pembayaran)
 * Sumber: PRD.md & content.md
 */
enum class TerrainType(val code: String, val label: String) {
    COASTAL("coastal", "Pesisir berkarang & perbukitan rendah"),
    FOREST("forest", "Jalur tanah berbatu & sungai kecil"),
    MOUNTAIN("mountain", "Trekking gunung berbatu (kemiringan 20 - 35 derajat)")
}

enum class AccessRequirement(val code: String, val badgeLabel: String, val shortTag: String) {
    STANDARD("standard", "Akses Standar Roda 4 & Bus Pariwisata", "Akses Roda 4 Standar"),
    FOUR_WD_ONLY("4wd_only", "Wajib Kendaraan 4WD / Off-Road", "Wajib Armada 4WD"),
    BOAT_ONLY("boat_only", "Perahu Nelayan / Jalur Laut", "Akses Perahu Lokal")
}

enum class NatureStatusTone {
    SAFE,      // --success / --success-surface
    WARNING,   // --warning / --warning-surface
    DANGER     // --danger / --danger-surface
}

enum class VisualSceneType {
    PAPUMA_COAST,
    PAPUMA_BOATS,
    WATU_ULO_COAST,
    MERU_BETIRI_FOREST,
    MERU_BETIRI_BAY,
    SUKAMADE_TURTLE_BEACH,
    IJEN_CRATER,
    BALURAN_SAVANNA,
    PULAU_MERAH_BEACH,
    BROMO_CALDERA,
    TUMPAK_SEWU_WATERFALL,
    MALANG_HIGHLAND,
    BALEKAMBANG_BEACH,
    WONOSARI_TEA_HIGHLAND,
    TELUK_LOVE_SUNSET,
    PAYANGAN_SAMBOJA,
    REMBANGAN_HIGHLAND
}

data class PhotoGalleryItem(
    val sceneType: VisualSceneType,
    val caption: String
)

enum class PaymentMethodCategory(val label: String) {
    QRIS("QRIS Instan"),
    VIRTUAL_ACCOUNT("Virtual Account Bank"),
    EWALLET("Dompet Digital (E-Wallet)")
}

data class PaymentMethodOption(
    val id: String,
    val name: String,
    val subtitle: String,
    val category: PaymentMethodCategory,
    val accountNumberOrNmid: String,
    val adminFee: Long,
    val badgeText: String
)

data class NatureStatusInfo(
    val id: String,
    val destinationId: String,
    val badgeText: String,
    val description: String,
    val routeContext: String,
    val tone: NatureStatusTone,
    val lastUpdated: String,
    val waveOrElevationMetric: String,
    val alternateRouteDestinationId: String? = null,
    val alternateRouteLabel: String? = null
)

data class ActivitySession(
    val timeOfDayLabel: String, // "Pagi", "Siang", "Sore"
    val timeSchedule: String,   // "08:30 WIB"
    val description: String,
    val locationName: String,
    val requires4wdBadge: Boolean = false,
    val guideAvailableBadge: Boolean = false
)

data class DaySchedule(
    val dayNumber: Int,
    val title: String,
    val sceneType: VisualSceneType,
    val elevationOrDistanceMeta: String,
    val activities: List<ActivitySession>
)

data class AddonOption(
    val id: String,
    val title: String,
    val subtitle: String,
    val pricePerPerson: Long,
    val badgeLabel: String
)

data class TourPackage(
    val id: String,
    val destinationId: String,
    val title: String,
    val durationDays: Int,
    val durationLabel: String,
    val departureDatesLabel: String,
    val availabilityBadge: String,
    val pricePerPerson: Long,
    val rating: Double,
    val reviewCount: Int,
    val sceneType: VisualSceneType,
    val requiresPhysicalAnd4wdWarning: Boolean,
    val facilitiesList: List<String>,
    val hotelName: String,
    val hotelRatingLabel: String,
    val pickupPoint: String,
    val terrainAdvisory: String,
    val emergencyContactName: String,
    val emergencyContactNumber: String,
    val gpsCoordinates: String,
    val addonOption: AddonOption,
    val itineraryDays: List<DaySchedule>
)

data class DestinationSeed(
    val id: String,
    val name: String,
    val shortCardTitle: String,
    val city: String,
    val province: String,
    val locationBadge: String,
    val fullLocation: String,
    val regionCategory: String,
    val rating: Double,
    val reviewCount: Int,
    val description: String,
    val terrainType: TerrainType,
    val accessRequirement: AccessRequirement,
    val recommendedHours: String,
    val sceneType: VisualSceneType,
    val isFeaturedHero: Boolean,
    val natureStatus: NatureStatusInfo
)

object ContentSeedData {
    // 1. Navigasi & Label Antarmuka (Microcopy - content.md Section 1)
    const val USER_GREETING = "Halo, Vanessa"
    const val HOME_SUBTITLE = "Jelajahi keindahan Jawa Timur"
    const val SEARCH_PLACEHOLDER = "Cari destinasi atau paket tur..."
    const val SECTION_NEXT_DESTINATION = "Pilih destinasi berikutnya"
    const val SECTION_POPULAR_TOURS = "Paket Tur Populer"
    const val ACTION_SEE_ALL = "Lihat semua"

    val REGION_CATEGORIES = listOf(
        "Semua",
        "Jember",
        "Banyuwangi",
        "Bromo",
        "Malang"
    )

    // Label tombol wajib sentence case (AGENTS.md)
    const val CTA_VIEW_DETAIL = "Lihat detail"
    const val CTA_BOOK_NOW = "Pesan sekarang"
    const val CTA_BOOK_THIS_TOUR = "Pesan tur ini"
    const val CTA_READ_MORE = "Baca selengkapnya"
    const val CTA_HIDE = "Sembunyikan"
    const val CTA_SAVE_OFFLINE = "Simpan jadwal offline"
    const val CTA_SAVED_OFFLINE = "Tersimpan di perangkat"
    const val CTA_OPEN_OFFLINE = "Buka jadwal offline"
    const val CTA_EXPLORE_JEMBER = "Lihat wisata Jember"
    const val CTA_PROCEED_PAYMENT = "Lanjut ke pembayaran"
    const val CTA_PAY_NOW = "Bayar sekarang"
    const val CTA_CONFIRM_BOOKING = "Konfirmasi pesanan"
    const val CTA_CONTACT_MITRA = "Hubungi mitra resmi"
    const val CTA_VIEW_ETICKET = "Lihat e-tiket & jadwal offline"
    const val CTA_ALTERNATE_ROUTE = "Lihat rute alternatif"
    const val CTA_RESET_FILTER = "Atur ulang filter"
    const val CTA_APPLY_FILTER = "Terapkan filter"

    val TOUR_TABS = listOf(
        "Jadwal tur",
        "Akomodasi",
        "Info penting"
    )

    // Pilihan Metode Pembayaran Resmi
    val PAYMENT_METHODS = listOf(
        PaymentMethodOption(
            id = "pay-qris",
            name = "QRIS Instan Nasional",
            subtitle = "Scan via BCA Mobile, Livin' Mandiri, BRImo, GoPay, OVO, atau Dana",
            category = PaymentMethodCategory.QRIS,
            accountNumberOrNmid = "NMID: ID202609184721 • PT Jelajah Jatim Nusantara",
            adminFee = 0L,
            badgeText = "Bebas Biaya Admin"
        ),
        PaymentMethodOption(
            id = "pay-va-bca",
            name = "BCA Virtual Account",
            subtitle = "Verifikasi otomatis 24 jam melalui m-BCA, KlikBCA, atau ATM",
            category = PaymentMethodCategory.VIRTUAL_ACCOUNT,
            accountNumberOrNmid = "8801 0812 3456 7890",
            adminFee = 4000L,
            badgeText = "Konfirmasi Instan"
        ),
        PaymentMethodOption(
            id = "pay-va-mandiri",
            name = "Mandiri Virtual Account",
            subtitle = "Pembayaran praktis melalui aplikasi Livin' by Mandiri",
            category = PaymentMethodCategory.VIRTUAL_ACCOUNT,
            accountNumberOrNmid = "8902 0812 3456 7890",
            adminFee = 4000L,
            badgeText = "Otomatis"
        ),
        PaymentMethodOption(
            id = "pay-va-bni",
            name = "BNI & BRI Virtual Account",
            subtitle = "Mendukung Wondr by BNI dan BRImo seluruh Indonesia",
            category = PaymentMethodCategory.VIRTUAL_ACCOUNT,
            accountNumberOrNmid = "8810 0812 3456 7890",
            adminFee = 4000L,
            badgeText = "Otomatis"
        ),
        PaymentMethodOption(
            id = "pay-ewallet-gopay",
            name = "GoPay / ShopeePay / Dana",
            subtitle = "Tautkan langsung ke aplikasi dompet digital di ponsel Anda",
            category = PaymentMethodCategory.EWALLET,
            accountNumberOrNmid = "0812-3456-7890 (Terhubung)",
            adminFee = 0L,
            badgeText = "1-Tap Bayar"
        )
    )

    // 3. Pesan Error, Loading & Mode Luring (content.md Section 3 & PRD.md 2.2)
    const val OFFLINE_BANNER_SIMPLE = "Mode Luring Aktif. Jadwal tersimpan aman."
    const val OFFLINE_TITLE = "Sinyal Seluler Terbatas"
    const val OFFLINE_DESCRIPTION =
        "Anda sedang berada di area minim sinyal. Jadwal tur dan titik darurat telah tersimpan di memori perangkat."

    const val EMPTY_SEARCH_TITLE = "Destinasi Belum Ditemukan"
    const val EMPTY_SEARCH_DESCRIPTION =
        "Kami belum menemukan hasil untuk kata kunci tersebut. Coba pilih kategori populer di bawah ini."

    // 2. Status Kondisi Alam & Akses Medan (content.md Section 2)
    val NATURE_STATUS_PAPUMA_SAFE = NatureStatusInfo(
        id = "status-safe-papuma",
        destinationId = "dest-jember-001",
        badgeText = "Kondisi Jalur Aman",
        description = "Ombak pesisir selatan tenang (0.8 - 1.2 meter). Akses trekking bukit kering dan aman.",
        routeContext = "Akses Standar Roda 4 & Bus Pariwisata",
        tone = NatureStatusTone.SAFE,
        lastUpdated = "Diperbarui 10 menit lalu • 09:30 WIB",
        waveOrElevationMetric = "Gelombang 0.8 - 1.2 m • Angin 12 knot"
    )

    val NATURE_STATUS_PAPUMA_HIGH_TIDE = NatureStatusInfo(
        id = "status-warning-tide",
        destinationId = "dest-jember-001",
        badgeText = "Pasang Air Laut Tinggi",
        description = "Hindari area bibir karang Tanjung Papuma setelah pukul 16:30 WIB.",
        routeContext = "Peringatan Pasang Sore Pesisir Selatan",
        tone = NatureStatusTone.WARNING,
        lastUpdated = "Peringatan berlaku 16:30 - 19:00 WIB",
        waveOrElevationMetric = "Pasang 2.1 m • Waspada Karang Malikan",
        alternateRouteDestinationId = "dest-jember-001",
        alternateRouteLabel = "Gunakan gardu pandang atas Siti Hinggil"
    )

    val NATURE_STATUS_BANDEALIT_4WD = NatureStatusInfo(
        id = "status-warning-4wd",
        destinationId = "dest-jember-002",
        badgeText = "Wajib Kendaraan 4WD / Off-Road",
        description = "Jalur tanah berbatu & lintasan sungai kecil menuju Teluk Bandealit wajib menggunakan armada Jeep 4x4 atau motor trail lokal.",
        routeContext = "Khusus rute Bandealit & Sukamade",
        tone = NatureStatusTone.WARNING,
        lastUpdated = "Pos Ranger Meru Betiri • 08:45 WIB",
        waveOrElevationMetric = "Medan Off-Road 14 km • Teluk Tenang 0.5 m",
        alternateRouteDestinationId = "dest-jember-001",
        alternateRouteLabel = "Alihkan ke Rute Aspal Tanjung Papuma"
    )

    val NATURE_STATUS_IJEN_WARNING = NatureStatusInfo(
        id = "status-warning-ijen",
        destinationId = "dest-jatim-003",
        badgeText = "Waspada Gas Belerang Kawah",
        description = "Aktivitas hembusan solfatara sedang. Wisatawan wajib mengenakan masker gas respirator standar dan mengikuti arahan pemandu lokal.",
        routeContext = "Pos Paltuding - Kawah Ijen",
        tone = NatureStatusTone.WARNING,
        lastUpdated = "Pos Pengamatan Gunung Api • 05:00 WIB",
        waveOrElevationMetric = "Elevasi 2.386 mdpl • Suhu 9°C",
        alternateRouteDestinationId = "dest-bwi-010",
        alternateRouteLabel = "Rute Alternatif: Savana Bekol Baluran"
    )

    val NATURE_STATUS_BROMO_SAFE = NatureStatusInfo(
        id = "status-safe-bromo",
        destinationId = "dest-bromo-004",
        badgeText = "Kondisi Jalur Aman",
        description = "Langit cerah di Penanjakan 1 & Lautan Pasir Berbisik. Jarak pandang matahari terbit optimal.",
        routeContext = "Wajib Kendaraan 4WD / Off-Road",
        tone = NatureStatusTone.SAFE,
        lastUpdated = "Balai Besar TNBTS • 06:00 WIB",
        waveOrElevationMetric = "Elevasi 2.329 mdpl • Suhu 11°C"
    )

    val NATURE_STATUS_MALANG_SAFE = NatureStatusInfo(
        id = "status-safe-malang",
        destinationId = "dest-malang-005",
        badgeText = "Kondisi Jalur Aman",
        description = "Akses jalan aspal mulus menuju dataran tinggi Pujon & Coban Rondo. Udara sejuk dengan visibilitas baik.",
        routeContext = "Akses Standar Roda 4 & Bus Pariwisata",
        tone = NatureStatusTone.SAFE,
        lastUpdated = "Dishub Malang Raya • 08:15 WIB",
        waveOrElevationMetric = "Elevasi 1.150 mdpl • Suhu 18°C"
    )

    val NATURE_STATUS_LIST = listOf(
        NATURE_STATUS_PAPUMA_SAFE,
        NATURE_STATUS_PAPUMA_HIGH_TIDE,
        NATURE_STATUS_BANDEALIT_4WD,
        NATURE_STATUS_IJEN_WARNING,
        NATURE_STATUS_BROMO_SAFE
    )

    // 4. Seed Data Realistis 14 Destinasi (Jember, Banyuwangi, Bromo, Malang)
    val DESTINATIONS = listOf(
        // === JEMBER (5 Destinasi) ===
        DestinationSeed(
            id = "dest-jember-001",
            name = "Tanjung Papuma",
            shortCardTitle = "Tanjung Papuma & Karang Malikan",
            city = "Jember",
            province = "Jawa Timur",
            locationBadge = "Jember, Jawa Timur",
            fullLocation = "Wuluhan, Jember, Jawa Timur",
            regionCategory = "Jember",
            rating = 5.0,
            reviewCount = 184,
            description = "Tanjung Papuma (Pasir Putih Malikan) memadukan hamparan pasir putih bersih dengan formasi gugusan batu karang menjulang tinggi di Samudra Hindia. Terkenal sebagai salah satu titik matahari terbenam paling dramatis di pesisir selatan Jawa Timur dengan latar hutan lindung yang masih asri.",
            terrainType = TerrainType.COASTAL,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "05:00 - 08:00 WIB (Sunrise) & 16:30 - 18:00 WIB (Sunset)",
            sceneType = VisualSceneType.PAPUMA_COAST,
            isFeaturedHero = true,
            natureStatus = NATURE_STATUS_PAPUMA_SAFE
        ),
        DestinationSeed(
            id = "dest-jember-002",
            name = "Taman Nasional Meru Betiri & Teluk Bandealit",
            shortCardTitle = "Meru Betiri & Teluk Bandealit",
            city = "Jember",
            province = "Jawa Timur",
            locationBadge = "Jember, Jawa Timur",
            fullLocation = "Tempurejo, Jember, Jawa Timur",
            regionCategory = "Jember",
            rating = 4.8,
            reviewCount = 92,
            description = "Kawasan konservasi hutan hujan tropis dataran rendah terakhir di Pulau Jawa. Rumah bagi flora langka dan satwa liar, bermuara di pesisir tenang Teluk Bandealit yang ideal untuk kano dan penjelajahan alam bebas.",
            terrainType = TerrainType.FOREST,
            accessRequirement = AccessRequirement.FOUR_WD_ONLY,
            recommendedHours = "06:00 - 15:30 WIB (Lintas Hutan & Kano)",
            sceneType = VisualSceneType.MERU_BETIRI_FOREST,
            isFeaturedHero = true,
            natureStatus = NATURE_STATUS_BANDEALIT_4WD
        ),
        DestinationSeed(
            id = "dest-jember-006",
            name = "Teluk Love & Bukit Domba Payangan",
            shortCardTitle = "Teluk Love & Bukit Samboja",
            city = "Jember",
            province = "Jawa Timur",
            locationBadge = "Ambulu, Jember",
            fullLocation = "Pantai Payangan, Ambulu, Jember, Jawa Timur",
            regionCategory = "Jember",
            rating = 4.9,
            reviewCount = 164,
            description = "Gardu pandang perbukitan hijau Bukit Domba dan Bukit Samboja yang menghadap langsung ke lekukan pesisir berbentuk hati alami di Teluk Love Payangan. Spot favorit menikmati senja pesisir selatan dan kuliner ikan bakar nelayan lokal.",
            terrainType = TerrainType.COASTAL,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "15:00 - 18:00 WIB (Trekking Sore & Golden Sunset)",
            sceneType = VisualSceneType.TELUK_LOVE_SUNSET,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_PAPUMA_SAFE
        ),
        DestinationSeed(
            id = "dest-jember-007",
            name = "Pantai Watu Ulo & Karang Naga",
            shortCardTitle = "Pantai Watu Ulo Jember",
            city = "Jember",
            province = "Jawa Timur",
            locationBadge = "Ambulu, Jember",
            fullLocation = "Sumberrejo, Ambulu, Jember, Jawa Timur",
            regionCategory = "Jember",
            rating = 4.7,
            reviewCount = 128,
            description = "Pantai legendaris di pesisir selatan Jember dengan formasi batu karang panjang menyerupai sisik ular raksasa yang menjorok langsung ke deburan ombak Samudra Hindia, berdampingan dengan Tanjung Papuma.",
            terrainType = TerrainType.COASTAL,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "06:00 - 17:00 WIB (Panorama Karang & Angin Laut)",
            sceneType = VisualSceneType.WATU_ULO_COAST,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_PAPUMA_HIGH_TIDE
        ),
        DestinationSeed(
            id = "dest-jember-008",
            name = "Agrowisata Puncak Rembangan & Kebun Kopi",
            shortCardTitle = "Puncak Rembangan & Kebun Kopi",
            city = "Jember",
            province = "Jawa Timur",
            locationBadge = "Arjasa, Jember",
            fullLocation = "Kemuning Lor, Arjasa, Jember, Jawa Timur",
            regionCategory = "Jember",
            rating = 4.8,
            reviewCount = 145,
            description = "Kawasan peristirahatan dataran tinggi di kaki Pegunungan Hyang Argopuro dengan perkebunan kopi robusta, kebun buah naga, peternakan sapi perah, serta pemandian air pegunungan yang sejuk.",
            terrainType = TerrainType.MOUNTAIN,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "06:30 - 11:00 WIB (Kabut Pagi & Tur Kebun Kopi)",
            sceneType = VisualSceneType.REMBANGAN_HIGHLAND,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_PAPUMA_SAFE
        ),

        // === BANYUWANGI (4 Destinasi) ===
        DestinationSeed(
            id = "dest-jatim-003",
            name = "Kawah Ijen & Blue Fire",
            shortCardTitle = "Kawah Ijen & Blue Fire",
            city = "Banyuwangi",
            province = "Jawa Timur",
            locationBadge = "Banyuwangi - Bondowoso",
            fullLocation = "Perbatasan Banyuwangi - Bondowoso, Jawa Timur",
            regionCategory = "Banyuwangi",
            rating = 4.9,
            reviewCount = 312,
            description = "Danau kawah asam terbesar di dunia dengan fenomena api biru elektrik langka yang hanya terlihat sebelum fajar menyingsing.",
            terrainType = TerrainType.MOUNTAIN,
            accessRequirement = AccessRequirement.FOUR_WD_ONLY,
            recommendedHours = "01:30 - 06:30 WIB (Pendakian Fajar)",
            sceneType = VisualSceneType.IJEN_CRATER,
            isFeaturedHero = true,
            natureStatus = NATURE_STATUS_IJEN_WARNING
        ),
        DestinationSeed(
            id = "dest-bwi-009",
            name = "Pantai Sukamade & Konservasi Penyu",
            shortCardTitle = "Pantai Sukamade Meru Betiri",
            city = "Banyuwangi",
            province = "Jawa Timur",
            locationBadge = "Pesanggaran, Banyuwangi",
            fullLocation = "Sarongan, Pesanggaran, Taman Nasional Meru Betiri",
            regionCategory = "Banyuwangi",
            rating = 4.9,
            reviewCount = 118,
            description = "Pantai tersembunyi di jantung Taman Nasional Meru Betiri yang menjadi lokasi pendaratan dan peneluran empat spesies penyu laut langka. Perjalanan menuju Sukamade menembus sungai dan hutan hujan dengan Jeep 4x4.",
            terrainType = TerrainType.FOREST,
            accessRequirement = AccessRequirement.FOUR_WD_ONLY,
            recommendedHours = "19:30 - 23:00 WIB (Observasi Penyu) & 06:00 WIB (Pelepasan Tukik)",
            sceneType = VisualSceneType.SUKAMADE_TURTLE_BEACH,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_BANDEALIT_4WD
        ),
        DestinationSeed(
            id = "dest-bwi-010",
            name = "Taman Nasional Baluran & Savana Bekol",
            shortCardTitle = "Savana Bekol Baluran",
            city = "Banyuwangi",
            province = "Jawa Timur",
            locationBadge = "Banyuwangi - Situbondo",
            fullLocation = "Banyuputih, Perbatasan Situbondo - Banyuwangi, Jawa Timur",
            regionCategory = "Banyuwangi",
            rating = 4.8,
            reviewCount = 276,
            description = "Dijuluki Africa van Java, hamparan padang savana luas berlatar Gunung Baluran ini menjadi habitat alami rusa timor, banteng jawa, dan merak hijau, bermuara langsung ke hutan mangrove Pantai Bama.",
            terrainType = TerrainType.FOREST,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "06:30 - 10:00 WIB & 15:00 - 17:30 WIB (Golden Hour Savana)",
            sceneType = VisualSceneType.BALURAN_SAVANNA,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_PAPUMA_SAFE
        ),
        DestinationSeed(
            id = "dest-bwi-011",
            name = "Pantai Pulau Merah & Teluk Hijau",
            shortCardTitle = "Pantai Pulau Merah Banyuwangi",
            city = "Banyuwangi",
            province = "Jawa Timur",
            locationBadge = "Pesanggaran, Banyuwangi",
            fullLocation = "Sumberagung, Pesanggaran, Banyuwangi, Jawa Timur",
            regionCategory = "Banyuwangi",
            rating = 4.8,
            reviewCount = 204,
            description = "Pantai berpasir lembut dengan bukit pulau kecil bertanah merah di tengah bibir pantai yang dapat dijangkau dengan berjalan kaki saat air surut, terkenal dengan ombak selancar ramah pemula dan sunset jingga.",
            terrainType = TerrainType.COASTAL,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "15:00 - 18:00 WIB (Surut Sore & Sunset)",
            sceneType = VisualSceneType.PULAU_MERAH_BEACH,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_PAPUMA_SAFE
        ),

        // === BROMO & SEMERU (2 Destinasi) ===
        DestinationSeed(
            id = "dest-bromo-004",
            name = "Kaldera Bromo & Bukit Kingkong",
            shortCardTitle = "Kaldera Bromo Tengger",
            city = "Probolinggo",
            province = "Jawa Timur",
            locationBadge = "Bromo, Jawa Timur",
            fullLocation = "Taman Nasional Bromo Tengger Semeru, Jawa Timur",
            regionCategory = "Bromo",
            rating = 4.9,
            reviewCount = 428,
            description = "Bentang alam kaldera vulkanik purba dengan lautan pasir luas dan panorama matahari terbit berlatar Gunung Batok serta puncak tertinggi Pulau Jawa, Gunung Semeru.",
            terrainType = TerrainType.MOUNTAIN,
            accessRequirement = AccessRequirement.FOUR_WD_ONLY,
            recommendedHours = "03:30 - 09:00 WIB (Golden Sunrise & Kawah)",
            sceneType = VisualSceneType.BROMO_CALDERA,
            isFeaturedHero = true,
            natureStatus = NATURE_STATUS_BROMO_SAFE
        ),
        DestinationSeed(
            id = "dest-bromo-012",
            name = "Air Terjun Tumpak Sewu & Goa Tetes",
            shortCardTitle = "Air Terjun Tumpak Sewu",
            city = "Lumajang",
            province = "Jawa Timur",
            locationBadge = "Lereng Semeru, Jatim",
            fullLocation = "Sidomulyo, Pronojiwo, Perbatasan Lumajang - Malang",
            regionCategory = "Bromo",
            rating = 4.9,
            reviewCount = 340,
            description = "Formasi air terjun tirai melingkar setinggi 120 meter di lembah ngarai tropis kaki Gunung Semeru. Menyajikan pemandangan megah dari gardu pandang atas maupun trekking menyusuri aliran sungai menuju dasar tebing.",
            terrainType = TerrainType.MOUNTAIN,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "06:30 - 11:00 WIB (Cahaya Pagi Tanpa Kabut)",
            sceneType = VisualSceneType.TUMPAK_SEWU_WATERFALL,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_BROMO_SAFE
        ),

        // === MALANG (3 Destinasi) ===
        DestinationSeed(
            id = "dest-malang-005",
            name = "Lembah Pinus Pujon & Coban Rondo",
            shortCardTitle = "Dataran Tinggi Pujon & Coban Rondo",
            city = "Malang",
            province = "Jawa Timur",
            locationBadge = "Pujon, Malang",
            fullLocation = "Pandansari, Pujon, Kabupaten Malang, Jawa Timur",
            regionCategory = "Malang",
            rating = 4.7,
            reviewCount = 156,
            description = "Perpaduan air terjun pegunungan setinggi 84 meter yang dikelilingi hutan pinus rimbun serta agrowisata dataran tinggi yang sejuk dan ramah keluarga.",
            terrainType = TerrainType.FOREST,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "07:00 - 16:00 WIB (Udara Pagi & Trekking Hutan)",
            sceneType = VisualSceneType.MALANG_HIGHLAND,
            isFeaturedHero = true,
            natureStatus = NATURE_STATUS_MALANG_SAFE
        ),
        DestinationSeed(
            id = "dest-malang-013",
            name = "Pantai Balekambang & Pulau Ismoyo",
            shortCardTitle = "Pantai Balekambang Malang",
            city = "Malang",
            province = "Jawa Timur",
            locationBadge = "Bantur, Malang Selatan",
            fullLocation = "Srigonco, Bantur, Kabupaten Malang, Jawa Timur",
            regionCategory = "Malang",
            rating = 4.8,
            reviewCount = 219,
            description = "Pantai ikonik di pesisir selatan Kabupaten Malang dengan jembatan panjang yang menghubungkan bibir pantai menuju Pura Amarta Jati di atas pulau karang Ismoyo.",
            terrainType = TerrainType.COASTAL,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "07:00 - 17:30 WIB (Jalur Lintas Selatan Mulus)",
            sceneType = VisualSceneType.BALEKAMBANG_BEACH,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_MALANG_SAFE
        ),
        DestinationSeed(
            id = "dest-malang-014",
            name = "Kebun Teh Wonosari & Lereng Arjuno",
            shortCardTitle = "Kebun Teh Wonosari Lawang",
            city = "Malang",
            province = "Jawa Timur",
            locationBadge = "Lawang, Malang",
            fullLocation = "Toyomarto, Singosari - Lawang, Kabupaten Malang",
            regionCategory = "Malang",
            rating = 4.8,
            reviewCount = 178,
            description = "Hamparan perkebunan teh hijau di ketinggian 1.050 mdpl lereng Gunung Arjuno. Dilengkapi jalur jalan pagi di sela pucuk teh, tur pabrik pengolahan teh kolonial, dan pemandian air hangat.",
            terrainType = TerrainType.MOUNTAIN,
            accessRequirement = AccessRequirement.STANDARD,
            recommendedHours = "06:00 - 10:30 WIB (Sunrise & Petik Pucuk Teh)",
            sceneType = VisualSceneType.WONOSARI_TEA_HIGHLAND,
            isFeaturedHero = false,
            natureStatus = NATURE_STATUS_MALANG_SAFE
        )
    )

    // 5. Seed Data Paket Tur & Itinerary Harian (content.md Section 5 + Paket Ekspedisi Tambahan)
    val TOUR_PACKAGES = listOf(
        TourPackage(
            id = "tour-jbr-3d2n",
            destinationId = "dest-jember-001",
            title = "Eksplor Jember",
            durationDays = 3,
            durationLabel = "3 Hari 2 Malam",
            departureDatesLabel = "Kamis, 12 Nov - Sabtu, 14 Nov",
            availabilityBadge = "Tersedia 6 kursi • Keberangkatan Pasti",
            pricePerPerson = 1450000L,
            rating = 4.9,
            reviewCount = 142,
            sceneType = VisualSceneType.PAPUMA_COAST,
            requiresPhysicalAnd4wdWarning = true,
            facilitiesList = listOf(
                "Transportasi AC eksekutif selama di Jember (BBM & Driver lokal berpengalaman)",
                "Menginap 2 malam di Luminor Hotel / Aston Jember (4 Bintang, termasuk sarapan)",
                "Tiket masuk seluruh kawasan wisata: Pantai Payangan, Teluk Love, Tanjung Papuma, Meru Betiri, dan Puncak Rembangan",
                "Kuliner khas terkurasi: Sarapan Gudeg Pecel Lumintu & Makan Malam Ikan Laut Bakar Payangan",
                "Pemandu wisata lokal bersertifikat HPI Jawa Timur & dokumentasi foto perjalanan"
            ),
            hotelName = "Aston Jember Hotel & Conference Center",
            hotelRatingLabel = "Hotel Bintang 4 • Pusat Kota Jember",
            pickupPoint = "Stasiun Kereta Api Jember (JR) — Pintu Keluar Utama (08:30 WIB)",
            terrainAdvisory = "Hari 1 & Hari 3 melalui jalan aspal standar. Ekspedisi Hari 2 menuju Teluk Bandealit melewati jalur tanah berbatu Meru Betiri (disarankan mengaktifkan opsi Sewa Armada Jeep 4WD).",
            emergencyContactName = "Posko Mitra Jelajah Jember & SAR Rimba Laut",
            emergencyContactNumber = "+62 812-3456-7890",
            gpsCoordinates = "-8.4312° S, 113.5539° E (Tanjung Papuma) • -8.4831° S, 113.7114° E (Bandealit)",
            addonOption = AddonOption(
                id = "addon-jeep-bandealit",
                title = "Sewa Armada Jeep Bandealit",
                subtitle = "Kendaraan 4x4 lokal terverifikasi + pengemudi medan off-road Meru Betiri (Hari 2)",
                pricePerPerson = 350000L,
                badgeLabel = "+Rp 350.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Kedatangan & Teluk Love",
                    sceneType = VisualSceneType.TELUK_LOVE_SUNSET,
                    elevationOrDistanceMeta = "Jarak tempuh 38 km • Akses Roda 4 Standar",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "08:30 WIB",
                            description = "Penjemputan di Stasiun Jember, sarapan kuliner lokal dan persiapan check-in.",
                            locationName = "Stasiun Jember & Pusat Kuliner Kota",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "13:00 WIB",
                            description = "Menuju Pantai Payangan dan trekking ringan ke Bukit Domba Teluk Love.",
                            locationName = "Bukit Domba & Teluk Love, Ambulu"
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "17:00 WIB",
                            description = "Menikmati sunset pesisir selatan dan santap malam ikan bakar Payangan.",
                            locationName = "Pesisir Pantai Payangan"
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Sunrise Papuma & Rimba Bandealit",
                    sceneType = VisualSceneType.PAPUMA_COAST,
                    elevationOrDistanceMeta = "Jarak tempuh 54 km • Kombinasi Aspal & Off-Road 4WD",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "05:00 WIB",
                            description = "Berburu matahari terbit di Siti Hinggil Pantai Papuma dengan latar batu karang.",
                            locationName = "Siti Hinggil, Tanjung Papuma",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "11:30 WIB",
                            description = "Ekspedisi lintas jalur Meru Betiri menuju Teluk Bandealit menggunakan armada Jeep 4WD.",
                            locationName = "Taman Nasional Meru Betiri",
                            requires4wdBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "16:00 WIB",
                            description = "Eksplorasi pesisir rimba Bandealit dan kembali menuju hotel di pusat kota Jember.",
                            locationName = "Teluk Bandealit & Kota Jember",
                            requires4wdBadge = true
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 3,
                    title = "Agrowisata & Pusat Suwar-Suwir",
                    sceneType = VisualSceneType.REMBANGAN_HIGHLAND,
                    elevationOrDistanceMeta = "Jarak tempuh 28 km • Dataran Tinggi 650 mdpl",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "07:30 WIB",
                            description = "Menikmati udara sejuk dataran tinggi perkebunan kopi dan susu segar di Kebun Rembangan.",
                            locationName = "Agrowisata Kebun Rembangan, Arjasa",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "11:00 WIB",
                            description = "Berbelanja oleh-oleh khas di Pusat Suwar-Suwir dan Prol Tape Jember.",
                            locationName = "Sentra Oleh-Oleh Jalan Gajah Mada"
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "15:00 WIB",
                            description = "Pengantaran peserta kembali ke Stasiun Jember sesuai jadwal keberangkatan kereta.",
                            locationName = "Stasiun Kereta Api Jember (JR)"
                        )
                    )
                )
            )
        ),
        TourPackage(
            id = "tour-meru-2d1n",
            destinationId = "dest-jember-002",
            title = "Safari Meru Betiri & Kano Bandealit 2H1M",
            durationDays = 2,
            durationLabel = "2 Hari 1 Malam",
            departureDatesLabel = "Sabtu, 21 Nov - Minggu, 22 Nov",
            availabilityBadge = "Tersedia 4 kursi • Ekspedisi Khusus",
            pricePerPerson = 1250000L,
            rating = 4.8,
            reviewCount = 68,
            sceneType = VisualSceneType.MERU_BETIRI_FOREST,
            requiresPhysicalAnd4wdWarning = true,
            facilitiesList = listOf(
                "Armada Jeep 4x4 gardan ganda dari titik kumpul Tempurejo",
                "Penginapan Eco-Lodge Pesanggrahan Bandealit (1 malam)",
                "Peralatan kano muara sungai & jaket pelampung standar keselamatan",
                "Izin masuk konservasi Taman Nasional Meru Betiri & Ranger pendamping",
                "Makan 4 kali dengan menu pedesaan organik"
            ),
            hotelName = "Eco-Lodge Pesanggrahan Bandealit",
            hotelRatingLabel = "Pondok Konservasi Tepi Teluk",
            pickupPoint = "Stasiun Jember / Alun-Alun Jember (07:00 WIB)",
            terrainAdvisory = "Wajib kondisi fisik prima. Lintasan sejauh 14 km melewati hutan hujan dataran rendah dan sungai batu tanpa sinyal seluler (disarankan mengunduh jadwal luring).",
            emergencyContactName = "Pos Ranger Seksi PTN Wilayah II Ambulu",
            emergencyContactNumber = "+62 813-3901-4422",
            gpsCoordinates = "-8.4831° S, 113.7114° E (Teluk Bandealit)",
            addonOption = AddonOption(
                id = "addon-jeep-private",
                title = "Upgrade Jeep 4WD Kabin Tertutup & Dokumentasi Drone",
                subtitle = "Kenyamanan ekstra melintasi jalur sungai & dokumentasi udara di Teluk Bandealit",
                pricePerPerson = 350000L,
                badgeLabel = "+Rp 350.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Lintas Hutan Hujan & Muara Bandealit",
                    sceneType = VisualSceneType.MERU_BETIRI_FOREST,
                    elevationOrDistanceMeta = "Jalur Off-Road 14 km • Wajib Armada 4WD",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "07:00 WIB",
                            description = "Keberangkatan dari Jember menuju desa penyangga Andongrejo dan perpindahan ke armada Jeep 4x4.",
                            locationName = "Andongrejo, Tempurejo",
                            requires4wdBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "12:30 WIB",
                            description = "Menjelajahi muara tenang Teluk Bandealit dengan kano dan pengamatan burung rangkong di kanopi hutan.",
                            locationName = "Muara Sungai & Teluk Bandealit",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "17:00 WIB",
                            description = "Menikmati senja sunyi di pesisir selatan dilanjutkan makan malam hangat di Pesanggrahan.",
                            locationName = "Pantai Bandealit"
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Gua Jepang & Kebun Karet Banda",
                    sceneType = VisualSceneType.REMBANGAN_HIGHLAND,
                    elevationOrDistanceMeta = "Trekking Hutan 3.5 km • Kembali ke Jember",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "06:30 WIB",
                            description = "Trekking pagi menuju peninggalan Gua Jepang dan melihat pengolahan karet tradisional perkebunan Banda.",
                            locationName = "Kawasan Perkebunan Sumber Tenggulun",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "12:00 WIB",
                            description = "Perjalanan kembali melintasi hutan Meru Betiri dan pengantaran ke Stasiun Jember.",
                            locationName = "Stasiun Jember",
                            requires4wdBadge = true
                        )
                    )
                )
            )
        ),
        TourPackage(
            id = "tour-sukamade-2d1n",
            destinationId = "dest-bwi-009",
            title = "Ekspedisi Penyu Sukamade & Teluk Hijau 2H1M",
            durationDays = 2,
            durationLabel = "2 Hari 1 Malam",
            departureDatesLabel = "Sabtu, 28 Nov - Minggu, 29 Nov",
            availabilityBadge = "Tersedia 5 kursi • Konservasi Resmi",
            pricePerPerson = 1350000L,
            rating = 4.9,
            reviewCount = 94,
            sceneType = VisualSceneType.SUKAMADE_TURTLE_BEACH,
            requiresPhysicalAnd4wdWarning = true,
            facilitiesList = listOf(
                "Armada Trooper / Jeep 4WD khusus lintas sungai Meru Betiri",
                "Penginapan Wisma Konservasi Sukamade (1 malam)",
                "Pendampingan Ranger Konservasi Penyu malam hari & pelepasan tukik pagi",
                "Kunjungan ke Teluk Hijau (Green Bay) & Pantai Rajegwesi",
                "Makan 4 kali dan donasi konservasi tukik resmi"
            ),
            hotelName = "Wisma Konservasi Pantai Sukamade",
            hotelRatingLabel = "Penginapan Resmi Taman Nasional",
            pickupPoint = "Stasiun Jember / Stasiun Banyuwangi Kota (08:00 WIB)",
            terrainAdvisory = "Jalur off-road menembus 3 aliran sungai dangkal di hutan Meru Betiri. Seluruh peserta diangkut menggunakan armada Jeep 4WD gardan ganda.",
            emergencyContactName = "Pos Konservasi Penyu Sukamade TNMB",
            emergencyContactNumber = "+62 812-3488-9901",
            gpsCoordinates = "-8.5622° S, 113.8831° E (Pantai Sukamade)",
            addonOption = AddonOption(
                id = "addon-sukamade-jeep",
                title = "Armada Jeep 4WD Eksklusif & Perahu Teluk Hijau",
                subtitle = "Termasuk sewa perahu nelayan Rajegwesi menuju Teluk Hijau tanpa trekking curam",
                pricePerPerson = 350000L,
                badgeLabel = "+Rp 350.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Teluk Hijau, Rajegwesi & Observasi Penyu Malam",
                    sceneType = VisualSceneType.MERU_BETIRI_BAY,
                    elevationOrDistanceMeta = "Lintas Sungai & Hutan • Wajib Armada 4WD",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "08:00 WIB",
                            description = "Menuju Pantai Rajegwesi dan menyeberang ke perairan pirus Teluk Hijau (Green Bay).",
                            locationName = "Teluk Hijau, Pesanggaran",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "13:30 WIB",
                            description = "Ekspedisi off-road menggunakan Jeep 4WD melintasi sungai hutan hujan menuju mess Pantai Sukamade.",
                            locationName = "Hutan Hujan Meru Betiri",
                            requires4wdBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "20:00 WIB",
                            description = "Patroli malam bersama Ranger mengamati penyu hijau raksasa bertelur di pasir Pantai Sukamade.",
                            locationName = "Pantai Konservasi Sukamade",
                            guideAvailableBadge = true
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Pelepasan Tukik Fajar & Pantai Pulau Merah",
                    sceneType = VisualSceneType.SUKAMADE_TURTLE_BEACH,
                    elevationOrDistanceMeta = "Pesisir Selatan Banyuwangi",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "06:00 WIB",
                            description = "Melepaskan tukik (anak penyu) menuju Samudra Hindia saat matahari terbit.",
                            locationName = "Bibir Pantai Sukamade",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "13:00 WIB",
                            description = "Singgah bersantai dan makan siang di Pantai Pulau Merah sebelum kembali ke stasiun.",
                            locationName = "Pantai Pulau Merah, Banyuwangi"
                        )
                    )
                )
            )
        ),
        TourPackage(
            id = "tour-ijen-2d1n",
            destinationId = "dest-jatim-003",
            title = "Ekspedisi Kawah Ijen & Savana Baluran 2H1M",
            durationDays = 2,
            durationLabel = "2 Hari 1 Malam",
            departureDatesLabel = "Jumat, 27 Nov - Sabtu, 28 Nov",
            availabilityBadge = "Tersedia 8 kursi • Termasuk Respirator",
            pricePerPerson = 1150000L,
            rating = 4.9,
            reviewCount = 210,
            sceneType = VisualSceneType.IJEN_CRATER,
            requiresPhysicalAnd4wdWarning = true,
            facilitiesList = listOf(
                "Shuttle Trooper / Jeep 4WD menuju Pos Paltuding (1.850 mdpl)",
                "Masker gas respirator double-cartridge standar keselamatan vulkanik",
                "Pemandu lokal berpengalaman, senter kepala, & tiket Taman Nasional Baluran",
                "1 malam transit di Grand Harvest Resort Licin / Ijen View",
                "Sarapan hangat pasca-pendakian & tiket konservasi BKSDA"
            ),
            hotelName = "Grand Harvest Resort & Villas Licin",
            hotelRatingLabel = "Resort Lereng Gunung Ijen",
            pickupPoint = "Stasiun Banyuwangi Kota / Stasiun Jember (13:00 WIB)",
            terrainAdvisory = "Jalur pendakian sepanjang 3 km dengan kemiringan 25-35 derajat. Wajib menggunakan sepatu gunung beralas grip kuat dan masker respirator.",
            emergencyContactName = "Pos BKSDA Paltuding & Medis Gunung Ijen",
            emergencyContactNumber = "+62 811-3409-112",
            gpsCoordinates = "-8.0583° S, 114.2425° E (Kawah Ijen)",
            addonOption = AddonOption(
                id = "addon-jeep-ijen",
                title = "Antar-Jemput Jeep 4WD Lintas Kalibendo & Savana Baluran",
                subtitle = "Ekspedisi off-road melewati perkebunan kopi kolonial dan savana Bekol Baluran",
                pricePerPerson = 300000L,
                badgeLabel = "+Rp 300.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Safari Savana Bekol Baluran & Lereng Licin",
                    sceneType = VisualSceneType.BALURAN_SAVANNA,
                    elevationOrDistanceMeta = "Savana Bekol & Lereng 850 mdpl",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "13:30 WIB",
                            description = "Menjelajahi Savana Bekol dan Pantai Bama di Taman Nasional Baluran untuk mengamati kawanan rusa dan merak hijau.",
                            locationName = "Taman Nasional Baluran",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "18:00 WIB",
                            description = "Check-in resort di lereng Licin, briefing keselamatan penggunaan respirator, dan istirahat dini.",
                            locationName = "Resort Lereng Ijen"
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Pendakian Fajar Blue Fire & Danau Toska",
                    sceneType = VisualSceneType.IJEN_CRATER,
                    elevationOrDistanceMeta = "Elevasi Puncak 2.386 mdpl • Trekking 6 km PP",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "01:00 WIB",
                            description = "Berangkat menuju Paltuding dan memulai pendakian ke bibir kawah untuk menyaksikan fenomena Blue Fire serta matahari terbit di atas danau asam terbesar di dunia.",
                            locationName = "Pos Paltuding & Bibir Kawah Ijen",
                            requires4wdBadge = true,
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "10:30 WIB",
                            description = "Kembali ke resort untuk sarapan, bersih diri, dan pengantaran kembali ke stasiun tujuan.",
                            locationName = "Banyuwangi / Jember"
                        )
                    )
                )
            )
        ),
        TourPackage(
            id = "tour-bromo-sewu-2d1n",
            destinationId = "dest-bromo-004",
            title = "Jelajah Kaldera Bromo & Tumpak Sewu 2H1M",
            durationDays = 2,
            durationLabel = "2 Hari 1 Malam",
            departureDatesLabel = "Sabtu, 5 Des - Minggu, 6 Des",
            availabilityBadge = "Tersedia 6 kursi • Termasuk Hardtop 4WD",
            pricePerPerson = 1390000L,
            rating = 4.9,
            reviewCount = 265,
            sceneType = VisualSceneType.BROMO_CALDERA,
            requiresPhysicalAnd4wdWarning = true,
            facilitiesList = listOf(
                "Jeep Hardtop 4x4 resmi TNBTS (Penanjakan 1, Bukit Widodaren, Kawah Bromo, Pasir Berbisik)",
                "Tiket masuk Air Terjun Tumpak Sewu & pemandu lokal lembah ngarai",
                "Menginap 1 malam di Jiwa Jawa Resort / Lava View Lodge Bromo",
                "Sarapan buffet pegunungan & jaket hangat cadangan"
            ),
            hotelName = "Lava View Lodge Cemoro Lawang",
            hotelRatingLabel = "Tepi Kaldera Tengger • 2.200 mdpl",
            pickupPoint = "Stasiun Malang / Stasiun Probolinggo (07:30 WIB)",
            terrainAdvisory = "Suhu dini hari di Penanjakan mencapai 8-11°C. Jalur menuju dasar Tumpak Sewu melewati anak tangga lembab dan aliran air (wajib alas kaki anti-selip).",
            emergencyContactName = "Posko SAR TNBTS Cemoro Lawang",
            emergencyContactNumber = "+62 811-3602-889",
            gpsCoordinates = "-7.9425° S, 112.9530° E (Gunung Bromo) • -8.2303° S, 112.9175° E (Tumpak Sewu)",
            addonOption = AddonOption(
                id = "addon-bromo-private-jeep",
                title = "Private Hardtop 4WD Sunrise & Dokumentasi Fotografer",
                subtitle = "1 unit Jeep Hardtop 4x4 khusus grup Anda tanpa digabung peserta lain",
                pricePerPerson = 350000L,
                badgeLabel = "+Rp 350.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Ngarai Air Terjun Tumpak Sewu & Cemoro Lawang",
                    sceneType = VisualSceneType.TUMPAK_SEWU_WATERFALL,
                    elevationOrDistanceMeta = "Lembah Pronojiwo & Kaldera 2.200 mdpl",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "08:30 WIB",
                            description = "Menikmati kemegahan tirai Air Terjun Tumpak Sewu dari titik Panorama dan trekking menuju dasar lembah.",
                            locationName = "Air Terjun Tumpak Sewu, Pronojiwo",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "16:30 WIB",
                            description = "Perjalanan naik menuju desa Cemoro Lawang di tepi kaldera Bromo dan istirahat.",
                            locationName = "Cemoro Lawang, Bromo"
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Golden Sunrise Penanjakan & Lautan Pasir Bromo",
                    sceneType = VisualSceneType.BROMO_CALDERA,
                    elevationOrDistanceMeta = "Elevasi 2.770 mdpl • Wajib Jeep Hardtop 4WD",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "03:00 WIB",
                            description = "Menembus lautan pasir dengan Jeep Hardtop 4WD menuju Bukit Kingkong / Penanjakan untuk menyaksikan matahari terbit berlatar Bromo, Batok, dan Semeru.",
                            locationName = "Bukit Kingkong & Kawah Bromo",
                            requires4wdBadge = true,
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "11:00 WIB",
                            description = "Kembali ke penginapan untuk sarapan dan pengantaran menuju stasiun.",
                            locationName = "Stasiun Malang / Probolinggo"
                        )
                    )
                )
            )
        ),
        TourPackage(
            id = "tour-malang-2d1n",
            destinationId = "dest-malang-005",
            title = "Retret Dataran Tinggi Malang & Pantai Selatan 2H1M",
            durationDays = 2,
            durationLabel = "2 Hari 1 Malam",
            departureDatesLabel = "Sabtu, 12 Des - Minggu, 13 Des",
            availabilityBadge = "Tersedia 10 kursi • Ramah Keluarga",
            pricePerPerson = 980000L,
            rating = 4.8,
            reviewCount = 134,
            sceneType = VisualSceneType.MALANG_HIGHLAND,
            requiresPhysicalAnd4wdWarning = false,
            facilitiesList = listOf(
                "Transportasi HiAce Premio / Innova Reborn AC selama 2 hari",
                "Tiket masuk Air Terjun Coban Rondo, Kebun Teh Wonosari, & Pantai Balekambang",
                "Menginap 1 malam di Golden Tulip Holland Resort Batu / Jambuluwuk",
                "Tur edukasi petik teh pagi & kuliner Bakso Bakar legendaris Malang"
            ),
            hotelName = "Golden Tulip Holland Resort Batu",
            hotelRatingLabel = "Resort Bintang 5 • Kota Wisata Batu",
            pickupPoint = "Stasiun Malang Kota Baru (08:00 WIB)",
            terrainAdvisory = "Seluruh rute menggunakan jalan beraspal mulus (Jalur Lintas Selatan & Jalan Raya Pujon-Batu), sangat nyaman untuk seluruh usia.",
            emergencyContactName = "Posko Mitra Wisata Malang Raya",
            emergencyContactNumber = "+62 812-3310-7788",
            gpsCoordinates = "-7.8847° S, 112.4772° E (Coban Rondo) • -8.4036° S, 112.5392° E (Balekambang)",
            addonOption = AddonOption(
                id = "addon-malang-jeep",
                title = "Safari Jeep Off-Road Kebun Apel & Pinus Brakseng",
                subtitle = "Keliling lembah sayur tertinggi Brakseng dan hutan pinus menggunakan Jeep terbuka",
                pricePerPerson = 250000L,
                badgeLabel = "+Rp 250.000 / orang"
            ),
            itineraryDays = listOf(
                DaySchedule(
                    dayNumber = 1,
                    title = "Air Terjun Coban Rondo & Senja Pantai Balekambang",
                    sceneType = VisualSceneType.MALANG_HIGHLAND,
                    elevationOrDistanceMeta = "Pujon 1.150 mdpl & Pesisir Bantur",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "08:30 WIB",
                            description = "Menikmati kesegaran Air Terjun Coban Rondo dan hutan pinus Pujon.",
                            locationName = "Coban Rondo, Pujon",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Sore",
                            timeSchedule = "15:30 WIB",
                            description = "Menyusuri Jalur Lintas Selatan menuju Pantai Balekambang dan menikmati matahari terbenam di depan Pulau Ismoyo.",
                            locationName = "Pantai Balekambang, Malang Selatan"
                        )
                    )
                ),
                DaySchedule(
                    dayNumber = 2,
                    title = "Udara Fajar Kebun Teh Wonosari Lereng Arjuno",
                    sceneType = VisualSceneType.WONOSARI_TEA_HIGHLAND,
                    elevationOrDistanceMeta = "Lereng Gunung Arjuno 1.050 mdpl",
                    activities = listOf(
                        ActivitySession(
                            timeOfDayLabel = "Pagi",
                            timeSchedule = "06:30 WIB",
                            description = "Tea walk menyusuri hamparan kebun teh Wonosari dan melihat proses pengolahan teh hitam.",
                            locationName = "Kebun Teh Wonosari, Lawang",
                            guideAvailableBadge = true
                        ),
                        ActivitySession(
                            timeOfDayLabel = "Siang",
                            timeSchedule = "12:00 WIB",
                            description = "Wisata kuliner siang dan pengantaran kembali ke Stasiun Malang Kota Baru.",
                            locationName = "Kota Malang"
                        )
                    )
                )
            )
        )
    )

    // Pertanyaan & Skenario Harga Dasar (untuk konsistensi akses cepat)
    const val FEATURED_TOUR_TITLE = "Eksplor Jember Eksotis 3H2M"
    const val FEATURED_TOUR_DURATION = "3 Hari 2 Malam"
    const val FEATURED_TOUR_DATES = "Kamis, 12 Nov - Sabtu, 14 Nov"
    const val FEATURED_TOUR_BASE_PRICE = 1450000L
    const val FEATURED_TOUR_JEEP_ADDON_PRICE = 350000L

    fun getDestinationById(id: String): DestinationSeed {
        return DESTINATIONS.find { it.id == id } ?: DESTINATIONS.first()
    }

    fun getToursForDestination(destinationId: String): List<TourPackage> {
        val destination = getDestinationById(destinationId)
        val exact = TOUR_PACKAGES.filter { it.destinationId == destinationId }
        val regional = TOUR_PACKAGES.filter { tour ->
            val tourDest = getDestinationById(tour.destinationId)
            tourDest.regionCategory.equals(destination.regionCategory, ignoreCase = true)
        }
        return (exact + regional + TOUR_PACKAGES).distinctBy { it.id }
    }

    fun getTourById(tourId: String): TourPackage {
        return TOUR_PACKAGES.find { it.id == tourId } ?: TOUR_PACKAGES.first()
    }

    fun getPhotoGalleryForDestination(destinationId: String): List<PhotoGalleryItem> =
        when (destinationId) {
            "dest-jember-001" -> listOf(
                PhotoGalleryItem(VisualSceneType.PAPUMA_COAST, "Batu Karang Malikan, Tanjung Papuma"),
                PhotoGalleryItem(VisualSceneType.PAPUMA_BOATS, "Perahu Nelayan di Pasir Putih Papuma"),
                PhotoGalleryItem(VisualSceneType.WATU_ULO_COAST, "Pesisir Pantai Watu Ulo Jember"),
                PhotoGalleryItem(VisualSceneType.TELUK_LOVE_SUNSET, "Panorama Teluk Love Payangan")
            )
            "dest-jember-002" -> listOf(
                PhotoGalleryItem(VisualSceneType.MERU_BETIRI_FOREST, "Kanopi Hutan Hujan Meru Betiri"),
                PhotoGalleryItem(VisualSceneType.MERU_BETIRI_BAY, "Teluk Pesisir Taman Nasional Meru Betiri"),
                PhotoGalleryItem(VisualSceneType.SUKAMADE_TURTLE_BEACH, "Pantai Konservasi Meru Betiri")
            )
            "dest-jember-006" -> listOf(
                PhotoGalleryItem(VisualSceneType.TELUK_LOVE_SUNSET, "Teluk Love dari Bukit Domba Payangan"),
                PhotoGalleryItem(VisualSceneType.PAYANGAN_SAMBOJA, "Pantai Payangan dari Bukit Samboja"),
                PhotoGalleryItem(VisualSceneType.PAPUMA_COAST, "Gugusan Karang Pesisir Selatan Jember")
            )
            "dest-jember-007" -> listOf(
                PhotoGalleryItem(VisualSceneType.WATU_ULO_COAST, "Formasi Batu Ular Pantai Watu Ulo"),
                PhotoGalleryItem(VisualSceneType.PAPUMA_BOATS, "Perahu Tradisional Nelayan Jember"),
                PhotoGalleryItem(VisualSceneType.PAPUMA_COAST, "Tanjung Papuma di Sebelah Barat Watu Ulo")
            )
            "dest-jember-008" -> listOf(
                PhotoGalleryItem(VisualSceneType.REMBANGAN_HIGHLAND, "Perkebunan Dataran Tinggi Jember"),
                PhotoGalleryItem(VisualSceneType.WONOSARI_TEA_HIGHLAND, "Lembah Hijau Pegunungan")
            )
            "dest-jatim-003" -> listOf(
                PhotoGalleryItem(VisualSceneType.IJEN_CRATER, "Danau Kawah Asam Toska Kawah Ijen"),
                PhotoGalleryItem(VisualSceneType.BALURAN_SAVANNA, "Lanskap Timur Jawa")
            )
            "dest-bwi-009" -> listOf(
                PhotoGalleryItem(VisualSceneType.SUKAMADE_TURTLE_BEACH, "Pantai Konservasi Penyu Sukamade"),
                PhotoGalleryItem(VisualSceneType.MERU_BETIRI_BAY, "Teluk Hijau (Green Bay) Meru Betiri"),
                PhotoGalleryItem(VisualSceneType.MERU_BETIRI_FOREST, "Hutan Hujan Tropis Meru Betiri")
            )
            "dest-bwi-010" -> listOf(
                PhotoGalleryItem(VisualSceneType.BALURAN_SAVANNA, "Golden Hour di Savana Bekol Baluran"),
                PhotoGalleryItem(VisualSceneType.IJEN_CRATER, "Lanskap Vulkanik Banyuwangi")
            )
            "dest-bwi-011" -> listOf(
                PhotoGalleryItem(VisualSceneType.PULAU_MERAH_BEACH, "Bukit Pulau Merah Banyuwangi"),
                PhotoGalleryItem(VisualSceneType.MERU_BETIRI_BAY, "Teluk Hijau Pesanggaran")
            )
            "dest-bromo-004" -> listOf(
                PhotoGalleryItem(VisualSceneType.BROMO_CALDERA, "Matahari Terbit Bromo, Batok & Semeru"),
                PhotoGalleryItem(VisualSceneType.TUMPAK_SEWU_WATERFALL, "Air Terjun Tumpak Sewu di Lereng Semeru")
            )
            "dest-bromo-012" -> listOf(
                PhotoGalleryItem(VisualSceneType.TUMPAK_SEWU_WATERFALL, "Tirai Air Terjun Tumpak Sewu Lumajang"),
                PhotoGalleryItem(VisualSceneType.BROMO_CALDERA, "Puncak Mahameru & Kaldera Tengger")
            )
            "dest-malang-005" -> listOf(
                PhotoGalleryItem(VisualSceneType.MALANG_HIGHLAND, "Air Terjun Coban Rondo Pujon Malang"),
                PhotoGalleryItem(VisualSceneType.WONOSARI_TEA_HIGHLAND, "Dataran Tinggi Malang Raya")
            )
            "dest-malang-013" -> listOf(
                PhotoGalleryItem(VisualSceneType.BALEKAMBANG_BEACH, "Pantai Balekambang & Pulau Ismoyo"),
                PhotoGalleryItem(VisualSceneType.MALANG_HIGHLAND, "Wisata Alam Kabupaten Malang")
            )
            else -> listOf(
                PhotoGalleryItem(VisualSceneType.WONOSARI_TEA_HIGHLAND, "Hamparan Kebun Teh Wonosari Lawang"),
                PhotoGalleryItem(VisualSceneType.MALANG_HIGHLAND, "Air Terjun Coban Rondo Malang")
            )
        }

    /**
     * Format mata uang sesuai AGENTS.md: "Rp 1.450.000" (menggunakan spasi dan titik pemisah ribuan).
     */
    fun formatRupiah(amount: Long, includePlusPrefix: Boolean = false): String {
        val symbols = DecimalFormatSymbols(Locale.forLanguageTag("id-ID")).apply {
            groupingSeparator = '.'
            decimalSeparator = ','
        }
        val formatter = DecimalFormat("#,###", symbols)
        val prefix = if (includePlusPrefix) "+Rp " else "Rp "
        return "$prefix${formatter.format(amount)}"
    }
}
