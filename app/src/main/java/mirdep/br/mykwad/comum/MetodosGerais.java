package mirdep.br.mykwad.comum;

import android.util.Base64;

public class MetodosGerais {

    public static String encodeId(String id){
        return Base64.encodeToString(id.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }
}
