/*
package com.example.myapplication;


//import android.support.v4.app.Fragment
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.car.carlauncher.R


class TripCompFragment : Fragment(){
    private var isOn: Boolean = false;
    public lateinit var speedView: TextView;
    private lateinit var gearView: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TCFrag","oncreate");
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout



        Log.d("TCFrag","oncreateview");
        val view = inflater!!.inflate(R.layout.comp_layout,container,false)

        // Get the text view widget reference from custom layout
        speedView = view.findViewById<TextView>(R.id.speedTextView);
        gearView = view.findViewById<TextView>(R.id.gearTextView)

        // Set a click listener for text view object

            // Change the text color
        speedView.setTextColor(Color.RED)

            // Show click confirmation



        // Return the fragment view/layout
        return view
    }

    override fun onPause() {
        super.onPause()
    }




    fun sourceValues(speed: String,gear: String){

        if (!::speedView.isInitialized){
            return;

        }

        speedView.setText("Speed: " + speed + "km/h");
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}*/
