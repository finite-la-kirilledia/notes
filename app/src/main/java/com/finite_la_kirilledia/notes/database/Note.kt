package com.finite_la_kirilledia.notes.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var description: String,
    var creationTimeMillis: Long
) : Parcelable