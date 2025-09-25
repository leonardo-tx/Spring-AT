package br.edu.example.api.test.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RequiredArgsConstructor
public class MockMvcHelper {
    public static ResultActions testSuccessfulResponse(
            ResultActions resultActions,
            HttpStatus status
    ) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.status().is(status.value()))
                .andDo(print())
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("error", is(nullValue())));
        return resultActions;
    }

    public static ResultActions testUnsuccessfulResponse(
            ResultActions resultActions,
            HttpStatus status,
            String code,
            String message,
            Object... messageArguments
    ) throws Exception {
        resultActions.andExpect(MockMvcResultMatchers.status().is(status.value()))
                .andDo(print())
                .andExpect(jsonPath("data", is(nullValue())))
                .andExpect(jsonPath("success", is(false)))
                .andExpect(jsonPath("error.code", is(code)))
                .andExpect(jsonPath("error.message", is(String.format(message, messageArguments))));
        return resultActions;
    }
}
