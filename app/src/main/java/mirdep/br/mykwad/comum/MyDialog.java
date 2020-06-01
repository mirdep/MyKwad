package mirdep.br.mykwad.comum;

import android.app.ProgressDialog;
import android.content.Context;

import mirdep.br.mykwad.R;

public class MyDialog {
    public static ProgressDialog criarProgressDialog(Context context, String text){
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage(text);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
