package com.pratheek.thoughts

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView


class Dashboard : BaseFragment() {

    @BindView(R.id.add_thought)
    lateinit var addThought: TextView

    @BindView(R.id.thought_cards)
    lateinit var mRecyclerView: RecyclerView

    var thoughtDataArrayList = ArrayList<ThoughtDataClass>()

    lateinit var mAdapter: ThoughtAdapter

    lateinit var thoughtViewModel: ThoughtViewModel

    override fun getLayoutResource(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thoughtViewModel = ViewModelProvider(this).get(ThoughtViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        thoughtViewModel.thoughtList.observe(viewLifecycleOwner, Observer {
            thoughtDataArrayList.clear()
            for (thought in it) {
                val thoughtDataClass = ThoughtDataClass(
                    thought.thought_content,
                    thought.thoughtTextStyle,
                    thought.thoughtCardColor,
                    thought.thoughtTextColor
                )
                thoughtDataArrayList.add(thoughtDataClass)
                mAdapter.notifyDataSetChanged()
            }
        })
        init()
    }

    override fun init() {
        mAdapter = ThoughtAdapter(
            requireActivity(),
            thoughtDataArrayList,
            object : ThoughtAdapter.ItemClickListener {
                override fun onClick(
                    content: String,
                    thoughtTextStyle: Int,
                    thoughtCardBackgroundColor: Int,
                    thoughtTextColor: String
                ) {
                    val thoughtDataClass =
                        ThoughtDataClass(
                            content,
                            thoughtTextStyle,
                            thoughtCardBackgroundColor,
                            thoughtTextColor
                        )
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.ParcelObject.THOUGHT_DATA, thoughtDataClass)
                    findNavController().navigate(R.id.action_dashboard_to_viewThought, bundle)
                }
            })
        mRecyclerView.adapter = mAdapter
        addThought.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_thoughtsNote)
        }
    }

}