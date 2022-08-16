package app.expense.tracker.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import app.expense.tracker.utils.NotificationUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideNotificationUtils(@ApplicationContext context: Context): NotificationUtils {
        return NotificationUtils(context, NotificationManagerCompat.from(context))
    }
}
