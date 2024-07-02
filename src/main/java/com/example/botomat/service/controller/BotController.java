package com.example.botomat.service.controller;

import com.example.botomat.model.Bot;
import com.example.botomat.model.leaderboard.ScoreContext;
import com.example.botomat.service.bot.BotManagerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class BotController {
    private final BotManagerService botManager;

    @PostMapping(value = "/bots", produces = {"application/json"})
    public void createBots(@Valid @RequestBody List<Bot> bots) {
        bots.forEach(botManager::createBot);
    }

    @PostMapping(value = "/bot", produces = {"application/json"})
    public void createBot(@Valid @RequestBody Bot bot) {
        botManager.createBot(bot);
    }

    @GetMapping(value = "/scores", produces = {"application/json"})
    public Mono<ScoreContext> getScores() {
        return Mono.just(botManager.getScoring());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Bot controller error", e);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>("Bot controller error", httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
