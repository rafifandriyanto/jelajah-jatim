package com.example.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ui.components.toDrawableRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Daftar 5 Destinasi Wisata Utama Jawa Timur yang disimpan otomatis ke dalam
 * penyimpanan lokal (Room SQLite + Disk Image Cache) agar tetap dapat diakses saat luring (offline).
 */
val TOP_5_OFFLINE_DESTINATION_IDS: List<String> = listOf(
    "dest-jember-001", // 1. Tanjung Papuma (Jember)
    "dest-jatim-003",  // 2. Kawah Ijen & Blue Fire (Banyuwangi)
    "dest-bromo-004",  // 3. Kaldera Bromo & Bukit Kingkong (Probolinggo)
    "dest-jember-002", // 4. Taman Nasional Meru Betiri & Teluk Bandealit (Jember)
    "dest-malang-005"  // 5. Lembah Pinus Pujon & Coban Rondo (Malang)
)

/**
 * 1. Entity Room untuk Metadata & Path Gambar Cache Luring 5 Destinasi Wisata Utama
 */
@androidx.compose.runtime.Immutable
@Entity(tableName = "cached_destinations")
data class CachedDestinationEntity(
    @PrimaryKey val id: String,
    val rankOrder: Int,
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
    val terrainTypeCode: String,
    val accessRequirementCode: String,
    val recommendedHours: String,
    val sceneTypeName: String,
    val isFeaturedHero: Boolean,
    val statusId: String,
    val statusBadgeText: String,
    val statusDescription: String,
    val statusRouteContext: String,
    val statusToneName: String,
    val statusLastUpdated: String,
    val waveOrElevationMetric: String,
    val alternateRouteDestinationId: String?,
    val alternateRouteLabel: String?,
    val cachedImageFilePath: String,
    val cachedImageSizeBytes: Long,
    val cachedAtTimestamp: Long = System.currentTimeMillis()
) {
    fun toDestinationSeed(): DestinationSeed {
        val terrain = TerrainType.entries.find { it.code == terrainTypeCode } ?: TerrainType.COASTAL
        val access = AccessRequirement.entries.find { it.code == accessRequirementCode }
            ?: AccessRequirement.STANDARD
        val scene = runCatching { VisualSceneType.valueOf(sceneTypeName) }
            .getOrDefault(VisualSceneType.PAPUMA_COAST)
        val tone = runCatching { NatureStatusTone.valueOf(statusToneName) }
            .getOrDefault(NatureStatusTone.SAFE)

        return DestinationSeed(
            id = id,
            name = name,
            shortCardTitle = shortCardTitle,
            city = city,
            province = province,
            locationBadge = locationBadge,
            fullLocation = fullLocation,
            regionCategory = regionCategory,
            rating = rating,
            reviewCount = reviewCount,
            description = description,
            terrainType = terrain,
            accessRequirement = access,
            recommendedHours = recommendedHours,
            sceneType = scene,
            isFeaturedHero = isFeaturedHero,
            natureStatus = NatureStatusInfo(
                id = statusId,
                destinationId = id,
                badgeText = statusBadgeText,
                description = statusDescription,
                routeContext = statusRouteContext,
                tone = tone,
                lastUpdated = statusLastUpdated,
                waveOrElevationMetric = waveOrElevationMetric,
                alternateRouteDestinationId = alternateRouteDestinationId,
                alternateRouteLabel = alternateRouteLabel
            )
        )
    }

    companion object {
        fun fromDestinationSeed(
            seed: DestinationSeed,
            rankOrder: Int,
            cachedImageFilePath: String,
            cachedImageSizeBytes: Long,
            timestamp: Long = System.currentTimeMillis()
        ): CachedDestinationEntity {
            return CachedDestinationEntity(
                id = seed.id,
                rankOrder = rankOrder,
                name = seed.name,
                shortCardTitle = seed.shortCardTitle,
                city = seed.city,
                province = seed.province,
                locationBadge = seed.locationBadge,
                fullLocation = seed.fullLocation,
                regionCategory = seed.regionCategory,
                rating = seed.rating,
                reviewCount = seed.reviewCount,
                description = seed.description,
                terrainTypeCode = seed.terrainType.code,
                accessRequirementCode = seed.accessRequirement.code,
                recommendedHours = seed.recommendedHours,
                sceneTypeName = seed.sceneType.name,
                isFeaturedHero = seed.isFeaturedHero,
                statusId = seed.natureStatus.id,
                statusBadgeText = seed.natureStatus.badgeText,
                statusDescription = seed.natureStatus.description,
                statusRouteContext = seed.natureStatus.routeContext,
                statusToneName = seed.natureStatus.tone.name,
                statusLastUpdated = seed.natureStatus.lastUpdated,
                waveOrElevationMetric = seed.natureStatus.waveOrElevationMetric,
                alternateRouteDestinationId = seed.natureStatus.alternateRouteDestinationId,
                alternateRouteLabel = seed.natureStatus.alternateRouteLabel,
                cachedImageFilePath = cachedImageFilePath,
                cachedImageSizeBytes = cachedImageSizeBytes,
                cachedAtTimestamp = timestamp
            )
        }
    }
}

