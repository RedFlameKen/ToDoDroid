package com.lacaba.tododroid.controller.db.event;

import com.google.firebase.firestore.DocumentReference;

public interface OnWriteSuccessListener {

    public void onWriteSuccess(DocumentReference docref);
}
