package com.junhwei.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String list(Model model, @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Info> page = repository.findAll(pageable);
        model.addAttribute("endpage", page.getTotalPages() - 1);
        model.addAttribute("curpage", pageable.getPageNumber());
        model.addAttribute("list", page);
        return "board";
    }

    @GetMapping("/board/read")
    public String read(Model model, @RequestParam Long id){
        Info info = repository.findById(id).orElse(null);
        info.setCount(info.getCount() + 1);
        repository.save(info);
        model.addAttribute("info", info);
        return "read";
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

    @GetMapping("/board/read/delete")
    String deleteInfo(@RequestParam(required = false) Long id){
        repository.deleteById(id);
        return "redirect:/board";
    }
}
