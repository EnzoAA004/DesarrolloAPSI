package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Repository.turnosRepository;
import org.example.backgrupo3vima.Service.turnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turnos")
public class turnosController {
    @Autowired
    private turnosService turnosService;

    @Autowired
    private turnosRepository turnosRepository;
}
