package guepardoapps.whosbirthday.activities

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import guepardoapps.whosbirthday.R
import guepardoapps.whosbirthday.database.birthday.DbBirthday
import guepardoapps.whosbirthday.model.Birthday
import kotlinx.android.synthetic.main.side_add.*

class ActivityAdd : Activity() {
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.side_add)

        saveButton = findViewById(R.id.save_birthday_edit_button)
        saveButton.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                saveButton.isEnabled = true
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        birthday_name_edit_textview.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, DbBirthday(this).getNames()))
        birthday_name_edit_textview.addTextChangedListener(textWatcher)

        birthday_group_edit_textview.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, DbBirthday(this).getGroups()))
        birthday_group_edit_textview.addTextChangedListener(textWatcher)

        birthday_edit_datePicker.setOnClickListener { saveButton.isEnabled = true }

        saveButton.setOnClickListener {
            birthday_name_edit_textview.error = null
            birthday_group_edit_textview.error = null
            var cancel = false
            var focusView: View? = null

            val name = birthday_name_edit_textview.text.toString()
            if (TextUtils.isEmpty(name)) {
                birthday_name_edit_textview.error = createErrorText(getString(R.string.error_field_required))
                focusView = birthday_name_edit_textview
                cancel = true
            }

            val group = birthday_group_edit_textview.text.toString()
            if (TextUtils.isEmpty(group)) {
                birthday_group_edit_textview.error = createErrorText(getString(R.string.error_field_required))
                focusView = birthday_group_edit_textview
                cancel = true
            }

            val dayOfMonth = birthday_edit_datePicker.dayOfMonth
            val month = birthday_edit_datePicker.month
            val year = birthday_edit_datePicker.year

            if (cancel) {
                focusView?.requestFocus()
            } else {
                DbBirthday(this).add(Birthday(0, name, group, dayOfMonth, month, year, true))
                finish()
            }
        }
    }

    private fun createErrorText(errorString: String): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder(errorString)
        spannableStringBuilder.setSpan(ForegroundColorSpan(Color.RED), 0, errorString.length, 0)
        return spannableStringBuilder
    }
}