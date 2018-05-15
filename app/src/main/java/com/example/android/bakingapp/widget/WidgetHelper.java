package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.model.Recipe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WidgetHelper {

    public static void saveToFile(Recipe recipe, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput("widget.data", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(recipe);
            objectOutputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Recipe loadFromFile(Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput("widget.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Recipe recipe = (Recipe) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return recipe;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context,RecipeIngredientWidgetProvider.class));
        context.sendBroadcast(intent);
    }
}
