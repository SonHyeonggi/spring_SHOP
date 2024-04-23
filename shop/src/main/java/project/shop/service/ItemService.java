package project.shop.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.shop.dto.CategoryDto;
import project.shop.dto.FilesDto;
import project.shop.dto.ItemDto;
import project.shop.entity.ItemEntity;

import java.io.IOException;
import java.util.List;

@Service
public interface ItemService {
    public void regist_proc(ItemDto itemDto) throws IOException;
    public Page<ItemEntity> list(Pageable pageable, String searchKeyword, String selectType);
    public String downloadthumb(Long itemidx, HttpServletRequest request, HttpServletResponse response);
    public void category_proc(CategoryDto categoryDto);
    public List<CategoryDto> category_list();
    public ItemDto view(ItemDto itemDto);
    public FilesDto FilesDto(ItemDto itemDto);
    public String download(String itemname, HttpServletRequest request, HttpServletResponse response);
    /*public void delete(ItemDto itemDto);
    public ItemDto modify(ItemDto itemDto);
    public void modify_proc(ItemDto itemDto, String ckb1, String ckb2, String ckb3) throws IOException;*/
}
