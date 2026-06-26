package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.utils.GsonInterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/* JADX INFO: loaded from: classes.dex */
public class TimelineGameControllerImpl extends GameControllerImpl implements GameController {
    public TimelineGameControllerImpl(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override // com.bq.zowi.controllers.GameControllerImpl
    public Gson buildGsonToSerializeDeserializeProgress() {
        return new GsonBuilder().registerTypeAdapter(Command.class, new GsonInterfaceAdapter()).create();
    }
}
