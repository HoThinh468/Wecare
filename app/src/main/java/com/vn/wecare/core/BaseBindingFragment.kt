package com.vn.wecare.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vn.wecare.ui.theme.WecareTheme

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseBindingFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : Fragment() {

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        setupComposeView()
        return binding.root
    }

    open fun setupComposeView(
        composeView: ComposeView? = null,
        content: @Composable (() -> Unit)? = null
    ) {
        composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    content?.let { it() }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}