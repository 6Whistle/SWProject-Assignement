package com.junhwei.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InfoAPIController {

    @Autowired
    private InfoRepository repository;

    @GetMapping("/info")
    List<Info> all(){
        return repository.findAll();
    }

    @GetMapping("/info/{id}")
    Info getInfo(@PathVariable Long id){
        return repository.findById(id)
                .map(info -> {
                    info.setCount(info.getCount()+1);
                    return repository.save(info);
                }).orElseThrow(() -> {
                    new InfoNotFoundException(id);
                    return null;
                });
    }

    @PostMapping("/info")
    Info newInfo(@RequestBody Info newInfo){
        if(newInfo.getTitle().equals("") || newInfo.getText().equals(""))
            return null;
        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        newInfo.setDate(date.format(now));
        return repository.save(newInfo);
    }

    @PutMapping("/info/{id}")
    Info replaceInfo(@RequestBody Info newInfo, @PathVariable Long id){
        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return repository.findById(id)
                .map(info -> {
                    info.setTitle(newInfo.getTitle());
                    info.setText(newInfo.getText());
                    info.setDate(date.format(now));
                    info.setCount(newInfo.getCount());
                    return repository.save(info);
                }).orElseGet(() -> {
                    newInfo.setDate(date.format(now));
                    newInfo.setCount(0);
                    return repository.save(newInfo);
                });
    }

    @DeleteMapping("info/{id}")
    void deleteInfo(@PathVariable Long id){
        repository.deleteById(id);
    }
}
