package com.lek.parrot.newevents.ui.createreminderevent

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lek.parrot.R
import com.lek.parrot.databinding.ViewCreateEventFragmentBinding
import com.lek.parrot.newevents.domain.ScreenType
import com.lek.parrot.newevents.domain.screenTypeFromOrdinal
import com.lek.parrot.newevents.ui.OnBackListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEventFragment : BottomSheetDialogFragment(), OnBackListener {

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var screenType: ScreenType? = null

    private lateinit var binding: ViewCreateEventFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewCreateEventFragmentBinding.bind(
            inflater.inflate(
                R.layout.view_create_event_fragment,
                container,
                true
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            screenType = screenTypeFromOrdinal(it.getInt(SCREEN_TYPE, -1))
            when (screenType) {
                ScreenType.REMINDER -> {
                    binding.createReminderEvent.visibility = View.VISIBLE
                    binding.createReminderEvent.setOnBackListener(this)
                }
                ScreenType.CALL -> {
                    binding.createCallEvent.visibility = View.VISIBLE
                    binding.createCallEvent.setOnBackListener(this)
                }
                ScreenType.MESSAGE -> {
                    binding.createMessageEvent.visibility = View.VISIBLE
                    binding.createMessageEvent.setOnBackListener(this)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onBack(screenType: ScreenType) {
        dismiss()
    }

    companion object {
        private const val TAG = "CREATE_REMINDER_FRAGMENT_TAG"
        const val SCREEN_TYPE = "CREATE_EVENT_FRAGMENT_SCREEN_TYPE"
        private var fragment: CreateEventFragment? = null

        @Synchronized
        fun show(fragmentManager: FragmentManager, screenType: ScreenType) {
            val bundle = Bundle().apply { putInt(SCREEN_TYPE, screenType.ordinal) }
            fragment = CreateEventFragment().apply { arguments = bundle }
            fragment?.show(fragmentManager, TAG)
        }
    }
}