package mirdep.br.mykwad.comum;

import android.text.method.DigitsKeyListener;
import android.widget.EditText;

public class FormatarEditText {

    public static EditText editTextEmail(final EditText editText){
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789@!#$%&'*+-/=?^_`{|}~.abcdefghijklmnopqrstuvwxyz"));
        return editText;
    }
}
