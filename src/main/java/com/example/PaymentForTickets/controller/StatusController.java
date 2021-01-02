package com.example.PaymentForTickets.controller;


import com.example.PaymentForTickets.domain.Application;
import com.example.PaymentForTickets.domain.Status;
import com.example.PaymentForTickets.repository.ApplicationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/status")
public class StatusController {


    private WebClient webClient;

    private final ApplicationRepository repository;

    public StatusController(ApplicationRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initWebClient() {//инициализация WebClient
        webClient = WebClient.create("http://localhost:8080/status/");
    }
//попытка реализации через многопоточность
//    private final Runnable runnable = new Runnable() {
//        @Override
//        public synchronized void run() {
//            while (true) {
//                try {
//                    for (Application application : repository.findAll()) {
//                        if (application.getStatus() != null && application.getStatus().equals(Status.PROCESSED)) {
//                            System.out.println("Ok");
//                            Long id = application.getId();
//                            webClient
//                                    .get()
//                                    .uri("new/{id}", id)
//                                    .retrieve()
//                                    .bodyToMono(Application.class);
//                        }
//                    }
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    };
//
//    private Thread thread = new Thread(runnable);
//
//
//    @GetMapping("/")
//    public Status payment() {
//        thread.start();
//        return null;
//    }


    @GetMapping("/")
    public Mono<Application> getStatus(){
        List<Application> all = repository.findAll();
        while (true) {
                try {
                    for (Application application : all) {
                        if (application.getStatus() != null && application.getStatus().equals(Status.PROCESSED)) {
                            System.out.println("Ok");
                            Long id = application.getId();
                           return webClient
                                    .put()
                                    .uri("new/{id}", id)
                                    .retrieve()
                                    .bodyToMono(Application.class);
                        }
                    }
                    Thread.sleep(1000*60);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
    }


    @PutMapping("new/{id}")
    public Application setStatus(@PathVariable("id") Application application) {
        Application one = repository.getOne(application.getId());
        List<Status> statuses = List.of(Status.values());
        int size = statuses.size();
        Random random = new Random();
        one.setStatus(statuses.get(random.nextInt(size)));
        System.out.println(one.getStatus());
        return repository.save(one);
    }


    @GetMapping("/{id}")
    public Application getStatus(@PathVariable("id") Application application) {
        return repository.getOne(application.getId());
    }
}

