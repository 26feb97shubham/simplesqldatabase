package com.example.simpledatabaseexample
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.simpledatabaseexample.databinding.ActivityMainBinding
import com.example.simpledatabaseexample.handler.DatabaseHandler
import com.example.simpledatabaseexample.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var user: User
    private lateinit var dataclasses : DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonSubmit.setOnClickListener {
            if (binding.edittextName.text.isNotEmpty() &&
                    binding.edittextAge.text.isNotEmpty()){

                user = User(binding.edittextName.text.toString(),binding.edittextAge.text.toString().toInt())
                dataclasses = DatabaseHandler(this)
                dataclasses.insertData(this,user)

            }else{
                Toast.makeText(this,
                "Please fill the details ",
                Toast.LENGTH_LONG)
                    .show()
            }
            binding.buttonRead.performClick()
        }

        binding.buttonRead.setOnClickListener {
            val data = dataclasses.readData(this)
            binding.textviewResult.text = ""
            for (i in 0 until data.size){
                binding.textviewResult.append(
                    data[i].id.toString() + " " +
                            data[i].name.toString() + " " +
                            data[i].age.toString() + " \n"
                )
            }
        }

        binding.buttonUpdate.setOnClickListener {
            dataclasses.updateData(this)
            binding.buttonRead.performClick()
        }

        binding.buttonDelete.setOnClickListener {
            dataclasses.deleteData()
            binding.buttonRead.performClick()
        }
    }
}