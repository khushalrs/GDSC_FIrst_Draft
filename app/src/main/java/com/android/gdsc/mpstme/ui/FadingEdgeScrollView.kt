package com.android.gdsc.mpstme.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

class FadingEdgeScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ScrollView(context, attrs, defStyle) {

    override fun getBottomFadingEdgeStrength(): Float {
        return 0F
    }
}
