package com.example.qr_go


import android.Manifest.permission.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.*
import android.print.*
import android.print.pdf.PrintedPdfDocument
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.example.qr_go.databinding.DuplicataprintBinding
import com.google.android.gms.vision.text.TextRecognizer
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.*
import java.io.File.separator
import java.text.SimpleDateFormat
import java.util.*


class Duplicataprint : AppCompatActivity() {

    private val FILE_PRINT = "last_file_print.pdf"
    private val dialog: AlertDialog? = null



    private lateinit var binding: DuplicataprintBinding
    private lateinit var textRecognizer: TextRecognizer

    private var QRCODE =
        "https://api.qrserver.com/v1/create-qr-code/?size=150x150&bgcolor=255-255-255&data="

    private var imagePath = ""
    private var imageName = ""

    private lateinit var scaledbmp: Bitmap
    private lateinit var CODEGRAVE: Bitmap

    private lateinit var TAILLE: Bitmap
    private lateinit var SKINAME: Bitmap
    private lateinit var Date: Bitmap
    private lateinit var HORS: Bitmap

    val fileName = "OGSO_SCAN.pdf"




    private lateinit var Spearhead : RadioButton
    private lateinit var  whymper : RadioButton
    private lateinit var  Bonatti : RadioButton
    private lateinit var Krumpe : RadioButton
    private lateinit var  Cosmique : RadioButton
    private lateinit var Gervasutti : RadioButton
    private lateinit var  Jaeger : RadioButton
    private lateinit var Danaides : RadioButton
    private lateinit var  Schwarztor : RadioButton
    private lateinit var Diable : RadioButton
    private lateinit var  Thor : RadioButton
    private lateinit var Marinelli : RadioButton
    private lateinit var  Corbets : RadioButton
    private lateinit var Spencer : RadioButton
    private lateinit var  coutturier : RadioButton
    private lateinit var Mallory : RadioButton



    private var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = DuplicataprintBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Spearhead = binding.radOption1
        whymper = binding.radOption2
        Bonatti = binding.radOption3
        Krumpe = binding.radOption4
        Cosmique = binding.radOption5
        Gervasutti = binding.radOption6
        Jaeger = binding.radOption7
        Danaides = binding.radOption8
        Schwarztor = binding.radOption9
        Diable = binding.radOption10
        Thor = binding.radOption11
        Marinelli = binding.radOption12
        Corbets = binding.radOption13
        Spencer = binding.radOption14
        coutturier = binding.radOption15
        Mallory = binding.radOption16



        // Skis Selection list
        Spearhead!!.setOnCheckedChangeListener(Radio_check())
        whymper!!.setOnCheckedChangeListener(Radio_check())
        Bonatti!!.setOnCheckedChangeListener(Radio_check())
        Krumpe!!.setOnCheckedChangeListener(Radio_check())
        Cosmique!!.setOnCheckedChangeListener(Radio_check())
        Gervasutti!!.setOnCheckedChangeListener(Radio_check())
        Jaeger!!.setOnCheckedChangeListener(Radio_check())
        Danaides!!.setOnCheckedChangeListener(Radio_check())
        Schwarztor!!.setOnCheckedChangeListener(Radio_check())
        Diable!!.setOnCheckedChangeListener(Radio_check())
        Thor!!.setOnCheckedChangeListener(Radio_check())
        Marinelli!!.setOnCheckedChangeListener(Radio_check())
        Corbets!!.setOnCheckedChangeListener(Radio_check())
        Spencer!!.setOnCheckedChangeListener(Radio_check())
        coutturier!!.setOnCheckedChangeListener(Radio_check())
        Mallory!!.setOnCheckedChangeListener(Radio_check())


        Qrdynamic(binding.tvText.toString(),binding.tvUserAge.text.toString())
        numericalButtons()

        /*  radio.setOnCheckedChangeListener { group, checkedId ->
            var text = getString(R.string.Chose)
            text += " " + getString(if (checkedId == 1 ) {
              R.string.java
            } else {
              R.string.python
            })
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
          }*/

