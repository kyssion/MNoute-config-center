package com.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mouter.admin.data.answer.Answer;
import org.mouter.admin.data.answer.ErrorAnser;

public class test {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Answer answer = Answer.createAnswer(200,"sdf",new ErrorAnser(123,"fffff"));
        System.out.println(objectMapper.writeValueAsString(answer));
//        System.out.println(objectMapper.writeValueAsString(new ErrorAnser(123,"fffff")));
    }
}
