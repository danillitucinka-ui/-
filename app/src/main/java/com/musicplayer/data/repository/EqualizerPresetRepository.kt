package com.musicplayer.data.repository

import com.musicplayer.data.model.EqualizerPreset
import kotlinx.coroutines.flow.Flow

interface EqualizerPresetRepository {
    fun getAllPresets(): Flow<List<EqualizerPreset>>
    fun getPresetById(id: Long): Flow<EqualizerPreset?>
    fun getDefaultPreset(): Flow<EqualizerPreset?>
    suspend fun insertPreset(preset: EqualizerPreset): Long
    suspend fun updatePreset(preset: EqualizerPreset)
    suspend fun deletePreset(preset: EqualizerPreset)
    suspend fun deletePresetById(presetId: Long)
    suspend fun clearDefaultPresets()
    suspend fun setDefaultPreset(presetId: Long)
}
