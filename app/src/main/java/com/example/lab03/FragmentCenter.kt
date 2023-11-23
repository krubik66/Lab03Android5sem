package com.example.lab03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCenter.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCenter : Fragment(), RadioGroup.OnCheckedChangeListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fragment1: Fragment1
    lateinit var fragment2: Fragment2
    lateinit var myTr: FragmentTransaction

    private val tagFragment1 = "Fragment1"
    private val tagFragment2 = "Fragment2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if(savedInstanceState == null) {
            fragment1 = Fragment1.newInstance("just", "work")
            fragment2 = Fragment2.newInstance("just", "work")
            myTr=childFragmentManager.beginTransaction()
            myTr!!.add(R.id.mainShowFrameLayout, fragment1, tagFragment1)
            myTr!!.detach(fragment1!!)
            myTr!!.add(R.id.mainShowFrameLayout, fragment2, tagFragment2)
            myTr!!.detach(fragment2!!)
            myTr!!.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentCenter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentCenter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        myTr = childFragmentManager.beginTransaction()
        when (checkedId){
            R.id.radioButtonMain1 -> {
                myTr.detach(fragment2)
                myTr.attach(fragment1)
            }
            R.id.radioButtonMain2 -> {
                myTr.detach(fragment1)
                myTr.attach(fragment2)
            }
        }
        myTr.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().findViewById(R.id.mainRadioGroup) as RadioGroup).setOnCheckedChangeListener(this)
        if (savedInstanceState != null) {
            fragment1 = childFragmentManager.findFragmentByTag(tagFragment1) as Fragment1
            fragment2 = childFragmentManager.findFragmentByTag(tagFragment2) as Fragment2
        }
        childFragmentManager.setFragmentResultListener("msgfromchild", viewLifecycleOwner)
        { key, bundle ->
            val result = bundle.getString("msg1")
            (requireActivity().findViewById<View>(R.id.dataMainText) as TextView).setText(result)
        }
    }
}