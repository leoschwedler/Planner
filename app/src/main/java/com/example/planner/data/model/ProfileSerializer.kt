package com.example.planner.data.model

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ProfileSerializer : Serializer<Profile> {
    override val defaultValue: Profile
        get() = Profile()

    override suspend fun readFrom(input: InputStream): Profile {
        return try {
            Json.decodeFromString(Profile.serializer(), input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Profile, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(Profile.serializer(), t).encodeToByteArray()
            )
        }
    }
}