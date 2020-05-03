package me.vitorvigano.chucknorrisjokes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "jokes")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val jokeId: Long,
    @SerializedName("id")
    @ColumnInfo(name = "external_id")
    val externalId: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String,
    val value: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String
)