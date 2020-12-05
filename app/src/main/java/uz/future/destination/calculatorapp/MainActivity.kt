package uz.future.destination.calculatorapp


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.faendir.rhino_android.RhinoAndroidHelper
import kotlinx.android.synthetic.main.buttons_layout.*
import kotlinx.android.synthetic.main.input_layout.*

class MainActivity : AppCompatActivity() {


    var process: String = ""
    var count = 1
    var bracketType: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (count == 1) {
            output_tv.text = "0"
            input_tv.text = "0"
        }
        btnClear.setOnClickListener {
            input_tv.text = "0"
            output_tv.text = "0"
            count = 1
            bracketType = false
        }

        btnEqauls.setOnClickListener {
            process = input_tv.text.toString()
            process = process.replace("%", "/100")
            process = process.replace("÷", "/")
            process = process.replace("×", "*")


            var rhinoAndroidHelper = RhinoAndroidHelper(this)
            var context = rhinoAndroidHelper.enterContext()
            context.optimizationLevel = -1

            var finalResult = ""
            finalResult = try {
                var srcip = context.initSafeStandardObjects()
                context.evaluateString(srcip, process, "javascript", 1, null).toString()
            } catch (e: Exception) {
                "0"
            }
            output_tv.text = finalResult
        }

        btnBracket.setOnClickListener {
            if (count == 1) {
                output_tv.text = "0"
                input_tv.text = ""
            }
            if (bracketType) {

                process = input_tv.text.toString()
                process += ")"
                input_tv.text = process
                bracketType = false
            } else {
                process = input_tv.text.toString()
                process += "("
                input_tv.text = process
                bracketType = true
            }
        }

    }

    fun onClick(view: View) {
        if (count == 1 && !input_tv.text.toString().contains("(")) {
            output_tv.text = "0"
            input_tv.text = ""
        }
        if (count <= 15) {
            process = input_tv.text.toString()
            val button: Button = view as Button
            process += button.text.toString()
            input_tv.text = process
            count++
        }

    }
}