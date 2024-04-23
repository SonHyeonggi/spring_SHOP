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
import project.shop.entity.CategoryEntity;
import project.shop.entity.FilesEntity;
import project.shop.entity.ItemEntity;
import project.shop.repository.CategoryRepository;
import project.shop.repository.FilesRepository;
import project.shop.repository.ItemRepository;
import net.coobird.thumbnailator.Thumbnailator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService{
private final ItemRepository itemRepository;
private final FilesRepository filesRepository;
private final CategoryRepository categoryRepository;

    public ItemServiceImpl(ItemRepository itemRepository, FilesRepository filesRepository ,CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.filesRepository = filesRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> category_list() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        /*for (CategoryEntity categoryEntity : categoryEntities) {
            categoryDtoList.add(new CategoryDto(categoryEntity.getCategory_code(), categoryEntity.getItem_type()));
        }*/
        for(int i = 0; i<categoryEntities.size(); i++) {
            CategoryDto categoryDto= new CategoryDto();
            categoryDto.setCategory_code(categoryEntities.get(i).getCategory_code());
            categoryDto.setItem_type(categoryEntities.get(i).getItem_type());
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }

    @Override
    public void category_proc(CategoryDto categoryDto){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory_code(categoryDto.getCategory_code());
        categoryEntity.setItem_type(categoryDto.getItem_type());
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void regist_proc(ItemDto itemDto) throws IOException {

        String projactPath = "C:\\Users\\YJ\\Desktop\\spring\\shop\\shop\\src\\main\\resources\\static\\image\\";

        String uuid = UUID.randomUUID().toString();

        String original1 = itemDto.getOriginal1().getOriginalFilename();
        String original2 = itemDto.getOriginal2().getOriginalFilename();
        String original3 = itemDto.getOriginal3().getOriginalFilename();

        String filename1 = uuid + "_" + original1;
        String filename2 = uuid + "_" + original2;
        String filename3 = uuid + "_" + original3;

        File saveFile1 = new File(projactPath, filename1);
        File saveFile2 = new File(projactPath, filename2);
        File saveFile3 = new File(projactPath, filename3);

        itemDto.getOriginal1().transferTo(saveFile1);
        itemDto.getOriginal2().transferTo(saveFile2);
        itemDto.getOriginal3().transferTo(saveFile3);

        try {
            /* 추가된 부분............... */
            File thumbnailFile = new File(projactPath + "thumbnail\\", "s_" + filename1);

            BufferedImage bo_image = ImageIO.read(saveFile1);

            /* 비율 */
            double ratio = 3;
            int width = (int) (bo_image.getWidth() / ratio);
            int height = (int) (bo_image.getHeight() / ratio);

            // 생성자 매개변수 넓이, 높이, 생성될 이미지 타입
            BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

            Graphics2D graphic = bt_image.createGraphics();

            graphic.drawImage(bo_image, 0, 0, width, height, null);

            ImageIO.write(bt_image, "jpg", thumbnailFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemEntity itemEntity = itemDto.toEntity();
        CategoryEntity categoryEntity = itemDto.tocategoryEntity(itemEntity);
        FilesEntity filesEntity = itemDto.tofilesEntity(itemEntity);
        filesEntity.setFilename1(filename1);
        filesEntity.setOriginal1(original1);
        filesEntity.setFilename2(filename2);
        filesEntity.setOriginal2(original2);
        filesEntity.setFilename3(filename3);
        filesEntity.setOriginal3(original3);


        itemRepository.save(itemEntity);
//        categoryRepository.save(categoryEntity);
        filesRepository.save(filesEntity);

    }

    @Override
    public Page<ItemEntity> list(Pageable pageable, String searchKeyword, String selectType) {

    /*    List<ItemEntity> list = itemRepository.findAll();
        List<ItemDto> dlist = new ArrayList<>();

        for(int i=0; i<list.size(); i++) {
            dlist.add(list.get(i).toDto());
        }*/

        Page<ItemEntity> page = null;
        if(searchKeyword.equals("none"))
        {
            page=itemRepository.findAll(pageable);
        }
        else if(selectType.equals("itemname"))
        {
            page=itemRepository.findByItemnameContaining(searchKeyword, pageable);
        }
        /*else if(selectType.equals("item_type"))
        {
            page=categoryRepository.findByItem_typeContaining(searchKeyword, pageable);
        }*/
        else
        {
            page=itemRepository.findByItemcountryContaining(searchKeyword, pageable);
        }

        return page;
    }

    @Override
    public String downloadthumb(Long itemidx, HttpServletRequest request, HttpServletResponse response) {
        String dir = "C:\\Users\\YJ\\Desktop\\spring\\shop\\shop\\src\\main\\resources\\static\\image\\";
        String filename = itemRepository.findById(itemidx).get().getFilesEntities().get(0).getFilename1();

        String filePath = dir + "thumbnail\\" + "s_" + filename;
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = "";

        try{
            try{
                file = new File(filePath);
                in = new FileInputStream(file);
            }catch(FileNotFoundException fe){
                skip = true;
            }


            client = request.getHeader("User-Agent");
            response.reset();

            if(ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))
            {

                response.setContentType("image/jpeg");
            }
            else
            {

                if(client.indexOf("MSIE") != -1){
                    response.setHeader ("Content-Disposition", "attachment; filename="+new String(filename.getBytes("KSC5601"),"ISO8859_1"));

                }else{
                    filename = new String(filename.getBytes("utf-8"),"iso-8859-1");

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", ""+file.length() );
                }
            }

            if(!skip){
                os = response.getOutputStream();
                byte b[] = new byte[(int)file.length()];
                int leng = 0;

                while( (leng = in.read(b)) > 0 ){
                    os.write(b,0,leng);
                }
            }else{
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("File not find.");
                System.out.println("File not find.");
            }

            in.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return filename;

    }

    @Override
    public ItemDto view(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findById(itemDto.getItemidx()).orElse(null);
        return itemEntity.toDto();
    }

    @Override
    public FilesDto FilesDto(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findById(itemDto.getItemidx()).orElse(null);
        return itemEntity.getFilesEntities().get(0).filesDto();
    }

    @Override
    public String download(String files, HttpServletRequest request, HttpServletResponse response) {
        String dir = "C:\\Users\\YJ\\Desktop\\spring\\shop\\shop\\src\\main\\resources\\static\\image\\";
        String filename = files;


        String filePath = dir + filename;
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = "";

        try{
            try{
                file = new File(filePath);
                in = new FileInputStream(file);
            }catch(FileNotFoundException fe){
                skip = true;
            }


            client = request.getHeader("User-Agent");
            response.reset();

            if(ext.equals("gif") || ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg"))
            {

                response.setContentType("image/jpeg");
            }
            else
            {

                if(client.indexOf("MSIE") != -1){
                    response.setHeader ("Content-Disposition", "attachment; filename="+new String(filename.getBytes("KSC5601"),"ISO8859_1"));

                }else{
                    filename = new String(filename.getBytes("utf-8"),"iso-8859-1");

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                    response.setHeader("Content-Length", ""+file.length() );
                }
            }

            if(!skip){
                os = response.getOutputStream();
                byte b[] = new byte[(int)file.length()];
                int leng = 0;

                while( (leng = in.read(b)) > 0 ){
                    os.write(b,0,leng);
                }
            }else{
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("File not find.");
                System.out.println("File not find.");
            }

            in.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return filename;

    }
/*
    @Override
    public void delete(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findById(itemDto.getItemidx()).orElse(null);
        String file1 = itemEntity.getFilesEntities().get().getFilename1();
        String file2 = itemEntity.getFilesEntities().get().getFilename2();
        String file3 = itemEntity.getFilesEntities().get().getFilename3();
        String projactPath = "C:\\Users\\YJ\\Desktop\\spring\\shop\\shop\\src\\main\\resources\\static\\image\\";
        File deleteFile1 = new File(projactPath + file1);
        File deleteFile2 = new File(projactPath + file2);
        File deleteFile3 = new File(projactPath + file3);
        File deleteFilethumb = new File(projactPath + "thumbnail\\" + "s_" + file1);
        if(deleteFile1.exists() || deleteFile2.exists() || deleteFile3.exists() || deleteFilethumb.exists()) {
            deleteFile1.delete();
            deleteFile2.delete();
            deleteFile3.delete();
            deleteFilethumb.delete();
        }
        itemRepository.delete(user);
    }

    @Override
    public ItemDto modify(ItemDto itemDto){
        ItemEntity itemEntity = itemRepository.findById(itemDto.getItemidx()).orElse(null);
        return itemEntity.toDto();
    }

    @Override
    public void modify_proc(ItemDto itemDto, String ckb1, String ckb2, String ckb3) throws IOException {
        System.out.println(itemDto.getItemidx());
        ItemEntity itemEntity = itemRepository.findById(itemDto.getItemidx()).orElse(null);
        String projactPath = "C:\\Users\\YJ\\Desktop\\spring\\shop\\shop\\src\\main\\resources\\static\\image\\";

        String ofile1 = itemEntity.getFilename1();
        String ofile2 = itemEntity.getFilename2();
        String ofile3 = itemEntity.getFilename3();

        File deleteFile1 = new File(projactPath + ofile1);
        File deleteFile2 = new File(projactPath + ofile2);
        File deleteFile3 = new File(projactPath + ofile3);
        File deleteFilethumb = new File(projactPath + "thumbnail\\" + "s_" + ofile1);

        String original1 = itemDto.getOriginal1().getOriginalFilename();
        String original2 = itemDto.getOriginal2().getOriginalFilename();
        String original3 = itemDto.getOriginal3().getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String filename1 = uuid + "_" + original1;
        String filename2 = uuid + "_" + original2;
        String filename3 = uuid + "_" + original3;

        if( !original1.isEmpty() && !original2.isEmpty() && !original3.isEmpty()) {
            deleteFile1.delete();
            deleteFile2.delete();
            deleteFile3.delete();
            deleteFilethumb.delete();

            File saveFile1 = new File(projactPath, filename1);
            File saveFile2 = new File(projactPath, filename2);
            File saveFile3 = new File(projactPath, filename3);

            itemDto.getOriginal1().transferTo(saveFile1);
            itemDto.getOriginal2().transferTo(saveFile2);
            itemDto.getOriginal3().transferTo(saveFile3);

            try {
                 추가된 부분...............
                File thumbnailFile = new File(projactPath + "thumbnail\\", "s_" + filename1);

                BufferedImage bo_image = ImageIO.read(saveFile1);

                 비율
                double ratio = 3;
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                // 생성자 매개변수 넓이, 높이, 생성될 이미지 타입
                BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

                Graphics2D graphic = bt_image.createGraphics();

                graphic.drawImage(bo_image, 0, 0, width, height, null);

                ImageIO.write(bt_image, "jpg", thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }

            itemEntity.setFilename1(filename1);
            itemEntity.setOriginal1(original1);
            itemEntity.setFilename2(filename2);
            itemEntity.setOriginal2(original2);
            itemEntity.setFilename3(filename3);
            itemEntity.setOriginal3(original3);

        }
        else if(original1.isEmpty() && ckb1.equals("del") && !ofile2.isEmpty() && !ofile3.isEmpty()) {
            deleteFile1.delete();
            deleteFile2.delete();
            deleteFile3.delete();
            deleteFilethumb.delete();

            File saveFile1 = new File(projactPath, filename1);
            File saveFile2 = new File(projactPath, filename2);
            File saveFile3 = new File(projactPath, filename3);

            itemDto.getOriginal1().transferTo(saveFile1);
            itemDto.getOriginal2().transferTo(saveFile2);
            itemDto.getOriginal3().transferTo(saveFile3);

            try {
                 추가된 부분...............
                File thumbnailFile = new File(projactPath + "thumbnail\\", "s_" + filename2);

                BufferedImage bo_image = ImageIO.read(saveFile2);

                 비율
                double ratio = 3;
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                // 생성자 매개변수 넓이, 높이, 생성될 이미지 타입
                BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

                Graphics2D graphic = bt_image.createGraphics();

                graphic.drawImage(bo_image, 0, 0, width, height, null);

                ImageIO.write(bt_image, "jpg", thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }

            itemEntity.setFilename1(filename1);
            itemEntity.setOriginal1(original1);
            itemEntity.setFilename2(filename2);
            itemEntity.setOriginal2(original2);
            itemEntity.setFilename3(filename3);
            itemEntity.setOriginal3(original3);
        }
        else if(original1.isEmpty() && ckb1.equals("del") && !ofile2.isEmpty() && !ofile3.isEmpty()) {

        }




        itemEntity.setItemname(itemDto.getItemname());
        itemEntity.setItemtype1(itemDto.getItemtype1());
        itemEntity.setItemtype2(itemDto.getItemtype2());
        itemEntity.setItemcountry(itemDto.getItemcountry());
        itemEntity.setItemingred(itemDto.getItemingred());
        itemEntity.setItemprice(itemDto.getItemprice());
        itemEntity.setItemcontent(itemDto.getItemcontent());
        itemEntity.setIteminven(itemDto.getIteminven());
        itemRepository.save(itemEntity);

    }*/
}
