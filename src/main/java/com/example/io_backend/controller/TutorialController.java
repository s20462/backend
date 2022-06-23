package com.example.io_backend.controller;

import com.example.io_backend.model.Tutorial;
import com.example.io_backend.service.TutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tutorial")
@RequiredArgsConstructor
public class TutorialController {
    private final TutorialService tutorialService;

    @GetMapping("")
    public List<Tutorial> getAll() {
        return tutorialService.getTutorials();
    }

    @GetMapping("/{id}")
    public Tutorial getById(@PathVariable Integer id) {
        return tutorialService.getTutorialById(id);
    }

    @PostMapping("")
    public Tutorial add(@RequestBody Tutorial tutorial) {
        return tutorialService.addTutorial(tutorial);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Tutorial tutorial, @PathVariable Integer id){
        tutorialService.updateTutorial(tutorial,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        tutorialService.deleteTutorial(id);
    }
}
