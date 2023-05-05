package com.example.exampleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView


import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exampleretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),OnQueryTextListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var  adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
       setContentView(binding.root)
       binding.svDogs.setOnQueryTextListener(this)
       initRecyclerView()
        // setContentView(R.layout.activity_main)

    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }


    private  fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private  fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call:Response<DogResponse> = getRetrofit().create(APÏService::class.java).getDoogsByBreeds("$query/images")
            val  puppies = call.body()

            runOnUiThread{
                  if (call.isSuccessful){
                      val images: List<String> = puppies?.images ?: emptyList()
                      dogImages.clear()
                      dogImages.addAll(images)
                      adapter.notifyDataSetChanged()

                  }else{
                      showError()
                  }
                  hideKeyboard()
              }

        }

    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
    private fun showError() {
        Toast.makeText(this,"ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return  true
    }
}