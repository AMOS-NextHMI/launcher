//package com.example.myapplication;
//
//
//import android.content.Context
//import android.graphics.Color
//import android.os.Bundle
//import android.util.Log
////import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.android.car.carlauncher.R
//import kotlinx.android.synthetic.main.comp_layout.*
//
//
//class TripCompFragment : Fragment(){
//    private var isOn: Boolean = false;
//    public lateinit var speedView: TextView;
//    private lateinit var gearView: TextView;
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d("TCFrag","oncreate");
//    }
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        // Get the custom view for this fragment layout
//        Log.d("TCFrag","oncreateview");
//        val view = inflater!!.inflate(R.layout.comp_layout,container,false)
//
//        // Get the text view widget reference from custom layout
//        speedView = view.findViewById<TextView>(R.id.speedTextView);
//        gearView = view.findViewById<TextView>(R.id.gearTextView)
//
//        // Set a click listener for text view object
//
//            // Change the text color
//        speedView.setTextColor(Color.RED)
//
//            // Show click confirmation
//
//
//
//        // Return the fragment view/layout
//        return view
//    }
//
//    override fun onPause() {
//        super.onPause()
//    }
//
//    fun sourceValues(speed: String,gear: String){
//        System.out.println("lahdidah");
//        if (::speedView.isInitialized){
//            Log.d("TCFrag","nully bly1"+speedView);
//        }else{
//            Log.d("TCFrag","init1");
//        }
//        println("wtfff");
//        Log.d("TCFrag","ok now")
//        println("borf diddly :)))");
//        if (::speedView.isInitialized){
//            Log.d("TCFrag","notnull");
//
//        }else{
//            Log.d("TCFrag","nully bly");
//            return;
//
//        }
//        Log.d("apparently we're going on","rip");
//
//        speedView.setText("Speed: " + speed + "km/h");
//        gearView.setText("hello world");
//    }
//
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//    }
//
//    override fun onStart() {
//        super.onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//    }
//}