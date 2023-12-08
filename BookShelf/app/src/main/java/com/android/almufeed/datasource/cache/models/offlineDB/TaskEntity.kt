package com.android.almufeed.datasource.cache.models.offlineDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,

    @ColumnInfo(name = "task_id") var task_id: String,
)