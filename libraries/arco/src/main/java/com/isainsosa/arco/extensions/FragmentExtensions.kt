package com.isainsosa.arco.extensions

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.isainsosa.arco.R


inline fun FragmentManager.transaction(actions: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.actions()
    transaction.commit()
}


fun FragmentManager.removeFragment(fragment: Fragment) {
    transaction {
        setReorderingAllowed(true)
        findFragmentByTag(fragment.tag)?.let { remove(it) }
        remove(fragment)
    }

}

fun FragmentManager.replaceFragment(fragmentContainer: Int, fragment: Fragment, addToBackstack: Boolean = true, fragmentTransitionAnimation: FragmentAnimation = FragmentAnimation.FADE_ANIM, TAG: String = fragment::class.java.simpleName) {
    val oldFrag: Fragment? = findFragmentByTag(TAG)
    if (oldFrag != null && oldFrag.isAdded) {
        popBackStack(TAG, 0)
    } else {
        transaction {
            setReorderingAllowed(true)
            putTransition(fragmentTransitionAnimation)
            replace(fragmentContainer, fragment, TAG)
            if (addToBackstack) addToBackStack(TAG)
        }
    }
}

fun FragmentManager.moveToFragment(fragmentContainer: Int, fragment: Fragment, addToBackstack: Boolean = true, fragmentTransitionAnimation: FragmentAnimation = FragmentAnimation.FADE_ANIM, TAG: String = fragment::class.java.simpleName) {
    try {
        transaction {
            for (f in fragments) {
                if (f != null && f.tag != TAG && !f.isDetached) {
                    detach(f)
                }
            }
        }
        val oldFrag = findFragmentByTag(TAG)
        transaction {
            if (oldFrag == null) {
                setReorderingAllowed(true)
                putTransition(fragmentTransitionAnimation)
                attach(fragment)
                add(fragmentContainer, fragment, TAG)
                if (addToBackstack) addToBackStack(TAG)
            } else if (oldFrag.isDetached) {
                attach(oldFrag)
                if (addToBackstack) addToBackStack(TAG)
            } else {
                detach(oldFrag)
                attach(oldFrag)
                if (addToBackstack) addToBackStack(TAG)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

private fun FragmentTransaction.putTransition(fragmentAnimation: FragmentAnimation?) {
    fragmentAnimation?.let {
        setCustomAnimations(it.added, it.removed, it.restored, it.popped)
    }
}

data class FragmentAnimation(@AnimatorRes @AnimRes val added: Int, @AnimatorRes @AnimRes val removed: Int, @AnimatorRes @AnimRes val restored: Int = added, @AnimatorRes @AnimRes val popped: Int = removed) {
    constructor(@AnimatorRes @AnimRes added: Int, @AnimatorRes @AnimRes removed: Int, consistent: Boolean) : this(added, removed, if (consistent) added else 0, if (consistent) removed else 0)

    companion object {
        val FADE_ANIM = FragmentAnimation(R.animator.fade_in, R.animator.fade_out)
        val VERTICAL_ANIM = FragmentAnimation(R.animator.slide_up, R.animator.slide_down)
        val VERTICAL_FADE_ANIM = FragmentAnimation(R.animator.slide_up_faded, R.animator.slide_down_faded)
        val HORIZONTAL_ANIM = FragmentAnimation(R.animator.slide_in, R.animator.slide_out)
        val HORIZONTAL_FADE_ANIM = FragmentAnimation(R.animator.slide_in_faded, R.animator.slide_out_faded)
    }
}

fun FragmentManager.handleBack(): Boolean {
    fragments.forEach {
        if (it.isVisible){
            if (it.childFragmentManager.handleBack())
                return true
            else if (it is OnBackHandler){
                return it.onBack()
            }
        }
    }
    if (backStackEntryCount > 0) {
        popBackStack()
        return true
    }
    return false
}

interface OnBackHandler {
    fun onBack(): Boolean
}
