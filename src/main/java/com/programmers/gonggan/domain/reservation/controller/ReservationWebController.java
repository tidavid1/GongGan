package com.programmers.gonggan.domain.reservation.controller;

import com.programmers.gonggan.domain.reservation.dto.ReservationServiceRequestDto;
import com.programmers.gonggan.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationWebController {
    private final ReservationService reservationService;

    @GetMapping("/list")
    public String listReservationForm(Model model) {
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "reservation/list";
    }

    @GetMapping("/cancel")
    public String cancelReservationForm(Model model) {
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "reservation/cancelForm";
    }

    @GetMapping("/delete")
    public String deleteReservationForm(Model model) {
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "reservation/deleteForm";
    }

    @GetMapping("/delete-all")
    public String deleteAllReservationForm() {
        return "reservation/deleteAllForm";
    }

    @DeleteMapping("/{reservationId}")
    public String deleteReservation(@PathVariable String reservationId) {
        reservationService.deleteReservationById(ReservationServiceRequestDto.builder()
                .reservationId(UUID.fromString(reservationId))
                .build());
        return "redirect:/";
    }

    @DeleteMapping("/all")
    public String deleteAllReservation() {
        reservationService.deleteAllReservations();
        return "redirect:/";
    }

    @PutMapping("/{reservationId}")
    public String cancelReservation(@PathVariable String reservationId) {
        reservationService.updateReservationStatusToCancel(ReservationServiceRequestDto.builder()
                .reservationId(UUID.fromString(reservationId))
                .build());
        return "redirect:/";
    }
}