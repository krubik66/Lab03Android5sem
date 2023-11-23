package com.example.lab03

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ActionMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRight.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRight : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRight.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRight().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var myAM: ActionMode? = null

    val myAMCallback: ActionMode.Callback = object: ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            requireActivity().menuInflater.inflate(R.menu.cam_view, menu)
            requireActivity().findViewById<AppBarLayout>(R.id.appbarMain).visibility = View.GONE
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            val colorText: TextView = requireActivity().findViewById(R.id.colorText)
            return when(item?.itemId) {
                R.id.camViewTheme1 -> {
                    colorText.setBackgroundColor(requireActivity().getColor(R.color.md_theme3_light_primary))
                    mode?.finish()
                    true
                }
                R.id.camViewTheme2 -> {
                    colorText.setBackgroundColor(requireActivity().getColor(R.color.md_theme1_light_primary))
                    mode?.finish()
                    true
                }
                R.id.camViewTheme3 -> {
                    colorText.setBackgroundColor(requireActivity().getColor(R.color.md_theme2_light_primary))
                    mode?.finish()
                    true
                }
                else -> {
                    true
                }
            }
        }
        override fun onDestroyActionMode(mode: ActionMode?) {
            myAM = null
            (requireActivity().findViewById<AppBarLayout>(R.id.appbarMain)).visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val backButton: Button = requireActivity().findViewById(R.id.backRightButton)
        backButton.setOnClickListener { _ ->
            val builder = AlertDialog.Builder(requireContext())
            val inflater = LayoutInflater.from(requireContext())
            val viewRadio = inflater.inflate(R.layout.dialogbackmessage, null)
            val radioGroup: RadioGroup = viewRadio.findViewById(R.id.radioGroup)
            builder.setTitle("Go Back Dialog")
            builder.setView(viewRadio)
            builder.setMessage("What color is the best?\nAre you sure at 100%?")
            builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                if (radioGroup.checkedRadioButtonId != -1) {
                    val selectedRadioButton: RadioButton =
                        viewRadio.findViewById(radioGroup.checkedRadioButtonId)
                    val data: SharedPreferences =
                        requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    val editor = data.edit()
                    editor.putString("color", selectedRadioButton.text.toString())
                    editor.putBoolean("isRated", true)
                    editor.apply()
                    requireActivity().onBackPressed()
                } else requireActivity().onBackPressed()
            }
            builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }

        val dateText: TextView = view.findViewById(R.id.dateText)
        dateText.setOnClickListener {_ ->
            val calendar = Calendar.getInstance()

            val dateDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int,
                                                     dayOfMonth: Int ->
                    dateText.text = "$dayOfMonth-${monthOfYear + 1}-$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dateDialog.show()
        }

        val colorText: TextView = view.findViewById(R.id.colorText)
        colorText.setOnLongClickListener(View.OnLongClickListener { _ ->
            if(myAM != null) {
                return@OnLongClickListener false
            }
            myAM = requireActivity().startActionMode(myAMCallback)
            true
        })

    }
}