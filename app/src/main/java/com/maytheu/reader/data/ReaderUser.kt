package com.maytheu.reader.data

data class ReaderUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatar: String,
    val quote: String,
    val proofession: String,
) {
    fun firebaseMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "displayName" to this.displayName,
            "quote" to this.quote,
            "avatar" to this.avatar,
            "profession" to this.proofession
        )
    }
}
