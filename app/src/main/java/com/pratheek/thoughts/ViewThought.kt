package com.pratheek.thoughts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import java.io.File
import java.io.FileOutputStream


class ViewThought : BaseFragment() {

    @BindView(R.id.close_thought)
    lateinit var closeTextView: TextView

    @BindView(R.id.background_color_theme)
    lateinit var backgroundColorThemeTextView: TextView

    @BindView(R.id.thought_note_background)
    lateinit var thoughNoteBackground: RelativeLayout

    @BindView(R.id.thought_card)
    lateinit var thoughtCardView: CardView

    @BindView(R.id.thought_text_view)
    lateinit var thoughtTextView: TextView

    @BindView(R.id.take_screen_shot)
    lateinit var capture: TextView

    var backgroundColor: Int = -1

    val backgroundColorIntArray = intArrayOf(
        R.color.colorRed,
        R.color.colorPurple,
        R.color.colorIndigo,
        R.color.colorBlue,
        R.color.colorCyan,
        R.color.colorTeal,
        R.color.colorGreen,
        R.color.colorLime,
        R.color.colorYellow,
        R.color.colorAmber,
        R.color.colorOrange,
        R.color.colorBrown,
        R.color.colorBlueGrey,
        R.color.colorWhite,
        R.color.colorRed100,
        R.color.colorPurple100,
        R.color.colorIndigo100,
        R.color.colorBlue100,
        R.color.colorCyan100,
        R.color.colorTeal100,
        R.color.colorGreen100,
        R.color.colorLime100,
        R.color.colorYellow100,
        R.color.colorAmber100,
        R.color.colorOrange100,
        R.color.colorBrown100,
        R.color.colorBlueGrey100
    )

    lateinit var thoughtDataClass: ThoughtDataClass

    override fun getLayoutResource(): Int {
        return R.layout.fragment_view_thought
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            thoughtDataClass =
                requireArguments().getParcelable(Constants.ParcelObject.THOUGHT_DATA)!!
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.dashboard, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun init() {
        setViews()

        backgroundColorThemeTextView.setOnClickListener {
            backgroundColor = backgroundColorIntArray.random()
            thoughNoteBackground.setBackgroundColor(
                getColor(
                    requireActivity(),
                    backgroundColor
                )
            )
        }

        thoughtCardView.setCardBackgroundColor(
            getColor(
                requireActivity(),
                thoughtDataClass.thoughtCardBackgroundColor
            )
        )

        capture.setOnClickListener {
            capture.visibility = View.GONE
            closeTextView.visibility = View.GONE
            backgroundColorThemeTextView.visibility = View.GONE

            val bitmap = ScreenUtils.getScreenShot(thoughNoteBackground)
            try {

                val cachePath = File(requireContext().cacheDir, "images")
                cachePath.mkdirs()
                val stream = FileOutputStream("${cachePath}/image.jpeg")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.close()
            } catch (e: Exception) {
                Log.e("I/O File", e.toString())
            }
            val imagePath = File(requireContext().cacheDir, "images")
            val newFile = File(imagePath, "image.jpeg")
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "com.pratheek.thoughts.fileprovider",
                newFile
            )
            shareScreenShot(uri)
            closeTextView.visibility = View.VISIBLE
            backgroundColorThemeTextView.visibility = View.VISIBLE
            capture.visibility = View.VISIBLE
        }

        closeTextView.setOnClickListener {
            findNavController().popBackStack(R.id.dashboard, false)
        }
    }

    private fun shareScreenShot(uri: Uri?) {
        if (uri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file

            shareIntent.setDataAndType(uri, requireActivity().contentResolver.getType(uri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }

    private fun setViews() {
        thoughtTextView.setTextColor(Color.parseColor(thoughtDataClass.thoughtTextColor))
        val face = ResourcesCompat.getFont(requireActivity(), thoughtDataClass.thoughtTextStyle)
        thoughtTextView.typeface = face
        thoughtTextView.text = thoughtDataClass.thoughtContent
    }


}