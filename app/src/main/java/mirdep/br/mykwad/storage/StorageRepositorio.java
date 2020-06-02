package mirdep.br.mykwad.storage;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import mirdep.br.mykwad.DRONES.DroneRepositorio;

public class StorageRepositorio {
    private static StorageRepositorio INSTANCE;
    private static final String LOG_TAG = "[StorageRepositorio]";

    public static StorageRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StorageRepositorio();
        return INSTANCE;
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

//    public void readData(StorageReference ref, final OnGetDataListener listener) {
//        listener.onStart();
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listener.onSuccess(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                listener.onFailure();
//            }
//        });
//
//    }
}
