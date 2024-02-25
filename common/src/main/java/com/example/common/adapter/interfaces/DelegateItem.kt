package com.example.common.adapter.interfaces

interface DelegateItem {
    val value: Any

    fun id(): String
    fun <T : DelegateItem> compareToOther(other: T): Boolean
}
