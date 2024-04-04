package project.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.metal.MetalIconFactory;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilesDto {
    private String filename1;
    private String filename2;
    private String filename3;
}
