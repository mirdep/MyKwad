package mirdep.br.mykwad.repositorio;

public class StorageRepositorio {
    private static StorageRepositorio INSTANCE;
    private static final String LOG_TAG = "[StorageRepositorio]";

    public static StorageRepositorio getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StorageRepositorio();
        return INSTANCE;
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
