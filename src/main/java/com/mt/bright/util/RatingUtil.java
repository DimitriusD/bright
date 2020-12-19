package com.mt.bright.util;

import com.mt.bright.entity.Rating;

public class RatingUtil {

    static int upgradeRating(int overall, int total, int newRating){
        return ((overall * total) + newRating) / (total + 1);

    }
}
