package com.junhwei.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

//Controller using Thymeleaf
//html files' path -> ~/src/resources/templetes/**
@RequestMapping("/**")
@Controller
public class InfoController {
    
    //Repository
    private final InfoRepository repository;
    //initial Repository
    public InfoController(InfoRepository repository) {
        this.repository = repository;
    }

    //GET localhost:8080/ -> redirect GET localhost:8080/board
    @GetMapping
    public String home(){
        return "redirect:/board";
    }

    //GET localhost:8080/board(page can exist) -> Print board page
    @GetMapping("/board")
    //Page size = 8, sorted by id and descend order
    public String list(Model model, @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Info> page = repository.findAll(pageable);     //Current page data
        model.addAttribute("endpage", page.getTotalPages());    //endpage = Total pages # in html
        model.addAttribute("curpage", pageable.getPageNumber());    //curpage = Current page # in html
        model.addAttribute("list", page);   //list = Current page data
        return "board"; // print page =  ~/main/resources/templetes/board.html
    }

    //GET localhost:8080/board/read(id should exist) -> Print read data page
    @GetMapping("/board/read")
    public String read(Model model, @RequestParam Long id){
        Info info = repository.findById(id).orElse(null);      //id's data
        info.setCount(info.getCount() + 1);     //info's count++
        repository.save(info);  //update info data
        model.addAttribute("info", info);   //info = info data in html
        return "read";  // print page =  ~/main/resources/templetes/read.html
    }

    //GET localhost:8080/board/form(id can exist) -> Print create or modifiy data page
    @GetMapping("/board/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){     //info = create data in html
            model.addAttribute("info", new Info());
        }else{          //info = modifiy data(find using id) in html
            model.addAttribute("info", repository.findById(id).orElse(null));
        }
        return "form";    // print page =  ~/main/resources/templetes/form.html
    }

    //POST localhost:8080/board/form -> create or modifiy data
    @PostMapping("/board/form")
    public String infoSubmit(@ModelAttribute Info info){
        //if title or text wasn't inserted, don't update repository
        if(info.getText().equals("") || info.getTitle().equals(""))
            return "redirect:/board";

            //if modifiying data, count = before info's count
            Info beforeInfo = repository.findById(info.getId()).orElse(null);
            if(beforeInfo == null)
            info.setCount(0);
            else
            info.setCount(beforeInfo.getCount());
            //update to current date
            Date now = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            info.setDate(date.format(now));
            repository.save(info);      //save data in repository
            return "redirect:/board";   //redirect to localhost:8080/board page
    }

    //GET localhost:8080/read/delete(id should exist) -> delete data
    @GetMapping("/board/read/delete")
    String deleteInfo(@RequestParam Long id){
        repository.deleteById(id);  //delete data in repository
        return "redirect:/board";   //redirect to localhost:8080/board page
    }
}
