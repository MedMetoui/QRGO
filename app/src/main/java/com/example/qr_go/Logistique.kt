package com.example.qr_go


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qr_go.Model.User
import com.example.qr_go.adapter.TableRowAdapter
import com.example.qr_go.databinding.ActivityLogisticsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_alert2.view.*
import org.apache.commons.io.FileUtils
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*


class Logistique : AppCompatActivity() {
    private lateinit var binding: ActivityLogisticsBinding
    private val authorities = "com.example.qr_go.fileprovider"
    private var filetext: File? = null

    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<User>()
    private lateinit var tableRowAdapter: TableRowAdapter



    private lateinit var floatingActionButton : FloatingActionButton
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView : View


    var counter = 1
    var num = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.back.setOnClickListener {
            onBackPressed()

        }



        binding.buttonGer.setOnClickListener {
            cameraPermissions()
        }


        binding.buttonGer2.setOnClickListener {
            saveToTxtFile( Listscan )
                    }




    }

    override fun onBackPressed() {
        // Create the object of AlertDialog Builder class


        val builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.activity_alert,null)
        val  confirme = view.findViewById<Button>(R.id.dialogDismiss_button)
        builder.setView(view)
        confirme.setOnClickListener {
            super.onBackPressed()
            this.finish()
        }
        val  annuler = view.findViewById<Button>(R.id.dialogDismiss_button2)
        builder.setView(view)
        annuler.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun cameraPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        } else {
            cameraResult.launch(Intent(this, CameraQR::class.java))
        }
    }
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK && result.data != null) {

                val text = result.data!!.getStringExtra("text")

                binding.buttonGer2.setVisibility(View.VISIBLE)
                binding.aucunText.setVisibility(View.INVISIBLE)





                test= ""+text

                val s1 = test
                val s2 = binding.TextView2.text

                if (s2.contains(s1)) {
                    val builder = AlertDialog.Builder(this,R.style.CustomAlertDialog)
                        .create()
                    val view = layoutInflater.inflate(R.layout.activity_alert2,null)

                        view.img2_text2.text= test
                    val  annuler = view.findViewById<Button>(R.id.dialogDismiss_button2)
                    builder.setView(view)
                    annuler.setOnClickListener {
                        builder.dismiss()
                    }
                    builder.setCanceledOnTouchOutside(false)
                    builder.show()
               }else
                {
                    alllist1 = ""+text
                    binding.TextView2.text = text+ "\r\n" +binding.TextView2.text


                var str = alllist1
                var taille = ""
                var codegrave =""
                var serie =""
                val alpha = StringBuffer ()
                val num = StringBuffer()
                val special = StringBuffer()
                for (i in 0 until str.length) {
                    if (Character.isDigit(str[i])) num.append(str[i]) else if (str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z') alpha.append(str[i])
                    else special.append(str.get(i))
                }

                alpha.append(" ")

                if (num.length>=3) taille = num.substring(0,3)
                codegrave = num.substring(3,9)

                serie= taille+" "+codegrave


                binding.TextView5.text =  "$alpha;     "+"$taille;     "+"$serie" + "\r\n"+  binding.TextView5.text

                userList.add(User(counter++ , "$alpha"+"$taille","$serie"))

                Listscan = binding.TextView5.text as String
                Glide.with(this)
                    .load("$text")


            }
            }

            tableRecyclerView = findViewById(R.id.table_recycler_view)
            tableRowAdapter = TableRowAdapter(userList)

            tableRecyclerView.layoutManager = LinearLayoutManager(this)
            tableRecyclerView.adapter = tableRowAdapter
        }


   /* private fun showSimpleDialog() {
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(this)
        //Setting message manually and performing action on button click
        builder.setMessage("Etes-vous sûr de vouloir \n" +
                "quitter ?")
            .setCancelable(false)
            .setPositiveButton(
                "Oui",
                DialogInterface.OnClickListener { dialog, id -> //  Action for 'Yes' Button
                    //exit application
                    finish()
                    System.exit(0)
                })
            .setNegativeButton(
                "Non",
                DialogInterface.OnClickListener { dialog, id -> //  Action for 'No' Button
                    Toast.makeText(applicationContext, "Annuler", Toast.LENGTH_SHORT).show()
                })
        //Creating dialog box
        val alert: AlertDialog = builder.create()
        //Setting the title manually
        alert.setTitle("Attention")
        alert.show()
    }*/



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE  -> {
                //if request is cancelled, the result arrays are empty
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted, save data
                    saveToTxtFile(binding.TextView5.text.toString())
                } else {
                    //permission was denied, show toast
                    Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    private fun saveToTxtFile(mText: String ) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }



        val nametitle ="N-Series_"
        val formatter = SimpleDateFormat("yy_MM_dd_HH;mm", Locale.FRANCE)
        val now = Date()
        val fileName = nametitle+formatter.format(now).toString() + ".csv"



        val downloadPath = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).absolutePath + "/"
        var inputStream = resources.openRawResource(R.raw.nouveau)
        filetext = File(downloadPath + fileName)
        FileUtils.copyInputStreamToFile(inputStream, filetext)

        try {



            //path tp storage
            // val path = Environment.getExternalStorageDirectory()
            val path = FileProvider.getUriForFile(this, authorities, filetext!!)
            //create folder named "My Files"
            //  val dir = File()
            //  dir.mkdirs()
            //file name
            //e.g MyFile_20180623_152322.txt

            val file = filetext

            //FileWriter class is used to store characters in file
            val fw = FileWriter(file)
            val bw = BufferedWriter(fw)

            bw.write(mText)
            bw.close()





            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, path)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            shareIntent.type = "text/txt"
            startActivity(Intent.createChooser(shareIntent, "Share..."))


            /*  val uri = Uri.parse(file.toString())
              val shareIntent = Intent().apply {

                  action = Intent.ACTION_SEND
                  type = "application/txt"

                  putExtra(Intent.EXTRA_STREAM, Uri.parse(path.toString() + "/My Files/Engraved_Code_List\"+\"$timeStamp\"+\".txt"))

              }
              startActivity(Intent.createChooser(shareIntent, "Share Via"))
  */


            //show file name and path where file is saved
            Toast.makeText(this, "$fileName is saved to DOWNLOADS", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            //if anything goes wrong
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }


    }




    companion object {
        private val WRITE_EXTERNAL_STORAGE_CODE = 1
        private const val FILE_NAME = "example.txt"
        private var Listscan =""
        private var alllist1 =""
        private var test =""
    }

}



