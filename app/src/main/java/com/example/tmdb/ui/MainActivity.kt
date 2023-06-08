package com.example.tmdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private object PreferencesKey {
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)

        if (getIsFirstLaunch()) showAlertDialog()
    }

    private fun setIsFirstLaunch() {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[PreferencesKey.IS_FIRST_LAUNCH] = false
            }
        }
    }

    private fun getIsFirstLaunch() = runBlocking {
        dataStore.data.first()[PreferencesKey.IS_FIRST_LAUNCH] ?: true
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_person_24))
            .setTitle(getString(R.string.tmdb_attribution_title))
            .setMessage(getString(R.string.tmdb_attribution_message))
            .setPositiveButton(getString(R.string.tmdb_attribution_button_text)) { dialog, _ ->
                setIsFirstLaunch()
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }
}