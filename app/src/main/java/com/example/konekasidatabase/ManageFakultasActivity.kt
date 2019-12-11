@file:Suppress("DEPRECATION")
package com.example.konekasidatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_manage_fakultas.*
import org.json.JSONObject
import javax.xml.transform.Templates

class ManageFakultasActivity : AppCompatActivity () {
    lateinit var i: Intent
    lateinit var add: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_fakultas)

        add = findViewById(R.id.btnCreate)
        i = intent
        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()


            }
        }
        add.setOnClickListener {
            onCreate()
        }
    }
}
private fun onEditMode() {
    TODO("not implemented") //
    use File | File Templates.txt_kodefakultas.setText(i.getStringExtra("txt_kodefakultas"))
    txt_namafakultas.setText(i.getStringExtra("txt_namafakultas"))

    btnCreate.visibility = View.GONE
    btnUpdate.visibility = View.VISIBLE
    btnDelete.visibility = View.VISIBLE

}

private fun onCreate() {

    val loading = progressDialog(this)
    loading.setMessage("Menambahkan data ...")
    loading.show()

    AndroidNetworking.post(ApiEndPoint.CREATE)
        .addBodyParameter("kodefakultas", txt_kodefakultas.text.toString())
        .addBodyParameter("namafakultas", txt_namafakultas.text.toString())
        .build()
        .getAsJSONObject(object : JSONObjectRequestListener {
            override fun onResponse(response: JSONObject) {
                loading.dismiss()

                Toast.makeText(
                    applicationContext,
                    response?.getString("message"),
                    Toast.LANGTH_SHORT
                ).show()

                if (response?.getString("message")?.contains("successfully")!!) {
                    this@ManagerFakultasActivity.finish()

                }
            }

            override fun onError(anError: ANError?) {
                loading.dismiss()
                Log.d("ONERROR", anError?.errorDetail?.toString())
                Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                    .show()
            }
        })


}
}
