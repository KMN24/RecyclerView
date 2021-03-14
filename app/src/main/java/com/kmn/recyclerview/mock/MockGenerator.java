package com.kmn.recyclerview.mock;

// генератор

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class MockGenerator {

    public static List<Mock> generator(int count){

        List<Mock> mocks = new ArrayList<>(count);

        Random random = new Random();

        for(int i=0; i<count; i++){ // генератор готов
            mocks.add(new Mock(UUID.randomUUID().toString(), random.nextInt(200)));
        }

        return mocks;
    }

}
