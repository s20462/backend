package com.example.io_backend.controller;

import com.example.io_backend.model.Victim;
import com.example.io_backend.service.VictimService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/victim")
@RequiredArgsConstructor
public class VictimController {
    private final VictimService victimService;

    @GetMapping("")
    public List<Victim> getAll() {return victimService.getVictims();}

    @GetMapping("/{id}")
    public Victim getById(@PathVariable Integer id) {return victimService.getVictimById(id);}

    @PostMapping("")
    public Victim add(@RequestBody Victim victim) {return victimService.addVictim(victim);}

    @PutMapping("/{id}")
    public void update(@RequestBody Victim victim, @PathVariable Integer id ) {victimService.updateVictim(victim,id);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        victimService.deleteVictim(id);
    }
}
