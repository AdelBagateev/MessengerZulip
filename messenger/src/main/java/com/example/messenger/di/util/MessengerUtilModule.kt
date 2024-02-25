package com.example.messenger.di.util

import com.example.messenger.di.qualifiers.RegexPattern
import com.example.messenger.di.qualifiers.UrlLoadMedia
import dagger.Module
import dagger.Provides

@Module
class MessengerUtilModule {
    @Provides
    @RegexPattern
    fun provideRegexPattern(): String =
        "\\[(.*?)\\]\\((/user_uploads.*?\\.(jpe?g|png|gif))\\)"

    @Provides
    fun provideRegex(
        @RegexPattern pattern: String
    ): Regex =
        Regex(pattern)

    @Provides
    @UrlLoadMedia
    fun provideUrlForLoadMedia(): String =
        "https://tinkoff-android-spring-2023.zulipchat.com"
}
