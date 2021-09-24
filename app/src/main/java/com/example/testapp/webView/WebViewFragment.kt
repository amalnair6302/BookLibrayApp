package com.example.testapp.webView

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.testapp.R
import com.example.testapp.commonMethod.CommonMethod
import com.example.testapp.databinding.FragmentWebviewBinding
import com.example.testapp.model.Format
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class WebViewFragment : Fragment(R.layout.fragment_webview) {

    private val TAG = WebViewFragment::class.java.simpleName
    private var format: Format? = null

    private val fragmentWebviewBinding by viewBinding(FragmentWebviewBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        Log.e(TAG, "On create view started..")
        init()
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.e(TAG, "Home button is pressed")
                Navigation.findNavController(fragmentWebviewBinding.root).navigate(R.id.action_webViewFragment_to_bookFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        val argumentsFromBookFragment = arguments
        if (argumentsFromBookFragment != null) {
            format = argumentsFromBookFragment.getParcelable("Formats")
            Log.d(TAG, "value $format")
        }

        val htmlFormat = format?.html
        val pdfFormat = format?.pdf
        val textFormat = format?.text
        if (htmlFormat != null) {
            initWebView(htmlFormat)
        } else if(pdfFormat !=null){
            initWebView(pdfFormat)
        } else if (textFormat != null) {
            initWebView(textFormat)
        } else {
            CommonMethod.showPopUp("No viewable version available", requireContext())
        }
    }


    private fun initWebView(url: String) {
        fragmentWebviewBinding.webPageView.settings.loadsImagesAutomatically = true
        fragmentWebviewBinding.webPageView.settings.javaScriptEnabled = true
        fragmentWebviewBinding.webPageView.settings.domStorageEnabled = true
        fragmentWebviewBinding.webPageView.settings.setSupportZoom(true)
        fragmentWebviewBinding.webPageView.settings.builtInZoomControls = true
        fragmentWebviewBinding.webPageView.settings.displayZoomControls = false
        fragmentWebviewBinding.webPageView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        fragmentWebviewBinding.webPageView.webViewClient = WebViewClient()
        fragmentWebviewBinding.webPageView.loadUrl(url)

    }
}