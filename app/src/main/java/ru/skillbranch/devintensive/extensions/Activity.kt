package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inMethManag : InputMethodManager  = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view : View? = this.currentFocus
    if (view == null) view = View(this)
    inMethManag.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Activity.isKeyboardOpen () : Boolean {
    var view : View? = this.window.decorView
    if (view == null) view = View(this)
    var rec : Rect? = null

    view.getWindowVisibleDisplayFrame(rec)

    return when {
        view.rootView.height - (rec!!.bottom - rec.top) > 0 -> true
        else -> false
    }
}