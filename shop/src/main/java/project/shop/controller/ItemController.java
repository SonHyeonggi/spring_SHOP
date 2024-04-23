package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.shop.dto.CategoryDto;
import project.shop.dto.ItemDto;
import project.shop.entity.ItemEntity;
import project.shop.service.ItemService;

import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/regist")
    public String regist(Model model) {

        List<CategoryDto> categoryDtoList = itemService.category_list();

        model.addAttribute("clist", categoryDtoList);
        return "/item/regist";
    }

    @GetMapping("/layouts/layout")
    public String layout() {
        return"/layouts/layout";
    }
    @GetMapping("/item/resist")
    @ResponseBody
    public void getCategoryList() {

    }

    @GetMapping("/item/category")
    public String category(){ return "/item/category";}

    @PostMapping("/category_proc")
    public String category_proc(CategoryDto categoryDto) {
        System.out.println(categoryDto);
        itemService.category_proc(categoryDto);
        return "redirect:/item/regist";
    }

    @PostMapping("/regist_proc")
    public String regist_proc(ItemDto itemDto) throws IOException {
        itemService.regist_proc(itemDto);
        return "redirect:/item/list";
    }

    @GetMapping("/item/list")
    public String list(Model model, @PageableDefault(page = 0, size = 5, sort = "itemidx", direction =Sort.Direction.DESC)Pageable pageable, @RequestParam(name = "searchKeyword", defaultValue = "none")String searchkeyword, @RequestParam(name = "selectType", defaultValue = "none")String selectType) {
        Page<ItemEntity> page = itemService.list(pageable, searchkeyword, selectType);
        int nowPage = page.getPageable().getPageNumber() +1;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, page.getTotalPages());

        model.addAttribute("mem_list", page);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/item/list";
    }
    @GetMapping("/thumbnail")
    public String thumbnail(@RequestParam("itemidx")Long itemidx, HttpServletRequest request, HttpServletResponse response){
        itemService.downloadthumb(itemidx, request, response);
    return "/item/list";
    }
    @GetMapping("/item/view")
    public String view(Model model, ItemDto itemDto) {
        model.addAttribute("dview", itemService.view(itemDto));
        model.addAttribute("files", itemService.FilesDto(itemDto));
        return"/item/view";
    }

    @GetMapping("/download")
    public void download(@RequestParam("files")String files, HttpServletResponse response, HttpServletRequest request) {
        itemService.download(files, request, response);
    }

     /*   @GetMapping("/item/delete")
    public String delete(ItemDto itemDto) {
        itemService.delete(itemDto);
        return "redirect:/item/list";
    }

    @GetMapping("/item/modify")
    public String modify(Model model, ItemDto itemDto) {
        model.addAttribute("mvalue", itemService.modify(itemDto));
        model.addAttribute("mfile", itemService.FilesDto(itemDto));
        return "/item/modify";
    }
    @PostMapping("/item/modify_proc")
    public String modify_proc(ItemDto itemDto, @RequestParam(name = "ckb1", defaultValue = "none")String ckb1, @RequestParam(name = "ckb2", defaultValue = "none")String ckb2, @RequestParam(name = "ckb3", defaultValue = "none")String ckb3) throws IOException{
        System.out.println("ckb1"+ckb1);
        itemService.modify_proc(itemDto, ckb1, ckb2, ckb3);
        return "redirect:/item/view?itemidx=" + itemDto.getItemidx();
    }*/
}
