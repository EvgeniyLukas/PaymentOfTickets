package com.example.PaymentForTickets.controller;

import com.example.PaymentForTickets.domain.Application;
import com.example.PaymentForTickets.domain.Status;
import com.example.PaymentForTickets.repository.ApplicationRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationRepository repository;


    public ApplicationController(ApplicationRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/{num}")//передаем номер маршрута и дату и время отправки заявки
    public Application acceptingApplications(@PathVariable("num") Integer num,
                                             @RequestParam(name = "date", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
        System.out.println(LocalDateTime.now());
        Application application = new Application();
        application.setRouteNumber(num);
        application.setDataTime(dateTime);
        application.setStatus(Status.PROCESSED);
        return application;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createApplication(@Valid @RequestBody Application application) {
      return repository.save(application).getId();
    }

}