package com.diyorbek.datastorepreferencesl1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.diyorbek.datastorepreferencesl1.databinding.ActivityMainBinding
import com.diyorbek.datastorepreferencesl1.manager.DataStoreManager
import com.diyorbek.datastorepreferencesl1.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dataStore = DataStoreManager(this)
        binding.saveUser.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val last_name = binding.editLastName.text.toString().trim()
            val age = binding.editAge.text.toString().trim().toInt()
            if (name.isNotBlank() && last_name.isNotBlank() && age.toString().isNotBlank()){
                val user = User(name,last_name,age)
                GlobalScope.launch(Dispatchers.IO) {
                    dataStore.saveUser(user)
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this@MainActivity, "Enter Data!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.getUser.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                dataStore.getUser().collect{
                    binding.textView.text = it.toString()
                }
            }
        }
    }
}