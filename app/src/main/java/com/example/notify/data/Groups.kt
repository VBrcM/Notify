package com.example.notify.data

data class Groups(
    val groupId: String = "",
    val memberCount: Long = 1,
    val groupName: String = "",
    val creatorEmail: String = "",
    val creatorUsername: String = "",
    val adminEmails: List<String> = emptyList(),
    val members: List<String> = emptyList(),
    val alarms: List<String> = emptyList()
)

fun generateUniqueId(): String {
    val timestamp = System.currentTimeMillis()
    val randomChars = ('0'..'9') + ('0'..'9') + ('0'..'9')
    val randomPart = (1..6).map { randomChars.random() }.joinToString("")
    return "$timestamp-$randomPart"
}
