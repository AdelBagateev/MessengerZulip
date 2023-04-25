package com.example.homework2.common.adapter.interfaces

interface DelegateItem {
    fun id(): String
    fun compareToOther(other: DelegateItem): Boolean
}
