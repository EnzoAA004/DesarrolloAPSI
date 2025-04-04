package Controller;

import Service.doctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class doctorController {
    @Autowired
    private doctorServiceImpl doctorServiceImpl;
}
