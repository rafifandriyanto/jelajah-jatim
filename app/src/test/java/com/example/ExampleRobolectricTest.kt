package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.JelajahOfflineDatabase
import com.example.data.OfflineDestinationRepository
import com.example.data.TOP_5_OFFLINE_DESTINATION_IDS
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Jelajah Jatim", appName)
    }

    @Test
    fun `offline repository caches top 5 tourist destinations metadata and image files`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = JelajahOfflineDatabase.createInMemory(context)
        val repository = OfflineDestinationRepository(context, db.cachedDestinationDao())

        val cached = repository.ensureTop5Cached()
        assertEquals(5, cached.size)
        assertEquals(TOP_5_OFFLINE_DESTINATION_IDS, cached.map { it.id })

        cached.forEach { entity ->
            val imageFile = File(entity.cachedImageFilePath)
            assertTrue("Cached image file should exist for ${entity.id}", imageFile.exists())
            assertTrue("Cached image file should not be empty for ${entity.id}", entity.cachedImageSizeBytes > 0L)

            val restoredSeed = entity.toDestinationSeed()
            assertEquals(entity.name, restoredSeed.name)
            assertEquals(entity.rating, restoredSeed.rating, 0.001)
        }

        db.close()
    }
}
