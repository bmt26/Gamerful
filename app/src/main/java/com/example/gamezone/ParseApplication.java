package com.example.gamezone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("K3TYVQAhBeycJg8a5Q6YLUVybsUio52JKDhlHDoj")
                .clientKey("r2QhXVlEq0QmEBuSBACpcFOMK1HuamT8hJ5mD9An")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
