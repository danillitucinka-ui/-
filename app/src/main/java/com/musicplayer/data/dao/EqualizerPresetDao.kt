package com.musicplayer.data.dao

import androidx.room.*
import com.musicplayer.data.model.EqualizerPreset
import kotlinx.coroutines.flow.Flow

@Dao
interface EqualizerPresetDao {
    @Query("SELECT * FROM equalizer_presets ORDER BY name ASC")
    fun getAllPresets(): Flow<List<EqualizerPreset>>

    @Query("SELECT * FROM equalizer_presets WHERE id = :id")
    fun getPresetById(id: Long): Flow<EqualizerPreset?>

    @Query("SELECT * FROM equalizer_presets WHERE isDefault = 1 LIMIT 1")
    fun getDefaultPreset(): Flow<EqualizerPreset?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreset(preset: EqualizerPreset): Long

    @Update
    suspend fun updatePreset(preset: EqualizerPreset)

    @Delete
    suspend fun deletePreset(preset: EqualizerPreset)

    @Query("DELETE FROM equalizer_presets WHERE id = :presetId")
    suspend fun deletePresetById(presetId: Long)

    @Query("UPDATE equalizer_presets SET isDefault = 0")
    suspend fun clearDefaultPresets()

    @Query("UPDATE equalizer_presets SET isDefault = 1 WHERE id = :presetId")
    suspend fun setDefaultPreset(presetId: Long)
}
