package com.musicplayer.data.repository

import com.musicplayer.data.dao.EqualizerPresetDao
import com.musicplayer.data.model.EqualizerPreset
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EqualizerPresetRepositoryImpl @Inject constructor(
    private val equalizerPresetDao: EqualizerPresetDao
) : EqualizerPresetRepository {
    override fun getAllPresets(): Flow<List<EqualizerPreset>> = equalizerPresetDao.getAllPresets()

    override fun getPresetById(id: Long): Flow<EqualizerPreset?> = equalizerPresetDao.getPresetById(id)

    override fun getDefaultPreset(): Flow<EqualizerPreset?> = equalizerPresetDao.getDefaultPreset()

    override suspend fun insertPreset(preset: EqualizerPreset): Long = equalizerPresetDao.insertPreset(preset)

    override suspend fun updatePreset(preset: EqualizerPreset) = equalizerPresetDao.updatePreset(preset)

    override suspend fun deletePreset(preset: EqualizerPreset) = equalizerPresetDao.deletePreset(preset)

    override suspend fun deletePresetById(presetId: Long) = equalizerPresetDao.deletePresetById(presetId)

    override suspend fun clearDefaultPresets() = equalizerPresetDao.clearDefaultPresets()

    override suspend fun setDefaultPreset(presetId: Long) = equalizerPresetDao.setDefaultPreset(presetId)
}
