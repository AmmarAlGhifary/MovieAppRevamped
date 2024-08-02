package com.example.tmdb.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentProfileBinding
import com.example.tmdb.ui.authentication.AuthenticationActivity
import com.example.tmdb.ui.authentication.AuthenticationViewModel
import com.example.tmdb.ui.base.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by activityViewModels()
    private val authViewModel: AuthenticationViewModel by activityViewModels()

    override val bindingVariables: (FragmentProfileBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!authViewModel.isAuthenticated()) {
            showAuthenticateDialog()
        } else {
            authViewModel.session.value?.let { session ->
                viewModel.fetchProfile(session.sessionId)
            }
        }
    }

    private fun showAuthenticateDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Authentication Required")
            .setMessage("You need to authenticate to view your profile. Do you want to proceed?")
            .setNegativeButtonIcon(resources.getDrawable(R.drawable.ic_baseline_close_24))
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButtonIcon(resources.getDrawable(R.drawable.ic_baseline_play_arrow_48))
            .setPositiveButton("Proceed") { dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            }
            .show()
    }
}
