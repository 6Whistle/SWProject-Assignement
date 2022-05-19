package com.junhwei.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/**")
@Controller
public class InfoController {
    private final InfoRepository repository;

    public InfoController(InfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String home(){
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String list(Model model){
        List<Info> infomations = repository.findAll();
        model.addAttribute("list", infomations);
        return "board";
    }

    @GetMapping("/board/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("info", new Info());
        }else{
            model.addAttribute("info", repository.findById(id).orElse(null));
        }

        return "form";
    }

    @PostMapping("/board/form")
    public String infoSubmit(@ModelAttribute Info info){
        if(info.getText().equals("") || info.getTitle().equals(""))
            return "redirect:/board";

        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        info.setCount(0);
        info.setDate(date.format(now));
        repository.save(info);
        return "redirect:/board";
    }

    @GetMapping("/boardList/{currentPage}")
    public String boardList(Model model, @RequestParam(name = "currentPage", defaultValue = "0") int currentPage) {
        int pageSize = 8;
        PageRequest pageRequest= PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<Info> page = repository.findAll(pageRequest);

        model.addAttribute("list", page);
        return "test";
    }

}
