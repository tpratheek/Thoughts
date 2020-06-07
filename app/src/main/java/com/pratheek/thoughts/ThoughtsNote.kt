package com.pratheek.thoughts

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorShape

class ThoughtsNote : BaseFragment() {

    @BindView(R.id.card_color_theme)
    lateinit var cardThemeColor: TextView

    @BindView(R.id.thought_card)
    lateinit var thoughtCardView: CardView

    @BindView(R.id.thought_edit_text)
    lateinit var thoughtEditText: EditText

    @BindView(R.id.edit_text_font_theme)
    lateinit var thoughtEditTextFontTheme: TextView

    @BindView(R.id.edit_text_color)
    lateinit var editTextColorTheme: TextView

    @BindView(R.id.save_thought)
    lateinit var saveThoughtsTextView: TextView

    @BindView(R.id.close_thought)
    lateinit var closeThought: TextView


    val cardColorIntArray = intArrayOf(
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
        R.color.colorWhite
    )

    val editTextFontFamily = arrayListOf(
        R.font.aldrich,
        R.font.abel,
        R.font.aladin,
        R.font.allura,
        R.font.roboto_light,
        R.font.swanky_and_moo_moo,
        R.font.roboto
    )

    lateinit var thoughtViewModel: ThoughtViewModel

    var editTextFont: Int = R.font.roboto

    var cardColor: Int = R.color.colorWhite

    var textColor: String = "#000000"

    override fun getLayoutResource(): Int {
        return R.layout.fragment_thoughts_note
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null && thoughtEditText.text.isEmpty()) {
            val thoughtDataClass =
                requireArguments().getParcelable<ThoughtDataClass>(Constants.ParcelObject.THOUGHT_DATA)
            thoughtEditText.setText(thoughtDataClass!!.thoughtContent)
            cardColor = thoughtDataClass.thoughtCardBackgroundColor
            textColor = thoughtDataClass.thoughtTextColor
            editTextFont = thoughtDataClass.thoughtTextStyle
        }
        thoughtViewModel = ViewModelProvider(this).get(ThoughtViewModel::class.java)
        init()
    }

    override fun init() {
        setViews()
        cardThemeColor.setOnClickListener {
            cardColor = cardColorIntArray.random()
            thoughtCardView.setCardBackgroundColor(getColor(requireActivity(), cardColor))
        }

        thoughtEditTextFontTheme.setOnClickListener {
            editTextFont = editTextFontFamily.random()
            val face = ResourcesCompat.getFont(requireActivity(), editTextFont)
            thoughtEditText.typeface = face
        }

        editTextColorTheme.setOnClickListener {
            ColorPickerDialog.Builder(requireActivity())
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor(getColor(requireActivity(), R.color.colorPrimaryDark))
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        textColor = colorHex
                        thoughtEditText.setTextColor(color)
                    }
                })
                .show()
        }

        saveThoughtsTextView.setOnClickListener {
            if (thoughtEditText.text.toString().trim().isNotEmpty()) {
                val bundle = Bundle()
                val thoughtDataClass =
                    ThoughtDataClass(
                        thoughtEditText.text.toString(),
                        editTextFont,
                        cardColor,
                        textColor
                    )
                val thoughtEntity =
                    ThoughtEntity(
                        thoughtEditText.text.toString(),
                        editTextFont,
                        cardColor,
                        textColor
                    )
                thoughtViewModel.insert(thoughtEntity)
                bundle.putParcelable(Constants.ParcelObject.THOUGHT_DATA, thoughtDataClass)
                findNavController().navigate(R.id.action_thoughtsNote_to_viewThought, bundle)
            } else {
                Toast.makeText(requireActivity(), "Please Write Your Thought", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        closeThought.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setViews() {
        if (editTextFont == -1) {
            val face = ResourcesCompat.getFont(requireActivity(), R.font.roboto)
            thoughtEditText.typeface = face
        } else {
            val face = ResourcesCompat.getFont(requireActivity(), editTextFont)
            thoughtEditText.typeface = face
        }
        thoughtEditText.setTextColor(Color.parseColor(textColor))
        if (cardColor == -1)
            thoughtCardView.setCardBackgroundColor(
                getColor(
                    requireActivity(),
                    R.color.colorWhite
                )
            )
        else
            thoughtCardView.setCardBackgroundColor(getColor(requireActivity(), cardColor))

    }
}