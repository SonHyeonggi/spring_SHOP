package project.shop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.shop.dto.MemberDto;
import project.shop.entity.MemberEntity;
import project.shop.service.MemberService;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/join")
    public String join() { return "/member/join"; }

    @PostMapping("/join_proc")
    public String join_proc(MemberDto memberDto) {

        memberService.join_proc(memberDto);
        return "redirect:/main/home";
    }

    @GetMapping("/member/login")
    public String login() { return "/member/login"; }

    @GetMapping("/member/logout")
    public String logout() {return "/member/logout"; }

    @GetMapping("/member/list")
    public String list(Model model, @PageableDefault(page = 0, size = 5, sort = "idx", direction = Sort.Direction.DESC)Pageable pageable, @RequestParam(name = "searchKeyword", defaultValue = "none")String searchKeyword, @RequestParam(name = "selectType", defaultValue = "none")String selectType) {
        Page<MemberEntity> page = memberService.paging(pageable, searchKeyword, selectType);
        int nowPage = page.getPageable().getPageNumber() +1;
        int startPage = Math.max(nowPage -4, 1);
        int endPage = Math.min(nowPage +5, page.getTotalPages());

        model.addAttribute("mem_list", page);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/member/list";
    }
    @GetMapping("/member/view")
    public String view(Model model, @RequestParam(name = "idx")Long memberIdx) {
        model.addAttribute("vlist", memberService.view(memberIdx));
        return "/member/view";
    }

    @GetMapping("/member/delete")
    public String delete(@RequestParam("idx")Long memberIdx) {
        memberService.delete(memberIdx);
        return "redirect:/member/list";
    }

    @GetMapping("/member/modify")
    public String modify(Model model, @RequestParam("idx")Long memberIdx) {
        model.addAttribute("mlist", memberService.modify(memberIdx));
        return "/member/modify";
    }

    @PostMapping("/modify_proc")
    public String modify_proc(MemberDto memberDto) {
        memberService.modify_proc(memberDto);
        return "redirect:/member/view?idx="+memberDto.getIdx();
    }
}
