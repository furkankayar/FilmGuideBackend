package com.service.filmguide.themoviedb;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility {

    public String buildUri(String path){
        return Properties.API_BASE + path + "?api_key=" + Properties.API_KEY;
    }

    public String buildUri(String path, String param1, Object val1){
        return buildUri(path) + "&" + param1 + "=" + val1;
    }
}
