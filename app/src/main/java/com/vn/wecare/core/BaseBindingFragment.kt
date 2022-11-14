package com.vn.wecare.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

// We use abstract class instead of open to avoid using BaseBindingFragment as a normal class
// E.g: var base = BaseBindingFragment()
//? Is VB in this situation is a type or variable?
abstract class BaseBindingFragment<VB : ViewBinding>(
    // To create a value inflate with type ViewBinding we pass the type parameter
    private val inflate: Inflate<VB>
) : Fragment() {
    // In the fragment we should only allow this property valid between
    // onCreateView and onDestroyView because if we use viewpager with fragment and
    // don't remember to destroy the binding, it will cause memory leak
    private var _binding: VB? = null

    // Because we set the _binding type is nullable so we need to create another variable
    // to ensure that when we get the value of _binding, it cannot be null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //? What does invoke mean?
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}