/**
 * 2. DAO Room untuk Akses Data Destinasi Luring
 */
@Dao
interface CachedDestinationDao {
    @Query("SELECT * FROM cached_destinations ORDER BY rankOrder ASC")
    fun observeTopCachedDestinations(): Flow<List<CachedDestinationEntity>>

    @Query("SELECT * FROM cached_destinations ORDER BY rankOrder ASC")
    suspend fun getCachedDestinationsOnce(): List<CachedDestinationEntity>

    @Query("SELECT * FROM cached_destinations WHERE id = :id LIMIT 1")
    suspend fun getCachedDestinationById(id: String): CachedDestinationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<CachedDestinationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDestination(item: CachedDestinationEntity)

    @Query("DELETE FROM cached_destinations WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM cached_destinations")
    suspend fun clearAll()
}

/**
 * 3. Room Database Holder
 */
@Database(
    entities = [CachedDestinationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class JelajahOfflineDatabase : RoomDatabase() {
    abstract fun cachedDestinationDao(): CachedDestinationDao

    companion object {
        @Volatile
        private var INSTANCE: JelajahOfflineDatabase? = null

        fun getInstance(context: Context): JelajahOfflineDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JelajahOfflineDatabase::class.java,
                    "jelajah_offline_destinations.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        fun createInMemory(context: Context): JelajahOfflineDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                JelajahOfflineDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}

/**
 * Registri Luring Berkecepatan Tinggi agar komponen gambar (`LazyOptimizedImage` / `DestinationArtwork`)
 * dapat membaca file gambar yang telah di-cache di disk lokal saat mode luring aktif.
 */
object LocalImageDiskCacheRegistry {
    @Volatile
    private var sceneToPathMap: Map<VisualSceneType, String> = emptyMap()

    fun updateSceneCachePaths(entities: List<CachedDestinationEntity>) {
        val updated = mutableMapOf<VisualSceneType, String>()
        entities.forEach { entity ->
            val scene = runCatching { VisualSceneType.valueOf(entity.sceneTypeName) }.getOrNull()
            if (scene != null && entity.cachedImageFilePath.isNotBlank()) {
                updated[scene] = entity.cachedImageFilePath
            }
        }
        sceneToPathMap = updated
    }

    fun getCachedFileForScene(sceneType: VisualSceneType): File? {
        val path = sceneToPathMap[sceneType] ?: return null
        val file = File(path)
        return if (file.exists() && file.length() > 0L) file else null
    }
}

/**
 * 4. Repository Pattern untuk Mengelola Metadata Room + File Gambar Cache Luring untuk 5 Destinasi Utama
 */
class OfflineDestinationRepository(
    private val appContext: Context,
    private val dao: CachedDestinationDao
) {
    private val imageCacheDir: File
        get() = File(appContext.filesDir, "offline_destinations_cache").apply {
            if (!exists()) mkdirs()
        }

    val cachedDestinationsFlow: Flow<List<CachedDestinationEntity>> =
        dao.observeTopCachedDestinations()

    /**
     * Memastikan 5 destinasi wisata utama beserta file gambarnya tersimpan di penyimpanan lokal
     * dan melakukan pre-warming LruCache bitmap di background thread agar 0ms saat dirender.
     */
    suspend fun ensureTop5Cached(): List<CachedDestinationEntity> = withContext(Dispatchers.IO) {
        val existing = dao.getCachedDestinationsOnce()
        val allFilesPresent = existing.size >= TOP_5_OFFLINE_DESTINATION_IDS.size &&
            existing.all { File(it.cachedImageFilePath).exists() && File(it.cachedImageFilePath).length() > 0L }

        val result = if (allFilesPresent) {
            LocalImageDiskCacheRegistry.updateSceneCachePaths(existing)
            existing
        } else {
            syncTop5DestinationsToLocalDisk()
        }
        prewarmMemoryBitmaps(result)
        result
    }

    /**
     * Menyimpan ulang (sinkronisasi) metadata dan menyalin aset foto asli 5 destinasi utama
     * ke direktori internal `filesDir/offline_destinations_cache/` + tabel Room `cached_destinations`.
     */
    suspend fun syncTop5DestinationsToLocalDisk(): List<CachedDestinationEntity> =
        withContext(Dispatchers.IO) {
            val top5Seeds = TOP_5_OFFLINE_DESTINATION_IDS.mapIndexedNotNull { idx, destId ->
                ContentSeedData.DESTINATIONS.find { it.id == destId }?.let { idx + 1 to it }
            }
            val now = System.currentTimeMillis()
            val entities = top5Seeds.map { (rank, seed) ->
                val targetFile = cacheDestinationImageToDisk(seed)
                CachedDestinationEntity.fromDestinationSeed(
                    seed = seed,
                    rankOrder = rank,
                    cachedImageFilePath = targetFile.absolutePath,
                    cachedImageSizeBytes = targetFile.length(),
                    timestamp = now
                )
            }
            dao.clearAll()
            dao.upsertAll(entities)
            LocalImageDiskCacheRegistry.updateSceneCachePaths(entities)
            prewarmMemoryBitmaps(entities)
            entities
        }

    private fun prewarmMemoryBitmaps(entities: List<CachedDestinationEntity>) {
        entities.forEach { entity ->
            val scene = runCatching { VisualSceneType.valueOf(entity.sceneTypeName) }.getOrNull()
            if (scene != null) {
                com.example.ui.components.ArtworkMemoryCache.decodeAndCache(appContext, scene)
            }
        }
    }

    suspend fun getCachedDestinationsOnce(): List<CachedDestinationEntity> =
        withContext(Dispatchers.IO) {
            dao.getCachedDestinationsOnce()
        }

    private fun cacheDestinationImageToDisk(seed: DestinationSeed): File {
        val targetFile = File(imageCacheDir, "${seed.id}.jpg")
        val drawableResId = seed.sceneType.toDrawableRes()
        runCatching {
            appContext.resources.openRawResource(drawableResId).use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        return targetFile
    }

    companion object {
        fun formatCacheSizeKb(bytes: Long): String {
            if (bytes <= 0L) return "0 KB"
            val kb = (bytes / 1024L).coerceAtLeast(1L)
            return "$kb KB"
        }
    }
}

/**
 * 5. ViewModel untuk Mengobservasi Cache Luring 5 Destinasi Utama secara Reaktif di Jetpack Compose
 */
class OfflineDestinationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OfflineDestinationRepository(
        appContext = application.applicationContext,
        dao = JelajahOfflineDatabase.getInstance(application.applicationContext).cachedDestinationDao()
    )

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    val cachedEntities: StateFlow<List<CachedDestinationEntity>> =
        repository.cachedDestinationsFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val cachedDestinationSeeds: StateFlow<List<DestinationSeed>> =
        repository.cachedDestinationsFlow
            .map { list ->
                LocalImageDiskCacheRegistry.updateSceneCachePaths(list)
                list.map { it.toDestinationSeed() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TOP_5_OFFLINE_DESTINATION_IDS.mapNotNull { id ->
                    ContentSeedData.DESTINATIONS.find { it.id == id }
                }
            )

    val totalCachedImageBytes: StateFlow<Long> =
        repository.cachedDestinationsFlow
            .map { list -> list.sumOf { it.cachedImageSizeBytes } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0L
            )

    init {
        viewModelScope.launch {
            _isSyncing.value = true
            repository.ensureTop5Cached()
            _isSyncing.value = false
        }
    }

    fun resyncTop5Destinations() {
        viewModelScope.launch {
            _isSyncing.value = true
            repository.syncTop5DestinationsToLocalDisk()
            _isSyncing.value = false
        }
    }
}
