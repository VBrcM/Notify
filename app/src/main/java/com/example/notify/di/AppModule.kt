package com.example.notify.di

import android.app.Application
import android.content.Context
import com.example.notify.alarmManager.AlarmScheduler
import com.example.notify.alarmManager.DefaultAlarmScheduler
import com.example.notify.repositories.AlarmRepository
import com.example.notify.repositories.AlarmRepositoryImpl
import com.example.notify.repositories.AuthRepository
import com.example.notify.repositories.AuthRepositoryImpl
import com.example.notify.repositories.GroupRepository
import com.example.notify.repositories.GroupRepositoryImpl
import com.example.notify.sharedPrefManager.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAlarmRepository(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth): AlarmRepository {
        return AlarmRepositoryImpl(firebaseFirestore, firebaseAuth)
    }


    @Provides
    fun provideSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesGroupRepository(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth): GroupRepository {
        return GroupRepositoryImpl(firebaseFirestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(context: Context): AlarmScheduler {
        return DefaultAlarmScheduler(context)
    }
}