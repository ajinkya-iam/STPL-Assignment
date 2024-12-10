package me.ajinkyashinde.stplassignment.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import me.ajinkyashinde.stplassignment.ui.customerlist.CustomerListAdapter

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @ActivityScoped
    @Provides
    fun provideMovieListAdapter() = CustomerListAdapter()
}