        // Display name of the Application

        binding.btnCamera.setOnClickListener {
            cameraPermissions()
        }

        /*binding.btnGallery.setOnClickListener {
            storagePermissions()
        }*/


        /* binding.btnPrint.setOnClickListener {
             print()
         }*/


        /* binding.idBtnGeneratePDF.setOnClickListener {
             printPdfToFile()
         }*/

        binding.btnPdf.setOnClickListener {
            //            createPDFFILE(Common.getAppPath(this@Usine) + filename)
            printDocument()
            uploadImage()
        }

        binding.btnDup.setOnClickListener {

            Duplication()


        }



    }
    lateinit var qrEncoder: QRGEncoder
    lateinit var mBitmap: Bitmap

    lateinit var qrIV: ImageView
    lateinit var msgRes: TextView


    // Get the selected radio button text using radio button on click listener
/*
  fun radio_button_click(view: View) {
    // Get the clicked radio button instance
    val radio: RadioButton = findViewById(binding.radioGroup.checkedRadioButtonId)
    Toast.makeText(applicationContext,"On click : ${radio.text}",
      Toast.LENGTH_SHORT).show()
  }*/

    fun Qrdynamic(dynaText: String , model : String ){


        qrIV = binding.imgQrcode
        val windowManager: WindowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // on below line we are initializing a
        // variable for our default display
        val display: Display = windowManager.defaultDisplay

        // on below line we are creating a variable
        // for point which is use to display in qr code
        val point: Point = Point()
        display.getSize(point)

        // on below line we are getting
        // height and width of our point
        val width = point.x
        val height = point.y

        // on below line we are generating
        // dimensions for width and height
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        // on below line we are initializing our qr encoder
        qrEncoder = QRGEncoder(model+" "+dynaText , null, QRGContents.Type.TEXT, dimen)

        // on below line we are running a try
        // and catch block for initialing our bitmap
        try {
            // on below line we are
            // initializing our bitmap
            mBitmap = qrEncoder.encodeAsBitmap()

            // on below line we are setting
            // this bitmap to our image view
            qrIV.setImageBitmap(mBitmap)
        } catch (e: Exception) {
            // on below line we
            // are handling exception
            e.printStackTrace()
        }
    }

    fun printDocument() {
        val printManager = this
            .getSystemService(PRINT_SERVICE) as PrintManager
        val jobName = this.getString(R.string.app_name) +
                " Document"
        printManager.print(
            jobName, MyPrintDocumentAdapter(this),
            null
        )
    }

    inner class MyPrintDocumentAdapter(var context: Context) :
        PrintDocumentAdapter() {
        private var pageHeight = 0
        private var pageWidth = 0
        var myPdfDocument: PdfDocument? = null
        var totalpages = 1

        private fun drawPage(
            page: PdfDocument.Page,

            ) {
            binding.imgQrcode.drawable?.let {
                mBitmap = (it as BitmapDrawable).bitmap
                scaledbmp = Bitmap.createScaledBitmap(mBitmap, 200, 200, false)
            }

            var str = binding.tvText.text
            var skiname = binding.tvUserAge.text
            var sexy = ""
            var kiki = ""
            val alpha = StringBuffer()
            val num = StringBuffer()
            val special = StringBuffer()
            for (i in 0 until str.length) {
                if (Character.isDigit(str[i])) num.append(str[i]) else if (str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z') alpha.append(
                    str[i]
                )
                else special.append(str.get(i))

            }
            alpha.append(" ")

            if (num.length >= 3) sexy = num.substring(0, 3)


            kiki = num.substring(3)


            //val canvas = page.canvas
            val date: String =
                SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date())

            val heur: String =
                SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date())

            val pdfDocument = PdfDocument()
            val paint = Paint()


            //  val mypageInfo = PdfDocument.PageInfo.Builder(1120, 790, 1).create()
            // val myPage = pdfDocument.startPage(mypageInfo)
            val canvas: Canvas = page.canvas



            canvas.drawBitmap(scaledbmp, 10F, 50f, paint)
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.textSize = 20f
            paint.color = Color.BLACK


            canvas.drawText(skiname.toString()+" "+sexy, 220f, 80f, paint)
            canvas.drawText(sexy+" "+kiki, 220f, 100f, paint)
            canvas.drawText(date, 220f, 180f, paint)
            canvas.drawText(heur, 220f, 225f, paint)
            canvas.drawText("     OGSO-MOUNTAIN-ESSENTIALS               ", 50f, 290f, paint)
            canvas.drawText("      ------------------------               ", 50f, 350f, paint)



            /*SKINAME= bitmaptext(alpha.toString(), 1000, 20)!!
            TAILLE = bitmaptext(sexy, 1000, 20)!!
            CODEGRAVE = bitmaptext(kiki, 1000, 20)!!
            Date = bitmaptext(date, 1000, 20)!!
            HORS = bitmaptext(heur, 1000, 20)!!

            canvas.drawBitmap(SKINAME, 350F, 40f, paint)
            canvas.drawBitmap(TAILLE, 400F, 40f, paint)
            canvas.drawBitmap(TAILLE, 350F, 80f, paint)
            canvas.drawBitmap(CODEGRAVE, 400F, 80f, paint)
            canvas.drawBitmap(Date, 350F, 210f, paint)
            canvas.drawBitmap(HORS, 350F,  260f, paint)*/


        }

        fun bitmaptext(text: String?, textWidth: Int, textSize: Int): Bitmap? {
            // Get text dimensions
            val textPaint = TextPaint(
                Paint.ANTI_ALIAS_FLAG
                        or Paint.LINEAR_TEXT_FLAG
            )
            textPaint.style = Paint.Style.FILL
            textPaint.color = Color.BLACK
            textPaint.textSize = textSize.toFloat()
            val mTextLayout = StaticLayout(
                text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 8.0f, 0.0f, false
            )

            // Create bitmap and canvas to draw to
            val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
            val c = Canvas(b)

            // Draw background
            val paint = Paint(
                (Paint.ANTI_ALIAS_FLAG
                        or Paint.LINEAR_TEXT_FLAG)
            )
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            c.drawPaint(paint)

            // Draw text
            c.save()

            mTextLayout.draw(c)
            c.restore()
            return b
        }
        override fun onLayout(
            oldAttributes: PrintAttributes,
            newAttributes: PrintAttributes,
            cancellationSignal: CancellationSignal,
            callback: LayoutResultCallback,
            metadata: Bundle
        ) {
            myPdfDocument = PrintedPdfDocument(context, newAttributes)
            pageHeight = newAttributes.mediaSize!!.heightMils / 790 * 72
            pageWidth = newAttributes.mediaSize!!.widthMils / 1120 * 72
            if (cancellationSignal.isCanceled) {
                callback.onLayoutCancelled()
                return
            }
            if (totalpages > 0) {
                val builder = PrintDocumentInfo.Builder("pr.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages)
                val info = builder.build()
                callback.onLayoutFinished(info, true)
            } else {
                callback.onLayoutFailed("Page count is zero.")
            }
        }

        override fun onWrite(
            pageRanges: Array<PageRange>,
            destination: ParcelFileDescriptor,
            cancellationSignal: CancellationSignal,
            callback: WriteResultCallback
        ) {

            val newPage = PdfDocument.PageInfo.Builder(
                pageWidth,
                pageHeight, 2 ,
            ).create()
            val page = myPdfDocument!!.startPage(newPage)

            drawPage(page)

            myPdfDocument!!.finishPage(page)


            try {
                myPdfDocument!!.writeTo(
                    FileOutputStream(
                        destination.fileDescriptor
                    )
                )
            } catch (e: IOException) {
                callback.onWriteFailed(e.toString())
                return
            } finally {
                myPdfDocument!!.close()
                myPdfDocument = null
            }
            callback.onWriteFinished(pageRanges)
        }
    }



    /* private fun createPDFFILE(path: String) {

        if (File(path).exists())
            File(path).delete()




        try {
            val daydate: String =
                SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date())
            val hour: String =
                SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date())

            var str = binding.tvText.text
            var sexy = ""
            val alpha = StringBuffer()
            val num = StringBuffer()
            val special = StringBuffer()
            for (i in 0 until str.length) {
                if (Character.isDigit(str[i])) num.append(str[i]) else if (str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z') alpha.append(
                    str[i]
                )
                else special.append(str.get(i))

            }
            alpha.append(" ")

            if (num.length >= 3) sexy = num.substring(0, 3)




            binding.imgQrcode.drawable?.let {
                mBitmap = (it as BitmapDrawable).bitmap
                scaledbmp = Bitmap.createScaledBitmap(mBitmap, 350, 350, false)
            }
            val document = Document()

            //save
            PdfWriter.getInstance(document, FileOutputStream(path))
            //Open to write
            document.open()

            //Setting
            document.pageSize = PageSize.A4
            document.addCreationDate()
            document.addAuthor("")
            document.addCreator("")

            //Font setting
            val colorAccent = BaseColor(0, 153, 204, 255)
            val HeadingFontSize = 20.0f
            val valueFontSize = 26.0f

            //Custom fontBaseFont.createFont("assets/fonts/bradon_medium.otf", "UTF-8", BaseFont.EMBEDDED)
            val fontName =
                BaseFont.createFont(
                    "assets/font/bradon_medium.otf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
                );
            val titleStyle = Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK)
            addNewItem(document, "$alpha" + "$sexy", Element.ALIGN_CENTER, titleStyle)


            val valueStyle = Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK)
            addNewItem(document, "$num", Element.ALIGN_LEFT, valueStyle)
            addNewItem(document, "$hour", Element.ALIGN_LEFT, valueStyle)

            addNewItem(document, "$daydate", Element.ALIGN_UNDEFINED, valueStyle)


            val stream = ByteArrayOutputStream()
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val myImg = Image.getInstance(stream.toByteArray())
            myImg.alignment = Image.ALIGN_MIDDLE
            document.add(myImg)


            //close
            document.close()
            Toast.makeText(this@Usine, "Success", Toast.LENGTH_SHORT).show()
            printPDF()

        } catch (e: Exception) {
            Log.e("EDMTDev", "" + e.message)
        }
    }
  */

    private fun printPDF() {
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        try {
            val printAdapter =
                pdfDocumentAdapter(this@Usine, Common.getAppPath(this@Usine) + fileName)
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())

        } catch (e: Exception) {
            Log.e("EDMTDev", "" + e.message)
        }

    }






    private  fun Duplication(){
        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA), 1)
        } else {
            rest.launch(Intent(this, CameraQR::class.java))

        }

    }

    private fun cameraPermissions() {
        if (ActivityCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA), 1)
        } else {
            cameraResult.launch(Intent(this, CameraViewActivity::class.java))

        }

    }

    private fun storagePermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                2
            )
        } else {
            galleryResult.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )
        }
    }



    private val rest=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {

                val text = result.data!!.getStringExtra("text")

                binding.tvText.text = text


                Glide.with(this)
                    .load("$QRCODE$text")
                    .into(binding.imgQrcode)

            }
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraResult.launch(Intent(this, CameraViewActivity::class.java))
                } else {
                    Toast.makeText(this, "Camera permission requested", Toast.LENGTH_LONG).show()
                }
            }
            2 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    galleryResult.launch(
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    )
                } else {
                    Toast.makeText(this, "Storage permission requested", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {

                val text = result.data!!.getStringExtra("text")
                var title = ""
                binding.tvText.text = text
                title = text+binding.tvUserAge.text

                Glide.with(this)
                    .load("$QRCODE$title")
                    .into(binding.imgQrcode)

            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data!!.data != null) {
                val imageUri = result.data!!.data
                try {
                    val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
                    val bitmap = BitmapFactory.decodeStream(imageStream)
                    val inputImage: InputImage =
                        InputImage.fromBitmap(bitmap, fixRotation(imageUri))
                    imageRecognition(inputImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    private fun fixRotation(uri: Uri): Int {
        val ei: ExifInterface
        var fixOrientation = 0
        try {
            val input = contentResolver.openInputStream(uri)
            ei = ExifInterface(input!!)
            val orientation: Int = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            fixOrientation = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 80
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                ExifInterface.ORIENTATION_NORMAL -> 0
                else -> 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fixOrientation
    }

    private fun imageRecognition(inputImage: InputImage) {
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        textRecognizer.process(inputImage)
            .addOnSuccessListener {
                binding.tvText.text = it.text

                Glide.with(this)
                    .load("$QRCODE${it.text}")
                    .into(binding.imgQrcode)

            }
            .addOnFailureListener {
                Log.e("error", it.message.toString())
            }
    }


/*
    private fun saveQRCode() {
        binding.imgQrcode.drawable?.let {
            val mBitmap = (it as BitmapDrawable).bitmap
            mBitmap.saveToGallery()
            uploadImage()
        }
    }
*/


    //create file to save image from camera to gallery
    @Throws(IOException::class)
    private fun createImagineFile(): File {
        var path: String =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + separator.toString() + "OCRKotlin"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            path =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + separator.toString() + "OCRKotlin"
        }
        val outputDir = File(path)
        outputDir.mkdir()
        val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(Date())
        val fileName = "OCRKotlin_$timeStamp.jpg"
        val image = File(path + separator.toString() + fileName)
        imagePath = image.absolutePath
        imageName = fileName
        Log.e("imagePath", imagePath)
        Log.e("imageAbsolute", image.absolutePath.toString())
        return image
    }

    //save bitmap to gallery
    private fun Bitmap.saveToGallery(): Uri? {
        val file = createImagineFile()
        if (Build.VERSION.SDK_INT >= 31) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.RELATIVE_PATH, file.absolutePath)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.name)

            val uri: Uri? =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(this, contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                contentResolver.update(uri, values, null, null)
                return uri
            }
        } else {
            saveImageToStream(this, FileOutputStream(file))
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            return Uri.fromFile(file)
        }

        return null
    }

    //save image to stream
    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun numericalButtons() {

        binding.button4.setOnClickListener {
            appendToDigitOnScreen("1")
        }

        binding.button5.setOnClickListener {
            appendToDigitOnScreen("2")
        }

        binding.button6.setOnClickListener {
            appendToDigitOnScreen("3")
        }

        binding.button7.setOnClickListener {
            appendToDigitOnScreen("4")
        }

        binding.button8.setOnClickListener {
            appendToDigitOnScreen("5")
        }

        binding.button9.setOnClickListener {
            appendToDigitOnScreen("6")
        }

        binding.button10.setOnClickListener {
            appendToDigitOnScreen("7")
        }

        binding.button11.setOnClickListener {
            appendToDigitOnScreen("8")
        }

        binding.button12.setOnClickListener {
            appendToDigitOnScreen("9")
        }

        binding.button13.setOnClickListener {
            appendToDigitOnScreen("0")
        }

        binding.button15.setOnClickListener {
            val str1 = binding.tvText.text
            val n = 1
            binding.tvText.text = str1.dropLast(n)
        }

        binding.button16.setOnClickListener {

            binding.tvText.text = ""
        }



    }
    private fun appendToDigitOnScreen(digit: String ) {

        // Add each digit to our string builder


        val skiname = binding.tvUserAge.text

        val zonetext = "" + binding.tvText.text
        /*for (i in 0 until str.length) {
          if (Character.isDigit(str[i])) num.append(str[i]) else if (str[i] >= 'a' && str[i] <= 'z' || str[i] >= 'A' && str[i] <= 'Z') alpha.append(
            str[i]
          )
          else special.append(str.get(i))

        }
        alpha.append(" ")

        if (num.length >= 3) sexy = num.substring(0, 3)*/
        if (binding.tvText.text.length >= 9) {
            Toast.makeText(applicationContext, "cliqué sur print", Toast.LENGTH_SHORT).show()
            printDocument()
        } else {
            val concat = zonetext + digit


            // display it on the screen of our mobile app
            binding.tvText.text = concat
            Qrdynamic(concat, skiname as String)

        }
    }
    private fun uploadImage() {
        val file = Uri.fromFile(File(imagePath))
        val fileName = imageName
        val imageURL = "/salem/$fileName"
        Log.e("name", imageURL)
        val riversRef = storageRef.child(imageURL)
        val uploadTask = riversRef.putFile(file)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("success", "upload")
                Toast.makeText(this, "success upload", Toast.LENGTH_LONG).show()
            } else {
                Log.e("failed", "upload")
                Toast.makeText(this, "failed upload", Toast.LENGTH_LONG).show()
            }
        }
    }


    inner class Radio_check : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
            if (Spearhead!!.isChecked) {
                binding.tvUserAge.text = "Spearhead"
                Toast.makeText(applicationContext, "Spencer", Toast.LENGTH_SHORT).show()
            } else if (whymper!!.isChecked) {
                binding.tvUserAge.text = "Whymper"
                Toast.makeText(applicationContext, "Whymper", Toast.LENGTH_SHORT).show()
            }else if (Bonatti!!.isChecked) {
                binding.tvUserAge.text = "Bonatti"
                Toast.makeText(applicationContext, "Bonatti", Toast.LENGTH_SHORT).show()
            }
            else if (Krumpe!!.isChecked) {
                binding.tvUserAge.text = "Krumpe"
                Toast.makeText(applicationContext, "Krumpe", Toast.LENGTH_SHORT).show()
            }
            else if (Cosmique!!.isChecked) {
                binding.tvUserAge.text = "Cosmique"
                Toast.makeText(applicationContext, "Cosmique", Toast.LENGTH_SHORT).show()
            }
            else if (Gervasutti!!.isChecked) {
                binding.tvUserAge.text = "Gervasutti"
                Toast.makeText(applicationContext, "Gervasutti", Toast.LENGTH_SHORT).show()
            }
            else if (Jaeger!!.isChecked) {
                binding.tvUserAge.text = "Jaeger"
                Toast.makeText(applicationContext, "Jaeger", Toast.LENGTH_SHORT).show()
            }
            else if (Danaides!!.isChecked) {
                binding.tvUserAge.text = "Danaides"
                Toast.makeText(applicationContext, "Danaides", Toast.LENGTH_SHORT).show()
            }
            else if (Schwarztor!!.isChecked) {
                binding.tvUserAge.text = "Schwarztor"
                Toast.makeText(applicationContext, "Schwarztor", Toast.LENGTH_SHORT).show()
            }
            else if (Diable!!.isChecked) {
                binding.tvUserAge.text = "Diable"
                Toast.makeText(applicationContext, "Diable", Toast.LENGTH_SHORT).show()
            }
            else if (Thor!!.isChecked) {
                binding.tvUserAge.text = "Thor"
                Toast.makeText(applicationContext, "Thor", Toast.LENGTH_SHORT).show()
            }
            else if (Marinelli!!.isChecked) {
                binding.tvUserAge.text = "Marinelli"
                Toast.makeText(applicationContext, "Marinelli", Toast.LENGTH_SHORT).show()
            }
            else if (Corbets!!.isChecked) {
                binding.tvUserAge.text = "Corbet's"
                Toast.makeText(applicationContext, "Corbet's", Toast.LENGTH_SHORT).show()
            }
            else if (Spencer!!.isChecked) {
                binding.tvUserAge.text = "Spencer"
                Toast.makeText(applicationContext, "Spencer", Toast.LENGTH_SHORT).show()
            }
            else if (coutturier!!.isChecked) {
                binding.tvUserAge.text = "coutturier"
                Toast.makeText(applicationContext, "coutturier", Toast.LENGTH_SHORT).show()
            }
            else if (Mallory!!.isChecked) {
                binding.tvUserAge.text = "Mallory"
                Toast.makeText(applicationContext, "Mallory", Toast.LENGTH_SHORT).show()
            }

        }
    }


}


