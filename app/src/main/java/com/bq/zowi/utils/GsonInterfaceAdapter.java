package com.bq.zowi.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public class GsonInterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    @Override // com.google.gson.JsonSerializer
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getName());
        wrapper.add("data", context.serialize(object));
        return wrapper;
    }

    @Override // com.google.gson.JsonDeserializer
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) jsonElement;
        return (T) jsonDeserializationContext.deserialize(get(jsonObject, "data"), typeForName(get(jsonObject, "type")));
    }

    private Type typeForName(JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(JsonObject wrapper, String memberName) {
        JsonElement elem = wrapper.get(memberName);
        if (elem == null) {
            throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        }
        return elem;
    }
}
