package com.programmers.gonggan.common.exception;

import com.programmers.gonggan.domain.place.controller.PlaceWebController;
import com.programmers.gonggan.domain.reservation.controller.ReservationWebController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = {PlaceWebController.class, ReservationWebController.class})
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.warn(e.toString());
        model.addAttribute("name", e.getClass().getName());
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
